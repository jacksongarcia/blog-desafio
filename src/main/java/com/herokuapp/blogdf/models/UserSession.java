package com.herokuapp.blogdf.models;

public class UserSession {
	private boolean loged;
	private int id;
	private String name;
	private String lastName;
	private String email;
	private boolean admin;
	
	public boolean isLoged() {
		return loged;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public void setLoged(boolean loged) {
		this.loged = loged;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
