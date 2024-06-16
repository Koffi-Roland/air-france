package com.airfrance.repindutf8.config;

import com.airfrance.repindutf8.config.vault.VaultConfigurationUTF8;
import com.airfrance.repindutf8.config.vault.VaultPropertiesUTF8;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

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
		VaultConfigurationUTF8.class,
		VaultPropertiesUTF8.class
})
@EnableConfigurationProperties
@DependsOn("vaultTemplate")
public class WebConfigUTF8 {
	
	@Autowired
	private HibernateJpaVendorAdapter jpaAdapter;

	@Inject
	private ConfigurableEnvironment environment;

	@Bean(destroyMethod = "", name = "dataSourceRepindUtf8")
	public DataSource dataSourceRepindUtf8() {
		final JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
		jndiObjectFactoryBean.setJndiName("jdbc/sicutf8");
		jndiObjectFactoryBean.setResourceRef(true);
		jndiObjectFactoryBean.setExpectedType(DataSource.class);

		// Set the JNDI environment with the username and password provided in Vault
		Properties jndiEnvironment = new Properties();
		jndiEnvironment.setProperty(Context.SECURITY_PRINCIPAL, environment.getProperty("sicUtf8Username"));
		jndiEnvironment.setProperty(Context.SECURITY_CREDENTIALS, environment.getProperty("sicUtf8Password"));
		jndiObjectFactoryBean.setJndiEnvironment(jndiEnvironment);

		try {
			jndiObjectFactoryBean.afterPropertiesSet();
		} catch (final NamingException e) {
			throw new RuntimeException(e);
		}
		return (DataSource) jndiObjectFactoryBean.getObject();
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
		return defaultPersistenceUnitManager;
	}
}
