package com.afklm.repind.msv.mapping.filter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.klm.opex.OpexMarker;
import com.klm.opex.correlation.CorrelationData;
import com.klm.opex.correlation.CorrelationDataHolder;
import com.klm.opex.servlet.CorrelationDataFactory;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

@Component
@Slf4j
public class LoggingFilter implements Filter {

	/** MDC customerId */
	public static final String MDC_CUSTOMER_ID = "customerId";


	private static final String SWAGGER_CONTEXT = "/api-mgr/repind/ultimate-preferences/swagger";

	private static String getRequestPayload(final HttpServletRequest request) throws UnsupportedEncodingException {
		String payload = null;
		final ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request,
				ContentCachingRequestWrapper.class);
		if (wrapper != null) {
			final byte[] buf = wrapper.getContentAsByteArray();
			if (buf.length > 0) {
				payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
			}
		}
		return payload;
	}

	private static String getResponsePayload(final HttpServletResponse response) throws IOException {
		String payload = null;
		final ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response,
				ContentCachingResponseWrapper.class);
		if (wrapper != null) {
			final byte[] buf = wrapper.getContentAsByteArray();
			if (buf.length > 0) {
				payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
				wrapper.copyBodyToResponse();
			}
		}
		return payload;
	}

	private static void logHeaders(final HttpServletRequest request, final HapiLog hapiLog) throws IOException {

		if (log.isInfoEnabled()) {
			final Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				final String key = headerNames.nextElement();
				final String value = request.getHeader(key);
				hapiLog.getHeaders().add(new HapiLogHeader(key, value));

			}
		}
	}

	private final CorrelationDataFactory correlationDataFactory;

	public LoggingFilter() {
		super();
		correlationDataFactory = CorrelationDataFactory.getInstance();
	}

	public void destroy() {
	}

	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
						 final FilterChain chain) throws IOException, ServletException {

		final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		final CorrelationData correlationData = correlationDataFactory.createCorrelationData((javax.servlet.http.HttpServletRequest)httpRequest);
		CorrelationDataHolder.setCorrelationData(correlationData);

		final long startTime = System.currentTimeMillis();

		final String path = ((HttpServletRequest) servletRequest).getRequestURI();

		try {

			if (log.isInfoEnabled() && !path.contains(SWAGGER_CONTEXT)) {

				final HapiLog hapiLog = new HapiLog();

				final HttpServletRequest request = (HttpServletRequest) servletRequest;
				final HttpServletResponse response = (HttpServletResponse) servletResponse;

				final HttpServletRequest requestToCache = new ContentCachingRequestWrapper(request);
				final HttpServletResponse responseToCache = new ContentCachingResponseWrapper(response);

				chain.doFilter(requestToCache, responseToCache);

				final StringBuilder uri = new StringBuilder();
				uri.append(request.getRequestURI());
				if (request.getQueryString() != null) {
					uri.append('?').append(request.getQueryString());
				}

				logHeaders(requestToCache, hapiLog);

				final String requestBody = getRequestPayload(requestToCache);

				final String responseBody = getResponsePayload(responseToCache);

				final long endTime = System.currentTimeMillis();
				final long executeTime = endTime - startTime;

				hapiLog.setMethod(request.getMethod());
				hapiLog.setURI(uri.toString());
				hapiLog.setContentType(request.getContentType());
				hapiLog.setCorrelationData(CorrelationDataHolder.getCorrelationData());
				hapiLog.setStatus(responseToCache.getStatus());

				hapiLog.setExecutionTime(executeTime);

				final ObjectWriter ow = new ObjectMapper().writer();
				final ObjectMapper mapper = new ObjectMapper();

				Object value = null;
				try {

					if (requestBody != null
							&& (requestToCache.getContentType() == null || requestToCache.getContentType() != null
									&& "application/json".equals(requestToCache.getContentType()))) {
						value = mapper.readValue(requestBody, Object.class);
						hapiLog.setRequest(value);
					}

					if (responseBody != null
							&& (responseToCache.getContentType() == null || responseToCache.getContentType() != null
									&& "application/json".equals(responseToCache.getContentType()))) {
						value = mapper.readValue(responseBody, Object.class);
						hapiLog.setResponse(value);
					}

				} catch (final JsonParseException e) {
					log.warn("Parse problem");
				} catch (final Exception e) {
					e.printStackTrace();
				}

				final String jsonHapilog = ow.writeValueAsString(hapiLog);
				log.info(OpexMarker.JSON.getMarker(), jsonHapilog);

			} else {

				chain.doFilter(servletRequest, servletResponse);

			}

		} finally {

			CorrelationDataHolder.clearCorrelationData();
			// Clear MDC context set by SecurityInterceptor
			MDC.remove(MDC_CUSTOMER_ID);

		}
	}

	public void init(final FilterConfig filterConfig) throws ServletException {
	}

	protected boolean isLoggable(final String contentType) {

		boolean isLoggable = true;

		if (contentType == null) {
			return isLoggable;
		}

		// StringUtils.isOneOf
		if (	contentType.equals("application/hal+json") ||
				contentType.equals(MediaType.APPLICATION_JSON_VALUE) ||
				contentType.equals(MediaType.APPLICATION_XML_VALUE) ||
				contentType.equals(MediaType.APPLICATION_XHTML_XML_VALUE) ||
				contentType.equals(MediaType.TEXT_HTML_VALUE) ||
				contentType.equals(MediaType.TEXT_PLAIN_VALUE) ||
				contentType.equals(MediaType.TEXT_XML_VALUE)
				) {
			isLoggable = true;
		} else {
			/**
			 * binary types ...
			 */
			isLoggable = false;
		}

		return isLoggable;

	}

	@Override
	public boolean isLoggable(LogRecord record) {
		return false;
	}
}
