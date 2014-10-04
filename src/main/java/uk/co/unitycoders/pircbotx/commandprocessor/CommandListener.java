/**
 * Copyright © 2012 Joseph Walton-Rivers <webpigeon@unitycoders.co.uk>
 *
 * This file is part of uc_PircBotX.
 *
 * uc_PircBotX is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * uc_PircBotX is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * uc_PircBotX. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.unitycoders.pircbotx.commandprocessor;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 * Command Processor wrapper class.
 *
 * This class is notified by pircbotx when the bot gets a message. It's sole
 * purpose is to act as an adapter between the command processor and pircbotx.
 */
public class CommandListener extends ListenerAdapter<PircBotX> {

    private final CommandProcessor processor;
    private final String prefix;

    public CommandListener(CommandProcessor processor, char prefix) {
        this.processor = processor;
        this.prefix = ""+prefix;
    }

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        String messageText = event.getMessage();

        if (messageText.startsWith(prefix)) {
            Message message = new ChannelMessage(event, messageText.substring(1));
            processor.invoke(message);
        }
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent<PircBotX> event) throws Exception {
        processor.invoke(new UserMessage(event));
    }
}
