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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.unitycoders.pircbotx.middleware.BotMiddleware;
import uk.co.unitycoders.pircbotx.modules.Module;

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
    private final Logger logger = LoggerFactory.getLogger(CommandProcessor.class);
    private final Pattern tokeniser;
    private final Map<String, Module> commands;
    private final List<BotMiddleware> middleware;

    /**
     * Create a new command processor.
     *
     * This will create a new command processor and will initialise the regex
     * pattern the bot will use to match commands. It will also create the maps
     * needed to store information about the commands.
     */
    public CommandProcessor(List<BotMiddleware> middleware) {
    	assert middleware != null : "don't pass null as the middleware, use empty list instead";
        this.tokeniser = Pattern.compile("([^\\s\"']+)|\"([^\"]*)\"|'([^']*)'");
        this.commands = new LinkedHashMap<String, Module>();
        this.middleware = middleware;
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
    public void register(String name, Module target) {
        commands.put(name, target);
    }

    /**
     * Alias an existing module to a new name.
     * 
     * This function will let you define an alias for an existing module.
     * Passing null as either argument is not permitted.
     * 
     * @param name the new name to use as the alias
     * @param oldName the existing name of the module
     * @throws IllegalArugmentException if module is not defined or alias already exists
     */
    public void alias(String name, String oldName) {
    	assert name != null : "name must not be null";
    	assert oldName != null : "old name must not be null";
    	
        Module node = commands.get(oldName);

        if (node == null) {
            throw new IllegalArgumentException(oldName + " is not a loaded class");
        }

        Module aliasNode = commands.get(name);
        if (aliasNode != null) {
            throw new IllegalArgumentException(name + " is already a keyword!");
        }

        commands.put(name, node);
    }

    public void remove(String command) {
        commands.remove(command);
    }

    
    protected List<String> tokenise(String message) {
    	
    	List<String> arguments = new ArrayList<String>();
    	
    	Matcher matcher = tokeniser.matcher(message);
    	while(matcher.find()) {
    		if (matcher.group(1) != null) {
    			arguments.add(matcher.group(1));
    		} else if (matcher.group(2) != null) {
    			arguments.add(matcher.group(2));
    		} else {
    			assert matcher.group(3) != null;
    			arguments.add(matcher.group(3));
    		}
    	}
    	
    	return arguments;
    }

    public List<String> processMessage(String inputMessage) {
    	
    	//let other parts of the bot modify the message if needed
    	for (BotMiddleware mw : middleware) {
    		inputMessage = mw.preprocess(inputMessage);
    		assert inputMessage != null : mw+" voilates contract";
    	}
    	
    	return tokenise(inputMessage);
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
    	for (BotMiddleware mw : middleware) {
    		message = mw.process(this, message);
    		
    		assert message != null : mw+" voilates contract";
    	}
    	
    	String command = message.getArgument(Module.MODULE_ARG, null);
    	Module node = commands.get(command);
    	if (node == null) {
    		throw new CommandNotFoundException(command);
    	}
    	
    	try {
    		node.fire(message);
    	} catch (CommandNotFoundException ex) {
    		message.respond("That's not a valid command");
    		throw ex;
    	} catch (Exception ex) {
    		message.respond("Something has gone wrong, please let the developers know");
    		logger.error("Exception thrown", ex);
    		throw ex;
    	}
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

        Module command = commands.get(moduleName);

        if (command == null) {
            return Collections.emptyList();
        }

        return command.getActions();
    }

	public Module getModule(String moduleName) {
		if (moduleName == null) {
			return null;
		}
		
		return commands.get(moduleName);
	}
}
