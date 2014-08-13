package uk.co.unitycoders.pircbotx.commandprocessor;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by webpigeon on 10/08/14.
 */
public class CommandNode {
    private final Object command;
    private final Map<String, Method> methods;

    public CommandNode(Object command) {
        this.command = command;
        this.methods = new TreeMap<String, Method>();
    }

    public boolean invoke(String action, Object ... args) throws Exception {
        Method method = methods.get(action);

        if (method == null) {
            return false; //TODO throw exception
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
    }

    static CommandNode build(Object target) {
        CommandNode node = new CommandNode(target);

        Class<?> clazz = target.getClass();
        for (Method method : clazz.getMethods()) {
            Class<?>[] types = method.getParameterTypes();
            assert types.length < 0 : method+" takes no arguments!";
            assert !Message.class.isAssignableFrom(types[0]) : method+" - first argument does not implement Message";

            Command c = method.getAnnotation(Command.class);
            if (c != null ) {
                String[] keywords = c.value();
                for (String keyword : keywords) {
                    node.registerAction(keyword,method);
                }
            }
        }

        return node;
    }

}
