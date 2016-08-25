package com.fossgalaxy.pircbotx.backends;

/**
 * Created by webpigeon on 25/08/16.
 */
public class BackendException extends Exception {

    public BackendException(String text) {
        super(text);
    }

    public BackendException(Throwable cause) {
        super(cause);
    }
}
