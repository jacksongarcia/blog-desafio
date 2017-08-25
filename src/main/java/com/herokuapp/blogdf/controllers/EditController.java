package com.herokuapp.blogdf.controllers;

import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.herokuapp.blogdf.daos.PostDAO;
import com.herokuapp.blogdf.models.Post;
import com.herokuapp.blogdf.models.UserSession;

public class EditController {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String attributeNameSession;
	
	public EditController(HttpServletRequest request,
			HttpServletResponse response, String attributeNameSession) {
		super();
		this.request = request;
		this.response = response;
		this.attributeNameSession = attributeNameSession;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setAttributeNameSession(String attributeNameSession) {
		this.attributeNameSession = attributeNameSession;
	}

	public JSONObject registerPost() {		 
		Post post = insertAttibutesInPost();
				
		JSONObject json = new JSONObject();
		 
		json = titleValidation(post, json);
		json = titleIsValidateExist(post, json);
		json = previewArticleValidation(post, json);
		json = articleValidation(post, json);
		json = userIdValidation(post, json);
				 		 
		response.setContentType("application/json");   
		response.setCharacterEncoding("UTF-8");
		 
		if (json.length() > 0) {
			json.put("erro", true);
			 
			return json;
			 
		} else {
			 			 			 
			PostDAO postDAO = new PostDAO();
			 
			try {
				postDAO.insert(post);
			} catch (SQLException e) {
				json.put("erro", true);
				json.put("falha", "Erro inesperado ao inserir artigo, tente mais tarde");
				
				return json;
			}
			
			json.put("seccess", true);
			return json;
		} 
	}
	
	public JSONObject deletePost() {
		PostDAO post = new PostDAO();
		
		JSONObject json = new JSONObject();
				 		 
		response.setContentType("application/json");   
		response.setCharacterEncoding("UTF-8");
		
		String strId = request.getParameter("id");
		if (strId != null) {
			int id = Integer.parseInt(strId);
		
			if (post.deleteByIdWithComment(id) == false)
				post.deleteById(id);
		}
		else 
		 json.put("falha", "Tente novamente mais tarde");

		if (json.length() > 0) {
			json.put("erro", true);
			 
			return json;
			 
		} else {
			json.put("seccess", true);
			 			 			 
			return json;
		} 
	}
	
	
	private Post insertAttibutesInPost() {
		Post post = new Post();
		
		post.setTitle(request.getParameter("title"));
		post.setPreview_article(request.getParameter("preview_article"));
		post.setArticle(request.getParameter("article"));
		
		java.sql.Date date = new java.sql.Date(
		        Calendar.getInstance().getTimeInMillis());
		post.setDatePublication(date);
		
		post.setUserId(getUserId());

		return post;
	}
	
	private int getUserId() {
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession)session.getAttribute(attributeNameSession);
		
		if(userSession != null && userSession.isLogged())
			return userSession.getId();
		
		return -1;
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
			jsonError.put("preview_article", "Campo de preview do artigo em branco");
		}
		return jsonError;
	}
	
	private JSONObject titleIsValidateExist(Post post, JSONObject jsonError) {
		if (post.getTitle() != null) {
			try {
				if (new PostDAO().hasTitle(post.getTitle())) {
					jsonError.put("title", "Titulo já cadastrado");
				}
			} catch (SQLException e) {
				jsonError.put("title", "Error inesperado na validação do titulo");
				e.printStackTrace();
			}
		}

		return jsonError;
	}
	
	private JSONObject userIdValidation(Post post, JSONObject jsonError) {
		if(post.getUserId() == -1){
			jsonError.put("user_id", "Falha inesperada ao procurar id do usuario");
		}
		return jsonError;
	}

}
