package com.herokuapp.blogdf.servelets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.herokuapp.blogdf.controllers.AuthController;
import com.herokuapp.blogdf.models.UserSession;

@WebServlet(name="AuthServelet", urlPatterns = {"/auth", "/logout", "/login"}) 
public class AuthServelet extends HttpServlet  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession)session.getAttribute("auth");
		
		AuthController auth = new AuthController();

		try {
			auth.logout(request, session);

			if (userSession != null && userSession.isLoged()) {
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
			else {
				session.invalidate();
				request.setAttribute("page", "auth");
				request.getRequestDispatcher("auth.jsp").forward(request, response);
			}
			
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}

	}
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		 if (request.getParameter("type") != null) {
			 AuthController auth = new AuthController();
			 
			 try {
				 if (request.getParameter("type").equals("register")) {
					JSONObject json = auth.registerUser(request, response);
					response.getWriter().print(json);
				 }
				 else if (request.getParameter("type").equals("login")) {
					 JSONObject json = auth.loginUser(request, response);
					 response.getWriter().print(json);
				 }
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }

	 }
	 	 


}
