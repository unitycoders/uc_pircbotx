package com.fossgalaxy.pircbotx.modules;

import com.fossgalaxy.pircbotx.commandprocessor.CommandNotFoundException;
import com.fossgalaxy.pircbotx.commandprocessor.Message;

import java.util.Collection;

/**
 * A set of commands which can be run by the bot.
 */
public interface Module {
    Integer MODULE_ARG = 0;
    Integer COMMAND_ARG = 1;
    String DEFAULT_COMMAND = "default";

    /**
     * Inform the module one if it's commands has been invoked.
     *
     * @param message the message which caused the invocation
     * @throws Exception in the event of failure.
     */
    void fire(Message message) throws ModuleException, CommandNotFoundException;

    /**
     * Check if an action is known to the module.
     *
     * @param action the action to check
     * @return true if the action is known, false otherwise
     */
    boolean isValidAction(String action);

    /**
     * Get the permissions which are required to use an action.
     *
     * @param action the action to check
     * @return a list of permissions required
     */
    String[] getRequiredPermissions(String action);

    /**
     * Get an unmodifiable collection of all actions defined in this class.
     *
     * @return the unmodifiable collection containing the actions defined in this class.
     */
    Collection<String> getActions();

    /**
     * Get the name of the module
     *
     * @return the string name of the module
     */
    String getName();

    String getHelp(String command);

    String getModuleHelp();

    String[] getArgumentsFor(String action);

}
