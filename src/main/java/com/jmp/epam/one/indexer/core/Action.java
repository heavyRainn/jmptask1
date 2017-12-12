package com.jmp.epam.one.indexer.core;

public enum Action {

    SCAN,
    SEARCH,
    EXIT,
    SHOW_CACHE,
    DEFAULT;

    public static Action convertFromString(String action) {
        for (Action a : Action.values()) {
            if (a.name().equalsIgnoreCase(action)) {
                return a;
            }
        }
        return DEFAULT;
    }

}
