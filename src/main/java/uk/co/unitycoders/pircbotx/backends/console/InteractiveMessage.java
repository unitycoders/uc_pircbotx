package uk.co.unitycoders.pircbotx.backends.console;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import org.pircbotx.PircBotX;
import org.pircbotx.User;

import uk.co.unitycoders.pircbotx.backends.AbstractMessage;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;

public class InteractiveMessage extends AbstractMessage {
	private final PrintStream out;
	
	public InteractiveMessage(List<String> args, PrintStream out) {
		super(args);
		this.out = out;
	}
	
	@Override
	public PircBotX getBot() {
		return null;
	}

	@Override
	public User getUser() {
		return null;
	}

	@Override
	public String getTargetName() {
		return "consoleUser";
	}

	@Override
	public void respond(String message) {
		out.println(String.format("bot: %s", message));
	}

	@Override
	public void sendAction(String action) {
		out.println(String.format("* %s *", action));
	}

}
