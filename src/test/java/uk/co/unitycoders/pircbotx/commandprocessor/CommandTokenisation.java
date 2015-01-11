package uk.co.unitycoders.pircbotx.commandprocessor;

import java.util.Arrays;
import java.util.Collection;

public class CommandTokenisation {

	public static void main(String[] args) {
		CommandProcessor processor = new CommandProcessor(null);
		testBasicString(processor);
		testSingleQuotedString(processor);
		testDoubleQuotedString(processor);
		testBothQuotedStrings(processor);
		testPossessiveStrings(processor);
	}
	
	public static void testBasicString(CommandProcessor processor) {
		Collection<String> expected = Arrays.asList("time", "unix");
		Collection<String> result = processor.tokenise("time unix");
		
		System.out.println(result);
		System.out.println("basic string: "+expected.equals(result));
	}

	public static void testSingleQuotedString(CommandProcessor processor) {
		Collection<String> expected = Arrays.asList("lart", "add", "slaps $who with a clue-by-four");
		Collection<String> result = processor.tokenise("lart add 'slaps $who with a clue-by-four'");
		
		System.out.println("single quoted string: "+expected.equals(result));
	}
	
	public static void testDoubleQuotedString(CommandProcessor processor) {
		Collection<String> expected = Arrays.asList("lart", "add", "slaps $who with a clue-by-four");
		Collection<String> result = processor.tokenise("lart add \"slaps $who with a clue-by-four\"");
		
		System.out.println("double quoted string: "+expected.equals(result));
	}
	
	public static void testBothQuotedStrings(CommandProcessor processor) {
		Collection<String> expected = Arrays.asList("lart", "add now", "slaps $who with a clue-by-four");
		Collection<String> result = processor.tokenise("lart 'add now' \"slaps $who with a clue-by-four\"");
		
		System.out.println("mixed quoted string: "+expected.equals(result));
	}
	
	public static void testPossessiveStrings(CommandProcessor processor) {
		Collection<String> expected = Arrays.asList("lart", "add now", "slaps $who with pigeon's clue-by-four");
		Collection<String> result = processor.tokenise("lart 'add now' \"slaps $who with pigeon's clue-by-four\"");
		
		System.out.println("posessive string: "+expected.equals(result));
	}
}
