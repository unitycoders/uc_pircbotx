package com.fossgalaxy.pircbotx.commands.math;

import com.fossgalaxy.pircbotx.commands.math.ast.*;

import java.util.*;

import static com.fossgalaxy.pircbotx.commands.math.TokenType.*;

/**
 * see https://www.engr.mun.ca/~theo/Misc/exp_parsing.htm (Thanks leonchen83)
 *
 * @author webpigeon
 */
public class ASTParser {

    private Map<String, Function> functions;

    private Queue<Token> input;

    private Token curr;

    public ASTParser(Queue<Token> input, Map<String, Function> functions) {
        this.input = input;
        this.functions = functions;
        next();
    }

    private final List<List<Object>> suffixExprListSupply = new ArrayList<>();

    private final List<Stack<Function>> opStackSupply = new ArrayList<>();

    private List<Object> newSuffixExprList() {
        suffixExprListSupply.add(new ArrayList<>());
        return suffixExprListSupply.get(suffixExprListSupply.size() - 1);
    }

    private Stack<Function> newOpStack() {
        opStackSupply.add(new Stack<Function>());
        return opStackSupply.get(opStackSupply.size() - 1);
    }

    /**
     * expr = expr + expr
     * expr = expr - expr
     * expr = expr * expr
     * expr = expr / expr
     * expr = expr ^ expr
     * expr = expr % expr
     * expr = (expr)
     * expr = num
     * expr = ^expr
     * expr = func({expr[,expr]...}) //only 1 argument?
     *
     */

    /**
     * remove left recursion
     *
     * @see [https://en.wikipedia.org/wiki/Left_recursion]
     * <p/>
     * expr = (expr) expr'
     * | func({expr[,expr]...}) expr'
     * | num expr'
     * <p/>
     * <p/>
     * <p/>
     * expr' =  + expr expr'
     * | - expr expr'
     * | * expr expr'
     * | / expr expr'
     * | ^ expr expr'
     * | % expr expr'
     * | ε
     */

    public Expr expr() {
        newSuffixExprList();
        newOpStack();
        Expr expr = expr0();
        suffixExprListSupply.remove(suffixExprListSupply.size() - 1);
        opStackSupply.remove(opStackSupply.size() - 1);
        return expr;
    }

    public Expr expr0() {
        if (curr.isType(L_PARA)) {
            accept(L_PARA);
            add(expr());
            accept(R_PARA);
            return exprRest();
        } else if (curr.isType(FUNCTION)) {
            String funcName = curr.getValue();
            accept(FUNCTION);
            accept(L_PARA);
            Expr expr = expr();
            accept(R_PARA);
            FuncExpr funcExpr = new FuncExpr(funcName, expr);
            add(funcExpr);
            return exprRest();
        } else if (curr.isType(NUMBER)) {
            String num = curr.getValue();
            add(new NumExpr(num));
            accept(NUMBER);
            return exprRest();
        } else {
            throw new RuntimeException("Expected ['(','func','num'] but actual " + curr.getType());
        }
    }

    public Expr exprRest() {
        if (curr == null) {
            pushOp(new OperatorFunction("$", true, 0));
            List<Object> suffixExpr = suffixExprListSupply.get(opStackSupply.size() - 1);
            return ast(suffixExpr);
        } else if (curr.isType(OPERATOR)) {
            String operator = curr.getValue();
            OperatorFunction function = (OperatorFunction) functions.get(operator);
            pushOp(function);
            accept(OPERATOR);
            expr0();
            return exprRest();
        } else {
            pushOp(new OperatorFunction("$", true, 0));
            List<Object> suffixExpr = suffixExprListSupply.get(opStackSupply.size() - 1);
            return ast(suffixExpr);
        }
    }

    private Expr ast(List<Object> suffixExpr) {
        Stack<Object> stack = new Stack<>();
        for (int i = 0; i < suffixExpr.size(); i++) {
            if (suffixExpr.get(i) instanceof OperatorFunction) {
                OperatorFunction op = (OperatorFunction) suffixExpr.get(i);
                if (op.getName().equals("+")) {
                    Expr right = (Expr) stack.pop();
                    Expr left = (Expr) stack.pop();
                    stack.push(new AddExpr(left, right));
                } else if (op.getName().equals("-")) {
                    Expr right = (Expr) stack.pop();
                    Expr left = (Expr) stack.pop();
                    stack.push(new SubExpr(left, right));
                } else if (op.getName().equals("*")) {
                    Expr right = (Expr) stack.pop();
                    Expr left = (Expr) stack.pop();
                    stack.push(new MultiExpr(left, right));
                } else if (op.getName().equals("/")) {
                    Expr right = (Expr) stack.pop();
                    Expr left = (Expr) stack.pop();
                    stack.push(new DivideExpr(left, right));
                } else if (op.getName().equals("^")) {
                    Expr right = (Expr) stack.pop();
                    Expr left = (Expr) stack.pop();
                    stack.push(new PowExpr(left, right));
                } else if (op.getName().equals("%")) {
                    Expr right = (Expr) stack.pop();
                    Expr left = (Expr) stack.pop();
                    stack.push(new ModExpr(left, right));
                }
            } else {
                stack.push(suffixExpr.get(i));
            }
        }
        assert stack.size() == 1;
        return (Expr) stack.pop();
    }

    private void add(Expr expr) {
        suffixExprListSupply.get(suffixExprListSupply.size() - 1).add(expr);
    }

    private void add(Function function) {
        suffixExprListSupply.get(suffixExprListSupply.size() - 1).add(function);
    }

    private void pushOp(OperatorFunction function) {
        Stack<Function> opStack = opStackSupply.get(opStackSupply.size() - 1);
        String o1 = function.getName();
        if (functions.get(o1) != null) {
            if (opStack.isEmpty()) {
                opStack.push(function);
            } else {
                while (!opStack.isEmpty() && ((function.isLeftAssoc() && function.getPrecidence() <= opStack.peek().getPrecidence()) || (!function.isLeftAssoc() && function.getPrecidence() < opStack.peek().getPrecidence()))) {
                    add(opStack.pop());
                }
                opStack.push(function);
            }
        } else {
            while (!opStack.isEmpty()) {
                add(opStack.pop());
            }
        }
    }

    public void accept(TokenType type) {
        if (curr.isType(type)) {
            next();
        } else {
            throw new RuntimeException("Expected " + type + " but actual " + curr);
        }
    }

    public void next() {
        curr = input.poll();
    }
}
