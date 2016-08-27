package com.fossgalaxy.pircbotx.modules;

/**
 * Created by webpigeon on 25/08/16.
 */
public class ModuleException extends Exception {

    public ModuleException(String message) {
        super(message);
    }

    public ModuleException(Throwable cause) {
        super(cause);
    }
}
