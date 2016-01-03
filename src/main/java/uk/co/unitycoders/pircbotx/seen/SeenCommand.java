package uk.co.unitycoders.pircbotx.seen;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.modules.AnnotationModule;

import java.text.SimpleDateFormat;

/**
 * Created by webpigeon on 07/04/14.
 */
public class SeenCommand extends AnnotationModule {
	private static final String USEAGE_STR = "usage: seen [nick]";
	private static final String FORMAT_STR = "I last saw %s on %s";
	private static final String UNKNOWN_NICK = "I don't know %s";
	
    private SeenListener listener;
    private SimpleDateFormat dateFormat;

    public SeenCommand(SeenListener listener) {
    	super("seen");
        this.listener = listener;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    }

    @Command
    public void onSeen(Message event) throws Exception {
    	String nick = event.getArgument(2, null);
    	if (nick == null) {
    		event.respond(USEAGE_STR);
    		return;
    	}
    	
        Long lastStamp = listener.getLastSeen(nick);
        if (lastStamp == null) {
            event.respond(String.format(UNKNOWN_NICK, nick));
            return;
        }

        event.respond(String.format(FORMAT_STR, nick, dateFormat.format(lastStamp)));
    }

}
