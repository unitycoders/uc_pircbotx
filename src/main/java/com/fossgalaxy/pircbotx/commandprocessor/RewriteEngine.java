/**
 * Copyright © 2015 Unity Coders
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

import com.fossgalaxy.pircbotx.LocalConfiguration;
import com.fossgalaxy.pircbotx.middleware.AbstractMiddleware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

public class RewriteEngine extends AbstractMiddleware {
    private static final Logger LOG = LoggerFactory.getLogger(RewriteEngine.class);
    private Map<String, String> rules;

    public RewriteEngine() {
        this.rules = new TreeMap<>();
    }

    @Override
    public void init(LocalConfiguration config) {
        if (config.aliases == null) {
            return;
        }

        for (Map.Entry<String, String> rule : config.aliases.entrySet()) {
            LOG.info("{} is mapped to {}", rule.getKey(), rule.getValue());
            addRule(rule.getKey(), rule.getValue());
        }

    }

    public void addRule(String regex, String replace) {
        rules.put(regex, replace);
    }

    public String process(String source) {

        String output = source;
        for (Map.Entry<String, String> rule : rules.entrySet()) {
            output = output.replaceAll(rule.getKey(), rule.getValue());
        }
        LOG.info("{} {}", source, output);

        return output;
    }

    @Override
    public String preprocess(String text) {
        return process(text);
    }

    @Override
    public Message process(CommandProcessor processor, Message message) {
        return message;
    }

}
