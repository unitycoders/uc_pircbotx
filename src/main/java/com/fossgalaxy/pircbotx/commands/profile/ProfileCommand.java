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
package com.fossgalaxy.pircbotx.commands.profile;

import com.fossgalaxy.pircbotx.commandprocessor.Command;
import com.fossgalaxy.pircbotx.modules.ModuleException;
import com.google.inject.Inject;
import org.pircbotx.hooks.events.MessageEvent;

import java.sql.SQLException;

/**
 *
 *
 */
public class ProfileCommand {

    private final ProfileManager manager;
    private final ProfileModel model;

    @Inject
    public ProfileCommand(ProfileManager manager, ProfileModel model) throws ClassNotFoundException, SQLException {
        this.manager = manager;
        this.model = model;
    }

    @Command("register")
    public void register(MessageEvent event) throws ModuleException {
        try {
            manager.register("demo123");
            event.respond("Created new profile for you");
        } catch (SQLException ex) {
            throw new ModuleException(ex);
        }
    }

    @Command("login")
    public void login(MessageEvent event) throws ModuleException {
        try {
            manager.login(event.getUser(), "demo123");
            event.respond("you are now logged in");
        } catch (SQLException ex) {
            throw new ModuleException(ex);
        }
    }

    @Command("logoff")
    public void logoff(MessageEvent event) throws ModuleException {
        manager.logoff(event.getUser());
        event.respond("you have been logged out");
    }

    @Command("addperm")
    public void addPerm(MessageEvent event) throws ModuleException {
        Profile profile = manager.getProfile(event.getUser());
        if (profile == null) {
            event.respond("you are not logged in.");
            return;
        }

        model.addPerm(profile.getName(), "perm1");
        event.respond("permission 'perm1' added to your profile");
    }

    @Command("rmperm")
    public void removePerm(MessageEvent event) throws ModuleException {
        Profile profile = manager.getProfile(event.getUser());
        if (profile == null) {
            event.respond("you are not logged in.");
            return;
        }

        model.removePerm(profile.getName(), "perm1");
        event.respond("permission 'perm1' removed from your profile");
    }

    @Command("hasperm")
    public void hasPerm(MessageEvent event) throws ModuleException {
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
