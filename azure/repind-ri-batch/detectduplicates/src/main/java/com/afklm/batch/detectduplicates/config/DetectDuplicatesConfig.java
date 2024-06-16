package com.afklm.batch.detectduplicates.config;

import com.afklm.batch.detectduplicates.BatchDetectDuplicates;
import com.airfrance.batch.common.config.*;
import com.airfrance.batch.common.exception.ConfigurationException;
import lombok.AllArgsConstructor;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackageClasses = {BatchDetectDuplicates.class})
@Import({ConfigBatch.class, JpaRepindConfig.class, JpaSicOdsConfig.class, JpaRepindUtf8Config.class, JpaPPConfig.class})
@PropertySource({"classpath:/detect-duplicates.properties"})
@EnableJpaRepositories(
        basePackages = "com.afklm.batch.detectduplicates",
        entityManagerFactoryRef = "entityManagerFactoryCustom",
        transactionManagerRef="platformTransactionManagerCustom")
@EntityScan(basePackages = "com.afklm.batch.detectduplicates.entity")
@AllArgsConstructor
public class DetectDuplicatesConfig {

    @Autowired
    @Qualifier("dataSourceSicOds")
    private DataSource dataSourceSicOds;
    private JpaVendorAdapter jpaAdapter;

    @Bean
    public JdbcTemplate jdbcTemplateSicOds() {
        return new JdbcTemplate(dataSourceSicOds);
    }

    @Bean("entityManagerFactoryCustom")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryCustom(JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSourceSicOds);
        bean.setJpaVendorAdapter(jpaVendorAdapter);
        bean.setPersistenceUnitManager(defaultPersistenceUnitManager());
        bean.setPackagesToScan("com.afklm.batch.detectduplicates.entity");
        bean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return bean;
    }

    @Bean("platformTransactionManagerCustom")
    public PlatformTransactionManager platformTransactionManagerCustom() throws ConfigurationException {
        EntityManagerFactory emf = entityManagerFactoryCustom(jpaAdapter).getObject();
        if(emf == null){
            throw new ConfigurationException("EntityManager is null");
        }
        return new JpaTransactionManager(emf);
    }

    @Bean("defaultPersistenceUnitManager")
    public DefaultPersistenceUnitManager defaultPersistenceUnitManager() {
        final DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
        defaultPersistenceUnitManager.setDefaultDataSource(dataSourceSicOds);
        defaultPersistenceUnitManager.setDefaultPersistenceUnitName("defaultPersistenceUnitManager");
        defaultPersistenceUnitManager.setPackagesToScan("com.afklm.batch.detectduplicates.entity");
        return defaultPersistenceUnitManager;
    }
}
