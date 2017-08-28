package com.herokuapp.blogdf.controllers;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.*;
import org.junit.Before;
import org.junit.Test;

public class URLContextControllerTest {

	private HttpServletRequest request;
	private URLContextController urlContextController;
	
	@Before
	public void setUp() throws Exception {
		request = createNiceMock(HttpServletRequest.class);
		urlContextController = new URLContextController();
	}

	@Test
	public void testIsEmptyParameter() {
		expect(request.getParameter("key")).andReturn("sucesso");
		replay(request);
		
		assertTrue(urlContextController.isEmptyParameter(request, "sucesso"));
	}

	@Test
	public void testValidateIsNumberParameter() {
		expect(request.getParameter("key")).andReturn("2");
		replay(request);
				
		assertEquals(urlContextController.validateIsNumberParameter("key", request), 2);
		
	}

}
