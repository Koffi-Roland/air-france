package com.airfrance.batch.config.vault;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.AppRoleAuthentication;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.Properties;


@Slf4j
@Configuration()
public class VaultConfiguration extends AbstractVaultConfiguration {

    @Inject
    VaultProperties vaultProperties;


    @Override
    public ClientAuthentication clientAuthentication() {

        Properties vaultProperties = readVaultConfigFile();

        AppRoleAuthenticationOptions appRoleAuthenticationOptions = AppRoleAuthenticationOptions.builder()
                .roleId(AppRoleAuthenticationOptions.RoleId.provided((String) vaultProperties.get("role-id")))
                .secretId(AppRoleAuthenticationOptions.SecretId.provided((String) vaultProperties.get("secret-id")))
                .path("approle")
                .build();
        return new AppRoleAuthentication(appRoleAuthenticationOptions, restOperations());
    }

    @Override
    public VaultEndpoint vaultEndpoint() {
        String vaultUrl = vaultProperties.getVaultUrl();
        return VaultEndpoint.from(URI.create(vaultUrl + "v1/repind"));
    }

    private Properties readVaultConfigFile() {
        try {
            Properties prop = new Properties();
            File propertiesFile = new File(vaultProperties.getPathToAppRoles());
            prop.load(Files.newInputStream(propertiesFile.toPath()));
            return prop;
        } catch (IOException e) {
            log.error("Cannot locate approles.txt file", e);
            System.exit(-1);
            return null;
        }
    }

}
