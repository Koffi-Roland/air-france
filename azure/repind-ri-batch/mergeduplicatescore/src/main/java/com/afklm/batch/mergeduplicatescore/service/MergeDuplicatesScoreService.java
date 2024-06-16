package com.afklm.batch.mergeduplicatescore.service;

import com.afklm.batch.mergeduplicatescore.logger.MergeDuplicateScoreLogger;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.environnement.VariablesDTO;
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

import static com.afklm.batch.mergeduplicatescore.helper.Constant.MERGE_CURRENT_COUNT;
import static com.afklm.batch.mergeduplicatescore.helper.Constant.MERGE_MAX_SIZE;

@Service
@Slf4j
public class MergeDuplicatesScoreService {

    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private VariablesDS variablesDS;

    @Autowired
    private MergeDuplicateScoreSummaryService summaryService;


    public void execute(String inputPath, String outputPath, String resultFileName, String logFileName, String metricFileName) {
        log.info("Service started.");
        try {
            JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
            Job batchJob = (Job) ctx.getBean("initBatchJob");
            log.info("Job launched.");
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            jobParametersBuilder.addString("inputPath", inputPath).toJobParameters();
            jobParametersBuilder.addString("outputPath", outputPath).toJobParameters();
            jobParametersBuilder.addString("resultFileName", resultFileName).toJobParameters();
            jobParametersBuilder.addString("logFileName", logFileName).toJobParameters();
            jobParametersBuilder.addString("metricFileName", metricFileName).toJobParameters();
            jobParametersBuilder.addLong("mergeMaxSize", getMergeMaxSize()).toJobParameters();
            jobParametersBuilder.addLong("mergeCurrentCount", getMergeCurrentCount()).toJobParameters();
            JobExecution execInitBatchJob = jobLauncher.run(batchJob, jobParametersBuilder.toJobParameters());
            BatchStatus status = execInitBatchJob.getStatus();
            log.info("Batch execution finished with status : " + status);

            // Create report
            if (BatchStatus.COMPLETED.equals(status)) {
                MergeDuplicateScoreLogger logger = new MergeDuplicateScoreLogger(outputPath, logFileName );
                logger.writeListToFile(summaryService.getMessage());
                summaryService.generateMetricFile(outputPath, metricFileName);
                log.info("Metric file generated.");
            }
        } catch (Exception e) {
            log.error("Failed to execute batch : ", e);
            System.exit(1);
        }
    }

    private long getMergeMaxSize() throws JrafDomainException {
        return Long.parseLong(variablesDS.getByEnvKey(MERGE_MAX_SIZE).getEnvValue());
    }

     private long getMergeCurrentCount() throws JrafDomainException {
        return Long.parseLong(variablesDS.getByEnvKey(MERGE_CURRENT_COUNT).getEnvValue());
    }

    public long updateMergeCurrentCount(long newMergeCount) throws JrafDomainException {
        VariablesDTO variable = new VariablesDTO(MERGE_CURRENT_COUNT, String.valueOf(newMergeCount));
        variablesDS.update(variable);
        log.info("[+] Successfully updated MERGE_CURRENT_COUNT env_var: new val is " + newMergeCount);
        return newMergeCount;
    }

}
