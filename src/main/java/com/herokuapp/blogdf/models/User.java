package com.herokuapp.blogdf.models;

import javax.servlet.http.HttpServletRequest;

import com.herokuapp.blogdf.controllers.AuthController;

public class User {
	private int id;
	private String first_name;
	private String last_name;
	private String email;
	private String password;
	private boolean admin;
	
	public User() {}
	
	public User(HttpServletRequest request) {
		first_name = request.getParameter(AuthController.PARAMETER_REGISTER_NAME).toString();
		last_name = request.getParameter(AuthController.PARAMETER_REGISTER_LAST_NAME);
		email = request.getParameter(AuthController.PARAMETER_REGISTER_EMAIL);
		password = request.getParameter(AuthController.PARAMETER_REGISTER_PASSWORD);
	}
	
	public int getId() {
		return id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

}
