package com.fossgalaxy.bot.core;

import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.bot.impl.command.chain.Catalogue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by webpigeon on 10/09/16.
 */
@RunWith(Parameterized.class)
public class TestReverseInsert {

    @Parameters(name="{index}: {0} -> {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new SimpleRequest("unique", "arg1", Collections.emptyList()), new SimpleRequest("dummy", "unique", Arrays.asList("arg1")), false},
                {new SimpleRequest("unique", "arg1", Arrays.asList("arg2")), new SimpleRequest("dummy", "unique", Arrays.asList("arg1", "arg2")), false},
                {new SimpleRequest("unique", null, Collections.emptyList()), new SimpleRequest("dummy", "unique", Collections.emptyList()), false},

                {new SimpleRequest("test", "something", Collections.emptyList()), new SimpleRequest("dummy", "test", Arrays.asList("something")), true},
                {new SimpleRequest("notFound", "test", Collections.emptyList()), new SimpleRequest("notFound", "test", Collections.emptyList()), false},

                {new SimpleRequest("dummy", "something", Collections.emptyList()), new SimpleRequest("dummy", "something", Collections.emptyList()), false},
                {new SimpleRequest("duplicate", "something", Collections.emptyList()), new SimpleRequest("duplicate", "something", Collections.emptyList()), false},
                {new SimpleRequest("duplicate", "test", Collections.emptyList()), new SimpleRequest("duplicate", "test", Collections.emptyList()), false}
        });
    }

    private final Request input;
    private final Request expected;
    private final boolean shouldFail;

    private final InferControllerPreprocessor fixer;
    private final InferControllerPreprocessor fixer2;

    public TestReverseInsert(Request input, Request expected, boolean shouldFail) {
        Catalogue catalogue = new Catalogue();
        catalogue.register("dummy", new DummyController("default", "test", "unique"));
        catalogue.register("duplicate", new DummyController("default", "test"));
        catalogue.register("actionMatchesController", new DummyController("duplicate"));

        this.fixer = new InferControllerPreprocessor(catalogue, false);
        this.fixer2 = new InferControllerPreprocessor(catalogue);

        this.input = input;
        this.expected = expected;
        this.shouldFail = shouldFail;
    }

    @Test
    public void testRewrite() {
        Request output = fixer.process(null, input);
        assertEquals(expected, output);
    }

    @Test
    public void testNonAmbiguous() {
        try {
            Request output = fixer2.process(null, input);
            assertEquals(expected, output);
            assertTrue(!shouldFail);
        } catch (RuntimeException ex) {
            assertTrue(shouldFail);
        }
    }

}
