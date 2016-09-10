package com.fossgalaxy.bot.api.command;

import java.util.Map;

/**
 * This class represents information associated with a request.
 *
 * This could include user-specific information and request specific information.
 */
public interface Context extends Map {
    String MESSAGE_TARGET = "messageTarget";

    /**
     * A unique identifier for this user.
     *
     * This should be globally unique string and can be completely decided by the backend. The same user should have
     * the same session key for subsequent requests and session keys should not be shared between users.
     */
    String SESSION_KEY = "sessionKey";

    /**
     * The protocol the user is connected over.
     */
    String PROTOCOL = "protocol";
}
