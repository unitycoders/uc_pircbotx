package com.fossgalaxy.pircbotx.commandprocessor;

import com.google.inject.AbstractModule;

/**
 * Created by webpigeon on 14/08/16.
 */
public class CommandModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CommandProcessor.class);
    }

}
