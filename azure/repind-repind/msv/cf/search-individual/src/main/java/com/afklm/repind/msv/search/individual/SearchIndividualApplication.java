package com.afklm.repind.msv.search.individual;

import com.afklm.repind.common.config.documentation.ConfigDocumentationScan;
import com.afklm.repind.common.controller.advice.ControllerAdviceScan;
import com.afklm.repind.common.controller.metric.MetricControllerScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Main class of RI Search Individual
 */
@SpringBootApplication
@Import(value= {
		ConfigDocumentationScan.class,
		MetricControllerScan.class,
		ControllerAdviceScan.class
})
public class SearchIndividualApplication {

    /**
     * Main method
     * @param args java args
     */
	public static void main(String[] args) {
		SpringApplication.run(SearchIndividualApplication.class, args);
	}
}
