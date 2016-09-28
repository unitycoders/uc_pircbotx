package uk.co.unitycoders.pircbotx.backends.console;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.middleware.BotMiddleware;

/**
 * This emulates the bot as in interactive prompt on the terminal.
 */
public class InteractivePrompt {

	public static void main(String[] args) {
		List<BotMiddleware> middleware = Collections.emptyList();
		CommandProcessor processor = new CommandProcessor(middleware);
		
		doPrompt(processor);
	}
	
	private static void doPrompt(CommandProcessor processor) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Interactive Prompt");
		System.out.println("the bot will evaluate any input in stdin as a command");
		
		while(scanner.hasNextLine()) {
			try {
				processor.invoke(buildMessage(processor, scanner.nextLine()));
			} catch (Exception ex) {
				System.err.println(String.format("[error] %s", ex));
				ex.printStackTrace();
			}
		}
		
		scanner.close();
	}
	
	private static Message buildMessage(CommandProcessor processor, String line) {
		List<String> args = processor.processMessage(line);
		return new InteractiveMessage(args, System.out);
	}
}
