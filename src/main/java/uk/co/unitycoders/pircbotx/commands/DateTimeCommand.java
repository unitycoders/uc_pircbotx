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
import org.pircbotx.hooks.events.MessageEvent;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;

/**
 * Outputs the formatted date or time.
 *
 * @author Bruce Cowan
 */
public class DateTimeCommand
{
	private final DateFormat dformat;
	private final DateFormat tformat;

	public DateTimeCommand()
	{
		this.dformat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		this.tformat = DateFormat.getTimeInstance(DateFormat.LONG);
	}

	@Command
	public void onMessage(MessageEvent<PircBotX> event) throws Exception
	{
		String msg = event.getMessage();
		Date date = new Date();

                String[] args = msg.split(" ");
                String keyword = args[0].substring(1);


                String resp = "INVALID";
                if(keyword.equals("date"))
                {
                    resp = dformat.format(date);
                }

                if(keyword.equals("time"))
                {
                    resp = tformat.format(date);
                }

                String fmt = String.format("The current %s is %s", keyword, resp);
                event.respond(fmt);
	}
}
