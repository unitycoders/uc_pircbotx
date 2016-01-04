/**
 * Copyright © 2013-2015 Unity Coders
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FactoidModel {
    private final Logger logger = LoggerFactory.getLogger(FactoidModel.class);
    private final Connection connection;

    private final PreparedStatement createStmt;
    private final PreparedStatement readStmt;
    private final PreparedStatement updateStmt;
    private final PreparedStatement deleteStmt;

    public FactoidModel(Connection connection) throws SQLException {
        this.connection = connection;
        buildTable();

        createStmt = connection.prepareStatement("INSERT INTO factoid VALUES (?,?);");
        readStmt = connection.prepareStatement("SELECT body FROM factoid WHERE name=?;");
        updateStmt = connection.prepareStatement("UPDATE factoid SET body=? WHERE name=?");
        deleteStmt = connection.prepareStatement("DELETE FROM factoid WHERE name=?");
    }

    private void buildTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS factoid (name TEXT PRIMARY KEY, body TEXT)");
    }

    public boolean addFactoid(String factoid, String text) {
        try {
            createStmt.clearParameters();
            createStmt.setString(1, factoid);
            createStmt.setString(2, text);
            return createStmt.executeUpdate() == 1;
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return false;
        }
    }

    public String getFactoid(String factoid) {
        try {
            readStmt.clearParameters();
            readStmt.setString(1, factoid);

            ResultSet rs = readStmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return null;
        }
    }

    public boolean editFactoid(String factoid, String text) {
        try {
            updateStmt.clearParameters();
            updateStmt.setString(1, text);
            updateStmt.setString(2, factoid);
            return updateStmt.executeUpdate() == 1;
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return false;
        }
    }

    public boolean deleteFactoid(String factoid) {
        try {
            deleteStmt.clearParameters();
            deleteStmt.setString(1, factoid);
            return deleteStmt.executeUpdate() == 1;
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return false;
        }
    }

}
