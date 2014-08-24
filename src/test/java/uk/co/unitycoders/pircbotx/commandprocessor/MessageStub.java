package uk.co.unitycoders.pircbotx.commandprocessor;

import org.pircbotx.PircBotX;
import org.pircbotx.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by webpigeon on 24/08/14.
 */
public class MessageStub implements Message {
    private final String message;

    public MessageStub(String message) {
        this.message = message;
    }

    @Override
    public PircBotX getBot() {
        return null;
    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getTargetName() {
        return null;
    }

    @Override
    public void respond(String message) {
        throw new NotImplementedException();
    }

    @Override
    public void sendAction(String action) {
        throw new NotImplementedException();
    }
}
