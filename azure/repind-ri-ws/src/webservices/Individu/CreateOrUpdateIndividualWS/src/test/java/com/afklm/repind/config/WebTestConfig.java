package com.afklm.repind.config;

import com.afklm.repind.v1.lastciconnectionlistener.LastConnectStatusListenerV1;
import com.afklm.soa.spring.consumer.WebServiceConsumer;
import com.airfrance.repind.config.WebRepindAndUtf8TestConfig;
import org.springframework.context.annotation.*;

import java.net.MalformedURLException;

@Profile("test")

@Configuration
@ComponentScan(basePackages = "com.afklm.repind",
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.repind.config.*")})
@Import(WebRepindAndUtf8TestConfig.class)
public class WebTestConfig {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean(name = "consumerW000413V02")
	public WebServiceConsumer consumerW000413V02() throws MalformedURLException{
		WebServiceConsumer consumerW000413V02 = new WebServiceConsumer();
		consumerW000413V02.setService(com.afklm.soa.stubs.w000413.v2.StoreMarketingPreferencesCustomerServiceV2.class);
		consumerW000413V02.setEndpointAddress("https://ws-qvi-dev.airfrance.fr/passenger/marketing/000413v02");
		consumerW000413V02.setWsdlLocation(getClass().getClassLoader().getResource("META-INF/wsdl/w000413-v02/w000413-v02-policy.wsdl"));
		return consumerW000413V02;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean(name = "consumerW000418V05")
	public WebServiceConsumer consumerW000418V05() throws MalformedURLException{
		WebServiceConsumer consumerW000418V05 = new WebServiceConsumer();
		consumerW000418V05.setService(com.afklm.soa.stubs.w000418.v5.ProvideIndividualDataServiceServiceV5.class);
		consumerW000418V05.setEndpointAddress("http://repind-ws-dev.airfrance.fr/ProvideIndividualDataWS/ws/passenger/marketing/000418v05");
		consumerW000418V05.setWsdlLocation(getClass().getClassLoader().getResource("META-INF/wsdl/w000418-v05/w000418-v05.wsdl"));
		return consumerW000418V05;
	}
	
	@Bean
    public LastConnectStatusListenerV1 lastConnectStatusNotificationV1() {
        return new LastConnectStatusListenerV1();
    }
}
