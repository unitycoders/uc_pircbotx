package com.fossgalaxy.pircbotx.backends.irc;

import com.fossgalaxy.pircbotx.backends.UserService;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.UserChannelDao;

/**
 * Created by webpigeon on 14/08/16.
 */
class IrcUserService implements UserService {
    private PircBotX bot;
    private UserChannelDao userDao;

    public IrcUserService(PircBotX bot) {
        this.bot = bot;
        this.userDao = bot.getUserChannelDao();
    }

    @Override
    public User getBotUser() {
        return bot.getUserBot();
    }

    @Override
    public User getUser(String name) {
        return userDao.getUser(name);
    }
}
