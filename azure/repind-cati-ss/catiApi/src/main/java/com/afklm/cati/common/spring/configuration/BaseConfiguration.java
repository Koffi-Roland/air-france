package com.afklm.cati.common.spring.configuration;

import org.dozer.DozerBeanMapper;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * A base configuration bootstrapping the application context.
 *
 * @author DINB - TA
 */
@Configuration
@Import({DefaultProfileConfiguration.class, H2ProfileConfiguration.class,
        LocalBdProfileConfiguration.class, SecurityConfiguration.class})

/**
 *
 * @author m312697
 * For Habile WS
 */
@ImportResource({"/WEB-INF/config/habile000479-ws-layer.xml"})

@ComponentScan(
        basePackages = {
                "com.afklm.cati.common"
        },
        excludeFilters = @Filter(
                type = FilterType.ANNOTATION,
                value = Configuration.class))
@EnableTransactionManagement(proxyTargetClass = false)
@EnableJpaRepositories(
        transactionManagerRef = "transactionManagerSic",
        basePackages = {"com.afklm.cati.common.repository"},
        entityManagerFactoryRef = "entityManagerFactorySic",
        includeFilters = {
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefCountryMarketRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefDgtRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefDomainRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefGroupInfoRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefGroupRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefGTypeRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefMediaRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefMlRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefRepositoryCustom"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefRepositoryImpl"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefTypeRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefPermissionsQuestionRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefPermissionsRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefTrackingRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefGroupProductRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefProductRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefPreferenceDataKeyRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefPreferenceKeyRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefPreferenceKeyTypeRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefPreferenceTypeRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.VariablesRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.PaysRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefPcsFactorRepository"),
                @Filter(type = FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefPcsScoreRepository")})

public class BaseConfiguration {

    protected static ResourceBundle catiResourceBundle = PropertyResourceBundle.getBundle("cati-config");

    /**
     * The datasource provided by other configurations.
     */
    @Autowired
    private DataSource dataSourceSIC;

    @Bean(name = "jpaDialect")
    public String jpaDialect() {
        return "org.hibernate.dialect.Oracle10gDialect";
    }

    @Bean(name = "showSql")
    public Boolean showSql() {
        return Boolean.valueOf(catiResourceBundle.getString("hibernate.showSql"));
    }


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
        return new DozerBeanMapper(Arrays.asList("dozer-bean-mappings.xml"));
    }

    /**
     * Builds the entity manager.
     *
     * @return the entity manager.
     */
    @Bean(name = "entityManagerFactorySic")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerSic() {
        final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSourceSIC);
        localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManagerSic());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter());
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return localContainerEntityManagerFactoryBean;
    }


    /**
     * Builds the persistence unit manager.
     *
     * @return the persistence unit manager.
     */
    @Bean(name = "persistenceUnitManagerSic")
    @Primary
    public DefaultPersistenceUnitManager persistenceUnitManagerSic() {
        final DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
        defaultPersistenceUnitManager.setDefaultDataSource(dataSourceSIC);
        defaultPersistenceUnitManager.setPackagesToScan("com.afklm.cati.common.entity");
        return defaultPersistenceUnitManager;
    }

    /**
     * Builds the transactionManagerSic.
     *
     * @return the PlatformTransactionManager.
     */
    @Bean(name = "transactionManagerSic")
    @Primary
    public PlatformTransactionManager transactionManagerSic() {
        return new JpaTransactionManager(entityManagerSic().getObject());
    }

    /**
     * Builds the persistence unit manager.
     *
     * @return the persistence unit manager.
     */
    @Bean
    public HibernateJpaVendorAdapter jpaAdapter() {
        HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
        jpaAdapter.setDatabasePlatform(jpaDialect());
        jpaAdapter.setShowSql(showSql());
        return jpaAdapter;
    }

}
