package com.afklm.rigui.spring.rest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Configuration for Spring REST
 */
@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(
		basePackages = {
				"com.afklm.rigui.spring.rest" ,
				"com.afklm.rigui.springsecurity",
				"com.afklm.spring.security.habile.web"},
		excludeFilters = @Filter(
				type = FilterType.ANNOTATION,
				value = Configuration.class))
public class SpringRestConfiguration {

	/**
	 * Defines a multipart resolver.
	 * @return a multipart resolver.
	 */
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(500000);
		return commonsMultipartResolver;
	}

}
