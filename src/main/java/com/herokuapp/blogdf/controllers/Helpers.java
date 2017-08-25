package com.herokuapp.blogdf.controllers;

import javax.servlet.http.HttpServletRequest;

public class Helpers {
	public String getURL(HttpServletRequest request) {
		String url = request.getRequestURL().substring(0, 
				 request.getRequestURL().indexOf(request.getServletPath())
				 );
		
		return url;
	}
	
	/**
	 * Retorn se a url for uma requisição de chave e valor correspodente
	 * 
	 * @return boolean
	 */
	public boolean isParamanterInUrl(HttpServletRequest request, String key, String value) {
		if (request.getParameter(key) == null) return false;
		if (request.getParameter(key).toString().equals(value) == false)
			return false;
		
		return true;

	}
}
