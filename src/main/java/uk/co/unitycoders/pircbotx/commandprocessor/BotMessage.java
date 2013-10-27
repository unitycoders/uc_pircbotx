/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.unitycoders.pircbotx.commandprocessor;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import uk.co.unitycoders.pircbotx.Bot;

/**
 *
 * @author joseph
 */
public interface BotMessage {
    
    public User getUser();
    public String getMessage();
    public Channel getChannel();
    
    public PircBotX getBot();
    
    public void respond(String response);
    
}
