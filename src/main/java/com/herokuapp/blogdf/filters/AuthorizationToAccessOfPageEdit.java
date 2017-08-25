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
import javax.servlet.http.HttpServletResponse;

import com.herokuapp.blogdf.controllers.AuthController;

@WebFilter(urlPatterns = {"/edit", "/new-post"})
public class AuthorizationToAccessOfPageEdit implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		AuthController auth = new AuthController((HttpServletRequest)request, (HttpServletResponse)response, "auth");
		
		if (auth.userLogged() != null && auth.userLogged().isAdmin())
			chain.doFilter(request, response);
		else
			request.getRequestDispatcher("notaccess.jsp").forward(request, response);		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
