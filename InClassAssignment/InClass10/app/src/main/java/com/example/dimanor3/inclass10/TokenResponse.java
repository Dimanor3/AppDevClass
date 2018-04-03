package com.example.dimanor3.inclass10;

public class TokenResponse {
	String status, token, user_email, user_fname, user_lname, user_role;
	int user_id;

	public TokenResponse (String status, String token, int user_id, String user_email, String user_fname, String user_lname, String user_role) {
		this.status = status;
		this.token = token;
		this.user_email = user_email;
		this.user_fname = user_fname;
		this.user_lname = user_lname;
		this.user_role = user_role;
		this.user_id = user_id;
	}

	public String getStatus () {
		return status;
	}

	public void setStatus (String status) {
		this.status = status;
	}

	public String getToken () {
		return token;
	}

	public void setToken (String token) {
		this.token = token;
	}

	public String getUser_email () {
		return user_email;
	}

	public void setUser_email (String user_email) {
		this.user_email = user_email;
	}

	public String getUser_fname () {
		return user_fname;
	}

	public void setUser_fname (String user_fname) {
		this.user_fname = user_fname;
	}

	public String getUser_lname () {
		return user_lname;
	}

	public void setUser_lname (String user_lname) {
		this.user_lname = user_lname;
	}

	public String getUser_role () {
		return user_role;
	}

	public void setUser_role (String user_role) {
		this.user_role = user_role;
	}

	public int getUser_id () {
		return user_id;
	}

	public void setUser_id (int user_id) {
		this.user_id = user_id;
	}
}
