package com.fossgalaxy.bot.core.command.compat;

import com.fossgalaxy.bot.api.command.*;
import com.fossgalaxy.pircbotx.commandprocessor.CommandNotFoundException;
import com.fossgalaxy.pircbotx.modules.Module;
import com.fossgalaxy.pircbotx.modules.ModuleException;

import java.util.Collection;

/**
 * Wrapper to make uc_pircbot modules work with the Command API.
 */
public class ModuleController implements Controller {
    private Module module;

    public ModuleController(Module module) {
        this.module = module;
    }

    @Override
    public Response execute(Context user, Request request) {
        try {
            MessageRequest message = MessageRequest.convert(user, request);
            module.fire(message);
            return Response.respond(message.getResponse());
        } catch(CommandNotFoundException ex) {
            throw new MissingRequestException(request, request.getController(), request.getAction());
        } catch (ModuleException ex) {
            throw new ControllerException(request, " "+ex.getCause());
        }
    }

    @Override
    public Collection<String> getActions() {
        return module.getActions();
    }

    @Override
    public String getInfo(String actionName) {
        return module.getHelp(actionName);
    }
}
