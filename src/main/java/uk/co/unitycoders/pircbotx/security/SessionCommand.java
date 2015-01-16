/**
 * Copyright © 2014-2015 Unity Coders
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
package uk.co.unitycoders.pircbotx.security;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;

/**
 * Created by webpigeon on 03/12/14.
 */
public class SessionCommand {
    // TODO replace this with a database/net lookup
    private final static String MAGIC_WORDS = "LlamaLlamaDuck";
    private SecurityManager securityManager;

    public SessionCommand(SecurityManager manager) {
        this.securityManager = manager;
    }

    @Command
    public void onDefault(Message event) {
        boolean active = securityManager.hasActiveSession(event.getUser());
        if (active) {
            event.respond("You are logged in");
        } else {
            event.respond("You are not logged in");
        }
    }

    @Command("test")
    @Secured
    public void onTest(Message event) {
        event.respond("You are logged in (hopefully)");
    }

    @Command("login")
    public void onLogin(Message event) {
        String[] args = event.getMessage().split(" ");
        String password = args[2];

        if (password.equals(MAGIC_WORDS)) {
            securityManager.startSession(event.getUser());
            System.out.println("Started session for " + securityManager.generateSessionKey(event.getUser()));
            event.respond("Started session");
        }
    }

    @Command("logoff")
    public void onLogoff(Message event) {
        securityManager.endSession(event.getUser());
        event.respond("Ended session");
    }

}
