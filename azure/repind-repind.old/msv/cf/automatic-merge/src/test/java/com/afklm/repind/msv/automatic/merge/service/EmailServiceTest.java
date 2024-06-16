package com.afklm.repind.msv.automatic.merge.service;

import com.afklm.repind.common.entity.contact.EmailEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
public class EmailServiceTest {

    @InjectMocks
    EmailService instance;

    @Test
    public void softDeleteEmailSuccessfull() throws ParseException {
        EmailEntity email = new EmailEntity();
        String dateString = "01-02-2000";
        Date dateModification = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
        String signature = "unitTest";
        String site = "siteUnitTest";
        instance.softDeleteEmail(email,dateModification,signature,site);
        assertEquals(dateModification.toString() , email.getDateModification().toString());
        assertEquals(signature , email.getSignatureModification());
        assertEquals(site , email.getSiteModification());
    }
}
