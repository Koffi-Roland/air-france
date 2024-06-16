package com.afklm.batch.prospect.tasklet;

import com.afklm.batch.prospect.property.AlimentationProspectProperty;
import com.afklm.batch.prospect.service.AlimentationProspectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

@Component
@Slf4j
public class AlimentationProspectOutputTasklet implements Tasklet {

    private AlimentationProspectProperty property;
    private AlimentationProspectService service;

    public AlimentationProspectOutputTasklet(
            AlimentationProspectProperty property,
            AlimentationProspectService service) {

        this.property = property;
        this.service = service;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        String reportDate = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String inputFileName = service.getFilePath().replace(property.getFtpDir(), "");
        // for local only
        inputFileName = inputFileName.replace("C:/tmp/EDATIS/", "");
        inputFileName = inputFileName.replace(property.getFileExt(), "");

        StringBuilder outputFileName =
                new StringBuilder(property.getReportPath())
                        .append(reportDate)
                        .append("/")
                        .append(inputFileName)
                        .append(property.getReportFilename());

        try {
            Path output = Paths.get(outputFileName.toString());
            Files.write(output, Arrays.asList(service.printCounter()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Unable to write report file");
        }


        return RepeatStatus.FINISHED;
    }
}
