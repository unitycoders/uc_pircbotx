package uk.co.unitycoders.pircbotx.commands.math;

import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * see https://www.engr.mun.ca/~theo/Misc/exp_parsing.htm (Thanks leonchen83)
 * 
 * @author webpigeon
 *
 */
public class ASTParser {
	private static final TokenType V = TokenType.NUMBER; //TODO find out what v corrisponds to, assumption value/number
	private static final Token SENTINAL = null;
	
	private Map<String, Function> functions;
	private Stack<Token> operators;
	private Stack<TreeNode> operands;
	
	//TODO rename functions to be easier to follow
	
	public ASTParser(Map<String,Function> functions) {
		this.functions = functions;
	}
	
	public TreeNode process(Queue<Token> input) {
		operators = new Stack<>();
		operands = new Stack<>();
		
		operators.push(SENTINAL);
		e(input);
		
		if (input.isEmpty()) {
			throw new RuntimeException("Expected end of tokens but wasn't");
		}
		
		return operands.pop(); //stop the compiler complaining.
	}
	
	public void e(Queue<Token> input) {
		p(input);
		
		int arity = -1;
		do {
			
			Token next = input.poll();
			if (next.isType(TokenType.OPERATOR)) {
				Function nextFunction = functions.get(next.getValue());
				arity = nextFunction.getArity();
				
				pushOperator(next);
				input.poll();
				p(input);	
			}
			
		} while (arity == 2);
		
		Token next = operators.peek();
		while (next != SENTINAL) {
			popOperator();
		}
		
	}
	
	public void p(Queue<Token> input) {
		Token next = input.peek();
		
		if (next.isType(V)) {
			operands.push(new TreeNode(next));
			input.poll();
		} else if (next.isType(TokenType.L_PARA)) {
			operators.push(SENTINAL);
			e(input);
			
			next = input.poll();
			if (next.isType(TokenType.R_PARA)) {
				throw new RuntimeException("Expected closing bracket");
			}
		} else if (next.isType(TokenType.OPERATOR)) {
			Function nextFunction = functions.get(next.getValue());
			if (nextFunction.getArity() == 1) {
				pushOperator(next);
				input.poll();
				p(input);
			}
		} else {
			System.out.println("didn't know what to do with "+next.getType());
			throw new RuntimeException("nope.");
		}
	}
	
	public void popOperator() {
		Token top = operators.peek();
		Function topFunction = functions.get(top);
		
		if (topFunction.getArity() == 2) { 
			TreeNode t1 = operands.pop();
			TreeNode t0 = operands.pop();
			operands.push(new TreeNode(operators.pop(), t0, t1));
		} else {
			operands.push(new TreeNode(operators.pop(), operands.pop()));
		}
	}
	
	public void pushOperator(Token op) {
		Token top = operators.peek();
		
		Function topFunction = functions.get(top.getValue());
		Function opFunction = functions.get(op.getValue());
		
		while (topFunction.getPrecidence() > opFunction.getPrecidence()) {
			popOperator();
		}
		operators.push(op);
	}
	
}
