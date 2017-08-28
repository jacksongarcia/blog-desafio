package com.herokuapp.blogdf.controllers;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;

public class AuthControllerTest {
	private HttpServletRequest request;
	private AuthController authController;
	private Connection connection;
	
	@Before
	public void setUp() throws Exception {
		request = createNiceMock(HttpServletRequest.class);
		connection = createNiceMock(Connection.class);
		authController = new AuthController();
	}

	@Test
	public void validateInputEmailDataBase() throws SQLException {
		expect(request.getParameter("key")).andReturn("teste");
		replay(request);
		
		boolean validateEmail = authController.validateInputEmailDataBase(request, "key", connection);
		assertTrue(validateEmail);
	}
	
	@Test
	public void validateFormatLastName() {
		expect(request.getParameter(AuthController.PARAMETER_REGISTER_LAST_NAME)).andReturn("teste");
		replay(request);

		assertNull(authController.validateFormatLastName(request));
	}

}
