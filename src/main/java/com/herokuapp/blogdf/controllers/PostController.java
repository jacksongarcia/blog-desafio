package com.herokuapp.blogdf.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.herokuapp.blogdf.daos.PostDAO;
import com.herokuapp.blogdf.models.Post;

public class PostController {
	public List<Post> getListPost() {
		PostDAO postDAO = new PostDAO();
		return postDAO.getListPost();
	}
	
	public Post getPost(HttpServletRequest request) {
		String title = request.getParameter("article").toString();
		
		PostDAO postDAO = new PostDAO();
		return postDAO.getPost(title);
	}
}
