package com.fossgalaxy.bot.core.command;

import com.fossgalaxy.bot.api.command.*;
import com.fossgalaxy.bot.api.command.chain.Postprocessor;
import com.fossgalaxy.bot.api.command.chain.Preprocessor;
import com.fossgalaxy.pircbotx.commands.ReleaseCommand;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Class responsible for delegating requests to the appropriate place and dealing with the responses.
 */
@Singleton
class RequestProcessor {
    private final Logger LOG = LoggerFactory.getLogger(RequestProcessor.class);
    private final Catalogue catalogue;
    private Preprocessor pre;
    private Postprocessor post;

    @Inject
    public RequestProcessor(Catalogue catalogue) {
        this(Preprocessor.identity(), Postprocessor.identity(), catalogue);
    }

    public RequestProcessor(Preprocessor pre, Postprocessor post, Catalogue catalogue){
        this.pre = Objects.requireNonNull(pre);
        this.post = Objects.requireNonNull(post);
        this.catalogue = Objects.requireNonNull(catalogue);
    }

    public void addLast(Preprocessor step) {
        assert step != null;
        pre = pre.andThen(step);
    }

    public void addFirst(Preprocessor step) {
        assert step != null;
        pre = pre.compose(step);
    }

    public void addFirst(Postprocessor step) {
        assert step != null;
        post = post.compose(step);
    }

    public void addLast(Postprocessor step) {
        assert step != null;
        post = post.andThen(step);
    }


    /**
     * Process a request from a user.
     *
     * @param context
     * @param request
     * @return
     */
    public Response process(Context context, Request request) {
        try {
            Request processedRequest = pre.process(context, request);

            Controller controller = catalogue.get(processedRequest.getController());
            if (controller == null) {
                throw new MissingRequestException(request, request.getController());
            }

            Response rawResponse = controller.execute(context, processedRequest);
            return post.process(context, rawResponse);
        } catch (RequestException ex) {
            //user messed up their request
            return Response.respond(String.format("error %s", ex.getMessage()));
        } catch (ControllerException ex) {
            LOG.error("error when processing request", ex);

            String role = (String)context.get("USER_ROLE");
            if (role != null && role.equals("admin")) {
                return Response.respond(String.format("error: ", ex.getMessage()));
            }

            return Response.respond("Something went wrong, please let the admin know");
        }
    }

}
