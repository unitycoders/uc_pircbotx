package uk.co.unitycoders.pircbotx.commands.math;

import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class MathParser {
	private Tokeniser tokeniser;
	private InfixParser parser;
	private PostfixEvaulator evaluator;
	
	public MathParser(Tokeniser t, InfixParser p, PostfixEvaulator e) {
		this.tokeniser = t;
		this.parser = p;
		this.evaluator = e;
	}
	
	public double evalInfix(String input) {
		Queue<Token> infix = tokeniser.process(input);
		Queue<Token> postfix = parser.process(infix);
		return evaluator.evaluate(postfix);
	}
	
	public static void main(String[] args) {
		MathParser parser = build();
		parser.evalInfix("COS(3.1415)+SIN(3.1415)");
	}
	
	public static MathParser build() {
		Map<String, Function> functions = new TreeMap<String, Function>();
		functions.put("+", new OperatorFunction("+", true, 2));
		functions.put("-", new OperatorFunction("-", true, 2));
		functions.put("/", new OperatorFunction("/", true, 3));
		functions.put("*", new OperatorFunction("*", true, 3));
		functions.put("%", new OperatorFunction("%", true, 3));
		functions.put("^", new OperatorFunction("^", false, 4));
		functions.put("SIN", new MathFunction("SIN", true, 5));
		functions.put("COS", new MathFunction("COS", true, 5));
		functions.put("TAN", new MathFunction("TAN", true, 5));
		functions.put("DEG", new MathFunction("DEG", true, 5));
		functions.put("RAD", new MathFunction("RAD", true, 5));
		
		Tokeniser t = new Tokeniser();
		InfixParser p = new InfixParser(functions);
		PostfixEvaulator e = new PostfixEvaulator(functions);
		
		return new MathParser(t,p,e);
	}

}
