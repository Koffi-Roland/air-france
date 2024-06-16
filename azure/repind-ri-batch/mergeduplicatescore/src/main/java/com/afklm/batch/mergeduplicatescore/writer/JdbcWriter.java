package com.afklm.batch.mergeduplicatescore.writer;

import com.afklm.batch.mergeduplicatescore.logger.MergeDuplicateScoreLogger;
import com.afklm.batch.mergeduplicatescore.model.OutputRecord;
import com.afklm.batch.mergeduplicatescore.service.IndividusDS;
import com.afklm.batch.mergeduplicatescore.service.MergeDuplicateScoreSummaryService;
import com.airfrance.batch.common.metric.StatusEnum;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.afklm.batch.mergeduplicatescore.helper.Constant.*;

@Component
public class JdbcWriter implements ItemWriter<OutputRecord> {
    @Autowired
    private IndividusDS individusDS;

    @Autowired
    private MergeDuplicateScoreSummaryService summaryService;

    @Autowired
    private MergeDuplicateScoreLogger logger;

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
                            summaryService.incrementFailedLinesCounter();
                            StringBuilder message = new StringBuilder(WRITE_DATABASE_ERROR)
                                    .append(COMMA).append(gin)
                                    .append(COMMA).append(e.getMessage())
                                    .append(COMMA).append(WRITE_DATABASE).append(END_OF_LINE);
                            summaryService.addErrorMessage(message.toString());
                        }
                    }
            );

        }
    }
}
