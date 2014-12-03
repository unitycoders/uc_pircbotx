package uk.co.unitycoders.pircbotx;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandListener;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.co.unitycoders.pircbotx.commands.*;
import uk.co.unitycoders.pircbotx.data.db.DBConnection;
import uk.co.unitycoders.pircbotx.listeners.JoinsListener;
import uk.co.unitycoders.pircbotx.listeners.LinesListener;
import uk.co.unitycoders.pircbotx.security.*;
import uk.co.unitycoders.pircbotx.security.SecurityManager;

import javax.net.ssl.SSLSocketFactory;

/**
 * Created by webpigeon on 07/09/14.
 */
public class BotRunnable implements Runnable {
    private PircBotX instance;
    private CommandProcessor processor;

    @Override
    public void run() {

        try {
            Configuration.Builder<PircBotX> cb = new Configuration.Builder<PircBotX>();
            LocalConfiguration config = ConfigurationManager.loadConfig();

            SecurityManager security = new SecurityManager();
            processor = buildProcessor(config.trigger, security, cb);

            DateTimeCommand dtCmd = new DateTimeCommand();

            processor.register("rand", new RandCommand());
            processor.register("time", dtCmd);
            processor.register("date", dtCmd);
            processor.register("datetime", dtCmd);
            processor.register("lart", new LartCommand());
            processor.register("killertrout", new KillerTroutCommand());
            processor.register("joins", new JoinsCommand());
            processor.register("calc", new CalcCommand());
            processor.register("karma", new KarmaCommand());
            processor.register("help", new HelpCommand(processor));
            processor.register("nick", new NickCommand());
            processor.register("factoid", new FactoidCommand(DBConnection.getFactoidModel()));
            processor.register("session", new SessionCommand(security));

            cb.addListener(JoinsListener.getInstance());
            cb.addListener(new LinesListener());

            buildBot(cb, config);
            instance = createBot(cb);
            instance.startBot();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /**
     * Map our configuration file onto PIrcBotX's config builder
     *
     * @param cb PircBotX config builder
     * @param config our configuration instance
     */
    private void buildBot(Configuration.Builder<PircBotX> cb, LocalConfiguration config) {
        cb.setName(config.nick);
        cb.setServer(config.host, config.port);

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
    }

    /**
     * Create the IRC bot from a complete PircBotX configuration
     * @param cb the bot configuration
     * @return a PircBotX instance
     */
    private PircBotX createBot(Configuration.Builder<PircBotX> cb) {
        Configuration<PircBotX> configuration = cb.buildConfiguration();
        return new PircBotX(configuration);
    }

    /**
     * Create a CommandProcessor Instance and return it.
     *
     * This method builds a CommandProcessor instance which can be used to register commands with the bot. This will
     * also register the command processor with the bot's configuration so it gets called when people send messages to
     * the irc bot.
     *
     * @param trigger the trigger charicter the bot will respond to
     * @param cb PircBotX configuration
     * @return A constructed CommandProcessor instance
     */
    private CommandProcessor buildProcessor(char trigger, SecurityManager s, Configuration.Builder<PircBotX> cb) {
        CommandProcessor processor = new CommandProcessor(trigger, s);
        cb.addListener(new CommandListener(processor));
        return processor;
    }
}
