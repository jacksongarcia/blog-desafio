package com.herokuapp.blogdf.controllers;

import java.util.List;

import com.herokuapp.blogdf.daos.PostDAO;
import com.herokuapp.blogdf.models.Post;

public class PostController {
	public List<Post> getListPost() {
		PostDAO postDAO = new PostDAO();
		return postDAO.getListPost();
	}
}
