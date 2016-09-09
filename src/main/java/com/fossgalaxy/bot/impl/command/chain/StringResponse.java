package com.fossgalaxy.bot.impl.command.chain;

import com.fossgalaxy.bot.api.command.Response;

/**
 * Created by webpigeon on 10/09/16.
 */
public class StringResponse implements Response {
    private final String body;

    public StringResponse(String body) {
        this.body = body;
    }

    public String toString() {
        return body;
    }
}
