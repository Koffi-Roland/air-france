package com.afklm.repind.config;

import com.afklm.repind.searchindividualbymulticriteriaws.SearchIndividualByMulticriteriaImpl;
import com.airfrance.repind.config.WebRepindTestConfig;
import org.springframework.context.annotation.*;

import java.net.MalformedURLException;

@Profile("test")

@Configuration
@ComponentScan(basePackages = "com.afklm.repind",
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.repind.config.*")})
@Import(WebRepindTestConfig.class)
public class WebTestConfig {
	
	@Bean(name = "passenger_SearchIndividualByMulticriteriaService-v2Bean")
	public SearchIndividualByMulticriteriaImpl searchIndividualByMulticriteriaImpl() throws MalformedURLException{
		return new SearchIndividualByMulticriteriaImpl();
	}
		
}
