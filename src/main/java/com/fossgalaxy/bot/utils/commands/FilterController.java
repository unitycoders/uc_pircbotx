package com.fossgalaxy.bot.utils.commands;

import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Controller;
import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.bot.api.command.Response;
import com.fossgalaxy.bot.core.command.annotation.Action;
import com.fossgalaxy.bot.core.command.annotation.AnnotationController;

import java.util.List;

/**
 * Created by webpigeon on 10/09/16.
 */
public class FilterController extends AnnotationController {

    public FilterController() {
        process();
    }

    @Action("upper")
    public Response toUpper(Context ctx, Request request) {
        String str = String.join(" ", request.getArguments());
        return Response.respond(str.toUpperCase());
    }


    @Action("join")
    public Response doJoin(Context ctx, Request request) {
        List<String> args = request.getArguments();

        String str = String.join(args.get(0), args.subList(1, args.size()));
        return Response.respond(str);
    }

    @Action("quote")
    public Response doQuote(Context ctx, Request request) {
        String str = String.join(" ", request.getArguments());
        return Response.respond("\""+str+"\"");
    }

    @Action("echo")
    public Response doEcho(Context ctx, Request request) {
        List<String> args = request.getArguments();
        String str = String.join(" ", args);

        return Response.respond(str);
    }
}
