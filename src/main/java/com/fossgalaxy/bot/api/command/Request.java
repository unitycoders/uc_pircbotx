package com.fossgalaxy.bot.api.command;

import java.io.Serializable;
import java.util.List;

/**
 * A request represents an attempt for a user to perform a task.
 */
public interface Request extends Serializable {

    /**
     * Get the controller associated with this users request.
     *
     * A controller represents a logical grouping of actions. Ideally, these should be nouns which describe the object the
     * user is attempting to perform the action upon.
     *
     * Examples include, "lart", "karma", "factoids" or "user".
     *
     * @return the name of the associated controller
     */
    String getController();

    /**
     * Get the action the user is attempting to invoke.
     *
     * An action represents a unit of work which the user wishes to complete. Ideally, these should be verbs that map
     * to a given task that the user is trying to perform.
     *
     * Examples include "add", "checkout", "view" or "join".
     *
     * @return the name of the associated action
     */
    String getAction();

    /**
     * Get an optional positional argument.
     *
     * @param pos the position of the argument
     * @param defaultValue the value to return if the argument is not present
     * @return the argument at position pos or defaultValue
     */
    String getArgument(int pos, String defaultValue);

    /**
     * Get an required positional argument.
     *
     * @param pos the position of the argument
     * @return the argument at position pos
     * @throws RuntimeException if there is no argument at position pos
     */
    String getArgument(int pos);

    /**
     * Get a list of all positional arguments.
     *
     * @return an array containing all of the positional arguments for this request.
     */
    List<String> getArguments();
}
