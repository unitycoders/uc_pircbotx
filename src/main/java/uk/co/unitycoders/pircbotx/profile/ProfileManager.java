/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.unitycoders.pircbotx.profile;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.pircbotx.User;

import uk.co.unitycoders.pircbotx.data.db.ProfileModel;

/**
 *
 *
 */
public class ProfileManager {

    private ProfileModel model;
    private Map<User, Profile> sessions;

    public ProfileManager(ProfileModel profileModel) {
        this.model = profileModel;
        this.sessions = new HashMap<User, Profile>();
    }

    public void login(User user, String profileName) throws SQLException {
        Profile profile = model.getProfile(profileName);
        sessions.put(user, profile);
    }

    public void logoff(User user) {
        sessions.remove(user);
    }

    public void register(String name) throws SQLException {
        model.createProfile(name);
    }

    public Profile getProfile(User user) {
        return sessions.get(user);
    }

    public boolean hasPermission(User user, String permission) {
        Profile profile = sessions.get(user);
        if (profile == null) {
            return false;
        }

        return profile.hasPermission(permission);
    }

}
