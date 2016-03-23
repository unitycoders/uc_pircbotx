/**
 * Copyright © 2015 Unity Coders
 *
 * This file is part of uc_pircbotx.
 *
 * uc_pircbotx is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * uc_pircbotx is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * uc_pircbotx. If not, see <http://www.gnu.org/licenses/>.
 */
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
