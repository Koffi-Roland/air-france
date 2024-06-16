package com.airfrance.jraf.batch.config;

import com.afklm.repindpp.paymentpreference.delete.DeletePaymentPreferencesServiceV1;
import com.afklm.soa.jaxws.customheader.configuration.ConsumerConfigurationBean;
import com.afklm.soa.jms.spring.ConsumerJmsFeature;
import com.afklm.soa.spring.consumer.WebServiceConsumer;
import com.afklm.soa.spring.consumer.WebServiceConsumerBuilder;
import com.afklm.soa.stubs.w000442.v8.CreateUpdateIndividualServiceServiceV8;
import com.afklm.soa.stubs.w000548.v1.HandleCommunicationServiceV1;
import com.afklm.soa.stubs.w001580.v3.ProvideCustomer360ViewServiceV3;
import com.afklm.soa.wss.jaxws.ConsumerSecurityFeature;
import com.sun.jndi.fscontext.RefFSContextFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jndi.JndiTemplate;

import javax.naming.Context;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {	"com.airfrance.jraf.batch.contract", 
								"com.airfrance.jraf.batch.individu",
								"com.airfrance.jraf.batch.invalidation",
								"com.airfrance.jraf.batch.prospect"},
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.airfrance.jraf.batch.config.*"),
        @ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.airfrance.jraf.batch.config.vault.*")})
@PropertySource("classpath:/web-config.properties")
@Import({WebConfigBatchPP.class, WebConfigBatchRef.class, WebConfigBatchRepindWS.class, WebConfigBatchRepindUtf8.class, Neo4jProperties.class})
public class WebConfigBatchRepind {

	@Value("${wsdl.address.env}")
	private String envWsdl;
	
	@Value("${wsdl.address.w000442-v08}")
	private String endpointWsdl000442v8;
	
	@Value("${wsdl.address.handle}")
	private String endpointHandleCom;
	
	@Value("${file.mq.crmevent}")
	private String mqCrmEventUrl;
	
	@Value("${provide.customer.endpoint}")
	private String endpointProvideCustomer;
	
	
    /**
     * Common bean for consumer identification
     * @return ConsumerConfigurationBean
     */
    @Bean
    public ConsumerConfigurationBean wsConsumerIdentification() {

        ConsumerConfigurationBean result = new ConsumerConfigurationBean();
        result.setConsumerID("w04776476");
        result.setConsumerLocation("QVI");
        result.setConsumerType("A");
        result.setPartyID("AF");
        return result;
    }

    /**
     * @return consumer CreateUpdateIndividualServiceServiceV8 web service
     * @throws Exception
     */
    @Bean
    @DependsOn("wsConsumerIdentification")
    public WebServiceConsumer<CreateUpdateIndividualServiceServiceV8> consumerW000442V08() throws Exception {

        WebServiceConsumerBuilder<CreateUpdateIndividualServiceServiceV8> webServiceConsumerBuilder = new WebServiceConsumerBuilder<>();

        ConsumerSecurityFeature feature = new ConsumerSecurityFeature();
        feature.setEnv(envWsdl);
        
        webServiceConsumerBuilder.service(CreateUpdateIndividualServiceServiceV8.class)
        	.wsdlLocation("classpath:/META-INF/wsdl/w000442-v08/w000442-v08-policy.wsdl")
	        .addFeature(feature)
	        .endpointAddress(endpointWsdl000442v8);

        return webServiceConsumerBuilder.getWebServiceConsumer();
    }
    
    @Bean
    public ConsumerJmsFeature consumerJmsFeature() throws IOException {

        ConsumerJmsFeature consumerJmsFeature = new ConsumerJmsFeature();
        consumerJmsFeature.setJndiTemplate(jndiTemplateCRMEVENT());
        
        return consumerJmsFeature;
    }

    
    /**
     * @return consumer HandleCommunicationServiceV1 web service
     * @throws Exception
     */
    @Bean
    @DependsOn("wsConsumerIdentification")
    public WebServiceConsumer<HandleCommunicationServiceV1> consumerHandleCommunication() throws Exception {

        WebServiceConsumerBuilder<HandleCommunicationServiceV1> webServiceConsumerBuilder = new WebServiceConsumerBuilder<>();
        
        webServiceConsumerBuilder.service(HandleCommunicationServiceV1.class)
                .wsdlLocation("classpath:/META-INF/wsdl/w000548-v01/w000548-v01.wsdl")
                .addFeature(consumerJmsFeature())
                .endpointAddress(endpointHandleCom);

        return webServiceConsumerBuilder.getWebServiceConsumer();
    }

    /**
     * @return consumer DeletePaymentPreferencesServiceV1 web service
     * @throws Exception
     */
    @Bean
    @DependsOn("wsConsumerIdentification")
    public WebServiceConsumer<DeletePaymentPreferencesServiceV1> consumerW000471V01() throws Exception {

        WebServiceConsumerBuilder<DeletePaymentPreferencesServiceV1> webServiceConsumerBuilder = new WebServiceConsumerBuilder<>();

        ConsumerSecurityFeature feature = new ConsumerSecurityFeature();
        feature.setEnv(envWsdl);
        
        webServiceConsumerBuilder.service(DeletePaymentPreferencesServiceV1.class)
        		.wsdlLocation("classpath:/META-INF/wsdl/deletepaymentdata/passenger_DeletePaymentPreferences-v1.wsdl")
                .addFeature(feature);

        return webServiceConsumerBuilder.getWebServiceConsumer();
    }


    @Bean
    public JndiTemplate jndiTemplateCRMEVENT() throws IOException {
        JndiTemplate jndiTemplate = new JndiTemplate();
        Properties environment = new Properties();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, RefFSContextFactory.class.getName());
        environment.put(Context.PROVIDER_URL, mqCrmEventUrl);
        jndiTemplate.setEnvironment(environment);
        return jndiTemplate;
        
    }
    
 	// ProvideCustomer360view START
 	 	
 	@Bean(name = "provideCustomer360ViewV3")
	@DependsOn("wsConsumerIdentification")
	public WebServiceConsumer<ProvideCustomer360ViewServiceV3> consumer_ProvideCustomer360View() throws Exception {
	
	    WebServiceConsumerBuilder<ProvideCustomer360ViewServiceV3> webServiceConsumerBuilder = new WebServiceConsumerBuilder<>();
	
	    ConsumerSecurityFeature feature = new ConsumerSecurityFeature();
	    feature.setEnv(envWsdl);
	     
	    webServiceConsumerBuilder.service(ProvideCustomer360ViewServiceV3.class)
	            .wsdlLocation("classpath:/META-INF/wsdl/w001580-v03/w001580-v03-policy.wsdl")
	            .addFeature(feature)
	            .endpointAddress(endpointProvideCustomer);
	
	    return webServiceConsumerBuilder.getWebServiceConsumer();
	}
 	// ProvideCustomer360view END
}
