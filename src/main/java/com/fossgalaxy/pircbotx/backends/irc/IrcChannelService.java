package com.fossgalaxy.pircbotx.backends.irc;

import com.fossgalaxy.pircbotx.backends.ChannelService;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;

/**
 * Created by webpigeon on 14/08/16.
 */
class IrcChannelService implements ChannelService {
    private Provider<PircBotX> bot;

    @Inject
    public IrcChannelService(Provider<PircBotX> bot) {
        this.bot = bot;
    }

    @Override
    public void join(String channel) {
        bot.get().sendIRC().joinChannel(channel);
    }

    @Override
    public void join(String channel, String key) {
        bot.get().sendIRC().joinChannel(channel, key);
    }

    @Override
    public Channel getChannel(String name) {
        return bot.get().getUserChannelDao().getChannel(name);
    }

    @Override
    public void leave(String channelName) {
        Channel channel = bot.get().getUserChannelDao().getChannel(channelName);
        if (channel == null) {
            return;
        }
        channel.send().part();
    }
}
