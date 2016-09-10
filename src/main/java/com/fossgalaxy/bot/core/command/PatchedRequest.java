package com.fossgalaxy.bot.core.command;

import com.fossgalaxy.bot.api.command.Request;

import java.util.List;

/**
 * A request modified by a core preprocessor to "fix" the user's request.
 */
class PatchedRequest extends SimpleRequest {
    protected static final String DEFAULT_ACTION = "default";
    private final Request original;

    public PatchedRequest(Request original, String controller, String action, List<String> args) {
        super(controller, action, args);
        this.original = original;
    }

}
