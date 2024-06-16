package com.afklm.cati.common.vault;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@ConditionalOnProperty(
        value="vault.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class VaultService {

    private final Environment environment;
    private final VaultConfiguration vaultConfiguration;

    private final String vaultNamespace;
    private final String vaultEnv;


    public VaultService(Environment env, VaultConfiguration vaultConfiguration) {
        this.environment = env;
        this.vaultConfiguration = vaultConfiguration;
        vaultNamespace = environment.getProperty("vault.namespace");
        vaultEnv = environment.getProperty("vault.env");
    }


    public Map<String, String> getByPath(String path){
        VaultTemplate vaultTemplate = vaultConfiguration.createVaultTemplate();
        Map<String, Object> data = vaultTemplate.read(String.format("%s/secrets/data/%s/%s", vaultNamespace, vaultEnv, path)).getData();
        if(data == null){
            return new HashMap<>();
        }
        return (LinkedHashMap<String, String>) data.get("data");
    }
}
