package com.afklm.rigui.spring;

import com.afklm.rigui.util.service.RestTemplateExtended;
import com.afklm.soa.jaxws.customheader.configuration.ConsumerConfigurationBean;
import com.afklm.soa.spring.consumer.WebServiceConsumer;
import com.afklm.soa.stubs.w000442.v8_0_1.CreateUpdateIndividualServiceServiceV8;
import com.afklm.soa.stubs.w000442.v8_0_1.CreateUpdateIndividualServiceV8;
import com.afklm.soa.stubs.w001274.v1.MergeStaffGinServiceV1;
import com.afklm.soa.stubs.w001274.v1.MergeStaffGinV1;
import com.afklm.soa.stubs.w001588.v1_0_1.ProvideIndividualReferenceTableServiceServiceV1;
import com.afklm.soa.stubs.w001588.v1_0_1.ProvideIndividualReferenceTableServiceV1;
import com.afklm.soa.stubs.w002008.v1.CreateUpdateIndividualGPServiceServiceV1;
import com.afklm.soa.stubs.w002008.v1.CreateUpdateIndividualGPServiceV1;
import com.afklm.soa.stubs.w002122.v1.ProvideFBContractMergePreferenceServiceV1;
import com.afklm.soa.stubs.w002122.v1.ProvideFBContractMergePreferenceV1;
import org.dozer.DozerBeanMapper;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Collections;

/**
 * A configuration bean for tests purposes.
 *
 * @author DINB - TA
 */
@Configuration
@Profile("test")
@ComponentScan(basePackages = {
        "com.afklm.rigui.dao",
        "com.afklm.rigui.services",
        "com.afklm.rigui.service",
        "com.afklm.rigui.mapper",
        "com.airfrance.sicutf8",
        "com.afklm.soa.stubs",
        "com.afklm.rigui.util"
},
        excludeFilters = {@Filter(
                type = FilterType.ANNOTATION,
                value = Configuration.class),
                @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "com.airfrance.sicutf8.config.*"),
                @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.rigui.config.*")})

@EnableTransactionManagement()
@Import({ConfigUTF8.class, ConfigRepindPP.class})
@EnableJpaRepositories(
        transactionManagerRef = "transactionManagerSic",
        basePackages = {
                "com.afklm.rigui.repository",
                "com.afklm.rigui.dao"
        },
        entityManagerFactoryRef = "entityManagerFactorySic")

public class TestConfiguration {

    @Autowired
    private Environment env;

    @Configuration
    @Profile("test")
    @PropertySource("classpath:/WEB-INF/ws.properties")
    static class test {
    }

    /**
     * Builds a JNDI datasource.
     *
     * @return the datasource.
     */
    @Bean(destroyMethod = "")
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@lh-dsic01-db.france.airfrance.fr:1521:SIC");
        dataSource.setUsername("SIC2");
        dataSource.setPassword("SIC2");

        return dataSource;
    }

    @Bean(name = "entityManagerFactorySic")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySic() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManagerSic());
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter());
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean(name = "transactionManagerSic")
    @Primary
    public JpaTransactionManager transactionManagerSic() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactorySic().getObject());
        return jpaTransactionManager;
    }

    @Bean(name = "persistenceUnitManagerSic")
    @Primary
    public DefaultPersistenceUnitManager persistenceUnitManagerSic() {
        final DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
        defaultPersistenceUnitManager.setDefaultDataSource(dataSource());
        defaultPersistenceUnitManager.setPackagesToScan("com.afklm.rigui.entity");
        return defaultPersistenceUnitManager;
    }

    /**
     * Returns the Generate DDL flag.
     *
     * @return the Generate DDL flag.
     */
    @Bean
    public Boolean generateDdl() {
        return Boolean.FALSE;
    }

    /**
     * Gets the bean mapper.
     *
     * @return the bean mapper.
     */
    @Bean
    public DozerBeanMapper dozerBeanMapper() {
        return new DozerBeanMapper(Collections.singletonList("dozer-bean-mappings-test.xml"));
    }

    /**
     * Builds the persistence unit manager.
     *
     * @return the persistence unit manager.
     */
    @Bean
    public HibernateJpaVendorAdapter jpaAdapter() {
        final HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
        jpaAdapter.setDatabasePlatform(jpaDialect());
        jpaAdapter.setGenerateDdl(generateDdl());
        jpaAdapter.setShowSql(showSql());
        return jpaAdapter;
    }

    /**
     * Returns the JPA dialect to use.
     *
     * @return the JPA dialect to use.
     */
    @Bean
    public String jpaDialect() {
        return "org.hibernate.dialect.Oracle10gDialect";
    }

    /**
     * Returns the Show SQL flag.
     *
     * @return the Show SQL flag.
     */
    @Bean
    public Boolean showSql() {
        return Boolean.FALSE;
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

    /*
    thhiss
     */
    @Bean(name = "consumerW000442v8")
    public WebServiceConsumer<CreateUpdateIndividualServiceV8> consumerW000442v8() throws Exception {
        return new WebServiceConsumer<CreateUpdateIndividualServiceV8>()
                .service(CreateUpdateIndividualServiceServiceV8.class)
                .wsdlLocation(env.getProperty("wsdl.location.w000442-v08"))
                .endpointAddress(env.getProperty("wsdl.address.w000442-v08"));
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

    @Bean(name = "manageConsent")
    public RestTemplateExtended manageConsent() {
        return new RestTemplateExtended();
    }

    @Bean(name = "manageHandicap")
    public RestTemplateExtended manageHandicap() {
        return new RestTemplateExtended();
    }

    @Bean(name = "manageMerge")
    public RestTemplateExtended manageMerge() {
        return new RestTemplateExtended();
    }

    @Bean(name = "searchIndividual")
    public RestTemplateExtended searchIndividual() {
        return new RestTemplateExtended();
    }

}
