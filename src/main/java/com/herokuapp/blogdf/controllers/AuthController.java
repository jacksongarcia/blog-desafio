package com.herokuapp.blogdf.controllers;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.herokuapp.blogdf.daos.ConnectionFactory;
import com.herokuapp.blogdf.daos.UserDAO;
import com.herokuapp.blogdf.models.User;

public class AuthController extends URLContextController {
	
	public final static String PARAMETER_REGISTER_VALUE = "REGISTER";
	public final static String PARAMETER_LOGIN_VALUE = "LOGIN";
	public final static String PARAMETER_LOGOUT_VALUE = "logout";
	
	// INPUTS DE CADASTRO
	public final static String PARAMETER_REGISTER_NAME = "REGISTER_NAME";
	public final static String PARAMETER_REGISTER_LAST_NAME = "REGISTER_LAST_NAME";
	public final static String PARAMETER_REGISTER_EMAIL = "REGISTER_EMAIL";
	public final static String PARAMETER_REGISTER_PASSWORD = "REGISTER_PASSWORD";
	public final static String PARAMETER_REGISTER_CONFIRM_PASSWORD = "REGISTER_CONFIRM_PASSWORD";

	// INPUTS DE LOGIN
	public final static String PARAMETER_LOGIN_EMAIL = "LOGIN_EMAIL";
	public final static String PARAMETER_LOGIN_PASSWORD = "LOGIN_PASSWORD";
	
	public static SessionController session = new SessionController();

	/**
	 * Registra um novo usuario
	 * 
	 * @param request
	 * @return
	 */
	public JSONArray register(HttpServletRequest request) {
		JSONArray jsonResponse = null;
		Connection connection = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			
		} catch (SQLException e) {
			JSONObject json = new JSONObject();
			json.put(ERRO_DATA_BASE, "Error ao abrir conexão com o banco de dados");
			
			return new JSONArray().put(json);
		}
		
		jsonResponse = validateFormatAllInputRegister(request);
		
		try {
			if (validateInputEmailDataBase(request, PARAMETER_REGISTER_EMAIL, connection))
				jsonResponse.put(new JSONObject().put(PARAMETER_REGISTER_EMAIL, "Email já cadastrado"));
		
		} catch (SQLException e) {
			jsonResponse.put(new JSONObject().put(ERRO_DATA_BASE, "Error ao procurar email no banco de dados"));

		}
		
		// INSERE NOVO USUARIO NO BANCO DE DADOS
		if (jsonResponse.length() == 0) {
			User user = new User(request);
			try {
				new UserDAO().insert(user, connection);
				
			} catch (SQLException e) {
				jsonResponse.put(new JSONObject().put(ERRO_DATA_BASE, "Error ao cadastrar no banco de dados"));
			
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
				jsonResponse.put(new JSONObject().put(ERRO_DATA_BASE, "Error criptar a senha"));
			} 
		}
		
		return jsonResponse;
	}
	
	/**
	 * Faz o login do usuário caso esteja tudo ok
	 * @param request
	 * @return
	 */
	public JSONArray login(HttpServletRequest request) {
		JSONArray jsonResponse = null;
		Connection connection = null;
		UserDAO userDAO = new UserDAO();
		
		try {
			connection = ConnectionFactory.getConnection();
			
		} catch (SQLException e) {
			JSONObject json = new JSONObject();
			json.put(ERRO_DATA_BASE, "Error ao abrir conexão com o banco de dados");
			
			return new JSONArray().put(json);
		}
		
		jsonResponse = validateFormatAllInputLogin(request, userDAO, connection);

		try {
			if (validateInputEmailDataBase(request, PARAMETER_LOGIN_EMAIL, connection) == false)
				jsonResponse.put(new JSONObject().put(PARAMETER_LOGIN_EMAIL, "Email não cadastrado"));
		
		} catch (SQLException e) {
			jsonResponse.put(new JSONObject().put(ERRO_DATA_BASE, "Error ao procurar email no banco de dados"));

		}
		
		// RETORNA CASO TENHA ALGUM ERRO ENCONTRADO
		if (jsonResponse.length() > 0) return jsonResponse;
		
		try {
			User user = userDAO.haveUserWithEmailAndPassword(
					request.getParameter(PARAMETER_LOGIN_EMAIL), 
					request.getParameter(PARAMETER_LOGIN_PASSWORD), 
					connection);
			
			if (user != null) 
				session.initSessionUser(request, user);
			else
				jsonResponse.put(new JSONObject().put(PARAMETER_LOGIN_PASSWORD, "Senha incorreta"));

		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			jsonResponse.put(new JSONObject().put(ERRO_DATA_BASE, "Error criptar a senha"));

		} catch (SQLException e) {
			jsonResponse.put(new JSONObject().put(ERRO_DATA_BASE, "Error ao procurar dados no banco de dados"));
		}
		
		return jsonResponse;
	}
	
	/**
	 * Se não tiver um usuário logado retorn -1
	 * 
	 * @return
	 */
	public int getUserIdOfSession(HttpServletRequest request) {
		User user = session.getUser(request);
		if (user != null)
			return user.getId();
		
		return -1;
	}
	
	public boolean isURLRegister(HttpServletRequest request) {
		return isParameterType(request, PARAMETER_NAME, PARAMETER_REGISTER_VALUE);
	}
	
	public boolean isURLLogin(HttpServletRequest request) {
		return isParameterType(request, PARAMETER_NAME, PARAMETER_LOGIN_VALUE);
	}
	
	public boolean isURLLogout(HttpServletRequest request) {
		return isParameterType(request, PARAMETER_NAME, PARAMETER_LOGOUT_VALUE);
	}

	/**
	 * Faz a validação dos formatos de todos os inputs de registro.
	 * 
	 * @param request
	 * @return
	 */
	public JSONArray validateFormatAllInputRegister(HttpServletRequest request) {
		JSONArray jsonResponse = new JSONArray();
		JSONObject json = null;
		
		// INPUT DO NOME
		json = validateFormatEmail(request, PARAMETER_REGISTER_EMAIL);
		if (json != null) jsonResponse.put(json);
		
		// INPUT DO SOBRENOME
		json = validateFormatLastName(request);
		if (json != null) jsonResponse.put(json);

		// INPUT DO EMAIL
		json = validateFormatEmail(request, PARAMETER_REGISTER_EMAIL);
		if (json != null) jsonResponse.put(json);


		// INPUT DA SENHA
		json = validateFormatPassword(request, PARAMETER_REGISTER_PASSWORD);
		if(json != null) jsonResponse.put(json);

		// INPUT DA CONFIRMAÇÃO DE SENHA
		json = validateFormatConfirmPassword(request);
		if(json != null) jsonResponse.put(json);


		// SE SENHA SÃO IGUAIS
		json = validateFormatEqualsPassword(request);
		if(json != null) jsonResponse.put(json);

		return jsonResponse;
	}
	
	public JSONArray validateFormatAllInputLogin(HttpServletRequest request, UserDAO userDAO, Connection connection) {
		JSONArray jsonResponse = new JSONArray();
		JSONObject json = null;
		
		// INPUT EMAIL
		json = validateFormatEmail(request, PARAMETER_LOGIN_EMAIL);
		if (json != null) jsonResponse.put(json);
		
		// INPUT SENHA
		json = validateFormatPassword(request, PARAMETER_LOGIN_PASSWORD);
		if (json != null) jsonResponse.put(json);

		return jsonResponse;
	}
	
	protected JSONObject validateFormatEqualsPassword(HttpServletRequest request) {
		if (!isEmptyParameter(request, PARAMETER_REGISTER_PASSWORD) && !isEmptyParameter(request, PARAMETER_REGISTER_CONFIRM_PASSWORD))
			if (!request.getParameter(PARAMETER_REGISTER_PASSWORD).equals(request.getParameter(PARAMETER_REGISTER_CONFIRM_PASSWORD)))
				return new JSONObject().put(PARAMETER_REGISTER_PASSWORD, "Suas senhas não coincidem");
		
		return null;
	}

	protected JSONObject validateFormatConfirmPassword(HttpServletRequest request) {
		if (isEmptyParameter(request, PARAMETER_REGISTER_CONFIRM_PASSWORD))
			return new JSONObject().put(PARAMETER_REGISTER_CONFIRM_PASSWORD, "Preencha o campo de confirmar senha");
		
		if (request.getParameter(PARAMETER_REGISTER_CONFIRM_PASSWORD).length() < 6)
			return new JSONObject().put(PARAMETER_REGISTER_CONFIRM_PASSWORD, "Sua senha de confirmação deve ser maior que 6 digitos");
				
		return null;
	}

	protected JSONObject validateFormatEmail(HttpServletRequest request, String paramenterName) {
		if (isEmptyParameter(request, paramenterName))
			return new JSONObject().put(paramenterName, "Preencha o campo de email");
		
		return null;
	}

	protected JSONObject validateFormatPassword(HttpServletRequest request, String paramenterName) {
		if (isEmptyParameter(request, paramenterName))
			return new JSONObject().put(paramenterName, "Preencha o campo de senha");
		
		if (request.getParameter(paramenterName).length() < 6)
			return new JSONObject().put(paramenterName, "Sua senha deve ser maior que 6 digitos");
		
		return null;
	}

	protected JSONObject validateFormatLastName(HttpServletRequest request) {
		if (isEmptyParameter(request, PARAMETER_REGISTER_LAST_NAME))
			return new JSONObject().put(PARAMETER_REGISTER_LAST_NAME, "Preencha o campo de sobrenome");
		
		String nameKey = request.getParameter(PARAMETER_REGISTER_LAST_NAME);
		if (nameKey.length() < 3)
			return new JSONObject().put(PARAMETER_REGISTER_LAST_NAME, "Seu sobrenome deve ser maior que tres");
		
		return null;
	}
	
	/**
	 * Procura no banco de dados se já existe esse email cadastrado
	 * 
	 * @param request
	 * @param userDAO
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	protected boolean validateInputEmailDataBase(HttpServletRequest request,
			String parameterName, Connection connection) throws SQLException {
		if (isEmptyParameter(request, parameterName) == false)
			if (new UserDAO().hasEmail(request.getParameter(parameterName), connection))
				return true;
		
		return false;
	}

}
