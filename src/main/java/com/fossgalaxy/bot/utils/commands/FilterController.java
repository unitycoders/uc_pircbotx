package com.fossgalaxy.bot.utils.commands;

import com.fossgalaxy.bot.api.command.*;
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

    @Action("lower")
    public Response toLower(Context ctx, Request request) {
        String str = String.join(" ", request.getArguments());
        return Response.respond(str.toLowerCase());
    }

    @Action("extract")
    public Response extract(Context ctx, Request request) {
        List<String> args = request.getArguments();

        try {
            Integer n = Integer.parseInt(request.getArgument(0));
            if (n < 0 || n >= args.size()) {
                throw new RequestException(request, "first argument must be a positive number less than total args");
            }

            return Response.respond(request.getArgument(n));
        } catch (NumberFormatException ex) {
            throw new RequestException(request, "The first argument to extract must be a number");
        }
    }

    @Action("find")
    public Response find(Context ctx, Request request) {
        String needle = request.getArgument(0);

        List<String> args = request.getArguments();
        for (int i=1; i<args.size(); i++) {
            if (args.get(i).contains(needle)) {
                return Response.respond(""+i);
            }
        }

        return Response.respond("-1");
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
