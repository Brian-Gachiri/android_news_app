package com.brige.newsapp.networking.pojos;

public class MessageRequest {

    int user_to;
    String message;

    public MessageRequest(int user_to, String message) {
        this.user_to = user_to;
        this.message = message;
    }
}
