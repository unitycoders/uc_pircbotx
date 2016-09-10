package com.fossgalaxy.bot.api.command;

import java.util.Map;

/**
 * This class represents information associated with a request.
 *
 * This could include user-specific information and request specific information.
 */
public interface Context extends Map {
    String MESSAGE_TARGET = "messageTarget";
}
