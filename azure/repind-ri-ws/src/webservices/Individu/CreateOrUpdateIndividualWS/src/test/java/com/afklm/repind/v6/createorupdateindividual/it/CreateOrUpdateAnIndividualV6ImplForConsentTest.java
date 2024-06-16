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

import java.util.Date;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualV6ImplForConsentTest { // extends CreateOrUpdateIndividualImplV6{

	private static Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualV6ImplTest.class);

	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v6Bean")
	private CreateOrUpdateIndividualImplV6 createOrUpdateIndividualImplV6;
	
	@MockBean
	private ConsentDS consentDSMock;

	private static final String CHANNEL = "B2C";
	private static final String SITE = "QVI";
	private static final String SIGNATURE = "TESTRI";

	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividualDefaultConsentsOK() throws BusinessErrorBlocBusinessException, SystemException, JrafDomainException {

		logger.info("Test start...");
		
		createOrUpdateIndividualImplV6.setConsentDS(consentDSMock);
		Mockito.doNothing().when(consentDSMock).createDefaultConsents(Mockito.anyString(), Mockito.anyString());
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();
		
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
	
	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividualDefaultConsentsKO() throws BusinessErrorBlocBusinessException, SystemException, JrafDomainException {

		logger.info("Test start...");
		
		createOrUpdateIndividualImplV6.setConsentDS(consentDSMock);
		Mockito.doThrow(new JrafDomainException("Error during creation of default consents")).when(consentDSMock).createDefaultConsents(Mockito.anyString(), Mockito.anyString());
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();
		
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
}
