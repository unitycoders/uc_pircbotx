package uk.co.unitycoders.pircbotx.commandprocessor;

public class CommandNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final String command;
	
	public CommandNotFoundException(String command) {
		super(command+" is not a valid command.");
		this.command = command;
	}
	
	public String getCommandName() {
		return command;
	}

}
