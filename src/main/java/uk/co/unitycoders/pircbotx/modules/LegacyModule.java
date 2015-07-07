/**
 * Copyright © 2014-2015 Unity Coders
 *
 * This file is part of uc_pircbotx.
 *
 * uc_pircbotx is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * uc_pircbotx is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * uc_pircbotx. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.unitycoders.pircbotx.modules;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandNotFoundException;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.security.Secured;

import java.lang.reflect.Method;
import java.util.*;

public class LegacyModule implements Module {
    private final Object command;
    private final Map<String, Method> methods;
    private final Map<String, String[]> permissions;

    public LegacyModule(Object command) {
        this.command = command;
        this.methods = new TreeMap<String, Method>();
        this.permissions =  new TreeMap<String, String[]>();
    }

    public String[] getRequiredPermissions(String action) {
        return permissions.get(action);
    }

    public boolean isValidAction(String action) {
        return methods.containsKey(action);
    }

    public Collection<String> getActions() {
        return Collections.unmodifiableCollection(methods.keySet());
    }

    void registerAction(String action, Method method) {
        methods.put(action, method);

        Secured permissionsRequired = method.getAnnotation(Secured.class);
        if (permissionsRequired != null) {
            String[] permissionList = permissionsRequired.value();
            permissions.put(action, permissionList);
        }
    }

    static LegacyModule build(Object target) {
        LegacyModule node = new LegacyModule(target);

        Class<?> clazz = target.getClass();
        for (Method method : clazz.getMethods()) {

            Command c = method.getAnnotation(Command.class);
            if (c != null ) {

                // check the class params match our spec
                assert ModuleUtils.isValidParams(method) : "first parameter of a command must be Message";

                String[] keywords = c.value();
                for (String keyword : keywords) {
                    node.registerAction(keyword,method);
                }
            }
        }

        return node;
    }

	@Override
	public void fire(Message message) throws Exception {
		String action = message.getArgument(Module.COMMAND_ARG, Module.DEFAULT_COMMAND);
        Method method = methods.get(action);

        if (method == null) {
            throw new CommandNotFoundException(action);
        }

        method.invoke(command, message);
	}
}
