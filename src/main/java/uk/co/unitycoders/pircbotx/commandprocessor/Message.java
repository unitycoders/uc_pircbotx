/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.unitycoders.pircbotx.commandprocessor;

import java.util.List;

import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * Expose a common set of methods for all message types.
 * 
 * This allows us to code modules which don't rely on being a channel message
 * or being a private message, but  simply treat both the same.
 */
public interface Message {
    
    public PircBotX getBot();
    public User getUser();
    public String getMessage();
    
    public void setArguments(List<String> args);
    public String getArgument(int id, String defaultValue);
    public String getTargetName();
    
    public void respond(String message);
    public void sendAction(String action);
}
