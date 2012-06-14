/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.unitycoders.pircbotx.commandProcessor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * centrally managed command parsing.
 *
 */
public class CommandProcessor {
    private final Map<String, Object> commands;
    private final Map<String, Map<String, Method>> callbacks;

    public CommandProcessor(){
        this.commands = new HashMap<String, Object>();
        this.callbacks = new HashMap<String, Map<String, Method>>();
    }

    public void register(String name, Object target){
        Map<String, Method> methods = new HashMap<String, Method>();

        Class<?> clazz = target.getClass();
        for(Method method : clazz.getMethods()){
            if(method.isAnnotationPresent(Command.class)){
                Command c = method.getAnnotation(Command.class);
                assert !methods.containsKey(c.keyword());
                methods.put(c.keyword(), method);
            }
        }

        commands.put(name, target);
        callbacks.put(name, methods);
    }


    public void invoke(MessageEvent<PircBotX> event) throws Exception{
        String[] parts = event.getMessage().split(" ");

        boolean valid;
        if(parts.length == 1){
            valid = false;
        }else{
            valid = call(parts[0], parts[1], event);
        }

        if(!valid){
            call(parts[0], "default", event);
        }
    }

    private boolean call(String type, String cmd, Object ... args) throws Exception{
        Object obj = commands.get(type);
        Map<String,Method> methods = callbacks.get(type);

        if(methods == null){
            return false;
        }

        Method method = methods.get(cmd);
        if(method == null){
            return false;
        }

        method.invoke(obj, args);
        return true;
    }
}
