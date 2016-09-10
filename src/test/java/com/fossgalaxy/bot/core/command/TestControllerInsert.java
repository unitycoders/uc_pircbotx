package com.fossgalaxy.bot.core.command;

import com.fossgalaxy.bot.api.command.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by webpigeon on 10/09/16.
 */
@RunWith(Parameterized.class)
public class TestControllerInsert {

    @Parameters(name="{index}: {0} -> {1} {2} {3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new SimpleRequest("dummy", Arrays.asList("test")), new SimpleRequest("dummy", Arrays.asList("test"))},
                {new SimpleRequest("dummy", Arrays.asList("arg1")), new SimpleRequest("dummy", Arrays.asList("arg1"))},
                {new SimpleRequest("dummy", "test", Arrays.asList("arg1")), new SimpleRequest("dummy", "test", Arrays.asList("arg1"))},
                {new SimpleRequest("dummy", "test", Arrays.asList("arg1", "arg2")), new SimpleRequest("dummy", "test", Arrays.asList("arg1", "arg2"))},

                {new SimpleRequest("dummy", "test2", Collections.emptyList()), new SimpleRequest("dummy", "default", Arrays.asList("test2"))},
                {new SimpleRequest("dummy", "default", Collections.emptyList()), new SimpleRequest("dummy", "default", Collections.emptyList())},
                {new SimpleRequest("invalid", "test2", Arrays.asList("arg1")), new SimpleRequest("invalid", "test2", Arrays.asList("arg1"))},
                {new SimpleRequest("invalid", "test2", Collections.emptyList()), new SimpleRequest("invalid", "test2", Collections.emptyList())},
        });
    }

    private final Request input;
    private final Request expected;

    private final InsertMissingDefaultPreprocessor fixer;

    public TestControllerInsert(Request input, Request expected) {
        SimpleCatalogue catalogue = new SimpleCatalogue();
        catalogue.register("dummy", new DummyController("default", "test"));
        catalogue.register("nodefault", new DummyController("test"));

        this.fixer = new InsertMissingDefaultPreprocessor(catalogue);
        this.input = input;
        this.expected = expected;
    }

    @Test
    public void testRewrite() {
        Request output = fixer.process(null, input);
        assertEquals(expected, output);
    }


}
