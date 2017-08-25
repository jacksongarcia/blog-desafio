package com.herokuapp.blogdf.servelets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.herokuapp.blogdf.controllers.CommentController;
import com.herokuapp.blogdf.controllers.PostController;
import com.herokuapp.blogdf.controllers.UserController;
import com.herokuapp.blogdf.models.Post;
import com.herokuapp.blogdf.models.User;
import com.herokuapp.blogdf.models.UserSession;

@WebServlet("/post")
public class PostServelet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getParameter("article") != null && request.getParameter("article").equals("") == false) {
				PostController postController = new PostController();
				UserController userController = new UserController();
				
				Post post = postController.getPost(request);
				User user = userController.getUserBtId(post.getUserId());
				
				request.setAttribute("post", post);
				request.setAttribute("user", user);

				request.getRequestDispatcher("post.jsp").forward(request, response);
			
			} else
				request.getRequestDispatcher("index.jsp").forward(request, response);
		
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) {		
		 if (request.getParameter("type") != null && hasLogedUser(request)) {
			 CommentController comment = new CommentController();
			 
			 try {
				 if (request.getParameter("type").equals("comment")) {
					JSONObject json = comment.registerComment(request, response);
					
					response.getWriter().print(json);
				 }

			} catch (IOException e) {
				e.printStackTrace();
			}
		 } 
		 
		 if (request.getParameter("type") != null) {
			 CommentController comment = new CommentController();

			 if (request.getParameter("type").equals("list-comment")) {
				 JSONArray json = comment.getListComment(request, response);
					
				try {
					response.getWriter().print(json);
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
		 }


	 }
	 
	 private boolean hasLogedUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession)session.getAttribute("auth");
		
		 return (userSession != null && userSession.isLoged() && userSession.isAdmin());
	 }
}
