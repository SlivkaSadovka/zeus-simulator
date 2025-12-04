package org.zeus.demo.model;

public enum Behavior {
    SINNER,
    NEUTRAL,
    FAITHFUL;

    private static final Behavior[] VALUES = values();

    public static Behavior random() {
        return VALUES[(int) (Math.random() * VALUES.length)];
    }
}

