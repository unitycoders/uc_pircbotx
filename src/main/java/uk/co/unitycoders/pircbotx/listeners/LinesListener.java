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
package uk.co.unitycoders.pircbotx.listeners;

import java.util.ArrayList;
import java.util.List;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import uk.co.unitycoders.pircbotx.data.db.DBConnection;
import uk.co.unitycoders.pircbotx.data.db.LineModel;

/**
 * A {@link ListenerAdapter} which keeps a log of all the lines said in a
 * channel.
 *
 * @author Bruce Cowan
 */
public class LinesListener extends ListenerAdapter<PircBotX>
{
	private static LinesListener singleton;
        private LineModel model;

	private LinesListener(LineModel model)
	{
            this.model = model;
	}

	/**
	 * Gets the {@link LinesListener} singleton.
	 * @return the {@link LinesListener} singleton
	 */
	public static LinesListener getLinesListener()
	{
            if (singleton == null)
                try{
                    singleton = new LinesListener(DBConnection.getLineModel());
                }catch(Exception ex){
                    ex.printStackTrace();
                }

            return singleton;
	}

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception
	{
            model.storeLine(event.getMessage());
	}

	/**
	 * Gets the list of lines.
	 * @return the list of lines
	 */
	public List<String> getLines()
	{
            return model.getAllLines();
	}
}
