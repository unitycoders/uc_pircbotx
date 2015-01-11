/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.unitycoders.pircbotx.commandprocessor;

import java.util.List;

import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;

/**
 * Abstract message adapter for uc_pircbotx.
 * 
 * An abstract version of the Bot Message interface which deals with some of
 * the common features which we can deal with for the child classes.
 */
public abstract class BasicMessage implements Message {
    private final GenericMessageEvent<PircBotX> event;
    private List<String> arguments;
    
    public BasicMessage(GenericMessageEvent<PircBotX> message) {
        this.event = message;
    }
    
    public void setArguments(List<String> arguments) {
    	this.arguments = arguments;
    }
    
    public String getArgument(int id, String defaultValue) {
    	if (arguments == null || arguments.size() <= id){
    		return defaultValue;
    	}
    	
    	return arguments.get(id);
    }
    
    @Override
    public void respond(String response) {
        event.respond(response);
    }

    @Override
    public  String getMessage() {
       return Colors.removeFormattingAndColors(event.getMessage());
    }

    @Override
    public User getUser() {
        return event.getUser();
    }

    @Override
    public PircBotX getBot() {
        return event.getBot();
    }
    
}
