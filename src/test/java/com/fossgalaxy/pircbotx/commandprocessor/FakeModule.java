/**
 * Copyright © 2013-2014 Unity Coders
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
package com.fossgalaxy.pircbotx.commandprocessor;

import com.fossgalaxy.pircbotx.commandprocessor.Command;
import com.fossgalaxy.pircbotx.commandprocessor.Message;

/**
 * Fake module used for unit testing.
 *
 * @author webpigeon
 *
 */
public class FakeModule {

	/**
	 * Default command
	 *
	 * @param ex
	 */
	@Command
	public void doStuff(Message ex) {
	}

	/**
	 * Single command
	 *
	 * @param ex
	 */
	@Command("hello")
	public void doStuffOnce(Message ex) {
	}

	/**
	 * Single command
	 *
	 * @param ex
	 */
	@Command("exception")
	public void doBadThings(Message ex) {
		System.out.println("test");
		throw new NullPointerException();
	}

	/**
	 * Double command
	 *
	 * @param ex
	 */
	@Command({"goodbye", "bye"})
	public void doStuffTwice(Message ex) {
	}
}
