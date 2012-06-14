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

import java.text.DateFormat;
import java.util.Date;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Outputs the formatted date or time.
 *
 * @author Bruce Cowan
 */
public class DateTimeCommand extends ListenerAdapter<PircBotX>
{
	private final DateFormat dformat;
	private final DateFormat tformat;

	public DateTimeCommand()
	{
		this.dformat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		this.tformat = DateFormat.getTimeInstance(DateFormat.LONG);
	}

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception
	{
		String msg = event.getMessage();
		Date date = new Date();

		if (event.getMessage().startsWith("!date"))
			event.respond("The current date is " + this.dformat.format(date));
		else if (msg.startsWith("!time"))
			event.respond("The current time is " + this.tformat.format(date));
	}
}
