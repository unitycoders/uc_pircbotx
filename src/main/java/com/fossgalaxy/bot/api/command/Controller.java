package com.fossgalaxy.bot.api.command;

import java.util.Collection;

/**
 * A controller is a logical grouping of actions.
 *
 *
 */
public interface Controller {

    Response execute(Context user, Request request);

    default boolean hasAction(String name) {
        Collection<String> actions = getActions();
        if (actions == null) {
            return false;
        }
        return actions.contains(name);
    }

    Collection<String> getActions();
}
