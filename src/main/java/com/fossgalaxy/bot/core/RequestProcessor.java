package com.fossgalaxy.bot.core;

import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Controller;
import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.bot.api.command.Response;
import com.fossgalaxy.bot.api.command.chain.Postprocessor;
import com.fossgalaxy.bot.api.command.chain.Preprocessor;
import com.fossgalaxy.bot.impl.command.chain.Catalogue;

import java.util.Objects;

/**
 * Class responsible for delegating requests to the appropriate place and dealing with the responses.
 */
public class RequestProcessor {
    private final Catalogue catalogue;
    private Preprocessor pre;
    private Postprocessor post;

    public RequestProcessor(Catalogue catalogue) {
        this(Preprocessor.identity(), Postprocessor.identity(), catalogue);
    }

    public RequestProcessor(Preprocessor pre, Postprocessor post, Catalogue catalogue){
        this.pre = Objects.requireNonNull(pre);
        this.post = Objects.requireNonNull(post);
        this.catalogue = Objects.requireNonNull(catalogue);
    }

    public void addLast(Preprocessor step) {
        pre = pre.andThen(step);
    }

    public void addFirst(Preprocessor step) {
        pre = pre.compose(step);
    }

    public void addFirst(Postprocessor step) {
        post = post.compose(step);
    }

    public void addLast(Postprocessor step) {
        post = post.andThen(step);
    }


    /**
     * Process a request from a user.
     *
     * @param context
     * @param request
     * @return
     */
    public Response process(Context context, Request request) {
        Request processedRequest = pre.process(context, request);

        Controller controller = catalogue.get(request.getController());
        if (controller == null) {
            return Response.respond("That controller does not exist");
        }

        Response rawResponse = controller.execute(context, processedRequest);

        return post.process(context, rawResponse);
    }

}
