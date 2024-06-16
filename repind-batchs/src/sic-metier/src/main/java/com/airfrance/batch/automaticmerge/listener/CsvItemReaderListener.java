package com.airfrance.batch.automaticmerge.listener;

import com.airfrance.batch.automaticmerge.service.AutomaticMergeSummaryService;
import com.airfrance.batch.automaticmerge.model.InputRecord;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.airfrance.batch.automaticmerge.helper.Constant.*;

@Service
@StepScope
public class CsvItemReaderListener implements ItemReadListener<InputRecord> {

    @Autowired
    private AutomaticMergeSummaryService summaryService;

    @Override
    public void beforeRead() {
        //do nothing
    }

    @Override
    public void afterRead(InputRecord item) {
        //do nothing
    }

    @Override
    public void onReadError(Exception ex) {
        summaryService.incrementSkipLineError();
        String message = READ_CSV_ERROR +
                COMMA + ex.getMessage() +
                COMMA + READ_CSV + END_OF_LINE;
        summaryService.addErrorMessage(message);
    }
}
