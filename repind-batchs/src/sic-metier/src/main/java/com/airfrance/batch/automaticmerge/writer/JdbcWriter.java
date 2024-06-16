package com.airfrance.batch.automaticmerge.writer;

import com.airfrance.batch.automaticmerge.logger.MergeDuplicateScoreLogger;
import com.airfrance.batch.automaticmerge.model.OutputRecord;
import com.airfrance.batch.automaticmerge.service.AutomaticMergeSummaryService;
import com.airfrance.batch.automaticmerge.service.IndividusDS;
import com.airfrance.batch.common.metric.StatusEnum;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.airfrance.batch.automaticmerge.helper.Constant.*;

@Component
public class JdbcWriter implements ItemWriter<OutputRecord> {

    private final IndividusDS individusDS;

    private final AutomaticMergeSummaryService summaryService;

    private final MergeDuplicateScoreLogger logger;

    public JdbcWriter(IndividusDS individusDS, AutomaticMergeSummaryService summaryService, MergeDuplicateScoreLogger logger) {
        this.individusDS = individusDS;
        this.summaryService = summaryService;
        this.logger = logger;
    }

    @Override
    public void write(List<? extends OutputRecord> outputRecords) {
        if(!outputRecords.isEmpty()){
            writeJDBC(outputRecords);
        }
    }

    private void writeJDBC(List<? extends OutputRecord> outputRecords) {
        for (OutputRecord outputRecord : outputRecords) {
            List<String> gins = Arrays.asList(outputRecord.getGinSource().split(COMMA));
            gins.forEach(
                    gin -> {
                        try {
                            individusDS.updateIndividuSource(gin, outputRecord);
                            summaryService.incrementNbMerged();
                            logger.logComo(gin, MERGE_SUCCESS, StatusEnum.SUCCESS );
                        } catch (Exception e) {
                            summaryService.incrementFailedMergeInDB();
                            String message = WRITE_DATABASE_ERROR +
                                    COMMA + gin +
                                    COMMA + e.getMessage() +
                                    COMMA + WRITE_DATABASE + END_OF_LINE;
                            summaryService.addErrorMessage(message);
                        }
                    }
            );

        }
    }
}
