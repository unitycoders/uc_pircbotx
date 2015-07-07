package uk.co.unitycoders.pircbotx.modules;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandNotFoundException;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.security.Secured;

public class AnnotationModule implements Module {
	protected final Map<String, Node> nodes;
	
	public AnnotationModule() {
		this.nodes = new TreeMap<>();
		processAnnotations();
	}
	
	@Override
	public void fire(Message message) throws Exception {
		String action = message.getArgument(Module.COMMAND_ARG, Module.DEFAULT_COMMAND);
		
		Method method = nodes.get(action).method;
        if (method == null) {
            throw new CommandNotFoundException(action);
        }

        method.invoke(this, message);
	}
	
	@Override
	public String[] getRequiredPermissions(String action) {
		Node node = nodes.get(action);
		if (node == null) {
			return new String[0];
		}
		return node.permissions;
	}
	
	@Override
	public boolean isValidAction(String action) {
		return nodes.containsKey(action);
	}
	
	protected void processAnnotations() {
        Class<?> clazz = this.getClass();
        for (Method method : clazz.getMethods()) {

            Command c = method.getAnnotation(Command.class);
            if (c != null ) {

                // check the class params match our spec
                assert ModuleUtils.isValidParams(method) : "first parameter of a command must be Message";

                String[] keywords = c.value();
                for (String keyword : keywords) {
                    registerAction(keyword,method);
                }
            }
        }
	}
	
    protected void registerAction(String action, Method method) {
    	Node node = new Node();
    	node.method = method;
        nodes.put(action, node);
        
        //Permissions annotation
        Secured permissionsRequired = method.getAnnotation(Secured.class);
        if (permissionsRequired != null) {
            node.permissions = permissionsRequired.value();
        }
    }

	@Override
	public Collection<String> getActions() {
		return Collections.unmodifiableCollection(nodes.keySet());
	}
	
	static class Node {
		protected Method method;
		protected String[] permissions;
	}
}
