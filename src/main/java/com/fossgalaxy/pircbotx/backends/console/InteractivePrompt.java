package com.fossgalaxy.pircbotx.backends.console;

import com.fossgalaxy.pircbotx.commandprocessor.CommandNotFoundException;
import com.fossgalaxy.pircbotx.commandprocessor.CommandProcessor;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.middleware.BotMiddleware;
import com.fossgalaxy.pircbotx.modules.ModuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * This emulates the bot as in interactive prompt on the terminal.
 */
public class InteractivePrompt {
    private final static Logger LOG = LoggerFactory.getLogger(InteractivePrompt.class);

    public static void main(String[] args) {
        List<BotMiddleware> middleware = Collections.emptyList();
        CommandProcessor processor = new CommandProcessor(middleware);

        doPrompt(processor);
    }

    private static void doPrompt(CommandProcessor processor) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Interactive Prompt");
        System.out.println("the bot will evaluate any input in stdin as a command");

        while (scanner.hasNextLine()) {
            try {
                processor.invoke(buildMessage(processor, scanner.nextLine()));
            } catch (ModuleException ex) {
                LOG.error("Something went wrong,", ex);
            } catch (CommandNotFoundException ex) {
                System.out.println("Sorry, that's not a valid command");
                LOG.debug("command was not found by processor", ex);
            }
        }

        scanner.close();
    }

    private static Message buildMessage(CommandProcessor processor, String line) {
        List<String> args = processor.processMessage(line);
        return new InteractiveMessage(args, System.out);
    }
}
