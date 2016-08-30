package com.fossgalaxy.pircbotx.backends.console;

import com.fossgalaxy.pircbotx.backends.AbstractMessage;
import org.pircbotx.User;

import java.io.PrintStream;
import java.util.List;

public class InteractiveMessage extends AbstractMessage {
    private final PrintStream out;

    public InteractiveMessage(List<String> args, PrintStream out) {
        super(args);
        this.out = out;
    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public String getTargetName() {
        return "consoleUser";
    }

    @Override
    public void respond(String message) {
        out.println(String.format("bot: %s", message));
    }

    @Override
    public void sendAction(String action) {
        out.println(String.format("* %s *", action));
    }

}
