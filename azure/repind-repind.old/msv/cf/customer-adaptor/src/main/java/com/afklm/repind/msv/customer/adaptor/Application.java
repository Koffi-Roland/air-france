package com.afklm.repind.msv.customer.adaptor;


import com.afklm.repind.common.config.database.ConfigDatabaseScan;
import com.afklm.repind.common.config.documentation.ConfigDocumentationScan;
import com.afklm.repind.common.config.vault.ConfigVaultConfigurationScan;
import com.afklm.repind.common.controller.metric.MetricControllerScan;
import com.afklm.repind.common.entity.ConfigEntityScan;
import com.afklm.repind.common.repository.ConfigRepositoryScan;
import com.afklm.repind.common.service.vault.ServiceVaultScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@SpringBootApplication
@Import(value= {
        ConfigDocumentationScan.class,
        ConfigVaultConfigurationScan.class,
        MetricControllerScan.class,
        ConfigDatabaseScan.class,
        MetricControllerScan.class,
        ServiceVaultScan.class
})
@EntityScan(basePackageClasses = {
        ConfigEntityScan.class
})
@EnableJpaRepositories(basePackageClasses = {
        ConfigRepositoryScan.class
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}