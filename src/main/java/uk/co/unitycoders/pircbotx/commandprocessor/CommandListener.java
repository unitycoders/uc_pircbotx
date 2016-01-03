/**
 * Copyright © 2012-2015 Unity Coders
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

import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 * Command Processor wrapper class.
 *
 * This class is notified by pircbotx when the bot gets a message. It's sole
 * purpose is to act as an adapter between the command processor and pircbotx.
 */
public class CommandListener extends ListenerAdapter<PircBotX> {

	private final RewriteEngine rewriter;
    private final CommandProcessor processor;
    private final String prefix;

    public CommandListener(CommandProcessor processor, RewriteEngine rewriter, char prefix) {
        this.processor = processor;
        this.rewriter = rewriter;
        this.prefix = ""+prefix;
    }

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
    	try{
	        String messageText = event.getMessage();
	
	        if (messageText.startsWith(prefix)) {
	        	messageText = extractMessage(messageText.substring(1));
	        	
	            BasicMessage message = new ChannelMessage(event, messageText);
	            processor.invoke(message);
	        }
    	} catch (Exception ex) {
    		event.respond("error: "+ex.getMessage());
    	}
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent<PircBotX> event) throws Exception {
    	try {
    		String messageText = extractMessage(event.getMessage());
    		processor.invoke(new UserMessage(event, messageText));
    	} catch(Exception ex) {
    		event.respond("error:"+ex.getMessage());
    	}
    }
    
    private String extractMessage(String raw) {
    	String clean = Colors.removeFormattingAndColors(raw);
    	return rewriter.process(clean);
    }
}
