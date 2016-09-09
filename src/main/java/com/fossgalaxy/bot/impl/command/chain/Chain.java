package com.fossgalaxy.bot.impl.command.chain;

import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.bot.api.command.Response;
import com.fossgalaxy.bot.api.command.chain.Postprocessor;
import com.fossgalaxy.bot.api.command.chain.Preprocessor;

import java.util.Objects;

/**
 * Simple utility class for creating chains of pre and post processors.
 *
 * This class should not be considered part of the public API.
 */
public class Chain {
    private Preprocessor pre;
    private Postprocessor post;

    public Chain() {
        this(Preprocessor.identity(), Postprocessor.identity());
    }

    public Chain(Preprocessor pre, Postprocessor post){
        this.pre = Objects.requireNonNull(pre);
        this.post = Objects.requireNonNull(post);
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

    public Request preprocess(Context context, Request request) {
        assert context != null;
        assert request != null;
        return pre.process(context, request);
    }

    public Response postprocess(Context context, Response response) {
        assert context != null;
        assert response != null;
        return post.process(context, response);
    }

}
