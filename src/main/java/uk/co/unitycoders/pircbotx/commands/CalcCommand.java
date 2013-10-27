/**
 * Copyright © 2012 Joseph Walton-Rivers <webpigeon@unitycoders.co.uk>
 * Copyright © 2012 Bruce Cowan <bruce@bcowan.me.uk>
 *
 * This file is part of uc_PircBotX.
 *
 * uc_PircBotX is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * uc_PircBotX is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * uc_PircBotX. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.unitycoders.pircbotx.commands;

import java.util.Stack;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;
import uk.co.unitycoders.pircbotx.commandprocessor.BotMessage;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;

/**
 *
 * @author webpigeon
 */
public class CalcCommand {

    private static final Integer OP_TOKEN = 1;
    private static final Integer NUM_TOKEN = 2;

    private int doExpr(int left, char op, int right) {
        switch (op) {
            case '*':
                return left * right;
            case '/':
                return left / right;
            case '+':
                return left + right;
            case '-':
                return left - right;
            case '%':
                return left % right;
        }

        return -1;
    }

    private int doNum(Token num) {
        return Integer.parseInt(num.data);
    }

    private char doOp(Token op) {
        return op.data.charAt(0);
    }

    private int doStmt(Stack<Token> input) {
        Token t = input.pop();
        System.out.println("token = " + t.data);

        if (t.type == NUM_TOKEN) {
            return doNum(t);
        } else if (t.type == OP_TOKEN) {
            char op = doOp(t);
            int right = doStmt(input);
            int left = doStmt(input);
            return doExpr(left, op, right);
        }

        return -1;
    }

    private Stack<Token> tokenise(String input) {
        String[] tokens = input.split(" ");
        Stack<Token> stack = new Stack<Token>();

        for (int i = 0; i < tokens.length; i++) {
            Token t = new Token();
            t.data = tokens[i];

            if (tokens[i].matches("\\d+")) {
                t.type = NUM_TOKEN;
            }

            if (tokens[i].matches("[+*/%-]")) {
                t.type = OP_TOKEN;
            }

            if (t.type == null) {
                throw new RuntimeException("illegal token " + tokens[i]);
            }

            stack.push(t);
        }

        return stack;
    }

    @Command
    public void onCalc(BotMessage event) throws Exception {
        String msg = event.getMessage();

        try {
            msg = msg.substring(6);
            event.respond(msg + " = " + parse(msg));
        } catch (IndexOutOfBoundsException ex) {
            event.respond(ex.getLocalizedMessage());
        }
    }

    public int parse(String input) {
        Stack<Token> tokens = tokenise(input);
        return doStmt(tokens);
    }

    class Token {

        Integer type;
        String data;
    }

}
