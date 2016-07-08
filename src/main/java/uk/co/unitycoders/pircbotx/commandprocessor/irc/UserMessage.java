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
package uk.co.unitycoders.pircbotx.commandprocessor.irc;

import java.util.List;

import org.pircbotx.hooks.events.PrivateMessageEvent;

class UserMessage extends BasicMessage {
	private final PrivateMessageEvent event;

	public UserMessage(PrivateMessageEvent event, List<String> args) {
		super(event, args);
		this.event = event;
	}

	@Override
	public void sendAction(String action) {
		event.getUser().send().action(action);
	}

	@Override
	public String getTargetName() {
		return event.getUser().getNick();
	}



}
