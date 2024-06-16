package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.IndividualInformationsV3;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplV8ForHiddenCrisisTest {
	
	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV8ForHiddenCrisisTest.class);
	
	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v8Bean")
	private CreateOrUpdateIndividualImplV8 createOrUpdateIndividualImplV8;
	
	private RequestorV2 initRequestor() {
		RequestorV2 req = new RequestorV2();
		req.setApplicationCode("ISI");
		req.setChannel("B2C");
		req.setSignature("IT442v8");
		req.setSite("QVI");
		return req;
	}
	
	private IndividualRequest initIndivu() {
		IndividualRequest req = new IndividualRequest();
		IndividualInformationsV3 infos = new IndividualInformationsV3();
		infos.setIdentifier("400272037921");
		req.setIndividualInformations(infos);
		return req;
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_UpdateHiddenIndividual() throws JrafDaoException, BusinessErrorBlocBusinessException {
		logger.info("START TEST update individual to Hidden...");
		
		// Prepare request
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setRequestor(initRequestor());
		request.setIndividualRequest(initIndivu());
		request.setProcess("H"); 		// HIDDEN (Crisis Mode)
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getInformationResponse());
		
		logger.info("END TEST update individual to Hidden");
	}
}
