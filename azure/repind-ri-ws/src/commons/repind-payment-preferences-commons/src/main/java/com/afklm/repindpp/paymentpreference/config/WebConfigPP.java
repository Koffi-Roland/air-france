package com.afklm.repindpp.paymentpreference.config;

import com.afklm.repindpp.paymentpreference.config.vault.VaultConfigurationPP;
import com.afklm.repindpp.paymentpreference.config.vault.VaultPropertiesPP;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
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
@ComponentScan(basePackages = "com.afklm.repindpp",
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.repindpp.paymentpreference.config.*")})
@EnableJpaRepositories(
	basePackages = "com.afklm.repindpp.paymentpreference.dao",
	entityManagerFactoryRef="entityManagerFactoryRepindPP",
	transactionManagerRef = "transactionManagerRepindPP"
)
@Import({
		VaultConfigurationPP.class,
		VaultPropertiesPP.class
})
@EnableConfigurationProperties
@DependsOn("vaultTemplate")
public class WebConfigPP {

	@Autowired
	Environment env;

	@Inject
	private ConfigurableEnvironment environment;

	@Autowired
	private HibernateJpaVendorAdapter jpaAdapter;
	
	@Bean(destroyMethod = "", name = "dataSourceRepindPP")
	public DataSource dataSourceRepindPP() {
		final JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
		jndiObjectFactoryBean.setJndiName("jdbc/repind_pp");
		jndiObjectFactoryBean.setResourceRef(true);
		jndiObjectFactoryBean.setExpectedType(DataSource.class);

		// Set the JNDI environment with the username and password provided in Vault
		Properties jndiEnvironment = new Properties();
		jndiEnvironment.setProperty(Context.SECURITY_PRINCIPAL, environment.getProperty("sicRepindPpUsername"));
		jndiEnvironment.setProperty(Context.SECURITY_CREDENTIALS, environment.getProperty("sicRepindPpPassword"));
		jndiObjectFactoryBean.setJndiEnvironment(jndiEnvironment);
		try {
			jndiObjectFactoryBean.afterPropertiesSet();
		} catch (final NamingException e) {
			throw new RuntimeException(e);
		}
		return (DataSource) jndiObjectFactoryBean.getObject();
	}
	
	@Bean(name = "entityManagerFactoryRepindPP")
	public LocalContainerEntityManagerFactoryBean entityManagerRepindPP() {
		final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setDataSource(dataSourceRepindPP());
		localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManagerRepindPP());
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter);
		localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		return localContainerEntityManagerFactoryBean;
	}

	@Bean(name = "transactionManagerRepindPP")
	public PlatformTransactionManager transactionManagerRepindPP() {
		return new JpaTransactionManager(entityManagerRepindPP().getObject());
	}

	@Bean(name = "persistenceUnitManagerRepindPP")
	public DefaultPersistenceUnitManager persistenceUnitManagerRepindPP() {
		final DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
		defaultPersistenceUnitManager.setDefaultDataSource(dataSourceRepindPP());
		defaultPersistenceUnitManager.setPackagesToScan("com.afklm.repindpp.paymentpreference.entity");
		return defaultPersistenceUnitManager;
	}

}
