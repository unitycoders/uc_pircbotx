package com.fossgalaxy.bot.utils.commands;

import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.bot.api.command.RequestException;
import com.fossgalaxy.bot.api.command.Response;
import com.fossgalaxy.bot.core.command.annotation.Action;
import com.fossgalaxy.bot.core.command.annotation.AnnotationController;

import java.util.List;

/**
 * Things in here have no practical use, they're just for fun.
 */
public class JustForFunController extends AnnotationController {

    public JustForFunController() {
        process();
    }

    /**
     * Say sorry if someone is yelling at us.
     *
     * @param ctx
     * @param request
     * @return
     */
    @Action({"silly", "bad"})
    public Response apologise(Context ctx, Request request) {
        return Response.respond("sorry :(");
    }

    /**
     * Say you're welcome if someone tries to thank us
     *
     * @param ctx
     * @param request
     * @return
     */
    @Action({"thanks", "thank"})
    public Response beThanked(Context ctx, Request request) {
        return Response.respond("you're welcome ^.^");
    }

}
