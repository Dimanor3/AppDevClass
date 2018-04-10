package com.example.ryanhaines.inclass10;

import java.util.Date;

/**
 * Created by ryanhaines on 4/2/18.
 */

public class ThreadResponse {
    String user_fname, user_lname, title, created_at;
    int user_id, id;

    public ThreadResponse(String user_fname, String user_lname, String title, String created_at, int user_id, int id) {
        this.user_fname = user_fname;
        this.user_lname = user_lname;
        this.title = title;
        this.created_at = created_at;
        this.user_id = user_id;
        this.id = id;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public String getUser_lname() {
        return user_lname;
    }

    public String getTitle() {
        return title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getUser_id() {
        return user_id;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
