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

import com.fossgalaxy.pircbotx.backends.BotService;
import com.fossgalaxy.pircbotx.backends.irc.IrcModule;
import com.fossgalaxy.pircbotx.commandprocessor.CommandModule;
import com.fossgalaxy.pircbotx.data.db.DatabaseModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fossgalaxy.pircbotx.commandprocessor.CommandProcessor;
import com.fossgalaxy.pircbotx.middleware.BotMiddleware;
import com.fossgalaxy.pircbotx.modules.Module;
import com.fossgalaxy.pircbotx.modules.ModuleConfig;
import com.fossgalaxy.pircbotx.modules.ModuleUtils;
import com.fossgalaxy.pircbotx.security.SecurityMiddleware;

public class BotRunnable implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(BotRunnable.class);
	private CommandProcessor processor;
	private LocalConfiguration config;

	public BotRunnable(LocalConfiguration config) {
		this.config = config;
	}

	private void setupProcessor(Injector injector) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<BotMiddleware> middleware = new ArrayList<>();
		if (config.middleware != null) {
			for (String middlewareClass : config.middleware) {
				BotMiddleware mw = ModuleUtils.loadMiddleware(middlewareClass);
				injector.injectMembers(mw);
				middleware.add(mw);
				mw.init(config);
			}
		}

		processor = injector.getInstance(CommandProcessor.class);

		processor.addMiddleware(injector.getInstance(SecurityMiddleware.class));
	}

	private void loadPlugins(Injector injector) {
		Map<String, ModuleConfig> moduleConfigs = config.modules;
		if (moduleConfigs == null) {
			moduleConfigs = Collections.emptyMap();
		}

		//load in the modules
		ServiceLoader<Module> modules = ServiceLoader.load(Module.class);
		for (Module module : modules) {

			injector.injectMembers(module);

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
	}

	@Override
	public void run() {

		try {
			Injector injector = Guice.createInjector(new DatabaseModule(), new CommandModule(), new IrcModule());

			//This is our bits
			setupProcessor(injector);
			loadPlugins(injector);

			BotService service = injector.getInstance(BotService.class);
			service.start(config, processor);
			service.stop();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
