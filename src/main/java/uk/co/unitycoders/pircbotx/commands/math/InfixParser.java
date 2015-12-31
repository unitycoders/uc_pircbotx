package uk.co.unitycoders.pircbotx.commands.math;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * An implementation of the Shunting-yard algorithm.
 * 
 */
public class InfixParser {

	private Map<String, Function> functions;
	
	public InfixParser(Map<String, Function> functions) {
		this.functions = functions;
	}
	
	public Queue<Token> process(Queue<Token> tokens) {
		Queue<Token> output = new LinkedList<Token>();
		Stack<Token> stack = new Stack<Token>();
		
		while(!tokens.isEmpty()) {
			Token token = tokens.poll();
			
			if (token.isType(TokenType.NUMBER)) {
				output.add(token);
			}
			
			if (token.isType(TokenType.FUNCTION_SEP)) {
				// Until the token at the top of the stack is a left parenthesis, pop operators off the stack onto the output queue. If no left parentheses are encountered, either the separator was misplaced or parentheses were mismatched.
			}
			
			if (token.isType(TokenType.OPERATOR) || token.isType(TokenType.FUNCTION) ) {
				if (!stack.isEmpty()) {
					Function currFunc = functions.get(token.getValue());
					Token stackTop = stack.peek();
					while (stackTop != null && (stackTop.isType(TokenType.OPERATOR) || token.isType(TokenType.FUNCTION))) {
						Function topFunc = functions.get(token.getValue());
						if ( (currFunc.isLeftAssoc() && currFunc.getPrecidence() <= topFunc.getPrecidence()) ||
								!currFunc.isLeftAssoc() && currFunc.getPrecidence() < topFunc.getPrecidence()) {
							output.add(stack.pop());
							if (stack.isEmpty()) {
								stackTop = null;
							} else {
								stackTop = stack.peek();
								System.out.println("stack added");
							}
						} else {
							stackTop = null;
						}
					}
				}
				
				stack.add(token);	
			}
			
			if (token.isType(TokenType.L_PARA)) {
				stack.push(token);
			}
			
			if (token.isType(TokenType.R_PARA)) {
				rightBracketBehavour(output, stack);
			}
		}
		
		while(!stack.isEmpty()) {
			//TODO throw an exception if you find ( or )
			output.add(stack.pop());
		}
		
		return output;
	}
	
	public void rightBracketBehavour(Queue<Token> output, Stack<Token> stack) {
		while (!stack.isEmpty()) {
			Token next = stack.pop();
			if (next.isType(TokenType.L_PARA)) {
				return;
			}
			
			output.add(next);
		}
	}
}
