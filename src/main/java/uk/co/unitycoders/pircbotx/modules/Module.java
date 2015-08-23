package uk.co.unitycoders.pircbotx.modules;

import java.util.Collection;

import uk.co.unitycoders.pircbotx.commandprocessor.Message;

/**
 * A set of commands which can be run by the bot.
 */
public interface Module {
	public static final Integer MODULE_ARG = 0;
	public static final Integer COMMAND_ARG = 1;
	public static final String DEFAULT_COMMAND = "default";
	
	/**
	 * Inform the module one if it's commands has been invoked.
	 * 
	 * @param message the message which caused the invocation
	 * @throws Exception in the event of failure.
	 */
	public void fire(Message message) throws Exception;
	
	/**
	 * Check if an action is known to the module.
	 * 
	 * @param action the action to check
	 * @return true if the action is known, false otherwise
	 */
	public boolean isValidAction(String action);
	
	/**
	 * Get the permissions which are required to use an action.
	 * 
	 * @param action the action to check
	 * @return a list of permissions required
	 */
	public String[] getRequiredPermissions(String action);
	
	/**
	 * Get an unmodifiable collection of all actions defined in this class.
	 * 
	 * @return the unmodifiable collection containing the actions defined in this class.
	 */
	public Collection<String> getActions();

	/**
	 * Get the name of the module
	 * 
	 * @return the string name of the module
	 */
	public String getName();

}
