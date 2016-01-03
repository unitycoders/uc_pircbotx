/**
 * Copyright © 2013-2014 Unity Coders
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

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.Gson;

/**
 * Static Utility class to deal with configuration files.
 *
 * This class can be used to read/write configuration files as required by the
 * bot.
 */
public class ConfigurationManager {
    public static final String JSON_FILE_NAME = "uc_pircbotx.json";

    // Default configuration values
    public static final String DEFAULT_IRC_HOST = "irc.freenode.net";
    public static final String DEFAULT_BOT_NAME = "uc_pircbotx";
    public static final String DEFAULT_PORT = "6697";
    public static final String DEFAULT_SSL = "true";
    public static final String DEFAULT_CHANS = "#unity-coders";
    public static final String DEFAULT_TRIGGER = "&";

    private static Reader getFileReader(String file) throws IOException {
        return new FileReader(file);
    }

    public static LocalConfiguration loadConfig(String filePath) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(getFileReader(filePath), LocalConfiguration.class);
    }

    public static LocalConfiguration loadConfig() throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(getFileReader(JSON_FILE_NAME), LocalConfiguration.class);
    }

}
