package uk.co.unitycoders.pircbotx.commands;

import org.pircbotx.PircBotX;
import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.security.Secured;


/**
 * Created by webpigeon on 03/12/14.
 */
public class IRCCommands {

    @Command("join")
    @Secured
    public void onJoinRequest(Message message) {
        String channel = message.getArgument(2, null);
        
        if (channel == null) {
        	message.respond("you didn't supply a channel");
        }

        PircBotX bot = message.getBot();
        bot.sendIRC().joinChannel(channel);
    }

    @Command("nick")
    @Secured
    public void onNickRequest(Message message) {
        String newNick = message.getArgument(2, null);
        
        if (newNick == null) {
        	message.respond("you didn't supply a new nick");
        }

        PircBotX bot = message.getBot();
        bot.sendIRC().changeNick(newNick);
    }

    @Command("quit")
    @Secured
    public void onQuitRequest(Message message) {
        PircBotX bot = message.getBot();
        bot.stopBotReconnect();
        bot.sendIRC().quitServer(message.getUser().getNick()+" told me to quit");
    }
}
