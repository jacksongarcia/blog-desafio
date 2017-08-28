package com.herokuapp.blogdf.controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.herokuapp.blogdf.daos.ConnectionFactory;
import com.herokuapp.blogdf.daos.PostDAO;
import com.herokuapp.blogdf.models.Post;
import com.herokuapp.blogdf.models.User;

public class IndexController extends URLContextController {
	public final static String PARAMETER_POST_LIST_VALUE = "LIST_POST";
	public final static String PARAMETER_POST_LIST_TOTAL_VALUE = "LIST_POST_TOTAL";
	
	public final static String PARAMETER_POST_INDEX = "INDEX";

	public boolean isURLListPost(HttpServletRequest request) {
		return isParameterType(request, PARAMETER_NAME, PARAMETER_POST_LIST_VALUE);
	}

	public boolean isURLListPostTotal(HttpServletRequest request) {
		return isParameterType(request, PARAMETER_NAME, PARAMETER_POST_LIST_TOTAL_VALUE);
	}
	
	public JSONArray getListPostTotal(HttpServletRequest request) {
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
			int postTotalLengh = new PostDAO().getLength(connection);
			jsonResponse.put(new JSONObject().put("total", postTotalLengh));

		} catch (SQLException e) {
			jsonResponse.put(new JSONObject().put(ERRO_DATA_BASE, "Error ao capturar total de artigos banco de dados"));
		}
				
		return jsonResponse;
	}

	public JSONArray getList(HttpServletRequest request) {
		JSONArray jsonResponse = new JSONArray();
		
		Connection connection = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			
		} catch (SQLException e) {
			JSONObject json = new JSONObject();
			json.put(ERRO_DATA_BASE, "Error ao abrir conexão com o banco de dados");
			
			return new JSONArray().put(json);
		}
		
		int index = validateIsNumberParameter(PARAMETER_POST_INDEX, request);
		
		try {
			Map<Post, User> listPost = new PostDAO().getListPost(index, 5, connection);
			
			for(Map.Entry<Post, User> entry: listPost.entrySet()) {
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
				
				jsonResponse.put(json);
			}
		} catch (SQLException e) {
			jsonResponse.put(new JSONObject().put(ERRO_DATA_BASE, "Error procurar artigos no banco de dados"));
		}
		
		return jsonResponse;
	}

}
