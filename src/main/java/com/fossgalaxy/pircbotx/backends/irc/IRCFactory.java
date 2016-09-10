package com.fossgalaxy.pircbotx.backends.irc;

import com.fossgalaxy.bot.api.command.Invoker;
import com.fossgalaxy.pircbotx.LocalConfiguration;
import com.fossgalaxy.pircbotx.commandprocessor.CommandProcessor;
import org.pircbotx.Configuration;
import org.pircbotx.cap.SASLCapHandler;

import javax.net.ssl.SSLSocketFactory;


/**
 * Build IRC bot components
 */
public class IRCFactory {

    /**
     * Map our configuration file onto PIrcBotX's config builder
     *
     * @param config    our configuration instance
     * @param processor the processor to bind to
     */
    public static Configuration.Builder doConfig(LocalConfiguration config, CommandProcessor processor) {
        Configuration.Builder cb = new Configuration.Builder();
        cb.addListener(new CommandListener(processor, config.trigger));

        return build(cb, config);
    }

    /**
     * Map our configuration file onto PIrcBotX's config builder.
     *
     * This uses the protocol independent Command API rather than the module based approach.
     *
     * @param config  our configuration instance
     * @param invoker the processor to bind to
     */
    public static Configuration.Builder doConfig(LocalConfiguration config, Invoker invoker) {
        Configuration.Builder cb = new Configuration.Builder();
        cb.addListener(new ApiCommandListener(invoker, config.trigger));

        return build(cb, config);
    }


    private static Configuration.Builder build(Configuration.Builder cb, LocalConfiguration config) {
        //build the bot
        cb.setName(config.nick);
        cb.addServer(config.host, config.port);

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
