package com.fossgalaxy.bot.core;

import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Controller;
import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.bot.api.command.chain.Preprocessor;
import com.fossgalaxy.bot.impl.command.chain.Catalogue;

import java.util.List;

/**
 * Attempt to insert missing controllers.
 *
 * If the request has controller set, but that controller doesn't exist it might be an action - see if we can infer the
 * controller based on the action. This allows the command framework to emulate actions which are not bound to
 * controllers but makes the commands less obvious. This is the same approach used by Supybot.
 *
 * If the action is ambiguous, either fail or select the first match (depending on operator preference).
 */
public class InferControllerPreprocessor implements Preprocessor {
    private final boolean failOnAmbiguous;
    private final Catalogue catalogue;

    public InferControllerPreprocessor(Catalogue catalogue) {
        this(catalogue, true); //fail on ambiguous is "least surprise" and safer
    }

    public InferControllerPreprocessor(Catalogue catalogue, boolean failOnAmbiguous) {
        this.catalogue = catalogue;
        this.failOnAmbiguous = failOnAmbiguous;
    }

    @Override
    public Request process(Context context, Request request) {
        String name = request.getController();

        // if the controller exists, no problem
        Controller controller = catalogue.get(name);
        if (controller != null) {
            return request;
        }

        //we need to do a reverse lookup to find matching controllers
        List<String> matchedControllers = catalogue.findByAction(name);
        if (matchedControllers.isEmpty()) {
            return request;
        }

        if (failOnAmbiguous) {
            int numMatches = matchedControllers.size();
            if (numMatches > 1) {
                throw new RuntimeException("the action "+name+" appears in the modules: "+matchedControllers+" as there was more than one match, I have aborted processing.");
            }
        }

        //either the action was ambiguous and we don't care or only one controller matched.
        return new PatchedRequest(matchedControllers.get(0), name, request.getArguments());
    }




}
