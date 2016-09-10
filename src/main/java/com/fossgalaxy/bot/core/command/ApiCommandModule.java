package com.fossgalaxy.bot.core.command;

import com.fossgalaxy.bot.api.command.Catalogue;
import com.fossgalaxy.bot.api.command.Invoker;
import com.google.inject.AbstractModule;

/**
 * Google Gson module for dealing with the command processing pipeline.
 */
public class ApiCommandModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(RequestParser.class).toInstance(new RequestParser());
        bind(RequestProcessor.class);

        bind(Catalogue.class).toInstance(new SimpleCatalogue());
        bind(Invoker.class).to(DefaultInvoker.class);

        //the process of going from a string to a request
        //step 1. convert String -> tokens
        //step 2. convert tokens -> request
        //step 3. invoke request (possibly with filters)
    }
}
