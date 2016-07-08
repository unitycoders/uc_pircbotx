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

import java.util.Collection;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.co.unitycoders.pircbotx.commandprocessor.HelpText;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.modules.AnnotationModule;
import uk.co.unitycoders.pircbotx.modules.Module;

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
	@HelpText("Displays infomation how to use the bot")
	public void onHelp(Message event) {
		event.respond("Type 'help modules' for a list of modules or 'help commands' to see valid commands for a module.");
	}

	@Command("modules")
	@HelpText("list all loaded modules")
	public void onList(Message event) {
		Collection<String> modules = processor.getModules();
		event.respond("Loaded modules are: " + modules+" type 'help module <name>' for more infomation or 'help commands <module>' to see valid commands");
	}

	@Command("info")
	@HelpText("wrapper around mod-info and cmd-info for ease of use")
	public void onInfo(Message event) {
		String moduleName = event.getArgument(2);
		String commandName = event.getArgument(3, null);

		if (commandName == null) {
			onModuleHelp(event);
		} else {
			onCommandHelp(event);
		}
	}

	@Command("module")
	@HelpText("shows help on a module's description")
	public void onModuleHelp(Message event) {
		String moduleName = event.getArgument(2, null);

		if (moduleName == null) {
			throw new IllegalArgumentException("[module]");
		}

		Module module = processor.getModule(moduleName);
		if (module == null) {
			event.respond("Sorry, there isn't a module named "+moduleName);
			return;
		}

		String moduleHelp = module.getModuleHelp();
		if (moduleHelp == null) {
			event.respond("Sorry, looks like the developer hasn't provided HelpText");
		} else {
			event.respond(moduleHelp);
		}
	}

	@Command("command")
	@HelpText("shows a description of a command")
	public void onCommandHelp(Message event) {
		String moduleName = event.getArgument(2, null);
		String commandName = event.getArgument(3, null);

		if (moduleName == null || commandName == null) {
			throw new IllegalArgumentException("[module] [command]");
		}

		Module module = processor.getModule(moduleName);
		if (module == null) {
			event.respond("Sorry, there isn't a module named "+moduleName);
			return;
		}

		String moduleHelp = module.getHelp(commandName);
		if (moduleHelp == null) {
			event.respond("Sorry, looks like the developer hasn't provided HelpText");
		} else {
			event.respond(moduleHelp);
		}
	}

	@Command("commands")
	@HelpText("Show a list of commands povided by a module")
	public void onCommands(Message event) {
		String moduleName = event.getArgument(2, null);

		if (moduleName == null) {
			throw new IllegalArgumentException("[module]");
		}

		Collection<String> commands = processor.getCommands(moduleName);

		if (commands.isEmpty()) {
			event.respond("Sorry, that module doesn't exist or has no commands");
		} else {
			event.respond(moduleName + " contains: " + commands+" type 'help command <module> <command>' for a description.");
		}
	}

}
