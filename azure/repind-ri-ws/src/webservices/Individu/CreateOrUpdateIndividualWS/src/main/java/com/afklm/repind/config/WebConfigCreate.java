package com.afklm.repind.config;

import com.afklm.repind.v1.fbeventlistener.FBNewFlyingBlueContractUpdateListenerV1;
import com.afklm.repind.v1.lastciconnectionlistener.LastConnectStatusListenerV1;
import com.afklm.soa.eventheader.EventHeaderFeature;
import com.afklm.soa.jaxws.customheader.configuration.ConsumerConfigurationBean;
import com.afklm.soa.jms.spring.ConsumerJmsFeature;
import com.afklm.soa.jms.spring.ProviderJmsFeature;
import com.afklm.soa.jms.spring.ServerConfig;
import com.afklm.soa.jms.spring.SpringSubscriberBuilder;
import com.afklm.soa.spring.consumer.WebServiceConsumer;
import com.afklm.soa.spring.consumer.WebServiceConsumerBuilder;
import com.afklm.soa.stubs.w000413.v2.StoreMarketingPreferencesCustomerServiceV2;
import com.afklm.soa.stubs.w000548.v1.HandleCommunicationServiceV1;
import com.afklm.soa.wss.jaxws.ConsumerSecurityFeature;
import com.airfrance.repind.config.WebConfigRepindAndUtf8;
import com.airfrance.repind.config.vault.VaultConfigurationRepind;
import com.airfrance.repind.config.vault.VaultPropertiesRepind;
import com.airfrance.repind.util.service.RestTemplateExtended;
import com.sun.jndi.fscontext.RefFSContextFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.inject.Inject;
import javax.naming.Context;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.afklm.repind",
        excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.repind.config.*")})
@PropertySource("classpath:/web-config.properties")
@Import({
        com.airfrance.ref.util.WebConfigRef.class,
        VaultConfigurationRepind.class,
        VaultPropertiesRepind.class,
        WebConfigRepindAndUtf8.class, WSConfig.class
})
@EnableConfigurationProperties
@DependsOn("vaultTemplate")
public class WebConfigCreate {

    @Inject
    private ConfigurableEnvironment env;
	
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
     * @return consumer StoreMarketingPreferencesCustomerServiceV2 web service
     * @throws Exception
     */
    @Bean
    @DependsOn("wsConsumerIdentification")
    public WebServiceConsumer<StoreMarketingPreferencesCustomerServiceV2> consumerW000413V02() throws Exception {

        WebServiceConsumerBuilder<StoreMarketingPreferencesCustomerServiceV2> webServiceConsumerBuilder = new WebServiceConsumerBuilder<>();

        ConsumerSecurityFeature feature = new ConsumerSecurityFeature();
        feature.setEnv(env.getProperty("wsdl.address.w000413-v02.env"));
        
        webServiceConsumerBuilder.service(StoreMarketingPreferencesCustomerServiceV2.class)
                .wsdlLocation("classpath:/META-INF/wsdl/w000413/V2/passenger_StoreMarketingPreferencesCustomer-v02.wsdl")
                .addFeature(feature)
                .addFeature(eventHeaderFeature())
                .endpointAddress(env.getProperty("wsdl.address.w000413-v02"));

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
                .addFeature(eventHeaderFeature())
                .endpointAddress(env.getProperty("wsdl.address.handle"));

        return webServiceConsumerBuilder.getWebServiceConsumer();
    }

    @Bean
    public JndiTemplate jndiTemplateCRMEVENT() throws IOException {
        JndiTemplate jndiTemplate = new JndiTemplate();
        Properties environment = new Properties();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, RefFSContextFactory.class.getName());
        environment.put(Context.PROVIDER_URL, env.getProperty("file.mq.crmevent"));
        jndiTemplate.setEnvironment(environment);
        return jndiTemplate;
        
    }
    
    /**
     * @return consumer fBNewFlyingBlueContractUpdateNotificationV1 feature
     * @throws Exception
     */
    @Bean
    public ProviderJmsFeature fBNewFlyingBlueContractUpdateNotificationV1Feature() throws Exception {

    	ProviderJmsFeature providerJmsFeature = new ProviderJmsFeature();
    	providerJmsFeature.setJndiTemplate(jndiTemplateFBN());
    	providerJmsFeature.setReceiveTimeout(new Long(10000));
    	providerJmsFeature.setSessionCacheSize(1);

        return providerJmsFeature;
    }
    
    @Bean
    public JndiTemplate jndiTemplateFBN() throws IOException {
        JndiTemplate jndiTemplate = new JndiTemplate();
        Properties environment = new Properties();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, RefFSContextFactory.class.getName());
        environment.put(Context.PROVIDER_URL, env.getProperty("file.mq.fbn"));
        jndiTemplate.setEnvironment(environment);
        return jndiTemplate;
        
    }
 
	@Bean
	public PropertiesFactoryBean headerProperties() {
		
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("header.properties"));
		
		return propertiesFactoryBean;
	}
	
	@Bean
    public ServerConfig wsFBNewFlyingBlueContractUpdateNotificationListenerV1() throws Exception {
		
        SpringSubscriberBuilder springSubscriberBuilder = new SpringSubscriberBuilder();
        springSubscriberBuilder
                .bean(fBNewFlyingBlueContractUpdateListenerV1())
                .url(env.getProperty("wsdl.address.w001815"))
                .primaryWsdl("classpath:/META-INF/wsdl/w001815-v02/w001815-v02.wsdl")
                .addFeature(fBNewFlyingBlueContractUpdateNotificationV1Feature())
                .addFeature(eventHeaderFeature());
        return springSubscriberBuilder.build();
        
    }

	@Bean
    public FBNewFlyingBlueContractUpdateListenerV1 fBNewFlyingBlueContractUpdateListenerV1() {
        return new FBNewFlyingBlueContractUpdateListenerV1();
    }   

	@Bean
    public EventHeaderFeature eventHeaderFeature() {
    	EventHeaderFeature eventHeaderFeature = new EventHeaderFeature();
    	eventHeaderFeature.setPublisher(null);
        return eventHeaderFeature;
    }
		
	@Bean(name = "manageConsent")
	public RestTemplateExtended consumerManageConsent() throws Exception {
		RestTemplateExtended restTemplaceExtended = new RestTemplateExtended();
		restTemplaceExtended.setUriTemplateHandler(new DefaultUriBuilderFactory(env.getProperty("manage-consent.url.location", "")));
		SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(1000);
		clientHttpRequestFactory.setReadTimeout(1000);
		restTemplaceExtended.setRequestFactory(clientHttpRequestFactory);
		restTemplaceExtended.setApiKey(env.getProperty("apiKeyManageConsent", ""));
		restTemplaceExtended.setApiSecret(env.getProperty("apiSecretManageConsent", ""));
			
		return restTemplaceExtended;
	}
	
	@Bean
	public ServerConfig wsLastConnectStatusListenerV1() throws Exception {
		SpringSubscriberBuilder springSubscriberBuilder = new SpringSubscriberBuilder();
		springSubscriberBuilder.bean(lastConnectStatusListenerV1()).url(env.getProperty("wsdl.address.w002577"))
				.primaryWsdl("classpath:/META-INF/wsdl/w002577-v01/w002577-v01.wsdl")
				.addFeature(lastConnectStatusNotificationV10Feature())
				.addFeature(eventHeaderFeature());
		return springSubscriberBuilder.build();
	}

	@Bean
	public LastConnectStatusListenerV1 lastConnectStatusListenerV1() {
		return new LastConnectStatusListenerV1();
	}

	@Bean
	public ProviderJmsFeature lastConnectStatusNotificationV10Feature() {
		ProviderJmsFeature providerJmsFeature = new ProviderJmsFeature();
		providerJmsFeature.setJndiTemplate(jndiTemplateLcic());
		providerJmsFeature.setReceiveTimeout(10000L);
		providerJmsFeature.setSessionCacheSize(1);
		return providerJmsFeature;
	}

	@Bean
	public JndiTemplate jndiTemplateLcic() {
		JndiTemplate jndiTemplate = new JndiTemplate();
		Properties environment = new Properties();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, RefFSContextFactory.class.getName());
		environment.put(Context.PROVIDER_URL, env.getProperty("file.mq.lcic"));
		jndiTemplate.setEnvironment(environment);
		return jndiTemplate;

	}
}
