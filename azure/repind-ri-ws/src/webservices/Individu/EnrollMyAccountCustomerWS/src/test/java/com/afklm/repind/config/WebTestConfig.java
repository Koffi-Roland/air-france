package com.afklm.repind.config;

import com.afklm.repind.enrollmyaccountcustomerws.EnrollMyAccountCustomerImpl;
import com.afklm.repind.v2.enrollmyaccountcustomerws.EnrollMyAccountCustomerV2Impl;
import com.airfrance.repind.config.WebRepindTestConfig;
import org.springframework.context.annotation.*;

import java.net.MalformedURLException;

@Profile("test")

@Configuration
@ComponentScan(basePackages = "com.afklm.repind",
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.repind.config.*")})
@Import(WebRepindTestConfig.class)
public class WebTestConfig {
	
	@Bean(name = "passenger_EnrollMyAccountCustomer-v01Bean")
	public EnrollMyAccountCustomerImpl enrollMyAccountCustomerImpl() throws MalformedURLException{
		return new EnrollMyAccountCustomerImpl();
	}
		
	@Bean(name = "passenger_EnrollMyAccountCustomer-v02Bean")
	public EnrollMyAccountCustomerV2Impl enrollMyAccountCustomerV2Impl() throws MalformedURLException{
		return new EnrollMyAccountCustomerV2Impl();
	}
}
