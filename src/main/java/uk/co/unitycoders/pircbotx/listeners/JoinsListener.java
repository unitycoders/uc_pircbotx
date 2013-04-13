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

import java.util.Map;
import java.util.TreeMap;

import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;

public class JoinsListener extends ListenerAdapter<PircBotX>
{
	private static JoinsListener instance;
	private Map<User, Integer> joins;

	private JoinsListener()
	{
		this.joins = new TreeMap<User, Integer>();
	}

	/**
	 * Gets the {@link JoinsListener} singleton.
	 * 
	 * @return the JoinsListener singleton
	 */
	public static JoinsListener getInstance()
	{
		if (instance == null)
			instance = new JoinsListener();

		return instance;
	}

	@Override
	public void onJoin(JoinEvent<PircBotX> event) throws Exception
	{
		Integer joins = this.joins.get(event.getUser());
		if (joins == null)
			joins = 0;
		this.joins.put(event.getUser(), joins + 1);
	}

	/**
	 * Gets the {@link Map} of joins.
	 * 
	 * @return the map of joins
	 */
	public Map<User, Integer> getJoins()
	{
		return joins;
	}
}
