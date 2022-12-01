package com.dishupproject.data.model;
import com.google.gson.annotations.SerializedName;

/**
 * Class that represents a user.
 */
public class User {
    /**
     * The user id.
     */
    @SerializedName("user_id")
    private int user_id;
    /**
     * The user's username.
     */
    @SerializedName("username")
    private String username;
    /**
     * The user's email.
     */
    @SerializedName("email")
    private String email;
    /**
     * The user's password.
     */
    @SerializedName("password")
    private String password;

    public User(int user_id, String username, String email, String password){
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(int user_id, String username){
        this.user_id = user_id;
        this.username = username;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
