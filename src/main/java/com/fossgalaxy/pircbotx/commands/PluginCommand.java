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
package com.fossgalaxy.pircbotx.commands;

import java.util.Collection;
import java.util.List;


import com.fossgalaxy.pircbotx.commandprocessor.Command;
import com.fossgalaxy.pircbotx.commandprocessor.CommandProcessor;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.modules.AnnotationModule;
import com.fossgalaxy.pircbotx.modules.Module;
import com.fossgalaxy.pircbotx.modules.ModuleUtils;
import com.fossgalaxy.pircbotx.security.Secured;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginCommand extends AnnotationModule {
	private final Logger LOG = LoggerFactory.getLogger(PluginCommand.class);
	private CommandProcessor processor;

	public PluginCommand() {
		super("plugin");
	}

	@Inject
	protected void inject(CommandProcessor processor) {
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
		String name = message.getArgument(2);
		String className = message.getArgument(3);

		try {
			Module module = ModuleUtils.loadModule(className);
			processor.register(name, module);
			message.respond("Class has been loaded");
		} catch (IllegalAccessException ex) {
			message.respond("No default no-arg constructor for class");
			LOG.warn("No default no-arg constructor for class", ex);
		} catch (ClassNotFoundException ex) {
			message.respond("Class could not be found");
			LOG.warn("Class could not be found", ex);
		} catch (InstantiationException ex) {
			message.respond("Class could not be instantiated");
			LOG.warn("Class could not be instantiated", ex);
		}
	}

	// WARNING: this represents a massive security risk - ONLY USE FOR DEBUGGING
	@Command("alias")
	@Secured
	public void onAlias(Message message) {

		try {
			String alias = message.getArgument(2);
			String commandKeyword = message.getArgument(3);
			processor.alias(alias, commandKeyword);
			message.respond("Plugin is now aliased");
		} catch (RuntimeException ex) {
			message.respond("error: "+ex.getLocalizedMessage());
			LOG.warn("could not create alias: ", ex);
		}
	}

	// WARNING: this represents a massive security risk - ONLY USE FOR DEBUGGING
	@Command("unload")
	@Secured
	public void onUnload(Message message) {
		String name = message.getArgument(2);
		processor.remove(name);
		message.respond("Plugin has been unloaded");
	}

	@Command("reverse")
	public void onReverse(Message message) {
		String action = message.getArgument(2);

		List<String> modules = processor.getReverse(action);
		if (!modules.isEmpty()) {
			message.respond(String.format("%s is provided by %s", action, modules));
		} else {
			message.respond("nothing provides that");
		}
	}
}
