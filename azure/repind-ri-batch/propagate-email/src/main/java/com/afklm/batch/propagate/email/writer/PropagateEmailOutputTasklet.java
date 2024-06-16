package com.afklm.batch.propagate.email.writer;

import com.airfrance.repind.entity.individu.AccountData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@PropertySource("classpath:/app/propagate-email.properties")
@Component
@Slf4j
public class PropagateEmailOutputTasklet implements Tasklet {

    @Value("${app.propagate.email.fileExt}")
    private String fileExt;

    @Value("${app.propagate.email.outputFileName}")
    private String outputFileName;

    @Value("${app.propagate.email.outputPath}")
    private String outputPath;

    @Value("${app.propagate.email.signature}")
    private String signature;

    private final JdbcTemplate jdbcTemplate;

    private LocalDate date = LocalDate.now();

    public PropagateEmailOutputTasklet(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        List<AccountData> updatedAccounts = jdbcTemplate.query(
                "SELECT * FROM ACCOUNT_DATA WHERE EMAIL_IDENTIFIER IS NOT NULL AND SSIGNATURE_MODIFICATION = ? AND TRUNC(DDATE_MODIFICATION) = TRUNC(SYSDATE)",
                new Object[]{signature},
                new BeanPropertyRowMapper<>(AccountData.class)
        );
        if (updatedAccounts.isEmpty()) {
            log.info("No accounts were updated with a new email.");
            return RepeatStatus.FINISHED;
        }

        String FileName = outputFileName + date + fileExt;
        File output = new File(outputPath, FileName);
        output.createNewFile();

        try (FileWriter writer = new FileWriter(output)) {
            writer.append("Script properly executed. \n\n" +
                    "Total number of Individuals updated with email identifier : " +
                    updatedAccounts.size() + "\n\n " +
                    "Here is the list of Individuals with new linked emails :\n\n" +
                    "SGIN,EMAIL_IDENTIFIER\n");
                    for (AccountData accountData : updatedAccounts) {
                writer.append(accountData.getSgin()).append(",").append(accountData.getEmailIdentifier()).append("\n");
            }
        } catch (IOException e) {
            throw new Exception("Failed to write to CSV file: " + FileName, e);
        }
        log.info("Output file: " +output+ " created successfully");
        return RepeatStatus.FINISHED;
        }

}
