package com.brige.newsapp.networking.pojos;

public class RegisterRequest {

    String number, username, email, password;

    public RegisterRequest(String number, String username, String email, String password) {
        this.number = number;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public RegisterRequest() {
    }
}
