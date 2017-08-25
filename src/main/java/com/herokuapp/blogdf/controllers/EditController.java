package com.herokuapp.blogdf.controllers;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.herokuapp.blogdf.daos.PostDAO;
import com.herokuapp.blogdf.models.Post;
import com.herokuapp.blogdf.models.UserSession;

public class EditController {
	public JSONObject registerPost(HttpServletRequest request, HttpServletResponse response) {		 
		Post post = insertAttibutesInPost(request);
				
		JSONObject json = new JSONObject();
		 
		json = titleValidation(post, json);
		json = titleIsValidateExist(post, json);
		json = previewArticleValidation(post, json);
		json = articleValidation(post, json);
				 		 
		response.setContentType("application/json");   
		response.setCharacterEncoding("UTF-8");
		 
		if (json.length() > 0) {
			json.put("erro", true);
			 
			return json;
			 
		} else {
			json.put("seccess", true);
			 			 			 
			PostDAO postDAO = new PostDAO();
			 
			postDAO.insert(post);
			return json;
		} 
	}
	
	private Post insertAttibutesInPost(HttpServletRequest request) {
		Post post = new Post();
		
		post.setTitle(request.getParameter("title").toString());
		post.setPreview_article(request.getParameter("preview_article").toString());
		post.setArticle(request.getParameter("article").toString());
		
		java.sql.Date date = new java.sql.Date(
		        Calendar.getInstance().getTimeInMillis());
		post.setDatePublication(date);
		
		post.setUserId(getUserId(request));

		return post;
	}
	
	private JSONObject titleValidation(Post post, JSONObject jsonError) {
		if(post.getTitle().equals("")){
			jsonError.put("title", "Campo do titulo em branco");
		}
		return jsonError;
	}
	
	private JSONObject articleValidation(Post post, JSONObject jsonError) {
		if(post.getArticle().equals("")){
			jsonError.put("article", "Artigo em branco");
		}
		return jsonError;
	}
	
	private JSONObject previewArticleValidation(Post post, JSONObject jsonError) {
		if(post.getPreview_article().equals("")){
			jsonError.put("preview_articke", "Campo de preview do artigo em branco");
		}
		return jsonError;
	}
	
	private JSONObject titleIsValidateExist(Post post, JSONObject jsonError) {
		if (post.getTitle() != null) {
			if (new PostDAO().hasTitle(post.getTitle())) {
				jsonError.put("title", "Titulo já cadastrado");
			}
		}

		return jsonError;
	}
	
	private int getUserId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession)session.getAttribute("auth");
		
		if(userSession != null && userSession.isLoged()) {
			return userSession.getId();
		}
		
		return -1;
	}
}
