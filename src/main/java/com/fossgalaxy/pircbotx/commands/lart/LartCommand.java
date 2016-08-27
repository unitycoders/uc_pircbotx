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
package com.fossgalaxy.pircbotx.commands.lart;

import com.fossgalaxy.pircbotx.commandprocessor.Command;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.modules.AnnotationModule;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Insults the 1st argument.
 *
 * @author Bruce Cowan
 */
public class LartCommand extends AnnotationModule {
    final Logger logger = LoggerFactory.getLogger(LartCommand.class);
    private LartModel model;

    /**
     * Creates a {@link LartCommand}.
     */
    public LartCommand(LartModel model) {
        this();
        this.model = model;
    }

    public LartCommand() {
        super("lart");
    }

    @Inject
    public void onModel(LartModel model) {
        this.model = model;
    }

    @Command("add")
    public void onAdd(Message event) {
        String ops = event.getArgument(2, null);
        if (ops == null) {
            event.respond("Invalid format for add");
            return;
        }

        try {
            int num = model.storeLart(event.getTargetName(), event.getUser(), ops);
            event.respond("Lart # " + num + " added");
        } catch (IllegalArgumentException ex) {
            event.respond("No $who section given");
            logger.debug("No $who section given", ex);
        }
    }

    @Command("delete")
    public void onDelete(Message event) {
        int id = toInt(event);
        if (id == -1) {
            event.respond("Invalid format for delete");
            return;
        }

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
        if (id == -1) {
            event.respond("Invalid format for info");
        }

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
        int id = toInt(event);
        String pattern = event.getArgument(3, null);

        if (id == -1 || pattern == null) {
            event.respond("Invalid Format for alter");
            return;
        }

        try {
            this.model.alterLart(id, event.getTargetName(), event.getUser(), pattern);
            event.respond("Lart #" + id + " altered");
        } catch (IllegalArgumentException ex) {
            event.respond("No $who section given");
            logger.debug("No $who section given", ex);
        }
    }

    /**
     * Insults someone.
     *
     * @param event the event from the bot framework
     * @param event The bot's message event
     */
    @Command("default")
    public void insult(Message event) {
        String nick = event.getArgument(2, event.getUser().getNick());
        String insult;

        String pattern = this.model.getRandomLart().getPattern();
        insult = pattern.replace("$who", nick);
        event.sendAction(insult);
    }

    private int toInt(Message message) {
        try {
            String idStr = message.getArgument(2, null);
            if (idStr == null) {
                return -1;
            }

            return Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            logger.error("Conversion error");
        }

        return -1;
    }
}
