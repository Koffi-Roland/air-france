package com.afklm.repind.msv.preferences.controller.handler;

import com.afklm.repind.msv.preferences.mashery.interceptor.InvalidParameterException;
import com.afklm.repind.msv.preferences.mashery.interceptor.JsonParserUtil;
import com.afklm.repind.msv.preferences.mashery.interceptor.MasheryUserContext;
import com.afklm.repind.msv.preferences.mashery.interceptor.SecurityInterceptor;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.servlet.HandlerMapping;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class SecurityInterceptorTest {
	private static final String OK_MASHERY_OAUTH_USER_CONTEXT = "{\"customerId\": \"1000000001\",\"membertype\": \"myaccount\",\"authLevel\": \"NORMAL\"}";

	private static final String WRONG_MASHERY_OAUTH_USER_CONTEXT = "bullshit";
	private static final String CUSTOMER_ID_PARAM = "customerId";
	private static final String UNAUTHORIZED_MSG = "Authorization failed";
	private static final String X_MASHERY_OAUTH_USER_CONTEXT = "X-Mashery-Oauth-User-Context";

	private static final String X_MASHERY_OAUTH_CLIENT_ID = "X-Mashery-Oauth-Client-Id";
	@InjectMocks
	private final SecurityInterceptor interceptor = new SecurityInterceptor();
	@Mock
	private JsonParserUtil jsonParserUtilMock;
	@Mock
	private MasheryUserContext masheryUserContextMock;
	@Mock
	private HttpServletRequest httpServletRequestMock;
	@Mock
	private HttpServletResponse httpServletResponseMock;

	@BeforeEach
	void setup() {
		initMocks(this);
	}

	@Test
	void testCustomerIdInvalid(){
		final Map<String, Object> pathVariables = new HashMap<>();
		pathVariables.put(CUSTOMER_ID_PARAM, "100000000X");

		when(httpServletRequestMock.getHeader(X_MASHERY_OAUTH_USER_CONTEXT))
				.thenReturn(WRONG_MASHERY_OAUTH_USER_CONTEXT);
		when(httpServletRequestMock.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE))
				.thenReturn(pathVariables);

		try {
			interceptor.preHandle(httpServletRequestMock, httpServletResponseMock, null);
			fail("Should have thrown exception)");
		} catch (final Exception e) {
			assertTrue("Exception not an InvalidParameterException", e instanceof InvalidParameterException);
			final InvalidParameterException ipe = (InvalidParameterException) e;
			assertEquals("Field not as expected", CUSTOMER_ID_PARAM, ipe.getFieldName());
			assertEquals("Value not as expected", "100000000X", ipe.getFieldValue());

			verify(httpServletRequestMock).getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			verifyNoMoreInteractions(httpServletResponseMock, httpServletRequestMock);
		}
	}

	@Test
	void testPreHandleEmptyMasheryOAuthUserContext() throws Exception {
		final Map<String, Object> pathVariables = new HashMap<>();
		pathVariables.put(CUSTOMER_ID_PARAM, "1000000002");

		when(httpServletRequestMock.getHeader(X_MASHERY_OAUTH_USER_CONTEXT)).thenReturn(null);
		when(httpServletRequestMock.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE))
				.thenReturn(pathVariables);

		final boolean result = interceptor.preHandle(httpServletRequestMock, httpServletResponseMock, null);

		assertTrue(result);
		verify(httpServletRequestMock).getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		verify(httpServletRequestMock).getHeader(X_MASHERY_OAUTH_USER_CONTEXT);
		verifyNoMoreInteractions(httpServletResponseMock, httpServletRequestMock);
	}

	@Test
	void testPreHandleNoPathVariable() throws Exception {
		when(httpServletRequestMock.getHeader(X_MASHERY_OAUTH_USER_CONTEXT)).thenReturn(OK_MASHERY_OAUTH_USER_CONTEXT);
		when(httpServletRequestMock.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)).thenReturn(null);

		final boolean result = interceptor.preHandle(httpServletRequestMock, httpServletResponseMock, null);

		verify(httpServletRequestMock).getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		verifyNoMoreInteractions(httpServletResponseMock, httpServletRequestMock);
		assertTrue(result);
	}

	@Test
	void testPreHandleOkMasheryOAuthUserContext() throws Exception {
		final Map<String, Object> pathVariables = new HashMap<>();
		pathVariables.put(CUSTOMER_ID_PARAM, "1000000001");

		when(jsonParserUtilMock.getMasheryUserContext(httpServletRequestMock)).thenReturn(masheryUserContextMock);
		when(masheryUserContextMock.getCustomerId()).thenReturn("1000000001");

		when(httpServletRequestMock.getHeader(X_MASHERY_OAUTH_USER_CONTEXT)).thenReturn(OK_MASHERY_OAUTH_USER_CONTEXT);
		when(httpServletRequestMock.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE))
				.thenReturn(pathVariables);

		final boolean result = interceptor.preHandle(httpServletRequestMock, httpServletResponseMock, null);

		verify(httpServletRequestMock).getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		verify(httpServletRequestMock).getHeader(X_MASHERY_OAUTH_USER_CONTEXT);
		verify(jsonParserUtilMock).getMasheryUserContext(httpServletRequestMock);
		verify(masheryUserContextMock).getCustomerId();
		verifyNoMoreInteractions(httpServletResponseMock, httpServletRequestMock, jsonParserUtilMock,
				masheryUserContextMock);
		assertTrue(result);
	}

	@Test
	void testPreHandleOkMasheryOAuthUserContextWrongCustomerId() throws Exception {
		final Map<String, Object> pathVariables = new HashMap<>();
		pathVariables.put(CUSTOMER_ID_PARAM, "1000000002");

		when(httpServletRequestMock.getHeader(X_MASHERY_OAUTH_USER_CONTEXT)).thenReturn(OK_MASHERY_OAUTH_USER_CONTEXT);
		when(httpServletRequestMock.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE))
				.thenReturn(pathVariables);
		when(jsonParserUtilMock.getMasheryUserContext(httpServletRequestMock)).thenReturn(masheryUserContextMock);
		when(masheryUserContextMock.getCustomerId()).thenReturn("1000000001");

		final boolean result = interceptor.preHandle(httpServletRequestMock, httpServletResponseMock, null);

		assertFalse(result);
		verify(httpServletRequestMock).getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		verify(httpServletRequestMock).getHeader(X_MASHERY_OAUTH_USER_CONTEXT);
		verify(httpServletRequestMock).getHeader(X_MASHERY_OAUTH_CLIENT_ID);
		verify(httpServletRequestMock).getRequestURL();
		verify(httpServletResponseMock).sendError(HttpServletResponse.SC_FORBIDDEN, UNAUTHORIZED_MSG);
		verify(jsonParserUtilMock).getMasheryUserContext(httpServletRequestMock);
		verify(masheryUserContextMock).getCustomerId();

		verifyNoMoreInteractions(httpServletResponseMock, httpServletRequestMock, jsonParserUtilMock,
				masheryUserContextMock);
	}

	@Test
	void testPreHandleOnlyOtherPathVariable() throws Exception {
		final Map<String, Object> pathVariables = new HashMap<>();
		pathVariables.put("dummy", "dummy");

		when(httpServletRequestMock.getHeader(X_MASHERY_OAUTH_USER_CONTEXT)).thenReturn(OK_MASHERY_OAUTH_USER_CONTEXT);
		when(httpServletRequestMock.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE))
				.thenReturn(pathVariables);

		final boolean result = interceptor.preHandle(httpServletRequestMock, httpServletResponseMock, null);

		verify(httpServletRequestMock).getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		verifyNoMoreInteractions(httpServletResponseMock, httpServletRequestMock);
		assertTrue(result);
	}

	@Test
	void testPreHandleWrongMasheryOAuthUserContext() throws Exception {
		final Map<String, Object> pathVariables = new HashMap<>();
		pathVariables.put(CUSTOMER_ID_PARAM, "1000000002");

		when(httpServletRequestMock.getHeader(X_MASHERY_OAUTH_USER_CONTEXT))
				.thenReturn(WRONG_MASHERY_OAUTH_USER_CONTEXT);
		when(httpServletRequestMock.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE))
				.thenReturn(pathVariables);
		when(jsonParserUtilMock.getMasheryUserContext(httpServletRequestMock)).thenReturn(null);

		final boolean result = interceptor.preHandle(httpServletRequestMock, httpServletResponseMock, null);

		assertFalse(result);
		verify(httpServletRequestMock).getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		verify(httpServletRequestMock).getHeader(X_MASHERY_OAUTH_USER_CONTEXT);
		verify(httpServletRequestMock).getHeader(X_MASHERY_OAUTH_CLIENT_ID);
		verify(httpServletRequestMock).getRequestURL();
		verify(jsonParserUtilMock).getMasheryUserContext(httpServletRequestMock);

		verify(httpServletResponseMock).sendError(HttpServletResponse.SC_FORBIDDEN, UNAUTHORIZED_MSG);

		verifyNoMoreInteractions(httpServletResponseMock, httpServletRequestMock, jsonParserUtilMock,
				masheryUserContextMock);
	}
}
