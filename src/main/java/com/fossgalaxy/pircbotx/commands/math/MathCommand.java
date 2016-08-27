package com.fossgalaxy.pircbotx.commands.math;


import com.fossgalaxy.pircbotx.commandprocessor.Command;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.commands.math.ast.Expr;
import com.fossgalaxy.pircbotx.modules.AnnotationModule;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class MathCommand extends AnnotationModule {
    private Map<String, Function> functions;

    public MathCommand() {
        super("math");
        this.functions = new HashMap<>();
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

    public static void main(String[] args) {
        MathCommand mc = new MathCommand();
        System.out.println(mc.eval("5+5"));
        System.out.println(mc.eval("SIN(90)"));
        System.out.println(mc.eval("SIN(90)-SIN(90)"));
        System.out.println(mc.eval("COS(SIN(COS(90)))"));
    }

    @Command
    public void onDefault(Message message) {
        String input = message.getArgument(2, null);
        if (input == null) {
            message.respond("usage: math [calculation]");
            return;
        }

        message.respond("answer is " + eval(input));
    }

    double eval(String input) {
        Tokeniser t = new Tokeniser();
        Queue<Token> tokens = t.process(input);
        ASTParser p = new ASTParser(tokens, functions);
        Expr e = p.expr();
        return e.eval();
    }
}
