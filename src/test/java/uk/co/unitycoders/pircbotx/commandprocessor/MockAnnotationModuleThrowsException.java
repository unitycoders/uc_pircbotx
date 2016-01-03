package uk.co.unitycoders.pircbotx.commandprocessor;

import uk.co.unitycoders.pircbotx.modules.AnnotationModule;
import uk.co.unitycoders.pircbotx.security.Secured;

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
