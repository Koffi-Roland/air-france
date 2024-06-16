package com.afklm.sicwscustomeridentification.impl;

import com.afklm.sicwscustomeridentification.config.WebTestConfig;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001345.v1.BusinessException;
import com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.siccommontype.Requestor;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.request.Context;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.request.Email;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.request.IdentifyCustomerCrossReferentialRequest;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.request.SearchIdentifier;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.response.Customer;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.response.IdentifyCustomerCrossReferentialResponse;
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
public class IdentifyCustomerCrossReferentialServiceImplForTravelerTest {
	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String COMPANY = "AF";
	private static final String APP_CODE = "B2C";
	
	@Autowired
	@Qualifier("passenger_IdentifyCustomerCrossReferentialService-v1Bean")
	private IdentifyCustomerCrossReferentialServiceV1Impl identifyCustomerCrossReferentialServiceV1Impl;
	
	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_TravelerOnly() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("M"); 	// MANUEL
		request.setIndex(1);			// Continuous index
		
		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Email email = new Email();
		email.setEmail("emergency@intektravel.com");
		searchIdentifier.setEmail(email);
		
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");	// Individual
		context.setResponseType("F");	// NON Unique result
		
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
		assertEquals(4, response.getCustomer().size());
		
		boolean retour = false;
		for (Customer custo : response.getCustomer()) {
			if ("910000001760".equals(custo.getIndividual().getIndividualInformations().getIndividualKey())) {
				retour = true;
			}
		}
		assertTrue(retour);		
	}


	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_TravelerOnly2() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("M"); 	// MANUEL
		request.setIndex(1);			// Continuous index
		
		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Email email = new Email();
		email.setEmail("denbol75@gmail.com");
		searchIdentifier.setEmail(email);
		
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");	// Individual
		context.setResponseType("F");	// NON Unique result
		
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
		assertEquals(5, response.getCustomer().size());
		
		boolean retour = false;
		for (Customer custo : response.getCustomer()) {
			if ("910000001793".equals(custo.getIndividual().getIndividualInformations().getIndividualKey())) {
				retour = true;
			}
		}
		assertTrue(retour);		
	}

}
