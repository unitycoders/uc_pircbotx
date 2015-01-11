package uk.co.unitycoders.pircbotx.commandprocessor;

import uk.co.unitycoders.pircbotx.security.Secured;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by webpigeon on 10/08/14.
 */
public class CommandNode {
    private final Object command;
    private final Map<String, Method> methods;
    private final Map<String, String[]> permissions;

    public CommandNode(Object command) {
        this.command = command;
        this.methods = new TreeMap<String, Method>();
        this.permissions =  new TreeMap<String, String[]>();
    }

    public String[] getRequiredPermissions(String action) {
        return permissions.get(action);
    }

    public boolean invoke(String action, Object ... args) throws Exception {
        Method method = methods.get(action);

        if (method == null) {
            throw new CommandNotFoundException("");
        }

        method.invoke(command, args);
        return true;
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

    static CommandNode build(Object target) {
        CommandNode node = new CommandNode(target);

        Class<?> clazz = target.getClass();
        for (Method method : clazz.getMethods()) {

            Command c = method.getAnnotation(Command.class);
            if (c != null ) {

                // check the class params match our spec
                assert isValidParams(method) : "first parameter of a command must be Message";

                String[] keywords = c.value();
                for (String keyword : keywords) {
                    node.registerAction(keyword,method);
                }
            }
        }

        return node;
    }

    private static boolean isValidParams(Method method) {
        Class<?>[] types = method.getParameterTypes();
        return types.length > 0 && types[0].equals(Message.class);
    }
}
