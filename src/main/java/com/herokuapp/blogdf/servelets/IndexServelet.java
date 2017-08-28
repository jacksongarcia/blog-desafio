package com.herokuapp.blogdf.servelets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.herokuapp.blogdf.controllers.IndexController;

@WebServlet("/index") 
public class IndexServelet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.getRequestDispatcher("public/index.jsp").forward(request, response);
		
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
			 // Retorna uma lista de posts
			 if (new IndexController().isURLListPostTotal(request))
				 response.getWriter().print(new IndexController().getListPostTotal(request));

			 // Retorna uma lista de posts
			 else if (new IndexController().isURLListPost(request))
				 response.getWriter().print(new IndexController().getList(request));

		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
}
