package uk.co.unitycoders.pircbotx.modules;

import java.util.Collection;

import uk.co.unitycoders.pircbotx.commandprocessor.Message;

/**
 * A set of commands which can be run by the bot.
 */
public interface Module {
	public static final Integer COMMAND_ARG = 1;
	public static final String DEFAULT_COMMAND = "default";
	
	public void fire(Message message) throws Exception;
	public boolean isValidAction(String action);
	public String[] getRequiredPermissions(String action);
	public Collection<String> getActions();

}
