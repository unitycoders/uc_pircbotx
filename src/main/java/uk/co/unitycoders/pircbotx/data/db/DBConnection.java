/**
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
package uk.co.unitycoders.pircbotx.data.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Java SQLite backed database driver
 */
public class DBConnection {
    private static Connection instance;

    //utiltiy class = private constructor
    private DBConnection() {
    }

    public static Connection getInstance() throws Exception{
        if(instance == null){
            Class.forName("org.sqlite.JDBC");
            instance = DriverManager.getConnection("jdbc:sqlite:bot.db");
        }

        return instance;
    }

    public static LineModel getLineModel() throws Exception{
        return new LineModel(getInstance());
    }

    public static LartModel getLartModel() throws Exception {
    	return new LartModel(getInstance());
    }
}
