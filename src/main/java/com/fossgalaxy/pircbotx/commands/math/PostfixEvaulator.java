package com.fossgalaxy.pircbotx.commands.math;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

public class PostfixEvaulator {
    private Map<String, Function> functions;

    public PostfixEvaulator(Map<String, Function> functions) {
        this.functions = functions;
    }

    public double evaluate(Deque<Token> input) {
        System.out.println(input);

        Deque<Double> stack = new LinkedList<>();

        Token token = input.poll();
        while (token != null) {
            if (token.isType(TokenType.NUMBER)) {
                stack.push(Double.parseDouble(token.getValue()));
            }

            if (token.isType(TokenType.OPERATOR) || token.isType(TokenType.FUNCTION)) {
                Function function = functions.get(token.getValue());

                if (function != null) {
                    int arity = function.getArity();
                    double[] args = new double[arity];
                    for (int i = 0; i < arity; i++) {
                        args[i] = stack.pop();
                    }
                    stack.push(function.evaulate(args));
                }

            }

            token = input.poll();
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("malformed expression");
        }

        return stack.pop();
    }
}
