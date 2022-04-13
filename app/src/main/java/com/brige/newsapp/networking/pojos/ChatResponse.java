package com.brige.newsapp.networking.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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


}
