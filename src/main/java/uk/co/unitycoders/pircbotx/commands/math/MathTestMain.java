package uk.co.unitycoders.pircbotx.commands.math;

public class MathTestMain {
	
	public static void main(String[] args) {
		
		MathParser parser = MathParser.build();
		
		double result = parser.evalInfix("5+5");
		System.out.println("result "+result);		
	}

}
