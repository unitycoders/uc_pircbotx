package com.fossgalaxy.bot.api.command;

import java.util.function.BiFunction;

/**
 * Created by webpigeon on 10/09/16.
 */
public interface Invoker extends BiFunction<Context, String, Response> {
}
