package com.afklm.repind.v6.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v6.createorupdateindividualws.CreateOrUpdateIndividualImplV6;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000442.v6.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v6.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v6.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v6.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v6.siccommontype.Requestor;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.IndividualInformations;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.IndividualProfil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualNoMockTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualNoMockTest.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private final String OPTION_SAMPLE = "AC";	
	private final String FB_NUMBER_SAMPLE = "002053750936";
	private final String OPTION_GIN = "GIN";

	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v6Bean")
	private CreateOrUpdateIndividualImplV6 createOrUpdateIndividualImplV6;

	@Test
	public void testCreateOrUpdateIndividualImplV6Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		
		IndividualRequest indRequest = new IndividualRequest();
		// indRequest.setCivilian("MR");
		
		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("PHILIPPOT"); 
		indInfo.setFirstNameSC("PATRICK");
		
		indRequest.setIndividualInformations(indInfo);
		
		request.setIndividualRequest(indRequest);
		

		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV6.createIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		
		logger.info("GIN created : " + response.getGin());
		
		logger.info("Test stop.");
	}
	
	// BUG FIDELIO
	
	@Test
	public void testCreateOrUpdateIndividualImplV6_FidelioTest() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("FID");		// Pour ne pas avoir a mettre la Birthdate
		requestor.setToken("WSSiC2");				// Pour ne pas avoir a faire la recherche avant

		request.setRequestor(requestor);

		
		IndividualRequest indRequest = new IndividualRequest();
		// indRequest.setCivilian("MR");
		
		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("AKPAN"); 
		indInfo.setFirstNameSC("CHRISTIAN");
		indInfo.setLanguageCode("UK");
		
		indRequest.setIndividualInformations(indInfo);

		
		IndividualProfil individualProfil = new IndividualProfil();
		individualProfil.setLanguageCode("KO");
		
		indRequest.setIndividualProfil(individualProfil);

		
		request.setIndividualRequest(indRequest);
		
		request.setUpdateCommunicationPrefMode("U");
		request.setUpdatePrefilledNumbersMode("U");
		request.setNewsletterMediaSending("T");
		
		
		

		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV6.createIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		
		logger.info("GIN created : " + response.getGin());
		
		logger.info("Test stop.");
	}

}
