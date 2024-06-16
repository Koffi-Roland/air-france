package com.afklm.repind.msv.mapping.filter;

import jakarta.servlet.http.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;


/**
 * RootPathHttpServletRequestWrapper Wrapper that adds a HTTP header suitable
 * for HATEOAS
 *
 *
 */
public class RootPathHttpServletRequestWrapper extends HttpServletRequestWrapper {

	public static final String X_FORWARDED_PREFIX = "X-Forwarded-Prefix";
	private final String rootPath;

	/**
	 * Constructor
	 *
	 * @param request
	 *            http request
	 * @param rootPath
	 *            rootPath
	 */
	public RootPathHttpServletRequestWrapper(final HttpServletRequest request, final String rootPath) {
		super(request);
		this.rootPath = rootPath;
	}

	@Override
	public String getHeader(final String name) {
		if (X_FORWARDED_PREFIX.equals(name)) {
			return getRootPath();
		} else {
			return super.getHeader(name);
		}
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		final ArrayList<String> list = Collections.list(super.getHeaderNames());
		list.add(X_FORWARDED_PREFIX);
		return Collections.enumeration(list);
	}

	public String getRootPath() {
		return rootPath;
	}
}
