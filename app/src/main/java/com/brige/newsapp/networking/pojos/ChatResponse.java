package com.brige.newsapp.networking.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

public class ChatResponse {

    @SerializedName("id")
    @Expose
    private Integer user_id;
    @SerializedName("user_from")
    @Expose
    private Integer userFrom;
    @SerializedName("user_from_name")
    @Expose
    private String userFromName;
    @SerializedName("user_to")
    @Expose
    private Integer userTo;
    @SerializedName("user_to_name")
    @Expose
    private String userToName;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;

    /**
     * Status 0 -> Unsent
     * Status 1 -> Sent
     * Status 2 -> Unread
     * Status 4 -> Read
     */
    private int status = 0;


    public ChatResponse() {
    }

    public ChatResponse(Integer user_id, Integer userFrom, String userFromName, Integer userTo, String userToName, String message, String dateCreated, int status) {
        this.user_id = user_id;
        this.userFrom = userFrom;
        this.userFromName = userFromName;
        this.userTo = userTo;
        this.userToName = userToName;
        this.message = message;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(Integer userFrom) {
        this.userFrom = userFrom;
    }

    public String getUserFromName() {
        return userFromName;
    }

    public void setUserFromName(String userFromName) {
        this.userFromName = userFromName;
    }

    public Integer getUserTo() {
        return userTo;
    }

    public void setUserTo(Integer userTo) {
        this.userTo = userTo;
    }

    public String getUserToName() {
        return userToName;
    }

    public void setUserToName(String userToName) {
        this.userToName = userToName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
