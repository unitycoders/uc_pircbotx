/**
 * Copyright © 2013-2015 Unity Coders
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

import org.pircbotx.User;
import org.pircbotx.hooks.events.PrivateMessageEvent;

import java.util.List;

class UserMessage extends IRCMessage {
    private final PrivateMessageEvent event;

    public UserMessage(PrivateMessageEvent event, List<String> args) {
        super(event, args);
        this.event = event;
    }

    @Override
    public void sendAction(String action) {
        User user = event.getUser();
        if (user == null) {
            return;
        }

        user.send().action(action);
    }

    @Override
    public String getTargetName() {
        User user = event.getUser();
        if (user == null) {
            return null;
        }

        return user.getNick();
    }

}
