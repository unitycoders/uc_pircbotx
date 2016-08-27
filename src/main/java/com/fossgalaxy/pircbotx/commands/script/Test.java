package com.fossgalaxy.pircbotx.commands.script;

import com.fossgalaxy.pircbotx.backends.console.InteractiveMessage;

import java.util.Arrays;

/**
 * Created by webpigeon on 31/07/16.
 */
public class Test {

    public static void main(String[] args) throws Exception {

        ScriptModule sm = new ScriptModule("test", "scripts/test.js");

        InteractiveMessage message = new InteractiveMessage(Arrays.asList("test", "test"), System.out);
        sm.fire(message);

        sm.fire(new InteractiveMessage(Arrays.asList("test", "hello"), System.out));
        sm.fire(new InteractiveMessage(Arrays.asList("test", "hello"), System.out));
        sm.fire(new InteractiveMessage(Arrays.asList("test", "hello"), System.out));
        sm.fire(new InteractiveMessage(Arrays.asList("test", "reload"), System.out));

        sm.fire(new InteractiveMessage(Arrays.asList("test", "hello"), System.out));
        sm.fire(new InteractiveMessage(Arrays.asList("test", "respond", "something!"), System.out));

        System.out.println(sm.getModuleHelp());
        System.out.println(sm.getHelp("respond"));
    }

}
