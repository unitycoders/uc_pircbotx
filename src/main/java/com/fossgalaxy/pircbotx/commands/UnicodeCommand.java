package com.fossgalaxy.pircbotx.commands;

import com.fossgalaxy.pircbotx.backends.console.InteractiveMessage;
import com.fossgalaxy.pircbotx.commandprocessor.Command;
import com.fossgalaxy.pircbotx.commandprocessor.CommandNotFoundException;
import com.fossgalaxy.pircbotx.commandprocessor.Message;
import com.fossgalaxy.pircbotx.modules.AnnotationModule;
import com.fossgalaxy.pircbotx.modules.ModuleException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by webpigeon on 27/08/16.
 */
public class UnicodeCommand extends AnnotationModule {

    public UnicodeCommand() {
        super("unicode");
    }

    @Command
    public void getNameOfChar(Message message) {
        String arg = message.getArgument(2);

        char c = arg.charAt(0);
        message.respond(Character.getName(c));
    }

    @Command("codepoint")
    public void getNameOfInt(Message message) {
        String arg = message.getArgument(2);
        int codePoint = Integer.parseInt(arg);
        message.respond(Character.getName(codePoint));
    }

    @Command("isUpper")
    public void isUpper(Message message) {
        String arg = message.getArgument(2);
        char c = arg.charAt(0);
        message.respond(Character.isUpperCase(c)?"yes":"no");
    }

    @Command("isLower")
    public void isLower(Message message) {
        String arg = message.getArgument(2);
        char c = arg.charAt(0);
        message.respond(Character.isLowerCase(c)?"yes":"no");
    }

    @Command("isDigit")
    public void isDigit(Message message) {
        String arg = message.getArgument(2);
        char c = arg.charAt(0);
        message.respond(Character.isDigit(c)?"yes":"no");
    }

    @Command("isWhitespace")
    public void isWhitespace(Message message) {
        String arg = message.getArgument(2);
        char c = arg.charAt(0);
        message.respond(Character.isWhitespace(c)?"yes":"no");
    }

    public static void main(String[] args) throws CommandNotFoundException, ModuleException {
        UnicodeCommand uc = new UnicodeCommand();
        List<String> cmdArgs = Arrays.asList("unicode", "isWhitespace", "3");
        uc.fire(new InteractiveMessage(cmdArgs, System.out));
    }
}
