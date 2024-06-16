package com.airfrance.batch.lastactivity.service;

import com.airfrance.batch.common.entity.lastactivity.LastActivity;
import com.airfrance.batch.common.repository.lastactivity.LastActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Last activity batch service
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LastActivityService {

    /**
     * Application context- Central interface to provide configuration for an application
     */
    private final ApplicationContext ctx;
    /**
     * Last activity repository - inject by spring
     */
    private final LastActivityRepository lastActivityRepository;

    /**
     * Job execution service with the last activity job bean
     */

    public void execute() {
        log.info("Service is started...");
        try {
            JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
            Job batchJob = (Job) ctx.getBean("lastActivityJob");
            log.info("Job launched.");
            JobExecution execInitBatchJob = jobLauncher.run(batchJob, new JobParameters());
            BatchStatus status = execInitBatchJob.getStatus();
            log.info("Batch execution finished with status : " + status);

        } catch (Exception e) {
            log.error("Failed to execute batch : ", e);
            System.exit(1);
        }
    }

    /**
     * Save and flush list of last activity
     *
     * @param lastActivities list of last activity
     */
    public void saveAndFlushLastActivities(List<LastActivity> lastActivities)
    {
        this.lastActivityRepository.saveAllAndFlush(lastActivities);
    }

    /**
     * Update Last activity
     *
     * @param lastActivity last activity
     */
    public void updateLastActivity(LastActivity lastActivity)
    {
        this.lastActivityRepository.updateLastActivity(lastActivity.getDateModification(), lastActivity.getSiteModification(), lastActivity.getSourceModification(), lastActivity.getSignatureModification(), lastActivity.getGin());
    }

    /**
     * Find last activity by gin
     *
     * @param gin individual identity number
     * @return last activity
     */
    public Optional<LastActivity> findByGin(String gin)
    {
        return this.lastActivityRepository.findByGin(gin);
    }

    /**
     * Find last activity from others referenced table(Adr_Post, Email, telecoms.....)
     *
     * @param gin individual numner
     * @return Last activity model
     */
    public List<LastActivity> findLastActivityByOthers(String gin)
    {
        return this.lastActivityRepository.findLastActivityByOthers(gin);
    }
}
