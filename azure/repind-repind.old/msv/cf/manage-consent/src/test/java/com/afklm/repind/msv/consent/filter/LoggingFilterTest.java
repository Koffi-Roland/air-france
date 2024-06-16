package com.afklm.repind.msv.consent.filter;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

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

}