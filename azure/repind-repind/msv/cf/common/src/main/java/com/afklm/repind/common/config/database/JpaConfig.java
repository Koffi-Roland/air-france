package com.afklm.repind.common.config.database;

import com.afklm.repind.common.service.vault.VaultService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

@Configuration
@AllArgsConstructor
@Slf4j
@ConditionalOnProperty(
        value = "vault.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class JpaConfig {

    private final Environment environment;
    private final VaultService vaultService;

    private static String TNS_PATH = "/TNS/";

    private static String TNS_ENV_VAR = "TNS_FILE_PATH_";

    @Bean
    @Primary
    public DataSource getDataSource() throws URISyntaxException, MalformedURLException {
        //This line allow the entity to use keyword of hibernate for column KEY AND VALUE of table EXTERNAL_IDENTIFIER_DATA
        System.setProperty("hibernate.globally_quoted_identifiers_skip_column_definitions", "true");

        String tnsFilePath = getTnsFilePath();
        System.setProperty("oracle.net.tns_admin", tnsFilePath);
        System.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.OracleDialect");
        Map<String, String> info = vaultService.getByPath("oracle");

        HikariConfig hkConfig = new HikariConfig();
        HikariDataSource hkDatasource;

        hkConfig.setJdbcUrl(environment.getProperty("spring.datasource.url"));
        hkConfig.setDriverClassName("oracle.jdbc.OracleDriver");
        hkConfig.setUsername(info.get("username"));
        hkConfig.setPassword(info.get("password"));

        hkDatasource = new HikariDataSource(hkConfig);
        hkDatasource.setMaximumPoolSize(Integer.parseInt(environment.getProperty("spring.datasource.maxPoolSize")));

        return hkDatasource;
    }

    private String getTnsFilePath() {
        String tnsFilePath = null;

        String tnsEnvDirectory = environment.getProperty("spring.datasource.tns_admin_env");
        log.info("Tns environment is: {}", tnsEnvDirectory);

        if (tnsEnvDirectory != null) {
            String tnsEnvironmentVariable = getTnsEnvironmentVariable(tnsEnvDirectory);
            log.info("Tns environment Variable is: {}", tnsEnvironmentVariable);

            String tnsNamePath = System.getenv(tnsEnvironmentVariable);
            log.info("the " + tnsEnvironmentVariable + " content is : {}", tnsNamePath);
            if (tnsNamePath != null && !"".equals(tnsNamePath)) {
                log.info("URL of TNS file by environment variable: {}", tnsNamePath);
                tnsFilePath = tnsNamePath;
            } else {
                tnsFilePath = getClassPathTnsFile(tnsEnvDirectory);
            }
        }

        return tnsFilePath;
    }

    private String getTnsDirectoryFilePath(String tnsEnvDirectory) {
        return TNS_PATH + tnsEnvDirectory;
    }

    private String getTnsEnvironmentVariable(String tnsEnvDirectory) {
        return TNS_ENV_VAR + tnsEnvDirectory;
    }

    private String getClassPathTnsFile(String tnsEnvDirectory) {
        String tnsFilePathTmp = null;
        String tnsDirectoryfilePath = getTnsDirectoryFilePath(tnsEnvDirectory);
        log.info("TNS Directory file path is: {}", tnsDirectoryfilePath);

        URL url = getClass().getResource(tnsDirectoryfilePath);
        tnsFilePathTmp = url.getPath();
        log.info("URL of TNS file by resource variable: {}", tnsFilePathTmp);

        return tnsFilePathTmp;
    }

}
