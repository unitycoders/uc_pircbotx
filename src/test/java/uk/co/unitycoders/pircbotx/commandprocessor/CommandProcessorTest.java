/**
 * Copyright © 2013 Joseph Walton-Rivers <webpigeon@unitycoders.co.uk>
 *
 * This file is part of uc_PircBotX.
 *
 * uc_PircBotX is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * uc_PircBotX is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * uc_PircBotX. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.unitycoders.pircbotx.commandprocessor;

import java.util.*;
import java.util.concurrent.Exchanger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author webpigeon
 *
 */
public class CommandProcessorTest {

    private CommandProcessor processor;
    private static final Character TRIGGER = '!';

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        processor = new CommandProcessor(null);
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
        Object module = new FakeModule();
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
        Object module = new FakeModule();
        processor.register(name, module);

        Collection<String> expected = new ArrayList<String>();
        expected.add("default");
        expected.add("goodbye");
        expected.add("bye");
        expected.add("hello");

        Collection<String> result = processor.getCommands(name);

        Assert.assertTrue(hasTheSameContents(result, expected));
    }

    @Test
    public void testNullModule() throws Exception {
        String name = null;

        Collection<String> expected = Collections.emptyList();
        Collection<String> result = processor.getCommands(name);

        Assert.assertTrue(hasTheSameContents(expected, result));
    }

    @Test
    public void testInvokeInvalidModule() throws Exception {
        Message message = new MessageStub(TRIGGER+"invalidModule");
        processor.invoke(message);
    }

    @Test
    public void testDefaultCommand() throws Exception {
        String name = "fake";
        Object module = new FakeModule();
        processor.register(name, module);

        Message message = new MessageStub(TRIGGER+name);
        processor.invoke(message);
    }


    @Test
    public void testCommandsNotExists() {
        String name = "doesNotExist";

        Collection<String> expected = Collections.emptyList();
        Collection<String> result = processor.getCommands(name);

        Assert.assertTrue(hasTheSameContents(result, expected));
    }

    private static <T> boolean hasTheSameContents(Collection<T> c1, Collection<T> c2) {
        return c1.containsAll(c2) && c2.containsAll(c1);
    }

}
