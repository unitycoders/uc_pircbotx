package uk.co.unitycoders.pircbotx.modules;

import java.lang.reflect.Method;

import uk.co.unitycoders.pircbotx.commandprocessor.Message;

public class ModuleUtils {

    public static boolean isValidParams(Method method) {
        Class<?>[] types = method.getParameterTypes();
        return types.length > 0 && types[0].equals(Message.class);
    }
    
    public static Module loadModule(String classpath) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    	Class<?> moduleClass = Class.forName(classpath);
    	if (moduleClass.isAssignableFrom(Module.class)) {
    		return (Module)moduleClass.newInstance();
    	}
    	
    	return wrap(moduleClass.newInstance());
    }
	
    public static Module wrap(Object commandClass) {
    	return new LegacyModule(commandClass);
    }
    
}
