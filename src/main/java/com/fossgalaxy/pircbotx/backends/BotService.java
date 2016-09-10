package com.fossgalaxy.pircbotx.backends;

import com.fossgalaxy.bot.api.command.Invoker;
import com.fossgalaxy.pircbotx.LocalConfiguration;
import com.fossgalaxy.pircbotx.commandprocessor.CommandProcessor;

import java.io.IOException;

/**
 * Class which deals with the running of the bot.
 * <p>
 * Created by webpigeon on 14/08/16.
 */
public interface BotService {

    void start(LocalConfiguration configuration, CommandProcessor processor) throws IOException, BackendException;
    void start(LocalConfiguration configuration, Invoker invoker) throws IOException, BackendException;

    void stop();

    void quit(String message);

    ChannelService getChannels();

    UserService getUsers();

    void setName(String newNick);
}
