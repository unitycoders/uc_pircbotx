/**
 * Copyright © 2014-2015 Unity Coders
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

import org.pircbotx.PircBotX;
import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.security.Secured;

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
