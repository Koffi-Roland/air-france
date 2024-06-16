package com.afklm.repind.v6.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v6.createorupdateindividualws.CreateOrUpdateIndividualImplV6;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000442.v6.request.MarketingDataRequest;
import com.afklm.soa.stubs.w000442.v6.siccommontype.Requestor;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.IndividualInformations;
import com.afklm.soa.stubs.w000442.v6.sicmarketingtype.MaccTravelCompanion;
import com.afklm.soa.stubs.w000442.v6.sicmarketingtype.MarketingInformation;
import com.afklm.soa.stubs.w000442.v6.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v6.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v6.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v6.response.BusinessErrorCodeEnum;
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
public class CreateOrUpdateAnIndividualImplV6ErrorTest {

    private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV6ErrorTest.class);
    private static final String CHANNEL = "B2C";
    private static final String SIGNATURE = "M437794";
    private static final String SITE = "QVI";

    @Autowired
    @Qualifier("passenger_CreateUpdateIndividualService-v6Bean")
    private CreateOrUpdateIndividualImplV6 createOrUpdateIndividualImplV6;


    // Travel Companion with non-alphabetic characters for firstName
    @Test
    public void testCreateOrUpdateAnIndividualImplV6_TravelCompanionWrongFN() throws SystemException {
        CreateOrUpdateAnIndividualImplV6ErrorTest.logger.info("Test start...");
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        Requestor requestor = new Requestor();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV6ErrorTest.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV6ErrorTest.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV6ErrorTest.SIGNATURE);
        request.setRequestor(requestor);

        IndividualRequest indRequest = new IndividualRequest();
        IndividualInformations indInfo = new IndividualInformations();
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
            createOrUpdateIndividualImplV6.createIndividual(request);
            Assert.fail();
        } catch (BusinessErrorBlocBusinessException e) {
            Assert.assertEquals(BusinessErrorCodeEnum.ERROR_905, e.getFaultInfo().getBusinessError().getErrorCode());
        }
    }

    // Travel Companion with non-alphabetic characters for lastName
    @Test
    public void testCreateOrUpdateAnIndividualImplV6_TravelCompanionWrongLN() throws SystemException {
        CreateOrUpdateAnIndividualImplV6ErrorTest.logger.info("Test start...");
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        Requestor requestor = new Requestor();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV6ErrorTest.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV6ErrorTest.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV6ErrorTest.SIGNATURE);
        request.setRequestor(requestor);

        IndividualRequest indRequest = new IndividualRequest();
        IndividualInformations indInfo = new IndividualInformations();
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
            createOrUpdateIndividualImplV6.createIndividual(request);
            Assert.fail();
        } catch (BusinessErrorBlocBusinessException e) {
            Assert.assertEquals(BusinessErrorCodeEnum.ERROR_905, e.getFaultInfo().getBusinessError().getErrorCode());
        }
    }
}
