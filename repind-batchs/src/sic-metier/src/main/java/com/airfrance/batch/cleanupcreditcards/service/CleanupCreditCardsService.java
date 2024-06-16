package com.airfrance.batch.cleanupcreditcards.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CleanupCreditCardsService {
    @Autowired
    private ApplicationContext ctx;

    @Autowired
    CleanupCreditCardsSummaryService summaryService;

    public void execute() {
        log.info("Service CleanupCreditCardsService is started...");
        try {
            JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
            Job batchJob = (Job) ctx.getBean("cleanupCreditCardsJob");
            log.info("Job launched.");
            JobExecution execInitBatchJob = jobLauncher.run(batchJob, new JobParameters());
            BatchStatus status = execInitBatchJob.getStatus();
            log.info("Batch execution finished with status : " + status);

        } catch (Exception e) {
            log.error("Failed to execute batch : ", e);
            System.exit(1);
        }finally {
            log.info(summaryService.printCounter());
        }
    }

}
