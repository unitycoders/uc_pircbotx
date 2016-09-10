package com.fossgalaxy.bot.core.command;

import com.fossgalaxy.bot.api.command.Request;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converts a string into a request.
 */
class RequestParser implements Function<String, Request> {
    private final Pattern tokeniser;

    /**
     * Create a new command processor.
     * <p>
     * This will create a new command processor and will initialise the regex
     * pattern the bot will use to match command. It will also create the maps
     * needed to store information about the command.
     */
    @Inject
    public RequestParser() {
        this.tokeniser = Pattern.compile("([^\\s\"']+)|\"([^\"]*)\"|'([^']*)'");
    }

    protected List<String> tokenise(String message) {

        List<String> arguments = new ArrayList<>();

        Matcher matcher = tokeniser.matcher(message);
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                arguments.add(matcher.group(1));
            } else if (matcher.group(2) != null) {
                arguments.add(matcher.group(2));
            } else {
                assert matcher.group(3) != null;
                arguments.add(matcher.group(3));
            }
        }

        return arguments;
    }

    public Request parse(String input) {
        assert input != null : "attempted to generate request from null";
        List<String> tokens = tokenise(input);

        // if there are no tokens, we should give up
        if (tokens.isEmpty()) {
            return null;
        }

        if (tokens.size() == 1) {
            return new SimpleRequest(tokens.get(0), Collections.emptyList());
        } else {
            return new SimpleRequest(tokens.get(0), tokens.get(1), tokens.subList(2, tokens.size()));
        }
    }

    @Override
    public Request apply(String s) {
        return parse(s);
    }
}
