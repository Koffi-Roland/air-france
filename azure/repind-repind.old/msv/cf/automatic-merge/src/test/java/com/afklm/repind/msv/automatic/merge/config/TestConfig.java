package com.afklm.repind.msv.automatic.merge.config;

import com.afklm.repind.common.config.database.ConfigDatabaseScan;
import com.afklm.repind.common.config.documentation.ConfigDocumentationScan;
import com.afklm.repind.common.config.vault.ConfigVaultConfigurationScan;
import com.afklm.repind.common.controller.advice.ControllerAdviceScan;
import com.afklm.repind.common.controller.metric.MetricControllerScan;
import com.afklm.repind.common.service.vault.ServiceVaultScan;
import com.afklm.soa.jaxws.customheader.configuration.ConsumerConfigurationBean;
import com.afklm.soa.spring.consumer.WebServiceConsumer;
import com.afklm.soa.stubs.w002122.v1.ProvideFBContractMergePreferenceServiceV1;
import com.afklm.soa.stubs.w002122.v1.ProvideFBContractMergePreferenceV1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@TestConfiguration
@ComponentScan(
        basePackages = {
                "com.afklm.repind.common.service"})
@Import(value= {
        ConfigDocumentationScan.class,
        MetricControllerScan.class,
        ControllerAdviceScan.class,
        ConfigVaultConfigurationScan.class,
        ConfigDatabaseScan.class,
        ServiceVaultScan.class,
})
public class TestConfig {

    @Autowired
    private Environment env;

    @Bean
    public ConsumerConfigurationBean wsConsumerIdentification() {

        ConsumerConfigurationBean result = new ConsumerConfigurationBean();
        result.setConsumerID("w04776476");
        result.setConsumerLocation("QVI");
        result.setConsumerType("A");
        result.setPartyID("AF");
        return result;
    }

    @Bean(name = "consumerW002122v01")
    @DependsOn("wsConsumerIdentification")
    public WebServiceConsumer<ProvideFBContractMergePreferenceV1> consumerW002122v01() throws Exception {
        WebServiceConsumer<ProvideFBContractMergePreferenceV1> webServiceConsumerBuilder = new WebServiceConsumer<>();
        webServiceConsumerBuilder.service(ProvideFBContractMergePreferenceServiceV1.class)
                .wsdlLocation(env.getProperty("spring.wsdl.location.w002122-v01"))
                .endpointAddress(env.getProperty("spring.wsdl.address.w002122-v01"));
        return webServiceConsumerBuilder;
    }
}
