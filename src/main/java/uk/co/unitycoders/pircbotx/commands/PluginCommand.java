/**
 * Copyright © 2014 Unity Coders
 *
 * This file is part of uc_pircbotx.
 *
 * uc_pircbotx is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * uc_pircbotx is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * uc_pircbotx. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.unitycoders.pircbotx.commands;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.modules.AnnotationModule;
import uk.co.unitycoders.pircbotx.modules.Module;
import uk.co.unitycoders.pircbotx.modules.ModuleUtils;
import uk.co.unitycoders.pircbotx.security.Secured;

import java.util.Collection;

public class PluginCommand extends AnnotationModule {
    private CommandProcessor processor;

    public PluginCommand(CommandProcessor processor) {
    	super("plugin");
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
            Module module = ModuleUtils.loadModule(className);
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
