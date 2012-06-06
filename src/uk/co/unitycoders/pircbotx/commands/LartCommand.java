/**
 * Copyright © 2012 Bruce Cowan <bruce@bcowan.me.uk>
 *
 * This file is part of uc_PircBotX.
 *
 * uc_PircBotX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * uc_PircBotX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with uc_PircBotX.  If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.unitycoders.pircbotx.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Insults the 1st argument.
 *
 * @author Bruce Cowan
 */
public class LartCommand extends ListenerAdapter<PircBotX>
{
	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception
	{
		String msg = event.getMessage();

		if (msg.startsWith("!lart"))
		{
			String[] split = msg.split(" ");
			if (split.length == 2)
				insult(event, split[1]);
			else
				insult(event, event.getUser().getNick());
		}
	}

	/**
	 * Insults someone.
	 * @param event the event from {@link #onMessage(MessageEvent)}.
	 * @param nick the nick to insult
	 */
	private void insult(MessageEvent<PircBotX> event, String nick)
	{
		String insult = "slaps " + nick + " with a wet trout";
		event.getBot().sendAction(event.getChannel(), insult);
	}
}
