package com.herokuapp.blogdf.controllers;

import com.herokuapp.blogdf.daos.UserDAO;
import com.herokuapp.blogdf.models.User;

public class UserController {
	public User getUserBtId(int id) {
		
		UserDAO userDAO = new UserDAO();
		return userDAO.getUserById(id);
	}
}
