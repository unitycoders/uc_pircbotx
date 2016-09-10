package com.fossgalaxy.bot.core.command;

import com.fossgalaxy.bot.api.command.*;
import com.fossgalaxy.bot.api.command.chain.Preprocessor;
import com.fossgalaxy.pircbotx.backends.console.InteractivePrompt;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

/**
 * Attempt to insert missing controllers.
 *
 * If the request has controller set, but that controller doesn't exist it might be an action - see if we can infer the
 * controller based on the action. This allows the command framework to emulate actions which are not bound to
 * controllers but makes the command less obvious. This is the same approach used by Supybot.
 *
 * If the action is ambiguous, either fail or select the first match (depending on operator preference).
 */
public class InferControllerPreprocessor implements Preprocessor {
    private final Logger LOG = LoggerFactory.getLogger(InferControllerPreprocessor.class);
    private final boolean failOnAmbiguous;
    private final Catalogue catalogue;

    @Inject
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
            LOG.debug("{} is a valid controller, skipping inference", name);
            return request;
        }

        //we need to do a reverse lookup to find matching controllers
        SortedSet<String> matchedControllers = catalogue.findByAction(name);
        if (matchedControllers.isEmpty()) {
            LOG.debug("{} is not provided by any controller, skipping inference", name);
            return request;
        }

        if (failOnAmbiguous) {
            int numMatches = matchedControllers.size();
            if (numMatches > 1) {
                LOG.info("{} is provided by one than one controller ({}), aborting", name, matchedControllers);
                throw new RequestException(request, "the action "+name+" appears in the modules: "+matchedControllers+" as there was more than one match, I have aborted processing.");
            }
        }

        List<String> args = new ArrayList<>(request.getArguments());
        if (request.getAction() != null) {
            args.add(0, request.getAction());
        }

        //either the action was ambiguous and we don't care or only one controller matched.
        return new PatchedRequest(request, matchedControllers.first(), name, args);
    }




}
