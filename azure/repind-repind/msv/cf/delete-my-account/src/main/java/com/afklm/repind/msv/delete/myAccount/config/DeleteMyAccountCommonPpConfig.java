package com.afklm.repind.msv.delete.myAccount.config;

import com.afklm.repind.commonpp.config.database.ConfigDatabasePpScan;
import com.afklm.repind.commonpp.config.vault.ConfigVaultConfigurationPpScan;
import com.afklm.repind.commonpp.entity.paymentDetails.PaymentDetailsEntity;
import com.afklm.repind.commonpp.entity.paymentDetails.PaymentDetailsEntityScan;
import com.afklm.repind.commonpp.service.vault.ServiceVaultPpScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;

@Configuration
@Import(value = {
        ConfigVaultConfigurationPpScan.class,
        ConfigDatabasePpScan.class,
        ServiceVaultPpScan.class
})
@EntityScan(basePackageClasses = {
        PaymentDetailsEntityScan.class
})
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryRepind",
        transactionManagerRef = "transactionManagerRepind",
        basePackages = "com.afklm.repind.commonpp.repository")
public class DeleteMyAccountCommonPpConfig {

    @Autowired
    @Qualifier("DataSourcePp")
    private DataSource dataSourcePp;

    @Bean(name = "transactionManagerRepind")
    public JpaTransactionManager transactionManagerRepind() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory().getObject());
        return tm;
    }

    @Bean(name = "entityManagerFactoryRepind")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSourcePp);
        emf.setJpaVendorAdapter(jpaVendorAdapterPp());
        emf.setPackagesToScan("com.afklm.repind.commonpp.entity.*");
        emf.setPersistenceUnitName("entityManagerFactoryRepind");
        emf.afterPropertiesSet();
        return emf;
    }

    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapterPp() {
        final HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
        jpaAdapter.setGenerateDdl(false);
        jpaAdapter.setShowSql(false);
        jpaAdapter.setDatabasePlatform("org.hibernate.dialect.OracleDialect");
        return jpaAdapter;
    }
}
