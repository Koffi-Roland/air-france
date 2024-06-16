package com.afklm.repind.msv.manage.external.identifier;

import com.afklm.repind.common.config.database.ConfigDatabaseScan;
import com.afklm.repind.common.config.documentation.ConfigDocumentationScan;
import com.afklm.repind.common.config.vault.ConfigVaultConfigurationScan;
import com.afklm.repind.common.controller.advice.ControllerAdviceScan;
import com.afklm.repind.common.controller.metric.MetricControllerScan;
import com.afklm.repind.common.entity.ConfigEntityScan;
import com.afklm.repind.common.entity.identifier.IdentifierEntityScan;
import com.afklm.repind.common.repository.identifier.IdentifierRepositoryScan;
import com.afklm.repind.common.service.vault.ServiceVaultScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Import(value = {
        ConfigDocumentationScan.class,
        ConfigDatabaseScan.class,
        MetricControllerScan.class,
        ControllerAdviceScan.class,
        ConfigVaultConfigurationScan.class,
        ServiceVaultScan.class
})
@EntityScan(basePackageClasses = {
        ConfigEntityScan.class
})
@EnableJpaRepositories(basePackageClasses = {
        IdentifierRepositoryScan.class,
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
