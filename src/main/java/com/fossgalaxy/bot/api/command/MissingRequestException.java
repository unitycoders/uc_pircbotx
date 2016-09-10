package com.fossgalaxy.bot.api.command;

/**
 * Either the controller or requested action did not exist
 */
public class MissingRequestException extends RequestException {

    private final String controller;
    private final String action;

    public MissingRequestException(Request request, String controller) {
        super(request, String.format("'%s' is not a loaded controller", controller));
        this.controller = controller;
        this.action = null;
    }

    public MissingRequestException(Request request, String controller, String action) {
        super(request, String.format("'%s' has no action '%s'", controller, action));
        this.controller = controller;
        this.action = action;
    }

    public boolean validController() {
        return controller != null && action == null;
    }

}
