/**
 * Copyright © 2014-2015 Unity Coders
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
package uk.co.unitycoders.pircbotx;

import java.util.Map;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.SASLCapHandler;

import uk.co.unitycoders.pircbotx.commandprocessor.CommandListener;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.co.unitycoders.pircbotx.commandprocessor.RewriteEngine;
import uk.co.unitycoders.pircbotx.commands.*;
import uk.co.unitycoders.pircbotx.listeners.JoinsListener;
import uk.co.unitycoders.pircbotx.listeners.LinesListener;
import uk.co.unitycoders.pircbotx.modules.Module;
import uk.co.unitycoders.pircbotx.modules.ModuleUtils;
import uk.co.unitycoders.pircbotx.security.*;
import uk.co.unitycoders.pircbotx.security.SecurityManager;

import java.util.ServiceLoader;
import java.util.TreeMap;

import javax.net.ssl.SSLSocketFactory;

public class BotRunnable implements Runnable {
    private PircBotX instance;
    private CommandProcessor processor;
    private LocalConfiguration config;

    public BotRunnable(LocalConfiguration config) {
        this.config = config;
    }

    @Override
    public void run() {

        try {
            Configuration.Builder<PircBotX> cb = new Configuration.Builder<PircBotX>();

            //rewrite rules
            RewriteEngine rewrite = new RewriteEngine();
    		rewrite.addRule("^([a-zA-Z0-9]+)\\+\\+$", "karma add $1");
    		rewrite.addRule("^([a-zA-Z0-9]+)--$", "karma remove $1");
    		rewrite.addRule("^\\?([a-zA-Z0-9]+)$", "factoid get $1");
            
            SecurityManager security = new SecurityManager();
            processor = buildProcessor(config.trigger, security, rewrite, cb);
            
            Map<String, ModuleConfig> moduleConfigs = new TreeMap<String,ModuleConfig>();
            for (ModuleConfig config : config.modules) {
            	moduleConfigs.put(config.className, config);
            }
            
            ServiceLoader<Module> modules = ServiceLoader.load(Module.class);
            for (Module module : modules) {
            	
            	//module data
            	String name = module.getName();
            	processor.register(name, module);
            	System.out.println("new module: "+module);
            	
            	//configuration items
            	ModuleConfig config = moduleConfigs.get(name);
            	if (config != null) {    		
            		if (config.aliases != null) {
            			for (String alias : config.aliases) {
            				processor.alias(module.getName(), alias);
            			}
            		}
            	}
            	
            }
            
            Module[] legacyModules = new Module[] {
            	ModuleUtils.wrap("factoid", new FactoidCommand(DBConnection.getFactoidModel()) ),
            	ModuleUtils.wrap("help", new HelpCommand(processor)),
            	ModuleUtils.wrap("plugins", new PluginCommand(processor)),
            	ModuleUtils.wrap("sesssion", new SessionCommand(security))
            };
            
            for (Module module : legacyModules) {
            	processor.register(module.getName(), module);
            }

			cb.addListener(new JoinsListener());
            cb.addListener(new LinesListener());

            buildBot(cb, config);
            instance = createBot(cb);
            instance.startBot();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /**
     * Map our configuration file onto PIrcBotX's config builder
     *
     * @param cb PircBotX config builder
     * @param config our configuration instance
     */
    private void buildBot(Configuration.Builder<PircBotX> cb, LocalConfiguration config) {
        cb.setName(config.nick);
        cb.setServer(config.host, config.port);
        
        if (config.sasl){
        	cb.addCapHandler(new SASLCapHandler(config.username, config.password));
        }

        if (config.ssl) {
            cb.setSocketFactory(SSLSocketFactory.getDefault());
        }

        // setup automatic channel joins
        for (String channel : config.channels) {
            cb.addAutoJoinChannel(channel);
        }

        //Useful stuff to keep the bot running
        cb.setAutoNickChange(true);
        cb.setAutoReconnect(true);
    }

    /**
     * Create the IRC bot from a complete PircBotX configuration
     * @param cb the bot configuration
     * @return a PircBotX instance
     */
    private PircBotX createBot(Configuration.Builder<PircBotX> cb) {
        Configuration<PircBotX> configuration = cb.buildConfiguration();
        return new PircBotX(configuration);
    }

    /**
     * Create a CommandProcessor Instance and return it.
     *
     * This method builds a CommandProcessor instance which can be used to register commands with the bot. This will
     * also register the command processor with the bot's configuration so it gets called when people send messages to
     * the irc bot.
     *
     * @param trigger the trigger charicter the bot will respond to
     * @param cb PircBotX configuration
     * @return A constructed CommandProcessor instance
     */
    private CommandProcessor buildProcessor(char trigger, SecurityManager s, RewriteEngine rewrite, Configuration.Builder<PircBotX> cb) {
        CommandProcessor processor = new CommandProcessor(s);
        cb.addListener(new CommandListener(processor, rewrite, trigger));
        return processor;
    }
}
