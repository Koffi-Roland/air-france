package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.ComunicationPreferencesRequest;
import com.afklm.soa.stubs.w000442.v8.request.EmailRequest;
import com.afklm.soa.stubs.w000442.v8.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8.request.PostalAddressRequest;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplV8ForMarketLanguageProspectTest {


    @Autowired
    private CreateOrUpdateIndividualImplV8 createOrUpdateIndividualImplV8;

    private RequestorV2 initRequestor() {
        RequestorV2 req = new RequestorV2();
        req.setChannel("CAPI");
        req.setMatricule("T165553");
        req.setContext("B2C_HOME_PAGE");
        req.setManagingCompany("AF");
        req.setIpAddress("10.100.73.80");
        req.setApplicationCode("B2C");
        req.setSignature("signatureTest");
        req.setSite("QVI");
        return req;
    }

    private List<EmailRequest> initEmailRequest() {
        List<EmailRequest> emailRequestList = new ArrayList<>();
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();
        email.setEmail("tiwtiw-ext@airfrance.fr");
        email.setMediumStatus("V");
        email.setMediumCode("D");
        emailRequest.setEmail(email);
        emailRequestList.add(emailRequest);
        return emailRequestList;
    }

    private List<PostalAddressRequest> initPostalAddressRequest() {
        List<PostalAddressRequest> postalAddressRequestList = new ArrayList<>();
        PostalAddressRequest postalAddressRequest = new PostalAddressRequest();

        PostalAddressProperties postalAddressProperties = new PostalAddressProperties();
        postalAddressProperties.setVersion("1");
        postalAddressProperties.setMediumCode("D");
        postalAddressProperties.setMediumStatus("V");

        PostalAddressContent postalAddressContent = new PostalAddressContent();
        postalAddressContent.setNumberAndStreet("Route 66");
        postalAddressContent.setCity("noWhere");
        postalAddressContent.setCountryCode("US");

        postalAddressRequest.setPostalAddressProperties(postalAddressProperties);
        postalAddressRequest.setPostalAddressContent(postalAddressContent);

        postalAddressRequestList.add(postalAddressRequest);

        return postalAddressRequestList;
    }

    private IndividualRequest initInd() {
        IndividualRequest req = new IndividualRequest();
        IndividualInformationsV3 infos = new IndividualInformationsV3();
        infos.setIdentifier("900043813136");
        req.setIndividualInformations(infos);
        return req;
    }

    private List<ComunicationPreferencesRequest> initComunicationPreferencesRequest() {
        List<ComunicationPreferencesRequest> comunicationPreferencesRequests = new ArrayList<>();
        ComunicationPreferencesRequest comunicationPreferencesRequest = new ComunicationPreferencesRequest();
        CommunicationPreferences comunicationPreferences = new CommunicationPreferences();
        comunicationPreferences.setDomain("P");
        comunicationPreferences.setCommunicationGroupeType("S");
        comunicationPreferences.setCommunicationType("RECO");
        comunicationPreferences.setOptIn("N");
        comunicationPreferences.setDateOfConsent(new Date());
        comunicationPreferences.setSubscriptionChannel("B2C Home Page");
        comunicationPreferencesRequest.setCommunicationPreferences(comunicationPreferences);
        comunicationPreferencesRequests.add(comunicationPreferencesRequest);

        return comunicationPreferencesRequests;
    }

    @Test
    public void testCreateOrUpdateIndividual_ProspectWithoutMarketLanguage() throws BusinessErrorBlocBusinessException {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        request.setProcess("W");
        RequestorV2 requestorV2 = initRequestor();
        List<EmailRequest> emailRequestList = initEmailRequest();
        List<PostalAddressRequest> postalAddressRequestList = initPostalAddressRequest();
        IndividualRequest individualRequest = initInd();
        List<ComunicationPreferencesRequest> comunicationPreferencesRequest = initComunicationPreferencesRequest();

        request.setRequestor(requestorV2);
        request.setIndividualRequest(individualRequest);
        request.getComunicationPreferencesRequest().addAll(comunicationPreferencesRequest);
        request.getEmailRequest().addAll(emailRequestList);
        request.getPostalAddressRequest().addAll(postalAddressRequestList);
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
        assertTrue(response.isSuccess());
    }

}
