package com.afklm.rigui.spring.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SecurityConfiguration.class
								})
@WebAppConfiguration
class SecurityConfigurationTest {

	@Test
	void testConfig() {
		// Just to check that configuration is loaded without error
	}

}
