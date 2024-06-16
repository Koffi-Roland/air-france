package com.afklm.batch.cleanContactData.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages= {"com.afklm.batch.cleanContactData"})
@Configuration
@Import({CleanContactDataConfProperties.class})

public class BatchCleanContactDataConf {
}
