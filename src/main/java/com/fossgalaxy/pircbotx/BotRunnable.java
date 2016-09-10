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

import com.fossgalaxy.bot.api.command.Catalogue;
import com.fossgalaxy.bot.api.command.Invoker;
import com.fossgalaxy.bot.core.command.ApiCommandModule;
import com.fossgalaxy.bot.core.command.InferControllerPreprocessor;
import com.fossgalaxy.bot.core.command.InsertMissingDefaultPreprocessor;
import com.fossgalaxy.bot.core.command.RequestProcessor;
import com.fossgalaxy.bot.core.command.compat.ModuleController;
import com.fossgalaxy.bot.utils.commands.CatalogueController;
import com.fossgalaxy.bot.utils.commands.FilterController;
import com.fossgalaxy.bot.utils.commands.JustForFunController;
import com.fossgalaxy.pircbotx.backends.BackendException;
import com.fossgalaxy.pircbotx.backends.BotService;
import com.fossgalaxy.pircbotx.backends.irc.IrcModule;
import com.fossgalaxy.pircbotx.commands.script.ScriptConfig;
import com.fossgalaxy.pircbotx.commands.script.ScriptModule;
import com.fossgalaxy.pircbotx.data.db.DatabaseModule;
import com.fossgalaxy.pircbotx.modules.Module;
import com.fossgalaxy.pircbotx.modules.ModuleConfig;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.*;

public class BotRunnable implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(BotRunnable.class);
    private LocalConfiguration config;

    public BotRunnable(LocalConfiguration config) {
        this.config = config;
    }


    private void loadPlugins(Injector injector) {
        Catalogue catalogue = injector.getInstance(Catalogue.class);

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
            catalogue.register(name, new ModuleController(module));
            LOG.info("module {} has been loaded though compatibility layer.", module);

            //configuration items
            ModuleConfig config = moduleConfigs.get(name);
            if (config != null) {
                if (config.aliases != null) {
                    for (String alias : config.aliases) {
                        catalogue.alias(alias, name);
                    }
                }
            }

        }
    }

    public void loadScripts(Injector injector) {
        if (config.scripts == null) {
            return;
        }

        Catalogue catalogue = injector.getInstance(Catalogue.class);

        Map<String, ScriptConfig> scripts = config.scripts;
        for (Map.Entry<String, ScriptConfig> configEntry : scripts.entrySet()) {
            try {
                String name = configEntry.getKey();
                ScriptConfig config = configEntry.getValue();
                ScriptModule sm = new ScriptModule(name, config.filename);
                injector.injectMembers(sm);
                sm.init();

                catalogue.register(name, new ModuleController(sm));
            } catch (ScriptException ex) {
                LOG.warn("failed to load script: ", ex);
            }
        }
    }

    @Override
    public void run() {

        try {
            Injector injector = Guice.createInjector(new DatabaseModule(), new ApiCommandModule(), new IrcModule());

            //get out pipelines sorted
            RequestProcessor processor = injector.getInstance(RequestProcessor.class);
            processor.addLast(injector.getInstance(InferControllerPreprocessor.class));
            processor.addLast(injector.getInstance(InsertMissingDefaultPreprocessor.class));
            //processor.addLast(injector.getInstance(SessionPreprocessor.class));

            //This is our bits
            loadPlugins(injector);
            loadScripts(injector);

            Catalogue catalogue = injector.getInstance(Catalogue.class);
            catalogue.register("catalogue", new CatalogueController(catalogue));
            catalogue.register("filters", new FilterController());
            catalogue.register("misc", new JustForFunController());

            BotService service = injector.getInstance(BotService.class);
            service.start(config, injector.getInstance(Invoker.class));
        } catch (IOException | BackendException ex) {
            LOG.error("bot failed to start", ex);
            throw new BotException(ex);
        }
    }

}
