/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.unitycoders.pircbotx.commandprocessor;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 *
 * @author joseph
 */
public class UserMessage extends BasicMessage {
    private final PrivateMessageEvent<PircBotX> event;
    
    public UserMessage(PrivateMessageEvent<PircBotX> event) {
        super(event);
        this.event = event;
    }

    @Override
    public void sendAction(String action) {
        event.getUser().send().action(action);
    }

    @Override
    public String getTargetName() {
        return event.getUser().getNick();
    }
    
    
    
}
