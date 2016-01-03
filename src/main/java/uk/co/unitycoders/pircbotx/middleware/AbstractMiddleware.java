package uk.co.unitycoders.pircbotx.middleware;

import uk.co.unitycoders.pircbotx.LocalConfiguration;
import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;

/**
 * Does nothing but fulfil the middleware contract.
 * 
 * This is designed to make it easier to implement middleware.
 */
public abstract class AbstractMiddleware implements BotMiddleware {

	@Override
	public void init(LocalConfiguration config) {
		
	}
	
	@Override
	public String preprocess(String text) {
		return text;
	}

	@Override
	public Message process(CommandProcessor processor, Message message) {
		return message;
	}

}
