package com.fossgalaxy.pircbotx.commandprocessor;

import java.util.Collection;
import java.util.Collections;

import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.modules.Module;
import com.fossgalaxy.pircbotx.modules.ModuleException;

public class ModuleWhichThrowsExceptions implements Module {

	@Override
	public void fire(Message message) throws ModuleException {
		throw new ModuleException(new NullPointerException("TEST"));
	}

	@Override
	public boolean isValidAction(String action) {
		return true;
	}

	@Override
	public String[] getRequiredPermissions(String action) {
		return new String[0];
	}

	@Override
	public Collection<String> getActions() {
		return Collections.emptyList();
	}

	@Override
	public String getName() {
		return "testException";
	}

	@Override
	public String getHelp(String command) {
		return "";
	}

	@Override
	public String getModuleHelp() {
		return "";
	}

	@Override
	public String[] getArgumentsFor(String action) {
		return new String[0];
	}

}
