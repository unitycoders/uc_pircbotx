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
import java.util.Collection;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.data.db.DBConnection;
import uk.co.unitycoders.pircbotx.data.db.KarmaModel;
import uk.co.unitycoders.pircbotx.types.Karma;

public class KarmaCommand {

    private KarmaModel model;

    public KarmaCommand() {
        try {
            this.model = DBConnection.getKarmaModel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Command("default")
    public void onKarma(Message event) throws Exception {
        String target = event.getArgument(2, null);

        int karma = this.model.getKarma(target);
        event.respond("Karma for " + target + " = " + karma);
    }
    
    @Command("top")
    public void topKarma(Message event) throws Exception {
    	Collection<Karma> karmaList = model.getTopKarma(5);
    	
    	StringBuffer buffer = new StringBuffer();
    	for (Karma karma : karmaList) {
    		buffer.append(karma.toString());
    		buffer.append(" ");
    	}
    	
    	event.respond(buffer.toString());
    }

    @Command({"increment", "add"})
    public void incrementKarma(Message event) throws Exception {
        String target = event.getArgument(2, null);

        try {
            int karma = this.model.incrementKarma(target);
            event.respond("Karma for " + target + " is now " + karma);
        } catch (SQLException ex) {
            event.respond("Database error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Command({"decrement", "remove"})
    public void decrementKarma(Message event) throws Exception {
        String target = event.getArgument(2, null);

        try {
            int karma = this.model.decrementKarma(target);
            event.respond("Karma for " + target + " is now " + karma);
        } catch (SQLException ex) {
            event.respond("Database error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
