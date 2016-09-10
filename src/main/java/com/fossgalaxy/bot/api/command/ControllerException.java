package com.fossgalaxy.bot.api.command;

/**
 * An exception encounted when processing a request, but is not the user's fault.
 *
 * This is the command API's of a HTTP 500.
 */
public class ControllerException extends RuntimeException {
    private final Request request;

    public ControllerException(Request request, String msg) {
        super(msg);
        this.request = request;
    }

    public ControllerException(Request request, String msg, Throwable cause) {
        super(msg, cause);
        this.request = request;
    }
}
