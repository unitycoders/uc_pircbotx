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
import uk.co.unitycoders.pircbotx.commandprocessor.Message;

import java.util.Collection;

/**
 * Displays information on other commands.
 *
 * This plug in helps users find information about the bot's capabilties and how
 * to use the bot.
 */
public class HelpCommand {

    private final CommandProcessor processor;

    public HelpCommand(CommandProcessor processor) {
        this.processor = processor;
    }

    @Command
    public void onList(Message event) {
        Collection<String> modules = processor.getModules();
        event.respond("Loaded modules are: " + modules);
    }

    @Command("commands")
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
