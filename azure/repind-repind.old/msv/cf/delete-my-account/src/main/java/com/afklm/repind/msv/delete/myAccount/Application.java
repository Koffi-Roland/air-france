package com.afklm.repind.msv.delete.myAccount;

import com.afklm.repind.common.config.database.ConfigDatabaseScan;
import com.afklm.repind.common.config.documentation.ConfigDocumentationScan;
import com.afklm.repind.common.config.vault.ConfigVaultConfigurationScan;
import com.afklm.repind.common.controller.advice.ControllerAdviceScan;
import com.afklm.repind.common.controller.metric.MetricControllerScan;
import com.afklm.repind.common.entity.identifier.IdentifierEntityScan;
import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.common.entity.role.RoleEntityScan;
import com.afklm.repind.common.repository.identifier.IdentifierRepositoryScan;
import com.afklm.repind.common.repository.role.RoleRepositoryScan;
import com.afklm.repind.common.service.vault.ServiceVaultScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@Import(value= {
        ConfigDocumentationScan.class,
        MetricControllerScan.class,
        ControllerAdviceScan.class,
        ConfigVaultConfigurationScan.class,
        ConfigDatabaseScan.class,
        ServiceVaultScan.class
})
@EntityScan(basePackageClasses = {
        IdentifierEntityScan.class,
        RoleEntityScan.class,
})
@EnableJpaRepositories(basePackageClasses = {
        IdentifierRepositoryScan.class,
        RoleRepositoryScan.class
})
@ComponentScan(
        basePackages = {
                "com.afklm.repind.common.service",
                "com.afklm.repind.common.repository"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}