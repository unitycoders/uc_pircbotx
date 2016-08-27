package com.fossgalaxy.pircbotx.commandprocessor;

import com.fossgalaxy.pircbotx.middleware.AbstractMiddleware;
import com.fossgalaxy.pircbotx.modules.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * Redirect non-existent actions to the module's default action.
 * If the module is missing and we can guess it insert the module name
 */
public class CommandFixerMiddleware extends AbstractMiddleware {
    private final static Logger LOG = LoggerFactory.getLogger(CommandFixerMiddleware.class);

    @Override
    public Message process(CommandProcessor processor, Message message) {

        // get the module (or skip if no such module)
        String moduleName = message.getArgument(Module.MODULE_ARG, null);
        Module module = processor.getModule(moduleName);
        if (module == null) {
            LOG.info("module {}, was not valid, attempting rewrite.", moduleName);

            //we couldn't repair this module, maybe it's shorthand?
            Set<String> modules = processor.getReverse(moduleName);
            if (modules.size() == 1) {
                LOG.info("one module provides {}, fixing request as {}", moduleName, modules);
                message.insertArgument(Module.MODULE_ARG, modules.iterator().next());
            } else {
                LOG.info("unable to fix request, not exactly one module provides {}, list was: {}", module, modules);
            }

            return message;
        }

        //check the message has a valid action
        String action = message.getArgument(Module.COMMAND_ARG, null);
        if (action == null || !module.isValidAction(action)) {
            message.insertArgument(1, Module.DEFAULT_COMMAND);
            LOG.info("module {} was valid, but second argument ({}) was not valid action, inserting default", module, action);
        }

        return message;
    }

}
