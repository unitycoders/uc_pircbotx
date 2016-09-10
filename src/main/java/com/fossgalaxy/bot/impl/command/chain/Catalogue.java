package com.fossgalaxy.bot.impl.command.chain;

import com.fossgalaxy.bot.api.command.Context;
import com.fossgalaxy.bot.api.command.Controller;
import com.fossgalaxy.bot.api.command.Request;
import com.fossgalaxy.bot.api.command.Response;

import java.util.*;

/**
 * Created by webpigeon on 10/09/16.
 */
public class Catalogue {
    private final Map<String, Controller> controllers;
    private final Map<String, SortedSet<String>> reverseMap;

    public Catalogue(){
        this.controllers = new HashMap<>();
        this.reverseMap = new HashMap<>();
    }

    public Controller get(String name) {
        name = Objects.requireNonNull(name);
        return controllers.get(name);
    }

    public boolean exists(String name) {
        name = Objects.requireNonNull(name);
        return controllers.containsKey(name);
    }

    public SortedSet<String> findByAction(String name) {
        SortedSet<String> args = reverseMap.get(name);
        if (args == null) {
            return Collections.emptySortedSet();
        }
        return Collections.unmodifiableSortedSet(args);
    }

    public void register(String name, Controller controller) {
        assert name != null;
        assert controller != null;
        controllers.put(name, controller);
        controller.bindCatalogue(name,this);
    }

    public void addReverse(String name, String action) {
        SortedSet<String> reverse = reverseMap.get(action);
        if (reverse == null) {
            reverse = new TreeSet<>();
            reverseMap.put(action, reverse);
        }
        reverse.add(name);
    }
}
