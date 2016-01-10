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
package uk.co.unitycoders.pircbotx.commandprocessor.irc;

import java.util.Iterator;
import java.util.List;

import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;

import uk.co.unitycoders.pircbotx.commandprocessor.Message;

/**
 * Abstract message adapter for uc_pircbotx.
 * 
 * An abstract version of the Bot Message interface which deals with some of
 * the common features which we can deal with for the child classes.
 */
public abstract class BasicMessage implements Message {
    private final GenericMessageEvent<PircBotX> event;
    private final List<String> args;
    
    public BasicMessage(GenericMessageEvent<PircBotX> message, List<String> args) {
        this.event = message;
        this.args = args;
    }
    
    public String getArguments() {
    	if (args.size() <= 2) {
    		return "";
    	}
    	
    	// get the argument list
    	List<String> cmdArgs = args.subList(2, args.size());
    	
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
    	return args.size();
    }
    
    public void insertArgument(int pos, String arg) {
    	args.add(pos, arg);
    }
    
    public String getArgument(int id, String defaultValue) {
    	if (args == null || args.size() <= id){
    		return defaultValue;
    	}
    	
    	return args.get(id);
    }
    
    @Override
    public void respond(String response) {
        event.respond(response);
    }
    
    @Override
    public void respondSuccess() {
    	respond("The operation was successful.");
    }
    
    @Override
    public String getMessage() {
    	return this.getArguments();
    }

    @Override
    public  String getRawMessage() {
       return event.getMessage();
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
