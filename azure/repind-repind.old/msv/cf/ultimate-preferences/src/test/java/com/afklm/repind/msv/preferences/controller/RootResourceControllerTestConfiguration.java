package com.afklm.repind.msv.preferences.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Configuration class for testing HostMonitoringService. No config needed for
 * this test
 *
 * @author klm42620 (Dennis Kruis)
 */
@Configuration
@PropertySource("classpath:/web-config.properties")
public class RootResourceControllerTestConfiguration {

	@Autowired
	Environment env;

}
