package com.airfrance.batch.cleanupcreditcards.it;

import com.airfrance.batch.cleanupcreditcards.config.CleanupCreditCardsConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(SpringExtension.class)
@SpringBatchTest
@ContextConfiguration(classes = {CleanupCreditCardsConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CleanupCreditardsTestConfig{
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Test
    @DisplayName("test Job without Parameters NOK")
    public void testCleanupCreditardsJob_withoutParams_Fails() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        assertEquals(ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }
}
