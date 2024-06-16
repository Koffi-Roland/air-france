package com.afklm.rigui.spring.configuration;

import com.afklm.rigui.util.service.RestTemplateExtended;
import com.afklm.soa.jaxws.customheader.configuration.ConsumerConfigurationBean;
import com.afklm.soa.spring.consumer.WebServiceConsumer;
import com.afklm.soa.stubs.w000442.v8_0_1.CreateUpdateIndividualServiceServiceV8;
import com.afklm.soa.stubs.w000442.v8_0_1.CreateUpdateIndividualServiceV8;
import com.afklm.soa.stubs.w001271.v2.SearchIndividualByMulticriteriaServiceServiceV2;
import com.afklm.soa.stubs.w001271.v2.SearchIndividualByMulticriteriaServiceV2;
import com.afklm.soa.stubs.w001274.v1.MergeStaffGinServiceV1;
import com.afklm.soa.stubs.w001274.v1.MergeStaffGinV1;
import com.afklm.soa.stubs.w001588.v1_0_1.ProvideIndividualReferenceTableServiceServiceV1;
import com.afklm.soa.stubs.w001588.v1_0_1.ProvideIndividualReferenceTableServiceV1;
import com.afklm.soa.stubs.w002008.v1.CreateUpdateIndividualGPServiceServiceV1;
import com.afklm.soa.stubs.w002008.v1.CreateUpdateIndividualGPServiceV1;
import com.afklm.soa.stubs.w002122.v1.ProvideFBContractMergePreferenceServiceV1;
import com.afklm.soa.stubs.w002122.v1.ProvideFBContractMergePreferenceV1;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Collections;

/**
 * A base configuration bootstrapping the application context.
 */
@ComponentScan(
        basePackages = {"com.afklm.rigui",
                "com.afklm.soa.stubs", "com.afklm.rigui"}, excludeFilters = {
        @Filter(type = FilterType.ANNOTATION,value = Configuration.class),
        @Filter(type = FilterType.ASPECTJ, pattern = "com.airfrance.sicutf8.config.*"),
        @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.rigui.config.*")})
@EnableTransactionManagement
@Configuration
public class BaseConfigurationTest {

    @Autowired
    private Environment env;

    @Configuration
    @Profile("default")
    @PropertySource("classpath:/web-config.properties")
    static class Defaults {
    }

    @Configuration
    @Profile("dev")
    @PropertySource("classpath:/config/web-config-dev.properties")
    static class Dev {
        // nothing needed here if you are only overriding property values
    }

    @Configuration
    @Profile("rct")
    @PropertySource("classpath:/config/web-config-rct.properties")
    static class Rct {
        // nothing needed here if you are only overriding property values
    }

    @Configuration
    @Profile("rc2")
    @PropertySource("classpath:/config/web-config-rc2.properties")
    static class Rc2 {
        // nothing needed here if you are only overriding property values
    }

    @Configuration
    @Profile("prd")
    @PropertySource("classpath:/config/web-config-prd.properties")
    static class Prd {
        // nothing needed here if you are only overriding property values
    }

    @Configuration
    @Profile("qal")
    @PropertySource("classpath:/config/web-config-qal.properties")
    static class Qal {
        // nothing needed here if you are only overriding property values
    }

    /**
     * Gets the bean mapper.
     *
     * @return the bean mapper.
     */
    @Bean
    public DozerBeanMapper dozerBeanMapper() {
        return new DozerBeanMapper(Collections.singletonList("dozer-bean-mappings.xml"));
    }

    @Bean
     public ConsumerConfigurationBean ConsumerConfigurationBean() {
        ConsumerConfigurationBean result = new ConsumerConfigurationBean();
        result.setConsumerID("w03194405");
        result.setConsumerLocation("QVI");
        result.setConsumerType("A");
        result.setPartyID("AFKL");
        return result;
    }

    @Bean(name = "consumerW000442v8")
    public WebServiceConsumer<CreateUpdateIndividualServiceV8> consumerW000442v8() throws Exception {
        return new WebServiceConsumer<CreateUpdateIndividualServiceV8>()
                .service(CreateUpdateIndividualServiceServiceV8.class)
                .wsdlLocation(env.getProperty("wsdl.location.w000442-v08"))
                .endpointAddress(env.getProperty("wsdl.address.w000442-v08"));
    }

    @Bean(name = "consumerW001271v2")
    public WebServiceConsumer<SearchIndividualByMulticriteriaServiceV2> consumerW001271v2() throws Exception {
        return new WebServiceConsumer<SearchIndividualByMulticriteriaServiceV2>()
                .service(SearchIndividualByMulticriteriaServiceServiceV2.class)
                .wsdlLocation(env.getProperty("wsdl.location.w001271-v02"))
                .endpointAddress(env.getProperty("wsdl.address.w001271-v02"));
    }

    @Bean(name = "consumerW002122v01")
    public WebServiceConsumer<ProvideFBContractMergePreferenceV1> consumerW002122v01() throws Exception {
        return new WebServiceConsumer<ProvideFBContractMergePreferenceV1>()
                .service(ProvideFBContractMergePreferenceServiceV1.class)
                .wsdlLocation(env.getProperty("wsdl.location.w002122-v01"))
                .endpointAddress(env.getProperty("wsdl.address.w002122-v01"));
    }

    @Bean(name = "consumerW001274v01")
    public WebServiceConsumer<MergeStaffGinV1> consumerW001274v01() throws Exception {
        return new WebServiceConsumer<MergeStaffGinV1>().service(MergeStaffGinServiceV1.class)
                .wsdlLocation(env.getProperty("wsdl.location.w001274-v01"))
                .endpointAddress(env.getProperty("wsdl.address.w001274-v01"));
    }

    @Bean(name = "consumerW002008v01")
    public WebServiceConsumer<CreateUpdateIndividualGPServiceV1> consumerW002008v01() throws Exception {
        return new WebServiceConsumer<CreateUpdateIndividualGPServiceV1>()
                .service(CreateUpdateIndividualGPServiceServiceV1.class)
                .wsdlLocation(env.getProperty("wsdl.location.w002008-v01"))
                .endpointAddress(env.getProperty("wsdl.address.w002008-v01"));
    }

    @Bean(name = "consumerW001588v01")
    public WebServiceConsumer<ProvideIndividualReferenceTableServiceV1> consumerW001588v01() throws Exception {
        return new WebServiceConsumer<ProvideIndividualReferenceTableServiceV1>()
                .service(ProvideIndividualReferenceTableServiceServiceV1.class)
                .wsdlLocation(env.getProperty("wsdl.location.w001588-v01"))
                .endpointAddress(env.getProperty("wsdl.address.w001588-v01"));
    }

    @Bean(name = "manageHandicap")
    public RestTemplateExtended consumerManageHandicap() throws Exception {
        RestTemplateExtended restTemplate = new RestTemplateExtended();
        restTemplate.setApiKey(env.getProperty("mashery.key.manage-handicap"));
        restTemplate.setApiSecret(env.getProperty("mashery.secret.manage-handicap"));
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(env.getProperty("url.location.manage-handicap")));
        return restTemplate;
    }

    @Bean(name = "manageConsent")
    public RestTemplateExtended consumerManageConsent() throws Exception {
        RestTemplateExtended restTemplate = new RestTemplateExtended();
        restTemplate.setApiKey(env.getProperty("mashery.key.manage-consent"));
        restTemplate.setApiSecret(env.getProperty("mashery.secret.manage-consent"));
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(env.getProperty("url.location.manage-consent")));
        return restTemplate;
    }

    @Bean(name = "manageMerge")
    public RestTemplateExtended consumerAutoMerge() {
        RestTemplateExtended restTemplate = new RestTemplateExtended();
        restTemplate.setApiKey(env.getProperty("mashery.key.merge"));
        restTemplate.setApiSecret(env.getProperty("mashery.secret.merge"));
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(env.getProperty("url.location.merge")));
        return restTemplate;
    }

}
