package uk.co.unitycoders.pircbotx.debug;

import org.pircbotx.PircBotX;
import org.pircbotx.User;

import uk.co.unitycoders.pircbotx.commandprocessor.Message;

public class DummyMessage implements Message {
	private String[] args;
	
	public DummyMessage(String ... args) {
		this.args = args;
	}

	@Override
	public PircBotX getBot() {
		throw new IllegalArgumentException("that is not supported");
	}

	@Override
	public User getUser() {
		throw new IllegalArgumentException("that is not supported");
	}

	@Override
	public String getRawMessage() {
		throw new IllegalArgumentException("that is not supported");
	}

	@Override
	public String getMessage() {
		throw new IllegalArgumentException("that is not supported");
	}

	@Override
	public String getArgument(int id, String defaultValue) {
		if (args.length <= id || id < 0) {
			return defaultValue;
		}
		
		return args[id];
	}

	@Override
	public String getTargetName() {
		return "stdout";
	}

	@Override
	public void respond(String message) {
		System.out.println("[bot] "+message);
	}

	@Override
	public void respondSuccess() {
		System.out.println("[bot] that worked.");
	}

	@Override
	public void sendAction(String action) {
		System.out.println("*bot "+action+"*");
	}

	@Override
	public void insertArgument(int i, String arg) {
		throw new IllegalArgumentException("that is not supported");
	}

	@Override
	public String getArgument(int id) {
		throw new IllegalArgumentException("that is not supported");
	}

}
