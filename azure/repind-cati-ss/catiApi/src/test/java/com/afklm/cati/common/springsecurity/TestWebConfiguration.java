package com.afklm.cati.common.springsecurity;

import java.util.Arrays;

import javax.sql.DataSource;

import org.dozer.DozerBeanMapper;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * A configuration bean for tests purposes.
 * 
 * @author t348254
 *
 */
@Profile("test")

@Configuration
@ComponentScan(
	    basePackages = {
                "com.afklm.cati.common.entity",
                "com.afklm.cati.common.service",
                "com.afklm.cati.common.spring.rest.controllers",
                "com.afklm.cati.common.spring.rest.assemblers",
                "com.afklm.cati.common.spring.rest.exceptions",
                "com.afklm.cati.common.spring.springsecurity"
	    		},
	        excludeFilters = @Filter(
	            type = FilterType.ANNOTATION,
	                value = Configuration.class))
@EnableTransactionManagement(proxyTargetClass = false)
@EnableJpaRepositories(
transactionManagerRef = "transactionManagerSic",
basePackages = {"com.afklm.cati.common.repository"},
entityManagerFactoryRef = "entityManagerFactorySic", 
includeFilters = {
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefCountryMarketRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefDgtRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefDomainRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefGroupInfoRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefGroupRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefGTypeRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefMediaRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefMlRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefRepositoryCustom"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefRepositoryImpl"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefComPrefTypeRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefPermissionsQuestionRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefPermissionsRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefTrackingRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefGroupProductRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefProductRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefPreferenceTypeRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefPreferenceKeyTypeRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefPreferenceDataKeyRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.VariablesRepository"),
        @Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.PaysRepository"),
		@Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefPcsFactorRepository"),
		@Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.cati.common.repository.RefPcsScoreRepository")})
@EnableWebMvc
@EnableSpringDataWebSupport
public class TestWebConfiguration {
	
	/**
	 * Builds a datasource.
	 * @return the dataSource.
	 */
	@Bean
	public DataSource dataSourceSIC() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
	}

	/**
	 * Builds the persistence unit manager.
	 * @return the persistence unit manager.
	 */
	@Bean
	public HibernateJpaVendorAdapter jpaAdapter() {
		HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
		jpaAdapter.setDatabasePlatform(H2Dialect.class.getName());
		jpaAdapter.setGenerateDdl(true);
		jpaAdapter.setShowSql(true);
		return jpaAdapter;
	}
	
	/**
	 * Builds the entity manager.
	 * @return the entity manager.
	 */
	@Bean(name = "entityManagerFactorySic")
	public LocalContainerEntityManagerFactoryBean entityManagerSic() {
		final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setDataSource(dataSourceSIC());
		localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManagerSic());
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter());
		localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		localContainerEntityManagerFactoryBean.getJpaPropertyMap().put(Environment.HBM2DDL_AUTO, "update");
		return localContainerEntityManagerFactoryBean;
	}
	

	/**
	 * Builds the persistence unit manager.
	 * @return the persistence unit manager.
	 */
	@Bean(name = "persistenceUnitManagerSic")
	public DefaultPersistenceUnitManager persistenceUnitManagerSic() {
		final DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
		defaultPersistenceUnitManager.setDefaultDataSource(dataSourceSIC());
		defaultPersistenceUnitManager.setPackagesToScan("com.afklm.cati.common.entity");
		return defaultPersistenceUnitManager;
	}

	/**
	 * Builds the transactionManagerSic.
	 * @return the PlatformTransactionManager.
	 */
	@Bean(name = "transactionManagerSic")
	public PlatformTransactionManager transactionManagerSic() {
		return new JpaTransactionManager(entityManagerSic().getObject());
	}
	
	/**
	 * Gets the bean mapper.
	 * @return the bean mapper.
	 */
	@Bean
	public DozerBeanMapper dozerBeanMapper() {
		return new DozerBeanMapper(Arrays.asList("dozer-bean-mappings.xml"));
	}

	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(500000);
		return commonsMultipartResolver;
	}

}
