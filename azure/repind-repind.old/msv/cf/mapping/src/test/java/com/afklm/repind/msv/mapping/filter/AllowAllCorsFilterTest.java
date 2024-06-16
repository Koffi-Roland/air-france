package com.afklm.repind.msv.mapping.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import java.io.IOException;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AllowAllCorsFilterTest {

	@Test
	void testDoFilter() throws IOException, ServletException {
		// create the objects to be mocked
		final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
		final HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
		final FilterChain filterChain = Mockito.mock(FilterChain.class);

		final AllowAllCorsFilter filter = Mockito.spy(new AllowAllCorsFilter());
		final CorsFilter instance = Mockito.mock(CorsFilter.class);
		Mockito.doReturn(instance).when(filter).instantiate();

		filter.doFilter(httpServletRequest, httpServletResponse, filterChain);

		Mockito.verify(filter).instantiate();
	}

	@Test
	void testInstantiate() throws IOException, ServletException {

		final AllowAllCorsFilter filter = Mockito.mock(AllowAllCorsFilter.class);
		final UrlBasedCorsConfigurationSource source = Mockito.mock(UrlBasedCorsConfigurationSource.class);
		when(filter.instantiate()).thenCallRealMethod();
		when(filter.instantiateCorsSource()).thenReturn(source);

		final ArgumentCaptor<String> pathCapture = ArgumentCaptor.forClass(String.class);
		final ArgumentCaptor<CorsConfiguration> configCapture = ArgumentCaptor.forClass(CorsConfiguration.class);

		filter.instantiate();

		Mockito.verify(source).registerCorsConfiguration(pathCapture.capture(), configCapture.capture());

		Assert.assertEquals("/**", pathCapture.getValue());
		final CorsConfiguration corsConfig = configCapture.getValue();
		Assert.assertEquals(true, corsConfig.getAllowCredentials());
		Assert.assertEquals("*", corsConfig.getAllowedHeaders().get(0));
		Assert.assertEquals("*", corsConfig.getAllowedMethods().get(0));
		Assert.assertEquals("*", corsConfig.getAllowedOrigins().get(0));

	}
}
