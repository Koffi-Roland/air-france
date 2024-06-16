package com.airfrance.batch.updateMarketLanguage.config;

import com.airfrance.batch.config.JpaPPConfig;
import com.airfrance.batch.config.JpaRepindConfig;
import com.airfrance.batch.config.JpaRepindUtf8Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties
@Configuration
@ComponentScan(basePackages = {	"com.airfrance.batch.updateMarketLanguage"})
@Import({JpaRepindConfig.class, JpaRepindUtf8Config.class , JpaPPConfig.class})
public class UpdateMarketLanguageConfig {

}