package com.airfrance.batch.detectduplicates.it;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class DetectDuplicatesTestConfig
{
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Test
    @DisplayName("test Job without Parameters NOK")
    public void testDetectDuplicatesJob_withoutParams_Fails() throws Exception {

    }
}
