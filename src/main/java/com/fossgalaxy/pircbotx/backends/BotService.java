package com.fossgalaxy.pircbotx.backends;

import com.fossgalaxy.pircbotx.LocalConfiguration;
import com.fossgalaxy.pircbotx.commandprocessor.CommandProcessor;

/**
 * Class which deals with the running of the bot.
 *
 * Created by webpigeon on 14/08/16.
 */
public interface BotService {

    void start(LocalConfiguration configuration, CommandProcessor processor) throws Exception;
    void stop();

    void quit(String message);

    ChannelService getChannels();
    UserService getUsers();

    void setName(String newNick);
}
