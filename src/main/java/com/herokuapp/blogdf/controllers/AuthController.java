package com.herokuapp.blogdf.controllers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.herokuapp.blogdf.daos.UserDAO;
import com.herokuapp.blogdf.daos.UserSessionDAO;
import com.herokuapp.blogdf.models.User;
import com.herokuapp.blogdf.models.UserSession;

public class AuthController {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private String attributeNameSession;
	
	/**
	 * 
	 * @param request - HttpRequest
	 * @param response - HttpServletResponse
	 * @param attributeNameSession - Nome da sessão a ser manipulada
	 * @param paramanterName - Nome do paramentro que será passo na url
	 * @param paramanterValue - Valor do para padrão
	 */
	public AuthController(HttpServletRequest request, HttpServletResponse response,
			String attributeNameSession) {
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
	
	/**
	 * Iniciar o logout do usuário matando a sessão
	 * 
	 * @return boolean
	 */
	public boolean logOut() {		
		HttpSession session = request.getSession();
		if (session == null) return false;
		
		session.removeAttribute(attributeNameSession);
		session.invalidate();
	
		return true;
	}
	
	/**
	 * Retorna true caso o usuário esteja logado
	 * 
	 * @return boolean
	 */
	public UserSession userLogged() {
		HttpSession session = request.getSession(false);
		
		if (session == null) return null;
		
		UserSession userSession = (UserSession)session.getAttribute(attributeNameSession);
		if (userSession == null) return null;
		
		if (userSession.isLogged() == false) return null;
		
		return userSession;
	}	
	
	public JSONObject registerUser() {		 
		User user = insertAttibutesInUser();
		 
		JSONObject json = new JSONObject();
		 
		json = firstNameValidation(user, json);
		json = lastNameValidation(user, json);
		json = emailValidation(user, json);
		json = emailIsValidateAccount(user, json);
		json = passwordValidation(user, json);
		 
		json = confirmPasswordValidation(request, json);
		 
		json = isPasswordMatch(json, user, request);
		 
		response.setContentType("application/json");   
		response.setCharacterEncoding("UTF-8");
		 
		if (json.length() > 0) {
			json.put("erro", true);
			 
			return json;
			 
		} else { 
			String encryptPassword = getEncryptPassword(user.getPassword());
			user.setPassword(encryptPassword);
			 
			UserDAO userDAO = new UserDAO();
			
			try {
				userDAO.insert(user);
				
			} catch (SQLException e) {
				json.put("falha", "Falha ao tentar cadastrar usuário no banco, tente mais tarde.");
				json.put("erro", true);
				
				return json;
			}
			
			json.put("seccess", true);
			return json;
		} 
	}
	
	/**
	 * Retorna o objeto UserSession populado com os atributos do usuario do banco
	 * 
	 * @param user
	 * @return UserSession
	 */
	private UserSession getUserSession(User user) {
		UserSessionDAO userSessionDAO = new UserSessionDAO();
		UserSession userSession;
		try {
			userSession = userSessionDAO.getUser(user.getEmail());
		} catch (SQLException e) {
			return null;
		}
		
		userSession.setLogged(true);
		
		return userSession;
	}
	
	public JSONObject logIn() {
		 
		User user = insertAttibutesInUser();
		 
		JSONObject json = new JSONObject();
		 
		json = emailValidation(user, json);
		json = validateEmail(user, json);
		json = passwordValidation(user, json);
		json = passwordIsValidateAccount(user, json);
		 
		response.setContentType("application/json");   
		response.setCharacterEncoding("UTF-8");
		 
		if (json.length() > 0) {
			json.put("erro", true);
			 
			return json;
			 
		} else {
			
			UserSession userSession = getUserSession(user);
			if (userSession == null) {
				json.put("erro", true);
				json.put("falha", "Falha inesperada, tente mais tarde");
				
				return json;
			}
			
			json.put("seccess", true);
			
			HttpSession session = request.getSession();
			if (session != null)
				session.setAttribute("auth", userSession);
			 
			return json; 
		} 
	}
	
	/**
	 * Popula os atributos de user a partir do paramentros do request
	 * 
	 * @return User
	 */
	private User insertAttibutesInUser() {
		User user = new User();
		
		if (request == null) 
			throw new NullPointerException("HttpServletRequest is NULL");
		
		user.setFirst_name(request.getParameter("first_name"));
		user.setLast_name(request.getParameter("last_name"));
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));
		
		return user;
	}
	
	private JSONObject firstNameValidation(User user, JSONObject jsonError) {		
		if(user.getFirst_name().equals("")){
			jsonError.put("first_name", "Campo de nome em branco");
		}
		else if (user.getFirst_name().length() < 3) {
			jsonError.put("first_name", "Nome inválido, coloque um nome maior que 3 letras");
		}
		
		return jsonError;
	}
	
	private JSONObject lastNameValidation(User user, JSONObject jsonError) {		
		if(user.getLast_name().equals("")){
			jsonError.put("last_name", "Campo de sobrenome em branco");
		}
		else if (user.getLast_name().length() < 3) {
			jsonError.put("last_name", "Sobrenome inválido, coloque um nome maior que 3 letras");
		}
		
		return jsonError;
	}

	private JSONObject emailValidation(User user, JSONObject jsonError) {
		if(user.getEmail().equals("")){
			jsonError.put("email", "Campo de email em branco");
		}
		
		return jsonError;
	}
	
	private JSONObject emailIsValidateAccount(User user, JSONObject jsonError) {
		if (user.getEmail() != null) {
			try {
				if (new UserDAO().hasEmail(user.getEmail())) {
					jsonError.put("email", "Email já cadastrado");
				}
			} catch (SQLException e) {
				jsonError.put("email", "Tivemos uma falha ao procurar seu email no nosso registro, tente mais tarde");
			}
		}

		return jsonError;
	}
	
	private JSONObject passwordValidation(User user, JSONObject jsonError) {
		if (user.getPassword().equals("")) {
			jsonError.put("password", "Campo de senha em branco");
		}
		else if (user.getPassword().length() < 5) {
			jsonError.put("password", "A senha deve ser maior que 6 caracteres");
		}
		
		return jsonError;
	}

	private JSONObject confirmPasswordValidation(HttpServletRequest request, JSONObject jsonError) {
		if (request.getParameter("confirm_password") == null) {
			jsonError.put("confirm_password", "Erro inesperado no campo de confirmação de senha");
		}
		else if (request.getParameter("confirm_password").equals("")) {
			jsonError.put("confirm_password", "Campo de confitmação de senha em branco");
		}
		else if (request.getParameter("confirm_password").length() < 5) {
			jsonError.put("confirm_password", "A senha de confirmação deve ser maior que 6 caracteres");
		}
		
		return jsonError;
	}

	private JSONObject isPasswordMatch(JSONObject jsonError, User user, HttpServletRequest request) {
		
		if(user.getPassword() != null && request.getParameter("confirm_password") != null){
			String password = user.getPassword();
			String confirmPassword = request.getParameter("confirm_password");

			if (password.equals("") == false && confirmPassword.equals("") == false) {
				if (password.equals(confirmPassword) == false) 
					jsonError.put("password", "Senhas não coincidem");
			}
		}
		
		return jsonError;
	}
	
	private String getEncryptPassword(String password) {
		MessageDigest algorithm;
		StringBuilder hexString = null;
		
		try {
			algorithm = MessageDigest.getInstance("SHA-256");
	
			byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));
	
			hexString = new StringBuilder();
			for (byte b : messageDigest) {
			  hexString.append(String.format("%02X", 0xFF & b));
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if (hexString != null)
			return hexString.toString();
		
		return null;
	}

	private JSONObject passwordIsValidateAccount(User user, JSONObject jsonError) {
		if (user.getEmail() != null && user.getPassword() != null) {
			String encryptPassword = getEncryptPassword(user.getPassword());
			try {
				if (!new UserDAO().isValidatePassword(user.getEmail(), encryptPassword)) {
					jsonError.put("password", "Senha não é válida");
				}
			} catch (SQLException e) {
				jsonError.put("password", "Tivemos problemas ao procurar ua senha no nosso banco, tente mais tarde");
				e.printStackTrace();
			}
		}

		return jsonError;
	}
		
	private JSONObject validateEmail(User user, JSONObject jsonError) {
		if (user.getEmail().equals("") == false) {
			UserDAO userDAO = new UserDAO();
			
			boolean emailValidate = false;
			
			try {
				emailValidate = userDAO.hasEmail(user.getEmail());

				if (emailValidate == false)
					jsonError.put("email", "Email não cadastrado");
				
			} catch (SQLException e) {
				jsonError.put("email", "Tivemos uma falha ao procurar seu email no nosso registro, tente mais tarde");
			}
		}
		return jsonError;
	}


}
