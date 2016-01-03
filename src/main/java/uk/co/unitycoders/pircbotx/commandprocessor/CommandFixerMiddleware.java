package uk.co.unitycoders.pircbotx.commandprocessor;

import uk.co.unitycoders.pircbotx.middleware.BotMiddleware;
import uk.co.unitycoders.pircbotx.modules.Module;

/**
 * Redirect non-existent actions to the module's default action.
 */
public class CommandFixerMiddleware implements BotMiddleware {

	@Override
	public String preprocess(String text) {
		return text;
	}

	@Override
	public Message process(CommandProcessor processor, Message message) {
		
		// get the module (or skip if no such module)
		String moduleName = message.getArgument(0, null);
		Module module = processor.getModule(moduleName);
		if (module == null) {
			return message;
		}
		
		//check the message has a valid action
		String action = message.getArgument(1, null);
		if (action == null || !module.isValidAction(action)) {
			message.insertArgument(1, Module.DEFAULT_COMMAND);
		}
		
		return message;
	}

}
