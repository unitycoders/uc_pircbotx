/**
 * Copyright © 2013-2015 Unity Coders
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

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.commandprocessor.MessageUtils;
import uk.co.unitycoders.pircbotx.data.db.DBConnection;
import uk.co.unitycoders.pircbotx.data.db.Factoid;
import uk.co.unitycoders.pircbotx.data.db.FactoidModel;
import uk.co.unitycoders.pircbotx.modules.AnnotationModule;

public class FactoidCommand extends AnnotationModule {
	private final Logger logger = LoggerFactory.getLogger(FactoidCommand.class);
	private FactoidModel model;

	public FactoidCommand() {
		super("factoid");
		try {
			this.model = DBConnection.getFactoidModel();
		} catch (ClassNotFoundException | SQLException ex) {
			logger.error("Database error", ex);
		}
	}

	@Command("add")
	public void addFactoid(Message message) {
		String name = message.getArgument(2, null);
		String text = message.getArgument(3, null);

		if (name == null || text == null) {
			message.respond("usage: factoid add [name] [text]");
			return;
		}

		boolean status = model.addFactoid(name, text);
		String result = status?"Factoid added successfully":"factoid failed to get added";
		message.respond(result);
	}

	@Command("get")
	public void getFactoid(Message message) {
		String name = message.getArgument(2, null);

		if (name == null) {
			message.respond("usage: factoid get [name]");
			return;
		}

		String factoid = model.getFactoid(name);
		if (factoid == null) {
			message.respond("Sorry, unknown factoid.");
		} else {
			message.respond(factoid);
		}
	}

	@Command("search")
	public void onSearch(Message message) {
		String query = message.getArgument(2, null);
		if (query == null) {
			message.respond("Usage: factoid search [query]");
			return;
		}

		List<Factoid> factoids = model.search(query);
		if (factoids.isEmpty()) {
			message.respond("That didn't match any results");
		} else {
			String listStr = MessageUtils.buildList(factoids);
			message.respond(String.format("matching factoids: %s", listStr));
		}
	}

	@Command("random")
	public void getRandom(Message message) {
		Factoid factoid = model.getRandom();
		if (factoid == null) {
			message.respond("Sorry, unknown factoid.");
		} else {
			message.respond(factoid.body);
		}
	}


	@Command("edit")
	public void updateFactoid(Message message) {
		String name = message.getArgument(2, null);
		String text = message.getArgument(3, null);

		if (name == null || text == null) {
			message.respond("usage: factoid edit [name] [text]");
			return;
		}

		boolean result = model.editFactoid(name, text);
		if (result) {
			message.respond("Factoid updated");
		} else {
			message.respond("factoid did not get updated");
		}
	}

	@Command("remove")
	public void removeFactoid(Message message) {
		String name = message.getArgument(2, null);

		if (name == null) {
			message.respond("usage: factoid remove [name]");
			return;
		}

		boolean status = model.deleteFactoid(name);
		String result = status?"Factoid removed successfully":"factoid failed to get removed";
		message.respond(result);
	}

}
