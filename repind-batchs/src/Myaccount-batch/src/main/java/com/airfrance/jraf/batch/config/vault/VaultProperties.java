package com.airfrance.jraf.batch.config.vault;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Getter
@ConfigurationProperties(prefix = "vault")
@PropertySource({"classpath:/vault.properties", "classpath:/local.properties"})
public class VaultProperties {
    private String vaultEnv;
    private String vaultUrl;

    @Value("${vault.approle.file.path}")
    private String pathToAppRoles;


    public void setVaultEnv(String vaultEnv) {
        this.vaultEnv = vaultEnv;
    }

    public void setVaultUrl(String vaultUrl) {
        this.vaultUrl = vaultUrl;
    }

    public void setPathToAppRoles(String pathToAppRoles) {
        this.pathToAppRoles = pathToAppRoles;
    }
}
