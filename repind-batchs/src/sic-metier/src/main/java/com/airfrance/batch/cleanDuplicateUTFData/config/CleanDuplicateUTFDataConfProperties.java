package com.airfrance.batch.cleanDuplicateUTFData.config;


import com.airfrance.batch.config.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties
@PropertySource({"classpath:/jpa-sic-utf8-config.properties","classpath:/local.properties"})
@Import({ConfigBatch.class, JpaRepindConfig.class, JpaSicOdsConfig.class, JpaRepindUtf8Config.class, JpaPPConfig.class})
public class CleanDuplicateUTFDataConfProperties {
}
