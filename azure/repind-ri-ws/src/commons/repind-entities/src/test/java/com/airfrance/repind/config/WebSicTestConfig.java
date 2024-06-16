package com.airfrance.repind.config;

import org.dozer.DozerBeanMapper;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Profile("test")

@Configuration
@ComponentScan(basePackages = "com.airfrance.repind",
		excludeFilters = { @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "com.airfrance.repind.config.*") })
@EnableJpaRepositories(
		transactionManagerRef = "transactionManagerRepind",
		basePackages = "com.airfrance.repind.dao",
		entityManagerFactoryRef = "entityManagerFactoryRepind"
)
@EnableTransactionManagement
@Import({
	com.airfrance.ref.util.WebConfigRef.class
})
public class WebSicTestConfig {
	@Autowired
	private Environment env;
	
	@Autowired
	private HibernateJpaVendorAdapter jpaAdapter;
	
	@Bean(destroyMethod = "", name = "dataSourceRepind")
	public DataSource dataSourceREPIND() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@lh-dsic02-db.france.airfrance.fr:1523:ODMN305");
		dataSource.setUsername("sic2");
		dataSource.setPassword("sic2");
		
		return dataSource;
	}

	@Bean
	public DozerBeanMapper dozerBeanMapper() {
		return new DozerBeanMapper(Arrays.asList("dozer-bean-mappings-test.xml"));
	}
	
	@Bean
	public DozerBeanMapper mapper_common(){
		DozerBeanMapper mapper = new DozerBeanMapper();
		List<String> files = new ArrayList<>();
		files.add("config/dozer-bean-mappings.xml");
		mapper.setMappingFiles(files);
		return mapper;
	}

	@Bean(name = "transactionManagerRepind")
	@Primary
	public PlatformTransactionManager transactionManagerRepind() {
		return new JpaTransactionManager(entityManagerRepind().getObject());
	}
	
	@Bean(name = "entityManagerFactoryRepind")
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerRepind() {
		final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setDataSource(dataSourceREPIND());
		localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManagerRepind());
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter);
		localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		return localContainerEntityManagerFactoryBean;
	}
	
	
	@Bean(name = "persistenceUnitManagerRepind")
	@Primary
	public DefaultPersistenceUnitManager persistenceUnitManagerRepind() {
		final DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
		defaultPersistenceUnitManager.setDefaultDataSource(dataSourceREPIND());
		defaultPersistenceUnitManager.setPackagesToScan("com.airfrance.repind.entity");
		return defaultPersistenceUnitManager;
	}
	
}
