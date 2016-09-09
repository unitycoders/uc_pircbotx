package com.fossgalaxy.bot.api.command;

/**
 * A controller is a logical grouping of actions.
 *
 *
 */
public interface Controller {

    Response execute(Context user, Request request);

}
