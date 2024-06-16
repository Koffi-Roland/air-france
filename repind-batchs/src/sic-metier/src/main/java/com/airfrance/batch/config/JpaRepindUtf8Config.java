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
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.env.VaultPropertySource;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.SQLException;

@EnableJpaRepositories(
        basePackages = "com.airfrance.repindutf8.dao",
        entityManagerFactoryRef="entityManagerFactoryRepindUtf8",
        transactionManagerRef = "transactionManagerRepindUtf8"
)
@Configuration
@EnableConfigurationProperties
@PropertySource({"classpath:/jpa-sic-utf8-config.properties"})
@Import({VaultConfiguration.class,
        VaultProperties.class})
public class JpaRepindUtf8Config extends JpaCommonConfig {

    @Inject
    private VaultTemplate vaultTemplate;

    @Inject
    VaultProperties vaultProperties;
    @Inject
    private ConfigurableEnvironment environment;
    @Value("${sic.db.url.utf8}")
    private String sicDbUrl;

    @Value("${sic.db.tns.utf8}")
    private String sicDbTNS;

    @Bean(destroyMethod = "", name = "dataSourceRepindUtf8")
    public DataSource dataSourceRepindUtf8() throws SQLException {
        System.setProperty("oracle.net.tns_admin", System.getenv("TNS_ADMIN"));

        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setDriverType(getOracleDriver());
        dataSource.setTNSEntryName(sicDbTNS);
        dataSource.setURL(sicDbUrl);
        dataSource.setUser(environment.getProperty("sicUtf8Username"));
        dataSource.setPassword(environment.getProperty("sicUtf8Password"));

        return dataSource;
    }

    /**
     * Builds the persistence unit manager.
     * @return the persistence unit manager.
     */
    @Bean(name = "entityManagerFactoryRepindUtf8")
    public LocalContainerEntityManagerFactoryBean entityManagerRepindUtf8() throws SQLException {
        final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSourceRepindUtf8());
        localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManagerRepindUtf8());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter());
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        localContainerEntityManagerFactoryBean.getJpaPropertyMap().put(AvailableSettings.HBM2DDL_AUTO, "validate");
        return localContainerEntityManagerFactoryBean;
    }

    /**
     * Builds the persistence unit manager.
     * @return the persistence unit manager.
     */
    @Bean(name = "transactionManagerRepindUtf8")
    public PlatformTransactionManager transactionManagerRepindUtf8() throws SQLException {
        return new JpaTransactionManager(entityManagerRepindUtf8().getObject());
    }

    /**
     * Builds the persistence unit manager.
     * @return the persistence unit manager.
     */
    @Bean(name = "persistenceUnitManagerRepindUtf8")
    public DefaultPersistenceUnitManager persistenceUnitManagerRepindUtf8() throws SQLException {
        final DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
        defaultPersistenceUnitManager.setDefaultDataSource(dataSourceRepindUtf8());
        defaultPersistenceUnitManager.setPackagesToScan("com.airfrance.repindutf8.entity");
        defaultPersistenceUnitManager.setDefaultPersistenceUnitName("persistenceUnitManagerRepindUtf8");
        return defaultPersistenceUnitManager;
    }

    @PostConstruct
    public void init() {
        environment.getPropertySources().addFirst(
                new VaultPropertySource(vaultTemplate, "secrets/" + vaultProperties.getVaultEnv() + "/dataBase")
        );
    }
}
