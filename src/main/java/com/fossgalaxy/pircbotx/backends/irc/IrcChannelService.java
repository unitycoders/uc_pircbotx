package com.fossgalaxy.pircbotx.backends.irc;

import com.fossgalaxy.pircbotx.backends.ChannelService;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.UserChannelDao;

/**
 * Created by webpigeon on 14/08/16.
 */
class IrcChannelService implements ChannelService {
    private PircBotX bot;
    private UserChannelDao channelDao;

    public IrcChannelService(PircBotX bot) {
        this.bot = bot;
        this.channelDao = bot.getUserChannelDao();
    }

    @Override
    public void join(String channel) {
        bot.sendIRC().joinChannel(channel);
    }

    @Override
    public void join(String channel, String key) {
        bot.sendIRC().joinChannel(channel, key);
    }

    @Override
    public Channel getChannel(String name) {
        return channelDao.getChannel(name);
    }

    @Override
    public void leave(String channelName) {
        Channel channel = channelDao.getChannel(channelName);
        if (channel == null) {
            return;
        }
        channel.send().part();
    }
}
