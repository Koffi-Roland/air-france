package com.afklm.repind.msv.provide.individual.score;

import com.afklm.repind.common.config.database.ConfigDatabaseScan;
import com.afklm.repind.common.config.documentation.ConfigDocumentationScan;
import com.afklm.repind.common.config.vault.ConfigVaultConfigurationScan;
import com.afklm.repind.common.controller.advice.ControllerAdviceScan;
import com.afklm.repind.common.controller.metric.MetricControllerScan;
import com.afklm.repind.common.entity.contact.ContactEntityScan;
import com.afklm.repind.common.entity.identifier.IdentifierEntityScan;
import com.afklm.repind.common.entity.individual.IndividualEntityScan;
import com.afklm.repind.common.entity.preferences.PreferencesEntityScan;
import com.afklm.repind.common.entity.profile.compliance.score.ProfileComplianceScoreScan;
import com.afklm.repind.common.entity.role.RoleEntityScan;
import com.afklm.repind.common.repository.contact.ContactRepositoryScan;
import com.afklm.repind.common.repository.identifier.IdentifierRepositoryScan;
import com.afklm.repind.common.repository.individual.IndividualRepositoryScan;
import com.afklm.repind.common.repository.preferences.PreferencesRepositoryScan;
import com.afklm.repind.common.repository.profileComplianceScore.ScoreRepositoryScan;
import com.afklm.repind.common.repository.role.RoleRepositoryScan;
import com.afklm.repind.common.service.format.ServiceFormatScan;
import com.afklm.repind.common.service.vault.ServiceVaultScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@Import(value = {
        ConfigDocumentationScan.class,
        MetricControllerScan.class,
        ControllerAdviceScan.class,
        ConfigVaultConfigurationScan.class,
        ConfigDatabaseScan.class,
        ServiceVaultScan.class,
        ServiceFormatScan.class
})
@EntityScan(basePackageClasses = {
        IdentifierEntityScan.class,
        RoleEntityScan.class,
        IndividualEntityScan.class,
        ContactEntityScan.class,
        ProfileComplianceScoreScan.class,
        PreferencesEntityScan.class
})
@EnableJpaRepositories(basePackageClasses = {
        RoleRepositoryScan.class,
        IndividualRepositoryScan.class,
        ContactRepositoryScan.class,
        IdentifierRepositoryScan.class,
        ScoreRepositoryScan.class,
        PreferencesRepositoryScan.class
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
