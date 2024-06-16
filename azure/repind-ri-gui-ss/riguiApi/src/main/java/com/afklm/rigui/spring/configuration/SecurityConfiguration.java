package com.afklm.rigui.spring.configuration;

import com.afklm.rigui.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.spring.security.habile.EntryPointConfiguration;
import com.afklm.spring.security.habile.SecurityConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * Security configuration
 * @author m405991
 *
 */
@Configuration("habileCustomSecurityConfig")
@Import(EntryPointConfiguration.class)
@PropertySource("classpath:/application.properties")
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(99)
public class SecurityConfiguration extends SecurityConfig {


	public String[] getExtraPublicPatterns() {
		return new String[] { "/css/**", "/font/**", "/icones/**", "/img/**" };
	}

	@Bean
	public UserProfilesAccessKey getAllAccessKeys() {
		ObjectMapper jsonMapper = new ObjectMapper();
		try {
			return jsonMapper.readValue(
					getClass().getClassLoader().getResourceAsStream("accessKeyConfig/accessKey.json"),
					new TypeReference<>() {
					});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new UserProfilesAccessKey();
	}
}
