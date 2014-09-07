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
        botThread = new Thread(new BotRunnable());
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
