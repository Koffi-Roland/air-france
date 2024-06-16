package com.airfrance.batch.common.config;

import com.airfrance.batch.common.config.vault.VaultConfiguration;
import com.airfrance.batch.common.config.vault.VaultProperties;
import oracle.jdbc.pool.OracleDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
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
        transactionManagerRef = "transactionManagerRepind",
        basePackages = {"com.airfrance.repind.dao","com.airfrance.batch.common.repository"},
        entityManagerFactoryRef = "entityManagerFactoryRepind"
)
@Configuration
@EnableConfigurationProperties
@PropertySource({"classpath:/jpa-sic-config.properties","classpath:/local.properties"})
@Import({VaultConfiguration.class,
        VaultProperties.class})
public class JpaRepindConfig extends JpaCommonConfig{

    @Inject
    private VaultTemplate vaultTemplate;

    @Inject
    VaultProperties vaultProperties;
    @Inject
    private ConfigurableEnvironment environment;

    @Value("${sic.db.url.sic}")
    private String sicDbUrl;

    @Value("${sic.db.tns.sic}")
    private String sicDbTNS;

    @Bean(destroyMethod = "", name = "dataSourceRepind")
    @Primary
    public DataSource dataSourceRepind() throws SQLException {
        System.setProperty("oracle.net.tns_admin", System.getenv("TNS_ADMIN"));

        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setDriverType(getOracleDriver());
        dataSource.setTNSEntryName(sicDbTNS);
        dataSource.setURL(sicDbUrl);
        dataSource.setUser(environment.getProperty("sicUsername"));
        dataSource.setPassword(environment.getProperty("sicPassword"));

        return dataSource;
    }

    @Bean(name = "transactionManagerRepind")
    @Primary
    public PlatformTransactionManager transactionManagerRepind() throws SQLException {
        return new JpaTransactionManager(entityManagerRepind().getObject());
    }

    @Bean(name = "entityManagerFactoryRepind")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerRepind() throws SQLException {
        final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSourceRepind());
        localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManagerRepind());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter());
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean(name = "persistenceUnitManagerRepind")
    @Primary
    public DefaultPersistenceUnitManager persistenceUnitManagerRepind() throws SQLException {
        final DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
        defaultPersistenceUnitManager.setDefaultDataSource(dataSourceRepind());
        defaultPersistenceUnitManager.setDefaultPersistenceUnitName("persistenceUnitManagerRepind");
        defaultPersistenceUnitManager.setPackagesToScan("com.airfrance.repind.entity","com.airfrance.batch.common.entity");
        return defaultPersistenceUnitManager;
    }
    @PostConstruct
    public void init() {
        environment.getPropertySources().addFirst(
                new VaultPropertySource(vaultTemplate, "secrets/" + vaultProperties.getVaultEnv() + "/dataBase")
        );
    }
}

