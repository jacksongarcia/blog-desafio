package com.herokuapp.blogdf.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.herokuapp.blogdf.controllers.AuthController;

@WebFilter(urlPatterns = {"/auth", "/login", "/logout"})
public class AuthorizationToAccessOfPage implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		if (AuthController.session.isLogged((HttpServletRequest)request) == false)
			chain.doFilter(request, response);
		
		else {
			 if (new AuthController().isURLLogout((HttpServletRequest)request)) {
				AuthController.session.logout((HttpServletRequest)request);
				request.getRequestDispatcher("public/auth.jsp").forward(request, response);

			 }
			 else
				request.getRequestDispatcher("public/index.jsp").forward(request, response);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
