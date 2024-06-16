package com.afklm.sicwscustomeridentification.impl;

import com.afklm.sicwscustomeridentification.config.WebTestConfig;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001345.v1.BusinessException;
import com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.siccommontype.Requestor;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.request.*;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.response.Customer;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.response.IdentifyCustomerCrossReferentialResponse;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

//@Ignore
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class IdentifyCustomerCrossReferentialServiceImplForProspectTest {
	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String COMPANY = "AF";
	private static final String APP_CODE = "B2C";
	
	@Autowired
	@Qualifier("passenger_IdentifyCustomerCrossReferentialService-v1Bean")
	private IdentifyCustomerCrossReferentialServiceV1Impl identifyCustomerCrossReferentialServiceV1Impl;
	
	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_Individu_Auto() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index
		
		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Email email = new Email();
		email.setEmail("charles.gaud@gmail.com");
		searchIdentifier.setEmail(email);
		
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");	// Individual
		context.setResponseType("U");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertNotNull(response.getCustomer().get(0).getIndividual());
		assertEquals("400262355440", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}

	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_Individu_Manual() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("M");	// MANUAL 
		request.setIndex(1);			// Index de continuité
		
		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Email email = new Email();
		email.setEmail("charles.gaud@gmail.com");
		searchIdentifier.setEmail(email);
		
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");
		context.setResponseType("U");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertNotNull(response.getCustomer().get(0).getIndividual());
		assertEquals("400262355440", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}

	@Test
	// @Ignore		// REPIND-854 : IdentifyCustomerCrossRef ne doit pas renvoyer des Prospects
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_IndividuInProspect_ManualByEmail() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("M");	// MANUAL 
		request.setIndex(1);			// Index de continuité
		
		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Email email = new Email();
		email.setEmail("fyxfeng813@hotmail.com");
		searchIdentifier.setEmail(email);
		
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");
		context.setResponseType("U");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertNotNull(response.getCustomer().get(0).getIndividual());
		assertEquals("900000065931", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
		// GIN start by 90xxxxxxxxxx are PROSPECT ! 
	}

	@Test
	// @Ignore		// REPIND-854 : IdentifyCustomerCrossRef ne doit pas renvoyer des Prospects 
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_IndividuInProspect_ManualByTelecom() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("M");	// MANUAL 
		request.setIndex(1);			// Index de continuité
		
		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setPhoneNumber("0497283644");
		
		searchIdentifier.setTelecom(telecom);
		
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");
		context.setResponseType("F");	// Full response (not Unique)
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		
		boolean retour = false;
		for (Customer c : response.getCustomer()) {
			// If we find a Prospect Only
			if ("900025436742".equals(c.getIndividual().getIndividualInformations().getIndividualKey())) {
				retour = true;
			}
		}
		assertTrue(retour);
	}
	
	@Test
	@Ignore		// REPIND-854 : IdentifyCustomerCrossRef ne doit pas renvoyer des Prospects - A voir plus tard !
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_IndividuInProspect_ManualByNameAdrPost() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("M");	// MANUAL 
		request.setIndex(1);			// Index de continuité
		
		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		PostalAddress postalAddress = new PostalAddress();
		postalAddress.setCountryCode("CN");
		postalAddress.setZipCode("200041");
		searchIdentifier.setPostalAddress(postalAddress);
		
		PersonsIdentity personsIdentity = new PersonsIdentity();
		personsIdentity.setFirstName("LAMBERTUS WILHELMUS");
		personsIdentity.setLastName("JONKHEER");
		searchIdentifier.setPersonsIdentity(personsIdentity);
		
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");
		context.setResponseType("F");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertNotNull(response.getCustomer().get(0).getIndividual());
		assertEquals("900000176817", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
		// GIN start by 90xxxxxxxxxx are PROSPECT ! 
	}

}
