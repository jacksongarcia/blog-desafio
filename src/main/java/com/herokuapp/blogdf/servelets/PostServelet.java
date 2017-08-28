package com.herokuapp.blogdf.servelets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.herokuapp.blogdf.controllers.AuthController;
import com.herokuapp.blogdf.controllers.PostController;

@WebServlet("/post")
public class PostServelet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		 try {
			 
			 if (new PostController().isURLPostView(request)) {
				if (new PostController().getPost(request)!= null)
						request.getRequestDispatcher("public/post.jsp").forward(request, response);
				else
					request.getRequestDispatcher("public/erro/erro.jsp").forward(request, response);
			 }else
				 request.getRequestDispatcher("public/erro/404.jsp").forward(request, response);
			 
		 } catch (IOException e) {
			 e.printStackTrace();
		 }	catch (ServletException e) {
				e.printStackTrace();
		 }
	}
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) {	
		 response.setContentType("application/json");   
		 response.setCharacterEncoding("UTF-8");
	
		 try {
			 if (new PostController().isURLGetComment(request))
				 response.getWriter().print(new PostController().getListComment(request));
			 else if (new PostController().isURLRegisterComment(request) && AuthController.session.isLogged(request))
				 response.getWriter().print(new PostController().registerComment(request));

		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
}
