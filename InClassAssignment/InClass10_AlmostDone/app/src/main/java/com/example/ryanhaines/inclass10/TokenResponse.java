package com.example.ryanhaines.inclass10;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ryanhaines on 4/2/18.
 */

public class TokenResponse {
    int user_id;
    String status, token, user_email, user_fname, user_lname, user_role, current_id;

    public TokenResponse(String status, String token, int user_id, String user_email, String user_fname, String user_lname, String user_role, String current_id) {
        this.status = status;
        this.token = token;
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_fname = user_fname;
        this.user_lname = user_lname;
        this.user_role = user_role;
        this.current_id = current_id;
    }

    public String getCurrent_id() {
        return current_id;
    }

    public void setCurrent_id(String current_id) {
        this.current_id = current_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getToken() {
        return token;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public String getUser_lname() {
        return user_lname;
    }
}
