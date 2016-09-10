package com.fossgalaxy.bot.core.command;

import com.fossgalaxy.bot.api.command.Catalogue;
import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Controller;
import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.bot.api.command.chain.Preprocessor;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * Attempt to insert missing default actions.
 *
 * If the request has module set and it exists but doesn't contain the mentioned action, it's possible the user meant
 * to execute the default action with arguments. We can detect this and so we should attempt to "fix" their request for
 * them.
 */
public class InsertMissingDefaultPreprocessor implements Preprocessor {
    private final Catalogue catalogue;

    @Inject
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

        //we need to patch the arguments to fix the request
        List<String> args = new ArrayList<>(request.getArguments());
        args.add(name);

        return new PatchedRequest(request, request.getController(), PatchedRequest.DEFAULT_ACTION, args);
    }




}
