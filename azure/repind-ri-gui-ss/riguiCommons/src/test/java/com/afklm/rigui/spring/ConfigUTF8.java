package com.afklm.rigui.spring;

import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Objects;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
                basePackages = "com.airfrance.sicutf8.dao",
                entityManagerFactoryRef="entityManagerFactorySicUtf8",
                transactionManagerRef = "transactionManagerSicUtf8"
)
public class ConfigUTF8 {

    @Autowired
    private HibernateJpaVendorAdapter jpaAdapter;

    @Bean(destroyMethod = "", name = "dataSourceSicUtf8")
    public DataSource dataSourceSicUtf8() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@lh-dsicutf801-db.france.airfrance.fr:1522:SICUTF8");
        dataSource.setUsername("sic_utf8");
        dataSource.setPassword("sic_utf8");

        return dataSource;
    }

    /**
     * Builds the persistence unit manager.
     *
     * @return the persistence unit manager.
     */
    @Bean(name = "entityManagerFactorySicUtf8")
    public LocalContainerEntityManagerFactoryBean entityManagerSicUtf8() {
        final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSourceSicUtf8());
        localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManagerSicUtf8());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter);
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        localContainerEntityManagerFactoryBean.getJpaPropertyMap().put(AvailableSettings.HBM2DDL_AUTO, "validate");
        return localContainerEntityManagerFactoryBean;
    }

    /**
     * Builds the persistence unit manager.
     *
     * @return the persistence unit manager.
     */
    @Bean(name = "transactionManagerSicUtf8")
    public PlatformTransactionManager transactionManagerSicUtf8() {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerSicUtf8().getObject()));
    }

    /**
     * Builds the persistence unit manager.
     *
     * @return the persistence unit manager.
     */
    @Bean(name = "persistenceUnitManagerSicUtf8")
    public DefaultPersistenceUnitManager persistenceUnitManagerSicUtf8() {
        final DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
        defaultPersistenceUnitManager.setDefaultDataSource(dataSourceSicUtf8());
        defaultPersistenceUnitManager.setPackagesToScan("com.airfrance.sicutf8.entity");
        defaultPersistenceUnitManager.setDefaultPersistenceUnitName("persistenceUnitManagerSicUtf8");
        return defaultPersistenceUnitManager;
    }
}
