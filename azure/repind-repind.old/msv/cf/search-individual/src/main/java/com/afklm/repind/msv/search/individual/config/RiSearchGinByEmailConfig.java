package com.afklm.repind.msv.search.individual.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Config for RI Search Gin By Email MS
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "ri-search-gin-by-email")
@Data
public class RiSearchGinByEmailConfig {

    private String url;
}
