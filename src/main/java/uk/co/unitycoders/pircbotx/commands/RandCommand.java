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

import java.sql.SQLException;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.data.db.DBConnection;
import uk.co.unitycoders.pircbotx.data.db.LineModel;

/**
 * Keeps a log of all the lines said, and randomly speaks one.
 *
 * @author Bruce Cowan
 */
public class RandCommand
{
	private LineModel lines;

	public RandCommand() throws Exception
	{
		lines = DBConnection.getLineModel();
	}

	@Command
	public void onRandom(MessageEvent<PircBotX> event) throws Exception
	{
		try
		{
			String line = lines.getRandomLine();
			event.respond(line);
		} catch (SQLException ex)
		{
			event.respond("Failed to get random line: " + ex.getMessage());
		}
	}
}
