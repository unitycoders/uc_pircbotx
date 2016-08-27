/**
 * Copyright © 2012-2015 Unity Coders
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
package com.fossgalaxy.pircbotx.commands.karma;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class KarmaModel {

    private final Logger logger = LoggerFactory.getLogger(KarmaModel.class);
    private final Connection conn;

    private final PreparedStatement newKarma;
    private final PreparedStatement getKarma;
    private final PreparedStatement topKarma;
    private final PreparedStatement incrementKarma;
    private final PreparedStatement decrementKarma;

    @Inject
    public KarmaModel(Connection conn) throws SQLException {
        this.conn = conn;
        buildTable();
        newKarma = conn.prepareStatement("INSERT INTO karma (target) VALUES (?)");
        topKarma = conn.prepareStatement("SELECT target, karma FROM karma ORDER BY karma DESC LIMIT ?");
        getKarma = conn.prepareStatement("SELECT karma FROM karma WHERE target = ?");
        incrementKarma = conn.prepareStatement("UPDATE karma SET karma = karma + 1 WHERE target = ?");
        decrementKarma = conn.prepareStatement("UPDATE karma SET karma = karma - 1 WHERE target = ?");
    }

    private void buildTable() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS karma (target TEXT PRIMARY KEY, karma INTEGER DEFAULT 1)");
        stmt.close();
    }

    public Collection<Karma> getTopKarma(int limit) {
        ArrayList<Karma> karmaList = new ArrayList<>();

        try {
            topKarma.clearParameters();
            topKarma.setInt(1, limit);
            ResultSet rs = topKarma.executeQuery();

            while (rs.next()) {
                Karma karmaEntry = new Karma(rs.getString(1), rs.getInt(2));
                karmaList.add(karmaEntry);
            }

            rs.close();
            return karmaList;
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return null;
        }
    }

    public int getKarma(String target) {
        try {
            getKarma.clearParameters();
            getKarma.setString(1, target);
            getKarma.execute();

            ResultSet rs = getKarma.getResultSet();
            return rs.getInt(1);
        } catch (SQLException ex) {
            // Probably not in the database yet, so return 0
            logger.warn("Database error", ex);
            return 0;
        }
    }

    private boolean newKarma(String target) throws SQLException {
        newKarma.clearParameters();
        newKarma.setString(1, target);
        return newKarma.execute();
    }

    public int incrementKarma(String target) {
        try {
            incrementKarma.clearParameters();
            incrementKarma.setString(1, target);
            int rows = incrementKarma.executeUpdate();

            if (rows == 0) {
                newKarma(target);
            }
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return 0;
        }
        return getKarma(target);
    }

    public int decrementKarma(String target) {
        try {
            decrementKarma.clearParameters();
            decrementKarma.setString(1, target);
            decrementKarma.execute();
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return 0;
        }
        return getKarma(target);
    }
}
