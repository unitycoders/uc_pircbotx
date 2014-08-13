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

}
