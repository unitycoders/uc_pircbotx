package com.fossgalaxy.bot.utils.session;

/**
 * Sessions provide per-user non-persistent storage.
 *
 * Commands can use sessions to store user specific information across requests, it will be exposed as part of the
 * context if the SessionPreprocessor is enabled.
 */
public interface SessionModel {

    Session create(String key);
    void destroy(String key);

    Session get(String key);

}
