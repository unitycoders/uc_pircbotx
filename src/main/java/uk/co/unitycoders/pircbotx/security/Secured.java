package uk.co.unitycoders.pircbotx.security;

/**
 * Created by webpigeon on 03/12/14.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Secured {

    public String[] value() default "default";

}
