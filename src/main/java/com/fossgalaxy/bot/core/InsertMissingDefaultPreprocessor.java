package com.fossgalaxy.bot.core;

import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Controller;
import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.bot.api.command.chain.Preprocessor;
import com.fossgalaxy.bot.impl.command.chain.Catalogue;

/**
 * Attempt to insert missing default actions.
 *
 * If the request has module set and it exists but doesn't contain the mentioned action, it's possible the user meant
 * to execute the default action with arguments. We can detect this and so we should attempt to "fix" their request for
 * them.
 */
public class InsertMissingDefaultPreprocessor implements Preprocessor {
    private final Catalogue catalogue;

    public InsertMissingDefaultPreprocessor(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    @Override
    public Request process(Context context, Request request) {
        String name = request.getAction();

        Controller controller = catalogue.get(request.getController());
        if (controller == null || controller.hasAction(name)) {
            return request;
        }

        return new PatchedRequest(request.getController(), name, request.getArguments());
    }




}
