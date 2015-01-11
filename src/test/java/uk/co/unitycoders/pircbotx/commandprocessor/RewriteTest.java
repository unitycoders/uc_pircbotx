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
