package com.afklm.repind.msv.handicap.mashery.interceptor;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.util.Map;


import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

/**
 * Interceptor that checks if the url contains a customerId and if so if the
 * customerId in the url matches the customerId in the Mashery header.
 *
 * If the header is not there then let the request pass in this case (Salesforce
 * for example) the calling party doesn't have a customerId in the header and it
 * should be allowed to get somebody else his/her data
 *
 * @author x074246
 *
 */
@Component
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

	private static final String X_MASHERY_OAUTH_USER_CONTEXT = "X-Mashery-Oauth-User-Context";
	private static final String UNKNOWN_CLIENT_ID = "unknown";
	private static final String X_MASHERY_OAUTH_CLIENT_ID = "X-Mashery-Oauth-Client-Id";
	private static final String UNAUTHORIZED_MSG = "Authorization failed";
	private static final String CUSTOMER_ID_PARAM = "customerId";
	private static final String CUSTOMER_ID_PATTERN = "[1-9][0-9]{9}";

	@Autowired
	private JsonParserUtil jsonParserUtil;

	/**
	 * Tries to read the clientId from the header for logging purpose.
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @return ClientId or "unknown"
	 */
	private String getClientIdFromHeader(final HttpServletRequest request) {
		String clientId = request.getHeader(X_MASHERY_OAUTH_CLIENT_ID);

		if (isEmpty(clientId)) {
			clientId = UNKNOWN_CLIENT_ID;
		}

		return clientId;
	}

	/**
	 * check if the UserContext header is set by Mashery, if so then retrieve the
	 * customerId in that UserContext
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @return The customerId in the header or null if not found
	 */
	private String getCustomerIdFromHeader(final HttpServletRequest request) {
		String customerId = null;

		final MasheryUserContext masheryUserContext = jsonParserUtil.getMasheryUserContext(request);
		if (masheryUserContext != null) {
			customerId = masheryUserContext.getCustomerId();
		}
		return customerId;
	}

	/**
	 * Checks the request path for a customerId path variable
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @return The customerId path variable or null if not found
	 */
	@SuppressWarnings("rawtypes")
	private String getCustomerIdFromPath(final HttpServletRequest request) {
		String customerId = null;

		final Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		if (pathVariables != null && !pathVariables.isEmpty() && pathVariables.containsKey(CUSTOMER_ID_PARAM)) {
			customerId = (String) pathVariables.get(CUSTOMER_ID_PARAM);
			if (isEmpty(customerId) || !customerId.matches(CUSTOMER_ID_PATTERN)) {
				throw new InvalidParameterException(CUSTOMER_ID_PARAM, customerId);
			}
		}

		return customerId;
	}

	/**
	 * Check if mashery User Context header exists.
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @return True if exists, otherwise false.
	 */
	private boolean masheryUserContextHeaderExists(final HttpServletRequest request) {
		final boolean masheryUserContextHeaderExist = request.getHeader(X_MASHERY_OAUTH_USER_CONTEXT) != null;
		if (!masheryUserContextHeaderExist) {
			log.warn("X-Mashery-Oauth-User-Context NOT found in header");
		}
		return masheryUserContextHeaderExist;
	}


	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {

		boolean result = true;

		final String customerIdFromPath = getCustomerIdFromPath(request);
		if (isNotEmpty(customerIdFromPath) && masheryUserContextHeaderExists(request)) {
			final String customerIdFromHeader = getCustomerIdFromHeader(request);
			if (!customerIdFromPath.equals(customerIdFromHeader)) {
				log.error(format("Authorization failed for Url: %s, ClientId: %s", request.getRequestURL(),
						getClientIdFromHeader(request)));

				result = false;
				response.sendError(HttpServletResponse.SC_FORBIDDEN, UNAUTHORIZED_MSG);
			}
		}

		return result;
	}

}
