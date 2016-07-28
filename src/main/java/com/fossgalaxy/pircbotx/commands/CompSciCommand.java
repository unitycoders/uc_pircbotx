package com.fossgalaxy.pircbotx.commands;

import java.util.ArrayList;
import java.util.List;

import com.fossgalaxy.pircbotx.commandprocessor.Command;
import com.fossgalaxy.pircbotx.commandprocessor.HelpText;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.commandprocessor.MessageUtils;
import com.fossgalaxy.pircbotx.modules.AnnotationModule;

@HelpText("Commands for bit manipulation")
public class CompSciCommand extends AnnotationModule {

	public CompSciCommand() {
		super("compsci");
	}

	@Command({"xor","b^"})
	@HelpText("bitwise xor two binary numbers")
	public void onXor(Message message) {
		Integer a = Integer.parseInt(message.getArgument(2, null), 2);
		Integer b = Integer.parseInt(message.getArgument(3, null), 2);

		message.respond(""+Integer.toBinaryString(a^b));
	}

	@Command({"and", "b&"})
	@HelpText("bitwise and two binary numbers")
	public void onAnd(Message message) {
		Integer a = Integer.parseInt(message.getArgument(2, null), 2);
		Integer b = Integer.parseInt(message.getArgument(3, null), 2);

		message.respond(""+Integer.toBinaryString(a&b));
	}

	@Command({"or", "b|"})
	@HelpText("bitwise or two binary numbers")
	public void onOr(Message message) {
		Integer a = Integer.parseInt(message.getArgument(2, null), 2);
		Integer b = Integer.parseInt(message.getArgument(3, null), 2);

		message.respond(""+Integer.toBinaryString(a|b));
	}

	@Command({"invert", "b~"})
	@HelpText("bitwise compliement operator on a binary number")
	public void onInvert(Message message) {
		Integer a = Integer.parseInt(message.getArgument(2, null), 2);

		message.respond(""+Integer.toBinaryString(~a));
	}

	@Command("dec")
	@HelpText("Convert a number into base 10")
	public void onDec(Message message) {
		String number = message.getArgument(2, null);
		Integer base = Integer.parseInt(message.getArgument(3, null));

		Integer result = Integer.parseInt(number, base);
		message.respond(""+result);
	}

	@Command("hex")
	@HelpText("convert a number to hexidemical")
	public void onHex(Message message) {
		String number = message.getArgument(2, null);
		Integer base = Integer.parseInt(message.getArgument(3, "10"));

		Integer n = Integer.parseInt(number, base);
		message.respond(Integer.toHexString(n));
	}

	@Command("bin")
	@HelpText("convert a number to binary")
	public void onBin(Message message) {
		String number = message.getArgument(2, null);
		Integer base = Integer.parseInt(message.getArgument(3, "10"));
		Integer n = Integer.parseInt(number, base);
		message.respond(Integer.toBinaryString(n));
	}

	@Command("oct")
	@HelpText("convert a number to octal")
	public void onOct(Message message) {
		String number = message.getArgument(2, null);
		Integer base = Integer.parseInt(message.getArgument(3, "10"));
		Integer n = Integer.parseInt(number, base);
		message.respond(Integer.toOctalString(n));
	}

	@Command("ascii")
	@HelpText("Get ascii value of a char")
	public void onAscii(Message message) {
		String text = message.getArgument(2, null);
		int chars = text.length();

		List<Integer> values = new ArrayList<Integer>();
		for (int i=0; i<chars; i++) {
			values.add((int)text.charAt(i));
		}

		String asciiVals = MessageUtils.buildList(values);

		message.respond("Ascii value(s): "+asciiVals);
	}

}
