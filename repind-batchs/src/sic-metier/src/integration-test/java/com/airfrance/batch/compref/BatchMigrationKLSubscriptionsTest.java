package com.airfrance.batch.compref;

import com.airfrance.batch.config.MigrationKLSubConfigTest;
import com.airfrance.batch.compref.migklsub.service.MigrationKLSubCounterService;
import com.airfrance.batch.utils.IConstants;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.MarketLanguage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@SpringBootTest(classes  = {MigrationKLSubConfigTest.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Profile("integration-test")
public class BatchMigrationKLSubscriptionsTest {

    public final String CSV_FILE_NAME = "SFMC_Feed_20200720.csv";
    public final String PATH = "";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private MigrationKLSubCounterService migrationKLSubCounterService;

    @Autowired
    private CommunicationPreferencesRepository communicationPreferencesRepository;

    public JobParameters getJobParameter(){
        return new JobParametersBuilder()
                .addString("fileName" , CSV_FILE_NAME)
                .addString("inputPath" , PATH)
                .addString("outputPath" , PATH)
                .toJobParameters();
    }

    @Test
    public void executeTest() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(getJobParameter());
        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            if("initStep".equals(stepExecution.getStepName())){
                Assert.assertEquals(153, stepExecution.getReadCount());
            }
            if("initSlave:parition1".equals(stepExecution.getStepName())){
                Assert.assertEquals(94, stepExecution.getReadCount());
            }
            if("initSlave:parition0".equals(stepExecution.getStepName())){
                Assert.assertEquals(59, stepExecution.getReadCount());
            }
        }

        checkComPref();

        int ginCinSuccessCount = 5;
        int ginEmailSuccessCount = 5;
        int fbSuccessCount = 0;
        int maSuccessCount = 0;
        int duplicateLine = 0;
        int numberOfLine = 152;


        Assert.assertEquals("Success Gin and Cin : " + ginCinSuccessCount + " \n"
        		+ "Success Gin and Email : " + ginEmailSuccessCount + " \n"
        		+ "Success Flying blue created or updated : " + fbSuccessCount + " \n"
        		+ "Success MyAccount created or updated : " + maSuccessCount + " \n"
        		+ "Duplicate lines : " + duplicateLine + " \n"
        		+ "Number of file lines : " + numberOfLine + " \n",
        		migrationKLSubCounterService.printCounter());
        Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
    }

    private void checkComPref() {
        List<CommunicationPreferences> comPrefs = communicationPreferencesRepository.findAll();
        Assert.assertEquals(7 , comPrefs.size());

        comPrefs.stream().forEach(cP -> {
            Assert.assertTrue(compareDate(new Date(),cP.getModificationDate()));
            Assert.assertTrue(compareDate(new Date(),cP.getCreationDate()));
            Assert.assertEquals(IConstants.CSM_WW, cP.getCreationSignature());
            Assert.assertEquals(IConstants.CSM_WW, cP.getModificationSignature());
            Assert.assertEquals(IConstants.BATCH_QVI, cP.getCreationSite());
            Assert.assertEquals(IConstants.BATCH_QVI, cP.getModificationSite());
            Assert.assertEquals(1 ,  cP.getMarketLanguage().size());
            MarketLanguage marketLanguage = (MarketLanguage) cP.getMarketLanguage().toArray()[0];
            Assert.assertTrue(compareDate(new Date(),marketLanguage.getModificationDate()));
            Assert.assertTrue(compareDate(new Date(),marketLanguage.getCreationDate()));
            Assert.assertTrue(marketLanguage.getCreationSite().length() == 1 || marketLanguage.getCreationSite().equals(IConstants.BATCH_QVI));
        });
    }

    private boolean compareDate(Date iDate1 , Date iDate2){
        DateFormatter dateFormatter = new DateFormatter(IConstants.DATE_FORMATTER);
        String sDate1 = dateFormatter.print(iDate1, Locale.FRANCE);
        String sDate2 = dateFormatter.print(iDate2, Locale.FRANCE);
        return sDate1.equalsIgnoreCase(sDate2);
    }
}
