package com.fossgalaxy.bot.utils.session;

import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.bot.api.command.chain.Preprocessor;
import com.sun.istack.internal.logging.Logger;

/**
 * Authentication preprocessor.
 *
 * This injects authentication and session data into the context of a request.
 */
public class SessionPreprocessor implements Preprocessor {
    private final Logger LOG = Logger.getLogger(SessionPreprocessor.class);
    private final SessionModel model;

    public SessionPreprocessor(SessionModel model) {
        this.model = model;
    }

    @Override
    public Request process(Context context, Request request) {
        String sessionKey = (String)context.get(Context.SESSION_KEY);

        //if the provider did not give us a user key, we have no way of knowing who this user is...
        if (sessionKey == null) {
            LOG.warning("Request did not provide a userKey, authorisation is not possible.");
            return request;
        }

        //restore the user's session
        Session session = model.get(sessionKey);
        context.put(Session.CONTEXT_NAME, session);

        //we're not altering the request, so we just return the existing one
        return request;
    }

}
