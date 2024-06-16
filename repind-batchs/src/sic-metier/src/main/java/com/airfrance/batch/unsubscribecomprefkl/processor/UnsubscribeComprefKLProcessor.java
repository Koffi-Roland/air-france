package com.airfrance.batch.unsubscribecomprefkl.processor;

import com.airfrance.batch.unsubscribecomprefkl.model.UnsubscribeComprefInput;
import com.airfrance.batch.unsubscribecomprefkl.service.UnsubscribeComprefKLSummaryService;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UnsubscribeComprefKLProcessor implements ItemProcessor<UnsubscribeComprefInput, UnsubscribeComprefInput>  {

    public static final String UNSUBSCRIBE = "U";
    public static final String KL = "KL";
    public static final String KL_PART = "KL_PART";

    @Autowired
    private UnsubscribeComprefKLSummaryService summaryService;

    @Override
    public UnsubscribeComprefInput process(UnsubscribeComprefInput item) throws IllegalArgumentException, JrafDomainException {
        // step1 : validate mandatory fields
        if(!StringUtils.isNotBlank(item.getActionIndex())
                || !StringUtils.isNotBlank(item.getDomainComprefIndex())
                || !StringUtils.isNotBlank(item.getComGroupTypeComprefIndex())
                || !StringUtils.isNotBlank(item.getComTypeComprefIndex())
                || !StringUtils.isNotBlank(item.getGinIndex())
                || !StringUtils.isNotBlank(item.getCauseIndex())
        ){
            summaryService.incrementNbEmptyMandatoryFields();
            summaryService.addErrorMessage("#" + item + "#;" + "BLANK_MANDATORY_FIELD" + "\n");
            log.error("[-] Some mandatory fields are null for entry - {}", item);
            return null;
        }

        if(!UNSUBSCRIBE.equalsIgnoreCase(item.getActionIndex())){
            summaryService.incrementNbIncorrectData();
            summaryService.addErrorMessage("#" + item + "#;" + "ACTION_NOT_U" + "\n");
            log.error("[-] Action is different than U(unsubscribe) - {}", item);
            return null;
        }

        if(verifySizeLimit(item.getCauseIndex())){
            summaryService.incrementNbIncorrectData();
            summaryService.addErrorMessage("#" + item + "#;" + "CAUSE_EXCEED_LIMIT_7_CHARS" + "\n");
            log.error("[-] CAUSE_INDEX exceed limit (7chars) - {}", item);
            return null;
        }

        if(!isKlOrKlPart(item.getComTypeComprefIndex())){
            summaryService.incrementNbIncorrectData();
            summaryService.addErrorMessage("#" + item + "#;" + "COM_TYPE_NOT_KL_OR_KLPART" + "\n");
            log.error("[-] COM_TYPE is not KL or KL_PART - {}", item);
            return null;
        }

        return cleanInput(item);
    }

    /**
     * This method verify if siwe of CAUSE_INDEX exceed limit defined at 7 chars
     * @param cause CAUSE_INDEX
     * @return boolean
     */
    public boolean verifySizeLimit(String cause){
        return cause.length()>7;
    }

    /**
     * verify if COMTYPE_COMPREF_INDEX is either KL or KL_PART
     * @param comType COMTYPE_COMPREF_INDEX
     * @return boolean
     */
    public boolean isKlOrKlPart(String comType){
        return KL.equals(comType) || KL_PART.equals(comType);
    }

    /**
     * This method cleans all trailing spaces in input
     * @param item UnsubscribeComprefInput
     * @return UnsubscribeComprefInput
     */
    public UnsubscribeComprefInput cleanInput(UnsubscribeComprefInput item) {
        if(!StringUtils.isNotBlank(item.getActionIndex())){
            item.setActionIndex(item.getActionIndex().trim());
        }
        if(!StringUtils.isNotBlank(item.getDomainComprefIndex())){
            item.setDomainComprefIndex(item.getDomainComprefIndex());
        }
        if(!StringUtils.isNotBlank(item.getComGroupTypeComprefIndex())){
            item.setComGroupTypeComprefIndex(item.getComGroupTypeComprefIndex());
        }
        if(!StringUtils.isNotBlank(item.getComTypeComprefIndex())){
            item.setComTypeComprefIndex(item.getComTypeComprefIndex());
        }
        if(!StringUtils.isNotBlank(item.getGinIndex())){
            item.setGinIndex(item.getGinIndex());
        }
        if(!StringUtils.isNotBlank(item.getCauseIndex())){
            item.setCauseIndex(item.getCauseIndex());
        }
        return item;
    }

}