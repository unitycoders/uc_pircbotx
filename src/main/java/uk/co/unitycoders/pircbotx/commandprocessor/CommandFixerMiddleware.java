package uk.co.unitycoders.pircbotx.commandprocessor;

import java.util.List;

import uk.co.unitycoders.pircbotx.middleware.AbstractMiddleware;
import uk.co.unitycoders.pircbotx.modules.Module;

/**
 * Redirect non-existent actions to the module's default action.
 * If the module is missing and we can guess it insert the module name
 */
public class CommandFixerMiddleware extends AbstractMiddleware {

	@Override
	public Message process(CommandProcessor processor, Message message) {

		// get the module (or skip if no such module)
		String moduleName = message.getArgument(Module.MODULE_ARG, null);
		Module module = processor.getModule(moduleName);
		if (module == null) {

			//we couldn't repair this module, maybe it's shorthand?
			List<String> modules = processor.getReverse(moduleName);
			if (modules.size() == 1) {
				message.insertArgument(Module.MODULE_ARG, modules.get(0));
			}

			return message;
		}

		//check the message has a valid action
		String action = message.getArgument(Module.COMMAND_ARG, null);
		if (action == null || !module.isValidAction(action)) {
			message.insertArgument(1, Module.DEFAULT_COMMAND);
		}

		return message;
	}

}
