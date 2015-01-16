/**
 * Copyright © 2012-2015 Unity Coders
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
package uk.co.unitycoders.pircbotx.commandprocessor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pircbotx.User;
import uk.co.unitycoders.pircbotx.security.*;
import uk.co.unitycoders.pircbotx.security.SecurityManager;

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
    private final Pattern tokeniser;
    private final Map<String, CommandNode> commands;
    private final SecurityManager security;

    /**
     * Create a new command processor.
     *
     * This will create a new command processor and will initialise the regex
     * pattern the bot will use to match commands. It will also create the maps
     * needed to store information about the commands.
     */
    public CommandProcessor(SecurityManager security) {
        this.tokeniser = Pattern.compile("([^\\s\"']+)|\"([^\"]*)\"|'([^']*)'");
        this.commands = new TreeMap<String, CommandNode>();
        this.security = security;
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
        CommandNode node = CommandNode.build(target);
        commands.put(name, node);
    }

    //proper aliasing of commands is now possible
    public void alias(String name, String oldName) {
        CommandNode node = commands.get(oldName);

        if (oldName == null) {
            throw new RuntimeException(oldName + " is not a loaded class");
        }

        CommandNode aliasNode = commands.get(name);
        if (aliasNode != null) {
            throw new RuntimeException(name + " is already a keyword!");
        }

        commands.put(name, node);
    }

    public void remove(String command) {
        commands.remove(command);
    }

    
    public List<String> tokenise(String message) {
    	
    	List<String> arguments = new ArrayList<String>();
    	
    	Matcher matcher = tokeniser.matcher(message);
    	while(matcher.find()) {
    		if (matcher.group(1) != null) {
    			arguments.add(matcher.group(1));
    		}else if (matcher.group(2) != null) {
    			arguments.add(matcher.group(2));
    		}else if (matcher.group(3) != null) {
    			arguments.add(matcher.group(3));
    		}
    	}
    	
    	return arguments;
    }

    /**
     * Process an IRC message to see if the bot needs to respond.
     *
     * This method takes an IRC message and splits it into it's component parts.
     * if the action is valid it will then call the call method to process the
     * event.
     *
     * @param message the event to be processed
     * @throws Exception
     */
    public void invoke(Message message) throws Exception {
    	List<String> arguments = tokenise(message.getMessage());
    	System.out.println("[debug] decoded: "+arguments);
    	
    	int argc = arguments.size();
    	String command = arguments.get(0);
    	//XXX not happy about this, should probably be dealt with before ending up here...
    	message.setArguments(arguments);
    	
    	CommandNode node = commands.get(command);
    	if (node == null) {
    		throw new CommandNotFoundException(command);
    	}
    	
    	String action = "default";
    	if (argc < 2 || !node.isValidAction(arguments.get(1))){
    		//ensure that if default is invoked that default is on the queue
    		arguments.add(1, "default");
    	}else {
    		action = arguments.get(1);
    	}
    	
    	if (!checkPermissions(node, action, message.getUser())) {
    		throw new PermissionException();
    	}
    	
    	try {
    		node.invoke(action, message);
    	} catch (CommandNotFoundException ex) {
    		throw new CommandNotFoundException(command + " " + action);
    	} catch (Exception ex) {
    		message.respond("Something has gone wrong, please let the developers know");
    		System.err.println("Exception thrown: "+ex.getMessage());
    	}
    }

    private boolean checkPermissions(CommandNode node, String action, User user) {
        //check if security is disabled
        if (security == null) {
            return true;
        }

        String[] permissions = node.getRequiredPermissions(action);
        if (permissions != null) {
            Session session = security.getSession(user);
            return session != null && session.hasPermissions(permissions);
        }
        return true;
    }

    /**
     * Get a list of modules registered with the command processor.
     *
     * @return the list of module names
     */
    public Collection<String> getModules() {
        return Collections.unmodifiableCollection(commands.keySet());
    }


    /**
     * Gets a list of commands which are registered with the command processor
     *
     * @param moduleName the name of the module to get the commands from.
     * @return the list of command names, or null if command doesn't exist.
     */
    public Collection<String> getCommands(String moduleName) {
        if (moduleName == null) {
            return Collections.emptyList();
        }

        CommandNode command = commands.get(moduleName);

        if (command == null) {
            return Collections.emptyList();
        }

        return command.getActions();
    }
}
