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
package uk.co.unitycoders.pircbotx.commands;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.co.unitycoders.pircbotx.commandprocessor.HelpText;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.modules.AnnotationModule;
import uk.co.unitycoders.pircbotx.modules.Module;

import java.util.Collection;

/**
 * Displays information on other commands.
 *
 * This plug in helps users find information about the bot's capabilties and how
 * to use the bot.
 */
@HelpText("Provides infomation about modules")
public class HelpCommand extends AnnotationModule {

    private final CommandProcessor processor;

    public HelpCommand(CommandProcessor processor) {
    	super("help");
        this.processor = processor;
    }

    @Command
    @HelpText("list all loaded modules")
    public void onList(Message event) {
        Collection<String> modules = processor.getModules();
        event.respond("Loaded modules are: " + modules);
    }
    
    @Command("info")
    @HelpText("wrapper around mod-info and cmd-info for ease of use")
    public void onInfo(Message event) {
    	String moduleName = event.getArgument(2, null);
    	String commandName = event.getArgument(3, null);
    	
    	if (moduleName == null) {
            event.respond("usage: help info [module] (command)");
            return;
    	}
    	
    	if (commandName == null) {
            onModuleHelp(event);
    	} else {
    		onCommandHelp(event);
    	}
    }
    
    @Command("mod-info")
    @HelpText("shows help on a module's description")
    public void onModuleHelp(Message event) {
    	String moduleName = event.getArgument(2, null);

        if (moduleName == null) {
            event.respond("usage: help mod-info [module]");
        }

        Module module = processor.getModule(moduleName);
        if (module == null) {
        	event.respond("Sorry, there isn't a module named "+moduleName);
        }
        
        String moduleHelp = module.getModuleHelp();
        if (moduleHelp == null) {
        	event.respond("Sorry, loops like the developer hasn't provided HelpText");
        } else {
        	event.respond(moduleHelp);
        }
    }
    
    @Command("cmd-info")
    @HelpText("shows a description of a command")
    public void onCommandHelp(Message event) {
    	String moduleName = event.getArgument(2, null);
    	String commandName = event.getArgument(3, null);

        if (moduleName == null || commandName == null) {
            event.respond("usage: help cmd-info [module] [command]");
            return;
        }

        Module module = processor.getModule(moduleName);
        if (module == null) {
        	event.respond("Sorry, there isn't a module named "+moduleName);
        }
        
        String moduleHelp = module.getHelp(commandName);
        if (moduleHelp == null) {
        	event.respond("Sorry, loops like the developer hasn't provided HelpText");
        } else {
        	event.respond(moduleHelp);
        }
    }

    @Command("commands")
    @HelpText("Show a list of commands povided by a module")
    public void onHelp(Message event) {
        String line = event.getMessage();
        String[] args = line.split(" ");

        if (args.length != 3) {
            event.respond("usage: help commands [module]");
            return;
        }

        String moduleName = args[2];
        Collection<String> commands = processor.getCommands(moduleName);

        if (commands.isEmpty()) {
            event.respond("Sorry, that module doesn't exist or has no commands");
        } else {
            event.respond(args[2] + " contains: " + commands);
        }
    }

}
