package com.afklm.rigui.spring;

import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
                basePackages = "com.afklm.repind.paymentpreference.dao",
                entityManagerFactoryRef="entityManagerFactorySicPP",
                transactionManagerRef = "transactionManagerSicPP"
)
public class ConfigRepindPP {
    @Autowired
    Environment env;

    @Autowired
    private HibernateJpaVendorAdapter jpaAdapter;

    @Bean(destroyMethod = "", name = "dataSourceSicPP")
    public DataSource dataSourceSicPP() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@lh-drepind01-db.france.airfrance.fr:1523:REPIND");
        dataSource.setUsername("repind_pp");
        dataSource.setPassword("repind_pp");

        return dataSource;
    }

    @Bean(name = "entityManagerFactorySicPP")
    public LocalContainerEntityManagerFactoryBean entityManagerSicPP() {
        final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSourceSicPP());
        localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManagerSicPP());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter);
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        localContainerEntityManagerFactoryBean.getJpaPropertyMap().put(AvailableSettings.HBM2DDL_AUTO, "validate");
        return localContainerEntityManagerFactoryBean;
    }

    @Bean(name = "transactionManagerSicPP")
    public PlatformTransactionManager transactionManagerSicPP() {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerSicPP().getObject()));
    }

    @Bean(name = "persistenceUnitManagerSicPP")
    public DefaultPersistenceUnitManager persistenceUnitManagerSicPP() {
        final DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
        defaultPersistenceUnitManager.setDefaultDataSource(dataSourceSicPP());
        defaultPersistenceUnitManager.setPackagesToScan("com.afklm.repind.paymentpreference.entity");
        return defaultPersistenceUnitManager;
    }
}
