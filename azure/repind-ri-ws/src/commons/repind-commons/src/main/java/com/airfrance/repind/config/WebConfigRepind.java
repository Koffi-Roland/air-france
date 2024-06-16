package com.airfrance.repind.config;

import com.afklm.soa.spring.consumer.DefaultConsumerTimeout;
import com.airfrance.repind.client.dqe.RNVP;
import com.airfrance.repind.client.dqe.RNVPDqeClient;
import com.airfrance.repind.config.vault.VaultConfigurationRepind;
import com.airfrance.repind.config.vault.VaultPropertiesRepind;
import com.airfrance.repind.service.individu.internal.PurgeIndividualDS;
import com.airfrance.repind.service.ws.internal.CreateOrUpdateIndividualDS;
import com.airfrance.repind.service.ws.internal.helpers.UtfHelper;
import com.airfrance.repind.util.service.RestTemplateExtended;
import org.dozer.DozerBeanMapper;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.airfrance.repind",
		excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.airfrance.repind.config.*"),
				@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=PurgeIndividualDS.class),
				@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=UtfHelper.class),
				@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=CreateOrUpdateIndividualDS.class)})
@EnableJpaRepositories(
		transactionManagerRef = "transactionManagerRepind",
		basePackages = "com.airfrance.repind.dao",
		entityManagerFactoryRef = "entityManagerFactoryRepind"
)
@EnableTransactionManagement
@Import({
		com.airfrance.ref.util.WebConfigRef.class,
		VaultConfigurationRepind.class,
		VaultPropertiesRepind.class
})
@EnableConfigurationProperties
@DependsOn("vaultTemplate")
public class WebConfigRepind {

	@Autowired
	private HibernateJpaVendorAdapter jpaAdapter;

	@Inject
	private ConfigurableEnvironment environment;

	/**
	 * Builds a JNDI datasource.
	 *
	 * @return the datasource.
	 */
	@Bean(destroyMethod = "")
	public DataSource dataSourceREPIND() {
		final JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
		jndiObjectFactoryBean.setJndiName("jdbc/repind");
		jndiObjectFactoryBean.setResourceRef(true);
		jndiObjectFactoryBean.setExpectedType(DataSource.class);

		// Set the JNDI environment with the username and password provided in Vault
		Properties jndiEnvironment = new Properties();
		jndiEnvironment.setProperty(Context.SECURITY_PRINCIPAL, environment.getProperty("sicUsername"));
		jndiEnvironment.setProperty(Context.SECURITY_CREDENTIALS, environment.getProperty("sicPassword"));
		jndiObjectFactoryBean.setJndiEnvironment(jndiEnvironment);
		try {
			jndiObjectFactoryBean.afterPropertiesSet();
		} catch (final NamingException e) {
			throw new RuntimeException(e);
		}
		return (DataSource) jndiObjectFactoryBean.getObject();
	}

	@Bean
	public DozerBeanMapper dozerBeanMapper() {
		return new DozerBeanMapper(Arrays.asList("config/dozer-bean-mappings.xml"));
	}


	// DEFAULT TIMEOUT START
	@Bean
	public DefaultConsumerTimeout defaultConsumerTimeout() {
		DefaultConsumerTimeout defaultConsumerTimeout = new DefaultConsumerTimeout();
		defaultConsumerTimeout.setConnectionTimeoutMilliseconds(3000);
		defaultConsumerTimeout.setRequestTimeoutMilliseconds(3000);
		return defaultConsumerTimeout;
	}
	// DEFAULT TIMEOUT END

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

	@Bean
	public RNVPDqeClient rnvpDqeClient(){
		return new RNVPDqeClient("https://prod2.dqe-software.com/RNVP/?Adresse={adresse}&Pays={pays}&Licence={licence}&Modification={modification}" , HttpMethod.GET , RNVP.class, rnvpDqeRestTemplate());
	}

	@Bean(name = "rnvpDqeRestTemplate")
	public RestTemplateExtended rnvpDqeRestTemplate(){
		RestTemplateExtended restTemplaceExtended = new RestTemplateExtended();
		SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(1000);
		clientHttpRequestFactory.setReadTimeout(1000);
		restTemplaceExtended.setRequestFactory(clientHttpRequestFactory);

		return restTemplaceExtended;
	}

}
