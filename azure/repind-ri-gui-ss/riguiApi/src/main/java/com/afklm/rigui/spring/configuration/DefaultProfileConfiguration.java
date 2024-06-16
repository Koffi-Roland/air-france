package com.afklm.rigui.spring.configuration;

import javax.sql.DataSource;

import com.afklm.rigui.services.vault.VaultService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.URL;
import java.util.Map;


/**
 * A configuration for 'Default' profile.
 * 
 * @author DINB - TA
 */
@Configuration
@AllArgsConstructor
@Slf4j
@ConditionalOnProperty(
        value="vault.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class DefaultProfileConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultProfileConfiguration.class);


    private final Environment environment;
    private final VaultService vaultService;

    private static String TNS_PATH = "/TNS/";

    private static String TNS_ENV_VAR = "TNS_FILE_PATH_";
	/**
	 * Builds a JNDI datasource.
	 * @return the datasource.
	 */
	@Bean(destroyMethod="", name="ds_sic")
    public DataSource getDataSource1() {
        String tnsFilePath = getTnsFilePath();
        System.setProperty("oracle.net.tns_admin", tnsFilePath);
        System.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.Oracle12cDialect");
        Map<String, String> info = vaultService.getByPath("dataBase");
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(environment.getProperty("spring.datasource.sic.url"));
        dataSourceBuilder.username(info.get("sicUsername"));
        dataSourceBuilder.password(info.get("sicPassword"));
        return dataSourceBuilder.build();
    }

    private String getTnsFilePath() {
        String tnsFilePath = null;

        String tnsEnvDirectory = environment.getProperty("spring.datasource.tns_admin_env");
        LOGGER.info("Tns environment is: {}", tnsEnvDirectory);

        if (tnsEnvDirectory != null) {
            String tnsEnvironmentVariable = getTnsEnvironmentVariable(tnsEnvDirectory);
            LOGGER.info("Tns environment Variable is: {}", tnsEnvironmentVariable);

            String tnsNamePath = System.getenv(tnsEnvironmentVariable);
            LOGGER.info("the " + tnsEnvironmentVariable + " content is : {}", tnsNamePath);
            if(tnsNamePath != null && !"".equals(tnsNamePath)){
                LOGGER.info("URL of TNS file by environment variable: {}", tnsNamePath);
                tnsFilePath = tnsNamePath;
            }else{
                tnsFilePath = getClassPathTnsFile(tnsEnvDirectory);
            }
        }

        return tnsFilePath;
    }
    private String getTnsDirectoryFilePath(String tnsEnvDirectory) {
        return TNS_PATH+tnsEnvDirectory;
    }

    private String getTnsEnvironmentVariable(String tnsEnvDirectory) {
        return TNS_ENV_VAR+tnsEnvDirectory;
    }

    private String getClassPathTnsFile(String tnsEnvDirectory) {
        String tnsFilePathTmp = null;
        String tnsDirectoryfilePath = getTnsDirectoryFilePath(tnsEnvDirectory);
        LOGGER.info("TNS Directory file path is: {}", tnsDirectoryfilePath);

        URL url = getClass().getResource(tnsDirectoryfilePath);
        tnsFilePathTmp = url.getPath();
        LOGGER.info("URL of TNS file by resource variable: {}", tnsFilePathTmp);

        return tnsFilePathTmp;
    }
}
