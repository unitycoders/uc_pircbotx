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
