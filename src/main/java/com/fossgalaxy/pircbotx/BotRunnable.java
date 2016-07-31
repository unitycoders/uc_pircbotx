/**
 * Copyright © 2014-2016 Unity Coders
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
package com.fossgalaxy.pircbotx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.fossgalaxy.pircbotx.commandprocessor.CommandProcessor;
import com.fossgalaxy.pircbotx.commands.HelpCommand;
import com.fossgalaxy.pircbotx.commands.PluginCommand;
import com.fossgalaxy.pircbotx.listeners.JoinsListener;
import com.fossgalaxy.pircbotx.listeners.LinesListener;
import com.fossgalaxy.pircbotx.middleware.BotMiddleware;
import com.fossgalaxy.pircbotx.modules.Module;
import com.fossgalaxy.pircbotx.modules.ModuleConfig;
import com.fossgalaxy.pircbotx.modules.ModuleUtils;
import com.fossgalaxy.pircbotx.security.SecurityManager;
import com.fossgalaxy.pircbotx.security.SecurityMiddleware;
import com.fossgalaxy.pircbotx.security.SessionCommand;
import com.fossgalaxy.pircbotx.backends.irc.IRCFactory;

public class BotRunnable implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(BotRunnable.class);
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
			LOG.info("new module: {} ", module);

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
			Configuration.Builder cb = IRCFactory.doConfig(config, processor);

			cb.addListener(new JoinsListener());
			cb.addListener(new LinesListener());

			//build pircbotx
			Configuration configuration = cb.buildConfiguration();
			PircBotX instance = new PircBotX(configuration);
			instance.startBot();
			instance.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
