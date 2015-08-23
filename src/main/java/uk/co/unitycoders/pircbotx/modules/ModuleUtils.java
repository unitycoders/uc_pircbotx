package uk.co.unitycoders.pircbotx.modules;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

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
    	
    	return wrap(moduleClass.getSimpleName(), moduleClass.newInstance());
    }
	
    public static Module wrap(String name, Object commandClass) {
    	return new AnnotationModule(name, commandClass);
    }
    
    public static List<Module> loadModules() {
    	ServiceLoader<Module> moduleLoader = ServiceLoader.load(Module.class);
    	List<Module> modules = new ArrayList<Module>();
    	
    	for (Module m : moduleLoader) {
    		modules.add(m);
    	}
    	
		return modules;	
    }
}
