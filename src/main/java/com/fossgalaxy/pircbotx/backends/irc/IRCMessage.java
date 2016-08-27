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

import com.fossgalaxy.pircbotx.backends.AbstractMessage;
import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.List;

/**
 * Abstract message adapter for uc_pircbotx.
 * <p>
 * An abstract version of the Bot Message interface which deals with some of
 * the common features which we can deal with for the child classes.
 */
abstract class IRCMessage extends AbstractMessage {
    private final GenericMessageEvent event;

    public IRCMessage(GenericMessageEvent message, List<String> args) {
        super(args);
        this.event = message;
    }

    @Override
    public void respond(String response) {
        event.respond(response);
    }

    @Override
    public User getUser() {
        return event.getUser();
    }

}
