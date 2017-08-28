package com.herokuapp.blogdf.controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.herokuapp.blogdf.daos.CommentDAO;
import com.herokuapp.blogdf.daos.ConnectionFactory;
import com.herokuapp.blogdf.daos.PostDAO;
import com.herokuapp.blogdf.models.Comment;
import com.herokuapp.blogdf.models.Post;
import com.herokuapp.blogdf.models.User;

public class PostController extends URLContextController {
	public static final String PARAMETER_POST_VIEW_VALUE = "postview";
	public static final String PARAMETER_POST_TITLE = "title";
	
	public static final String PARAMETER_POST_COMMENT_VALUE = "GET_COMMENT";
	public static final String PARAMETER_POST_COMMENT_ID = "ID";
	public final static String PARAMETER_POST_COMMENT_INDEX = "INDEX";

	public final static String PARAMETER_POST_REGISTER_COMMENT_VALUE = "REGISTER_COMMENT";
	public final static String PARAMENTER_COMMENT_INPUT_TEXT = "INPUT_TEXT";


	public boolean isURLPostView(HttpServletRequest request) {
		return isParameterType(request, PARAMETER_NAME, PARAMETER_POST_VIEW_VALUE);
	}
	
	public boolean isURLGetComment(HttpServletRequest request) {
		return isParameterType(request, PARAMETER_NAME, PARAMETER_POST_COMMENT_VALUE);
	}
	
	public boolean isURLRegisterComment(HttpServletRequest request) {
		return isParameterType(request, PARAMETER_NAME, PARAMETER_POST_REGISTER_COMMENT_VALUE);
	}

	public HttpServletRequest getPost(HttpServletRequest request) {
		if (validateFormatTitle(request) == false) {
			return null;
		}
		
		Connection connection = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			
		} catch (SQLException e) {
			return null;
		}
		
		try {
			Map<Post, User> mapPost = new PostDAO().getPost(request.getParameter(PARAMETER_POST_TITLE).toString(), connection);
			
			if(mapPost.isEmpty()) return null;
			
			for(Map.Entry<Post, User> entry : mapPost.entrySet()) {
			
				JSONObject json = new JSONObject();
				json.put("id", entry.getKey().getId());
				json.put("title", entry.getKey().getTitle());
				json.put("preview_article", entry.getKey().getPreview_article());
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				json.put("date_publication", dateFormat.format(entry.getKey().getDatePublication()));
				json.put("user_id", entry.getKey().getUserId());
				
				json.put("first_name", entry.getValue().getFirst_name());
				json.put("last_name", entry.getValue().getLast_name());
				json.put("email", entry.getValue().getEmail());
				
				request.setAttribute("post", entry.getKey());
				request.setAttribute("user", entry.getValue());
			}
				
		} catch (SQLException e) {
			return null;
		}
		
		return request;
	}
	
	public JSONArray getListPostCommentTotal(HttpServletRequest request) {
		JSONArray jsonResponse = new JSONArray();
		
		Connection connection = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			
		} catch (SQLException e) {
			JSONObject json = new JSONObject();
			json.put(ERRO_DATA_BASE, "Error ao abrir conexão com o banco de dados");
			
			return new JSONArray().put(json);
		}
						
		try {
			int postId = validateIsNumberParameter(PARAMETER_POST_COMMENT_ID, request);
			int postCommentTotalLengh = new CommentDAO().getLength(postId, connection);
			jsonResponse.put(new JSONObject().put("total", postCommentTotalLengh));

		} catch (SQLException e) {
			jsonResponse.put(new JSONObject().put(ERRO_DATA_BASE, "Error ao capturar total de comentários banco de dados"));
		}
				
		return jsonResponse;
	}
	
	/**
	 * Retorna uma array de JSON como todo os comentios do post
	 * e os dados do usuário de fez o comentário associado.
	 * 
	 * @return JSONArray
	 */
	public JSONArray getListComment(HttpServletRequest request) {		
		JSONArray jsonResponse = new JSONArray();
		Connection connection = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			
		} catch (SQLException e) {
			JSONObject json = new JSONObject();
			json.put(ERRO_DATA_BASE, "Error ao abrir conexão com o banco de dados");
			
			return new JSONArray().put(json);
		}
		
		int index = validateIsNumberParameter(PARAMETER_POST_COMMENT_INDEX, request);
		int postId = validateIsNumberParameter(PARAMETER_POST_COMMENT_ID, request);

		try {
			Map<Comment, User> listComment = new CommentDAO().getListComment(postId, index, 5, connection);
			
			for(Map.Entry<Comment, User> entry: listComment.entrySet()) {
				JSONObject json = new JSONObject();
				json.put("text", entry.getKey().getText());
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				json.put("publish", dateFormat.format(entry.getKey().getPublish()));
				
				json.put("first_name", entry.getValue().getFirst_name());
				json.put("last_name", entry.getValue().getLast_name());
				json.put("email", entry.getValue().getEmail());
				
				jsonResponse.put(json);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			jsonResponse.put(new JSONObject().put(ERRO_DATA_BASE, "Error procurar comentários no banco de dados"));
		}
		
		return jsonResponse;
	}
	
	public JSONArray registerComment(HttpServletRequest request) {
		JSONArray jsonResponse = new JSONArray();
		Connection connection = null;
		
		System.out.println("cheguei");
		try {
			connection = ConnectionFactory.getConnection();
			
		} catch (SQLException e) {
			JSONObject json = new JSONObject();
			json.put(ERRO_DATA_BASE, "Error ao abrir conexão com o banco de dados");
			
			return new JSONArray().put(json);
		}
		
		int postId = validateIsNumberParameter(PARAMETER_POST_COMMENT_ID, request);
		User user = AuthController.session.getUser(request);
		String text = request.getParameter(PARAMENTER_COMMENT_INPUT_TEXT).toString();
		Date publish = new Date(Calendar.getInstance().getTimeInMillis());
		
		if (validateFormatText(request)) return jsonResponse;
		
		try {
			new CommentDAO().insertComment(postId, user.getId(), text, publish, connection);
			
			JSONObject json = new JSONObject();
			json.put("text", text);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			json.put("publish", dateFormat.format(publish));
			
			json.put("first_name", user.getFirst_name());
			json.put("last_name", user.getLast_name());
			json.put("email", user.getEmail());
			
			jsonResponse.put(json);
		} catch (SQLException e) {
			e.printStackTrace();
			jsonResponse.put(new JSONObject().put(ERRO_DATA_BASE, "Error ao registrar comentários no banco de dados"));
		}
		
		return jsonResponse;
	}
	
	protected boolean validateFormatText(HttpServletRequest request) {
		if (isEmptyParameter(request, PARAMENTER_COMMENT_INPUT_TEXT))
			return false;		
		if (request.getParameter(PARAMENTER_COMMENT_INPUT_TEXT).toString().length() < 6)
			return false;
		
		return false;
	}

	protected boolean validateFormatTitle(HttpServletRequest request) {
		if (isEmptyParameter(request, PARAMETER_POST_TITLE))
			return false;		
		if (request.getParameter(PARAMETER_POST_TITLE).toString().length() < 6)
			return false;		
				
		return true;
	}

}
