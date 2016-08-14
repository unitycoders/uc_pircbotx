package com.fossgalaxy.pircbotx.backends;

import org.pircbotx.Channel;

/**
 * Created by webpigeon on 14/08/16.
 */
public interface ChannelService {

    void join(String channel);
    void join(String channel, String key);

    Channel getChannel(String name);

    void leave(String channel);

}
