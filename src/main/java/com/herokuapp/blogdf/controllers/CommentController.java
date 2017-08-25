package com.herokuapp.blogdf.controllers;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.herokuapp.blogdf.daos.CommentDAO;
import com.herokuapp.blogdf.models.Comment;
import com.herokuapp.blogdf.models.User;
import com.herokuapp.blogdf.models.UserSession;

public class CommentController {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String attributeNameSession;
	
	public CommentController(HttpServletRequest request,
			HttpServletResponse response, String attributeNameSession) {
		super();
		this.request = request;
		this.response = response;
		this.attributeNameSession = attributeNameSession;
	}
	
	/**
	 * Insiro todos os atrbutos de comentário, faço a validação
	 * do texto, caso não não tenha nenhum erro formata o json, insere
	 * o comentário no banco e envia o json.
	 * 
	 * @param request
	 * @param response
	 * @return JSONObject
	 */
	public JSONObject registerComment() {		 
		Comment comment = insertAttibutesInComment();
				
		JSONObject json = new JSONObject();
		 
		json = commentTextValidation(comment, json);;
				 		 
		response.setContentType("application/json");   
		response.setCharacterEncoding("UTF-8");
		 
		if (json.length() > 0) {
			json.put("erro", true);
			 
			return json;
			 
		} else {
			json.put("name", getUserFullName());
			
			SimpleDateFormat sdff = new SimpleDateFormat("dd/MM/yyyy");

			json.put("publishDate", sdff.format(comment.getPublish()));
			 			 			 
			CommentDAO commentDAO = new CommentDAO();
			 
			try {
				commentDAO.insert(comment);
			} catch (SQLException e) {
				json.put("erro", true);
				json.put("falha", "Tivemos um problem ao inserir seu comentario tente mais tarde.");
			}
			
			json.put("seccess", true);
			return json;
		} 
	}
	
	private Comment insertAttibutesInComment() {
		Comment comment = new Comment();
		
		comment.setText(request.getParameter("comment"));
		
		java.sql.Date date = new java.sql.Date(
		        Calendar.getInstance().getTimeInMillis());
		comment.setPublish(date);
		
		comment.setUser_id(getUserId());
		
		comment.setPost_id(getIdPost());

		return comment;
	}
	
	private JSONObject commentTextValidation(Comment comment, JSONObject jsonError) {
		if(comment.getText() == null && comment.getText().equals("")) 
			jsonError.put("comment", "Campo em branco");
		
		return jsonError;
	}
	
	private int getIdPost() {
		if(request.getParameter("id")!= null && request.getParameter("id").equals("") == false)
			return Integer.parseInt(request.getParameter("id").toString());
		
		return -1;
	}
	
	private int getUserId() {
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession)session.getAttribute("auth");
		
		if(userSession != null && userSession.isLogged()) {
			return userSession.getId();
		}
		
		return -1;
	}
	
	private String getUserFullName() {
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession)session.getAttribute(attributeNameSession);
		
		if(userSession != null && userSession.isLogged()) {
			return userSession.getName() + " " +userSession.getLastName();
		}
		
		return "";
	}
	
	/**
	 * Retorna uma array de JSON como todo os comentios do post
	 * e os dados do usuário de fez o comentário associado.
	 * 
	 * @return JSONArray
	 */
	public JSONArray getListComment() {
		CommentDAO commentDAO = new CommentDAO();
		JSONArray jsonArray = new JSONArray();
		
		response.setContentType("application/json");   
		response.setCharacterEncoding("UTF-8");
		
		Map<Comment, User> mapComment = null;
		try {
			mapComment = commentDAO.getListCommentByIdPost(getIdPost());
		} catch (SQLException e) {
			return jsonArray;
		}
		
		if (mapComment == null)
			return jsonArray;
		
		for(Map.Entry<Comment, User> entry: mapComment.entrySet()) {
			
			JSONObject json = new JSONObject();
			json.put("text", entry.getKey().getText());
			json.put("publish", entry.getKey().getPublish());
			
			json.put("name", entry.getValue().getFirst_name() + " " + entry.getValue().getLast_name());
			SimpleDateFormat sdff = new SimpleDateFormat("dd/MM/yyyy");
			
			json.put("publish", sdff.format(entry.getKey().getPublish()));
			
			jsonArray.put(json);
		}
		
		return jsonArray;
	}
	
}
