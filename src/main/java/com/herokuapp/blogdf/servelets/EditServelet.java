package com.herokuapp.blogdf.servelets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.herokuapp.blogdf.controllers.EditController;
import com.herokuapp.blogdf.controllers.PostController;

@WebServlet(urlPatterns={"/edit", "/new-post", "/delete-post", "/edit-post"}) 
public class EditServelet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		try {
			 if (new EditController().isURLEdit(request))
				 if (new PostController().getPost(request)!= null)
						request.getRequestDispatcher("public/edit.jsp").forward(request, response);
					else
						request.getRequestDispatcher("public/erro/erro.jsp").forward(request, response);
			 else
				 request.getRequestDispatcher("public/edit.jsp").forward(request, response);
			
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}

	}
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) {		
		 response.setContentType("application/json");   
		 response.setCharacterEncoding("UTF-8");
		
		 try {
			 // PUBLICAR ARTIGO
			 if (new EditController().isURLPublish(request))
				 response.getWriter().print(new EditController().publish(request));
			 else if (new EditController().isURLDelete(request))
				 response.getWriter().print(new EditController().delete(request));
			 else if (new EditController().isURLEditPost(request))
				 response.getWriter().print(new EditController().edit(request));

		 } catch (IOException e) {
			 e.printStackTrace();
		 }

	 }
}
