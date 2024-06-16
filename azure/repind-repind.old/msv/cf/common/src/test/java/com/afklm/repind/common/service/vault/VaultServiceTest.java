package com.afklm.repind.common.service.vault;

import com.afklm.repind.common.config.vault.VaultConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class VaultServiceTest {
    @Mock
    Environment env;
    @Mock
    VaultConfiguration vaultConfiguration;

    @Test
    void getByPath() {
        LinkedHashMap<String, String> dataOracle = new LinkedHashMap<>();
        dataOracle.put("username", "test");
        dataOracle.put("password", "passwordtest");
        VaultResponse vaultResponse = new VaultResponse();
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("data", dataOracle);
        vaultResponse.setData(data);

        when(this.env.getProperty("vault.namespace")).thenReturn("namespace");
        when(this.env.getProperty("vault.env")).thenReturn("dev");
        VaultTemplate vaultTemplate = Mockito.mock(VaultTemplate.class);
        Mockito.when(vaultTemplate.read("namespace/secrets/data/dev/oracle")).thenReturn(vaultResponse);
        Mockito.when(vaultConfiguration.createVaultTemplate()).thenReturn(vaultTemplate);
        VaultService vaultService = new VaultService(env, vaultConfiguration);
        Map<String, String> result = vaultService.getByPath("oracle");
        Assertions.assertEquals("test", result.get("username"));
        Assertions.assertEquals("passwordtest", result.get("password"));
    }

    @Test
    void errorGetByPath_notFound() {
        VaultResponse vaultResponse = new VaultResponse();
        vaultResponse.setData(null);

        when(this.env.getProperty("vault.namespace")).thenReturn("namespace");
        when(this.env.getProperty("vault.env")).thenReturn("dev");
        VaultTemplate vaultTemplate = Mockito.mock(VaultTemplate.class);
        Mockito.when(vaultTemplate.read("namespace/secrets/data/dev/oracle")).thenReturn(vaultResponse);
        Mockito.when(vaultConfiguration.createVaultTemplate()).thenReturn(vaultTemplate);
        VaultService vaultService = new VaultService(env, vaultConfiguration);
        Map<String, String> result = vaultService.getByPath("oracle");
        Assertions.assertTrue(result.isEmpty());
    }
}
