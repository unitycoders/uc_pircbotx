/**
 * Copyright © 2012-2015 Unity Coders
 * <p>
 * This file is part of uc_pircbotx.
 * <p>
 * uc_pircbotx is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p>
 * uc_pircbotx is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * uc_pircbotx. If not, see <http://www.gnu.org/licenses/>.
 */
package com.fossgalaxy.pircbotx.commands;

import com.fossgalaxy.pircbotx.commandprocessor.Command;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.modules.AnnotationModule;
import com.fossgalaxy.pircbotx.modules.ModuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.LinkedList;

public class CalcCommand extends AnnotationModule {
    private static final Integer OP_TOKEN = 1;
    private static final Integer NUM_TOKEN = 2;
    private final Logger LOG = LoggerFactory.getLogger(CalcCommand.class);
    public CalcCommand() {
        super("calc");
    }

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

    private int doStmt(Deque<Token> input) {
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

    private Deque<Token> tokenise(String input) throws ModuleException {
        String[] tokens = input.split(" ");
        Deque<Token> stack = new LinkedList<Token>();

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
                throw new ModuleException("illegal token " + tokens[i]);
            }

            stack.push(t);
        }

        return stack;
    }

    @Command
    public void onCalc(Message event) throws ModuleException {
        String msg = event.getArgument(2, null);

        try {
            event.respond(msg + " = " + parse(msg));
        } catch (IndexOutOfBoundsException ex) {
            event.respond(ex.getLocalizedMessage());
            LOG.info("Too few arguments when processing calculation", ex);
        } catch (ModuleException ex) {
            event.respond("Sorry, I didn't understand that");
            LOG.info("failed to correctly parse input: {} ", ex);
        }
    }

    public int parse(String input) throws ModuleException {
        Deque<Token> tokens = tokenise(input);
        return doStmt(tokens);
    }

    class Token {

        Integer type;
        String data;
    }

}
