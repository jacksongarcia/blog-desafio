package com.herokuapp.blogdf.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.herokuapp.blogdf.models.User;

public class SessionController {
	
	public final static String SESSION_NAME = "auth";
	
	/**
	 * Cria uma sessao
	 * 
	 * @param request
	 * @param sessionName
	 * @param user
	 */
	public void initSessionUser(HttpServletRequest request, User user) {
		HttpSession session = request.getSession();
		session.setAttribute(SESSION_NAME, user);
	}
	
	public boolean isLogged(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		if((User)session.getAttribute(SESSION_NAME) == null)
			return false;

		return true;
	}

	public boolean isAdmin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		User user = (User)session.getAttribute(SESSION_NAME);
		if (user == null) return false;
		if (user.isAdmin() == false) return false;
		
		return true;
	}
	
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		session.removeAttribute(SESSION_NAME);
		session.invalidate();
	}

	public User getUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		return (User)session.getAttribute(SESSION_NAME);
	}
}
