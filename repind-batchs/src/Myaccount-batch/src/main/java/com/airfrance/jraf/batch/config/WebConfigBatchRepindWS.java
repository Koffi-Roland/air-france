package com.airfrance.jraf.batch.config;

import com.airfrance.jraf.batch.config.vault.VaultConfiguration;
import com.airfrance.jraf.batch.config.vault.VaultProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import oracle.jdbc.pool.OracleDataSource;
import org.dozer.DozerBeanMapper;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.env.VaultPropertySource;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@ComponentScan(basePackages = "com.airfrance.repind",
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.airfrance.repind.config.*"),
		@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.airfrance.repind.config.vault.*")})
@EnableJpaRepositories(
		transactionManagerRef = "transactionManagerRepind",
		basePackages = "com.airfrance.repind.dao",
		entityManagerFactoryRef = "entityManagerFactoryRepind"
)
@PropertySource("classpath:/web-config.properties")
@Import({VaultConfiguration.class,
		VaultProperties.class})
@EnableAutoConfiguration(exclude = {
	    JmxAutoConfiguration.class
	})
public class WebConfigBatchRepindWS {
	
	@Autowired
	private HibernateJpaVendorAdapter jpaAdapter;

	@Inject
	private VaultTemplate vaultTemplate;

	@Inject
	private VaultProperties vaultProperties;
	@Inject
	private ConfigurableEnvironment environment;


	@Value("${datasource.repind.tns}")
	private String sicDbTNS;

	@Value("${datasource.repind.url}")
	private String sicDbURL;

	@Value("${datasource.maxpool.size}")
	private int maxPoolSize;

	@Bean(destroyMethod = "", name = "dataSourceRepind")
	public DataSource dataSourceREPIND() throws SQLException {
		System.setProperty("oracle.net.tns_admin", System.getenv("TNS_ADMIN"));

		HikariConfig hkConfig = new HikariConfig();
		HikariDataSource hkDatasource;

		hkConfig.setJdbcUrl(sicDbURL);
		hkConfig.setDriverClassName("oracle.jdbc.OracleDriver");
		hkConfig.setUsername(environment.getProperty("sicUsername"));
		hkConfig.setPassword(environment.getProperty("sicPassword"));

		hkDatasource = new HikariDataSource(hkConfig);
		hkDatasource.setMaximumPoolSize(maxPoolSize);
		return hkDatasource;

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
	public PlatformTransactionManager transactionManagerRepind() throws SQLException {
		return new JpaTransactionManager(entityManagerRepind().getObject());
	}
	
	@Bean(name = "entityManagerFactoryRepind")
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerRepind() throws SQLException {
		final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setDataSource(dataSourceREPIND());
		localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManagerRepind());
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter);
		localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		return localContainerEntityManagerFactoryBean;
	}
	
	
	@Bean(name = "persistenceUnitManagerRepind")
	@Primary
	public DefaultPersistenceUnitManager persistenceUnitManagerRepind() throws SQLException {
		final DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
		defaultPersistenceUnitManager.setDefaultDataSource(dataSourceREPIND());
		defaultPersistenceUnitManager.setPackagesToScan("com.airfrance.repind.entity");
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
