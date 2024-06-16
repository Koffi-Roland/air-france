package com.afklm.batch.addnewklcompref;

import com.afklm.batch.addnewklcompref.config.AddNewKlComPrefConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class BatchAddNewKlComPref {
    /**
     * Launch application
     */
    public static void main(String[] args) {
        log.info("Start of BatchAddNewKlComPref");
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AddNewKlComPrefConfig.class)) {
            JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
            Job job = (Job) context.getBean("addNewKlComPrefJob");
            jobLauncher.run(job, new JobParameters());
        }
        catch (Exception e) {
            log.error("Failed to run BatchAddNewKlComPref");
            e.printStackTrace();
        }
        log.info("End BatchAddNewKlComPref");
    }
}
