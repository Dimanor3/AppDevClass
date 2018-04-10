package com.example.ryanhaines.inclass10;

import java.util.Date;

/**
 * Created by ryanhaines on 4/3/18.
 */

public class MessageResponse {

    String user_fname, user_lname, message, created_at;
    int id, user_id;

    public MessageResponse(String user_fname, String user_lname, String message, int id, int user_id, String created_at) {
        this.user_fname = user_fname;
        this.user_lname = user_lname;
        this.user_id = user_id;
        this.message = message;
        this.id = id;
        this.created_at = created_at;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public String getUser_lname() {
        return user_lname;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(int id) {
        this.id = id;
    }


}
