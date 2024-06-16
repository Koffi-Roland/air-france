/*
 * Copyright (c) KLM Royal Dutch Airlines. All Rights Reserved.
 * ============================================================
 */
package com.afklm.repind.msv.mapping.controller.handler;

import com.afklm.repind.msv.mapping.mashery.interceptor.UserContextHandlerInterceptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertTrue;

@ExtendWith(SpringExtension.class)
class UserContextHandlerInterceptorTest {

	@Test
	void testPreHandleNoHeadersOk() throws Exception {
		final MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		final MockHttpServletResponse mockResponse = new MockHttpServletResponse();

		final UserContextHandlerInterceptor interceptor = new UserContextHandlerInterceptor();

		final boolean result = interceptor.preHandle(mockRequest, mockResponse, null);

		assertTrue(result);

	}

	@Test
	void testPreHandleOk() throws Exception {
		final MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		mockRequest.addHeader("PosCountry", "NL");
		mockRequest.addHeader("PosCarrier", "KL");
		mockRequest.addHeader("PosLanguage", "nl");
		mockRequest.addHeader("EndUserIpAddress", "127.0.0.1");
		mockRequest.addHeader("ConsumerApplication", "API");
		mockRequest.setRequestURI("/testweb");
		mockRequest.setQueryString("a=1");

		final MockHttpServletResponse mockResponse = new MockHttpServletResponse();

		final UserContextHandlerInterceptor interceptor = new UserContextHandlerInterceptor();

		final boolean result = interceptor.preHandle(mockRequest, mockResponse, null);

		assertTrue(result);
	}

}
