package com.getaji.rrt.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class EventDispatcher {

    public static EventDispatcher create() {
        return new EventDispatcher();
    }

    private final Map<Class<? extends Event>, List<Consumer<Event>>> handlers = new HashMap<>();

    private EventDispatcher() {}

    public <T extends Event> void onFire(Class<T> eventClass, T instance) {
        List<Consumer<Event>> handlers = this.handlers.get(eventClass);
        if (handlers != null) {
            for (Consumer<Event> handler : handlers) {
                handler.accept(instance);
            }
        }
    }

    public <T extends Event> void addHandler(Class<T> eventClass, Consumer<T> handler) {
        List<Consumer<Event>> handlers = this.handlers.get(eventClass);
        if (handlers == null) {
            handlers = new ArrayList<>();
            this.handlers.put(eventClass, handlers);
        }
        handlers.add((Consumer<Event>) handler);
    }
}
