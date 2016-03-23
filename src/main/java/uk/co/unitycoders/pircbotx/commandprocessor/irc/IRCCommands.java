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
package uk.co.unitycoders.pircbotx.commandprocessor.irc;

import java.util.HashSet;
import java.util.Set;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.modules.AnnotationModule;
import uk.co.unitycoders.pircbotx.security.Secured;

public class IRCCommands extends AnnotationModule {
	//TODO remove hack once security is secure
	private static final String BOT_OWNER = "webpigeon";

	//Important channels to protect
	private Set<String> protectedChannels;

	public IRCCommands() {
		super("irc");

		this.protectedChannels = new HashSet<String>();
		this.protectedChannels.add("#unity-coders");
	}

	@Command("protect")
	@Secured
	public void onProtect(Message message){
		if(BOT_OWNER.equals(message.getUser().getNick())) {
			String channel = message.getArgument(2, null);
			if (channel == null) {
				message.respond("usage: protect [channel]");
				return;
			}
			protectedChannels.add(channel);
			return;
		}
		message.respond("only BOT_OWNER can use this command");
	}

	@Command("unprotect")
	@Secured
	public void onUnprotect(Message message){
		if(BOT_OWNER.equals(message.getUser().getNick())) {
			String channel = message.getArgument(2, null);
			if (channel == null) {
				message.respond("usage: protect [channel]");
				return;
			}
			protectedChannels.remove(channel);
			message.respond("unprotected channel");
			return;
		}
		message.respond("only BOT_OWNER can use this command");
	}


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

	private Channel getChannel(PircBotX bot, String channelName){
		try {
			Channel channel = bot.getUserChannelDao().getChannel(channelName);
			if (channel == null) {
				return null;
			}
			return channel;
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}

	private User getUser(PircBotX bot, Channel channel, String nick){
		if (nick == null){
			return null;
		}

		try {
			User user = bot.getUserChannelDao().getUser(nick);
			if (user == null) {
				return null;
			}
			return user;
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}

	@Command("mode")
	@Secured
	public void onMode(Message message){
		String mode = message.getArgument(2, null);
		String channelName = message.getArgument(3, message.getTargetName());

		if (protectedChannels.contains(channelName)) {
			message.respond("That channel is protected.");
			return;
		}

		//WARNING: magic beyond this point
		PircBotX bot = message.getBot();
		Channel channel = getChannel(bot, channelName);

		//check we're in the channel and know the user
		if (mode == null || channel == null) {
			message.respond("I couldn't find that channel or invalid mode");
			return;
		}

		channel.send().setMode(mode);
	}

	@Command("op")
	@Secured
	public void onOp(Message message){
		String channelName = message.getArgument(3, message.getTargetName());
		String userToOp = message.getArgument(2, message.getUser().getNick());

		if (protectedChannels.contains(channelName)) {
			message.respond("That channel is protected.");
			return;
		}

		//WARNING: magic beyond this point
		PircBotX bot = message.getBot();
		Channel channel = getChannel(bot, channelName);
		User user = getUser(bot, channel, userToOp);

		//check we're in the channel and know the user
		if (user == null || channel == null) {
			message.respond("I couldn't find that user or channel");
			return;
		}

		//check we are op in the channel
		if (!channel.isOp(bot.getUserBot())) {
			message.respond("I am not OP'd");
			return;
		}

		channel.send().op(user);
	}

	@Command("deop")
	@Secured
	public void onDeop(Message message){
		String channelName = message.getArgument(3, message.getTargetName());
		String userToOp = message.getArgument(2, null);

		if (protectedChannels.contains(channelName)) {
			message.respond("That channel is protected.");
			return;
		}

		//WARNING: magic beyond this point
		PircBotX bot = message.getBot();
		Channel channel = getChannel(bot, channelName);
		User user = getUser(bot, channel, userToOp);

		//check we're in the channel and know the user
		if (user == null || channel == null) {
			message.respond("I couldn't find that user or channel");
			return;
		}

		//check we are op in the channel
		if (!channel.isOp(bot.getUserBot())) {
			message.respond("I am not OP'd");
			return;
		}

		channel.send().deOp(user);
	}

	@Command("voice")
	@Secured
	public void onVoice(Message message){
		String channelName = message.getArgument(3, message.getTargetName());
		String userToOp = message.getArgument(2, null);

		if (protectedChannels.contains(channelName)) {
			message.respond("That channel is protected.");
			return;
		}

		//WARNING: magic beyond this point
		PircBotX bot = message.getBot();
		Channel channel = getChannel(bot, channelName);
		User user = getUser(bot, channel, userToOp);

		//check we're in the channel and know the user
		if (user == null || channel == null) {
			message.respond("I couldn't find that user or channel");
			return;
		}

		//check we are op in the channel
		if (!channel.isOp(bot.getUserBot())) {
			message.respond("I am not OP'd");
			return;
		}

		channel.send().voice(user);
	}

	@Command("devoice")
	@Secured
	public void onDevoice(Message message){
		String channelName = message.getArgument(3, message.getTargetName());
		String userToOp = message.getArgument(2, null);

		if (protectedChannels.contains(channelName)) {
			message.respond("That channel is protected.");
			return;
		}

		//WARNING: magic beyond this point
		PircBotX bot = message.getBot();
		Channel channel = getChannel(bot, channelName);
		User user = getUser(bot, channel, userToOp);

		//check we're in the channel and know the user
		if (user == null || channel == null) {
			message.respond("I couldn't find that user or channel");
			return;
		}

		//check we are op in the channel
		if (!channel.isOp(bot.getUserBot())) {
			message.respond("I am not OP'd");
			return;
		}

		channel.send().deVoice(user);
	}

	@Command("kick")
	@Secured
	public void onKick(Message message){
		String channelName = message.getArgument(3, message.getTargetName());
		String userToOp = message.getArgument(2, null);

		if (protectedChannels.contains(channelName)) {
			message.respond("That channel is protected.");
			return;
		}

		//WARNING: magic beyond this point
		PircBotX bot = message.getBot();
		Channel channel = getChannel(bot, channelName);
		User user = getUser(bot, channel, userToOp);

		//check we're in the channel and know the user
		if (user == null || channel == null) {
			message.respond("I couldn't find that user or channel");
			return;
		}

		//check we are op in the channel
		if (!channel.isOp(bot.getUserBot())) {
			message.respond("I am not OP'd");
			return;
		}

		channel.send().kick(user,"kicked by "+message.getUser().getNick());
	}

	@Command("topic")
	@Secured
	public void onTopic(Message message){
		String channelName = message.getArgument(3, message.getTargetName());
		String topic = message.getArgument(2, null);

		if (protectedChannels.contains(channelName)) {
			message.respond("That channel is protected.");
			return;
		}

		//WARNING: magic beyond this point
		PircBotX bot = message.getBot();
		Channel channel = getChannel(bot, channelName);

		//check we're in the channel and know the user
		if (channel == null) {
			message.respond("I couldn't find that channel");
			return;
		}

		//check we are op in the channel
		if (!channel.isOp(bot.getUserBot())) {
			message.respond("I am not OP'd");
			return;
		}

		channel.send().setTopic(topic);
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
