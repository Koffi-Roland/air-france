package com.afklm.repind.common.config.vault;

import com.afklm.repind.common.exception.ConfigurationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class VaultConfigurationTest {
    @Mock
    Environment env;

    @Test
    void vaultEndpoint() {
        when(this.env.getProperty("vault.uri")).thenReturn("https://vault-tls-cae.airfranceklm.com");
        VaultConfiguration vaultConfig = new VaultConfiguration(env);
        Assertions.assertEquals("vault-tls-cae.airfranceklm.com", vaultConfig.vaultEndpoint().getHost());
    }

    @Test
    void clientAuthentication() {
        when(this.env.getProperty("vault.uri")).thenReturn("https://vault-tls-cae.airfranceklm.com");
        when(this.env.getProperty("vault.credentials.role_id")).thenReturn("roleid");
        when(this.env.getProperty("vault.credentials.secret_id")).thenReturn("secret");
        when(this.env.getProperty("vault.namespace")).thenReturn("namespace");
        VaultConfiguration vaultConfig = new VaultConfiguration(env);
        Assertions.assertNotNull(vaultConfig.clientAuthentication());
    }

    @Test
    void errorClientAuthentication_roleIdNull() {
        when(this.env.getProperty("vault.credentials.role_id")).thenReturn(null);
        when(this.env.getProperty("vault.credentials.secret_id")).thenReturn("secret");
        when(this.env.getProperty("vault.namespace")).thenReturn("namespace");
        VaultConfiguration vaultConfig = new VaultConfiguration(env);
        Throwable exception = assertThrows(ConfigurationException.class, () -> vaultConfig.clientAuthentication());
        assertEquals("VaultService: the role_id or the secret_id is null", exception.getMessage());
    }

    @Test
    void errorClientAuthentication_secretNull() {
        when(this.env.getProperty("vault.credentials.role_id")).thenReturn("roleId");
        when(this.env.getProperty("vault.credentials.secret_id")).thenReturn(null);
        when(this.env.getProperty("vault.namespace")).thenReturn("namespace");
        VaultConfiguration vaultConfig = new VaultConfiguration(env);
        Throwable exception = assertThrows(ConfigurationException.class, () -> vaultConfig.clientAuthentication());
        assertEquals("VaultService: the role_id or the secret_id is null", exception.getMessage());
    }

    @Test
    void restOperations() {
        when(this.env.getProperty("vault.uri")).thenReturn("https://vault-tls-cae.airfranceklm.com");
        when(this.env.getProperty("vault.credentials.role_id")).thenReturn("roleid");
        when(this.env.getProperty("vault.credentials.secret_id")).thenReturn("secret");
        when(this.env.getProperty("vault.namespace")).thenReturn("namespace");
        VaultConfiguration vaultConfig = new VaultConfiguration(env);
        Assertions.assertNotNull(vaultConfig.restOperations());
    }
}
