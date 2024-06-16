package com.afklm.rigui.spring.configuration;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories(transactionManagerRef = "transactionManagerSic", basePackages = { "com.afklm.rigui.dao",
		"com.afklm.rigui.repository" }, entityManagerFactoryRef = "entityManagerFactorySic")
@Configuration
public class DBConfigurationSIC extends AbstractDBConfiguration {
	
	/** logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBConfigurationSIC.class);

	@Autowired
	@Qualifier("ds_sic")
	private DataSource dataSourceSic;
	
	@Bean(name = "entityManagerFactorySic")
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactorySic() {
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManagerSic());
		localContainerEntityManagerFactoryBean.setDataSource(dataSourceSic);
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter());
		localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		return localContainerEntityManagerFactoryBean;
	}
	
	@Bean(name = "transactionManagerSic")
	@Primary
	public JpaTransactionManager transactionManagerSic() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactorySic().getObject());
		return jpaTransactionManager;
	}

	@Bean(name = "persistenceUnitManagerSic")
	@Primary
	public DefaultPersistenceUnitManager persistenceUnitManagerSic() {
		final DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
		defaultPersistenceUnitManager.setDefaultDataSource(dataSourceSic);
		defaultPersistenceUnitManager.setPackagesToScan("com.afklm.rigui.entity");
		return defaultPersistenceUnitManager;
	}
}
