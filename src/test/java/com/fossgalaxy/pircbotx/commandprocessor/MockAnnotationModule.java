package com.fossgalaxy.pircbotx.commandprocessor;

import com.fossgalaxy.pircbotx.commandprocessor.Command;
import com.fossgalaxy.pircbotx.commandprocessor.HelpText;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.modules.AnnotationModule;
import com.fossgalaxy.pircbotx.security.Secured;

@HelpText("Test annotation module")
public class MockAnnotationModule extends AnnotationModule {

	public MockAnnotationModule() {
		super("mock");
	}

	@Command
	public void onDefault(Message message) {

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
