package com.fossgalaxy.pircbotx.commandprocessor;

import com.fossgalaxy.pircbotx.commandprocessor.Command;
import com.fossgalaxy.pircbotx.commandprocessor.HelpText;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.modules.AnnotationModule;
import com.fossgalaxy.pircbotx.security.Secured;

@HelpText("Test annotation module")
public class MockAnnotationModuleThrowsException extends AnnotationModule {

	public MockAnnotationModuleThrowsException() {
		super("mock");
	}

	@Command
	public void onDefault(Message message) {
		throw new IllegalArgumentException("Whoops!");
	}

	@Command("helpText")
	@HelpText("helpText on Command")
	public void onHelpText(Message message) {

	}

	@Command("secured")
	@Secured
	public void onSecured(Message message) {

	}

	@Command("invalid")
	protected void notAValidCommand(Message message) {

	}


	public void notACommand() {

	}
}
