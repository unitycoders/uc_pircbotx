package uk.co.unitycoders.pircbotx.commands;

import java.sql.SQLException;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.data.db.DBConnection;
import uk.co.unitycoders.pircbotx.data.db.KarmaModel;

public class KarmaCommand
{
	private KarmaModel model;

	public KarmaCommand()
	{
		try
		{
			this.model = DBConnection.getKarmaModel();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Command(keyword="default")
	public void onKarma(MessageEvent<PircBotX> event) throws Exception
	{
		String target = event.getMessage().split(" ")[1];

		int karma = this.model.getKarma(target);
		event.respond("Karma for " + target + " = " + karma);
	}

	@Command(keyword="increment")
	public void incrementKarma(MessageEvent<PircBotX> event) throws Exception
	{
		String target = event.getMessage().split(" ")[2];

		try
		{
			int karma = this.model.incrementKarma(target);
			event.respond("Karma for " + target + " is now " + karma);
		} catch (SQLException ex)
		{
			event.respond("Database error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Command(keyword="decrement")
	public void decrementKarma(MessageEvent<PircBotX> event) throws Exception
	{
		String target = event.getMessage().split(" ")[2];

		try
		{
			int karma = this.model.decrementKarma(target);
			event.respond("Karma for " + target + " is now " + karma);
		} catch (SQLException ex)
		{
			event.respond("Database error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
