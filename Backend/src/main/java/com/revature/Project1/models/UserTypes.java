package com.revature.Project1.models;

public enum UserTypes {
    ADMIN(1),
    USER(0);

    private final int value;

    UserTypes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
