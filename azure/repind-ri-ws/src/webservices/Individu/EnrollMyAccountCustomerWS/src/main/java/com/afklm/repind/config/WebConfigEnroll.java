package com.afklm.repind.config;

import com.afklm.soa.jaxws.customheader.configuration.ConsumerConfigurationBean;
import com.afklm.soa.jms.spring.ConsumerJmsFeature;
import com.afklm.soa.spring.consumer.WebServiceConsumer;
import com.afklm.soa.spring.consumer.WebServiceConsumerBuilder;
import com.afklm.soa.stubs.w000548.v1.HandleCommunicationServiceV1;
import com.airfrance.repind.config.vault.VaultConfigurationRepind;
import com.airfrance.repind.config.vault.VaultPropertiesRepind;
import com.airfrance.repind.util.service.RestTemplateExtended;
import com.sun.jndi.fscontext.RefFSContextFactory;
import org.dozer.DozerBeanMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.inject.Inject;
import javax.naming.Context;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.afklm.repind",
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.repind.config.*")})
@PropertySource("classpath:/web-config.properties")
@Import({
        com.airfrance.ref.util.WebConfigRef.class,
        VaultConfigurationRepind.class,
        VaultPropertiesRepind.class,
        com.airfrance.repind.config.WebConfigRepind.class,
        WSConfig.class
})
@EnableConfigurationProperties
@DependsOn("vaultTemplate")
public class WebConfigEnroll {

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
    
    @Bean
    public ConsumerJmsFeature consumerJmsFeature() throws IOException {

        ConsumerJmsFeature consumerJmsFeature = new ConsumerJmsFeature();
        consumerJmsFeature.setJndiTemplate(jndiTemplateCRMEVENT());
        
        return consumerJmsFeature;
    }

    @Bean(name = "org.dozer.MapperWS.enrollV1")
	public DozerBeanMapper mapperV1() {
		return new DozerBeanMapper(Arrays.asList("config/dozer-bean-mappings-enrollmyaccountcustomer.xml"));
	}
	
	@Bean(name = "org.dozer.MapperWS.enrollV2")
	public DozerBeanMapper mapperV2() {
		return new DozerBeanMapper(Arrays.asList("config/dozer-bean-mappings-enrollmyaccountcustomerv2.xml"));
	}
	
	@Bean(name = "org.dozer.MapperWS.enrollV3")
	public DozerBeanMapper mapperV3() {
		return new DozerBeanMapper(Arrays.asList("config/dozer-bean-mappings-enrollmyaccountcustomerv3.xml"));
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
}
