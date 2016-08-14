package com.fossgalaxy.pircbotx.backends.irc;

import com.fossgalaxy.pircbotx.backends.UserService;
import com.fossgalaxy.pircbotx.profile.Profile;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.UserChannelDao;

/**
 * Created by webpigeon on 14/08/16.
 */
class IrcUserService implements UserService {
    private Provider<PircBotX> bot;

    @Inject
    public IrcUserService(Provider<PircBotX> bot) {
        this.bot = bot;
    }

    @Override
    public User getBotUser() {
        return bot.get().getUserBot();
    }

    @Override
    public User getUser(String name) {
        return bot.get().getUserChannelDao().getUser(name);
    }
}
