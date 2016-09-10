package com.fossgalaxy.bot.api.command;

import com.fossgalaxy.bot.api.command.RequestException;

/**
 * We expected to see a required argument but it was missing.
 */
public class MissingArgumentException extends RequestException {

    private final int pos;

    public MissingArgumentException(Request request, int pos) {
        super(request, "Missing required argument: "+pos);
        this.pos = pos;
    }

}
