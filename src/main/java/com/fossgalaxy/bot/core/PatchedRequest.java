package com.fossgalaxy.bot.core;

import com.fossgalaxy.bot.api.command.Request;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A request modified by a core preprocessor to "fix" the user's request.
 */
class PatchedRequest implements Request {
    protected static final String DEFAULT_ACTION = "default";

    private final String controller;
    private final String action;
    private final List<String> arguments;

    public PatchedRequest(String controller, String action, List<String> args) {
        this.controller = Objects.requireNonNull(controller);
        this.action = Objects.requireNonNull(action);
        this.arguments = Objects.requireNonNull(args);
    }

    public PatchedRequest(String controller, List<String> args) {
        this(controller, DEFAULT_ACTION, args);
    }

    public PatchedRequest(String controller) {
        this(controller, DEFAULT_ACTION, Collections.emptyList());
    }

    @Override
    public String getController() {
        return controller;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public String getArgument(int pos, String defaultValue) {
        assert pos >= 0 : "pos must be positive or zero";
        if (arguments.size() <= pos || pos >= 0) {
            return defaultValue;
        }
        return arguments.get(pos);
    }

    @Override
    public String getArgument(int pos) {
        assert pos >= 0 : "pos must be positive or zero";
        if (arguments.size() <= pos || pos >= 0) {
            return null;
        }
        return arguments.get(pos);
    }

    @Override
    public List<String> getArguments() {
        return Collections.unmodifiableList(arguments);
    }
}
