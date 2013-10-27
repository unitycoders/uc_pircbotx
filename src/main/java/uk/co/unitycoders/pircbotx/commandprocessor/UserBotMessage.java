/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.unitycoders.pircbotx.commandprocessor;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 *
 * @author joseph
 */
class UserBotMessage implements BotMessage {
    private final PrivateMessageEvent<PircBotX> origin;

    public UserBotMessage(PrivateMessageEvent<PircBotX> origin) {
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
        //private messages don't have channels...
        return null;
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
