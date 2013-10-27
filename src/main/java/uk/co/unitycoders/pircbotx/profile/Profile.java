/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.unitycoders.pircbotx.profile;

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
        this.permissions = new HashSet<String>();
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
