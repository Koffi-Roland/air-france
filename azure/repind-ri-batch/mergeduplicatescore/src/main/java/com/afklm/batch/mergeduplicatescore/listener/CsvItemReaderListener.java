package com.afklm.batch.mergeduplicatescore.listener;

import com.afklm.batch.mergeduplicatescore.model.InputRecord;
import com.afklm.batch.mergeduplicatescore.service.MergeDuplicateScoreSummaryService;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.afklm.batch.mergeduplicatescore.helper.Constant.*;

@Service
@StepScope
public class CsvItemReaderListener implements ItemReadListener<InputRecord> {

    @Autowired
    private MergeDuplicateScoreSummaryService summaryService;

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
        summaryService.incrementFailedLinesCounter();
        StringBuilder message = new StringBuilder(READ_CSV_ERROR)
                .append(COMMA).append(ex.getMessage())
                .append(COMMA).append(READ_CSV).append(END_OF_LINE);
        summaryService.addErrorMessage(message.toString());
    }
}
