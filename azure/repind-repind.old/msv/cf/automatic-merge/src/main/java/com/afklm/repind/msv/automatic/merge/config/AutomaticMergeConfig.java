package com.afklm.repind.msv.automatic.merge.config;

import com.afklm.repind.common.config.database.ConfigDatabaseScan;
import com.afklm.repind.common.config.documentation.ConfigDocumentationScan;
import com.afklm.repind.common.config.vault.ConfigVaultConfigurationScan;
import com.afklm.repind.common.controller.advice.ControllerAdviceScan;
import com.afklm.repind.common.controller.metric.MetricControllerScan;
import com.afklm.repind.common.service.vault.ServiceVaultScan;
import com.afklm.soa.autoconfigure.annotation.SdfwHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
@ComponentScan(
        basePackages = {
                "com.afklm.repind.common.service"})
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactorySic",
        transactionManagerRef = "transactionManagerSic",
        basePackages = "com.afklm.repind.common.repository")
@EntityScan("com.afklm.repind.common.entity.*")
@Import(value= {
        ConfigDocumentationScan.class,
        MetricControllerScan.class,
        ControllerAdviceScan.class,
        ConfigVaultConfigurationScan.class,
        ConfigDatabaseScan.class,
        ServiceVaultScan.class,
})
@SdfwHandler(targetWS = "w002122-v01")
public class AutomaticMergeConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment env;

    @Primary
    @Bean(name = "transactionManagerSic")
    public JpaTransactionManager transactionManagerSic() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory().getObject());
        return tm;
    }

    @Primary
    @Bean(name = "entityManagerFactorySic")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapter());
        emf.setPackagesToScan("com.afklm.repind.common.entity.*");
        emf.setPersistenceUnitName("entityManagerFactorySic");
        emf.afterPropertiesSet();
        return emf;
    }

    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        final HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
        jpaAdapter.setGenerateDdl(false);
        jpaAdapter.setShowSql(false);
        jpaAdapter.setDatabasePlatform("org.hibernate.dialect.OracleDialect");

        return jpaAdapter;
    }
}
