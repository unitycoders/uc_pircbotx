package com.fossgalaxy.bot.core.command.annotation;

import com.fossgalaxy.bot.api.command.Request;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by webpigeon on 10/09/16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Action {

    String[] value() default Request.DEFAULT_ACTION;

}
