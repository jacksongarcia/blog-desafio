package com.herokuapp.blogdf.controllers;

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
	public JSONObject registerComment(HttpServletRequest request, HttpServletResponse response) {		 
		Comment comment = insertAttibutesInComment(request);
				
		JSONObject json = new JSONObject();
		 
		json = commentTextValidation(comment, json);;
				 		 
		response.setContentType("application/json");   
		response.setCharacterEncoding("UTF-8");
		 
		if (json.length() > 0) {
			json.put("erro", true);
			 
			return json;
			 
		} else {
			json.put("name", getUserFullName(request));
			
			SimpleDateFormat sdff = new SimpleDateFormat("dd/MM/yyyy");

			json.put("publishDate", sdff.format(comment.getPublish()));
			json.put("seccess", true);
			 			 			 
			CommentDAO commentDAO = new CommentDAO();
			 
			commentDAO.insert(comment);
			return json;
		} 
	}
	
	private JSONObject commentTextValidation(Comment comment, JSONObject jsonError) {
		if(comment.getText().equals("")){
			jsonError.put("comment", "Campo em branco");
		}
		return jsonError;
	}
	
	private Comment insertAttibutesInComment(HttpServletRequest request) {
		Comment comment = new Comment();
		
		comment.setText(request.getParameter("comment").toString());
		
		java.sql.Date date = new java.sql.Date(
		        Calendar.getInstance().getTimeInMillis());
		comment.setPublish(date);
		
		comment.setUser_id(getUserId(request));
		
		comment.setPost_id(getIdPost(request));

		return comment;
	}
	
	private int getIdPost(HttpServletRequest request) {
		if(request.getParameter("id")!= null && request.getParameter("id").equals("") == false)
			return Integer.parseInt(request.getParameter("id").toString());
		
		return -1;
	}
	
	private int getUserId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession)session.getAttribute("auth");
		
		if(userSession != null && userSession.isLoged()) {
			return userSession.getId();
		}
		
		return -1;
	}
	
	private String getUserFullName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession)session.getAttribute("auth");
		
		if(userSession != null && userSession.isLoged()) {
			return userSession.getName() + " " +userSession.getLastName();
		}
		
		return "";
	}
	
	public JSONArray getListComment(HttpServletRequest request, HttpServletResponse response) {
		CommentDAO commentDAO = new CommentDAO();
		JSONArray jsonArray = new JSONArray();
		
		Map<Comment, User> mapComment = commentDAO.getListCommentByIdPost(getIdPost(request));
		
		response.setContentType("application/json");   
		response.setCharacterEncoding("UTF-8");
		
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
