package uk.co.unitycoders.pircbotx.commands;

import java.util.HashMap;
import java.util.Map;

import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;

public class JoinsCommand extends ListenerAdapter<PircBotX>
{
	private HashMap<User, Integer> joins;

	public JoinsCommand()
	{
		this.joins = new HashMap<User, Integer>();
	}

	@Override
	public void onJoin(JoinEvent<PircBotX> event) throws Exception
	{
		Integer joins = this.joins.get(event.getUser());
		if (joins == null)
			joins = 0;
		this.joins.put(event.getUser(), joins + 1);
	}

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception
	{
		if (event.getMessage().startsWith("!joins"))
		{
			StringBuilder builder = new StringBuilder();

			for (Map.Entry<User, Integer> entry : this.joins.entrySet())
			{
				String nick = entry.getKey().getNick();
				String value = entry.getValue().toString();
				builder.append(nick + " = " + value + "; ");
			}

			//TODO chop off end semicolon
			event.respond(builder.toString());
		}
	}
}
