package com.herokuapp.blogdf.models;

public class UserSession {
	private boolean loged;
	private String name;
	private String email;
	private boolean admin;
	
	public boolean isLoged() {
		return loged;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	
	public boolean getAdmin() {
		return admin;
	}
	
	public void setLoged(boolean loged) {
		this.loged = loged;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
