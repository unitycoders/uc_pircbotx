package com.fossgalaxy.bot.api.command;

import com.fossgalaxy.bot.impl.command.chain.StringResponse;

/**
 * Created by webpigeon on 09/09/16.
 */
public interface Response {

    /**
     * Provide a standardised way of indicating a successful operation.
     *
     * @return A response object which corresponds to successful completion of a task.
     */
    static Response success() {
        return new StringResponse("okay");
    }

    /**
     * Provide a way of exposing a short message as a response.
     *
     * @return a response which will display a short response to the user.
     */
    static Response respond(String message) {
        return new StringResponse(message);
    }
}
