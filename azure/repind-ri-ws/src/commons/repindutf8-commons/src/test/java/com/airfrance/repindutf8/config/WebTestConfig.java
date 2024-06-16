package com.airfrance.repindutf8.config;

import com.airfrance.ref.util.WebConfigRef;
import com.airfrance.repindutf8.config.vault.VaultConfigurationUTF8;
import com.airfrance.repindutf8.config.vault.VaultPropertiesUTF8;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement 
@ComponentScan(basePackages = "com.airfrance.repindutf8",
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.airfrance.repindutf8.config.*")})
@EnableJpaRepositories(
	basePackages = "com.airfrance.repindutf8.dao",
	entityManagerFactoryRef="entityManagerFactoryRepindUtf8",
	transactionManagerRef = "transactionManagerRepindUtf8"
)
@Import({
		WebConfigRef.class,
		VaultConfigurationUTF8.class,
		VaultPropertiesUTF8.class
})
@EnableConfigurationProperties
@PropertySource({"classpath:/vault-test.properties"})
@DependsOn("vaultTemplate")
public class WebTestConfig {

	@Autowired
	private HibernateJpaVendorAdapter jpaAdapter;

	@Inject
	private ConfigurableEnvironment environment;

	@Bean(destroyMethod = "", name = "dataSourceRepindUtf8")
	public DataSource dataSourceRepindUtf8() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@lh-dsicutf802-db.france.airfrance.fr:1521:ODMN303");
		dataSource.setUsername(environment.getProperty("sicUtf8Username"));
		dataSource.setPassword(environment.getProperty("sicUtf8Password"));

		return dataSource;
	}

	/**
	 * Builds the persistence unit manager.
	 * 
	 * @return the persistence unit manager.
	 */
	@Bean(name = "entityManagerFactoryRepindUtf8")
	public LocalContainerEntityManagerFactoryBean entityManagerRepindUtf8() {
		final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setDataSource(dataSourceRepindUtf8());
		localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManagerRepindUtf8());
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
	@Bean(name = "transactionManagerRepindUtf8")
	public PlatformTransactionManager transactionManagerRepindUtf8() {
		return new JpaTransactionManager(entityManagerRepindUtf8().getObject());
	}

	/**
	 * Builds the persistence unit manager.
	 * 
	 * @return the persistence unit manager.
	 */
	@Bean(name = "persistenceUnitManagerRepindUtf8")
	public DefaultPersistenceUnitManager persistenceUnitManagerRepindUtf8() {
		final DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
		defaultPersistenceUnitManager.setDefaultDataSource(dataSourceRepindUtf8());
		defaultPersistenceUnitManager.setPackagesToScan("com.airfrance.repindutf8.entity");
		defaultPersistenceUnitManager.setDefaultPersistenceUnitName("persistenceUnitManagerRepindUtf8");
		return defaultPersistenceUnitManager;
	}
}
