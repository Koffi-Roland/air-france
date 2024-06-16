package com.afklm.batch.propagate.email;

import com.afklm.batch.propagate.email.config.PropagateEmailConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BatchPropagateEmail {


    /**
     * Launch application
     */
    public static void main(String[] args) {
        log.info("Start of BatchPropagateEmail");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PropagateEmailConfig.class)) {

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("offset", args[0])
                    .toJobParameters();

            JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
            Job job = (Job) context.getBean("propagateEmailJob");
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            log.error("Failed to run BatchPropagateEmail");
            e.printStackTrace();
        }
        log.info("End BatchPropagateEmail");
    }


}
