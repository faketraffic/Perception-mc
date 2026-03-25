package xyz.tharmsy.perception.event.impl;

import xyz.tharmsy.perception.event.Event;

public class EventKey extends Event {
    private final int key;

    public EventKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
