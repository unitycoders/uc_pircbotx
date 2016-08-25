/**
 * Copyright © 2013-2015 Unity Coders
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
package com.fossgalaxy.pircbotx.commandprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.fossgalaxy.pircbotx.modules.ModuleException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fossgalaxy.pircbotx.commandprocessor.CommandFixerMiddleware;
import com.fossgalaxy.pircbotx.commandprocessor.CommandNotFoundException;
import com.fossgalaxy.pircbotx.commandprocessor.CommandProcessor;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.middleware.BotMiddleware;
import com.fossgalaxy.pircbotx.modules.Module;
import com.fossgalaxy.pircbotx.modules.ModuleUtils;

public class CommandProcessorTest {

	private CommandProcessor processor;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		List<BotMiddleware> middleware = new ArrayList<BotMiddleware>();
		middleware.add(new CommandFixerMiddleware());

		processor = new CommandProcessor(middleware);
	}

	/**
	 * Check that if the module list is empty, the getModules method returns an
	 * empty array.
	 */
	@Test
	public void testEmptyModules() {
		Collection<String> expected = new LinkedList<String>();
		Collection<String> result = processor.getModules();

		Assert.assertTrue(hasTheSameContents(result, expected));
	}

	/**
	 * Check that if an invalid module is passed to the getCommands list, that
	 * an empty array is returned.
	 */
	@Test
	public void testInvalidModuleCommands() {
		String fakeModuleName = "fakemodule";
		Collection<String> expected = new LinkedList<String>();
		Collection<String> result = processor.getCommands(fakeModuleName);

		Assert.assertTrue(hasTheSameContents(result, expected));
	}

	/**
	 * Check that if a module is registered then all module commands show up
	 */
	@Test
	public void testModuleExists() {
		String name = "fake";
		Module module = ModuleUtils.wrap(name, new FakeModule());
		processor.register(name, module);

		Collection<String> expected = new ArrayList<String>();
		expected.add(name);

		Collection<String> result = processor.getModules();

		Assert.assertTrue(hasTheSameContents(result, expected));
	}

	/**
	 * Check that if a module is registered then all module commands show up
	 */
	@Test
	public void testCommandsExists() {
		String name = "fake";
		Module module = ModuleUtils.wrap(name, new FakeModule());
		processor.register(name, module);

		Collection<String> expected = new ArrayList<String>();
		expected.add("default");
		expected.add("goodbye");
		expected.add("bye");
		expected.add("hello");
		expected.add("exception");

		Collection<String> result = processor.getCommands(name);

		Assert.assertTrue(hasTheSameContents(result, expected));
	}

	@Test
	public void testCommandsRemovalWorks() {
		String name = "fake";
		Module module = ModuleUtils.wrap(name, new FakeModule());
		processor.register(name, module);

		Module result = processor.getModule(name);
		Assert.assertEquals(module, result);

		processor.remove(name);
		Assert.assertNull(processor.getModule(name));
	}

	@Test
	public void testDefaultAction() throws Exception {
		String name = "fake";
		Module module = ModuleUtils.wrap(name, new FakeModule());
		processor.register(name, module);

		Message message = new MessageStub("fake", "default");
		processor.invoke(message);
	}

	@Test
	public void testDefaultActionWithArguments() throws Exception {
		String name = "fake";
		Module module = ModuleUtils.wrap(name, new FakeModule());
		processor.register(name, module);

		Message message = new MessageStub("fake", "banana");
		processor.invoke(message);
	}

	@Test
	public void testGetModuleNull() {
		String moduleName = null;
		Module result = processor.getModule(moduleName);
		Assert.assertNull(result);
	}

	@Test(expected=ModuleException.class)
	public void testCommandThrowsException() throws Exception {
		String name = "fake";
		Module module = new ModuleWhichThrowsExceptions();
		processor.register(name, module);

		Message message = new MessageStub("fake", "notFound");
		processor.invoke(message);
	}

	@Test(expected=CommandNotFoundException.class)
	public void testCommandNotFound() throws Exception {
		String name = "fake";
		Module module = new ModuleWhichReportsNoCommands();
		processor.register(name, module);

		Message message = new MessageStub("fake", "notAValidCommand");
		processor.invoke(message);
	}

	@Test
	public void testNullModule() throws Exception {
		String name = null;

		Collection<String> expected = Collections.emptyList();
		Collection<String> result = processor.getCommands(name);

		Assert.assertTrue(hasTheSameContents(expected, result));
	}

	@Test(expected=CommandNotFoundException.class)
	public void testInvokeInvalidModule() throws Exception {
		Message message = new MessageStub("invalidModule");
		processor.invoke(message);
	}

	@Test
	public void testDefaultCommand() throws Exception {
		String name = "fake";
		Module module = ModuleUtils.wrap(name, new FakeModule());
		processor.register(name, module);

		Message message = new MessageStub(name);
		processor.invoke(message);
	}

	@Test
	public void testCommandsNotExists() {
		String name = "doesNotExist";

		Collection<String> expected = Collections.emptyList();
		Collection<String> result = processor.getCommands(name);

		Assert.assertTrue(hasTheSameContents(result, expected));
	}

	@Test
	public void testAliasValidCommand() {
		String existingCommand = "test";
		String commandAlias = "test2";

		//setup
		Module module = ModuleUtils.wrap(existingCommand, new FakeModule());
		processor.register(existingCommand, module);

		//test
		processor.alias(commandAlias, existingCommand);
		Module result = processor.getModule(commandAlias);

		Assert.assertEquals(module, result);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAliasDefinedCommand() {
		String existingCommand = "test";
		String commandAlias = "test";

		//setup
		Module module = ModuleUtils.wrap(existingCommand, new FakeModule());
		processor.register(existingCommand, module);

		//test
		processor.alias(commandAlias, existingCommand);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAliasInvalidCommand() {
		String existingCommand = "notValidCommand";
		String commandAlias = "test2";

		//test
		processor.alias(commandAlias, existingCommand);
	}

	@Test
	public void testTokensNoQuote() {
		String command = "a b c";
		List<String> tokens = Arrays.asList("a", "b", "c");
		List<String> result = processor.tokenise(command);

		Assert.assertEquals(tokens, result);
	}

	@Test
	public void testTokensDoubleQuote() {
		String command = "a \"b c\"";
		List<String> tokens = Arrays.asList("a", "b c");
		List<String> result = processor.tokenise(command);

		Assert.assertEquals(tokens, result);
	}

	@Test
	public void testTokensSingleQuote() {
		String command = "a 'd e'";
		List<String> tokens = Arrays.asList("a", "d e");
		List<String> result = processor.tokenise(command);

		Assert.assertEquals(tokens, result);
	}

	@Test
	public void testTokensPrecedingWhitespace() {
		String command = "    a";
		List<String> tokens = Arrays.asList("a");
		List<String> result = processor.tokenise(command);

		Assert.assertEquals(tokens, result);
	}

	@Test
	public void testTokensInWhitespace() {
		String command = "a    b";
		List<String> tokens = Arrays.asList("a", "b");
		List<String> result = processor.tokenise(command);

		Assert.assertEquals(tokens, result);
	}

	@Test
	public void testTokensPostfixWhitespace() {
		String command = "a     ";
		List<String> tokens = Arrays.asList("a");
		List<String> result = processor.tokenise(command);

		Assert.assertEquals(tokens, result);
	}

	@Test
	public void testTokensWhitespace() {
		String command = "    ";
		List<String> tokens = Arrays.asList();
		List<String> result = processor.tokenise(command);

		Assert.assertEquals(tokens, result);
	}


	private static <T> boolean hasTheSameContents(Collection<T> c1, Collection<T> c2) {
		return c1.containsAll(c2) && c2.containsAll(c1);
	}

}
