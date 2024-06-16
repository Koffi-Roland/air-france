package com.airfrance.jraf.batch.config;

import com.airfrance.jraf.batch.config.vault.VaultConfiguration;
import com.airfrance.jraf.batch.config.vault.VaultProperties;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.env.VaultPropertySource;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Configuration
@PropertySource({"classpath:/web-config.properties"})
@Import({VaultConfiguration.class,
        VaultProperties.class})
public class Neo4jProperties {

    @Inject
    private VaultTemplate vaultTemplate;

    @Inject
    private VaultProperties vaultProperties;
    @Inject
    private ConfigurableEnvironment environment;

    @Value("${neo4j.repind.url}")
    private String neo4jRepindUrl;


    @Bean(destroyMethod = "", name = "Neo4jProperties")
    public Driver dataSourceNeo4j() {
        final Driver neo4jDriver = GraphDatabase.driver(
                neo4jRepindUrl,
                AuthTokens.basic(environment.getProperty("neo4jCodUsername"), environment.getProperty("neo4jCodPassword")));
        return neo4jDriver;
    }

    @PostConstruct
    public void init() {
        environment.getPropertySources().addFirst(
                new VaultPropertySource(vaultTemplate, "secrets/" + vaultProperties.getVaultEnv() + "/dataBase")
        );
    }

}
