package com.fossgalaxy.pircbotx.commands.script;

import com.fossgalaxy.pircbotx.commandprocessor.CommandNotFoundException;
import com.fossgalaxy.pircbotx.commandprocessor.CommandProcessor;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.modules.Module;
import com.fossgalaxy.pircbotx.modules.ModuleException;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

/**
 * Created by webpigeon on 31/07/16.
 */
public class ScriptModule implements Module {
    private static final Logger LOG = LoggerFactory.getLogger(ScriptModule.class);
    private static final String RELOAD_COMMAND = "reload";
    private static final String SCRIPT_ENGINE = "nashorn";

    private final String name;
    private final Set<String> actions;
    private final String filename;

    private String helpText;
    private Bindings moduleHelp;

    private ScriptEngine engine;
    private Invocable invocable;
    private CommandProcessor commandProcessor;

    public ScriptModule(String name, String filename) throws ScriptException {
        this.name = name;
        this.actions = new HashSet<>();
        this.filename = filename;

        this.engine = buildScript();
        this.invocable = (Invocable) engine;
    }

    private ScriptEngine buildScript() {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByName(SCRIPT_ENGINE);

        return engine;
    }

    @Inject
    public void onBind(CommandProcessor cp) {
        this.commandProcessor = cp;
    }


    public void init() {
        loadScript(filename);
    }

    void loadScript(String filename) {
        //TODO ensure that actions are kept up to date with the action list.

        try (
                InputStream is = ScriptModule.class.getClassLoader().getResourceAsStream(filename);
                Reader r = new InputStreamReader(is);
        ) {
            engine.eval(r);

            moduleHelp = (Bindings)engine.get("helpText");

            helpText = ((Bindings)engine.get("metadata")).get("help").toString(); //XXX deal with npe

            Set<String> previousActions = new HashSet<>(actions);

            if (commandProcessor != null) {
                actions.clear();
                actions.add(RELOAD_COMMAND);
                Bindings actionsObject = (Bindings) engine.get("actions");
                if (actionsObject != null) {
                    for (String action : actionsObject.keySet()) {
                        commandProcessor.addReverseLookup(getName(), action);
                        actions.add(action);
                    }
                }

                previousActions.removeAll(actions);
                for (String action : previousActions) {
                    commandProcessor.rmReverseLookup(getName(), action);
                }
            }
        } catch (IOException e) {
            System.err.println("error: " + e);
            LOG.error("could not init script", e);
        } catch (ScriptException e) {
            LOG.warn("could not init script", e);
        }
    }

    public void addCommand(String name, String script) throws ScriptException {
        assert !actions.contains(name);
        actions.add(name);
        engine.eval(script);
    }

    @Override
    public void fire(Message message) throws ModuleException, CommandNotFoundException {
        String actionName = message.getArgument(Module.COMMAND_ARG, Module.DEFAULT_COMMAND);

        if (RELOAD_COMMAND.equals(actionName)) {
            loadScript(filename);
            message.respond("Your plugin was reloaded");
            return;
        }

        try {
            Object retVal = invocable.invokeFunction(actionName, message);
            message.respond(retVal.toString());
        } catch (ScriptException e) {
            LOG.warn("error running script", e);
            throw new ModuleException(e);
        } catch (NoSuchMethodException e) {
            LOG.warn("could not find method", e);
            throw new CommandNotFoundException(getName(), actionName);
        }
    }

    @Override
    public boolean isValidAction(String action) {
        return actions.contains(action);
    }

    @Override
    public String[] getRequiredPermissions(String action) {
        return new String[0];
    }

    @Override
    public Collection<String> getActions() {
        return Collections.unmodifiableCollection(actions);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getHelp(String command) {
        if (moduleHelp == null) {
            return null;
        }

        if (RELOAD_COMMAND.equals(command)) {
            return "reload this module from disk";
        }

        Object o = moduleHelp.get(command);
        if (o == null) {
            return null;
        }
        return o.toString();
    }

    @Override
    public String getModuleHelp() {
        return helpText;
    }

    @Override
    public String[] getArgumentsFor(String action) {
        //TODO expose this via the script
        return new String[0];
    }
}
