/**
 * Copyright © 2012-2013 Unity Coders
 * <p>
 * This file is part of uc_pircbotx.
 * <p>
 * uc_pircbotx is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p>
 * uc_pircbotx is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * uc_pircbotx. If not, see <http://www.gnu.org/licenses/>.
 */
package com.fossgalaxy.pircbotx.commandprocessor;

import com.fossgalaxy.pircbotx.modules.Module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Command annotation.
 *
 * This annotation is used to tag a method as being a command the bot should
 * recognise and call when certian words or phrases are mentioned in a channel.
 *
 * The method must take exactly 1 argument, of {@link Message}
 *
 * @author webpigeon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Command {

    /**
     * Command trigger words.
     *
     * This is used to tell the bot what keywords it should respond to when
     * processing actions. The keyword "default" will be called automatically if
     * no keyword is specified. If an array of keywords is passed the bot will
     * register all keywords for this method.
     *
     * @return The keywords for this command
     */
    String[] value() default Module.DEFAULT_COMMAND;
}
