package com.afklm.batch.cleanDuplicateUTFData.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages = {"com.afklm.batch.cleanDuplicateUTFData"})
@Configuration
@Import({ CleanDuplicateUTFDataConfProperties.class })

public class BatchCleanDuplicateUTFDataConf {
}
