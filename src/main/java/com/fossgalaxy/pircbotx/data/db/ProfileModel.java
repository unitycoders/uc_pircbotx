/**
 * Copyright © 2012-2013 Unity Coders
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

import com.fossgalaxy.pircbotx.profile.Profile;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 */
public class ProfileModel {

    private final Logger logger = LoggerFactory.getLogger(ProfileModel.class);
    private final Connection conn;
    private final PreparedStatement createProfile;
    private final PreparedStatement createPerm;
    private final PreparedStatement deletePerm;
    private final PreparedStatement getProfile;
    private final PreparedStatement getPerms;

    @Inject
    public ProfileModel(Connection conn) throws SQLException {
        this.conn = conn;
        buildTable();
        createProfile = conn.prepareStatement("INSERT INTO profiles VALUES(?)");
        createPerm = conn.prepareStatement("INSERT INTO permissions VALUES (?,?)");
        deletePerm = conn.prepareStatement("DELETE FROM permissions WHERE user=? AND name=?");
        getPerms = conn.prepareStatement("SELECT * FROM permissions WHERE user=?");
        getProfile = conn.prepareStatement("SELECT * FROM profiles WHERE user=?");
    }

    private void buildTable() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS permissions (user TEXT, name TEXT)");
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS profiles (user TEXT)");
        stmt.close();
    }

    public boolean createProfile(String name) {
        try {
            createProfile.clearParameters();
            createProfile.setString(1, name);
            return createProfile.executeUpdate() == 1;
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return false;
        }
    }

    public boolean addPerm(String user, String perm) {
        try {
            createPerm.clearParameters();
            createPerm.setString(1, user);
            createPerm.setString(2, perm);
            return createPerm.execute();
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return false;
        }
    }

    public boolean removePerm(String user, String perm) {
        try {
            deletePerm.clearParameters();
            deletePerm.setString(1, user);
            deletePerm.setString(2, perm);
            return deletePerm.executeUpdate() == 1;
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return false;
        }
    }

    public String[] getPerms(String user) {
        Set<String> perms = new HashSet<>();

        try {
            getPerms.clearParameters();
            getPerms.setString(1, user);
            ResultSet rs = getPerms.executeQuery();

            while (rs.next()) {
                perms.add(rs.getString(2));
            }

            rs.close();

            return perms.toArray(new String[perms.size()]);
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return null;
        }
    }

    public Profile getProfile(String profileName) {
        try {
            getProfile.clearParameters();
            getProfile.setString(1, profileName);
            ResultSet rs = getProfile.executeQuery();
            rs.next();

            Profile profile = new Profile(rs.getString(1));
            return profile;
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            return null;
        }
    }
}
