package uk.co.unitycoders.pircbotx.listeners;

import java.util.Map;
import java.util.TreeMap;

import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;

public class JoinsListener extends ListenerAdapter<PircBotX>
{
	private static JoinsListener instance;
	private Map<User, Integer> joins;

	private JoinsListener()
	{
		this.joins = new TreeMap<User, Integer>();
	}

	public static JoinsListener getInstance()
	{
		if (instance == null)
			instance = new JoinsListener();

		return instance;
	}

	@Override
	public void onJoin(JoinEvent<PircBotX> event) throws Exception
	{
		Integer joins = this.joins.get(event.getUser());
		if (joins == null)
			joins = 0;
		this.joins.put(event.getUser(), joins + 1);
	}

	/**
	 * Gets the {@link Map} of joins.
	 *
	 * @return the map of joins
	 */
	public Map<User, Integer> getJoins()
	{
		return joins;
	}
}
