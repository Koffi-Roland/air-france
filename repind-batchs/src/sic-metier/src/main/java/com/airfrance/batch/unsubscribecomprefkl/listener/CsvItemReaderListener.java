package com.airfrance.batch.unsubscribecomprefkl.listener;

import com.airfrance.batch.unsubscribecomprefkl.model.UnsubscribeComprefInput;
import com.airfrance.batch.unsubscribecomprefkl.service.UnsubscribeComprefKLSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@StepScope
@Slf4j
public class CsvItemReaderListener implements ItemReadListener<UnsubscribeComprefInput> {

    @Autowired
    private UnsubscribeComprefKLSummaryService summaryService;

    @Override
    public void beforeRead() {
        summaryService.incrementNbProcessedLines();
    }

    @Override
    public void afterRead(UnsubscribeComprefInput item) {
        //do nothing
    }

    @Override
    public void onReadError(Exception ex) {
        if(ex instanceof FlatFileParseException parseException){
            summaryService.incrementNbTechnicalError();
            String builder = "line=" + parseException.getLineNumber() + ";"
                    + ex.getMessage() + ";"
                    + "TECHNICAL" + ";"
                    + "parseException" + "\n";
            summaryService.addErrorMessage(builder);
            log.error(builder,parseException);
        }else{
            summaryService.incrementNbTechnicalError();
            log.error("an error occured", ex);
        }

    }
}
