package com.revature.Project1.models;

import jakarta.persistence.Entity;

@Entity
public class Auth {
    private String id;
    private String userId;
    private String key;
    private String value;

    public Auth() {
    }

    public Auth(String id, String userId, String key, String value) {
        this.id = id;
        this.userId = userId;
        this.key = key;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
