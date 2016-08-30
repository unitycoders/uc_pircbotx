package com.fossgalaxy.pircbotx.middleware;

import com.fossgalaxy.pircbotx.LocalConfiguration;
import com.fossgalaxy.pircbotx.commandprocessor.CommandProcessor;
import com.fossgalaxy.pircbotx.commandprocessor.Message;

/**
 * A plugin which lets developers hook into command processing.
 * <p>
 * This allows additional steps for processing commands at two key stages:
 * 1. when the message is first received
 * 2. when the message has been tokenized
 * <p>
 * At any point the plugin can stop processing of the request by throwing an exception.
 */
public interface BotMiddleware {

    /**
     * Called when plugin is loaded to initialise it.
     *
     * @param config the bot's configuration
     */
    void init(LocalConfiguration config);

    /**
     * Pre-process a message before it is tokenised.
     * <p>
     * This callback is designed for any processing which should be done
     * before the rest of the system processes the request (eg. cleaning the
     * input or changing the command the user entered).
     * <p>
     * Middleware is chained together so this method MUST return the text which
     * should be send to the next piece of middleware.
     *
     * @param text the request at present
     * @return the modified request (or text if no modification needed)
     */
    String preprocess(String text);

    /**
     * Process a parsed message.
     * <p>
     * This is processed just before the message is processed by the Module which will
     * fulfil the request, as a result it is a good place to perform security checks or
     * inject context into the message object.
     * <p>
     * Middleware is chained together so this method MUST return the text which
     * should be send to the next piece of middleware.
     *
     * @param processor the processor dealing with the request
     * @param message   the message being processed
     * @return the modified message (or message if no modification needed).
     */
    Message process(CommandProcessor processor, Message message);

}
