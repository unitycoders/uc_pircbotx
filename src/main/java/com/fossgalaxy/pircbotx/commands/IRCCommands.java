/**
 * Copyright © 2014-2015 Unity Coders
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

import com.fossgalaxy.pircbotx.backends.BotService;
import com.fossgalaxy.pircbotx.backends.ChannelService;
import com.fossgalaxy.pircbotx.backends.UserService;
import com.fossgalaxy.pircbotx.commandprocessor.Command;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.modules.AnnotationModule;
import com.fossgalaxy.pircbotx.security.Secured;
import com.google.inject.Inject;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.util.HashSet;
import java.util.Set;

public class IRCCommands extends AnnotationModule {
    //TODO remove hack once security is secure
    private static final String BOT_OWNER = "webpigeon";

    //Important channels to protect
    private Set<String> protectedChannels;

    private BotService bot;
    private UserService users;
    private ChannelService service;

    public IRCCommands() {
        super("irc");

        this.protectedChannels = new HashSet<>();
        this.protectedChannels.add("#unity-coders");
    }

    @Inject
    protected void setBot(BotService service) {
        this.bot = service;
    }

    @Inject
    protected void setUsers(UserService service) {
        this.users = service;
    }

    @Inject
    protected void setChannels(ChannelService service) {
        this.service = service;
    }


    @Command("protect")
    @Secured
    public void onProtect(Message message) {
        if (BOT_OWNER.equals(message.getUser().getNick())) {
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
    public void onUnprotect(Message message) {
        if (BOT_OWNER.equals(message.getUser().getNick())) {
            String channel = message.getArgument(2);
            protectedChannels.remove(channel);
            message.respond("unprotected channel");
            return;
        }
        message.respond("only BOT_OWNER can use this command");
    }


    @Command("join")
    @Secured
    public void onJoinRequest(Message message) {
        String channel = message.getArgument(2);
        service.join(channel);
    }

    @Command("mode")
    @Secured
    public void onMode(Message message) {
        String mode = message.getArgument(2);
        String channelName = message.getArgument(3, message.getTargetName());

        if (protectedChannels.contains(channelName)) {
            message.respond("That channel is protected.");
            return;
        }

        //WARNING: magic beyond this point
        Channel channel = service.getChannel(channelName);

        //check we're in the channel and know the user
        if (channel == null) {
            message.respond("I couldn't find that channel");
            return;
        }

        channel.send().setMode(mode);
    }

    @Command("op")
    @Secured
    public void onOp(Message message) {
        String channelName = message.getArgument(3, message.getTargetName());
        String userToOp = message.getArgument(2, message.getUser().getNick());

        if (protectedChannels.contains(channelName)) {
            message.respond("That channel is protected.");
            return;
        }

        //WARNING: magic beyond this point
        Channel channel = service.getChannel(channelName);
        User user = users.getUser(userToOp);

        //check we're in the channel and know the user
        if (user == null || channel == null) {
            message.respond("I couldn't find that user or channel");
            return;
        }

        //check we are op in the channel
        if (!channel.isOp(users.getBotUser())) {
            message.respond("I am not OP'd");
            return;
        }

        channel.send().op(user);
    }

    @Command("deop")
    @Secured
    public void onDeop(Message message) {
        String channelName = message.getArgument(3, message.getTargetName());
        String userToOp = message.getArgument(2, null);

        if (protectedChannels.contains(channelName)) {
            message.respond("That channel is protected.");
            return;
        }

        //WARNING: magic beyond this point
        Channel channel = service.getChannel(channelName);
        User user = users.getUser(userToOp);

        //check we're in the channel and know the user
        if (user == null || channel == null) {
            message.respond("I couldn't find that user or channel");
            return;
        }

        //check we are op in the channel
        if (!channel.isOp(users.getBotUser())) {
            message.respond("I am not OP'd");
            return;
        }

        channel.send().deOp(user);
    }

    @Command("voice")
    @Secured
    public void onVoice(Message message) {
        String channelName = message.getArgument(3, message.getTargetName());
        String userToOp = message.getArgument(2, null);

        if (protectedChannels.contains(channelName)) {
            message.respond("That channel is protected.");
            return;
        }

        //WARNING: magic beyond this point
        Channel channel = service.getChannel(channelName);
        User user = users.getUser(userToOp);

        //check we're in the channel and know the user
        if (user == null || channel == null) {
            message.respond("I couldn't find that user or channel");
            return;
        }

        //check we are op in the channel
        if (!channel.isOp(users.getBotUser())) {
            message.respond("I am not OP'd");
            return;
        }

        channel.send().voice(user);
    }

    @Command("devoice")
    @Secured
    public void onDevoice(Message message) {
        String channelName = message.getArgument(3, message.getTargetName());
        String userToOp = message.getArgument(2, null);

        if (protectedChannels.contains(channelName)) {
            message.respond("That channel is protected.");
            return;
        }

        //WARNING: magic beyond this point
        Channel channel = service.getChannel(channelName);
        User user = users.getUser(userToOp);

        //check we're in the channel and know the user
        if (user == null || channel == null) {
            message.respond("I couldn't find that user or channel");
            return;
        }

        //check we are op in the channel
        if (!channel.isOp(users.getBotUser())) {
            message.respond("I am not OP'd");
            return;
        }

        channel.send().deVoice(user);
    }

    @Command("kick")
    @Secured
    public void onKick(Message message) {
        String channelName = message.getArgument(3, message.getTargetName());
        String userToOp = message.getArgument(2, null);

        if (protectedChannels.contains(channelName)) {
            message.respond("That channel is protected.");
            return;
        }

        //WARNING: magic beyond this point
        Channel channel = service.getChannel(channelName);
        User user = users.getUser(userToOp);

        //check we're in the channel and know the user
        if (user == null || channel == null) {
            message.respond("I couldn't find that user or channel");
            return;
        }

        //check we are op in the channel
        if (!channel.isOp(users.getBotUser())) {
            message.respond("I am not OP'd");
            return;
        }

        channel.send().kick(user, "kicked by " + message.getUser().getNick());
    }

    @Command("topic")
    @Secured
    public void onTopic(Message message) {
        String channelName = message.getArgument(3, message.getTargetName());
        String topic = message.getArgument(2);

        if (protectedChannels.contains(channelName)) {
            message.respond("That channel is protected.");
            return;
        }

        //WARNING: magic beyond this point
        Channel channel = service.getChannel(channelName);

        //check we're in the channel and know the user
        if (channel == null) {
            message.respond("I couldn't find that channel");
            return;
        }

        //check we are op in the channel
        if (!channel.isOp(users.getBotUser())) {
            message.respond("I am not OP'd");
            return;
        }

        channel.send().setTopic(topic);
    }

    @Command("nick")
    @Secured
    public void onNickRequest(Message message) {
        String newNick = message.getArgument(2);
        bot.setName(newNick);
    }

    @Command("quit")
    @Secured
    public void onQuitRequest(Message message) {
        bot.quit(String.format("%s told me to quit", message.getUser().getNick()));
    }
}
