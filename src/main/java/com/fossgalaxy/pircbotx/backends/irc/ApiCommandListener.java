package com.fossgalaxy.pircbotx.backends.irc;

import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Invoker;
import com.fossgalaxy.bot.api.command.Response;
import com.fossgalaxy.bot.core.command.DefaultContext;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PIRCBotX backend for new command API.
 */
public class ApiCommandListener extends ListenerAdapter {

    private final static Logger LOG = LoggerFactory.getLogger(ApiCommandListener.class);
    private final Invoker processor;
    private final String prefix;

    public ApiCommandListener(Invoker processor, char prefix) {
        this.processor = processor;
        this.prefix = "" + prefix;
    }

    @Override
    public void onMessage(MessageEvent event) throws Exception {
        try {
            String messageText = event.getMessage();
            String target = event.getChannel().getName();

            if (messageText.startsWith(prefix)) {
                fire(event, target, messageText.substring(1));
            } else {
                //check for someone trying to address the bot by name
                Pattern pattern = Pattern.compile("^" + event.getBot().getUserBot().getNick() + ".? (.*)$");
                Matcher matcher = pattern.matcher(messageText);
                if (matcher.matches()) {
                    fire(event, target, matcher.group(1));
                }
            }
        } catch (Exception ex) {
            event.respond("error: " + ex.getMessage());
            LOG.error("error processing message", ex);
        }
    }

    protected void fire(GenericMessageEvent event, String target, String message) {
        Context context = new DefaultContext();
        context.put(Context.SESSION_KEY, "pircbotx:irc:"+event.getUser().getHostmask());
        context.put(Context.MESSAGE_TARGET, target);
        context.put(Context.PROTOCOL, "irc");

        Response r = processor.apply(context, message);
        String output = r.toString();
        event.respondWith(output);
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        try {
            fire(event, event.getUser().getNick(), event.getMessage());
        } catch (Exception ex) {
            event.respond("error:" + ex.getMessage());
            LOG.error("error processing private message", ex);
        }
    }

}
