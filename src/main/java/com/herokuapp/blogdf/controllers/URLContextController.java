package com.herokuapp.blogdf.controllers;

import javax.servlet.http.HttpServletRequest;

class URLContextController {
	
	protected final static String PARAMETER_NAME = "ACTION";
	
	public final static String ERRO_DATA_BASE = "ERRO_DATA_BASE";
	
	/**
	 * Retorna true caso os paramanter estejan válidos
	 * 
	 * @param request
	 * @param key - Nome do paramento
	 * @param value valor do paramento esperado
	 * @return boolean
	 */
	protected boolean isParameterType(HttpServletRequest request, String key, String value) {
		String keyValue = request.getParameter(key);
		if (keyValue == null) return false;
		if (keyValue.equals("")) return false;
		if (keyValue.equalsIgnoreCase(value) == false) return false;
		
		return true;
	}
	
	/**
	 * Retorna verdadeiro se campo nulo ou vazio
	 * 
	 * @param request
	 * @param parameterRegisterName
	 * @return
	 */
	protected boolean isEmptyParameter(HttpServletRequest request,
			String parameterRegisterName) {
		String name = request.getParameter(parameterRegisterName);
		if (name == null) return true;
		if (name.equals("")) return true;
		
		return false;
	}
	
	protected int validateIsNumberParameter(String nameParamenter, HttpServletRequest request) {
		String index = request.getParameter(nameParamenter);	
		if (index == null || index.equals("")) return 0;
		if (index.contains("[a-Z]")) return 0;
		
		int value = Integer.parseInt(index);
		if (value < 0) return 0;
		
		return value;
	}
	
}
