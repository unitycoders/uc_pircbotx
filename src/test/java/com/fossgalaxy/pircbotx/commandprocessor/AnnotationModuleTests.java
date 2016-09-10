package com.fossgalaxy.pircbotx.commandprocessor;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fossgalaxy.pircbotx.commandprocessor.CommandNotFoundException;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.modules.AnnotationModule;
import com.fossgalaxy.pircbotx.modules.Module;

public class AnnotationModuleTests {
	private static final String INVALID_COMMAND = "banana";
	private AnnotationModule object;

	@Before
	public void setup() {
		this.object = new MockAnnotationModule();
	}

	@Test
	public void testModuleHelpText() {
		String expected = "Test annotation module";
		String result = object.getModuleHelp();
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testModuleNoHelpText() {
		AnnotationModule module = new MockAnnotationModuleNoHelpText();

		String result = module.getModuleHelp();
		Assert.assertNull(result);
	}

	@Test
	public void testFireDefaultCommand() throws Exception {
		Message message = new MessageStub("mock");
		object.fire(message);
	}

	@Test
	public void testFireNonDefaultCommand() throws Exception {
		Message message = new MessageStub("mock", "helpText");
		object.fire(message);
	}

	@Test
	public void testToString() throws Exception {
		String expected = "mock";
		String result = object.toString();
		Assert.assertEquals(expected, result);
	}



	@Test(expected=IllegalArgumentException.class)
	public void testFireCommandException() throws Exception {
		Message message = new MessageStub("mock");
		Module m = new MockAnnotationModuleThrowsException();
		m.fire(message);
	}


	@Test(expected=CommandNotFoundException.class)
	public void testFireInvalidCommand() throws Exception {
		Message message = new MessageStub("mock", INVALID_COMMAND);
		object.fire(message);
	}

	@Test
	public void testCommandHelpText() {
		String command = "helpText";

		String expected = "helpText on Command";
		String result = object.getHelp(command);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void testCommandNoHelpTextdefault() {
		String command = "default";

		String result = object.getHelp(command);
		Assert.assertNull(result);
	}

	@Test
	public void testCommandSecured() {
		String command = "secured";

		String[] expected = new String[]{"default"};
		String[] result = object.getRequiredPermissions(command);

		Assert.assertArrayEquals(expected, result);
	}

	@Test
	public void testCommandNotSecured() {
		String command = "default";

		String[] expected = new String[]{};
		String[] result = object.getRequiredPermissions(command);

		Assert.assertArrayEquals(expected, result);
	}

	@Test
	public void testCommandDoesNotExistSecured() {
		String command = INVALID_COMMAND;

		String[] expected = new String[]{};
		String[] result = object.getRequiredPermissions(command);

		Assert.assertArrayEquals(expected, result);
	}

	@Test
	public void testNoCommandHelpText() {
		String command = INVALID_COMMAND;

		String result = object.getHelp(command);
		Assert.assertNull(result);
	}

	@Test
	public void testCommandList() {
		Collection<String> expected = Arrays.asList("default", "helpText", "secured");
		Collection<String> result = object.getActions();

		Assert.assertTrue(hasTheSameContents(expected, result));
	}

	@Test
	public void TestValidAction() {
		String action = "default";

		final boolean expected = true;
		final boolean result = object.isValidAction(action);

		Assert.assertEquals(expected, result);
	}

	@Test
	public void TestInvalidAction() {
		String action = INVALID_COMMAND;

		boolean expected = false;
		boolean result = object.isValidAction(action);

		Assert.assertEquals(expected, result);
	}

	@Test
	public void testGetNameWorks() {
		String expected = "mock";
		Assert.assertEquals(expected, object.getName());
	}

	private static <T> boolean hasTheSameContents(Collection<T> c1, Collection<T> c2) {
		return c1.containsAll(c2) && c2.containsAll(c1);
	}
}
