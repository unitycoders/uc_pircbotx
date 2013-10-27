/**
 * Copyright © 2012 Bruce Cowan <bruce@bcowan.me.uk>
 *
 * This file is part of uc_PircBotX.
 *
 * uc_PircBotX is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * uc_PircBotX is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * uc_PircBotX. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.unitycoders.pircbotx.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;
import uk.co.unitycoders.pircbotx.commandprocessor.BotMessage;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;

/**
 * Kills the bot. The command name is an in-joke.
 *
 * @author Bruce Cowan
 */
public class KillerTroutCommand {

    @Command
    public void onTrout(BotMessage event) throws Exception {
        event.respond("Bot has been killed by a trout");
        event.getBot().shutdown();
    }
}
