package com.airfrance.batch.automaticmerge.service;

import com.airfrance.batch.automaticmerge.logger.MergeDuplicateScoreLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
@Slf4j
public class AutomaticMergeService {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private FileService fileService;

    @Autowired
    private AutomaticMergeSummaryService summaryService;


    public void execute(String inputPath, String outputPath, String resultFileName, String logFileName, String metricFileName) {
        log.info("Service started.");
        try {
            JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
            Job batchJob = (Job) ctx.getBean("automaticMergeBatchJob");
            log.info("Job launched.");
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            jobParametersBuilder.addString("inputPath", inputPath).toJobParameters();
            jobParametersBuilder.addString("outputPath", outputPath).toJobParameters();
            jobParametersBuilder.addString("resultFileName", resultFileName).toJobParameters();
            jobParametersBuilder.addString("logFileName", logFileName).toJobParameters();
            jobParametersBuilder.addString("metricFileName", metricFileName).toJobParameters();
            JobExecution execInitBatchJob = jobLauncher.run(batchJob, jobParametersBuilder.toJobParameters());
            BatchStatus status = execInitBatchJob.getStatus();
            log.info("Batch execution finished with status : " + status);

            // Create report
            if (BatchStatus.COMPLETED.equals(status)) {
                MergeDuplicateScoreLogger logger = new MergeDuplicateScoreLogger(outputPath, logFileName );
                logger.writeListToFile(summaryService.getMessage());
                summaryService.generateMetricFile(outputPath, metricFileName);
                backupFileProcessed();
                log.info("Metric file generated.");
            }
        } catch (Exception e) {
            log.error("Failed to execute batch : ", e);
            System.exit(1);
        }
    }

    private void backupFileProcessed(){
        try{
            Path fileInProcessing = fileService.getFileInProcessing();
            fileService.moveFileToProcessed(fileInProcessing);
        }catch(IOException e){
            log.error("Exception during backup File Processed by batch, ", e);
        }


    }

}