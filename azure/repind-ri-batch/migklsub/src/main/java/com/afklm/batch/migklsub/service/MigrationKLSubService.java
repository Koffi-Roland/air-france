package com.afklm.batch.migklsub.service;

import com.afklm.batch.migklsub.logger.MigrationKLSubscriptionsLogger;
import com.afklm.batch.migklsub.enums.FileNameEnum;
import com.afklm.batch.migklsub.enums.ModeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

@Slf4j
public class MigrationKLSubService {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private MigrationKLSubCounterService migrationKLSubCounterService;
    
    @Autowired
    @Qualifier("tokens")
    private String[] tokens;

    public void execute(String iCsvFileName , String iInputPath , String iOutputPath , ModeEnum iModeEnum){
        log.info("Execution de la Migration KL Subscriptions Service...");
        try{
            // Chargement de la config
            JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");

            Job batchJob = (Job) ctx.getBean( getJobName(iModeEnum) );

            log.info("Execution du Job...");

            MigrationKLSubscriptionsLogger logger = new MigrationKLSubscriptionsLogger(iCsvFileName , iOutputPath , tokens);
            initOutputFiles(logger);

            JobParametersBuilder jobParametersBuilder =  new JobParametersBuilder();
            jobParametersBuilder.addString("fileName" , iCsvFileName).toJobParameters();
            jobParametersBuilder.addString("inputPath" , iInputPath).toJobParameters();
            jobParametersBuilder.addString("outputPath" , iOutputPath).toJobParameters();
            jobParametersBuilder.addString("mode" , iModeEnum.getName()).toJobParameters();
            JobExecution execInitBatchJob = jobLauncher.run(batchJob,jobParametersBuilder.toJobParameters());
            log.info("Batch execution of KL Subscription done : " + execInitBatchJob.getStatus());

            logger.write(logger.getFile(FileNameEnum.SUCCESS.getValue()), migrationKLSubCounterService.printCounter());

        }catch (Exception e) {
            log.error("Erreur lors de l'execution du batch" , e);
            System.exit(1);
        }
    }

    public String getJobName(ModeEnum modeEnum){
        String name = null;
        switch (modeEnum){
            case INIT:
                name = "initBatchJob";
                log.info("Execution d'Init Batch...");
                break;
            case DAILY:
                name =  "dailyBatchJob";
                log.info("Execution de Daily Batch...");
                break;
        }
        return name;
    }

    /**
     * Create all the excel file for output
     * @param iLogger
     */
    public void initOutputFiles(MigrationKLSubscriptionsLogger iLogger){
        iLogger.write(FileNameEnum.GIN_AND_CIN_DOESNT_EXIST , null);
        iLogger.write(FileNameEnum.GIN_AND_EMAIL_DOESNT_EXIST , null);
        iLogger.write(FileNameEnum.GIN_DOESNT_EXIST , null);
        iLogger.write(FileNameEnum.LANGUAGE_DOESNT_EXIST , null);
        iLogger.write(FileNameEnum.MARKET_DOESNT_EXIST , null);
    }
}
