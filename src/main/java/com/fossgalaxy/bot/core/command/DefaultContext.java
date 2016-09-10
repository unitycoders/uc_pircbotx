package com.fossgalaxy.bot.core.command;

import com.fossgalaxy.bot.api.command.Context;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by webpigeon on 10/09/16.
 */
public class DefaultContext implements Context {
    private Map back;

    public DefaultContext() {
        this.back = new HashMap();
    }

    @Override
    public int size() {
        return back.size();
    }

    @Override
    public boolean isEmpty() {
        return back.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return back.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return back.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return back.get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        return back.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return back.remove(key);
    }

    @Override
    public void putAll(Map m) {
        back.putAll(m);
    }

    @Override
    public void clear() {
        back.clear();
    }

    @Override
    public Set keySet() {
        return back.keySet();
    }

    @Override
    public Collection values() {
        return back.values();
    }

    @Override
    public Set<Entry> entrySet() {
        return back.entrySet();
    }
}
