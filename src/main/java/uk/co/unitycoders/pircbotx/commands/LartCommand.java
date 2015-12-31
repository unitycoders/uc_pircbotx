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
package uk.co.unitycoders.pircbotx.commands;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pircbotx.hooks.events.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.data.db.DBConnection;
import uk.co.unitycoders.pircbotx.data.db.LartModel;
import uk.co.unitycoders.pircbotx.modules.AnnotationModule;
import uk.co.unitycoders.pircbotx.types.Lart;

/**
 * Insults the 1st argument.
 *
 * @author Bruce Cowan
 */
public class LartCommand extends AnnotationModule {
	final Logger logger = LoggerFactory.getLogger(LartCommand.class);
    private LartModel model;
    private final Pattern re;
    private final Pattern alterRe;

    /**
     * Creates a {@link LartCommand}.
     */
    public LartCommand() {
    	super("lart");
        try {
            this.model = DBConnection.getLartModel();
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error("Database error", ex);
        }

        this.re = Pattern.compile("lart ([^ ]+) *(.*)?");
        this.alterRe = Pattern.compile("lart alter (\\d) (.*)");
    }

    @Command("add")
    public void onAdd(Message event) {
        // TODO this bit could still be nicer
        String msg = event.getMessage();
        Matcher matcher = re.matcher(msg);

        if (!matcher.matches()) {
            event.respond("Invalid format for add");
            return;
        }

        String ops = matcher.group(2);

        try {
            int num = model.storeLart(event.getTargetName(), event.getUser(), ops);
            event.respond("Lart # " + num + " added");
        } catch (IllegalArgumentException ex) {
            event.respond("No $who section given");
        }
    }

    @Command("delete")
    public void onDelete(Message event) {
        int id = toInt(event);

        boolean result = model.deleteLart(id);
        if (result) {
            event.respond("Deleted lart #" + id);
        } else {
            event.respond("No such lart in database");
        }
    }

    @Command("info")
    public void onInfo(Message event) {
        int id = toInt(event);
        Lart lart = model.getLart(id);
        String resp = String.format("Channel: %s, Nick: %s, Pattern: %s", lart.getChannel(), lart.getNick(), lart.getPattern());
        event.respond(resp);
    }

    @Command("list")
    public void onList(Message event) {
        StringBuilder builder = new StringBuilder();
        List<Lart> larts = this.model.getAllLarts();

        if (larts.isEmpty()) {
            return;
        }

        for (Lart lart : larts) {
            int id = lart.getID();
            builder.append(id);
            builder.append(',');
        }

        builder.deleteCharAt(builder.length() - 1);
        event.respond(builder.toString());
    }

    @Command("alter")
    public void onAlter(Message event) {
        Matcher matcher = alterRe.matcher(event.getMessage());
        if (!matcher.matches()) {
            event.respond("Invalid Format for alter");
            return;
        }

        try {
            int id = Integer.parseInt(matcher.group(1));
            String pattern = matcher.group(2);

            this.model.alterLart(id, event.getTargetName(), event.getUser(), pattern);
            event.respond("Lart #" + id + " altered");
        } catch (IllegalArgumentException ex) {
            event.respond("No $who section given");
        }
    }

    /**
     * Insults someone.
     *
     * @param event the event from {@link #onMessage(MessageEvent)}.
     */
    @Command("default")
    public void insult(Message event) {
        String nick = event.getMessage().split(" ")[1];
        String insult;

        String pattern = this.model.getRandomLart().getPattern();
        insult = pattern.replace("$who", nick);
        event.sendAction(insult);
    }

    private int toInt(Message event) {
        String msg = event.getMessage();
        Matcher matcher = re.matcher(msg);
        if (!matcher.matches()) {
            logger.error("Conversion error");
        }
        return Integer.parseInt(matcher.group(2));
    }
}
