package com.airfrance.batch.config;

import com.airfrance.batch.compref.migklsub.writer.IndividuItemWriter;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@ComponentScan(basePackages = {	"com.airfrance.repind.dao.individu", "com.airfrance.batch.compref.migklsub"} , excludeFilters={
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= IndividuItemWriter.class)})
@EnableJpaRepositories(
        transactionManagerRef = "transactionManagerRepind",
        basePackages = "com.airfrance.repind.dao",
        entityManagerFactoryRef = "entityManagerFactoryRepind"
)
@Configuration
public class JpaSicConfigTest {

    /**
     * Builds the persistence unit manager.
     *
     * @return the persistence unit manager.
     */
    @Bean
    public HibernateJpaVendorAdapter jpaAdapter() {
        final HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
        jpaAdapter.setDatabasePlatform(jpaDialect());
        jpaAdapter.setGenerateDdl(generateDdl());
        jpaAdapter.setShowSql(showSql());
        return jpaAdapter;
    }

    /**
     * Returns the Show SQL flag.
     *
     * @return the Show SQL flag.
     */
    @Bean
    public Boolean showSql() {
        return Boolean.FALSE;
    }

    /**
     * Returns the Generate DDL flag.
     *
     * @return the Generate DDL flag.
     */
    @Bean
    public Boolean generateDdl() {
        return Boolean.FALSE;
    }

    /**
     * Returns the JPA dialect to use.
     *
     * @return the JPA dialect to use.
     */
    @Bean
    public String jpaDialect() {
        return "org.hibernate.dialect.H2Dialect";
    }

    @Bean(destroyMethod = "", name = "dataSourceRepind")
    public DataSource dataSourceRepind() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.H2)
                .addScript("org/springframework/batch/core/schema-h2.sql")
                .addScript("classpath:schema-creation.sql")
                .addScript("classpath:MigrationKLSub-script.sql")
                .build();
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
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter());
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean(name = "persistenceUnitManagerRepind")
    @Primary
    public DefaultPersistenceUnitManager persistenceUnitManagerRepind() {
        final DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
        defaultPersistenceUnitManager.setDefaultDataSource(dataSourceRepind());
        defaultPersistenceUnitManager.setDefaultPersistenceUnitName("persistenceUnitManagerRepind");
        defaultPersistenceUnitManager.setPackagesToScan("com.airfrance.repind.entity");
        return defaultPersistenceUnitManager;
    }
}
