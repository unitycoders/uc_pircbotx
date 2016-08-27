package com.fossgalaxy.pircbotx.commands.math;

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokeniser {
    private final Integer NUMBER = 1;
    private final Integer OPERATOR = 2;
    private final Integer FUNCTION = 3;
    private final Integer BRACKET = 4;
    private Pattern regex;

    public Tokeniser() {
        this.regex = Pattern.compile("([0-9]+\\.[0-9]+|[0-9]+)|([\\Q+/*%-^\\E])|([A-Za-z]{1,4})|(\\(|\\))");
    }

    public Queue<Token> process(String input) {
        Queue<Token> tokens = new LinkedList<>();

        Matcher matcher = regex.matcher(input);
        while (matcher.find()) {

            String data = matcher.group(NUMBER);
            if (data != null) {
                tokens.add(new Token(TokenType.NUMBER, data));
            }

            data = matcher.group(OPERATOR);
            if (data != null) {
                tokens.add(new Token(TokenType.OPERATOR, data));
            }

            data = matcher.group(FUNCTION);
            if (data != null) {
                tokens.add(new Token(TokenType.FUNCTION, data));
            }

            data = matcher.group(BRACKET);
            if (data != null) {
                if ("(".equals(data)) {
                    tokens.add(new Token(TokenType.L_PARA, null));
                }

                if (")".equals(data)) {
                    tokens.add(new Token(TokenType.R_PARA, null));
                }
            }
        }

        return tokens;
    }


}
