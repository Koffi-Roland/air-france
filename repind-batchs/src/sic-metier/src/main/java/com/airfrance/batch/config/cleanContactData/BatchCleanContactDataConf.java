package com.airfrance.batch.config.cleanContactData;


import com.airfrance.batch.cleanContactData.conf.CleanContactDataConfProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages= {"com.airfrance.batch.cleanContactData"})
@Configuration
@Import({CleanContactDataConfProperties.class})

public class BatchCleanContactDataConf {
}
