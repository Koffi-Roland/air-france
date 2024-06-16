package com.airfrance.batch.deduplicatecompref.service;

import com.airfrance.batch.deduplicatecompref.model.CommunicationPreferencesModel;
import com.airfrance.repind.bean.CommunicationPreferencesBean;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.individu.MarketLanguageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j(topic = "DeduplicateComprefService")
@RequiredArgsConstructor
public class DeduplicateComprefService {

    /**
     * Application context- Central interface to provide configuration for an application
     */
    private final ApplicationContext ctx;

    /**
     * Communication preferences repository - inject by spring
     */
    private final CommunicationPreferencesRepository communicationPreferencesRepository;

    /**
     * Market language repository - inject by spring
     */
    private final MarketLanguageRepository marketLanguageRepository;

    /**
     * Find communication preference wiht SNAF or SNKL
     *
     * @param communicationPreferencesModel communication preferences model
     * @return List<CommunicationPreferencesDto> list of communication preferences dto
     */
      public List<CommunicationPreferencesBean> findCommunicationPreferences(CommunicationPreferencesModel communicationPreferencesModel)
      {
       return  this.communicationPreferencesRepository.getCommunicationPreferencesSNAForSNKL(communicationPreferencesModel.getComPrefId(), communicationPreferencesModel.getGin(),communicationPreferencesModel.getComType());
      }

    /**
     * Delete communication preferences SNAF or SNKL
     *
     * @param comPrefIdNew New communication preferences
     * @param comPrefIdOld Old communication preferences
     * @param gin Individual identifier
     * @param comType Communication type
     */
      public void deleteCommunicationPreferences(Integer comPrefIdNew, Integer comPrefIdOld,String gin,String comType)
      {
        this.communicationPreferencesRepository.deleteCommunicationPreferencesSNAForSNKL(comPrefIdNew,comPrefIdOld,gin,comType);
      }

    /**
     * Update Market language
     *
     * @param comPrefIdNew New communication preferences
     * @param comPrefIdOld Old communication preferences
     */
     public void  updateMarketLanguage(Integer comPrefIdNew, Integer comPrefIdOld)
      {
          this.marketLanguageRepository.updateMarketLanguageForSNAForSNKL(comPrefIdNew,comPrefIdOld);
      }

    /**
     * Delete market language according to communication preferences identifier and market language identifier
     *
     * @param comPrefId Communication preferences identifier
     * @param marketLanguageId Market language identifier
     */
      public void deleteByComprefAndMarketLanguage(Integer comPrefId,Integer marketLanguageId)
      {
        this.marketLanguageRepository.deleteByComprefAndMarketLanguage(comPrefId,marketLanguageId);
      }

    /**
     * Job execution service with the deduplicate communication preferences job bean
     */
    public BatchStatus execute(String comType,String comGroupType, String domain,String jobName) {

        BatchStatus status = null;
        try {
            JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
            Job batchJob = (Job) ctx.getBean(jobName);
            log.info("Job {} launched.", jobName);
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            jobParametersBuilder.addString("comType", comType).toJobParameters();
            jobParametersBuilder.addString("comGroupType", comGroupType).toJobParameters();
            jobParametersBuilder.addString("domain", domain).toJobParameters();

            JobExecution execInitBatchJob = jobLauncher.run(batchJob,jobParametersBuilder.toJobParameters());
             status = execInitBatchJob.getStatus();
            log.info("Batch execution finished with status : " + status);

        } catch (Exception e) {
            log.error("Failed to execute batch : ", e);
            System.exit(1);
        }
        return status;
    }

}
