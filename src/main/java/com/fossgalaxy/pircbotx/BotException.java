package com.fossgalaxy.pircbotx;

/**
 * Created by webpigeon on 25/08/16.
 */
public class BotException extends RuntimeException {

    public BotException(String message) {
        super(message);
    }

    public BotException(Throwable cause) {
        super(cause);
    }
}
