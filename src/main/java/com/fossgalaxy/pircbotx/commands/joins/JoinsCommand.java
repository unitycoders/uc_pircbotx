/**
 * Copyright © 2012-2015 Unity Coders
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
package com.fossgalaxy.pircbotx.commands.joins;

import com.fossgalaxy.pircbotx.commandprocessor.Command;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.modules.AnnotationModule;
import com.google.inject.Inject;

import java.util.Map;

/**
 * Keeps a list of joins, and gives a list of nicks and number of joins.
 *
 * @author Bruce Cowan
 */
public class JoinsCommand extends AnnotationModule {

    private JoinModel model;

    /**
     * Creates a {@link JoinsCommand}.
     *
     * @throws Exception if database connection failed
     */
    public JoinsCommand(JoinModel model) {
        this();
        this.model = model;
    }

    public JoinsCommand() {
        super("joins");
    }

    @Inject
    public void onModel(JoinModel model) {
        this.model = model;
    }

    @Command
    public void onJoins(Message event) {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, Integer> entry : this.model.getAllJoins().entrySet()) {
            String nick = entry.getKey();
            String value = entry.getValue().toString();
            builder.append(nick);
            builder.append(" = ");
            builder.append(value);
            builder.append("; ");
        }

        event.respond(builder.substring(0, builder.length() - 2));
    }
}
