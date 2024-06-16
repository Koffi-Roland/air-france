package com.airfrance.batch.config;

import oracle.jdbc.pool.OracleDataSource;
import com.airfrance.batch.config.vault.VaultConfiguration;
import com.airfrance.batch.config.vault.VaultProperties;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.env.VaultPropertySource;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.SQLException;

@EnableJpaRepositories(
        basePackages = "com.afklm.repindpp.paymentpreference.dao",
        entityManagerFactoryRef="entityManagerFactoryRepindPP",
        transactionManagerRef = "transactionManagerRepindPP"
)
@Configuration
@EnableConfigurationProperties
@EnableTransactionManagement
@PropertySource({"classpath:/jpa-pp-config.properties"})
@Import({VaultConfiguration.class,
        VaultProperties.class})
public class JpaPPConfig extends JpaCommonConfig{

    @Inject
    private VaultTemplate vaultTemplate;

    @Inject
    VaultProperties vaultProperties;
    @Inject
    private ConfigurableEnvironment environment;

    @Value("${sic.db.url.pp}")
    private String ppDbUrl;

    @Value("${sic.db.tns.pp}")
    private String sicDbTNS;

    @Bean(destroyMethod = "", name = "dataSourceRepindPP")
    public DataSource dataSourceRepindPP() throws SQLException {
        System.setProperty("oracle.net.tns_admin", System.getenv("TNS_ADMIN"));

        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setDriverType(getOracleDriver());
        dataSource.setTNSEntryName(sicDbTNS);
        dataSource.setURL(ppDbUrl);
        dataSource.setUser(environment.getProperty("sicRepindPpUsername"));
        dataSource.setPassword(environment.getProperty("sicRepindPpPassword"));

        return dataSource;
    }

    @Bean(name = "entityManagerFactoryRepindPP")
    public LocalContainerEntityManagerFactoryBean entityManagerRepindPP() throws SQLException {
        final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSourceRepindPP());
        localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManagerRepindPP());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter());
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        localContainerEntityManagerFactoryBean.getJpaPropertyMap().put(AvailableSettings.HBM2DDL_AUTO, "validate");
        return localContainerEntityManagerFactoryBean;
    }

    @Bean(name = "transactionManagerRepindPP")
    public PlatformTransactionManager transactionManagerRepindPP() throws SQLException {
        return new JpaTransactionManager(entityManagerRepindPP().getObject());
    }

    @Bean(name = "persistenceUnitManagerRepindPP")
    public DefaultPersistenceUnitManager persistenceUnitManagerRepindPP() throws SQLException {
        final DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
        defaultPersistenceUnitManager.setDefaultDataSource(dataSourceRepindPP());
        defaultPersistenceUnitManager.setDefaultPersistenceUnitName("persistenceUnitManagerRepindPP");
        defaultPersistenceUnitManager.setPackagesToScan("com.afklm.repindpp.paymentpreference.entity");
        return defaultPersistenceUnitManager;
    }

    @PostConstruct
    public void init() {
        // Add to environment data from vault
        environment.getPropertySources().addFirst(
                new VaultPropertySource(vaultTemplate, "secrets/" + vaultProperties.getVaultEnv() + "/dataBase")
        );
    }
}
