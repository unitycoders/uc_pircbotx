/**
 * Copyright © 2015 Unity Coders
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
package uk.co.unitycoders.pircbotx.types;

/**
 * Represents a "thing" and an integer score associated with it
 * 
 * @author Joseph Walton-Rivers
 */
public class Karma {
	private String target;
	private int karma;
	
	/**
	 * Creates a new "thing" with no score
	 * @param target the "thing"
	 */
	public Karma(String target) {
		this(target, 0);
	}
	
	/**
	 * Creates a new "thing" with a score
	 * @param target the "thing"
	 * @param karma the score
	 */
	public Karma(String target, int karma) {
		this.target = target;
		this.karma = karma;
	}
	
	/**
	 * Gets the name of the "thing" represented
	 * @return the thing
	 */
	public String getTarget() {
		return target;
	}
	
	/**
	 * Gets the score associated to the "thing"
	 * @return the score
	 */
	public int getKarma() {
		return karma;
	}
	
	public String toString() {
		return target+" ("+karma+")";
	}

}
