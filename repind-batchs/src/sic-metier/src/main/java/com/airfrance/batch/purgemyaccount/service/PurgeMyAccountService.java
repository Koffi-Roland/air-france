package com.airfrance.batch.purgemyaccount.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class PurgeMyAccountService {
    @Autowired
    private ApplicationContext ctx;

    private static final String DATE = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
    private static final String CSV = ".csv";
    private static final String OUTPUT = "/app/REPIND/data/PURGE_MYA" ;
    private static final String RESULT_BACKUP_FILENAME = "myaBackup_" + DATE + CSV ;
    private static final String RESULT_SUCCESS_FILENAME = "myaUpdate_" + DATE + CSV ;
    private static final String RESULT_REJECT_FILENAME = "myaReject_" + DATE + CSV ;


    public BatchStatus execute(String jobName) {
        log.info("Service is started...");
        try {
            JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
            Job batchJob = (Job) ctx.getBean(jobName);
            log.info("Job {} launched.", jobName);
            JobParametersBuilder jobParamBuilder = new JobParametersBuilder();
            jobParamBuilder.addString("outputPath", OUTPUT).toJobParameters();
            jobParamBuilder.addString("resultBackupFilename", RESULT_BACKUP_FILENAME).toJobParameters();
            jobParamBuilder.addString("resultSuccessFilename", RESULT_SUCCESS_FILENAME).toJobParameters();
            JobExecution execInitBatchJob = jobLauncher.run(batchJob, jobParamBuilder.toJobParameters());
            BatchStatus status = execInitBatchJob.getStatus();
            log.info("Job {} execution finished wih status : {} .", jobName, status);
            return status;
        } catch (Exception e) {
            log.error("Batch[BatchPurgeMyAccount] execution failed during job {} : {} .. ", jobName, e.getMessage());
            System.exit(1);
        }
        return null;
    }

    public static void writeRejectedData(String data){
        File file = new File(OUTPUT, RESULT_REJECT_FILENAME);
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.append(data).append(System.lineSeparator());
        }catch (Exception e){
            log.error("[-] Exception during writeRejectedData : {}", e.getMessage());
        }
    }

}
