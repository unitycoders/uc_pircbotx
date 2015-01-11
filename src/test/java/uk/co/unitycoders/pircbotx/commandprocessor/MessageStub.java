package uk.co.unitycoders.pircbotx.commandprocessor;

import java.util.List;

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
    }

    @Override
    public void sendAction(String action) {
    }

	@Override
	public String getArgument(int id, String defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setArguments(List<String> args) {
		// TODO Auto-generated method stub
		
	}
}
