/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.unitycoders.pircbotx.commands;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;

import java.util.Arrays;

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
        String[] modules = processor.getModules();
        event.respond("Loaded modules are: " + Arrays.toString(modules));
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
        String[] commands = processor.getCommands(moduleName);

        if (commands.length == 0) {
            event.respond("Sorry, that module doesn't exist or has no commands");
        }

        event.respond(args[2] + " contains: " + Arrays.toString(commands));
    }

}
