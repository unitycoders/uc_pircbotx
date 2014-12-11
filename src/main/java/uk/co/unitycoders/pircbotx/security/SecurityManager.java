package uk.co.unitycoders.pircbotx.security;

import org.pircbotx.User;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by webpigeon on 03/12/14.
 */
public class SecurityManager {
    private Map<String, Session> sessions;

    public SecurityManager() {
        this.sessions = new TreeMap<String, Session>();
    }
    
    public void startSession(User user) {
        String sessionKey = generateSessionKey(user);
        Session session = new Session();
        sessions.put(sessionKey, session);
    }
   
    public void endSession(User user){
        String sessionKey = generateSessionKey(user);
        sessions.remove(sessionKey);
    }

    public boolean hasActiveSession(User user) {
        String sessionKey = generateSessionKey(user);
        return sessions.containsKey(sessionKey);
    }

    public Session getSession(User user) {
        String sessionKey = generateSessionKey(user);
        return sessions.get(sessionKey);
    }

    public String generateSessionKey(User user) {
        return user.getNick() + "@" + user.getHostmask();
    }

}
