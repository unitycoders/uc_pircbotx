package com.fossgalaxy.bot.utils.commands;

import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.bot.api.command.Response;
import com.fossgalaxy.bot.core.command.annotation.Action;
import com.fossgalaxy.bot.core.command.annotation.AnnotationController;
import com.fossgalaxy.pircbotx.commandprocessor.Command;

/**
 * Testing the possibility of adding question answering commands to the bot.
 */
public class QuestionController extends AnnotationController {

    public QuestionController() {
        this.process();
    }

    @Action({"who"})
    public Response whoQuestion(Context ctx, Request request) {
        return Response.respond("No idea, maybe it was me?");
    }

    @Action({"what"})
    public Response whatQuestion(Context ctx, Request request) {
        return Response.respond("No idea, I can't answer 'what' questions");
    }

    @Action({"when"})
    public Response whenQuestion(Context ctx, Request request) {
        return Response.respond("No idea, I can't answer 'when' questions");
    }

    @Action({"where"})
    public Response whereQuestion(Context ctx, Request request) {
        return Response.respond("under the sofa?");
    }

    @Action({"why"})
    public Response whyQuestion(Context ctx, Request request) {
        return Response.respond("Because reasons.");
    }

    @Action({"how"})
    public Response howQuestion(Context ctx, Request request) {
        String q = String.join("+", request.getArguments());
        return Response.respond(String.format("not sure, ask google? %s%s", "http://lmgtfy.com/?q=", q));
    }

    @Action({"are", "is"})
    public Response booleanQuestion(Context ctx, Request request) {
        boolean even = request.getArguments().size() % 2 == 0;
        return Response.respond(String.format("no idea, %s?", even?"yes":"no"));
    }
}
