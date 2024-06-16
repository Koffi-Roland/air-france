package com.airfrance.batch.unsubscribecomprefkl.writer;

import com.airfrance.batch.unsubscribecomprefkl.model.UnsubscribeComprefInput;
import com.airfrance.batch.unsubscribecomprefkl.service.UnsubscribeComprefKLService;
import com.airfrance.batch.unsubscribecomprefkl.service.UnsubscribeComprefKLSummaryService;
import com.airfrance.ref.exception.compref.CommunicationPreferencesNotFoundException;
import com.airfrance.ref.exception.compref.MarketLanguageNotFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class UnsubscribeComprefKLWriter implements ItemWriter<UnsubscribeComprefInput> {

    @Autowired
    private UnsubscribeComprefKLSummaryService summaryService;
    @Autowired
    private UnsubscribeComprefKLService unsubscribeComprefKLService;

    @Override
    @Transactional
    public void write(@NotNull List<? extends UnsubscribeComprefInput> invalidInputs){
                if (!CollectionUtils.isEmpty(invalidInputs)) {
            invalidInputs.forEach(item -> {
                try {
                    unsubscribeComprefKLService.unsubscribeMarketLanguage(item);
                    summaryService.incrementNbSuccessComprefUnsub();
                } catch(CommunicationPreferencesNotFoundException | MarketLanguageNotFoundException e) {
                    summaryService.incrementNbComprefNotFound();
                    summaryService.addErrorMessage("#" + item + "#;" + "COMPREF_NOT_FOUND" + "\n");
                    log.error("[-] COMPREF_NOT_FOUND - Exception during treatment of compref for item - {}", item);
                }catch(JrafDomainException e) {
                summaryService.incrementNbTechnicalError();
                summaryService.addErrorMessage("#" + item + "#;" + e.getMessage() + "\n");
                log.error("[-] Exception during treatment of compref for item - {}", item);
                }

            });
        }
        else{
            log.error("The list of data is empty.");
            throw new IllegalArgumentException("The list of data is empty...");
        }
    }



}