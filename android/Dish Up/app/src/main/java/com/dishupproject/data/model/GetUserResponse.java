package com.dishupproject.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class that represents the response in
 * the form of user model
 *
 * @author Dhau' Embun Azzahra
 */
public class GetUserResponse extends DefaultResponse{
    @SerializedName("data")
    private User user = null;

    public GetUserResponse(String message, User user) {
        super(message);
        this.user = user;
    }

    public GetUserResponse(String message) {
        super(message);
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return super.getMessage();
    }
}
