/**
 * Copyright © 2012-2014 Unity Coders
 *
 * This file is part of uc_pircbotx.
 *
 * uc_pircbotx is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * uc_pircbotx is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * uc_pircbotx. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.unitycoders.pircbotx;

/**
 * The actual bot itself.
 *
 * @author Bruce Cowan
 */
public class Bot {

    public static void main(String[] args) throws Exception {
        String configPath = ConfigurationManager.JSON_FILE_NAME;
        if (args.length > 0) {
            configPath = args[0];
        }

        BotRunnable runnable = new BotRunnable(ConfigurationManager.loadConfig(configPath));
        Thread botThread = new Thread(runnable);

        botThread.start();
        botThread.join();
    }
}
