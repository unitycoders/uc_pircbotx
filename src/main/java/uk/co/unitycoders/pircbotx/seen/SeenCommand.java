package uk.co.unitycoders.pircbotx.seen;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;

import java.text.SimpleDateFormat;

/**
 * Created by webpigeon on 07/04/14.
 */
public class SeenCommand {
    private SeenListener listener;
    private SimpleDateFormat dateFormat;

    public SeenCommand(SeenListener listener) {
        this.listener = listener;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    }

    @Command
    public void onSeen(Message event) throws Exception {
        String nick = event.getMessage().split(" ")[1];
        Long lastStamp = listener.getLastSeen(nick);
        if (lastStamp == null) {
            event.respond("I don't know "+nick);
            return;
        }


        event.respond("I last saw "+nick+" on "+dateFormat.format(lastStamp));
    }

}
