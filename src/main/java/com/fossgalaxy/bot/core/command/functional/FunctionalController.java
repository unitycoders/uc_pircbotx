package com.fossgalaxy.bot.core.command.functional;

import com.fossgalaxy.bot.api.command.*;
import com.fossgalaxy.bot.core.command.SimpleCatalogue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * A controller built using Java Functional interfaces
 */
public class FunctionalController implements Controller {
    private final Map<String, BiFunction<Context, Request, Response>> actions;

    private String controllerName;
    private Catalogue catalogue;

    public FunctionalController() {
        this.actions = new HashMap<>();
    }

    public void add(String name, BiFunction<Context, Request, Response> action) {
        actions.put(name, action);

        //if we are bound to a catalogue, let them know we gained a new action.
        if (catalogue != null) {
            catalogue.addReverse(controllerName, name);
        }
    }

    @Override
    public Response execute(Context context, Request request) {
        BiFunction<Context, Request, Response> action = actions.get(request.getAction());
        if (action == null) {
            throw new MissingRequestException(request, request.getController(), request.getAction());
        }

        return action.apply(context, request);
    }

    @Override
    public Collection<String> getActions() {
        return actions.keySet();
    }

    @Override
    public void bindCatalogue(String name, SimpleCatalogue catalogue) {
        if (controllerName == null) {
            this.controllerName = name;
            this.catalogue = catalogue;

            for (String action : getActions()) {
                catalogue.addReverse(name, action);
            }
        }
    }
}
