package com.afklm.batch.updateMarketLanguage.config;


import com.airfrance.batch.common.config.JpaPPConfig;
import com.airfrance.batch.common.config.JpaRepindConfig;
import com.airfrance.batch.common.config.JpaRepindUtf8Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties
@Configuration
@ComponentScan(basePackages = {	"com.afklm.batch.updateMarketLanguage"})
@Import({JpaRepindConfig.class, JpaRepindUtf8Config.class , JpaPPConfig.class})
public class UpdateMarketLanguageConfig {

}
