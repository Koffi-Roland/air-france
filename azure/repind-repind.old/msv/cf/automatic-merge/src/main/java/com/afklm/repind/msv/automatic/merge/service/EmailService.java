package com.afklm.repind.msv.automatic.merge.service;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.msv.automatic.merge.model.StatusEnum;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * Title : EmailService.java
 * </p>
 * Service Implementation to manage Email
 * <p>
 * Copyright : Copyright (c) 2022
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */

@Service
public class EmailService {

    /**
     * Soft delete an email
     * @param email An instance of Email
     * @param dateModification Date of modification
     * @param signature The application signature that makes the modification
     * @param site the site code that makes the modification
     */
    public void softDeleteEmail(EmailEntity email, Date dateModification, String signature, String site) {
        email.setStatutMedium(StatusEnum.HISTORIZED.getName());
        email.setDateModification(dateModification);
        email.setSignatureModification(signature);
        email.setSiteModification(site);
    }
}
