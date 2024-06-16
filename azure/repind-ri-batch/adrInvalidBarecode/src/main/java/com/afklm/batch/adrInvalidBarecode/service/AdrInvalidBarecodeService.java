package com.afklm.batch.adrInvalidBarecode.service;

import com.airfrance.repind.service.environnement.internal.VariablesDS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class AdrInvalidBarecodeService {
    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private VariablesDS variablesDS;
    @Autowired
    AdrInvalidBarecodeSummaryService summaryService;


    public void execute(String fileName, String inputPath, String outputPath, String resultFileName, String metricFileName, Date startBatch) {
        log.info("Service is started...");
        try {
            JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
            Job batchJob = (Job) ctx.getBean("adrInvalidBarecodeJob");
            log.info("Job launched.");
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            jobParametersBuilder.addString("fileName", fileName).toJobParameters();
            jobParametersBuilder.addString("inputPath", inputPath).toJobParameters();
            jobParametersBuilder.addString("outputPath", outputPath).toJobParameters();
            jobParametersBuilder.addString("resultFileName", resultFileName).toJobParameters();
            jobParametersBuilder.addString("metricFileName", metricFileName).toJobParameters();
            JobExecution execInitBatchJob = jobLauncher.run(batchJob, jobParametersBuilder.toJobParameters());
            BatchStatus status = execInitBatchJob.getStatus();
            log.info("Batch execution finished with status : " + status);

            // Create report
            if (BatchStatus.COMPLETED.equals(status)) {
                summaryService.generateMetricFile(outputPath, metricFileName, startBatch);
                summaryService.zipFiles(outputPath,fileName,resultFileName, metricFileName);
                log.info("Metric file generated.");
            }
        } catch (Exception e) {
            log.error("Failed to execute batch : ", e);
            System.exit(1);
        }
    }

}
