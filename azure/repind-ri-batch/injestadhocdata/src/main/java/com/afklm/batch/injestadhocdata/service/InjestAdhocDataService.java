package com.afklm.batch.injestadhocdata.service;

import com.afklm.batch.injestadhocdata.logger.InjestAdhocDataLogger;
import com.afklm.batch.injestadhocdata.property.InjestAdhocDataPropoerty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@Slf4j
public class InjestAdhocDataService {

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private InjestAdhocDataSummaryService summaryService;

	@Autowired
	private InjestAdhocDataPropoerty property;
	
	public void execute(String inputFileName, String inputPath, String outputPath) {
		log.info("Service started.");
		try {
			JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
			Job batchJob = (Job) ctx.getBean("initBatchJob");
			log.info("Job launched.");
			JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
			jobParametersBuilder.addString("fileName", inputFileName).toJobParameters();
			jobParametersBuilder.addString("inputPath", inputPath).toJobParameters();
			jobParametersBuilder.addString("outputPath", outputPath).toJobParameters();
			JobExecution execInitBatchJob = jobLauncher.run(batchJob, jobParametersBuilder.toJobParameters());
			BatchStatus status = execInitBatchJob.getStatus();
			log.info("Batch execution finished with status : " + status);

			// Create report
			if (BatchStatus.COMPLETED.equals(status)) {
				InjestAdhocDataLogger logger = new InjestAdhocDataLogger(inputFileName, outputPath, property);
				logger.write(summaryService.printCounter(), summaryService.getErrorMessage());
				log.info("Report file generated.");
			}
		} catch (Exception e) {
			log.error("Failed to execute batch : ", e);
			System.exit(1);
		}
	}

}
