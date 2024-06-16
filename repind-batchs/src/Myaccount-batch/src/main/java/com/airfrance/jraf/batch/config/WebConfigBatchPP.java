package com.airfrance.jraf.batch.config;


import com.airfrance.jraf.batch.config.vault.VaultConfiguration;
import com.airfrance.jraf.batch.config.vault.VaultProperties;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.vault.core.VaultTemplate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.vault.core.env.VaultPropertySource;

import java.sql.SQLException;


@Configuration
@EnableTransactionManagement 
@ComponentScan(basePackages = "com.afklm.repindpp",
excludeFilters={@ComponentScan.Filter(type= FilterType.ASPECTJ, pattern = "com.afklm.repindpp.paymentpreference.config.*"),
		@ComponentScan.Filter(type= FilterType.ASPECTJ, pattern = "com.afklm.repindpp.paymentpreference.config.vault.*")})

@PropertySource("classpath:/web-config.properties")
@EnableJpaRepositories(
	basePackages = "com.afklm.repindpp.paymentpreference.dao",
	entityManagerFactoryRef="entityManagerFactoryRepindPP",
	transactionManagerRef = "transactionManagerRepindPP"
)
@Import({VaultConfiguration.class,
		VaultProperties.class})
public class WebConfigBatchPP {

	@Autowired
	private HibernateJpaVendorAdapter jpaAdapter;

	@Inject
	private VaultTemplate vaultTemplate;

	@Inject
	private ConfigurableEnvironment environment;

	@Value("${datasource.repindpp.url}")
	private String ppDbUrl;

	@Value("${datasource.repindpp.tns}")
	private String sicDbTNS;
	@Inject
	private VaultProperties vaultProperties;

	
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
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter);
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
		defaultPersistenceUnitManager.setPackagesToScan("com.afklm.repindpp.paymentpreference.entity");
		return defaultPersistenceUnitManager;
	}

	@PostConstruct
	public void init() {
		environment.getPropertySources().addFirst(
				new VaultPropertySource(vaultTemplate, "secrets/" + vaultProperties.getVaultEnv() + "/dataBase")
		);
	}

	public String getOracleDriver(){
		return "oracle.jdbc.driver.OracleDriver";
	}
}
