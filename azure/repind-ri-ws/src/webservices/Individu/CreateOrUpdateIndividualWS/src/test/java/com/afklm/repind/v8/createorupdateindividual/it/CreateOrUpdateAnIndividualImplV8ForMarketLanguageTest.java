package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.ComunicationPreferencesRequest;
import com.afklm.soa.stubs.w000442.v8.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8.siccommonenum.LanguageCodesEnum;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.siccommontype.Signature;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.CommunicationPreferences;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.IndividualInformationsV3;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.MarketLanguage;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplV8ForMarketLanguageTest {

    @Autowired
    private CreateOrUpdateIndividualImplV8 createOrUpdateIndividualImplV8;

    @Autowired
    private CommunicationPreferencesRepository communicationPreferencesRepository;

    private RequestorV2 initRequestor() {
        RequestorV2 req = new RequestorV2();
        req.setContext("test");
        req.setApplicationCode("RPD");
        req.setChannel("DS");
        req.setSignature("signatureTest");
        req.setSite("QVI");
        return req;
    }

    private IndividualRequest initIndivu() {
        IndividualRequest req = new IndividualRequest();
        IndividualInformationsV3 infos = new IndividualInformationsV3();
        infos.setIdentifier("400476875771");
        req.setIndividualInformations(infos);
        return req;
    }

    private List<MarketLanguage> initMarketLanguage() {
        List<MarketLanguage> marketLanguages = new ArrayList<>();
        MarketLanguage marketLanguage = new MarketLanguage();
        marketLanguage.setMarket("BR");
        marketLanguage.setLanguage(LanguageCodesEnum.PT);
        marketLanguage.setOptIn("Y");

        Signature signatureModif = new Signature();
        signatureModif.setSignatureType("M");
        signatureModif.setSignatureSite("TestDaily2");
        signatureModif.setSignature("klm.com");

        Signature signatureCreate = new Signature();
        signatureCreate.setSignatureType("C");
        signatureCreate.setSignatureSite("TestDaily2");
        signatureCreate.setSignature("klm.com");

        marketLanguage.getSignature().add(signatureCreate);
        marketLanguage.getSignature().add(signatureModif);

        marketLanguages.add(marketLanguage);
        return marketLanguages;
    }

    private List<ComunicationPreferencesRequest> initComunicationPreferencesRequest() {
        List<ComunicationPreferencesRequest> comunicationPreferencesRequests = new ArrayList<>();
        ComunicationPreferencesRequest comunicationPreferencesRequest = new ComunicationPreferencesRequest();
        List<MarketLanguage> marketLanguages = initMarketLanguage();
        CommunicationPreferences comunicationPreferences = new CommunicationPreferences();
        comunicationPreferences.setDomain("S");
        comunicationPreferences.setCommunicationGroupeType("N");
        comunicationPreferences.setCommunicationType("KL");
        comunicationPreferences.setOptIn("Y");
        comunicationPreferences.setDateOfConsent(new Date());
        comunicationPreferences.setSubscriptionChannel("ebt");
        comunicationPreferences.getMarketLanguage().addAll(marketLanguages);
        comunicationPreferencesRequest.setCommunicationPreferences(comunicationPreferences);
        comunicationPreferencesRequests.add(comunicationPreferencesRequest);

        return comunicationPreferencesRequests;
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateOrUpdateIndividual_addCommunicationsPreferences() throws BusinessErrorBlocBusinessException {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        RequestorV2 requestorV2 = initRequestor();
        IndividualRequest individualRequest = initIndivu();
        List<ComunicationPreferencesRequest> comunicationPreferencesRequest = initComunicationPreferencesRequest();

        request.setRequestor(requestorV2);
        request.setIndividualRequest(individualRequest);
        request.getComunicationPreferencesRequest().addAll(comunicationPreferencesRequest);

        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        List<com.airfrance.repind.entity.individu.CommunicationPreferences> communicationPreferences = communicationPreferencesRepository.findByGin("400476875771");
        com.airfrance.repind.entity.individu.CommunicationPreferences firstElement = communicationPreferences.get(0);

        assertEquals("TestDaily2", firstElement.getMarketLanguage().iterator().next().getCreationSite());
    }
}
