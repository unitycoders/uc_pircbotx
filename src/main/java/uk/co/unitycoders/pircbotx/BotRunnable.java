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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.co.unitycoders.pircbotx.commandprocessor.irc.IRCFactory;
import uk.co.unitycoders.pircbotx.commands.HelpCommand;
import uk.co.unitycoders.pircbotx.commands.PluginCommand;
import uk.co.unitycoders.pircbotx.listeners.JoinsListener;
import uk.co.unitycoders.pircbotx.listeners.LinesListener;
import uk.co.unitycoders.pircbotx.middleware.BotMiddleware;
import uk.co.unitycoders.pircbotx.modules.Module;
import uk.co.unitycoders.pircbotx.modules.ModuleConfig;
import uk.co.unitycoders.pircbotx.modules.ModuleUtils;
import uk.co.unitycoders.pircbotx.security.SecurityManager;
import uk.co.unitycoders.pircbotx.security.SecurityMiddleware;
import uk.co.unitycoders.pircbotx.security.SessionCommand;

public class BotRunnable implements Runnable {
	private SecurityManager security;
	private CommandProcessor processor;
	private LocalConfiguration config;

	public BotRunnable(LocalConfiguration config) {
		this.config = config;
	}

	private void setupProcessor() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<BotMiddleware> middleware = new ArrayList<BotMiddleware>();
		if (config.middleware != null) {
			for (String middlewareClass : config.middleware) {
				BotMiddleware mw = ModuleUtils.loadMiddleware(middlewareClass);
				middleware.add(mw);
				mw.init(config);
			}
		}

		//legacy middleware
		security = new SecurityManager();
		middleware.add(new SecurityMiddleware(security));
		processor = new CommandProcessor(middleware);
	}

	private void loadPlugins() {
		Map<String, ModuleConfig> moduleConfigs = config.modules;
		if (moduleConfigs == null) {
			moduleConfigs = Collections.emptyMap();
		}

		//load in the modules
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
						processor.alias(alias,module.getName());
					}
				}
			}

		}

		//load in modules which require arguments
		Module[] legacyModules = new Module[] {
				ModuleUtils.wrap("help", new HelpCommand(processor)),
				ModuleUtils.wrap("plugins", new PluginCommand(processor)),
				ModuleUtils.wrap("session", new SessionCommand(security))
		};

		//register all legacy style modules
		for (Module module : legacyModules) {
			processor.register(module.getName(), module);
		}
	}

	@Override
	public void run() {

		try {

			//This is out bits
			setupProcessor();
			loadPlugins();


			//this creates our host bot instance
			Configuration.Builder<PircBotX> cb = IRCFactory.doConfig(config, processor);

			cb.addListener(new JoinsListener());
			cb.addListener(new LinesListener());

			//build pircbotx
			Configuration<PircBotX> configuration = cb.buildConfiguration();
			PircBotX instance = new PircBotX(configuration);
			instance.startBot();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
