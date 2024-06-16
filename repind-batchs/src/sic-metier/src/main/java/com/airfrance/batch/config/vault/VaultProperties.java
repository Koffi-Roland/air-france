package com.airfrance.batch.config.vault;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "vault")
@PropertySource({"classpath:/vault.properties", "classpath:/local.properties"})
public class VaultProperties {
    private String vaultEnv;
    private String vaultUrl;

    @Value("${vault.approle.file.path}")
    private String pathToAppRoles;


    public String getVaultEnv() {
        return vaultEnv;
    }

    public void setVaultEnv(String vaultEnv) {
        this.vaultEnv = vaultEnv;
    }

    public String getVaultUrl() {
        return vaultUrl;
    }

    public void setVaultUrl(String vaultUrl) {
        this.vaultUrl = vaultUrl;
    }

    public String getPathToAppRoles() {
        return pathToAppRoles;
    }

    public void setPathToAppRoles(String pathToAppRoles) {
        this.pathToAppRoles = pathToAppRoles;
    }
}
