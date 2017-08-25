package com.herokuapp.blogdf.models;

public class UserSession {
	private boolean logged;
	private int id;
	private String name;
	private String lastName;
	private String email;
	private boolean admin;
	
	public boolean isLogged() {
		return logged;
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
	
	public void setLogged(boolean logged) {
		this.logged = logged;
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
