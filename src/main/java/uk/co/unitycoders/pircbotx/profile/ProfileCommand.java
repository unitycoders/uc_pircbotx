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

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.data.db.DBConnection;
import uk.co.unitycoders.pircbotx.data.db.ProfileModel;

/**
 *
 *
 */
public class ProfileCommand {

	private ProfileManager manager;
	private ProfileModel model;

	public ProfileCommand(ProfileManager manager) throws ClassNotFoundException, SQLException {
		this.manager = manager;
		this.model = DBConnection.getProfileModel();
	}

	@Command("register")
	public void register(MessageEvent<PircBotX> event) throws Exception {
		try {
			manager.register("demo123");
			event.respond("Created new profile for you");
		} catch (SQLException ex) {
			event.respond("error: " + ex);
		}
	}

	@Command("login")
	public void login(MessageEvent<PircBotX> event) throws Exception {
		manager.login(event.getUser(), "demo123");
		event.respond("you are now logged in");
	}

	@Command("logoff")
	public void logoff(MessageEvent<PircBotX> event) throws Exception {
		manager.logoff(event.getUser());
		event.respond("you have been logged out");
	}

	@Command("addperm")
	public void addPerm(MessageEvent<PircBotX> event) throws Exception {
		Profile profile = manager.getProfile(event.getUser());
		if (profile == null) {
			event.respond("you are not logged in.");
			return;
		}

		model.addPerm(profile.getName(), "perm1");
		event.respond("permission 'perm1' added to your profile");
	}

	@Command("rmperm")
	public void removePerm(MessageEvent<PircBotX> event) throws Exception {
		Profile profile = manager.getProfile(event.getUser());
		if (profile == null) {
			event.respond("you are not logged in.");
			return;
		}

		model.removePerm(profile.getName(), "perm1");
		event.respond("permission 'perm1' removed from your profile");
	}

	@Command("hasperm")
	public void hasPerm(MessageEvent<PircBotX> event) throws Exception {
		Profile profile = manager.getProfile(event.getUser());
		if (profile == null) {
			event.respond("you are not logged in.");
			return;
		}

		if (profile.hasPermission("perm1")) {
			event.respond("you have permission 'perm1'");
		} else {
			event.respond("you don't have permission 'perm1'");
		}
	}

}
