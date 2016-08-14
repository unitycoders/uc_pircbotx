package com.fossgalaxy.pircbotx.backends.irc;

import com.fossgalaxy.pircbotx.LocalConfiguration;
import com.fossgalaxy.pircbotx.backends.BotService;
import com.fossgalaxy.pircbotx.commandprocessor.CommandProcessor;
import com.fossgalaxy.pircbotx.listeners.JoinsListener;
import com.fossgalaxy.pircbotx.listeners.LinesListener;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import java.io.IOException;

/**
 *
 */
@Singleton
public class IrcService implements BotService {
    private PircBotX instance;

    @Inject
    private Injector injector;

    public IrcService() {
    }

    @Override
    public void start(LocalConfiguration config, CommandProcessor processor) throws IOException, IrcException {
        //this creates our host bot instance
        Configuration.Builder cb = IRCFactory.doConfig(config, processor);

        cb.addListener(injector.getInstance(JoinsListener.class));
        cb.addListener(injector.getInstance(LinesListener.class));

        //build pircbotx
        Configuration configuration = cb.buildConfiguration();
        instance = new PircBotX(configuration);
        instance.startBot();
    }

    @Override
    public void stop() {
        if(instance != null) {
            return;
        }
        instance.close();
    }
}
