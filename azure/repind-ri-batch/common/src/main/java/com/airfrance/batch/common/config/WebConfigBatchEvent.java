package com.airfrance.batch.common.config;

import com.afklm.soa.eventheader.EventHeaderFeature;
import com.afklm.soa.jms.spring.ConsumerJmsFeature;
import com.afklm.soa.spring.consumer.WebServiceConsumer;
import com.afklm.soa.spring.consumer.WebServiceConsumerBuilder;
import com.afklm.soa.stubs.w001539.v1.NotifyEventIndividualServiceV1;
import com.sun.jndi.fscontext.RefFSContextFactory;
import com.sun.xml.ws.developer.SchemaValidationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jndi.JndiTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.naming.InitialContext;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = "com.airfrance.jraf.batch.event.individu", 
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.airfrance.jraf.batch.config.*")})
@PropertySource("classpath:/web-config.properties")
@Import(WebConfigBatchRepind.class)
@ImportResource({"/config/event-ind-application-context-spring.xml"})
public class WebConfigBatchEvent {
	
	@Value("${file.mq.event.ind}")
	private String mqEventIndUrl;
	
	@Value("${file.mq.event.sec}")
	private String mqEventSecUrl;
	
	@Value("${wsdl.address.w001539-v01}")
	private String endpointWsdl001539v1;
    
	@Value("${wsdl.address.w001992-v01}")
	private String endpointWsdl001992v1;
	
	@Value("${wsdl.address.w001690-v01}")
	private String endpointWsdl001690v1;
	
    @Bean 
    public ScheduledExecutorService scheduledExecutorService() {
    	return Executors.newScheduledThreadPool(5);
    }
    
    /**
     * @return consumer NotifyEventIndividualServiceV1 web service
     * @throws Exception
     */
    @Bean("notifyEventIndividual-v1")
    @DependsOn("wsConsumerIdentification")
    public WebServiceConsumer<NotifyEventIndividualServiceV1> consumerW001539V01() throws Exception {

        WebServiceConsumerBuilder<NotifyEventIndividualServiceV1> webServiceConsumerBuilder = new WebServiceConsumerBuilder<NotifyEventIndividualServiceV1>();
        
        webServiceConsumerBuilder
        		.service(NotifyEventIndividualServiceV1.class)
        		.wsdlLocation("classpath:/META-INF/wsdl/w001539-v01/w001539-v01.wsdl")
                .endpointAddress(endpointWsdl001539v1)
                .addFeature(consumerJmsFeatureNEI())
                .addFeature(new SchemaValidationFeature())
                .addFeature(eventHeaderFeature());

        return webServiceConsumerBuilder.getWebServiceConsumer();
    }

    /**
     * @return consumer NotifyAdhocEventIndividualServiceV1 web service
     * @throws Exception
     */
//    @Bean("NotifyAdhocEventIndividual-v1")
//    @DependsOn("wsConsumerIdentification")
//    public WebServiceConsumer<NotifyAdhocEventIndividualServiceV1> consumerW001992V01() throws Exception {
//
//        WebServiceConsumerBuilder<NotifyAdhocEventIndividualServiceV1> webServiceConsumerBuilder = new WebServiceConsumerBuilder<NotifyAdhocEventIndividualServiceV1>();
//        
//        webServiceConsumerBuilder
//        		.service(NotifyAdhocEventIndividualServiceV1.class)
//        		.wsdlLocation("classpath:/META-INF/wsdl/w001992-v01/w001992-v01.wsdl")
//        		.endpointAddress(endpointWsdl001992v1)
//                .addFeature(consumerJmsFeatureNAEI())
//                .addFeature(new SchemaValidationFeature())
//                .addFeature(eventHeaderFeature());
//
//        return webServiceConsumerBuilder.getWebServiceConsumer();
//    }

    /**
     * @return consumer NotifySecurityEventForIndividualServiceV1 web service
     * @throws Exception
     */
//    @Bean("NotifySecurityEventForIndividual-v1")
//    @DependsOn("wsConsumerIdentification")
//    public WebServiceConsumer<NotifySecurityEventForIndividualServiceV1> consumerW001690V01() throws Exception {
//
//        WebServiceConsumerBuilder<NotifySecurityEventForIndividualServiceV1> webServiceConsumerBuilder = new WebServiceConsumerBuilder<NotifySecurityEventForIndividualServiceV1>();
//        
//        webServiceConsumerBuilder
//        		.service(NotifySecurityEventForIndividualServiceV1.class)
//        		.wsdlLocation("classpath:/META-INF/wsdl/w001690-v01/w001690-v01.wsdl")
//        		.endpointAddress(endpointWsdl001690v1)
//                .addFeature(consumerJmsFeatureNSEI())
//                .addFeature(new SchemaValidationFeature())
//                .addFeature(eventHeaderFeature());
//
//        return webServiceConsumerBuilder.getWebServiceConsumer();
//    }
    
    /**
     * @return consumer notify individual feature
     * @throws Exception
     */
    @Bean
    public ConsumerJmsFeature consumerJmsFeatureNEI() throws Exception {

    	ConsumerJmsFeature consumerJmsFeature = new ConsumerJmsFeature();
    	consumerJmsFeature.setJndiTemplate(jndiTemplateEventNEI());
    	consumerJmsFeature.setTimeout(1000);
    	consumerJmsFeature.setTimeToLive(1000);
    	return consumerJmsFeature;

    }
    
    /**
     * @return consumer notify individual and adhoc feature
     * @throws Exception
     */
//    @Bean
//    public ConsumerJmsFeature consumerJmsFeatureNAEI() throws Exception {
//
//    	ConsumerJmsFeature consumerJmsFeature = new ConsumerJmsFeature();
//    	consumerJmsFeature.setJndiTemplate(jndiTemplateEventNAEI());
//    	consumerJmsFeature.setTimeout(1000);
//    	consumerJmsFeature.setTimeToLive(1000);
//    	return consumerJmsFeature;
//    }

    /**
     * @return consumer notify individual security feature
     * @throws Exception
     */
//    @Bean
//    public ConsumerJmsFeature consumerJmsFeatureNSEI() throws Exception {
//
//    	ConsumerJmsFeature consumerJmsFeature = new ConsumerJmsFeature();
//    	consumerJmsFeature.setJndiTemplate(jndiTemplateEventNSEI());
//    	consumerJmsFeature.setTimeout(1000);
//    	consumerJmsFeature.setTimeToLive(1000);
//    	return consumerJmsFeature;
//    }
    
    @Bean
    public EventHeaderFeature eventHeaderFeature() {
    	EventHeaderFeature eventHeaderFeature = new EventHeaderFeature();
    	eventHeaderFeature.setPublisher(null);
        return eventHeaderFeature;
    }
    
    @Bean
    public JndiTemplate jndiTemplateEventNEI() throws IOException {
        JndiTemplate jndiTemplate = new JndiTemplate();
        Properties environment = new Properties();
        environment.put(InitialContext.INITIAL_CONTEXT_FACTORY, RefFSContextFactory.class.getName());
        environment.put(InitialContext.PROVIDER_URL, mqEventIndUrl);
        jndiTemplate.setEnvironment(environment);
        return jndiTemplate;
        
    }
    
//    @Bean
//    public JndiTemplate jndiTemplateEventNAEI() throws IOException {
//        JndiTemplate jndiTemplate = new JndiTemplate();
//        Properties environment = new Properties();
//        environment.put(InitialContext.INITIAL_CONTEXT_FACTORY, RefFSContextFactory.class.getName());
//        environment.put(InitialContext.PROVIDER_URL, mqEventIndUrl);
//        jndiTemplate.setEnvironment(environment);
//        return jndiTemplate;
//        
//    }
    
//    @Bean
//    public JndiTemplate jndiTemplateEventNSEI() throws IOException {
//        JndiTemplate jndiTemplate = new JndiTemplate();
//        Properties environment = new Properties();
//        environment.put(InitialContext.INITIAL_CONTEXT_FACTORY, RefFSContextFactory.class.getName());
//        environment.put(InitialContext.PROVIDER_URL, mqEventSecUrl);
//        jndiTemplate.setEnvironment(environment);
//        return jndiTemplate;
//        
//    }
}
