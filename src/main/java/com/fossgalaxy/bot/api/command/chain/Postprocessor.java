package com.fossgalaxy.bot.api.command.chain;

import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Response;


/**
 * A postprocessor rewrites a response or adds context to a request.
 *
 * It does this by either modifying the mutable context object or by creating a new response to replace the existing
 * one. Processing of the response can also be aborted by throwing an exception. The ability to abort responses is
 * provided to assist in the creation of Rate Limiting and Authorisation post-processors.
 */
@FunctionalInterface
public interface Postprocessor {

    static Postprocessor identity() {
        return (c, r) -> r;
    }

    Response process(Context context, Response response);

    default Postprocessor andThen(Postprocessor after) {
        return (c, r) -> after.process(c, process(c, r));
    }

    default Postprocessor compose(Postprocessor before) {
        return (c, r) -> process(c, before.process(c, r));
    }
}
