package com.fossgalaxy.bot.core.command;

import com.fossgalaxy.bot.api.command.MissingArgumentException;
import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.bot.core.command.SimpleRequest;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by webpigeon on 10/09/16.
 */
public class TestSimpleRequest {

    @Test
    public void testConstructor() {

        String controller = "controller";
        List<String> args = Arrays.asList("arg1", "arg2");

        String expectedController = "controller";
        String expectedAction = "default";
        List<String> expectedArgs = Arrays.asList("arg1", "arg2");

        SimpleRequest simpleRequest = new SimpleRequest(controller, args);
        assertEquals(expectedController, simpleRequest.getController());
        assertEquals(expectedAction, simpleRequest.getAction());
        assertEquals(expectedArgs, simpleRequest.getArguments());
    }

    @Test
    public void testConstructor2() {

        String controller = "controller";
        String action = "action";
        List<String> args = Arrays.asList("arg1", "arg2");

        String expectedController = "controller";
        String expectedAction = "action";
        List<String> expectedArgs = Arrays.asList("arg1", "arg2");

        SimpleRequest simpleRequest = new SimpleRequest(controller, action, args);
        assertEquals(expectedController, simpleRequest.getController());
        assertEquals(expectedAction, simpleRequest.getAction());
        assertEquals(expectedArgs, simpleRequest.getArguments());
    }

    @Test
    public void testGetRequiredArg() {

        String controller = "controller";
        List<String> args = Arrays.asList("arg1", "arg2");
        List<String> expected = Arrays.asList("arg1", "arg2");

        SimpleRequest simpleRequest = new SimpleRequest(controller, args);
        assertEquals("arg1", simpleRequest.getArgument(0));
        assertEquals("arg2", simpleRequest.getArgument(1));
        assertEquals(expected, simpleRequest.getArguments());
    }

    @Test
    public void testGetOptionalArgPresent() {

        String controller = "controller";
        List<String> args = Arrays.asList("arg1", "arg2");
        List<String> expected = Arrays.asList("arg1", "arg2");

        Request simpleRequest = new SimpleRequest(controller, args);
        assertEquals("arg1", simpleRequest.getArgument(0, null));
        assertEquals("arg2", simpleRequest.getArgument(1, null));
        assertEquals(expected, simpleRequest.getArguments());
    }

    @Test
    public void testGetOptionalArgMissing() {

        String controller = "controller";
        List<String> args = Arrays.asList("arg1", "arg2");
        List<String> expected = Arrays.asList("arg1", "arg2");

        SimpleRequest simpleRequest = new SimpleRequest(controller, args);
        assertEquals("optional", simpleRequest.getArgument(2, "optional"));
        assertEquals(expected, simpleRequest.getArguments());
    }

    @Test(expected = MissingArgumentException.class)
    public void testGetRequiredArgMissing() {

        String controller = "controller";
        List<String> args = Arrays.asList("arg1", "arg2");;

        SimpleRequest simpleRequest = new SimpleRequest(controller, args);
        simpleRequest.getArgument(2);
    }

    @Test
    public void testEquals() {
        SimpleRequest request1 = new SimpleRequest("controller", Arrays.asList("arg1", "arg2"));
        SimpleRequest request2 = new SimpleRequest("controller", "default", Arrays.asList("arg1", "arg2"));
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    public void testEqualsDifferentActions() {
        SimpleRequest request1 = new SimpleRequest("controller", "test", Arrays.asList("arg1", "arg2"));
        SimpleRequest request2 = new SimpleRequest("controller", "default", Arrays.asList("arg1", "arg2"));
        assertNotEquals(request1, request2);
    }

    @Test
    public void testEqualsDifferentControllers() {
        SimpleRequest request1 = new SimpleRequest("controller1", "default", Arrays.asList("arg1", "arg2"));
        SimpleRequest request2 = new SimpleRequest("controller2", "default", Arrays.asList("arg1", "arg2"));
        assertNotEquals(request1, request2);
    }

    @Test
    public void testEqualsDifferentArgs() {
        SimpleRequest request1 = new SimpleRequest("controller", "default", Arrays.asList("arg1", "arg2"));
        SimpleRequest request2 = new SimpleRequest("controller", "default", Arrays.asList("arg1", "different"));
        assertNotEquals(request1, request2);
    }

}
