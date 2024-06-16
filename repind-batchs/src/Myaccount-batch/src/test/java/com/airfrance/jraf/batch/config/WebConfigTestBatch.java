package com.airfrance.jraf.batch.config;


import com.afklm.bird.ws.w000309.ProvideCityInformationServiceV1;
import com.afklm.bird.ws.w000311.ProvideStationInformationServiceV1;
import com.afklm.repindpp.paymentpreference.delete.DeletePaymentPreferencesServiceV1;
import com.afklm.soa.spring.consumer.WebServiceConsumer;
import com.afklm.soa.spring.consumer.WebServiceConsumerBuilder;
import com.afklm.soa.stubs.w001165.v1.PAYTokenizeCreditCardV10_Service;
import com.afklm.soa.stubs.w001391.v1.NotifyAgencyCompanyServiceV1;
import com.afklm.soa.stubs.w001392.v1.NotifyMemberServiceV1;
import com.afklm.soa.stubs.w001539.v1.NotifyEventIndividualServiceV1;
import com.afklm.soa.stubs.w001580.v3.ProvideCustomer360ViewV3;
import com.afklm.soa.stubs.w001690.v1.NotifySecurityEventForIndividualServiceV1;
import com.afklm.soa.stubs.w001992.v1.NotifyAdhocEventIndividualServiceV1;
import com.airfrance.repind.config.WebTestConfig;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;

import java.net.MalformedURLException;

@Profile("test")

@Configuration
@ComponentScan(basePackages = "com.airfrance.jraf.batch", 
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.airfrance.jraf.batch.config.*")})
@Import(WebTestConfig.class)
public class WebConfigTestBatch {

	// ProvideCustomer360view START
	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@Bean(name = "provideCustomer360ViewV3")
//	public WebServiceConsumer provideCustomer360ViewV3() throws MalformedURLException {
//		WebServiceConsumer consumer_ProvideCustomer360View = new WebServiceConsumer<>();
//		consumer_ProvideCustomer360View.setService(com.afklm.soa.stubs.w001580.v3.ProvideCustomer360ViewServiceV3.class);
//		consumer_ProvideCustomer360View.setEndpointAddress("https://ws-qvi-rct.airfrance.fr/passenger/marketing/001580v03");
//		consumer_ProvideCustomer360View.setWsdlLocation(getClass().getClassLoader().getResource("META-INF/wsdl/w001580-v03/w001580-v03-policy.wsdl"));
//		return consumer_ProvideCustomer360View;
//	}	
	// ProvideCustomer360view END

	// ProvideCustomer360view START
	@Bean(name = "provideCustomer360ViewV3")
	public ProvideCustomer360ViewV3 provideCustomer360ViewV3() throws MalformedURLException {
			
		return Mockito.mock(ProvideCustomer360ViewV3.class);
	}	
	// ProvideCustomer360view END

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean(name = "consumerW000311V01")
	public WebServiceConsumer consumerW000311V01() throws MalformedURLException{
		WebServiceConsumer consumerW000311V01 = new WebServiceConsumer();
		consumerW000311V01.setService(ProvideStationInformationServiceV1.class);
		consumerW000311V01.setEndpointAddress("https://ws-qvi-dev.airfrance.fr/passenger/netopcontrol/000311v01");
		consumerW000311V01.setWsdlLocation(getClass().getClassLoader().getResource("META-INF/wsdl/provideStationInformation/passenger_ProvideStationInformation-v01.wsdl"));
		return consumerW000311V01;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean(name = "consumerW000309V01")
	public WebServiceConsumer consumerW000309V01() throws MalformedURLException{
		WebServiceConsumer consumerW000309V01 = new WebServiceConsumer();
		consumerW000309V01.setService(ProvideCityInformationServiceV1.class);
		consumerW000309V01.setEndpointAddress("https://ws-qvi-dev.airfrance.fr/passenger/netopcontrol/000309v01");
		consumerW000309V01.setWsdlLocation(getClass().getClassLoader().getResource("META-INF/wsdl/provideCityInformation/passenger_ProvideCityInformation-v01.wsdl"));
		return consumerW000309V01;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean(name = "notifyEventIndividual-v1")
	public WebServiceConsumer consumerW001539V01() throws MalformedURLException{
		WebServiceConsumer consumerW001539V01 = new WebServiceConsumer();
		consumerW001539V01.setService(NotifyEventIndividualServiceV1.class);
		consumerW001539V01.setEndpointAddress("xjms:jndi:D.REPIND.SOAEDA.NEI_QVI.001.QAP?jndiConnectionFactoryName=DQTDYVAREPINDQCF");
		consumerW001539V01.setWsdlLocation(getClass().getClassLoader().getResource("META-INF/wsdl/notifyeventindividual/V1/passenger_NotifyEventIndividual-v1.wsdl"));
		return consumerW001539V01;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean(name = "notifySecurityEventForIndividual-v1")
	public WebServiceConsumer consumerW001690V01() throws MalformedURLException{
		WebServiceConsumer consumerW001690V01 = new WebServiceConsumer();
		consumerW001690V01.setService(NotifySecurityEventForIndividualServiceV1.class);
		consumerW001690V01.setEndpointAddress("xjms:jndi:D.REPIND.SOAEDA.NSEI_QVI.001.QAP?jndiConnectionFactoryName=DQTDYVAREPINDQCF");
		consumerW001690V01.setWsdlLocation(getClass().getClassLoader().getResource("META-INF/wsdl/notifysecurityeventforindividual/V1/passenger_NotifySecurityEventForIndividual-v1.wsdl"));
		return consumerW001690V01;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean(name = "notifyAdhocEventIndividual-v1")
	public WebServiceConsumer consumerW001992V01() throws MalformedURLException{
		WebServiceConsumer consumerW001992V01 = new WebServiceConsumer();
		consumerW001992V01.setService(NotifyAdhocEventIndividualServiceV1.class);
		consumerW001992V01.setEndpointAddress("xjms:jndi:D.REPIND.SOAEDA.NAEI_QVI.001.QAP?jndiConnectionFactoryName=DQTDYVAREPINDQCF");
		consumerW001992V01.setWsdlLocation(getClass().getClassLoader().getResource("META-INF/wsdl/w001992-v01/w001992-v01.wsdl"));
		return consumerW001992V01;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean(name = "notifyAgencyCompany-v1")
	public WebServiceConsumer consumerW001391V01() throws MalformedURLException{
		WebServiceConsumer consumerW001391V01 = new WebServiceConsumer();
		consumerW001391V01.setService(NotifyAgencyCompanyServiceV1.class);
		consumerW001391V01.setEndpointAddress("xjms:jndi:D.REPFIRM.SOAEDA.NAC_QVI.001.QAP?jndiConnectionFactoryName=DQTDYVAREPFIRMQCF");
		consumerW001391V01.setWsdlLocation(getClass().getClassLoader().getResource("META-INF/wsdl/notifyagencycompany/V1/passenger_NotifyAgencyCompany-v1.wsdl"));
		return consumerW001391V01;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean(name = "notifyMember-v1")
	public WebServiceConsumer consumerW001392V01() throws MalformedURLException{
		WebServiceConsumer consumerW001392V01 = new WebServiceConsumer();
		consumerW001392V01.setService(NotifyMemberServiceV1.class);
		consumerW001392V01.setEndpointAddress("xjms:jndi:D.REPFIRM.SOAEDA.NME_QVI.001.QAP?jndiConnectionFactoryName=DQTDYVAREPFIRMQCF");
		consumerW001392V01.setWsdlLocation(getClass().getClassLoader().getResource("META-INF/wsdl/notifymember/V1/passenger_NotifyMember-v1.wsdl"));
		return consumerW001392V01;
	}

    /**
     * @return consumer DeletePaymentPreferencesServiceV1 web service
     * @throws Exception
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public WebServiceConsumer consumerW000471V01() throws Exception {

		WebServiceConsumer consumerW000471V01 = new WebServiceConsumer();
		consumerW000471V01.setService(DeletePaymentPreferencesServiceV1.class);
		consumerW000471V01.setEndpointAddress("https://ws-qvi-dev.airfrance.fr/passenger/marketing/000471v01");
		consumerW000471V01.setWsdlLocation(getClass().getClassLoader().getResource("META-INF/wsdl/deletepaymentdata/passenger_DeletePaymentPreferences-v1.wsdl"));
        return consumerW000471V01;
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean(name = "consumerW000413V01")
	public WebServiceConsumer consumerW000413V01() throws MalformedURLException{
		WebServiceConsumer consumerW000413V02 = new WebServiceConsumer();
		consumerW000413V02.setService(com.afklm.bdm.ws.w000413.StoreMarketingPreferencesCustomerServiceV1.class);
		consumerW000413V02.setEndpointAddress("https://ws-qvi-dev.airfrance.fr/passenger/marketing/000413v01");
		consumerW000413V02.setWsdlLocation(getClass().getClassLoader().getResource("META-INF/wsdl/marketingdata/passenger_StoreMarketingPreferencesCustomer-v01.wsdl"));
		return consumerW000413V02;
	}
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean(name = "consumerW001165V01")
	public WebServiceConsumer consumerW001165V01() throws Exception{

        WebServiceConsumerBuilder<PAYTokenizeCreditCardV10_Service> webServiceConsumerBuilder = new WebServiceConsumerBuilder<>();

        webServiceConsumerBuilder.service(PAYTokenizeCreditCardV10_Service.class)
                .wsdlLocation("classpath:/META-INF/wsdl/tokenize/TokenizeCreditCard-v1.0.0.wsdl");

        return webServiceConsumerBuilder.getWebServiceConsumer();
	}
	
}
