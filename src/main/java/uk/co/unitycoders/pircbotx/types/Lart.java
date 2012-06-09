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
package uk.co.unitycoders.pircbotx.types;

/**
 * A lart database
 * 
 * @author Bruce Cowan
 */
public class Lart
{
	private final String channel;
	private final String nick;
	private final String pattern;

	/**
	 * Creates a new lart.
	 * @param channel the channel the lart belongs in
	 * @param user the creator of the lart
	 * @param pattern the pattern of the lart, requires a $who part
	 */
	public Lart(String channel, String nick, String pattern)
	{
		this.channel = channel;
		this.nick = nick;
		this.pattern = pattern;
	}

	/**
	 * Gets the channel the lart belongs to.
	 * @return the channel the lart belongs to
	 */
	public String getChannel()
	{
		return this.channel;
	}

	/**
	 * Gets the author of the lart.
	 * @return the author of the lart
	 */
	public String getNick()
	{
		return this.nick;
	}

	/**
	 * Gets the pattern of the lart.
	 * @return the pattern of the lart
	 */
	public String getPattern()
	{
		return this.pattern;
	}
}
