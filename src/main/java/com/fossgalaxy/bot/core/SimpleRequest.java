package com.fossgalaxy.bot.core;

import com.fossgalaxy.bot.api.command.MissingArgumentException;
import com.fossgalaxy.bot.api.command.Request;

import java.util.Collections;
import java.util.List;

/**
 * A simple kind of request.
 */
public class SimpleRequest implements Request {
    private final String controller;
    private final String action;
    private final List<String> arguments;

    public SimpleRequest(String module, List<String> args) {
        this(module, "default", args);
    }

    public SimpleRequest(String controller, String action, List<String> args) {
        this.controller = controller;
        this.action = action;
        this.arguments = args;
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
        if (arguments.size() <= pos || pos < 0) {
            return defaultValue;
        }
        return arguments.get(pos);
    }

    @Override
    public String getArgument(int pos) {
        assert pos >= 0 : "pos must be positive or zero";
        if (arguments.size() <= pos || pos < 0) {
            throw new MissingArgumentException(this, pos);
        }
        return arguments.get(pos);
    }

    @Override
    public List<String> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleRequest)) return false;

        SimpleRequest that = (SimpleRequest) o;

        if (controller != null ? !controller.equals(that.controller) : that.controller != null) return false;
        if (action != null ? !action.equals(that.action) : that.action != null) return false;
        return arguments != null ? arguments.equals(that.arguments) : that.arguments == null;

    }

    @Override
    public int hashCode() {
        int result = controller != null ? controller.hashCode() : 0;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (arguments != null ? arguments.hashCode() : 0);
        return result;
    }

    public String toString() {
        return String.format("Request{cont=%s,act=%s,args=%s}", controller, action, arguments);
    }
}
