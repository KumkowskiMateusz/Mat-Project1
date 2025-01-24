package com.revature.Project1.Dtos;

public class UserDTO {
    private int userId;
    private String username;
    private String password;
    private String token;
    private String role;

    public UserDTO(int userId, String username, String password, String token, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.token = token;
        this.role = role;
    }

    public UserDTO() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
