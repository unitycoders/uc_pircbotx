package uk.co.unitycoders.pircbotx.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class KillerTroutCommand extends ListenerAdapter<PircBotX>
{
	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception
	{
		if (event.getMessage().startsWith("!killertrout"))
		{
			String trout = event.getBot().getNick() + " has been killed by a trout";
			event.getBot().sendMessage(event.getChannel(), trout);
			event.getBot().shutdown();
		}
	}
}
