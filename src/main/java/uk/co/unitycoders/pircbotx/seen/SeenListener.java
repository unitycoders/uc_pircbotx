package uk.co.unitycoders.pircbotx.seen;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by webpigeon on 07/04/14.
 */
public class SeenListener extends ListenerAdapter<PircBotX> {
    private final Map<String, Long> lastSeen;

    public SeenListener() {
        this.lastSeen = new TreeMap<String, Long>();
    }

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        lastSeen.put(event.getUser().getNick(), event.getTimestamp());
    }

    @Override
    public void onJoin(JoinEvent<PircBotX> event) throws Exception {
        lastSeen.put(event.getUser().getNick(), event.getTimestamp());
    }

    public Long getLastSeen(String nick) {
        return lastSeen.get(nick);
    }
}
