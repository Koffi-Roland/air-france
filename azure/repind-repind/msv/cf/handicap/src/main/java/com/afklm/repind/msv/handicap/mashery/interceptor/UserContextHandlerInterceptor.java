package com.afklm.repind.msv.handicap.mashery.interceptor;


import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Fetches a set of header values from the request to set the "UserContext"
 * parameters. If the headers are not specified then defaults are used in the
 * UserContextCreator. This class is also used to put the requestURL in the
 * usercontext. In case of an error, this URL is logged.
 *
 * @author x074246 (Ramon de Beijer)
 * @author X074245 (Keesjan van Bunningen)
 */
@Component
public class UserContextHandlerInterceptor implements HandlerInterceptor {

	private static final String LANG = "PosLanguage";
	private static final String COUNTRY = "PosCountry";
	private static final String CARRIER = "PosCarrier";
	private static final String IP_ADDRESS = "EndUserIpAddress";
	private static final String CLIENT_APPLICATION = "ConsumerApplication";

	// @Autowired
	// private UserContextCreator userContextCreator;

	/**
	 * For every request look for UserContext request parameters and create a
	 * UserContext.
	 * 
	 * @param request
	 *            the current HttpServletRequest
	 * @param response
	 *            the current HttpServletResponse
	 * @param handler
	 *            chosen handler to execute, for type and/or instance evaluation
	 * @return true
	 */

	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {
		request.getHeader(CARRIER);
		request.getHeader(COUNTRY);
		request.getHeader(LANG);
		request.getHeader(IP_ADDRESS);
		request.getHeader(CLIENT_APPLICATION);

		final StringBuilder sb = new StringBuilder();
		sb.append(request.getRequestURL());

		if (request.getQueryString() != null) {
			sb.append("?").append(request.getQueryString());
		}
		sb.toString();

		// userContextCreator.createUserContext(carrier, country, language,
		// ipAddress, caller, completeURL);
		return true;
	}

}
