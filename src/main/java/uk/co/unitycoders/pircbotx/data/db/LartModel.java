/**
 * Copyright © 2012-2014 Unity Coders
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
import java.util.ArrayList;
import java.util.List;

import org.pircbotx.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.unitycoders.pircbotx.types.Lart;

/**
 * Stores a load of larts.
 *
 * @author Joseph Walton-Rivers
 * @author Bruce Cowan
 */
public class LartModel {

    private final Logger logger = LoggerFactory.getLogger(LartModel.class);
    private final Connection conn;
    private final PreparedStatement createLart;
    private final PreparedStatement readLarts;
    private final PreparedStatement deleteLart;
    private final PreparedStatement specificLart;
    private final PreparedStatement randomLart;
    private final PreparedStatement lastId;
    private final PreparedStatement alterLart;

    /**
     * Creates a new LartModel.
     *
     * @param conn the database connection
     * @throws SQLException if there was a database error
     */
    public LartModel(Connection conn) throws SQLException {
        this.conn = conn;

        buildTable();
        this.createLart = conn.prepareStatement("INSERT INTO larts (channel, nick, pattern) VALUES (?, ?, ?)");
        this.readLarts = conn.prepareStatement("SELECT * FROM larts");
        this.deleteLart = conn.prepareStatement("DELETE FROM larts WHERE id = ?");
        this.specificLart = conn.prepareStatement("SELECT * FROM larts WHERE id = ?");
        this.randomLart = conn.prepareStatement("SELECT * FROM larts ORDER BY RANDOM() LIMIT 1");
        this.lastId = conn.prepareStatement("SELECT last_insert_rowid();");
        this.alterLart = conn.prepareStatement("UPDATE larts SET channel = ?, nick = ?, pattern = ? WHERE id = ?");
    }

    private void buildTable() throws SQLException {
        Statement stmt = this.conn.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS larts"
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT, channel TEXT, nick TEXT, pattern TEXT)");
    }

    /**
     * Stores a lart in the database.
     *
     * @param target the channel where the lart belongs to, or name if in PM
     * @param user the user who created the lart
     * @param pattern the pattern of the lart
     * @return the ID of the newly-created lart
     * @throws IllegalArgumentException if no $who section is given
     */
    public int storeLart(String target, User user, String pattern) throws IllegalArgumentException {
        if (!pattern.contains("$who")) {
            throw new IllegalArgumentException("No $who section found");
        }

        try {
            createLart.clearParameters();
            createLart.setString(1, target);
            createLart.setString(2, user.getNick());
            createLart.setString(3, pattern);
            createLart.execute();

            // Do this manually because getGeneratedKeys() is broken
            lastId.clearParameters();
            lastId.execute();
            ResultSet rs = lastId.getResultSet();
            return rs.getInt(1);
        } catch (SQLException ex) {
           logger.error("Database error", ex);
           return 0;
        }
    }

    /**
     * Deletes a lart from the database.
     *
     * @param id the ID of the lart to delete
     * @return <code>true</code> if successful, <code>false</code> if not
     */
    public boolean deleteLart(int id) {
        try {
            deleteLart.clearParameters();
            deleteLart.setInt(1, id);
            return (deleteLart.executeUpdate() > 0);
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return false;
        }
    }

    private Lart buildLart(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String channel = rs.getString(2);
        String nick = rs.getString(3);
        String pattern = rs.getString(4);
        return new Lart(id, channel, nick, pattern);
    }

    /**
     * Gets a {@link Lart} from the database.
     *
     * @param id the ID of the lart to get
     * @return the lart
     */
    public Lart getLart(int id) {
        try {
            specificLart.clearParameters();
            specificLart.setInt(1, id);
            specificLart.execute();

            ResultSet rs = specificLart.getResultSet();
            return buildLart(rs);
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return null;
        }
    }

    /**
     * Gets a random {@link Lart} from the database.
     *
     * @return a random lart
     */
    public Lart getRandomLart() {
        try {
            ResultSet rs = randomLart.executeQuery();
            return buildLart(rs);
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return null;
        }
    }

    /**
     * Gets a {@link List} of all {@link Lart}s in the database.
     *
     * @return a list of all the larts
     */
    public List<Lart> getAllLarts() {
        List<Lart> larts = new ArrayList<Lart>();

        try {
            ResultSet rs = readLarts.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String channel = rs.getString(2);
                String nick = rs.getString(3);
                String pattern = rs.getString(4);

                Lart lart = new Lart(id, channel, nick, pattern);
                larts.add(lart);
            }
            rs.close();
        } catch (SQLException ex) {
            logger.error("Database error", ex);
        }

        return larts;
    }

    public boolean alterLart(int id, String target, User user, String pattern) throws IllegalArgumentException {
        if (!pattern.contains("$who")) {
            throw new IllegalArgumentException("No $who section found");
        }

        try {
            alterLart.clearParameters();
            alterLart.setString(1, target);
            alterLart.setString(2, user.getNick());
            alterLart.setString(3, pattern);
            alterLart.setInt(4, id);
            return alterLart.execute();
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return false;
        }
    }
}
