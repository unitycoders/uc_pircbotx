package com.fossgalaxy.bot.utils.commands;

import com.fossgalaxy.bot.api.command.Catalogue;
import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.bot.api.command.Response;
import com.fossgalaxy.bot.core.command.annotation.Action;
import com.fossgalaxy.bot.core.command.annotation.AnnotationController;
import com.google.inject.Inject;

import java.util.Set;
import java.util.SortedSet;

/**
 * Provide commands to allow inspection of the catalogue online.
 *
 * This allows users to see what the bot has to offer and allows them to find out information about a particular action.
 */
public class CatelogueController extends AnnotationController {

    private Catalogue catalogue;

    @Inject
    public CatelogueController(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    /**
     * Provides the ability to list controllers which are currently loaded into the catalogue.
     *
     * @Param ctx the context of the request
     * @Param request the request itself
     */
    @Action({"list", "plugins", "controllers"})
    public Response listControllers(Context ctx, Request request) {
        Set<String> controllers = catalogue.getKeywords();
        String list = String.join(",", controllers);

        return Response.respond(String.format("the loaded controllers are: %s", list));
    }

    /**
     * Provides the ability to lookup what controller provides a given action.
     *
     * @param ctx the request context
     * @param request the request itself
     * @return a response indicating if the search was successful
     */
    @Action({"lookup", "provides", "action"})
    public Response findReverse(Context ctx, Request request) {
        String action = request.getArgument(0);

        SortedSet<String> controllers = catalogue.findByAction(action);
        if (controllers.isEmpty()) {
            return Response.respond("doesn't look like that's an action");
        }

        if (controllers.size() == 1) {
            return Response.respond(String.format("%s is provided by %s", action, controllers.first()));
        } else {
            String list = String.join(",", controllers);
            return Response.respond(String.format("%s is provided by (in order of precedence): %s", action, list));
        }
    }



}
