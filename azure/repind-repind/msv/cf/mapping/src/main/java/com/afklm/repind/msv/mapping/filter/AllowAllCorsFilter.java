package com.afklm.repind.msv.mapping.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;

public class AllowAllCorsFilter extends GenericFilterBean {

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) req;
		final CorsFilter filter = instantiate();
		filter.doFilter(request, res, chain);

	}

	/**
	 * idem
	 */
	protected CorsFilter instantiate() {

		final UrlBasedCorsConfigurationSource source = instantiateCorsSource();

		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); // you USUALLY want this
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);

	}

	/**
	 * for testing case
	 */
	protected UrlBasedCorsConfigurationSource instantiateCorsSource() {
		return new UrlBasedCorsConfigurationSource();
	}

}
