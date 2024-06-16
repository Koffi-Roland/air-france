package com.airfrance.batch.compref.fixafcompref.service;

import com.airfrance.batch.compref.fixafcompref.enums.FileNameEnum;
import com.airfrance.batch.compref.fixafcompref.logger.FixAfComPrefLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FixAfComPrefService {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private FixAfComPrefCounterService fixAfComPrefCounterService;

    @Autowired
    @Qualifier("tokens")
    private String[] tokens;

    public void execute(String iCsvFileName , String iInputPath , String iOutputPath){
        log.info("Execution Fix AF for Com Pref Service...");
        try{
            // Chargement de la config
            JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");

            Job batchJob = (Job) ctx.getBean( "fixAfComPrefJob" );

            log.info("Execution du Job...");

            FixAfComPrefLogger logger = new FixAfComPrefLogger(iCsvFileName , iOutputPath , tokens);
            initOutputFiles(logger);

            JobParametersBuilder jobParametersBuilder =  new JobParametersBuilder();
            jobParametersBuilder.addString("fileName" , iCsvFileName).toJobParameters();
            jobParametersBuilder.addString("inputPath" , iInputPath).toJobParameters();
            jobParametersBuilder.addString("outputPath" , iOutputPath).toJobParameters();
            JobExecution job = jobLauncher.run(batchJob,jobParametersBuilder.toJobParameters());
            log.info("Batch execution of Fix AF for Com Pref done : " + job.getStatus());

            logger.write(logger.getFile(FileNameEnum.SUCCESS.getValue()), fixAfComPrefCounterService.printCounter());

        }catch (Exception e) {
            log.error("Erreur lors de l'execution du batch" , e);
            System.exit(1);
        }
    }

    /**
     * Create all the excel file for output
     * @param iLogger
     */
    public void initOutputFiles(FixAfComPrefLogger iLogger){
        iLogger.write(FileNameEnum.GIN_AND_CIN_DOESNT_EXIST , null);
        iLogger.write(FileNameEnum.GIN_AND_EMAIL_DOESNT_EXIST , null);
        iLogger.write(FileNameEnum.GIN_DOESNT_EXIST , null);
        iLogger.write(FileNameEnum.LANGUAGE_DOESNT_EXIST , null);
        iLogger.write(FileNameEnum.MARKET_DOESNT_EXIST , null);
        iLogger.write(FileNameEnum.ERROR , null);
    }
}
