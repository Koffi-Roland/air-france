package com.afklm.repind.msv.provide.contact.data.unittests;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.enums.SignatureEnum;
import com.afklm.repind.msv.provide.contact.data.transform.EmailsTransform;
import com.afklm.soa.stubs.r000347.v1.model.Email;
import com.afklm.soa.stubs.r000347.v1.model.EmailResponse;
import com.afklm.soa.stubs.r000347.v1.model.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class EmailsTransformTest {

    @InjectMocks
    private EmailsTransform emailsTransform;
    private final String GIN_EMAIL = "800200037835";

    private final String ain = "25521403";
    private final Integer version = 1;
    private final String codeMedium = "D";
    private final String statutMedium = "V";
    private final String email = "gattokiller@nate.com";
    private final String autorisationMailing = "N";
    private final String signatureModification = "ISI";
    private final String siteModification = "WEB";
    private final Date dateModification = new SimpleDateFormat("yyyy-MM-dd").parse("2009-12-01");
    private final String signatureCreation = "ISI";
    private final String siteCreation = "WEB";
    private final Date dateCreation = new SimpleDateFormat("yyyy-MM-dd").parse("2009-12-01");
    private final Integer cleTemp = 6093034;

    public EmailsTransformTest() throws ParseException {
    }

    @Test
    void boToResponseTest() throws ParseException{
        List<EmailResponse> emailResponses = emailsTransform.boToResponse(buildMockedEmailEntities());
        Email email1 = buildMockedEmail();
        List<Signature> signatures = buildMockedSignatures();

        EmailResponse emailResponse = emailResponses.get(0);
        assertEquals(email1,emailResponse.getEmail());
        assertEquals(signatures,emailResponse.getSignature());
    }

    @Test
    void setEmailsResponseEmailTest() throws ParseException{
        EmailResponse emailResponseResult = buildMockedEmailResponse().get(0);
        EmailEntity emailBo = buildMockedEmailEntities().get(0);
        EmailResponse emailResponse = new EmailResponse();
        emailsTransform.setEmailsResponseEmail(emailBo,emailResponse);

        assertEquals(emailResponseResult,emailResponse);
    }

    @Test
    void setEmailsResponseSignatureTest() throws ParseException{
        EmailResponse emailResponseResult = buildMockedEmailResponse().get(1);
        EmailEntity emailBo = buildMockedEmailEntities().get(0);
        EmailResponse emailResponse = new EmailResponse();
        emailsTransform.setEmailsResponseSignature(emailBo,emailResponse);

        assertEquals(emailResponseResult,emailResponse);
    }

    private List<EmailEntity> buildMockedEmailEntities() throws ParseException{
        List<EmailEntity> emailEntities = new ArrayList<>();
        Individu individu = new Individu();
        individu.setGin(GIN_EMAIL);

        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setAin(ain);
        emailEntity.setVersion(version);
        emailEntity.setCodeMedium(codeMedium);
        emailEntity.setStatutMedium(statutMedium);
        emailEntity.setEmail(email);
        emailEntity.setAutorisationMailing(autorisationMailing);
        emailEntity.setSignatureModification(signatureModification);
        emailEntity.setSiteModification(siteModification);
        emailEntity.setDateModification(dateModification);
        emailEntity.setSignatureCreation(signatureCreation);
        emailEntity.setSiteCreation(siteCreation);
        emailEntity.setDateCreation(dateCreation);
        emailEntity.setCleTemp(cleTemp);
        emailEntity.setIndividu(individu);
        emailEntities.add(emailEntity);

        return emailEntities;
    }

    private Email buildMockedEmail() throws ParseException{
        Email email1 = new Email();

        email1.setEmail(email);
        email1.setEmailOptin(autorisationMailing);
        email1.setVersion(version);
        email1.setMediumCode(codeMedium);
        email1.setMediumStatus(statutMedium);

        return email1;
    }

    private List<Signature> buildMockedSignatures() throws ParseException{
        List<Signature> signatures = new ArrayList<>();

        Signature signatureCreation1 = new Signature();
        signatureCreation1.setSignature(signatureCreation);
        signatureCreation1.setSignatureSite(siteCreation);
        signatureCreation1.setSignatureType(SignatureEnum.CREATION.toString());
        signatureCreation1.setDate(dateCreation.toInstant().atOffset(ZoneOffset.UTC));

        Signature signatureModification1 = new Signature();
        signatureModification1.setSignature(signatureModification);
        signatureModification1.setSignatureSite(siteModification);
        signatureModification1.setSignatureType(SignatureEnum.MODIFICATION.toString());
        signatureModification1.setDate(dateModification.toInstant().atOffset(ZoneOffset.UTC));

        signatures.add(signatureCreation1);
        signatures.add(signatureModification1);

        return signatures;
    }

    private List<EmailResponse> buildMockedEmailResponse() throws ParseException{
        List<EmailResponse> emailResponses = new ArrayList<>();

        EmailResponse emailResponseEmail = new EmailResponse();
        emailResponseEmail.setEmail(buildMockedEmail());
        emailResponses.add(emailResponseEmail);

        EmailResponse emailResponseSignature = new EmailResponse();
        emailResponseSignature.setSignature(buildMockedSignatures());
        emailResponses.add(emailResponseSignature);

        return emailResponses;
    }
}
