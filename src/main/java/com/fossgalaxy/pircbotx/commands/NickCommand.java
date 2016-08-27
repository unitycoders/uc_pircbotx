/**
 * Copyright © 2013-2014 Unity Coders
 * <p>
 * This file is part of uc_pircbotx.
 * <p>
 * uc_pircbotx is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p>
 * uc_pircbotx is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * uc_pircbotx. If not, see <http://www.gnu.org/licenses/>.
 */
package com.fossgalaxy.pircbotx.commands;

import com.fossgalaxy.pircbotx.backends.BotService;
import com.fossgalaxy.pircbotx.commandprocessor.Command;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.modules.AnnotationModule;
import com.fossgalaxy.pircbotx.security.Secured;
import com.google.inject.Inject;


/**
 * @author Bruce Cowan
 */
public class NickCommand extends AnnotationModule {
    private BotService bot;

    public NickCommand() {
        super("nick");
    }

    @Inject
    protected void inject(BotService bot) {
        this.bot = bot;
    }

    @Command
    @Secured
    public void onNick(Message event) {
        String nick = event.getArgument(2);

        bot.setName(nick);
        event.respond("Changed nick to " + nick);
    }
}
