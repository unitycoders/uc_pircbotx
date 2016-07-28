/**
 * Copyright © 2012-2015 Unity Coders
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

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fossgalaxy.pircbotx.commandprocessor.Command;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.data.db.DBConnection;
import com.fossgalaxy.pircbotx.data.db.LineModel;
import com.fossgalaxy.pircbotx.modules.AnnotationModule;

/**
 * Keeps a log of all the lines said, and randomly speaks one.
 *
 * @author Bruce Cowan
 */
public class RandCommand extends AnnotationModule {

	private static final Logger logger = LoggerFactory.getLogger(RandCommand.class);
	private LineModel lines;

	public RandCommand() {
		super("rand");
		try {
			lines = DBConnection.getLineModel();
		} catch (ClassNotFoundException | SQLException ex) {
			logger.error("Database error", ex);
		}
	}

	@Command
	public void onRandom(Message event) {
		String line = lines.getRandomLine();
		event.respond(line);
	}
}
