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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.data.db.FactoidModel;
import uk.co.unitycoders.pircbotx.modules.AnnotationModule;

public class FactoidCommand extends AnnotationModule {
    private FactoidModel model;
    private Pattern factoidPattern;

    public FactoidCommand(FactoidModel model) {
    	super("factoid");
        this.model = model;
        this.factoidPattern = Pattern.compile("factoid ([^ ]+) (\\S+) (.+)");
    }

    @Command("add")
    public void addFactoid(Message message) {
        String messageText = message.getMessage();

        Matcher matcher = factoidPattern.matcher(messageText);
        if (!matcher.matches()) {
            message.respond("usage: factoid add [name] [text]");
            return;
        }

        boolean status = model.addFactoid(matcher.group(2), matcher.group(3));
        String result = status?"Factoid added successfully":"factoid failed to get added";
        message.respond(result);
    }

    @Command("get")
    public void getFactoid(Message message) {
        String messageText = message.getMessage();
        String[] args = messageText.split(" ");

        if (args.length != 3) {
            message.respond("usage: factoid get [name]");
            return;
        }

        String factoid = model.getFactoid(args[2]);
        if (factoid == null) {
            message.respond("Sorry, unknown factoid.");
        } else {
            message.respond(factoid);
        }
    }

    @Command("edit")
    public void updateFactoid(Message message) {
        String messageText = message.getMessage();

        Matcher matcher = factoidPattern.matcher(messageText);
        if (!matcher.matches()) {
            message.respond("usage: factoid add [name] [text]");
        }

        model.editFactoid(matcher.group(2), matcher.group(3));
    }

    @Command("remove")
    public void removeFactoid(Message message) {
        String messageText = message.getMessage();
        String[] args = messageText.split(" ");

        if (args.length != 3) {
            message.respond("usage: factoid remove [name]");
        }

        boolean status = model.deleteFactoid(args[2]);
        String result = status?"Factoid removed successfully":"factoid failed to get removed";
        message.respond(result);
    }

}
