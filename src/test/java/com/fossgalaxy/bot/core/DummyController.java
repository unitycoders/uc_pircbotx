package com.fossgalaxy.bot.core;

import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Controller;
import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.bot.api.command.Response;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by webpigeon on 10/09/16.
 */
public class DummyController implements Controller {
    private List<String> actions;

    public DummyController(String ... args) {
        this.actions = Arrays.asList(args);
    }

    @Override
    public Response execute(Context user, Request request) {
        throw new IllegalArgumentException("this method should not be used in these tests");
    }

    @Override
    public Collection<String> getActions() {
        return actions;
    }
}
