/*
 * Copyright (c) KLM Royal Dutch Airlines. All Rights Reserved.
 * ============================================================
 */
package com.afklm.repind.msv.handicap.controller.handler;

import com.afklm.repind.msv.handicap.mashery.interceptor.UserContextHandlerInterceptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertTrue;

@ExtendWith(SpringExtension.class)
class UserContextHandlerInterceptorTest {

	@InjectMocks
	private UserContextHandlerInterceptor interceptor;

	// @Mock
	// private UserContextCreator mockUserContextCreator;
	@Test
	void testPreHandleNoHeadersOk() throws Exception {
		final MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		final MockHttpServletResponse mockResponse = new MockHttpServletResponse();

		final boolean result = interceptor.preHandle(mockRequest, mockResponse, null);

		assertTrue(result);
		// verify(mockUserContextCreator).createUserContext(null, null, null,
		// null, null,
		// "http://localhost");
	}

	@Test
	public void testPreHandleOk() throws Exception {
		final MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		mockRequest.addHeader("PosCountry", "NL");
		mockRequest.addHeader("PosCarrier", "KL");
		mockRequest.addHeader("PosLanguage", "nl");
		mockRequest.addHeader("EndUserIpAddress", "127.0.0.1");
		mockRequest.addHeader("ConsumerApplication", "API");
		mockRequest.setRequestURI("/testweb");
		mockRequest.setQueryString("a=1");

		final MockHttpServletResponse mockResponse = new MockHttpServletResponse();

		final boolean result = interceptor.preHandle(mockRequest, mockResponse, null);

		assertTrue(result);
		// verify(mockUserContextCreator).createUserContext("KL", "NL", "nl",
		// "127.0.0.1", "API",
		// "http://localhost/testweb?a=1");
		// verify(mockUserContextCreatorNew).createUserContext("KL", "NL", "nl",
		// "127.0.0.1", "API",
		// "http://localhost/testweb?a=1");
	}

}
