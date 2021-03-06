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
package com.fossgalaxy.pircbotx.commands.lines;

import com.google.inject.Inject;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * A ListenerAdapter which keeps a log of all the lines said in a
 * channel.
 *
 * @author Bruce Cowan
 */
public class LinesListener extends ListenerAdapter {

    private final LineModel model;

    /**
     * Creates a new {@link LinesListener}.
     */
    @Inject
    public LinesListener(LineModel model) {
        this.model = model;
    }

    @Override
    public void onMessage(MessageEvent event) throws Exception {
        model.storeLine(event.getMessage());
    }
}
