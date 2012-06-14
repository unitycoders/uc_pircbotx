/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.unitycoders.pircbotx.commandProcessor;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 */
public class CommandListener extends ListenerAdapter<PircBotX> {
    private final CommandProcessor processor;

    public CommandListener(CommandProcessor processor){
        this.processor = processor;
    }

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception{
        processor.invoke(event);
    }

}
