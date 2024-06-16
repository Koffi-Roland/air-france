package com.afklm.config;

import com.afklm.repind.provideindividualreferencetable.v1.ProvideIndividualReferenceTableV1Impl;
import com.afklm.repind.provideindividualreferencetable.v2.ProvideIndividualReferenceTableV2Impl;
import com.afklm.repind.v4.provideindividualdata.ProvideIndividualDataV4Impl;
import com.afklm.repind.v5.provideindividualdata.ProvideIndividualDataV5Impl;
import com.afklm.repind.v6.provideindividualdata.ProvideIndividualDataV6Impl;
import com.afklm.repind.v7.provideindividualdata.ProvideIndividualDataV7Impl;
import org.springframework.context.annotation.*;

import java.net.MalformedURLException;

@Profile("test")

@Configuration
@ComponentScan(basePackages = "com.afklm.repind",
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.repind.config.*")})
@Import(com.airfrance.repind.config.WebTestConfig.class)
public class WebTestConfig {
	
	@Bean(name = "passenger_ProvideIndividualReferenceTable-v01Bean")
	public ProvideIndividualReferenceTableV1Impl provideIndividualReferenceTableV1Impl() throws MalformedURLException{
		return new ProvideIndividualReferenceTableV1Impl();
	}
	
	@Bean(name = "passenger_ProvideIndividualReferenceTable-v02Bean")
	public ProvideIndividualReferenceTableV2Impl provideIndividualReferenceTableV2Impl() throws MalformedURLException{
		return new ProvideIndividualReferenceTableV2Impl();
	}
	
	@Bean(name = "passenger_ProvideIndividualData-v04Bean")
	public ProvideIndividualDataV4Impl provideIndividualDataV4Impl() throws MalformedURLException{
		return new ProvideIndividualDataV4Impl();
	}
	
	@Bean(name = "passenger_ProvideIndividualData-v05Bean")
	public ProvideIndividualDataV5Impl provideIndividualDataV5Impl() throws MalformedURLException{
		return new ProvideIndividualDataV5Impl();
	}
	
	@Bean(name = "passenger_ProvideIndividualData-v06Bean")
	public ProvideIndividualDataV6Impl provideIndividualDataV6Impl() throws MalformedURLException{
		return new ProvideIndividualDataV6Impl();
	}
	
	@Bean(name = "passenger_ProvideIndividualData-v07Bean")
	public ProvideIndividualDataV7Impl provideIndividualDataV7Impl() throws MalformedURLException{
		return new ProvideIndividualDataV7Impl();
	}
}
