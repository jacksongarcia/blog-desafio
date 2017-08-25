package com.herokuapp.blogdf.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.herokuapp.blogdf.daos.PostDAO;
import com.herokuapp.blogdf.daos.UserDAO;
import com.herokuapp.blogdf.models.Post;
import com.herokuapp.blogdf.models.User;

public class PostController {
	private HttpServletRequest request;
	private String paramanterName;
	
	public PostController() {}
	
	public PostController(HttpServletRequest request,
							String paramanterName) {
		this.request = request;
		this.paramanterName = paramanterName;
	}
	
	public boolean formatResponseForPagePost() {	
		Post post = getPost();
		if (post == null) return false;
		
		User user = null;
		try {
			user = new UserDAO().getUserById(post.getUserId());
		} catch (SQLException e) {
			return false;
		}
		
		if (user == null) return false;
		
		request.setAttribute("post", post);
		request.setAttribute("user", user);
		
		return true;
	}
	
	public boolean isUrlValide() {
		if (request.getParameter(paramanterName) == null) return false;
		if (request.getParameter(paramanterName).equals("")) return false;
		
		return true;
	}
	
	public List<Post> getListPost() {
		PostDAO postDAO = new PostDAO();
		return postDAO.getListPost();
	}
	
	public Post getPost() {
		if (request.getParameter(paramanterName) == null)
			return null;
		
		String title = request.getParameter(paramanterName).toString();
		
		PostDAO postDAO = new PostDAO();
		return postDAO.getPost(title);
	}
}
