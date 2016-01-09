/**
 * Copyright © 2014-2015 Unity Coders
 *
 * This file is part of uc_pircbotx.
 *
 * uc_pircbotx is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * uc_pircbotx is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * uc_pircbotx. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.unitycoders.pircbotx.commandprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.pircbotx.PircBotX;
import org.pircbotx.User;

public class MessageStub implements Message {
    private final List<String> args;

    public MessageStub(String ... message) {
        this.args = new ArrayList<>();
        args.addAll(Arrays.asList(message));
    }

    @Override
    public PircBotX getBot() {
        return null;
    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String getTargetName() {
        return null;
    }

    @Override
    public void respond(String message) {
    }

    @Override
    public void sendAction(String action) {
    }

	@Override
	public String getArgument(int id, String defaultValue) {
		if (args.size() <= id) {
			return defaultValue;
		}
		
		return args.get(id);
	}

	@Override
	public void insertArgument(int i, String arg) {
		args.add(i, arg);
	}

	@Override
	public String getRawMessage() {
		return null;
	}

	@Override
	public void sendSuccess() {
		
	}

	@Override
	public String getArgument(int id) {
		return getArgument(id, null);
	}
}
