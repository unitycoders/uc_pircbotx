/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.unitycoders.pircbotx.commandprocessor;

import org.pircbotx.User;

/**
 * Expose a common set of methods for all message types.
 * 
 * This allows us to code modules which don't rely on being a channel message
 * or being a private message, but  simply treat both the same.
 */
public interface Message {
    
    public User getUser();
    public String getMessage();
    
    public void respond(String message);
    
}
