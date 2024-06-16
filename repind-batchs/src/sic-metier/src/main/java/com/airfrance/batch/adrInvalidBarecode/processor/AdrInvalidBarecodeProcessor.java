package com.airfrance.batch.adrInvalidBarecode.processor;

import com.airfrance.batch.adrInvalidBarecode.model.InputRecord;
import com.airfrance.batch.adrInvalidBarecode.model.OutputRecord;
import com.airfrance.batch.adrInvalidBarecode.service.AdrInvalidBarecodeSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AdrInvalidBarecodeProcessor implements ItemProcessor<InputRecord, OutputRecord> {
    @Autowired
    AdrInvalidBarecodeSummaryService summaryService;

    @Override
    public OutputRecord process(InputRecord inputRecords) throws Exception {
        OutputRecord outputRecord = null;
            summaryService.incrementProcessedLinesCounter();
            log.info(inputRecords.toString());
                outputRecord = new OutputRecord(inputRecords.getNumeroContrat(), inputRecords.getSain(), inputRecords.getDateModification(), "null");
           return  outputRecord;
    }
}


