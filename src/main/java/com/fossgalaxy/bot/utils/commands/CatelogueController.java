package com.fossgalaxy.bot.utils.commands;

import com.fossgalaxy.bot.api.command.*;
import com.fossgalaxy.bot.core.command.annotation.Action;
import com.fossgalaxy.bot.core.command.annotation.AnnotationController;
import com.fossgalaxy.pircbotx.commandprocessor.HelpText;
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
        this.process();
    }

    /**
     * Provides the ability to list controllers which are currently loaded into the catalogue.
     *
     * @Param ctx the context of the request
     * @Param request the request itself
     */
    @Action({"list", "plugins", "controllers"})
    @HelpText("List loaded controllers")
    public Response listControllers(Context ctx, Request request) {
        Set<String> controllers = catalogue.getKeywords();
        String list = String.join(", ", controllers);

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
    @HelpText("find the controller associated with an action")
    public Response findReverse(Context ctx, Request request) {
        String action = request.getArgument(0);

        SortedSet<String> controllers = catalogue.findByAction(action);
        if (controllers.isEmpty()) {
            return Response.respond("doesn't look like that's an action");
        }

        if (controllers.size() == 1) {
            return Response.respond(String.format("%s is provided by %s", action, controllers.first()));
        } else {
            String list = String.join(", ", controllers);
            return Response.respond(String.format("%s is provided by (in order of precedence): %s", action, list));
        }
    }

    @Action({"info", "help", "man"})
    @HelpText("Display a short message about an action's purpose")
    public Response info(Context ctx, Request request) {
        String controllerName = request.getArgument(0);
        String actionName = request.getArgument(1, Request.DEFAULT_ACTION);

        Controller controller = catalogue.get(controllerName);
        if (controller == null) {
            //lets see if the user really meant to invoke help on an action
            SortedSet<String> reverse = catalogue.findByAction(controllerName);
            if (reverse.isEmpty()) {
                //nope, whatever they are looking for we don't know about it
                return Response.respond(String.format("%s is not a loaded controller", controllerName));
            }

            //aha! looks like we were on to something
            actionName = controllerName;
            controllerName = reverse.first();
            controller = catalogue.get(controllerName);
        }

        String info = controller.getInfo(actionName);
        if (info == null) {
            info = "developer did not provide HelpInfo";
        }
        return Response.respond(String.format("%s %s - %s", controllerName, actionName, info));
    }

}
