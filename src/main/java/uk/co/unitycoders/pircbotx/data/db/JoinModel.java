/**
 * Copyright © 2015 Bruce Cowan <bruce@bcowan.me.uk>
 *
 * This file is part of uc_PircBotX.
 *
 * uc_PircBotX is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * uc_PircBotX is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * uc_PircBotX. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.co.unitycoders.pircbotx.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

public class JoinModel {

    private final Connection conn;
    private final PreparedStatement newJoin;
    private final PreparedStatement getJoin;
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
        getJoin = conn.prepareStatement("SELECT joins FROM joins WHERE nick = ?");
        incrementJoin = conn.prepareStatement("UPDATE joins SET joins = joins + 1 WHERE nick = ?");
        readJoins = conn.prepareStatement("SELECT * FROM joins");
    }

    private void buildTable() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS joins (nick TEXT PRIMARY KEY, joins INTEGER DEFAULT 1)");
    }

    public int getKarma(String nick) {
        try {
            getJoin.clearParameters();
            getJoin.setString(1, nick);
            getJoin.execute();

            ResultSet rs = getJoin.getResultSet();
            return rs.getInt(1);
        } catch (SQLException ex) {
            // Probably not in the database yet, so return 0
            return 0;
        }
    }

    private void newJoin(String nick) throws SQLException {
        newJoin.clearParameters();
        newJoin.setString(1, nick);
        newJoin.execute();
    }

    public int incrementJoin(String nick) throws SQLException {
        incrementJoin.clearParameters();
        incrementJoin.setString(1, nick);
        int rows = incrementJoin.executeUpdate();

        if (rows == 0) {
            newJoin(nick);
        }

        return getKarma(nick);
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
            ex.printStackTrace();
        }
        return joins;
    }

}
