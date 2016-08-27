/**
 * Copyright © 2012-2013 Unity Coders
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
package com.fossgalaxy.pircbotx.commands.lart;

/**
 * Represents a lart (Luser Attitude Readjustment Tool), or an insult
 *
 * @author Bruce Cowan
 */
public class Lart {

    private final int id;
    private final String channel;
    private final String nick;
    private final String pattern;

    /**
     * Creates a new lart.
     *
     * @param id the id of the lart in the database
     * @param channel the channel the lart belongs in
     * @param nick the creator of the lart
     * @param pattern the pattern of the lart, requires a $who part
     */
    public Lart(int id, String channel, String nick, String pattern) {
        this.id = id;
        this.channel = channel;
        this.nick = nick;
        this.pattern = pattern;
    }

    /**
     * Gets the ID of the lart.
     *
     * @return the ID of the lart
     */
    public int getID() {
        return this.id;
    }

    /**
     * Gets the channel the lart belongs to.
     *
     * @return the channel the lart belongs to
     */
    public String getChannel() {
        return this.channel;
    }

    /**
     * Gets the author of the lart.
     *
     * @return the author of the lart
     */
    public String getNick() {
        return this.nick;
    }

    /**
     * Gets the pattern of the lart.
     *
     * @return the pattern of the lart
     */
    public String getPattern() {
        return this.pattern;
    }
}
