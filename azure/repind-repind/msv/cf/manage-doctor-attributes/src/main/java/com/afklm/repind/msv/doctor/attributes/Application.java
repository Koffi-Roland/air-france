package com.afklm.repind.msv.doctor.attributes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

    /** logger */
    private static final Log log = LogFactory.getLog(Application.class);
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}