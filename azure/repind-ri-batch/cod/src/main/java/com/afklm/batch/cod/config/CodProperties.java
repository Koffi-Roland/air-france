package com.afklm.batch.cod.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:/cod.properties","classpath:/local.properties"})
public class CodProperties {

    @Value("${oracle.sic.url}")
    private String OracleSicUrl;

    @Value("${oracle.sic.tns}")
    private String OracleSicTNS;

    @Value("${neo4j.repind.url}")
    private String neo4jRepindUrl;

    public String getOracleSicUrl() {
        return OracleSicUrl;
    }

    public void setOracleSicUrl(String oracleSicUrl) {
        OracleSicUrl = oracleSicUrl;
    }

    public String getNeo4jRepindUrl() {
        return neo4jRepindUrl;
    }

    public void setNeo4jRepindUrl(String neo4jRepindUrl) {
        this.neo4jRepindUrl = neo4jRepindUrl;
    }

    public String getOracleSicTNS() {
        return OracleSicTNS;
    }

    public void setOracleSicTNS(String oracleSicTNS) {
        OracleSicTNS = oracleSicTNS;
    }
}
