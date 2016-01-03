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
    
	/**
	 * Get the bot this message was sent to.
	 * 
	 * @return the instance of the bot which received the message
	 */
    public PircBotX getBot();
    
    /**
     * Get the user which sent this message.
     * 
     * @return the user which sent the message
     */
    public User getUser();
    
    /**
     * Get the message the bot received.
     * 
     * This is mostly of use for the parsing function but may be useful
     * to some commands. Previously, this was the only way to access message
     * contents. Formatting will have been removed to make processing easier.
     * The trigger character (if present) will have also been removed.
     * 
     * @return the cleaned message
     */
    public String getMessage();
    
    /**
     * Internal use only, do not use.
     * 
     * This is used internally to setup the arguments. It is part of the
     * private API and should not be used by plugins.
     * 
     * @param args the tokenised arguments.
     */
    public void setArguments(List<String> args);
    
    /**
     * Get a tokenised argument.
     * 
     * The tokeniser will parse the message, extract quoted strings and then split
     * by whitespace. Using this function you can extract arguments from a message.
     * The zeroth argument is always the command name, the first is always the 
     * subcommand and 2 onwards is always the arguments.
     * 
     * In the event the user calls a command with an invalid subcommand, default
     * will be inserted as the subcommand.
     * 
     * @param id the argument ID
     * @param defaultValue a value to return of the argument does not exist
     * @return the argument at position id, else defaultValue
     */
    public String getArgument(int id, String defaultValue);
    
    /**
     * Gets the target of the message.
     * 
     * If this is a channel message, this is the name of the channel. If this is
     * a private message, this is the name of the user which sent the message.
     * 
     * @return a channel name or user name
     */
    public String getTargetName();
    
    /**
     * Send a response to the user.
     * 
     * This will send a response to the user highlighting them. If the message is a 
     * channel message, this will respond in the channel, if this message was a
     * private message the bot will respond in a private message.
     * 
     * @param message the message to send
     */
    public void respond(String message);
    
    /**
     * Send an action to the user.
     * 
     * This will use /me to send an action to the target. If the message is a 
     * channel message, this will respond in the channel, if this message was a
     * private message the bot will respond in a private message.
     * 
     * @param action the action to perform
     */
    public void sendAction(String action);
}
