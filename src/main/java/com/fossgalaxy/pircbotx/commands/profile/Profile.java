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

import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 */
public class Profile {

    private final String name;
    private final Set<String> permissions;

    public Profile(String name) {
        this.name = name;
        this.permissions = new HashSet<>();
    }

    public void addPermission(String perm) {
        this.permissions.add(perm);
    }

    public void removePermission(String perm) {
        this.permissions.remove(perm);
    }

    public boolean hasPermission(String perm) {
        return this.permissions.contains(perm);
    }

    public String getName() {
        return name;
    }

}
