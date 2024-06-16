package com.afklm.repind.msv.handicap.mashery.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;


/**
 * Mashery UserContext header contains a String that looks like: { "customerId":
 * "1000000001", "membertype": "myaccount", "authLevel": "NORMAL" }
 *
 */

// {"customerId": "<customerId>", "memberType": "<accountType>", "authLevel":
// "<authorizationLevel>", "gin": "<GIN>"}

class JsonParserUtilTest {

	static final String HOSTMONITOR_USER_AGENT = "AF-KL/HostMonitorBot";

	static final String USER_CONTEXT = "{ \"customerId\": \"1000000001\", \"membertype\": \"myaccount\", \"authLevel\": \"NORMAL\" }";
	JsonParserUtil util = new JsonParserUtil();

	@Test
	void getMasheryUserContextWithMonitoringHeader() {

		final MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(JsonParserUtil.X_MASHERY_OAUTH_USER_CONTEXT, HOSTMONITOR_USER_AGENT);
		final MasheryUserContext context = util.getMasheryUserContext(request);
		Assert.assertNull(context);

	}

	@Test
	void getMasheryUserContextWithNullHeader() {

		final MockHttpServletRequest request = new MockHttpServletRequest();
		final MasheryUserContext context = util.getMasheryUserContext(request);
		Assert.assertNull(context);

	}

	@Test
	void getMasheryUserContextWithUserHeader() {

		final MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(JsonParserUtil.X_MASHERY_OAUTH_USER_CONTEXT, USER_CONTEXT);
		final MasheryUserContext context = util.getMasheryUserContext((HttpServletRequest) request);

		Assert.assertEquals("1000000001", context.getCustomerId());
		Assert.assertEquals("NORMAL", context.getAuthLevel());

	}
}
