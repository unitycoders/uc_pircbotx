package com.fossgalaxy.bot.core.command.compat;

import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.pircbotx.backends.AbstractMessage;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Wraps a Command API request as if it was a uc_pircbotx message.
 */
public class MessageRequest extends AbstractMessage {
    private Request request;
    private Context context;
    private List<String> response;

    public MessageRequest(Request request, Context context, List<String> oldArgs) {
        super(oldArgs);
        this.request = request;
        this.context = context;
        this.response = new ArrayList<>();
    }

    public String getResponse() {
        return String.join(", ", response);
    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public String getTargetName() {
        return (String)context.get(Context.MESSAGE_TARGET);
    }

    @Override
    public void respond(String message) {
        response.add(message);
    }

    @Override
    public void sendAction(String action) {
        response.add(action);
    }

    public static MessageRequest convert(Context context, Request request) {
        List<String> args = new ArrayList<>(request.getArguments());
        args.add(0, request.getController());
        args.add(1, request.getAction());
        System.out.println(args);
        return new MessageRequest(request, context, args);
    }
}
