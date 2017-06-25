package com.example.reactiveapp.model;

/**
 * Created by mario on 24.6.2017..
 */

public class LoginRequestModel {
    private String username;
    private String password;

    public LoginRequestModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
