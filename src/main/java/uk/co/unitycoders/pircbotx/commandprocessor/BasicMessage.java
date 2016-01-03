/**
 * Copyright © 2013-2015 Unity Coders
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
package uk.co.unitycoders.pircbotx.commandprocessor;

import java.util.Iterator;
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
    private final String text;
    private List<String> arguments;
    
    public BasicMessage(GenericMessageEvent<PircBotX> message) {
        this.event = message;
        this.text = Colors.removeFormattingAndColors(event.getMessage());
    }
    
    public BasicMessage(GenericMessageEvent<PircBotX> message, String text) {
        this.event = message;
        this.text = text;
    }
    
    public void setArguments(List<String> arguments) {
    	this.arguments = arguments;
    }
    
    public String getArguments() {
    	if (arguments.size() <= 2) {
    		return "";
    	}
    	
    	// get the argument list
    	List<String> cmdArgs = arguments.subList(2, arguments.size());
    	
    	//emulate String.join in java 1.7
    	StringBuilder argStr = new StringBuilder();
    	Iterator<String> argItr = cmdArgs.iterator();
    	while(argItr.hasNext()) {
    		argStr.append(argItr.next());
    		if (argItr.hasNext()) {
    			argStr.append(" ");
    		}
    	}
    	
    	return argStr.toString();
    }
    
    public int getArgumentCount() {
    	return arguments.size();
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
       return text;
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
