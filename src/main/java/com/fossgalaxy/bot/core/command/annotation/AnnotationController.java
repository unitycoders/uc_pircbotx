package com.fossgalaxy.bot.core.command.annotation;

import com.fossgalaxy.bot.api.command.*;
import com.fossgalaxy.pircbotx.commandprocessor.HelpText;
import com.sun.istack.internal.logging.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The controller counterpart to an annotation module.
 *
 * This class allows to you annotate methods which have the required signature to have the bot use them as actions.
 * The expected signature is: Response methodName(Context, Request).
 */
public abstract class AnnotationController implements Controller {
    private final Logger LOG = Logger.getLogger(AnnotationController.class);
    private final Map<String, Method> methods;
    private final Map<String, String> info;

    public AnnotationController(){
        this.methods = new HashMap<>();
        this.info = new HashMap<>();
    }

    protected void process() {
        for (Method method : getClass().getDeclaredMethods()) {
            Action action = method.getAnnotation(Action.class);
            if (action != null) {
                String[] names = action.value();
                HelpText infoAnnotation = method.getAnnotation(HelpText.class);

                //register the name(s) for this action
                for (String name : names) {
                    methods.put(name, method);
                    if (infoAnnotation != null) {
                        info.put(name, infoAnnotation.value());
                    }
                }
            }
        }
    }

    @Override
    public Response execute(Context user, Request request) {

        Method method = methods.get(request.getAction());
        if (method == null) {
            throw new MissingRequestException(request, request.getController(), request.getAction());
        }

        try {
            return (Response)method.invoke(this, user, request);
        } catch (IllegalAccessException e) {
            LOG.warning("error attempting to access method", e);
            throw new ControllerException(request, "error attempting to access requested method");
        } catch (InvocationTargetException e) {
            LOG.warning("got exception from annotation action", e);
            throw new ControllerException(request, "annotation action throw exception: "+e.getCause());
        } catch (ClassCastException e) {
            LOG.warning("annotation action caused class cast exception", e);
            throw new ControllerException(request, "an action should return a response");
        }
    }

    @Override
    public Collection<String> getActions() {
        return methods.keySet();
    }

    @Override
    public String getInfo(String actionName) {
        return info.get(actionName);
    }
}
