package com.airfrance.batch.unsubscribecomprefkl.service;

import com.airfrance.batch.unsubscribecomprefkl.model.UnsubscribeComprefInput;
import com.airfrance.ref.exception.compref.CommunicationPreferencesNotFoundException;
import com.airfrance.ref.exception.compref.MarketLanguageNotFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.individu.MarketLanguageRepository;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesTransform;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.dto.individu.MarketLanguageTransform;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.MarketLanguage;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
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
    public class UnsubscribeComprefKLService {

    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private CommunicationPreferencesDS comprefDS;
    @Autowired
    private CommunicationPreferencesRepository communicationPreferencesRepository;
    @Autowired
    private MarketLanguageRepository marketLanguageRepository;


    public static final String BATCH_QVI = "BATCH_QVI";

    public BatchStatus execute(String inputPath, String outputPath, String fileName, String jobName) {
        log.info("Service is started...");
        try {
            JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
            Job batchJob = (Job) ctx.getBean(jobName);
            log.info("Job {} launched.", jobName);
            JobParametersBuilder jobParamBuilder = new JobParametersBuilder();
            jobParamBuilder.addString("inputPath", inputPath).toJobParameters();
            jobParamBuilder.addString("outputPath", outputPath).toJobParameters();
            jobParamBuilder.addString("fileName", fileName).toJobParameters();
            JobExecution execInitBatchJob = jobLauncher.run(batchJob, jobParamBuilder.toJobParameters());
            BatchStatus status = execInitBatchJob.getStatus();
            log.info("Job {} execution finished wih status : {} .", jobName, status);
            return status;
        } catch (Exception e) {
            log.error("Batch[BatchUnsubscribeComprefKL] execution failed during job {} : {} .. ", jobName, e.getMessage());
            System.exit(1);
        }
        return null;
    }

    /**
     * This method takes an object, will retrieve market/language then will invalidate it
     * @param item InputInvalid
     * @throws JrafDomainException exception
     */
    public void unsubscribeMarketLanguage(UnsubscribeComprefInput item) throws JrafDomainException {
        String gin    = item.getGinIndex();
        String domain = item.getDomainComprefIndex();
        String comGroupType = item.getComGroupTypeComprefIndex();
        String comType = item.getComTypeComprefIndex();
        String market   = item.getMarketComprefIndex();
        String language = item.getLanguageComprefIndex();
        String modificationSignature = "Un_Batch_" + item.getCauseIndex();

        // step2 : retrieve compref
        if ( item.getGinIndex().matches("[0-9]+")) {
            CommunicationPreferencesDTO comPrefDTO = comprefDS.findComPrefId(gin, domain, comGroupType, comType);
            if (comPrefDTO == null) {
                throw new CommunicationPreferencesNotFoundException("[-] COM_PREF_ID not found in COMMUNICATION PREFERENCES", gin, domain, comGroupType, comType);
            }

            MarketLanguageDTO mlDTO = comprefDS.findMarketId(comPrefDTO.getComPrefId(), market, language);
            if (mlDTO == null) {
                throw new MarketLanguageNotFoundException("[-] MARKET_ID not found in MARKET LANGUAGE", comPrefDTO.getComPrefId().toString(), market, language);
            }
            if (mlDTO.getOptIn() == null || !"N".equals(mlDTO.getOptIn())) {
                MarketLanguage marketLanguage = MarketLanguageTransform.dto2Bo(mlDTO);
                marketLanguage.setModificationSignature(modificationSignature);
                marketLanguage.setModificationSite(BATCH_QVI);
                marketLanguageRepository.unsubscribeMarketLanguage(marketLanguage);
                if (comprefDS.checkOptinCoherence(comPrefDTO) == 0) {
                    CommunicationPreferences comPref = CommunicationPreferencesTransform.dto2Bo2(comPrefDTO);
                    comPref.setModificationSignature(modificationSignature);
                    comPref.setModificationSite(BATCH_QVI);
                    communicationPreferencesRepository.unsubscribeCommPref(comPref);
                }
            }
        }
    }
}
