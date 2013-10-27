/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.unitycoders.pircbotx.commandprocessor;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author joseph
 */
class ChannelBotMessage implements BotMessage {
    private final MessageEvent<PircBotX> origin;
    
    public ChannelBotMessage(MessageEvent<PircBotX> origin) {
        this.origin = origin;
    }

    @Override
    public User getUser() {
        return origin.getUser();
    }

    @Override
    public String getMessage() {
        return origin.getMessage();
    }

    @Override
    public Channel getChannel() {
        return origin.getChannel();
    }

    @Override
    public PircBotX getBot() {
        return origin.getBot();
    }

    @Override
    public void respond(String response) {
        origin.respond(response);
    }
    
}
