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
package com.fossgalaxy.pircbotx.commands;

import com.fossgalaxy.pircbotx.commandprocessor.Command;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.data.db.KarmaModel;
import com.fossgalaxy.pircbotx.modules.AnnotationModule;
import com.fossgalaxy.pircbotx.types.Karma;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class KarmaCommand extends AnnotationModule {

    private static final Logger logger = LoggerFactory.getLogger(KarmaCommand.class);
    private KarmaModel model;

    @Inject
    public KarmaCommand(KarmaModel model) {
        this();
        this.model = model;
    }

    public KarmaCommand() {
        super("karma");
    }

    @Inject
    public void onModel(KarmaModel model) {
        this.model = model;
    }

    @Command("default")
    public void onKarma(Message event) {
        String target = event.getArgument(2);

        int karma = this.model.getKarma(target);
        event.respond("Karma for " + target + " = " + karma);
    }

    @Command("top")
    public void topKarma(Message event) {
        Collection<Karma> karmaList = model.getTopKarma(5);
        if (karmaList == null) {
            event.respond("Could not get top 5");
        } else {
            StringBuilder builder = new StringBuilder();
            for (Karma karma : karmaList) {
                builder.append(karma.toString());
                builder.append(" ");
            }

            event.respond(builder.toString());
        }
    }

    @Command({"increment", "add"})
    public void incrementKarma(Message event) {
        String target = event.getArgument(2);

        int karma = this.model.incrementKarma(target);
        event.respond("Karma for " + target + " is now " + karma);
    }

    @Command({"decrement", "remove"})
    public void decrementKarma(Message event) {
        String target = event.getArgument(2);

        int karma = this.model.decrementKarma(target);
        event.respond("Karma for " + target + " is now " + karma);
    }
}
