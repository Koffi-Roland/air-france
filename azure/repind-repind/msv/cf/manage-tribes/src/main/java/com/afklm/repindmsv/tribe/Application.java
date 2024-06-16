package com.afklm.repindmsv.tribe;

import com.afklm.repind.common.config.documentation.ConfigDocumentationScan;
import com.afklm.repind.common.controller.advice.ControllerAdviceScan;
import com.afklm.repind.common.controller.metric.MetricControllerScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import(value= {
        ConfigDocumentationScan.class,
        MetricControllerScan.class,
        ControllerAdviceScan.class
})
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}