package com.herokuapp.blogdf.servelets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.herokuapp.blogdf.controllers.AuthController;
import com.herokuapp.blogdf.controllers.CommentController;
import com.herokuapp.blogdf.controllers.Helpers;
import com.herokuapp.blogdf.controllers.PostController;

@WebServlet("/post")
public class PostServelet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		PostController postController = new PostController(request, "article");

		try {
			if (postController.isUrlValide()) {
				if (postController.formatResponseForPagePost())
					request.getRequestDispatcher("post.jsp").forward(request, response);
				else
					request.getRequestDispatcher("erro.jsp").forward(request, response);

			} else
				request.getRequestDispatcher("index.jsp").forward(request, response);
		
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) {	
		CommentController comment = new CommentController(request, response, "auth");
		
		try {
			if (new Helpers().isParamanterInUrl(request, "type", "comment") && 
					new AuthController(request, response, "auth").userLogged() != null)
				response.getWriter().print(comment.registerComment());
						
			else if (new Helpers().isParamanterInUrl(request, "type", "list-comment"))
				response.getWriter().print(comment.getListComment());
			
	 	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	 }
}
