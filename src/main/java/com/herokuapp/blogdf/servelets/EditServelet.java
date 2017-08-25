package com.herokuapp.blogdf.servelets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.herokuapp.blogdf.controllers.EditController;
import com.herokuapp.blogdf.controllers.Helpers;

@WebServlet(name="EditServelet", urlPatterns = {"/edit", "/new-post"}) 
public class EditServelet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		try {
			request.getRequestDispatcher("edit.jsp").forward(request, response);
			
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}

	}
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) {		
		EditController edit = new EditController(request, response, "auth");

		if (new Helpers().isParamanterInUrl(request, "type", "publish")) {
			JSONObject jsonOfResponse = edit.registerPost();
			
			try {
				response.getWriter().print(jsonOfResponse);
			} catch (IOException e) {
				e.printStackTrace();
		 	}
		}
		else if (new Helpers().isParamanterInUrl(request, "type", "delete")) {
			JSONObject jsonOfResponse = edit.deletePost();
			
			if(jsonOfResponse.has("erro") == false)
				 jsonOfResponse.put("url", new Helpers().getURL(request));
			
			try {
				response.getWriter().print(jsonOfResponse);
			} catch (IOException e) {
				e.printStackTrace();
		 	}
		}
		

	 }
}
