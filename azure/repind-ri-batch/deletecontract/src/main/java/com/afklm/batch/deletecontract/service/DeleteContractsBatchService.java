package com.afklm.batch.deletecontract.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeleteContractsBatchService {
    @Autowired
    private ApplicationContext ctx;

    public void execute() {
        log.info("Service is started...");
        try {
            JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
            Job batchJob = (Job) ctx.getBean("deleteContractsBatchJob");
            log.info("Job launched.");
            JobExecution execInitBatchJob = jobLauncher.run(batchJob, new JobParameters());
            BatchStatus status = execInitBatchJob.getStatus();
            log.info("Batch execution finished with status : " + status);

        } catch (Exception e) {
            log.error("Failed to execute batch : ", e);
            System.exit(1);
        }
    }


}
