/**
 * Copyright © 2012 Bruce Cowan <bruce@bcowan.me.uk>
 * Copyright © 2012 Joseph Walton-Rivers <webpigeon@unitycoders.co.uk>
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
package uk.co.unitycoders.pircbotx;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * Static Utility class to deal with configuration files.
 * 
 * This class can be used to read/write configuration files as required by the
 * bot.
 */
public class ConfigurationManager {
    public static final String CONF_FILE_NAME = "uc_pircbotx.properties";
    
    // Default configuration values
    public static final String DEFAULT_IRC_HOST = "irc.freenode.net";
    public static final String DEFAULT_BOT_NAME = "uc_pircbotx";
    public static final String DEFAULT_PORT = "6697";
    public static final String DEFAULT_SSL = "true";
    public static final String DEFAULT_CHANS = "#unity-coders";
    
    public static Configuration loadConfig() throws IOException {
        Reader reader = new FileReader(CONF_FILE_NAME);
        Properties properties = new Properties();
        properties.load(reader);
        
        Configuration config = new Configuration();
        
        // Get connection infomation
        config.host = properties.getProperty("host", DEFAULT_IRC_HOST);
        config.port = Integer.parseInt(properties.getProperty("port", DEFAULT_PORT));
        config.nick = properties.getProperty("nick", DEFAULT_BOT_NAME);
        config.ssl = Boolean.parseBoolean(properties.getProperty("ssl", DEFAULT_SSL));
        
        //work out what channels to connect to
        String channelStr = properties.getProperty("channels",DEFAULT_CHANS);
        config.channels = channelStr.split(",");
        
        return config;
    }
    
}
