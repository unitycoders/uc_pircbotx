package com.fossgalaxy.bot.api.command.chain;

import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Request;

/**
 * A preprocessor rewrites request or adds context to a request.
 *
 * It does this by either modifying the mutable context object or by creating a new request to replace the existing
 * one. Processing of the request can also be aborted by throwing an exception. The ability to abort requests is
 * provided to assist in the creation of Rate Limiting and Authorisation preprocessors.
 */
@FunctionalInterface
public interface Preprocessor {

    static Preprocessor identity() { return (c, r) -> r; }

    Request process(Context context, Request request);

    default Preprocessor andThen(Preprocessor after) { return (c, r) -> after.process(c, process(c, r)); }

    default Preprocessor compose(Preprocessor before) { return (c, r) -> process(c, before.process(c, r)); }
}
