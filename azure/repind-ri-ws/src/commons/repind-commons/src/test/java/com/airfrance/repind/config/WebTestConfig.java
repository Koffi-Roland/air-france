package com.airfrance.repind.config;

import com.afklm.repindpp.paymentpreference.config.WebConfigTestConfig;
import com.afklm.soa.stubs.w001793.v1.InWeboLoginCreateV10;
import com.airfrance.repind.client.dqe.RNVP;
import com.airfrance.repind.client.dqe.RNVPDqeClient;
import com.airfrance.repind.config.vault.VaultConfigurationRepind;
import com.airfrance.repind.config.vault.VaultPropertiesRepind;
import com.airfrance.repind.util.service.RestTemplateExtended;
import org.dozer.DozerBeanMapper;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Profile("test")

@Configuration
@ComponentScan(basePackages = "com.airfrance.repind",
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.airfrance.repind.config.*")})
@EnableJpaRepositories(
		transactionManagerRef = "transactionManagerRepind",
		basePackages = "com.airfrance.repind.dao",
		entityManagerFactoryRef = "entityManagerFactoryRepind"
)
@EnableTransactionManagement
@Import({
	com.airfrance.ref.util.WebConfigRef.class,
		com.airfrance.repindutf8.config.WebTestConfig.class,
		WebConfigTestConfig.class,
		VaultConfigurationRepind.class,
		VaultPropertiesRepind.class
})
@EnableConfigurationProperties
@PropertySource({"classpath:/vault-test.properties"})
@DependsOn("vaultTemplate")
public class WebTestConfig {
	
	@Autowired
	private HibernateJpaVendorAdapter jpaAdapter;

	@Inject
	private ConfigurableEnvironment environment;

	@Bean(destroyMethod = "", name = "dataSourceRepind")
	public DataSource dataSourceRepind() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@lh-dsic02-db.france.airfrance.fr:1523:ODMN305");
		dataSource.setUsername(environment.getProperty("sicUsername"));
		dataSource.setPassword(environment.getProperty("sicPassword"));

		return dataSource;
	}
	@Bean
	public DozerBeanMapper dozerBeanMapper() {
		return new DozerBeanMapper(Arrays.asList("dozer-bean-mappings-test.xml"));
	}

	@Bean(name = "consumerW001793v1")
	public InWeboLoginCreateV10 consumerW001793v1() throws MalformedURLException {
			
		return Mockito.mock(InWeboLoginCreateV10.class);
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
		localContainerEntityManagerFactoryBean.setDataSource(dataSourceRepind());
		localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManagerRepind());
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter);
		localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		return localContainerEntityManagerFactoryBean;
	}
	
	
	@Bean(name = "persistenceUnitManagerRepind")
	@Primary
	public DefaultPersistenceUnitManager persistenceUnitManagerRepind() {
		final DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
		defaultPersistenceUnitManager.setDefaultDataSource(dataSourceRepind());
		defaultPersistenceUnitManager.setPackagesToScan("com.airfrance.repind.entity");
		defaultPersistenceUnitManager.setDefaultPersistenceUnitName("persistenceUnitManagerRepind");
		return defaultPersistenceUnitManager;
	}
		
	@Bean(name = "manageConsent")
	public RestTemplateExtended consumerManageConsent() throws Exception {
		RestTemplateExtended restTemplaceExtended = new RestTemplateExtended();
		restTemplaceExtended.setUriTemplateHandler(new DefaultUriBuilderFactory("https://repind-api-dev.airfrance.fr/api-mgr/manage-consent"));
		SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(1000);
		clientHttpRequestFactory.setReadTimeout(1000);
		restTemplaceExtended.setRequestFactory(clientHttpRequestFactory);
		restTemplaceExtended.setApiKey("c3czby9yp5dg45eug65kzx86");
		restTemplaceExtended.setApiSecret("SK5NvA633B");
		
		return restTemplaceExtended;
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
