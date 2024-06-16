package com.afklm.repind.msv.inferred;

import com.afklm.repind.common.config.database.ConfigDatabaseScan;
import com.afklm.repind.common.config.documentation.ConfigDocumentationScan;
import com.afklm.repind.common.config.vault.ConfigVaultConfigurationScan;
import com.afklm.repind.common.controller.metric.MetricControllerScan;
import com.afklm.repind.common.service.vault.ServiceVaultScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import(value= {
        ConfigDocumentationScan.class,
        ConfigVaultConfigurationScan.class,
        ConfigDatabaseScan.class,
        MetricControllerScan.class,
        ServiceVaultScan.class
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}