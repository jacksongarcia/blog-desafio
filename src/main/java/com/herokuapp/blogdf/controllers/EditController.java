package com.herokuapp.blogdf.controllers;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.herokuapp.blogdf.daos.ConnectionFactory;
import com.herokuapp.blogdf.daos.PostDAO;
import com.herokuapp.blogdf.models.Post;

public class EditController extends URLContextController {

	public final static String PARAMETER_POST_VALUE = "PUBLISH";
	
	public final static String PARAMETER_POST_TITLE = "POST_TITLE";
	public final static String PARAMETER_POST_PREVIEW_ARTICLE = "POST_PREVIEW_ARTICLE";
	public final static String PARAMETER_POST_ARTICLE = "POST_ARTICLE";
	
	public final static String PARAMETER_POST_EDIT_VALUE = "edit";
	public final static String PARAMETER_EDIT_DELETE_VALUE = "DELETE";
	public final static String PARAMETER_POST_ID = "POST_ID";
	public final static String PARAMETER_EDIT_SAVE_EDIT_VALUE = "EDIT";

		
	public JSONArray publish(HttpServletRequest request) {
		JSONArray jsonResponse = null;
		Connection connection = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			
		} catch (SQLException e) {
			JSONObject json = new JSONObject();
			json.put(ERRO_DATA_BASE, "Error ao abrir conexão com o banco de dados");
			
			return new JSONArray().put(json);
		}
		
		jsonResponse = validateFormatAllInputPost(request);

		try {
			if (validateInputTitleDataBase(request, PARAMETER_POST_TITLE, connection))
				jsonResponse.put(new JSONObject().put(PARAMETER_POST_TITLE, "Título já cadastrado"));
		
		} catch (SQLException e) {
			jsonResponse.put(new JSONObject().put(ERRO_DATA_BASE, "Error ao procurar título no banco de dados"));

		}
		
		// INSERE NOVO ARTIGO NO BANCO DE DADOS
		if (jsonResponse.length() == 0) {
			Post post = new Post(request);
			
			try {
				new PostDAO().insert(post, connection);
				
			} catch (Exception e) {
				jsonResponse.put(new JSONObject().put(ERRO_DATA_BASE, "Error ao publicar artigo no banco de dados"));
			
			}
		}
		
		return jsonResponse;
	}
	
	public JSONArray delete(HttpServletRequest request) {
		JSONArray jsonResponse = new JSONArray();
		Connection connection = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			
		} catch (SQLException e) {
			JSONObject json = new JSONObject();
			json.put(ERRO_DATA_BASE, "Error ao abrir conexão com o banco de dados");
			
			return new JSONArray().put(json);
		}
		
		int postId = validateIsNumberParameter(PARAMETER_POST_ID, request);

		try {
			new PostDAO().delete(postId, connection);
			
		} catch (SQLException e) {
			jsonResponse.put(new JSONObject().put(ERRO_DATA_BASE, "Error ao deletar artigo no banco de dados"));
		
		}
		
		return jsonResponse;
	}
	
	public JSONArray edit(HttpServletRequest request) {
		JSONArray jsonResponse = null;
		Connection connection = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			
		} catch (SQLException e) {
			JSONObject json = new JSONObject();
			json.put(ERRO_DATA_BASE, "Error ao abrir conexão com o banco de dados");
			
			return new JSONArray().put(json);
		}
		
		jsonResponse = validateFormatAllInputPost(request);
		
		// INSERE NOVO ARTIGO NO BANCO DE DADOS
		if (jsonResponse.length() == 0) {
			Post post = new Post(request);
			int postId = validateIsNumberParameter(PARAMETER_POST_ID, request);

			try {
				new PostDAO().edit(postId,post, connection);
				
			} catch (SQLException e) {
				jsonResponse.put(new JSONObject().put(ERRO_DATA_BASE, "Error ao salva edição do artigo no banco de dados"));
			
			}
		}
		
		return jsonResponse;
	}
	
	public boolean isURLPublish(HttpServletRequest request) {
		return isParameterType(request, PARAMETER_NAME, PARAMETER_POST_VALUE);
	}
	
	public boolean isURLEdit(HttpServletRequest request) {
		return isParameterType(request, PARAMETER_NAME, PARAMETER_POST_EDIT_VALUE);
	}

	public boolean isURLDelete(HttpServletRequest request) {
		return isParameterType(request, PARAMETER_NAME, PARAMETER_EDIT_DELETE_VALUE);
	}
	
	public boolean isURLEditPost(HttpServletRequest request) {
		return isParameterType(request, PARAMETER_NAME, PARAMETER_EDIT_SAVE_EDIT_VALUE);
	}
	
	/**
	 * Faz a validação dos formatos de todos os inputs de registro.
	 * 
	 * @param request
	 * @return
	 */
	public JSONArray validateFormatAllInputPost(HttpServletRequest request) {
		JSONArray jsonResponse = new JSONArray();
		JSONObject json = null;
		
		// INPUT DO TITLE
		json = validateFormatTitle(request);
		if (json != null) jsonResponse.put(json);

		// INPUT DO TEXTO DE PREVIEW
		json = validateFormatTextPreview(request);
		if (json != null) jsonResponse.put(json);
		
		// INPUT DO TEXTO DE ARTICLE
		json = validateFormatAricle(request);
		if (json != null) jsonResponse.put(json);
		
		return jsonResponse;
	}

	protected boolean validateInputTitleDataBase(HttpServletRequest request,
			String parameterPostTitile, Connection connection) throws SQLException {
		if (isEmptyParameter(request, parameterPostTitile) == false)
			if (new PostDAO().hasTitile(request.getParameter(parameterPostTitile).toString(), connection))
				return true;
		
		return false;
	}

	protected JSONObject validateFormatAricle(HttpServletRequest request) {
		if (isEmptyParameter(request, PARAMETER_POST_ARTICLE))
			return new JSONObject().put(PARAMETER_POST_ARTICLE, "Preencha o campo de artigo na aba editar");
				
		return null;
	}

	protected JSONObject validateFormatTextPreview(HttpServletRequest request) {
		if (isEmptyParameter(request, PARAMETER_POST_PREVIEW_ARTICLE))
			return new JSONObject().put(PARAMETER_POST_PREVIEW_ARTICLE, "Preencha o campo de preview do artigo na aba preview");
				
		return null;
	}

	protected JSONObject validateFormatTitle(HttpServletRequest request) {
		if (isEmptyParameter(request, PARAMETER_POST_TITLE))
			return new JSONObject().put(PARAMETER_POST_TITLE, "Preencha o campo de título na aba preview");
		
		if (request.getParameter(PARAMETER_POST_TITLE).toString().length() < 6)
			return new JSONObject().put(PARAMETER_POST_TITLE, "Seu título deve ser maior que 6 digitos");
				
		return null;
	}


}
