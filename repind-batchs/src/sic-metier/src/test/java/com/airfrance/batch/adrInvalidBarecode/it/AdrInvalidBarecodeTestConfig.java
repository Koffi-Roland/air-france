package com.airfrance.batch.adrInvalidBarecode.it;

import com.airfrance.batch.adrInvalidBarecode.config.AdrInvalidBarecodeConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBatchTest
@ContextConfiguration(classes = {AdrInvalidBarecodeConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdrInvalidBarecodeTestConfig {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    private static String INPUT_FILENAME = "RINVA1_6904080";
    private static String RESULT_FILENAME = "RESULT_adrInvalidBarecode";
    private String absolutePath;
    @BeforeAll
    public void setup(){
        Path resourceDirectory = Paths.get("src","test","resources", "adrInvalidBarecode");
        absolutePath = resourceDirectory.toFile().getAbsolutePath();
    }

    @AfterAll
    public void clean(){
        // remove files that are generated in test
        Path path = Paths.get(absolutePath, RESULT_FILENAME);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }

    @Test
    @DisplayName("test Job with Parameters OK")
    public void testadrInvalidBarecodeJob_withParams_success() throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("inputPath", absolutePath)
                .addString("fileName", INPUT_FILENAME)
                .addString("outputPath", absolutePath)
                .addString("resultFileName", RESULT_FILENAME)
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        assertTrue(Paths.get(absolutePath, RESULT_FILENAME).toFile().exists());
    }

    @Test
    @DisplayName("test Job without Parameters NOK")
    public void testadrInvalidBarecodeJob_withoutParams_Fails() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        assertEquals(ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
    }
}
