package com.afklm.repind.v7.createorupdateindividual;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v7.createorupdateindividualws.CreateOrUpdateIndividualImplV7;
import com.afklm.soa.stubs.w000442.v7.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v7.request.EmailRequest;
import com.afklm.soa.stubs.w000442.v7.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v7.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.Email;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.IndividualInformationsV3;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.entity.individu.Individu;
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
public class CreateOrUpdateV7ForGDPRIntegrationTest {
	
	private static final Log logger = LogFactory.getLog(CreateOrUpdateV7ForGDPRIntegrationTest.class);
	
	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v7Bean")
	private CreateOrUpdateIndividualImplV7 createOrUpdateIndividualImplV7;
	
	@Autowired
	private IndividuRepository individuRepository;
	
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
		infos.setIdentifier("400424668522");
		req.setIndividualInformations(infos);
		return req;
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_updateForgotten() throws JrafDaoException {
		logger.info("START TEST update forgotten individual ...");
		
		// Prepare request
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setRequestor(initRequestor());
		request.setIndividualRequest(initIndivu());
		
		EmailRequest emailReq = new EmailRequest();
		Email email = new Email();
		email.setEmail("random19998522@rdm.tst");
		email.setMediumCode("D");
		email.setMediumStatus("V");
		emailReq.setEmail(email);
		request.getEmailRequest().add(emailReq);
		
		// Init database
		Individu indFromDB = individuRepository.findBySgin("400424668522");
		if (indFromDB != null) {
			indFromDB.setStatutIndividu("F");
			individuRepository.saveAndFlush(indFromDB);
		}
		
		// Execute test
		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
			// Individual must not be found as he's in status 'F'
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException ex) {
			Assert.assertTrue(ex.getFaultInfo().getBusinessError().getErrorDetail().contains("not exist"));
		}
		
		
		logger.info("END TEST update forgotten individual ...");
	}
}
