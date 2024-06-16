package com.afklm.batch.cleanDuplicateUTFData.config;


import com.airfrance.batch.common.config.vault.VaultConfiguration;
import com.airfrance.batch.common.config.vault.VaultProperties;
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

@Configuration
@EnableConfigurationProperties
@PropertySource({"classpath:/jpa-sic-utf8-config.properties","classpath:/local.properties"})
@Import({VaultConfiguration.class,
        VaultProperties.class})
public class CleanDuplicateUTFDataConfProperties {

    @Inject
    private VaultTemplate vaultTemplate;

    @Inject
    VaultProperties vaultProperties;
    @Inject
    private ConfigurableEnvironment environment;
    @Value("${sic.db.url.utf8}")
    private String OracleSicUrlUtf8;

    @Value("${sic.db.tns.utf8}")
    private String OracleSicTNSUtf8;

    public String getOracleSicUrlUtf8() {
        return OracleSicUrlUtf8;
    }

    public void setOracleSicUrlUtf8(String oracleSicUrlUtf8) {
        OracleSicUrlUtf8 = oracleSicUrlUtf8;
    }

    @PostConstruct
    public void init() {
        environment.getPropertySources().addFirst(
                new VaultPropertySource(vaultTemplate, "secrets/" + vaultProperties.getVaultEnv() + "/dataBase")
        );
    }

    public String getOracleSicTNSUtf8() {
        return OracleSicTNSUtf8;
    }

    public void setOracleSicTNSUtf8(String oracleSicTNSUtf8) {
        this.OracleSicTNSUtf8 = oracleSicTNSUtf8;
    }
}
