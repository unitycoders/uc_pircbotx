package uk.co.unitycoders.pircbotx.commands;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.data.db.FactoidModel;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by webpigeon on 16/12/13.
 */
public class FactoidCommand {
    private FactoidModel model;
    private Pattern factoidPattern;

    public FactoidCommand(FactoidModel model) {
        this.model = model;
        this.factoidPattern = Pattern.compile(".factoid ([^ ]+) (\\S+) (.+)");
    }

    @Command("add")
    public void addFactoid(Message message) throws SQLException {
        String messageText = message.getMessage();

        Matcher matcher = factoidPattern.matcher(messageText);
        if (!matcher.matches()) {
            message.respond("usage: factoid add [name] [text]");
        }

        boolean status = model.addFactoid(matcher.group(2), matcher.group(3));

        String result = status?"Factoid added successfully":"factoid failed to get added";
        message.respond(result);
    }

    @Command("get")
    public void getFactoid(Message message) throws SQLException {
        String messageText = message.getMessage();
        String[] args = messageText.split(" ");

        if (args.length != 3) {
            message.respond("usage: factoid get [name]");
        }

        String factoid = model.getFactoid(args[2]);
        if (factoid == null) {
            message.respond("Sorry, unknown factoid.");
        } else {
            message.respond(factoid);
        }
    }

    @Command("edit")
    public void updateFactoid(Message message) throws SQLException {
        String messageText = message.getMessage();

        Matcher matcher = factoidPattern.matcher(messageText);
        if (!matcher.matches()) {
            message.respond("usage: factoid add [name] [text]");
        }

        model.editFactoid(matcher.group(2), matcher.group(3));
    }

    @Command("remove")
    public void removeFactoid(Message message) throws SQLException {
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
