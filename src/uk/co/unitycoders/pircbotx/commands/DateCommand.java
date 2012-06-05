//TODO GPLv3
package uk.co.unitycoders.pircbotx.commands;

import java.util.Date;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Gives the unformatted time
 * TODO make it formatted
 *
 * @author Bruce Cowan
 */
public class DateCommand extends ListenerAdapter<PircBotX>
{
	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception
	{
		if (event.getMessage().equals("!time"))
			event.respond("The current time is " + new Date());
	}
}
