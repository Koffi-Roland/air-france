package com.afklm.batch.cleanContactData.config;


import com.airfrance.batch.common.config.vault.VaultConfiguration;
import com.airfrance.batch.common.config.vault.VaultProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.env.VaultPropertySource;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties
@PropertySource({"classpath:/jpa-sic-config.properties","classpath:/local.properties"})
@Import({VaultConfiguration.class,
        VaultProperties.class})
public class CleanContactDataConfProperties {
    @Inject
    private VaultTemplate vaultTemplate;

    @Inject
    VaultProperties vaultProperties;
    @Inject
    private ConfigurableEnvironment environment;
    @Value("${sic.db.url.sic}")
    private String OracleSicUrlSic;

    @Value("${sic.db.tns.sic}")
    private String OracleSicTNSSic;

    @PostConstruct
    public void init() {
        environment.getPropertySources().addFirst(
                new VaultPropertySource(vaultTemplate, "secrets/" + vaultProperties.getVaultEnv() + "/dataBase")
        );
    }
}

