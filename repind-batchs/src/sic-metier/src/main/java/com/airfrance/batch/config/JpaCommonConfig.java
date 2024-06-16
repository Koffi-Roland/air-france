package com.airfrance.batch.config;

import com.airfrance.repind.config.WebConfig;
import com.airfrance.repind.config.WebConfigRepind;
import com.airfrance.repind.config.WebConfigRepindAndUtf8;
import com.airfrance.repind.config.vault.VaultConfigurationRepind;
import com.airfrance.repind.service.individu.internal.PurgeIndividualDS;
import com.airfrance.repindutf8.config.WebConfigUTF8;
import com.airfrance.repindutf8.config.vault.VaultConfigurationUTF8;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@ComponentScan(basePackages = {	"com.airfrance.repind" , "com.airfrance.repindutf8"} , excludeFilters={
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= WebConfigRepind.class),
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= WebConfigRepindAndUtf8.class),
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= WebConfig.class),
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= WebConfig.class),
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= WebConfigUTF8.class),
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= VaultConfigurationRepind.class),
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= PurgeIndividualDS.class),
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= VaultConfigurationUTF8.class)
})
@Configuration
public class JpaCommonConfig {

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
    @Bean(name="jpaDialect")
    public String jpaDialect() {
        return "org.hibernate.dialect.Oracle12cDialect";
    }

    public String getOracleDriver(){
        return "oracle.jdbc.driver.OracleDriver";
    }

}
