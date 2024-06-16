package com.airfrance.batch.common.config;

import com.airfrance.batch.common.config.vault.VaultConfiguration;
import com.airfrance.batch.common.config.vault.VaultProperties;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.env.VaultPropertySource;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

@EnableJpaRepositories(
        transactionManagerRef = "transactionManagerSicOds",
        basePackages = "com.airfrance.batch.common.entity",
        entityManagerFactoryRef = "entityManagerFactorySicOds"
)
@Configuration
@EnableConfigurationProperties
@PropertySource({"classpath:/jpa-sic-ods-config.properties","classpath:/local.properties"})
@Import({VaultConfiguration.class,
        VaultProperties.class})
public class JpaSicOdsConfig extends JpaCommonConfig{

    @Inject
    private VaultTemplate vaultTemplate;
    @Inject
    VaultProperties vaultProperties;
    @Inject
    private ConfigurableEnvironment environment;

    @Value("${sic.db.url.ods}")
    private String sicOdsDbUrl;

    @Bean(destroyMethod = "", name = "dataSourceSicOds")
    public DataSource dataSourceSicOds() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(getOracleDriver());
        dataSource.setUrl(sicOdsDbUrl);
        dataSource.setUsername(environment.getProperty("sicOdsUsername"));
        dataSource.setPassword(environment.getProperty("sicOdsPassword"));
        return dataSource;
    }

    @Bean(name = "transactionManagerSicOds")
    public PlatformTransactionManager transactionManagerSicOds() {
        return new JpaTransactionManager(entityManagerSicOds().getObject());
    }

    @Bean(name = "entityManagerFactorySicOds")
    public LocalContainerEntityManagerFactoryBean entityManagerSicOds() {
        final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSourceSicOds());
        localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManagerSicOds());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter());
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean(name = "persistenceUnitManagerSicOds")
    public DefaultPersistenceUnitManager persistenceUnitManagerSicOds() {
        final DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
        defaultPersistenceUnitManager.setDefaultDataSource(dataSourceSicOds());
        defaultPersistenceUnitManager.setDefaultPersistenceUnitName("persistenceUnitManagerSicOds");
        defaultPersistenceUnitManager.setPackagesToScan("com.afklm.batch.detectduplicates.entity");
        return defaultPersistenceUnitManager;
    }

    @PostConstruct
    public void init() {
        environment.getPropertySources().addFirst(
                new VaultPropertySource(vaultTemplate, "secrets/" + vaultProperties.getVaultEnv() + "/dataBase")
        );
    }
}
