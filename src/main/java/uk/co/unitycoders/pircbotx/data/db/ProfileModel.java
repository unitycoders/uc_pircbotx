/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.unitycoders.pircbotx.data.db;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import uk.co.unitycoders.pircbotx.profile.Profile;

/**
 *
 *
 */
public class ProfileModel {
    private final Connection conn;
    private final PreparedStatement createProfile;
    private final PreparedStatement createPerm;
    private final PreparedStatement deletePerm;
    private final PreparedStatement getProfile;
    private final PreparedStatement getPerms;

    public ProfileModel(Connection conn) throws SQLException {
        this.conn = conn;
        buildTable();
        createProfile = conn.prepareStatement("INSERT INTO profiles VALUES(?)");
        createPerm = conn.prepareStatement("INSERT INTO permissions VALUES (?,?)");
        deletePerm = conn.prepareStatement("DELETE FROM permissions WHERE user=? AND name=?");
        getPerms = conn.prepareStatement("SELECT * FROM permissions WHERE user=?");
        getProfile = conn.prepareStatement("SELECT * FROM profiles WHERE user=?");
    }

    private void buildTable() throws SQLException
    {
	Statement stmt = conn.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS permissions (user string, name string)");
	stmt.executeUpdate("CREATE TABLE IF NOT EXISTS profiles (user string)");
    }

    public void createProfile(String name) throws SQLException
    {
        createProfile.clearParameters();
        createProfile.setString(1, name);
        createProfile.executeUpdate();
    }

    public void addPerm(String user, String perm) throws SQLException
    {
        createPerm.clearParameters();
        createPerm.setString(1, user);
        createPerm.setString(2, perm);
        createPerm.execute();
    }

    public void removePerm(String user, String perm) throws SQLException
    {
        deletePerm.clearParameters();
        deletePerm.setString(1, user);
        deletePerm.setString(2, perm);
        deletePerm.executeUpdate();
    }

    public String[] getPerms(String user) throws SQLException
    {
        Set<String> perms = new HashSet<String>();

        getPerms.clearParameters();
        getPerms.setString(1, user);
        ResultSet rs = getPerms.executeQuery();

        while(rs.next()){
            perms.add(rs.getString(2));
        }

        rs.close();

        return perms.toArray(new String[perms.size()]);
    }

    public Profile getProfile(String profileName) throws SQLException {
        getProfile.clearParameters();
        getProfile.setString(1, profileName);
        ResultSet rs = getProfile.executeQuery();
        rs.next();

        Profile profile = new Profile(rs.getString(1));
        return profile;
    }
}
