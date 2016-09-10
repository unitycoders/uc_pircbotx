package com.fossgalaxy.bot.utils.session;

import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.bot.api.command.RequestException;
import com.fossgalaxy.bot.api.command.Response;
import com.fossgalaxy.bot.core.command.annotation.Action;
import com.fossgalaxy.bot.core.command.annotation.AnnotationController;

/**
 * Commands related to password-based authentication.
 */
public class AuthController extends AnnotationController {
    private SessionModel sessions;

    public AuthController(SessionModel model) {
        this.sessions = model;
    }

    @Action("login")
    public Response doLogin(Context ctx, Request request) {
        String sessionKey = (String)ctx.get(Context.SESSION_KEY);
        if (sessionKey == null) {
            throw new RequestException(request, "Your provider does not expose session keys, you cannot login");
        }

        //TODO check username and password against database
        String username = request.getArgument(0);
        String password = request.getArgument(1);

        //TODO replace debug hack, password is username + 42
        if (!password.equals(username+"42")) {
            throw new RequestException(request, "Your username or password was incorrect");
        }

        Session session = sessions.create(sessionKey);
        session.grant("loggedIn");

        return Response.success();
    }



}
