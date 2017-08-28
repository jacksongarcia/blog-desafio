package com.herokuapp.blogdf.servelets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.herokuapp.blogdf.controllers.AuthController;

@WebServlet(urlPatterns={"/auth", "/login", "/logout"}) 
public class AuthServelet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
			try {
				 request.getRequestDispatcher("public/auth.jsp").forward(request, response);
				
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) {		 	 		 
		 response.setContentType("application/json");   
		 response.setCharacterEncoding("UTF-8");
		
		 try {
			 // Register
			 if (new AuthController().isURLRegister(request))
				 response.getWriter().print(new AuthController().register(request));
			 
			 // Login
			 else if (new AuthController().isURLLogin(request))
				 response.getWriter().print(new AuthController().login(request));

		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }

}
