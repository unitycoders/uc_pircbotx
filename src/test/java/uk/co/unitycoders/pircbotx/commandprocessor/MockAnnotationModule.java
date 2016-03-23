package uk.co.unitycoders.pircbotx.commandprocessor;

import uk.co.unitycoders.pircbotx.modules.AnnotationModule;
import uk.co.unitycoders.pircbotx.security.Secured;

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
