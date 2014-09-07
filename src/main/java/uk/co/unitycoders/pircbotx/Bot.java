/**
 * Copyright © 2012-2014 Bruce Cowan <bruce@bcowan.me.uk>
 * Copyright © 2012-2013 Joseph Walton-Rivers <webpigeon@unitycoders.co.uk>
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
package uk.co.unitycoders.pircbotx;

import javax.net.ssl.SSLSocketFactory;

import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;

import uk.co.unitycoders.pircbotx.commandprocessor.CommandListener;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.co.unitycoders.pircbotx.commands.CalcCommand;
import uk.co.unitycoders.pircbotx.commands.DateTimeCommand;
import uk.co.unitycoders.pircbotx.commands.FactoidCommand;
import uk.co.unitycoders.pircbotx.commands.HelpCommand;
import uk.co.unitycoders.pircbotx.commands.JoinsCommand;
import uk.co.unitycoders.pircbotx.commands.KarmaCommand;
import uk.co.unitycoders.pircbotx.commands.KillerTroutCommand;
import uk.co.unitycoders.pircbotx.commands.LartCommand;
import uk.co.unitycoders.pircbotx.commands.NickCommand;
import uk.co.unitycoders.pircbotx.commands.RandCommand;
import uk.co.unitycoders.pircbotx.data.db.DBConnection;
import uk.co.unitycoders.pircbotx.listeners.JoinsListener;
import uk.co.unitycoders.pircbotx.listeners.LinesListener;
import uk.co.unitycoders.pircbotx.profile.ProfileCommand;
import uk.co.unitycoders.pircbotx.profile.ProfileManager;

/**
 * The actual bot itself.
 *
 * @author Bruce Cowan
 */
public class Bot {

    public static void main(String[] args) throws Exception {
        BotRunnable runnable = new BotRunnable();
        Thread botThread = new Thread(runnable);

        botThread.start();
        botThread.join();
    }
}
