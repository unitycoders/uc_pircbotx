package uk.co.unitycoders.pircbotx.security;

import uk.co.unitycoders.pircbotx.commandprocessor.CommandProcessor;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.middleware.AbstractMiddleware;
import uk.co.unitycoders.pircbotx.middleware.BotMiddleware;
import uk.co.unitycoders.pircbotx.modules.Module;

public class SecurityMiddleware extends AbstractMiddleware {
	private final SecurityManager security;
	
	public SecurityMiddleware(SecurityManager security) {
		this.security = security;
	}

	@Override
	public Message process(CommandProcessor processor, Message message) {	
		//get the module the user requested
		String moduleName = message.getArgument(0, null);
		Module module = processor.getModule(moduleName);
		
		//if the module doesn't exist, give up
		if (module == null) {
			return message;
		}
		
		// find out what action the user wanted to perform
		String action = message.getArgument(1, "default");
		
		//check the user's permissions
		String[] permissions = module.getRequiredPermissions(action);
		Session session = security.getSession(message.getUser());
		
		if (session != null) {
			//if the user has a session, check if they have all the required permissions
			for (String permission : permissions) {
				if (!session.hasPermission(permission)) {
					throw new PermissionException();
				}
			}
		} else {
			//if the user doesn't have a session and we need permissions stop processing
			if (permissions.length != 0) {
				throw new NotLoggedInException();
			}
		}
		
		return message;
	}

}
