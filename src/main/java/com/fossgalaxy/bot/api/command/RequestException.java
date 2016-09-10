package com.fossgalaxy.bot.api.command;

import com.fossgalaxy.bot.api.command.Request;

/**
 * Something that goes wrong processing a request.
 */
public class RequestException extends RuntimeException {
    private final Request request;

    public RequestException(Request request, String msg) {
        super(msg);
        this.request = request;
    }

    public RequestException(Request request, String msg, Throwable cause) {
        super(msg, cause);
        this.request = request;
    }

}
