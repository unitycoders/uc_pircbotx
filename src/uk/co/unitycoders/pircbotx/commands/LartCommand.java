//TODO GPLv3
package uk.co.unitycoders.pircbotx.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Insults the 1st argument
 * TODO add, remove and list insults 
 *
 * @author Bruce Cowan
 */
public class LartCommand extends ListenerAdapter<PircBotX>
{
	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception
	{
		String msg = event.getMessage();

		if (msg.startsWith("!lart"))
		{
			String[] split = msg.split(" ");
			if (split.length == 2)
			{
				String insult = "slaps " + split[1] + " with a wet trout";
				event.getBot().sendAction(event.getChannel(), insult);
			}
			else
				event.respond("No-one to insult");
		}
	}
}
