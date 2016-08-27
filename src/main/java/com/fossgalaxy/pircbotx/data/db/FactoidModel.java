/**
 * Copyright © 2013-2015 Unity Coders
 * <p>
 * This file is part of uc_pircbotx.
 * <p>
 * uc_pircbotx is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p>
 * uc_pircbotx is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * uc_pircbotx. If not, see <http://www.gnu.org/licenses/>.
 */
package com.fossgalaxy.pircbotx.data.db;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FactoidModel {
    private static final String CREATE_SQL = "INSERT INTO factoid VALUES (?,?);";
    private static final String UPDATE_SQL = "UPDATE factoid SET body=? WHERE name=?;";
    private static final String DELETE_SQL = "DELETE FROM factoid WHERE name=?;";
    private static final String READ_SQL = "SELECT body FROM factoid WHERE name=?;";
    private static final String SEARCH_SQL = "SELECT name,body FROM factoid WHERE name LIKE ? LIMIT 10";
    private static final String RANDOM_SQL = "SELECT name,body FROM factoid ORDER BY RANDOM() LIMIT 1;";

    private final Logger logger = LoggerFactory.getLogger(FactoidModel.class);
    private final Connection connection;

    private PreparedStatement createStmt;
    private PreparedStatement searchStmt;
    private PreparedStatement readStmt;
    private PreparedStatement randStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;

    @Inject
    public FactoidModel(Connection connection) throws SQLException {
        this.connection = connection;
        buildTable();

        createStmt = connection.prepareStatement(CREATE_SQL);
        searchStmt = connection.prepareStatement(SEARCH_SQL);
        readStmt = connection.prepareStatement(READ_SQL);
        updateStmt = connection.prepareStatement(UPDATE_SQL);
        deleteStmt = connection.prepareStatement(DELETE_SQL);
        randStmt = connection.prepareStatement(RANDOM_SQL);
    }

    private void buildTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS factoid (name TEXT PRIMARY KEY, body TEXT)");
        stmt.close();
    }

    public boolean addFactoid(String factoid, String text) {
        if (createStmt == null) {
            try {
                createStmt = connection.prepareStatement(CREATE_SQL);
            } catch (SQLException ex) {
                logger.warn("database error", ex);
                createStmt = null;
                return false;
            }
        }

        try {
            createStmt.clearParameters();
            createStmt.setString(1, factoid);
            createStmt.setString(2, text);
            return createStmt.executeUpdate() == 1;
        } catch (SQLException ex) {
            //work around https://github.com/xerial/sqlite-jdbc/issues/74
            killConnection(createStmt);
            createStmt = null;
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

    public List<Factoid> search(String query) {
        try {
            searchStmt.clearParameters();
            searchStmt.setString(1, query);

            ResultSet rs = searchStmt.executeQuery();

            List<Factoid> factoids = new ArrayList<>();

            while (rs.next()) {
                Factoid factoid = new Factoid();
                factoid.name = rs.getString(1);
                factoid.body = rs.getString(2);
                factoids.add(factoid);
            }
            rs.close();

            return factoids;
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return null;
        }
    }

    public Factoid getRandom() {
        try {
            randStmt.clearParameters();
            ResultSet rs = randStmt.executeQuery();

            if (rs.next()) {
                Factoid factoid = new Factoid();

                factoid.name = rs.getString(1);
                factoid.body = rs.getString(2);

                return factoid;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return null;
        }
    }

    public boolean editFactoid(String factoid, String text) {
        if (updateStmt == null) {
            try {
                updateStmt = connection.prepareStatement(UPDATE_SQL);
            } catch (SQLException ex) {
                updateStmt = null;
                logger.warn("database error", ex);
                return false;
            }
        }

        try {
            updateStmt.clearParameters();
            updateStmt.setString(1, text);
            updateStmt.setString(2, factoid);
            return updateStmt.executeUpdate() == 1;
        } catch (SQLException ex) {
            killConnection(updateStmt);
            updateStmt = null;
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

    private void killConnection(PreparedStatement stmt) {
        try {
            stmt.close();
            createStmt.closeOnCompletion();
            searchStmt.closeOnCompletion();
            readStmt.closeOnCompletion();
            updateStmt.closeOnCompletion();
            deleteStmt.closeOnCompletion();
            randStmt.closeOnCompletion();
        } catch (SQLException ex) {
            //it's probably dead
            logger.warn("database had a problem closing statements", ex);
        }
    }
}
