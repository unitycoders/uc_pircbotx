package com.fossgalaxy.bot.core.command;

import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.bot.core.command.RequestParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by webpigeon on 10/09/16.
 */
@RunWith(Parameterized.class)
public class TestRequestGeneration {

    @Parameters(name="{index}: {0} -> {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"controller", "controller", "default", Collections.emptyList()},
                {"controller action", "controller", "action", Collections.emptyList()},
                {"controller action arg1", "controller", "action", Arrays.asList("arg1")},
                {"controller action arg1 arg2", "controller", "action", Arrays.asList("arg1", "arg2")},
                {"controller action \"arg1\"", "controller", "action", Arrays.asList("arg1")},
                {"controller action \"arg1\" arg2", "controller", "action", Arrays.asList("arg1", "arg2")},
                {"controller action \"arg1 with space\"", "controller", "action", Arrays.asList("arg1 with space")},
                {"controller action \"arg 1\" \"arg 2\"", "controller", "action", Arrays.asList("arg 1", "arg 2")},
                {"controller action 'arg1'", "controller", "action", Arrays.asList("arg1")},
                {"controller action 'arg1' arg2", "controller", "action", Arrays.asList("arg1", "arg2")},
                {"controller action 'arg1 with space'", "controller", "action", Arrays.asList("arg1 with space")},
                {"controller action 'arg 1' 'arg 2'", "controller", "action", Arrays.asList("arg 1", "arg 2")},
                {"controller action \"arg 1\" 'arg 2'", "controller", "action", Arrays.asList("arg 1", "arg 2")},
                {"controller action 'arg 1' \"arg 2\"", "controller", "action", Arrays.asList("arg 1", "arg 2")},
                {"controller action \"arg's 1\" \"arg 2\"", "controller", "action", Arrays.asList("arg's 1", "arg 2")},
                {"controller action \"arg's 1\" \"arg's 2\"", "controller", "action", Arrays.asList("arg's 1", "arg's 2")},
        });
    }

    private final String input;
    private final String controller;
    private final String action;
    private final List<String> args;

    private final RequestParser parser;

    public TestRequestGeneration(String input, String controller, String action, List<String> args) {
        this.parser = new RequestParser();
        this.input = input;
        this.controller = controller;
        this.action = action;
        this.args = args;
    }

    @Test
    public void testParse() {
        Request request = parser.parse(input);
        assertEquals(controller, request.getController());
        assertEquals(action, request.getAction());
        assertEquals(args, request.getArguments());
    }

    @Test
    public void testFunctional() {
        Request request = parser.apply(input);
        assertEquals(controller, request.getController());
        assertEquals(action, request.getAction());
        assertEquals(args, request.getArguments());
    }


}
