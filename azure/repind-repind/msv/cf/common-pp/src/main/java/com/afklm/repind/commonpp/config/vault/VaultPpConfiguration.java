package com.afklm.repind.commonpp.config.vault;

import com.afklm.repind.commonpp.exception.ConfigurationException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.vault.authentication.AppRoleAuthentication;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.client.VaultClients;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@Primary
@ConditionalOnProperty(
        value="vault.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class VaultPpConfiguration extends AbstractVaultConfiguration {

    private final Environment environment;

    private final String vaultUri;
    private final String vaultNamespace;


    public VaultPpConfiguration(Environment env) {
        this.environment = env;
        vaultUri = environment.getProperty("vault.uri");
        vaultNamespace = environment.getProperty("vault.namespace");
    }

    @Override
    public VaultEndpoint vaultEndpoint() {
        return VaultEndpoint.from(URI.create(vaultUri));
    }

    @Override
    public ClientAuthentication clientAuthentication() {
        String roleId = environment.getProperty("vault.credentials.role_id");
        String secretId = environment.getProperty("vault.credentials.secret_id");

        if(roleId == null || secretId == null){
            throw new ConfigurationException("VaultService: the role_id or the secret_id is null");
        }

        AppRoleAuthenticationOptions.AppRoleAuthenticationOptionsBuilder builder = AppRoleAuthenticationOptions
                .builder()
                .roleId(AppRoleAuthenticationOptions.RoleId.provided(roleId))
                .secretId(AppRoleAuthenticationOptions.SecretId.provided(secretId));
        return new AppRoleAuthentication(builder.build(), restOperations());
    }

    @Override
    public RestOperations restOperations() {
        RestTemplate restTemplate = VaultClients.createRestTemplate(vaultEndpointProvider(),
                clientHttpRequestFactoryWrapper().getClientHttpRequestFactory());

        restTemplate.getInterceptors().add(VaultClients.createNamespaceInterceptor(vaultNamespace));
        return restTemplate;
    }

    public VaultTemplate createVaultTemplate() {
        AppRoleAuthentication appRole = (AppRoleAuthentication) this.clientAuthentication();
        VaultEndpoint endpoint = this.vaultEndpoint();
        return new VaultTemplate(endpoint,appRole);
    }
}
