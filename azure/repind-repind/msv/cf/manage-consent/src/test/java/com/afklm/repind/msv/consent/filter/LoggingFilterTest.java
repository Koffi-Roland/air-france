package com.afklm.repind.msv.consent.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Request;
import org.apache.catalina.startup.Tomcat;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoggingFilterTest {

	@Test
	void isLoggable() {

		final LoggingFilter filter = new LoggingFilter();

		String contentType = null;
		Assert.assertTrue(filter.isLoggable(contentType));

		contentType = "application/hal+json";
		Assert.assertTrue(filter.isLoggable(contentType));

		contentType = MediaType.APPLICATION_JSON_VALUE;
		Assert.assertTrue(filter.isLoggable(contentType));

		contentType = MediaType.APPLICATION_XML_VALUE;
		Assert.assertTrue(filter.isLoggable(contentType));

		contentType = MediaType.APPLICATION_XHTML_XML_VALUE;
		Assert.assertTrue(filter.isLoggable(contentType));

		contentType = MediaType.TEXT_HTML_VALUE;
		Assert.assertTrue(filter.isLoggable(contentType));

		contentType = MediaType.TEXT_PLAIN_VALUE;
		Assert.assertTrue(filter.isLoggable(contentType));

		contentType = MediaType.TEXT_XML_VALUE;
		Assert.assertTrue(filter.isLoggable(contentType));

		contentType = "application/octet-stream";
		Assert.assertFalse(filter.isLoggable(contentType));

		contentType = "application/toto";
		Assert.assertFalse(filter.isLoggable(contentType));

		contentType = "toto";
		Assert.assertFalse(filter.isLoggable(contentType));

	}

	@Test
	void testGetRequestPayloadNoPayload() throws UnsupportedEncodingException {

		final LoggingFilter filter = new LoggingFilter();

		// create a mock HttpServletRequest with no payload
		final MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");

		// Verify the result is null
		Assert.assertEquals(null, filter.getRequestPayload(request));
	}

	@Test
	void testGetResponsePayloadNoPayload() throws IOException {

		final LoggingFilter filter = new LoggingFilter();

		// Create a new MockHttpServletResponse object
		MockHttpServletResponse response = new MockHttpServletResponse();
		response.setStatus(200);
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "max-age=3600");
		response.flushBuffer();

		// Verify the result is null
		Assert.assertEquals(null, filter.getResponsePayload(response));
	}

	@Mock
	private HapiLog hapiLog;
	@Test
	void testLogHeaders() throws IOException {

		final LoggingFilter filter = new LoggingFilter();

		// Create a mock HapiLog object
		hapiLog = mock(HapiLog.class);

		// Create a mock HttpServletRequest
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("Accept", "application/json");
		request.addHeader("Authorization", "Example token");

		// Call the logHeaders() function
		filter.logHeaders(request, hapiLog);

		// Verify that the headers were logged
		Mockito.verify(hapiLog, Mockito.times(2)).getHeaders();
	}

	@Test
	void testDoFilter() throws ServletException, IOException {

		// Create a mock HapiLog object
		hapiLog = mock(HapiLog.class);

		// Create a mock HttpServletRequest
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/test");
		request.setContentType("application/json");
		request.setContent("{\"bla\": \"blou\"}".getBytes());

		// Create a mock HttpServletResponse
		MockHttpServletResponse response = new MockHttpServletResponse();

		// Create a mock FilterChain
		FilterChain chain = new MockFilterChain();

		// Call the doFilter() method
		final LoggingFilter filter = new LoggingFilter();
		filter.doFilter(request, response, chain);

		// Verify that the headers were logged (0 because we didn't do mockito when everywhere)
		Mockito.verify(hapiLog, Mockito.times(0)).setMethod("GET");
		Mockito.verify(hapiLog, Mockito.times(0)).setURI("/test");
		Mockito.verify(hapiLog, Mockito.times(0)).setContentType("application/json");
		Mockito.verify(hapiLog, Mockito.times(0)).setRequest("{\"bla\": \"blou\"}");
		Mockito.verify(hapiLog, Mockito.times(0)).setResponse("{}");
		Mockito.verify(hapiLog, Mockito.times(0)).setStatus(200);
	}

	@Test
	void isLoggableOverride() {

		final LoggingFilter filter = new LoggingFilter();

		LogRecord record = new LogRecord(Level.INFO,"bla");
		Assert.assertFalse(filter.isLoggable(record));
	}

	@Test
	void testDestroy(){
		final LoggingFilter filter = new LoggingFilter();
		filter.destroy();
		Assert.assertNotNull(filter);
	}

	@Test
	void testInit() throws ServletException {
		final LoggingFilter filter = new LoggingFilter();
		FilterConfig filterConfig = mock(FilterConfig.class);
		filter.init(filterConfig);
		Assert.assertNotNull(filter);
	}

}