/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.unitycoders.pircbotx.commandprocessor;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author joseph
 */
public class ChannelMessage extends BasicMessage {
    private final MessageEvent<PircBotX> event;
    
    public ChannelMessage(MessageEvent<PircBotX> event, String message) {
        super(event, message);
        this.event = event;
    }
    
    @Override
    public void sendAction(String action) {
        event.getChannel().send().action(action);
    }

    @Override
    public String getTargetName() {
        return event.getChannel().getName();
    }
    
}
