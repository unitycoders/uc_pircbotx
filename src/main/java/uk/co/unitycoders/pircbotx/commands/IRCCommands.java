package uk.co.unitycoders.pircbotx.commands;

import org.pircbotx.PircBotX;
import uk.co.unitycoders.pircbotx.commandprocessor.ChannelMessage;
import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.security.Secured;

import java.nio.channels.Channel;

/**
 * Created by webpigeon on 03/12/14.
 */
public class IRCCommands {

    @Command("join")
    @Secured
    public void onJoinRequest(Message message) {
        String[] args = message.getMessage().split(" ");
        String channel = args[2];

        PircBotX bot = message.getBot();
        bot.sendIRC().joinChannel(channel);
    }

    @Command("nick")
    @Secured
    public void onNickRequest(Message message) {
        String[] args = message.getMessage().split(" ");
        String newNick = args[2];

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
