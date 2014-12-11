package uk.co.unitycoders.pircbotx;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;

/**
 * Created by webpigeon on 07/09/14.
 */
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
