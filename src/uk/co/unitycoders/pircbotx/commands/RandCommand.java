//TODO GPLv3
package uk.co.unitycoders.pircbotx.commands;

import java.util.ArrayList;
import java.util.Random;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Keeps a log of all the lines said, and randomly speaks one
 *
 * @author Bruce Cowan
 */
public class RandCommand extends ListenerAdapter<PircBotX>
{
	private ArrayList<String> lines;
	private Random random;

	public RandCommand()
	{
		this.lines = new ArrayList<String>();
		this.random = new Random();
	}

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception
	{
		String msg = event.getMessage();

		if (msg.equals("!time"))
		{
			int size = this.lines.size();
			int index = this.random.nextInt(size - 1);
			event.respond(this.lines.get(index));
		}
	}
}
