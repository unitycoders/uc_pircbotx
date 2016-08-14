package com.fossgalaxy.pircbotx.backends.irc;

import com.fossgalaxy.pircbotx.backends.BotService;
import com.fossgalaxy.pircbotx.backends.ChannelService;
import com.fossgalaxy.pircbotx.backends.UserService;
import com.google.inject.AbstractModule;
import org.pircbotx.PircBotX;

/**
 * Created by webpigeon on 14/08/16.
 */
public class IrcModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(PircBotX.class).toProvider(IrcService.class);
        bind(BotService.class).to(IrcService.class);
        bind(UserService.class).to(IrcUserService.class);
        bind(ChannelService.class).to(IrcChannelService.class);
    }

}
