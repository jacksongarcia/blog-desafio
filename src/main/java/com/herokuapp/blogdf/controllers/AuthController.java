package com.herokuapp.blogdf.controllers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.herokuapp.blogdf.daos.UserDAO;
import com.herokuapp.blogdf.daos.UserSessionDAO;
import com.herokuapp.blogdf.models.User;
import com.herokuapp.blogdf.models.UserSession;

public class AuthController {

	public boolean logout(HttpServletRequest request, HttpSession session) {
		if (request != null && request.getParameter("action") != null && 
				session.getAttribute("auth") != null) {
			if (request.getParameter("action").equals("logout")) {
				session.removeAttribute("auth");
				session.invalidate();
				return true;
			}
		}
		
		return false;
	}
	
	public JSONObject loginUser(HttpServletRequest request, HttpServletResponse response) {
		 
		User user = insertAttibutesInUser(request);
		 
		//JSONObject json = auth.validateParameterLogin(request);
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
			json.put("seccess", true);
			 
			HttpSession session = request.getSession();
			 
			session.setAttribute("auth", getUserSession(user));
			 
			return json;
			 
		} 
	}
	
	public JSONObject registerUser(HttpServletRequest request, HttpServletResponse response) {		 
		User user = insertAttibutesInUser(request);
		 
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
			json.put("seccess", true);
			 
			String encryptPassword = getEncryptPassword(user.getPassword());
			 
			user.setPassword(encryptPassword);
			 
			UserDAO userDAO = new UserDAO();
			 
			userDAO.insert(user);
			return json;
		} 
	}
	
	private User insertAttibutesInUser(HttpServletRequest request) {
		User user = new User();
		
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
		else if (user.getFirst_name().length() < 3) {
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
			if (new UserDAO().hasEmail(user.getEmail())) {
				jsonError.put("email", "Email já cadastrado");
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
			if (!new UserDAO().isValidatePassword(user.getEmail(), encryptPassword)) {
				jsonError.put("password", "Senha não é válida");
			}
		}

		return jsonError;
	}
		
	private JSONObject validateEmail(User user, JSONObject jsonError) {
		if (user.getEmail().equals("") == false) {
			UserDAO userDAO = new UserDAO();
			boolean emailValidate = userDAO.hasEmail(user.getEmail());
			
			if (emailValidate == false)
				jsonError.put("email", "Email não cadastrado");
		}
		return jsonError;
	}
	
	private UserSession getUserSession(User user) {
		UserSessionDAO userSessionDAO = new UserSessionDAO();
		UserSession userSession = userSessionDAO.getUser(user.getEmail());
		
		userSession.setLoged(true);
		
		return userSession;
	}

}
