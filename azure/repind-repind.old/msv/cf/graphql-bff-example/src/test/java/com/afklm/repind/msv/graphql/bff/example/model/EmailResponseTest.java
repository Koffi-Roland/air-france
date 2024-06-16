package com.afklm.repind.msv.graphql.bff.example.model;

import com.afklm.repind.common.enums.SignatureEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test email response
 */
@ExtendWith(SpringExtension.class)
public class EmailResponseTest {

    private static final Integer VERSION = 1;
    private static final String CODE_MEDIUM = "D";
    private static final String STATUS_MEDIUM = "V";
    private static final String EMAIL = "ri@airfrance.fr";
    private static final String AUTORISATION_MAILING = "N";
    private static final String SIGNATURE_CREATION = "ISI";
    private static final String SITE_CREATION = "WEB";

    @Test
    @DisplayName("Unit test on email response")
    public void setEmailResponseTest() throws ParseException {
       EmailResponse emailResponse = this.buildMockEmailResponse();
       Email email = this.buildMockEmail();
       List<Signature> signatures = this.buildMockSignature();
       assertNotNull(email);
       assertNotNull(signatures);
       assertEquals(emailResponse.getEmail().getEmail(),email.getEmail());
       assertEquals(emailResponse.getSignature().get(0).getSignature(),signatures.get(0).getSignature());

    }

    private Email buildMockEmail()
    {
        Email email = new Email();
        email.setEmail(EMAIL);
        email.setEmailOptin(AUTORISATION_MAILING);
        email.setVersion(VERSION);
        email.setMediumCode(CODE_MEDIUM);
        email.setMediumStatus(STATUS_MEDIUM);

        return email;
    }

    private List<Signature> buildMockSignature() throws ParseException {
        final Date dateCreation = new SimpleDateFormat("yyyy-MM-dd").parse("2009-12-01");

        List<Signature> signatures = new ArrayList<>();
        Signature signatureCreation = new Signature();
        signatureCreation.setSignature(SIGNATURE_CREATION);
        signatureCreation.setSignatureSite(SITE_CREATION);
        signatureCreation.setSignatureType(SignatureEnum.CREATION.toString());
        signatureCreation.setDate(dateCreation.toInstant().atOffset(ZoneOffset.UTC));
        signatures.add(signatureCreation);

        return signatures;
    }

    private EmailResponse buildMockEmailResponse() throws ParseException {
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setEmail(this.buildMockEmail());
        emailResponse.setSignature(this.buildMockSignature());
        return emailResponse;
    }

}
