package com.fossgalaxy.bot.core.command;

import com.fossgalaxy.bot.api.command.Response;

/**
 * Created by webpigeon on 10/09/16.
 */
public class StringResponse implements Response {
    private String text;

    public StringResponse(String text) {
        this.text = text;
    }

    public String toString() {
        return text;
    }
}
