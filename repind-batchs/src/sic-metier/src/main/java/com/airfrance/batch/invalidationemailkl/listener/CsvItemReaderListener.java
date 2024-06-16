package com.airfrance.batch.invalidationemailkl.listener;

import com.airfrance.batch.invalidationemailkl.model.InputInvalid;
import com.airfrance.batch.invalidationemailkl.service.InvalidationEmailKLSummaryService;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@StepScope
public class CsvItemReaderListener implements ItemReadListener<InputInvalid> {

    @Autowired
    private InvalidationEmailKLSummaryService summaryService;

    @Override
    public void beforeRead() {
        summaryService.incrementNbProcessedLines();
    }

    @Override
    public void afterRead(InputInvalid item) {
        //do nothing
    }

    @Override
    public void onReadError(Exception ex) {
        summaryService.incrementNbTechnicalError();
        summaryService.addErrorMessage( ex.getMessage() + ";" + "TECHNICAL" + ";" + "read error" + "\n");
    }
}
