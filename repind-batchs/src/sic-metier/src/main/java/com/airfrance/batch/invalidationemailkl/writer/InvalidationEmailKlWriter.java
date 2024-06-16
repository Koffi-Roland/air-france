package com.airfrance.batch.invalidationemailkl.writer;

import com.airfrance.batch.invalidationemailkl.model.InputInvalid;
import com.airfrance.batch.invalidationemailkl.service.InvalidationEmailKLSummaryService;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.service.adresse.internal.EmailDS;
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
public class InvalidationEmailKlWriter implements ItemWriter<InputInvalid> {

    @Autowired
    private EmailDS emailDS;

    @Autowired
    private InvalidationEmailKLSummaryService summaryService;

    @Override
    @Transactional
    public void write(@NotNull List<? extends InputInvalid> invalidInputs){
        if (!CollectionUtils.isEmpty(invalidInputs)) {
            invalidInputs.forEach(item -> {
                try {
                    invalidateInput(item);
                    summaryService.incrementNbSuccessEmailInvalid();
                } catch (Exception e) {
                    summaryService.incrementNbTechnicalError();
                    summaryService.addErrorMessage("#" + item + "#;" + "TECHNICAL" + "\n");
                    log.error("Exception during treatment of email - {}", item.getContactIndex());
                }
            });
        }
        else{
            log.error("The list of data is empty.");
            throw new IllegalArgumentException("The list of data is empty...");
        }
    }

    /**
     * This method takes an object, will retrieve email then look for shared emails and invalidate their status too
     * @param item InputInvalid
     * @throws JrafDomainException exception
     */
    public void invalidateInput(InputInvalid item) throws JrafDomainException {
        // step2 : retrieve email
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setEmail(item.getContactIndex().trim());

        // step3 : search emails
        List<EmailDTO> emailsDTO = emailDS.search(emailDTO.getEmail());

        if(emailsDTO == null || CollectionUtils.isEmpty(emailsDTO)){
            summaryService.incrementNbEmailNotFound();
            summaryService.addErrorMessage(emailDTO.getEmail() + ";" + "NOT_FOUND" + "\n");
            log.error("Email not found in EMAILS - {}",emailDTO.getEmail());
        }else{
            emailsDTO.forEach(
                    eDTO -> {
                        try {
                            emailDS.invalidOnEmail(eDTO, "InvEmail_SFMC_" + item.getComReturnCodeIndex());
                        } catch (JrafDomainException e) {
                            summaryService.incrementNbTechnicalError();
                            summaryService.addErrorMessage(eDTO + ";" + "TECHNICAL" + ";" + e.getMessage() + "\n");
                            log.error("[-] Exeption - update not possible for email : {} - {}", eDTO, e.getMessage());
                        }
                    }
            );
        }
    }

}