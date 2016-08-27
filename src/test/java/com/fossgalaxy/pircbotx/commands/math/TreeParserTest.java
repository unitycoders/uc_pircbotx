package com.fossgalaxy.pircbotx.commands.math;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import com.fossgalaxy.pircbotx.commands.math.ast.Expr;
import org.junit.Before;
import org.junit.Test;

public class TreeParserTest {
	private static final Double ERROR_MARGIN = 0.00000000001; //something really small
	private ASTParser parser;

    Map<String, Function> functions;

	@Before
	public void setup() {
		functions = new TreeMap<>();
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

	}
	
	@Test
	public void testParseNumber() {
		//build infix queue
		Queue<Token> tokens = new LinkedList<>();
		tokens.add(new Token(TokenType.NUMBER, "5"));
        this.parser = new ASTParser(tokens,functions);
		//convert to postfix
		Expr expr = parser.expr(); //??
        System.out.println(expr);

		//check they match
		/*Token first = reordered.poll();
		Assert.assertEquals(first, new Token(TokenType.NUMBER, "5"));
		Assert.assertEquals(true, reordered.isEmpty());*/
	}
	
	@Test
	public void testParseOperator() {
		//build infix queue
		Queue<Token> tokens = new LinkedList<>();
		tokens.add(new Token(TokenType.NUMBER, "5"));
		tokens.add(new Token(TokenType.OPERATOR, "+"));
		tokens.add(new Token(TokenType.NUMBER, "6"));

        this.parser = new ASTParser(tokens,functions);
		//convert to postfix
		Expr reordered = parser.expr(); //??
		System.out.println(reordered);

		//check they match
		/*Token first = reordered.poll();
		Assert.assertEquals(first, new Token(TokenType.NUMBER, "5"));

		Token second = reordered.poll();
		Assert.assertEquals(second, new Token(TokenType.NUMBER, "5"));

		Token last = reordered.poll();
		Assert.assertEquals(last, new Token(TokenType.OPERATOR, "+"));

		Assert.assertEquals(true, reordered.isEmpty());*/
	}

	@Test
	public void testParseFunction() {
		//build infix queue
		Queue<Token> tokens = new LinkedList<>();
		tokens.add(new Token(TokenType.FUNCTION, "COS"));
		tokens.add(new Token(TokenType.L_PARA));
		tokens.add(new Token(TokenType.NUMBER, "5"));
		tokens.add(new Token(TokenType.R_PARA));

        this.parser = new ASTParser(tokens,functions);
		//convert to postfix
		Expr reordered = parser.expr(); //??
		System.out.println(reordered);

		/*System.out.println("REORDERED "+reordered); //lies.

		//check they match
		Token first = reordered.poll();
		Assert.assertEquals(first, new Token(TokenType.NUMBER, "5"));

		Token second = reordered.poll();
		Assert.assertEquals(second, new Token(TokenType.FUNCTION, "COS"));

		Assert.assertEquals(true, reordered.isEmpty());*/
	}



	@Test
	public void testParse2Functions() {
		//build infix queue
		Queue<Token> tokens = new LinkedList<>();
		tokens.add(new Token(TokenType.FUNCTION, "COS"));
		tokens.add(new Token(TokenType.L_PARA));
		tokens.add(new Token(TokenType.NUMBER, "5"));
		tokens.add(new Token(TokenType.R_PARA));
		tokens.add(new Token(TokenType.OPERATOR, "+"));
		tokens.add(new Token(TokenType.FUNCTION, "COS"));
		tokens.add(new Token(TokenType.L_PARA));
		tokens.add(new Token(TokenType.NUMBER, "5"));
		tokens.add(new Token(TokenType.R_PARA));

        this.parser = new ASTParser(tokens,functions);
		//convert to postfix
		Expr reordered = parser.expr(); //??
		System.out.println(reordered);

		//So we expect elements in postfix because we pop elements off the stack
		//then do what they mean and pop the result back on
		//we need the elements to therefore be in arguments then function order
		/*Token[] expTokens = new Token[]{
				new Token(TokenType.NUMBER, "5"),
				new Token(TokenType.FUNCTION, "COS"),
				new Token(TokenType.NUMBER, "5"),
				new Token(TokenType.FUNCTION, "COS"),
				new Token(TokenType.OPERATOR, "+")
		};

		//check they match
		for (Token expToken : expTokens) {
			Token next = reordered.poll();
			Assert.assertEquals(expToken, next);
		}

		//check there are no more
		Assert.assertEquals(true, reordered.isEmpty());*/
	}
	
}
