/**
 * Copyright © 2013-2015 Unity Coders
 *
 * This file is part of uc_pircbotx.
 *
 * uc_pircbotx is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * uc_pircbotx is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * uc_pircbotx. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.unitycoders.pircbotx.commandprocessor;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author joseph
 */
public class ChannelMessage extends BasicMessage {
    private final MessageEvent<PircBotX> event;
    
    public ChannelMessage(MessageEvent<PircBotX> event, String message) {
        super(event, message);
        this.event = event;
    }
    
    @Override
    public void sendAction(String action) {
        event.getChannel().send().action(action);
    }

    @Override
    public String getTargetName() {
        return event.getChannel().getName();
    }
    
}
