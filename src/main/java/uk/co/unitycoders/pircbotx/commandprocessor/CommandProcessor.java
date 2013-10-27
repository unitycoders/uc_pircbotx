/**
 * Copyright © 2012 Joseph Walton-Rivers <webpigeon@unitycoders.co.uk>
 *
 * This file is part of uc_PircBotX.
 *
 * uc_PircBotX is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * uc_PircBotX is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * uc_PircBotX. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.unitycoders.pircbotx.commandprocessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * centrally managed command parsing.
 *
 * This class is responsible to breaking IRC messages into commands which the
 * bot can understand. It contains a list of modules and the methods which are
 * tagged with the command annotation. Classes must be registered with the
 * Command Processor in order for the processor to recognise them as commands.
 *
 */
public class CommandProcessor {

    private final Pattern regex;
    private final Map<String, Object> commands;
    private final Map<String, Map<String, Method>> callbacks;

    /**
     * Create a new command processor.
     *
     * This will create a new command processor and will initialise the regex
     * pattern the bot will use to match commands. It will also create the maps
     * needed to store information about the commands.
     *
     * @param trigger the first character of any line directed at the bot
     */
    public CommandProcessor(char trigger) {
        this.regex = Pattern.compile(trigger + "([a-z0-9]+)(?: ([a-z0-9]+))?(?: (.*))?");
        this.commands = new TreeMap<String, Object>();
        this.callbacks = new TreeMap<String, Map<String, Method>>();
    }

    /**
     * Register a new module and extract it's commands.
     *
     * This method will look at target and process any method which has been
     * annotated with the command annotation. It will then remember the module
     * and command names for use when processing messages.
     *
     * The module's name will need to be put before any command. This is to
     * prevent two modules conflicting with the same named commands.
     *
     * @param name the name of the module
     * @param target the module object
     */
    public void register(String name, Object target) {
        Map<String, Method> methods = new HashMap<String, Method>();

        Class<?> clazz = target.getClass();
        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Command c = method.getAnnotation(Command.class);
                String[] keywords = c.value();
                for (String keyword : keywords) {
                    assert !methods.containsKey(keyword);
                    methods.put(keyword, method);
                }
            }
        }

        commands.put(name, target);
        callbacks.put(name, methods);
    }

    /**
     * Process an IRC message to see if the bot needs to respond.
     *
     * This method takes an IRC message and splits it into it's component parts.
     * if the action is valid it will then call the call method to process the
     * event.
     *
     * @param event the event to be processed
     * @throws Exception
     */
    public void invoke(MessageEvent<PircBotX> event) throws Exception {
        Matcher matcher = regex.matcher(event.getMessage());

        // not valid command format
        if (!matcher.matches()) {
            return;
        }

        // XXX lart [thing], thing will be an action
        String command = matcher.group(1);
        String action = matcher.group(2);
        String args = matcher.group(3);

        System.out.println("[DEBUG] Command: " + command);
        System.out.println("[DEBUG] action: " + action);
        System.out.println("[DEBUG] args: " + args);

        try {
            boolean valid;
            if (action == null) {
                valid = false;
            } else {
                valid = call(command, action, event);
            }

            if (!valid) {
                call(command, "default", event);
            }
        } catch (InvocationTargetException ex) {
            Throwable real = ex.getCause();
            real.printStackTrace();
            event.respond("[cmd-error] " + real.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            event.respond("[error] " + ex.getMessage());
        }
    }

    /**
     * Call a module's command with required arguments.
     *
     * @param type the name of the module
     * @param cmd the name of the command
     * @param args the arguments to pass to the method associated with command
     * @return true if method was called, false if not
     * @throws Exception if the method throws an exception.
     */
    private boolean call(String type, String cmd, Object... args) throws Exception {
        Object obj = commands.get(type);
        Map<String, Method> methods = callbacks.get(type);

        System.out.println("Invoking " + type + " : " + cmd + " " + args);

        if (methods == null) {
            return false;
        }

        Method method = methods.get(cmd);
        if (method == null) {
            return false;
        }

        method.invoke(obj, args);
        return true;
    }

    /**
     * Get a list of modules registered with the command processor.
     *
     * @return the list of module names
     */
    public String[] getModules() {
        Collection<String> modules = callbacks.keySet();
        String[] moduleArray = new String[modules.size()];
        return modules.toArray(moduleArray);
    }

    /**
     * Gets a list of commands which are registered with the command processor
     *
     * @param moduleName the name of the module to get the commands from.
     * @return the list of command names, or null if command doesn't exist.
     */
    public String[] getCommands(String moduleName) {
        Map<String, Method> commands = callbacks.get(moduleName);

        if (commands == null) {
            return new String[0];
        }

        Collection<String> names = commands.keySet();
        String[] nameArray = new String[names.size()];
        return names.toArray(nameArray);
    }
}
