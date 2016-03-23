package uk.co.unitycoders.pircbotx.commandprocessor.irc;

import javax.net.ssl.SSLSocketFactory;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.SASLCapHandler;

import uk.co.unitycoders.pircbotx.LocalConfiguration;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;

/**
 * Build IRC bot components
 *
 */
public class IRCFactory {

	/**
	 * Map our configuration file onto PIrcBotX's config builder
	 *
<<<<<<< HEAD
	 * @param cb PircBotX config builder
	 * @param config our configuration instance
=======
	 * @param config our configuration instance
	 * @param processor the processor to bind to
>>>>>>> master
	 */
	public static Configuration.Builder<PircBotX> doConfig(LocalConfiguration config, CommandProcessor processor) {
		Configuration.Builder<PircBotX> cb = new Configuration.Builder<PircBotX>();
		cb.addListener(new CommandListener(processor, config.trigger));

		//build the bot
		cb.setName(config.nick);
		cb.setServer(config.host, config.port);

		if (config.sasl) {
			cb.setCapEnabled(true);
			cb.addCapHandler(new SASLCapHandler(config.username, config.password));
		}

		if (config.ssl) {
			cb.setSocketFactory(SSLSocketFactory.getDefault());
		}

		// setup automatic channel joins
		for (String channel : config.channels) {
			cb.addAutoJoinChannel(channel);
		}

		//Useful stuff to keep the bot running
		cb.setAutoNickChange(true);
		cb.setAutoReconnect(true);

		return cb;
	}

}
