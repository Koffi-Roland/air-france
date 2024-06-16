package com.airfrance.batch.cleanContactData.conf;


import com.airfrance.batch.config.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties
@PropertySource({"classpath:/jpa-sic-config.properties","classpath:/local.properties"})
@Import({ConfigBatch.class, JpaRepindConfig.class, JpaSicOdsConfig.class, JpaRepindUtf8Config.class, JpaPPConfig.class})
public class CleanContactDataConfProperties {

}