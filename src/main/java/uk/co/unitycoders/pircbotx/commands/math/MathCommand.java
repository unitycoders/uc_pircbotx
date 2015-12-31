package uk.co.unitycoders.pircbotx.commands.math;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;

public class MathCommand {
	private MathParser parser;
	
	public MathCommand() {
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
