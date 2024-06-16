package com.afklm.repind.v7.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v7.createorupdateindividualws.CreateOrUpdateIndividualImplV7;
import com.afklm.soa.stubs.w000442.v7.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v7.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v7.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.IndividualInformationsV3;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.service.consent.internal.ConsentDS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplV7ForConsentTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV7Test.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "TESTRI";
	private static final String SITE = "QVI";
	
	/** the Entity Manager*/
	@PersistenceContext(unitName="entityManagerFactoryRepind")
	private EntityManager entityManager;

	@MockBean
	private ConsentDS consentDSMock;

	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v7Bean")
	private CreateOrUpdateIndividualImplV7 createOrUpdateIndividualImplV7;

	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividualDefaultConsentsOK() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start...");
		
		createOrUpdateIndividualImplV7.setConsentDS(consentDSMock);
		Mockito.doNothing().when(consentDSMock).createDefaultConsents(Mockito.anyString(), Mockito.anyString());

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("PHILIPPOT"); 
		indInfo.setFirstNameSC("PATRICK");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

		logger.info("GIN created : " + response.getGin());

		logger.info("Test stop.");
	}
	
	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividualDefaultConsentsKO() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start...");
		
		createOrUpdateIndividualImplV7.setConsentDS(consentDSMock);
		Mockito.doThrow(new JrafDomainException("Error during creation of default consents")).when(consentDSMock).createDefaultConsents(Mockito.anyString(), Mockito.anyString());

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("PHILIPPOT"); 
		indInfo.setFirstNameSC("PATRICK");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

		logger.info("GIN created : " + response.getGin());

		logger.info("Test stop.");
	}


}
