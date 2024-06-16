package com.afklm.batch.invalidation.email.config;

import com.airfrance.batch.common.config.JpaPPConfig;
import com.airfrance.batch.common.config.JpaRepindConfig;
import com.airfrance.batch.common.config.JpaRepindUtf8Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages = {	"com.afklm.batch.invalidation"})
@Configuration
@EnableConfigurationProperties
@Import({JpaRepindConfig.class, JpaRepindUtf8Config.class , JpaPPConfig.class})
public class BatchInvalidationConfig {

}
