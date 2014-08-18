/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.unitycoders.pircbotx.commands;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;

import java.util.Arrays;
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
