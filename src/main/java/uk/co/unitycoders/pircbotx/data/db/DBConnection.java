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
import java.sql.SQLException;

/**
 * Java SQLite backed database driver
 */
public class DBConnection
{
	private static Connection instance;

	// utility class = private constructor
	private DBConnection()
	{

	}

	/**
	 * Gets the {@link Connection} singleton.
	 *
	 * @return the connection singleton
	 * @throws ClassNotFoundException if the class can't be located
	 * @throws SQLException if there was a database error
	 */
	public static Connection getInstance() throws ClassNotFoundException, SQLException
	{
		if (instance == null)
		{
			Class.forName("org.sqlite.JDBC");
			instance = DriverManager.getConnection("jdbc:sqlite:bot.db");
		}

		return instance;
	}

	/**
	 * Gets a {@link LineModel}.
	 *
	 * @return a new {@link LineModel}
	 * @throws ClassNotFoundException if the class can't be located
	 * @throws SQLException if there was a database error
	 */
	public static LineModel getLineModel() throws ClassNotFoundException, SQLException
	{
		return new LineModel(getInstance());
	}

	/**
	 * Gets a {@link LartModel}.
	 *
	 * @return a new {@link LartModel}
	 * @throws ClassNotFoundException if the class can't be located
	 * @throws SQLException if there was a database error
	 */
	public static LartModel getLartModel() throws ClassNotFoundException, SQLException
	{
		return new LartModel(getInstance());
	}

<<<<<<< HEAD
	public static KarmaModel getKarmaModel() throws ClassNotFoundException, SQLException
	{
		return new KarmaModel(getInstance());
	}
=======
        public static ProfileModel getProfileModel() throws ClassNotFoundException, SQLException {
            return new ProfileModel(getInstance());
        }
>>>>>>> start of session management
}
