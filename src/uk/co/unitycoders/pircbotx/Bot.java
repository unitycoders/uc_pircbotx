//TODO GPLv3
package uk.co.unitycoders.pircbotx;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.managers.ListenerManager;

import uk.co.unitycoders.pircbotx.commands.LartCommand;
import uk.co.unitycoders.pircbotx.commands.RandCommand;
import uk.co.unitycoders.pircbotx.commands.DateCommand;

public class Bot
{
	public static void main(String[] args)
	{
		PircBotX bot = new PircBotX();
		ListenerManager<PircBotX> manager = (ListenerManager<PircBotX>) bot.getListenerManager();

		manager.addListener(new DateCommand());
		manager.addListener(new RandCommand());
		manager.addListener(new LartCommand());

		try
		{
			bot.setName("uc_pircbotx");
			bot.connect("irc.freenode.net");
			bot.joinChannel("#unity-coders");
			bot.setVerbose(true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
