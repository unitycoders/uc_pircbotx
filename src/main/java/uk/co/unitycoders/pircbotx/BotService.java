/**
 * Copyright © 2014 Unity Coders
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

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;

public class BotService implements Daemon {
    private Thread botThread;

    @Override
    public void init(DaemonContext daemonContext) throws Exception {
        String[] args = daemonContext.getArguments();
        String configPath = ConfigurationManager.JSON_FILE_NAME;
        if (args.length != 0) {
            configPath = args[0];
        }

        botThread = new Thread(new BotRunnable(ConfigurationManager.loadConfig(configPath)));
    }

    @Override
    public void start() throws Exception {
        botThread.start();
    }

    @Override
    public void stop() throws Exception {
        botThread.interrupt();
    }

    @Override
    public void destroy() {
        botThread = null;
    }
}
