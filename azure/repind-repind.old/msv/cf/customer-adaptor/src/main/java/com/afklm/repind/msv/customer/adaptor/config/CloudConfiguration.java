package com.afklm.repind.msv.customer.adaptor.config;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.io.*;
import java.util.Base64;

@Configuration
@Profile({"dev", "rct", "acp", "prd"})
public class CloudConfiguration {

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(CloudConfiguration.class);

    @Value("${az-vault.namespace}")
    private String keyVaultName;
    @Value("${az-vault.secrets.file_path}")
    private String tempFilePath;
    @Value("${az-vault.secrets.jass.file_name}")
    private String jassFileName ;
    @Value("${az-vault.secrets.jass.secretName}")
    private String jaasConfigSecretName;
    @Value("${az-vault.secrets.keytab.file_name}")
    private String keyTabFileName;
    @Value("${az-vault.secrets.keytab.secretName}")
    private String keytabSecretName;
    @Primary
    @Bean(name = "jaas_config_keytab_base64")
    public void decryptJaasConfigKeytabBase64() throws IOException{
        LOGGER.info("[Start] - Decrypting jaas config - keytab files in Base64");
        String jaasConfigBase64Str = getKeyVaultSecretValue(jaasConfigSecretName);
        if(jaasConfigBase64Str != null) decryptBase64(tempFilePath, jassFileName, jaasConfigBase64Str);
        else  LOGGER.error("Decrypting jaas config file in Base64 failed");

        String keytabBase64Str = getKeyVaultSecretValue(keytabSecretName);
        if(keytabBase64Str != null)decryptBase64(tempFilePath, keyTabFileName, keytabBase64Str);
        else  LOGGER.error("Decrypting keytab file in Base64 failed");

        System.setProperty("java.security.auth.login.config", tempFilePath+"/"+jassFileName);
        LOGGER.info("[END] - Decrypting jaas config - keytab files in Base64");
    }

    public void decryptBase64(String filePath, String fileName, String base64Str) throws IOException {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath, fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            byte[] bfile = Base64.getDecoder().decode(base64Str);
            bos.write(bfile);
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public String getKeyVaultSecretValue(String secretName) throws IllegalArgumentException {
        String keyVaultUri = "https://" + keyVaultName + ".vault.azure.net";

        LOGGER.info("key vault name = {} and key vault URI = {} \n", keyVaultName, keyVaultUri);

        SecretClient secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUri)
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();

        KeyVaultSecret retrievedSecret = secretClient.getSecret(secretName);

        String secretValue = retrievedSecret.getValue();

        LOGGER.info("Retrieving your secret from {} .", keyVaultName);

        LOGGER.info("Your secret's value is '{}'.", secretValue);
        return secretValue;
    }
}