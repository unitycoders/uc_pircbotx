/**
 * Copyright © 2012-2013 Unity Coders
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
package com.fossgalaxy.pircbotx.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class LineModel {

	private final Logger logger = LoggerFactory.getLogger(LineModel.class);
	private final Connection conn;
	private final PreparedStatement createLine;
	private final PreparedStatement readLines;
	private final PreparedStatement randomLine;

	/**
	 * Creates a new LineModel.
	 *
	 * @param conn the database connection
	 * @throws SQLException if there was a database error
	 */
	public LineModel(Connection conn) throws SQLException {
		this.conn = conn;
		buildTable();
		createLine = conn.prepareStatement("INSERT INTO lines VALUES(?)");
		readLines = conn.prepareStatement("SELECT * FROM lines");
		randomLine = conn.prepareStatement("SELECT * FROM lines ORDER BY RANDOM() LIMIT 1");
	}

	private void buildTable() throws SQLException {
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS lines (line TEXT)");
	}

	/**
	 * Store a line in the database.
	 *
	 * @param line the line to store
	 * @return if the storing was successful
	 */
	public boolean storeLine(String line) {
		try {
			createLine.clearParameters();
			createLine.setString(1, line);
			return createLine.execute();
		} catch (SQLException ex) {
			logger.error("Database error", ex);
			return false;
		}
	}

	/**
	 * Get a random line from the database.
	 *
	 * @return the random line
	 */
	public String getRandomLine() {
		try {
			ResultSet rs = randomLine.executeQuery();
			return rs.getString(1);
		} catch (SQLException ex) {
			logger.error("Database error", ex);
			return "";
		}
	}

	/**
	 * Get a {@link List} of all lines in the database.
	 *
	 * @return a list of all lines
	 */
	public List<String> getAllLines() {
		List<String> lines = new ArrayList<String>();
		try {
			ResultSet rs = readLines.executeQuery();
			while (rs.next()) {
				lines.add(rs.getString(1));
			}
			rs.close();
			return lines;

		} catch (SQLException ex) {
			logger.error("Database error", ex);
			return null;
		}
	}

}
