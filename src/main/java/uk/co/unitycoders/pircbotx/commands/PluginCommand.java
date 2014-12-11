package uk.co.unitycoders.pircbotx.commands;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.security.Secured;

import java.util.Collection;

/**
 * Created by webpigeon on 03/12/14.
 */
public class PluginCommand {
    private CommandProcessor processor;

    public PluginCommand(CommandProcessor processor) {
        this.processor = processor;
    }

    @Command
    public void onDefault(Message message) {
        Collection<String> modules = processor.getModules();
        message.respond("Loaded modules are: " + modules);
    }

    // WARNING: this represents a massive security risk - ONLY USE FOR DEBUGGING
    @Command("load")
    @Secured
    public void onLoad(Message message) {
        String[] args = message.getMessage().split(" ");

        String name = args[2];
        String className = args[3];

        try {
            Class<?> clazz = Class.forName(className);
            Object module = clazz.newInstance();
            processor.register(name, module);
            message.respond("Class has been loaded");
        } catch (IllegalAccessException ex) {
            message.respond("No default no-arg constructor for class");
        } catch (ClassNotFoundException ex) {
            message.respond("Class could not be found");
        } catch (InstantiationException ex) {
            message.respond("Class could not be instantiated");
        }
    }

    // WARNING: this represents a massive security risk - ONLY USE FOR DEBUGGING
    @Command("alias")
    @Secured
    public void onAlias(Message message) {
        String[] args = message.getMessage().split(" ");

        try {
            String alias = args[2];
            String commandKeyword = args[3];
            processor.alias(alias, commandKeyword);
            message.respond("Plugin is now aliased");
        } catch (RuntimeException ex) {
            message.respond("error: "+ex.getLocalizedMessage());
        }
    }

    // WARNING: this represents a massive security risk - ONLY USE FOR DEBUGGING
    @Command("unload")
    @Secured
    public void onUnload(Message message) {
        String[] args = message.getMessage().split(" ");

        String name = args[2];
        processor.remove(name);
        message.respond("Plugin has been unloaded");
    }
}
