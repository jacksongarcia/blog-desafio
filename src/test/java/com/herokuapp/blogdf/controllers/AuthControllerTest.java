package com.herokuapp.blogdf.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.easymock.EasyMock;

import com.herokuapp.blogdf.models.UserSession;

import junit.framework.TestCase;

public class AuthControllerTest extends TestCase {

	/**
	 * Faz a validação se tem sessão, caso tenha remove e retorna true
	 * 
	 */
	public void testLogOut() {
		HttpSession sessionMock = getSessionMock(EasyMock.createMock(UserSession.class));
		
		HttpServletRequest requestMock = EasyMock.createMock(HttpServletRequest.class);  
		EasyMock.expect(requestMock.getSession()).andReturn(sessionMock);
		EasyMock.replay(requestMock);

        AuthController auth = new AuthController(requestMock, null, "auth");  
        assertTrue(auth.logOut());  
	}

	public void testUserLogged() {	
		UserSession userMock = getUserSessionMock();
		HttpSession sessionMock = getSessionMock(userMock);
		
		HttpServletRequest requestMock = EasyMock.createMock(HttpServletRequest.class);  
		EasyMock.expect(requestMock.getSession()).andReturn(sessionMock);
		EasyMock.replay(requestMock);
		
        AuthController auth = new AuthController(requestMock, null, "auth");  
        
        assertNotNull(auth.userLogged());
	}

	public void testLogIn() {
		fail("Not yet implemented");
	}
	
	private UserSession getUserSessionMock() {
		UserSession userMock = EasyMock.createNiceMock(UserSession.class);
		EasyMock.expect(userMock.isLogged()).andReturn(true);
		EasyMock.replay(userMock);
		
		return userMock;
	}

	private HttpSession getSessionMock(UserSession userMock) {
		HttpSession sessionMock = EasyMock.createNiceMock(HttpSession.class);
		EasyMock.expect(sessionMock.getAttribute("auth")).andReturn(userMock);
		EasyMock.replay(sessionMock);
		
		return sessionMock;
	}
	
}
