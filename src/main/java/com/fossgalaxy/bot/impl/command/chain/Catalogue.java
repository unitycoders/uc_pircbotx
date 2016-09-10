package com.fossgalaxy.bot.impl.command.chain;

import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Controller;
import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.bot.api.command.Response;

import java.util.*;

/**
 * Created by webpigeon on 10/09/16.
 */
public class Catalogue {
    private final Map<String, Controller> controllers;

    public Catalogue(){
        this.controllers = new HashMap<>();
    }

    public Controller get(String name) {
        name = Objects.requireNonNull(name);
        return controllers.get(name);
    }

    public boolean exists(String name) {
        name = Objects.requireNonNull(name);
        return controllers.containsKey(name);
    }

    public Response execute(Context context, Request request) {
        Controller controller = controllers.get(request.getController());
        if (controller == null) {
            //TODO handle non-existant controllers
        }
        return controller.execute(context, request);
    }

    public List<String> findByAction(String name) {
        return Collections.emptyList();
    }
}
