package com.getaji.rrt.util;

import java.util.HashMap;
import java.util.Map;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class WrapperAdapter {

    // ================================================================
    // Static methods
    // ================================================================
    public static WrapperAdapter create() {
        return new WrapperAdapter();
    }

    // ================================================================
    // Fields
    // ================================================================
    private final Map<Object, Wrapper<?>> wrappers = new HashMap<>();

    // ================================================================
    // Setters
    // ================================================================
    public void add(Object value) {
        if (wrappers.containsKey(value)) {
            return;
        }
        wrappers.put(value, Wrapper.wrapNullable(value));
    }

    public void addAll(Object... values) {
        for (Object value : values) {
            add(value);
        }
    }

    // ================================================================
    // Getters
    // ================================================================
    public <E> Wrapper<E> getWrapper(E value) {
        return (Wrapper<E>) wrappers.get(value);
    }
}
