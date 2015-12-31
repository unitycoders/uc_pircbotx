package uk.co.unitycoders.pircbotx.commands.math;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.modules.AnnotationModule;

public class MathCommand extends AnnotationModule {
	private MathParser parser;
	
	public MathCommand() {
		super("math");
		this.parser = MathParser.build();
	}
	
	@Command
	public void onDefault(Message message) {
		String input = message.getArgument(2, null);
		if (input == null) {
			message.respond("usage: math [calculation]");
			return;
		}
		
		double result = parser.evalInfix(input);
		message.respond("answer is "+result);
	}

}
