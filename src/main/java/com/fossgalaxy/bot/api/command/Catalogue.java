package com.fossgalaxy.bot.api.command;


import java.util.SortedSet;

/**
 * Created by webpigeon on 10/09/16.
 */
public interface Catalogue {

    Controller get(String name);

    boolean exists(String name);

    SortedSet<String> findByAction(String name);

    void register(String name, Controller controller);

    void addReverse(String name, String action);

    void alias(String alias, String name);
}
