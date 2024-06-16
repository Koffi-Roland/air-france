package com.afklm.repind.msv.graphql.bff.example.controller;

import com.afklm.repind.common.enums.SignatureEnum;
import com.afklm.repind.msv.graphql.bff.example.client.EmailClient;
import com.afklm.repind.msv.graphql.bff.example.client.LastActivityClient;
import com.afklm.repind.msv.graphql.bff.example.exception.ServiceException;
import com.afklm.repind.msv.graphql.bff.example.model.Email;
import com.afklm.repind.msv.graphql.bff.example.model.EmailResponse;
import com.afklm.repind.msv.graphql.bff.example.model.LastActivity;
import com.afklm.repind.msv.graphql.bff.example.model.Signature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
public class GraphQlControllerTest {

    private static final Integer VERSION = 1;
    private static final String CODE_MEDIUM = "D";
    private static final String STATUS_MEDIUM = "V";
    private static final String EMAIL = "ri@airfrance.fr";
    private static final String AUTORISATION_MAILING = "N";
    private static final String SIGNATURE_CREATION = "ISI";
    private static final String SITE_CREATION = "WEB";
    private static final String GIN = "110000038701";

    @Mock
    private LastActivityClient lastActivityClient;
    @Mock
    private EmailClient emailClient;
    @InjectMocks
    private GraphQlController graphQlController;

    @Test
    @DisplayName("Unit test on get emails by gin")
    public void getEmailsByGinTest() throws ServiceException, IOException, ParseException
    {
        List<EmailResponse> emailResponses = new ArrayList<>();
        List<EmailResponse> emailResponsesController = new ArrayList<>();
        emailResponses.add(this.buildMockEmailResponse());
        when(emailClient.getEmailsByGin(GIN)).thenReturn(emailResponses);
        emailResponsesController = this.graphQlController.getEmailsByGin(GIN);
        assertEquals(emailResponsesController,emailResponses);
        assertEquals(emailResponsesController.get(0).getEmail(),emailResponses.get(0).getEmail());
        assertEquals(emailResponsesController.get(0).getSignature(),emailResponses.get(0).getSignature());


    }

    @Test
    @DisplayName("Unit test on get last activity by gin")
    public void getLastActivityByGin() throws ServiceException, IOException, ParseException
    {
       LastActivity lastActivity = this.buildMockLastActivity();
        when(lastActivityClient.getLastActivityByGin(GIN)).thenReturn(lastActivity);
        LastActivity lastActivityResponse = this.graphQlController.getLastActivityByGin(GIN);
        assertEquals(lastActivityResponse,lastActivity);
        assertAll(
                () -> assertEquals(lastActivityResponse.getGin(), lastActivity.getGin()),
                () -> assertEquals(lastActivityResponse.getSourceModification(), lastActivity.getSourceModification()),
                () -> assertEquals(lastActivityResponse.getSignatureModification(), lastActivity.getSignatureModification()),
                () -> assertEquals(lastActivityResponse.getSiteModification(), lastActivity.getSiteModification())
        );
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

    private List<Signature> buildMockSignature() throws ParseException
    {
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

    private EmailResponse buildMockEmailResponse() throws ParseException
    {
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setEmail(this.buildMockEmail());
        emailResponse.setSignature(this.buildMockSignature());
        return emailResponse;
    }

    private LastActivity buildMockLastActivity()
    {
        Date date = Date.from(LocalDate.of(2013, 12, 6).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return LastActivity.builder()
                .gin("110000038701")
                .sourceModification("Individuals_all")
                .siteModification("KLM")
                .signatureModification("REPIND/IHM")
                .dateModification(date)
                .build();
    }


}
