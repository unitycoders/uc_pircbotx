package com.fossgalaxy.bot.core.command;

import com.fossgalaxy.bot.api.command.*;
import com.fossgalaxy.bot.core.command.functional.FunctionalController;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.Objects;

/**
 * A wrapper around the whole command processing pipeline.
 */
public class DefaultInvoker implements Invoker {
    private RequestParser parser;
    private RequestProcessor processor;

    @Inject
    public DefaultInvoker(RequestParser parser, RequestProcessor processor){
        this.parser = Objects.requireNonNull(parser);
        this.processor = Objects.requireNonNull(processor);
    }

    /**
     * Process a request and generate a response.
     *
     * @param context
     * @param input
     * @return
     */
    @Override
    public Response apply(Context context, String input) {
        Request request = parser.apply(input);
        return processor.process(context, request);
    }

    @Override
    public Response doChain(Context context, String[] commands) {

        Response response = apply(context, commands[0]);

        // respond for all other phases
        for (int i=1; i<commands.length; i++) {
            response = apply(context, commands[i]+" "+response.toString());
        }

        return response;
    }

    /**
     * Debug method for default invoker.
     *
     * This will create a command processor and output some expected values using the whole command processing system.
     * It also provides a minimal example for getting the command processing engine working.
     *
     * @param args ignored
     */
    public static void main(String[] args) {

        //setup our dependency injection framework and get the stuff we're going to need
        Injector injector = Guice.createInjector(new ApiCommandModule());
        Invoker invoker = injector.getInstance(Invoker.class);
        Catalogue catalogue = injector.getInstance(Catalogue.class);

        //ensure that our preprocessors are loaded
        RequestProcessor parser = injector.getInstance(RequestProcessor.class);
        parser.addLast(injector.getInstance(InferControllerPreprocessor.class));
        //parser.addLast(injector.getInstance(InsertMissingDefaultPreprocessor.class));

        //create a functional module for us to play with
        FunctionalController fc = new FunctionalController();
        fc.add("echo", (c,r) -> Response.respond(r.getArgument(0)));
        fc.add("upper", (c,r) -> Response.respond(r.getArgument(0).toUpperCase()));
        fc.add("lower", (c,r) -> Response.respond(r.getArgument(0).toLowerCase()));
        fc.add("length", (c,r) -> Response.respond(""+r.getArgument(0).length()));
        fc.add("duplicate", (c,r) -> Response.respond(r.getArgument(0)));
        fc.add("tell", (c,r) -> Response.respond(String.format("%s: %s",r.getArgument(0), r.getArgument(1))));
        catalogue.register("test", fc);

        FunctionalController fc2 = new FunctionalController();
        fc2.add("duplicate", (c,r) -> Response.respond(r.getArgument(0)));
        catalogue.register("test2", fc2);

        //run the test commands
        System.out.println(invoker.apply(null, "not found"));
        System.out.println(invoker.apply(null, "test Found"));
        System.out.println(invoker.apply(null, "test default Found"));
        System.out.println(invoker.apply(null, "duplicate"));
        System.out.println(invoker.apply(null, "test echo"));

        System.out.println(invoker.apply(null, "tell 'webpigeon' 'cats are fluffy'"));
        System.out.println(invoker.apply(null, "echo Found"));
        System.out.println(invoker.apply(null, "upper Found"));
        System.out.println(invoker.apply(null, "lower Found"));
        System.out.println(invoker.apply(null, "length Found"));

    }

}
