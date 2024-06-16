package com.afklm.repind.msv.delete.myAccount.config;

import com.afklm.repind.common.config.database.ConfigDatabaseScan;
import com.afklm.repind.common.config.documentation.ConfigDocumentationScan;
import com.afklm.repind.common.config.vault.ConfigVaultConfigurationScan;
import com.afklm.repind.common.controller.advice.ControllerAdviceScan;
import com.afklm.repind.common.controller.metric.MetricControllerScan;
import com.afklm.repind.common.entity.ConfigEntityScan;
import com.afklm.repind.common.repository.identifier.IdentifierRepositoryScan;
import com.afklm.repind.common.repository.preferences.PreferenceDataRepository;
import com.afklm.repind.common.repository.preferences.PreferenceRepository;
import com.afklm.repind.common.repository.role.RoleRepositoryScan;
import com.afklm.repind.common.service.format.ServiceFormatScan;
import com.afklm.repind.common.service.vault.ServiceVaultScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
@Import(value = {
        ConfigDocumentationScan.class,
        ConfigVaultConfigurationScan.class,
        ConfigDatabaseScan.class,
        MetricControllerScan.class,
        ControllerAdviceScan.class,
        ServiceFormatScan.class,
        com.afklm.repind.common.config.documentation.CommonConfig.class,
        ServiceVaultScan.class,
})
@EntityScan(basePackageClasses = {
        ConfigEntityScan.class
})
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactorySic",
        transactionManagerRef = "transactionManagerSic",
        basePackages = "com.afklm.repind.common.repository")
public class DeleteMyAccountCommonConfig {

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
    @Primary
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        final HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
        jpaAdapter.setGenerateDdl(false);
        jpaAdapter.setShowSql(false);
        jpaAdapter.setDatabasePlatform("org.hibernate.dialect.OracleDialect");

        return jpaAdapter;
    }
}
