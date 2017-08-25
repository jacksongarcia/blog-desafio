package com.herokuapp.blogdf.servelets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.herokuapp.blogdf.controllers.AuthController;
import com.herokuapp.blogdf.controllers.Helpers;

@WebServlet(name="AuthServelet", urlPatterns = {"/auth", "/logout", "/login"}) 
public class AuthServelet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
			try {
				request.getRequestDispatcher("auth.jsp").forward(request, response);
				
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) {		 
		 JSONObject jsonOfResponse = null;
		 
		 if (new Helpers().isParamanterInUrl(request, "type", "register")) 
			 jsonOfResponse = new AuthController(request, response, "auth").registerUser();
		 
		 if (new Helpers().isParamanterInUrl(request, "type", "login")) 
			 jsonOfResponse = new AuthController(request, response, "auth").logIn();
		 
		 if(jsonOfResponse.has("erro") == false)
			 jsonOfResponse.put("url", new Helpers().getURL(request));
		 
		 try {
			response.getWriter().print(jsonOfResponse);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	 }

}
