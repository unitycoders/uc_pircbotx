/**
 * Copyright © 2015 Unity Coders
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

public class RewriteTest {

	public static void main(String[] args) {

		//TODO make this into junit tests
		RewriteEngine engine = new RewriteEngine();
		engine.addRule("^([a-z0-9]+)\\+\\+$", "karma add $1");
		engine.addRule("^([a-z0-9]+)--$", "karma remove $1");
		engine.addRule("^#([a-z0-9]+) (.*)$", "meeting $1 $2");

		//test cases
		System.out.println(engine.process("bruce89++"));
		System.out.println(engine.process("webpigeon--"));
		System.out.println(engine.process("I like programming in c++"));
		System.out.println(engine.process("you can use --- to delimit markdown test"));
		System.out.println(engine.process("#topic debian meetbot emulation"));
		System.out.println(engine.process("I think that #unity-coders is an irc channel"));
	}

}
