/**
 * Copyright © 2014-2015 Unity Coders
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
package uk.co.unitycoders.pircbotx;

import java.util.Map;

import uk.co.unitycoders.pircbotx.modules.ModuleConfig;

public class LocalConfiguration {

	public String nick;
	public char trigger;

	//connection
	public String host;
	public int port;
	public Boolean ssl;
	public String[] channels;

	//authentication
	public boolean sasl;
	public String username;
	public String password;

	//Stuff to autoload
	public String[] middleware;
	public Map<String, ModuleConfig> modules;
	public Map<String,String> aliases;
}
