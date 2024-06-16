package com.airfrance.repind.config.vault;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@ConfigurationProperties(prefix = "vault")
@PropertySource({"classpath:/vault.properties"})
public class VaultPropertiesRepind {
    @Value("${vault.vaultEnv}")
    private String vaultEnv;

    @Value("${vault.vaultUrl}")
    private String vaultUrl;

    @Value("${vault.approle.file.path}")
    private String pathToAppRoles;

    @Value("${vault.secret.path.datasource}")
    private String pathToSecrets;

    @Value("${vault.namespace}")
    private String namespace;
}
