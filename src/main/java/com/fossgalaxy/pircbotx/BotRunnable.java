/**
 * Copyright © 2014-2016 Unity Coders
 * <p>
 * This file is part of uc_pircbotx.
 * <p>
 * uc_pircbotx is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p>
 * uc_pircbotx is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * uc_pircbotx. If not, see <http://www.gnu.org/licenses/>.
 */
package com.fossgalaxy.pircbotx;

import com.fossgalaxy.pircbotx.backends.BackendException;
import com.fossgalaxy.pircbotx.backends.BotService;
import com.fossgalaxy.pircbotx.backends.irc.IrcModule;
import com.fossgalaxy.pircbotx.commandprocessor.CommandModule;
import com.fossgalaxy.pircbotx.commandprocessor.CommandProcessor;
import com.fossgalaxy.pircbotx.data.db.DatabaseModule;
import com.fossgalaxy.pircbotx.middleware.BotMiddleware;
import com.fossgalaxy.pircbotx.modules.Module;
import com.fossgalaxy.pircbotx.modules.ModuleConfig;
import com.fossgalaxy.pircbotx.modules.ModuleUtils;
import com.fossgalaxy.pircbotx.security.SecurityMiddleware;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

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
                        processor.alias(alias, module.getName());
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
        } catch (ReflectiveOperationException ex) {
            LOG.error("could not setup bot", ex);
            throw new BotException(ex);
        } catch (IOException | BackendException ex) {
            LOG.error("bot failed to start", ex);
            throw new BotException(ex);
        }
    }

}
