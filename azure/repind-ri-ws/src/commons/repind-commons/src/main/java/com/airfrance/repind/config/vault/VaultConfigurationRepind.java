package com.airfrance.repind.config.vault;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.vault.authentication.AppRoleAuthentication;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
@Slf4j
@Configuration()
public class VaultConfigurationRepind extends AbstractVaultConfiguration {

    @Inject
    VaultPropertiesRepind vaultProperties;

    @Autowired
    private ConfigurableEnvironment environment;

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

    @Override
    public ClientAuthentication clientAuthentication() {

        Properties properties = readVaultConfigFile();

        AppRoleAuthenticationOptions appRoleAuthenticationOptions = AppRoleAuthenticationOptions.builder()
                .roleId(AppRoleAuthenticationOptions.RoleId.provided((String) properties.get("role-id")))
                .secretId(AppRoleAuthenticationOptions.SecretId.provided((String) properties.get("secret-id")))
                .path("approle")
                .build();
        return new AppRoleAuthentication(appRoleAuthenticationOptions, restOperations());
    }

    @Override
    public VaultEndpoint vaultEndpoint() {
        String vaultUrl = vaultProperties.getVaultUrl();
        return VaultEndpoint.from(URI.create(vaultUrl));
    }

    @PostConstruct
    public void init() {
        environment.getPropertySources().addFirst(new PropertySource<Object>("vaultPropertySource") {
            private Map<String, String> properties;

            @Override
            public Object getProperty(String name) {
                if (properties == null) {
                    Map<String, Object> response = Objects.requireNonNull(vaultTemplate().read(vaultProperties.getPathToSecrets())).getData();
                    properties = (Map<String, String>) response.get("data");
                }
                return properties.get(name);
            }
        });

    }

}
