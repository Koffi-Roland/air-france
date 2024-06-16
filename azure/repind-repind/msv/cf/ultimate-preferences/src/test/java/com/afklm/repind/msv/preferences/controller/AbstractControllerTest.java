package com.afklm.repind.msv.preferences.controller;

import com.afklm.repind.msv.preferences.controller.handler.ServiceRestErrorHandler;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Abstract Controller Test
 *
 * @author m312812
 *
 */
public abstract class AbstractControllerTest {
	MockMvc mockMvc;

	abstract protected Object getController();

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);

		final MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
		final ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
			@Override
			protected ServletInvocableHandlerMethod getExceptionHandlerMethod(final HandlerMethod handlerMethod,
					final Exception exception) {
				final Method method = new ExceptionHandlerMethodResolver(ServiceRestErrorHandler.class)
						.resolveMethod(exception);
				return new ServletInvocableHandlerMethod(new ServiceRestErrorHandler(), method);
			}
		};
		exceptionResolver.getMessageConverters().add(jacksonConverter);
		exceptionResolver.afterPropertiesSet();

		mockMvc = standaloneSetup(getController()).setMessageConverters(jacksonConverter)
				.setHandlerExceptionResolvers(exceptionResolver).build();
	}

}
