package com.afklm.repind.v7.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v7.createorupdateindividualws.CreateOrUpdateIndividualImplV7;
import com.afklm.soa.stubs.w000442.v7.request.MarketingDataRequest;
import com.afklm.soa.stubs.w000442.v7.sicmarketingtype.MaccTravelCompanion;
import com.afklm.soa.stubs.w000442.v7.sicmarketingtype.MarketingInformation;
import com.afklm.soa.stubs.w000442.v7.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v7.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v7.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000442.v7.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.IndividualInformationsV3;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplV7ErrorTest {

    private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV7ErrorTest.class);
    private static final String CHANNEL = "B2C";
    private static final String SIGNATURE = "M437794";
    private static final String SITE = "QVI";

    @Autowired
    @Qualifier("passenger_CreateUpdateIndividualService-v7Bean")
    private CreateOrUpdateIndividualImplV7 createOrUpdateIndividualImplV7;


    // Travel Companion with non-alphabetic characters for firstName
    @Test
    public void testCreateOrUpdateAnIndividualImplV7_TravelCompanionWrongFN() {
        CreateOrUpdateAnIndividualImplV7ErrorTest.logger.info("Test start...");
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV7ErrorTest.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV7ErrorTest.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV7ErrorTest.SIGNATURE);
        request.setRequestor(requestor);

        IndividualRequest indRequest = new IndividualRequest();
        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setIdentifier("400509651532");
        indInfo.setCivility("Miss");
        indRequest.setIndividualInformations(indInfo);
        request.setIndividualRequest(indRequest);

        // Set travel companion with wrong FN
        MaccTravelCompanion maccTravelCompanion = new MaccTravelCompanion();
        maccTravelCompanion.setCivility("MR");
        maccTravelCompanion.setEmail("POI@REP.COM");
        maccTravelCompanion.setDateOfBirth(new Date(0));
        maccTravelCompanion.setFirstName("VALOU !");
        maccTravelCompanion.setLastName("POIROT");

        MarketingInformation marketingInformation = new MarketingInformation();
        marketingInformation.getTravelCompanion().add(maccTravelCompanion);

        MarketingDataRequest marketingDataRequest = new MarketingDataRequest();
        marketingDataRequest.setMarketingInformation(marketingInformation);

        request.setMarketingDataRequest(marketingDataRequest);


        try {
            createOrUpdateIndividualImplV7.createIndividual(request);
            Assert.fail();
        } catch (BusinessErrorBlocBusinessException e) {
            Assert.assertEquals(BusinessErrorCodeEnum.ERROR_905, e.getFaultInfo().getBusinessError().getErrorCode());
        }
    }

    // Travel Companion with non-alphabetic characters for lastName
    @Test
    public void testCreateOrUpdateAnIndividualImplV7_TravelCompanionWrongLN() {
        CreateOrUpdateAnIndividualImplV7ErrorTest.logger.info("Test start...");
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV7ErrorTest.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV7ErrorTest.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV7ErrorTest.SIGNATURE);
        request.setRequestor(requestor);

        request.setProcess("I");

        IndividualRequest indRequest = new IndividualRequest();
        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setIdentifier("400509651532");
        indInfo.setCivility("Miss");
        indRequest.setIndividualInformations(indInfo);
        request.setIndividualRequest(indRequest);

        // Set travel companion with wrong LN
        MaccTravelCompanion maccTravelCompanion = new MaccTravelCompanion();
        maccTravelCompanion.setCivility("MR");
        maccTravelCompanion.setEmail("POI@REP.COM");
        maccTravelCompanion.setDateOfBirth(new Date(0));
        maccTravelCompanion.setFirstName("VALOU");
        maccTravelCompanion.setLastName("POIROT&");

        MarketingInformation marketingInformation = new MarketingInformation();
        marketingInformation.getTravelCompanion().add(maccTravelCompanion);

        MarketingDataRequest marketingDataRequest = new MarketingDataRequest();
        marketingDataRequest.setMarketingInformation(marketingInformation);

        request.setMarketingDataRequest(marketingDataRequest);


        try {
            createOrUpdateIndividualImplV7.createIndividual(request);
            Assert.fail();
        } catch (BusinessErrorBlocBusinessException e) {
            Assert.assertEquals(BusinessErrorCodeEnum.ERROR_905, e.getFaultInfo().getBusinessError().getErrorCode());
        }
    }
}
