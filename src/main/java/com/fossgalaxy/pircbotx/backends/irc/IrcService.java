package com.fossgalaxy.pircbotx.backends.irc;

import com.fossgalaxy.pircbotx.LocalConfiguration;
import com.fossgalaxy.pircbotx.backends.BackendException;
import com.fossgalaxy.pircbotx.backends.BotService;
import com.fossgalaxy.pircbotx.backends.ChannelService;
import com.fossgalaxy.pircbotx.backends.UserService;
import com.fossgalaxy.pircbotx.commandprocessor.CommandProcessor;
import com.fossgalaxy.pircbotx.commands.joins.JoinsListener;
import com.fossgalaxy.pircbotx.listeners.LinesListener;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import java.io.IOException;

/**
 *
 */
@Singleton
public class IrcService implements BotService, Provider<PircBotX> {
    private PircBotX instance;

    @Inject
    private Injector injector;

    public IrcService() {
    }

    @Override
    public void start(LocalConfiguration config, CommandProcessor processor) throws IOException, BackendException {
        //this creates our host bot instance
        Configuration.Builder cb = IRCFactory.doConfig(config, processor);

        cb.addListener(injector.getInstance(JoinsListener.class));
        cb.addListener(injector.getInstance(LinesListener.class));

        try {
            //build pircbotx
            Configuration configuration = cb.buildConfiguration();
            instance = new PircBotX(configuration);
            instance.startBot();
        } catch (IrcException ex) {
            throw new BackendException(ex);
        }
    }

    @Override
    public void stop() {
        if (instance == null) {
            return;
        }
        instance.close();
    }

    @Override
    public void quit(String quitMessage) {
        instance.stopBotReconnect();
        instance.sendIRC().quitServer(quitMessage);
    }

    @Override
    public ChannelService getChannels() {
        return new IrcChannelService(this);
    }

    @Override
    public UserService getUsers() {
        return new IrcUserService(this);
    }

    @Override
    public void setName(String newNick) {
        instance.sendIRC().changeNick(newNick);
    }

    @Override
    public PircBotX get() {
        return instance;
    }
}
