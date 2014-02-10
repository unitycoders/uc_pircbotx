package uk.co.unitycoders.pircbotx.commands;

import java.sql.SQLException;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.data.db.DBConnection;
import uk.co.unitycoders.pircbotx.data.db.KarmaModel;

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
        String target = event.getMessage().split(" ")[1];

        int karma = this.model.getKarma(target);
        event.respond("Karma for " + target + " = " + karma);
    }

    @Command({"increment", "add"})
    public void incrementKarma(Message event) throws Exception {
        String target = event.getMessage().split(" ")[2];

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
        String target = event.getMessage().split(" ")[2];

        try {
            int karma = this.model.decrementKarma(target);
            event.respond("Karma for " + target + " is now " + karma);
        } catch (SQLException ex) {
            event.respond("Database error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
