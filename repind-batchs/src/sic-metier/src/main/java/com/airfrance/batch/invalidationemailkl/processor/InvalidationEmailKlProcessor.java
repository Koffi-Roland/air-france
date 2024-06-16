package com.airfrance.batch.invalidationemailkl.processor;

import com.airfrance.batch.invalidationemailkl.model.InputInvalid;
import com.airfrance.batch.invalidationemailkl.service.InvalidationEmailKLSummaryService;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InvalidationEmailKlProcessor implements ItemProcessor<InputInvalid, InputInvalid>  {

    @Autowired
    private InvalidationEmailKLSummaryService summaryService;

    @Override
    public InputInvalid process(InputInvalid item) throws IllegalArgumentException, JrafDomainException {
        // step1 : validate mandatory fields
        if(!StringUtils.isNotBlank(item.getActionIndex())
                || !StringUtils.isNotBlank(item.getComReturnCodeIndex())
                || !StringUtils.isNotBlank(item.getContactTypeIndex())
                || !StringUtils.isNotBlank(item.getContactIndex())
                || !StringUtils.isNotBlank(item.getCauseIndex())
        ){
            summaryService.incrementNbEmptyMandatoryFields();
            summaryService.addErrorMessage("#" + item + "#;" + "BLANK_MANDATORY_FIELD" + "\n");
            log.error("Some mandatory fields are null for entry - {}", item);
            return null;
        }

        return item;
    }

}