/**
 * Copyright © 2012-2015 Unity Coders
 * <p>
 * This file is part of uc_pircbotx.
 * <p>
 * uc_pircbotx is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p>
 * uc_pircbotx is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * uc_pircbotx. If not, see <http://www.gnu.org/licenses/>.
 */
package com.fossgalaxy.pircbotx.backends.irc;

import com.fossgalaxy.pircbotx.commandprocessor.CommandProcessor;
import org.pircbotx.Colors;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Command Processor wrapper class.
 * <p>
 * This class is notified by pircbotx when the bot gets a message. It's sole
 * purpose is to act as an adapter between the command processor and pircbotx.
 */
class CommandListener extends ListenerAdapter {
    private final static Logger LOG = LoggerFactory.getLogger(CommandListener.class);
    private final CommandProcessor processor;
    private final String prefix;

    public CommandListener(CommandProcessor processor, char prefix) {
        this.processor = processor;
        this.prefix = "" + prefix;
    }

    @Override
    public void onMessage(MessageEvent event) throws Exception {
        try {
            String messageText = event.getMessage();

            if (messageText.startsWith(prefix)) {
                List<String> args = extractMessage(messageText.substring(1));

                IRCMessage message = new ChannelMessage(event, args);
                processor.invoke(message);
            } else {
                //check for someone trying to address the bot by name
                Pattern pattern = Pattern.compile("^" + event.getBot().getUserBot().getNick() + ".? (.*)$");
                Matcher matcher = pattern.matcher(messageText);
                if (matcher.matches()) {
                    List<String> args = extractMessage(matcher.group(1));
                    IRCMessage message = new ChannelMessage(event, args);
                    processor.invoke(message);
                }
            }
        } catch (Exception ex) {
            event.respond("error: " + ex.getMessage());
            LOG.error("error processing message", ex);
        }
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        try {
            List<String> messageText = extractMessage(event.getMessage());
            processor.invoke(new UserMessage(event, messageText));
        } catch (Exception ex) {
            event.respond("error:" + ex.getMessage());
            LOG.error("error processing private message", ex);
        }
    }

    private List<String> extractMessage(String raw) {
        String clean = Colors.removeFormattingAndColors(raw);
        return processor.processMessage(clean);
    }
}
