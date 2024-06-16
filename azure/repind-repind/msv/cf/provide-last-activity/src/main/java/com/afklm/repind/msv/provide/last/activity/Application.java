package com.afklm.repind.msv.provide.last.activity;

import com.afklm.repind.common.config.database.ConfigDatabaseScan;
import com.afklm.repind.common.config.documentation.ConfigDocumentationScan;
import com.afklm.repind.common.config.vault.ConfigVaultConfigurationScan;
import com.afklm.repind.common.controller.advice.ControllerAdviceScan;
import com.afklm.repind.common.controller.metric.MetricControllerScan;
import com.afklm.repind.common.service.vault.ServiceVaultScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {
        ConfigDocumentationScan.class,
        ConfigDatabaseScan.class,
        MetricControllerScan.class,
        ControllerAdviceScan.class,
        ConfigVaultConfigurationScan.class,
        ServiceVaultScan.class
})

/**
 * Application
 * A java file used to launch the entire spring project and scan the entire MS
 */
public class Application {
    /**
     * A main method launched at the launching of this file and used to launch the MS
     *
     * @param args command-line arguments passed to the program.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
