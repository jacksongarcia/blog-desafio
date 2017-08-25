package com.herokuapp.blogdf.servelets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.herokuapp.blogdf.controllers.EditController;
import com.herokuapp.blogdf.models.UserSession;

@WebServlet(name="EditServelet", urlPatterns = {"/edit", "/new-post"}) 
public class EditServelet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (hasLogedUser(request)) {
				request.getRequestDispatcher("edit.jsp").forward(request, response);
			}
			else {
				request.getRequestDispatcher("auth.jsp").forward(request, response);
			}
			
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}

	}
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) {		
		 if (request.getParameter("type") != null && hasLogedUser(request)) {
			 EditController edit = new EditController();
			 
			 try {
				 if (request.getParameter("type").equals("publish")) {
					JSONObject json = edit.registerPost(request, response);
					
					response.getWriter().print(json);
				 }
//				 else if (request.getParameter("type").equals("login")) {
//					 JSONObject json = auth.loginUser(request, response);
//					 
//					if(json.has("erro") == false)
//						json.put("url", getURL(request));
//					
//					 response.getWriter().print(json);
//				 }
			} catch (IOException e) {
				e.printStackTrace();
			}
		 } 

	 }
	 
	 private boolean hasLogedUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession)session.getAttribute("auth");
		
		 return (userSession != null && userSession.isLoged() && userSession.isAdmin());
	 }
}
