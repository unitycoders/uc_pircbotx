package com.fossgalaxy.pircbotx.commands.script;

import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.modules.Module;
import com.fossgalaxy.pircbotx.modules.ModuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by webpigeon on 31/07/16.
 */
public class ScriptModule implements Module {
    private static final Logger LOG = LoggerFactory.getLogger(ScriptModule.class);
    private static final String RELOAD_COMMAND = "reload";
    private static final String SCRIPT_ENGINE = "nashhorn";

    private final String name;
    private final List<String> actions;
    private final String filename;

    private ScriptEngine engine;
    private Invocable invocable;

    public ScriptModule(String name, String filename) throws ScriptException {
        this.name = name;
        this.actions = new ArrayList<>();
        this.filename = filename;

        this.engine = buildScript();
        this.invocable = (Invocable) engine;

        loadScript(filename);
    }

    private ScriptEngine buildScript() {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByName(SCRIPT_ENGINE);

        return engine;
    }

    private void loadScript(String filename) {
        //TODO ensure that actions are kept up to date with the action list.

        try (
                InputStream is = ScriptModule.class.getClassLoader().getResourceAsStream(filename);
                Reader r = new InputStreamReader(is);
        ) {
            engine.eval(r);
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
    public void fire(Message message) throws ModuleException {
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
            throw new ModuleException(e);
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
        //TODO expose this via the script
        return null;
    }

    @Override
    public String getModuleHelp() {
        //TODO expose this via the script
        return null;
    }

    @Override
    public String[] getArgumentsFor(String action) {
        //TODO expose this via the script
        return new String[0];
    }
}
