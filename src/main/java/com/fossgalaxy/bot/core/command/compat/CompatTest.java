package com.fossgalaxy.bot.core.command.compat;

import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Response;
import com.fossgalaxy.bot.core.command.DefaultContext;
import com.fossgalaxy.bot.core.command.SimpleRequest;
import com.fossgalaxy.pircbotx.commands.CompSciCommand;

import java.util.Arrays;

/**
 * Created by webpigeon on 10/09/16.
 */
public class CompatTest {

    public static void main(String[] args) {
        ModuleController controller = new ModuleController(new CompSciCommand());
        Context ctx = new DefaultContext();
        ctx.put(Context.MESSAGE_TARGET, "terminal");

        System.out.println(controller.execute(ctx, new SimpleRequest("compsci", "or", Arrays.asList("100", "010"))));
        System.out.println(controller.execute(ctx, new SimpleRequest("compsci", "and", Arrays.asList("100", "010"))));
        System.out.println(controller.execute(ctx, new SimpleRequest("compsci", "xor", Arrays.asList("100", "010"))));
    }
}
