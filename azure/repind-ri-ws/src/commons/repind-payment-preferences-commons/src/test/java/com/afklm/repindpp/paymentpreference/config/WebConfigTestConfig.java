package com.afklm.repindpp.paymentpreference.config;

import com.afklm.repindpp.paymentpreference.config.vault.VaultConfigurationPP;
import com.afklm.repindpp.paymentpreference.config.vault.VaultPropertiesPP;
import com.airfrance.ref.util.WebConfigRef;
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
@ComponentScan(basePackages = "com.afklm.repindpp",
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.repindpp.paymentpreference.config.*")})
@EnableJpaRepositories(
	basePackages = "com.afklm.repindpp.paymentpreference.dao",
	entityManagerFactoryRef="entityManagerFactoryRepindPP",
	transactionManagerRef = "transactionManagerRepindPP"
)
@Import({
		WebConfigRef.class,
		VaultConfigurationPP.class,
		VaultPropertiesPP.class
})
@EnableConfigurationProperties
@PropertySource({"classpath:/vault-test.properties"})
@DependsOn("vaultTemplate")
public class WebConfigTestConfig {

	@Autowired
	Environment env;
	
	@Autowired
	private HibernateJpaVendorAdapter jpaAdapter;

	@Inject
	private ConfigurableEnvironment environment;
	
	@Bean(destroyMethod = "", name = "dataSourceRepindPP")
	public DataSource dataSourceRepindPP() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@lh-drepind02-db.france.airfrance.fr:1522:ODMN306");
		dataSource.setUsername(environment.getProperty("sicRepindPpUsername"));
		dataSource.setPassword(environment.getProperty("sicRepindPpPassword"));

		return dataSource;
	}
	
	@Bean(name = "entityManagerFactoryRepindPP")
	public LocalContainerEntityManagerFactoryBean entityManagerRepindPP() {
		final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setDataSource(dataSourceRepindPP());
		localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManagerRepindPP());
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter);
		localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		localContainerEntityManagerFactoryBean.getJpaPropertyMap().put(AvailableSettings.HBM2DDL_AUTO, "validate");
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
