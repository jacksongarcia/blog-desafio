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
import com.herokuapp.blogdf.controllers.Helpers;

@WebFilter(urlPatterns = {"/auth", "/logout", "/login"})
public class NotAccessOfPageAuthAndLogOut  implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		AuthController auth = new AuthController((HttpServletRequest)request, (HttpServletResponse)response, "auth");

		if (new Helpers().isParamanterInUrl((HttpServletRequest)request, "action", "logout") && auth.userLogged() != null)
			auth.logOut();
		
		if (auth.userLogged() == null)
			chain.doFilter(request, response);
		else
			request.getRequestDispatcher("index.jsp").forward(request, response);		
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
