package com.fossgalaxy.pircbotx.security;

import com.fossgalaxy.pircbotx.commandprocessor.CommandProcessor;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.middleware.AbstractMiddleware;
import com.fossgalaxy.pircbotx.modules.Module;
import com.google.inject.Inject;

public class SecurityMiddleware extends AbstractMiddleware {
	private SecurityManager security;

	@Inject
	protected void inject(SecurityManager security) {
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
