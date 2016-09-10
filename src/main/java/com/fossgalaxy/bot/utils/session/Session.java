package com.fossgalaxy.bot.utils.session;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by webpigeon on 10/09/16.
 */
public class Session implements Serializable {
    public static final String CONTEXT_NAME = "session.permissions";

    private String username;
    private Set<String> permissions;

    public Session() {
        this.permissions = new HashSet<>();
    }

    public void grant(String permission) {
        permissions.remove(permission);
    }

    public void revoke(String permission) {
        permissions.remove(permission);
    }

    public boolean hasPermission(String name) {
        return permissions.contains(name);
    }
}
