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
        String[] args = event.getMessage().split(" ");

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
