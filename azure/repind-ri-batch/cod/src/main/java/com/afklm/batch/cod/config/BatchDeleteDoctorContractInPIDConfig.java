package com.afklm.batch.cod.config;


import com.airfrance.batch.common.config.vault.VaultConfiguration;
import com.airfrance.batch.common.config.vault.VaultProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.env.VaultPropertySource;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@ComponentScan(basePackages = {"com.afklm.batch.cod"})
@Configuration
@EnableConfigurationProperties
@Import({VaultConfiguration.class,
        VaultProperties.class,
        CodProperties.class
        })
public class BatchDeleteDoctorContractInPIDConfig {
    @Inject
    private VaultTemplate vaultTemplate;

    @Inject
    VaultProperties vaultProperties;
    @Inject
    private ConfigurableEnvironment environment;

    @PostConstruct
    public void init() {
        environment.getPropertySources().addFirst(
                new VaultPropertySource(vaultTemplate, "secrets/" + vaultProperties.getVaultEnv() + "/dataBase")
        );
    }
}
