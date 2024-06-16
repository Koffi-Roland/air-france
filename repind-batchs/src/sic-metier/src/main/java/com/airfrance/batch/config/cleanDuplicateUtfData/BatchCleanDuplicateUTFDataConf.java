package com.airfrance.batch.config.cleanDuplicateUtfData;

import com.airfrance.batch.cleanDuplicateUTFData.config.CleanDuplicateUTFDataConfProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages = {"com.airfrance.batch.cleanDuplicateUTFData"})
@Configuration
@Import({ CleanDuplicateUTFDataConfProperties.class })

public class BatchCleanDuplicateUTFDataConf {
}
