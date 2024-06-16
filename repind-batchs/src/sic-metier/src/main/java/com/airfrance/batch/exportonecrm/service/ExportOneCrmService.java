package com.airfrance.batch.exportonecrm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExportOneCrmService {
    @Autowired
    private ApplicationContext ctx;

    private static final String OUTPUT = "/app/REPIND/data/exportonecrm" ;


    public BatchStatus execute() {
        String jobName = "exportOneCrmJob";
        log.info("Service is started...");
        try {
            JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
            Job batchJob = (Job) ctx.getBean(jobName);
            log.info("Job {} launched.", jobName);
            JobParametersBuilder jobParamBuilder = new JobParametersBuilder();
            jobParamBuilder.addString("outputPath", OUTPUT).toJobParameters();
            JobExecution execInitBatchJob = jobLauncher.run(batchJob, jobParamBuilder.toJobParameters());
            BatchStatus status = execInitBatchJob.getStatus();
            log.info("Job {} execution finished wih status : {} .", jobName, status);
            return status;
        } catch (Exception e) {
            log.error("Batch[BatchExportOneCrm] execution failed during job {} : {} .. ", jobName, e.getMessage());
            System.exit(1);
        }
        return null;
    }

}
