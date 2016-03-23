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
package uk.co.unitycoders.pircbotx.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JoinModel {

	private final Logger logger = LoggerFactory.getLogger(JoinModel.class);
	private final Connection conn;
	private final PreparedStatement newJoin;
	private final PreparedStatement incrementJoin;
	private final PreparedStatement readJoins;

	/**
	 * Creates a new JoinModel.
	 *
	 * @param conn the database connection
	 * @throws SQLException if there was a database error
	 */
	public JoinModel(Connection conn) throws SQLException {
		this.conn = conn;
		buildTable();
		newJoin = conn.prepareStatement("INSERT INTO joins (nick) VALUES (?)");
		incrementJoin = conn.prepareStatement("UPDATE joins SET joins = joins + 1 WHERE nick = ?");
		readJoins = conn.prepareStatement("SELECT * FROM joins");
	}

	private void buildTable() throws SQLException {
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS joins (nick TEXT PRIMARY KEY, joins INTEGER DEFAULT 1)");
	}

	private boolean newJoin(String nick) throws SQLException {
		newJoin.clearParameters();
		newJoin.setString(1, nick);
		return newJoin.execute();
	}

	public boolean incrementJoin(String nick) {
		int rows;
		try {
			incrementJoin.clearParameters();
			incrementJoin.setString(1, nick);
			rows = incrementJoin.executeUpdate();
			if (rows == 0) {
				return newJoin(nick);
			} else {
				return true;
			}
		} catch (SQLException ex) {
			logger.error("Database error", ex);
			return false;
		}
	}

	public Map<String, Integer> getAllJoins() {
		TreeMap<String, Integer> joins = new TreeMap<String, Integer>();
		try {
			ResultSet rs = readJoins.executeQuery();
			while (rs.next()) {
				joins.put(rs.getString(1), rs.getInt(2));
			}
			rs.close();
		} catch (SQLException ex) {
			logger.error("Database error", ex);
		}
		return joins;
	}

}
