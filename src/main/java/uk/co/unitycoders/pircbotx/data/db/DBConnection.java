/**
 * Copyright © 2012-2015 Unity Coders
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
package uk.co.unitycoders.pircbotx.data.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Java SQLite backed database driver
 */
public class DBConnection {

	private static Connection instance;

	// utility class = private constructor
	private DBConnection() {

	}

	/**
	 * Gets the {@link Connection} singleton.
	 *
	 * @return the connection singleton
	 * @throws ClassNotFoundException if the class can't be located
	 * @throws SQLException if there was a database error
	 */
	public static Connection getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null) {
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
	public static LineModel getLineModel() throws ClassNotFoundException, SQLException {
		return new LineModel(getInstance());
	}

	/**
	 * Gets a {@link LartModel}.
	 *
	 * @return a new {@link LartModel}
	 * @throws ClassNotFoundException if the class can't be located
	 * @throws SQLException if there was a database error
	 */
	public static LartModel getLartModel() throws ClassNotFoundException, SQLException {
		return new LartModel(getInstance());
	}

	/**
	 * Gets a {@link KarmaModel}
	 * @return a new {@link KarmaModel}
	 * @throws ClassNotFoundException if the class can't be located
	 * @throws SQLException if there was a database error
	 */
	public static KarmaModel getKarmaModel() throws ClassNotFoundException, SQLException {
		return new KarmaModel(getInstance());
	}

	/**
	 * Gets a {@link ProfileModel}.
	 *
	 * @return a new {@link ProfileModel}
	 * @throws ClassNotFoundException if the class can't be located
	 * @throws SQLException if there was a database error
	 */
	public static ProfileModel getProfileModel() throws ClassNotFoundException, SQLException {
		return new ProfileModel(getInstance());
	}

	/**
	 * Gets a {@link FactoidModel}.
	 *
	 * @return a new {@link FactoidModel}
	 * @throws ClassNotFoundException if the class can't be located
	 * @throws SQLException if there was a database error
	 */
	public static FactoidModel getFactoidModel() throws ClassNotFoundException, SQLException {
		return new FactoidModel(getInstance());
	}

	/**
	 * Gets a {@link JoinModel}.
	 *
	 * @return a new {@link JoinModel}
	 * @throws ClassNotFoundException if the class can't be located
	 * @throws SQLException if there was a database error
	 */
	public static JoinModel getJoinModel() throws ClassNotFoundException, SQLException {
		return new JoinModel(getInstance());
	}
}
