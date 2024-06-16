package com.afklm.sicwscustomeridentification.impl;


import com.afklm.sicwscustomeridentification.config.WebTestConfig;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001345.v1.BusinessException;
import com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.siccommontype.Requestor;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.request.*;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.response.IdentifyCustomerCrossReferentialResponse;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.entity.individu.Individu;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

//@Ignore
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class IdentifyCustomerCrossReferentialServiceImpTest {
	
	@Autowired
	@Qualifier("passenger_IdentifyCustomerCrossReferentialService-v1Bean")
	private IdentifyCustomerCrossReferentialServiceV1Impl identifyCustomerCrossReferentialServiceV1Impl;
	
	@Autowired
	private IndividuRepository individuRepository;
	
	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_Performance() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index

		ProvideIdentifier pi = new  ProvideIdentifier();
		pi.setIdentificationType("FP");
		pi.setIdentificationValue("1037154424");
		request.setProvideIdentifier(pi);
		
		Context context = new Context();
		context.setTypeOfSearch("I");	// Individual
		context.setResponseType("U");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("IVS");
		requestor.setMatricule("T412211");
		requestor.setSignature("T412211");
		requestor.setSite("TLS");
		requestor.setManagingCompany("AF");
		requestor.setOfficeId("OFFICE");
		requestor.setApplicationCode("DRE");

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertNotNull(response.getCustomer().get(0).getIndividual());
		assertEquals("400026205304", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_GDPR() throws BusinessException, SystemException, JrafDaoException {

		// Init database
		Individu indFromDB = individuRepository.findBySgin("400424668522");
		if (indFromDB != null) {
			indFromDB.setStatutIndividu("F");
			individuRepository.saveAndFlush(indFromDB);
		}
		
		// Prepare request
		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index

		ProvideIdentifier pi = new  ProvideIdentifier();
		pi.setIdentificationType("FP");
		pi.setIdentificationValue("1116447134");
		request.setProvideIdentifier(pi);
		
		Context context = new Context();
		context.setTypeOfSearch("I");	// Individual
		context.setResponseType("U");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setMatricule("W001345");
		requestor.setSignature("IntegTest");
		requestor.setSite("QVI");
		requestor.setManagingCompany("AF");
		requestor.setOfficeId("OFFICE");
		requestor.setApplicationCode("ISI");

		context.setRequestor(requestor);
		request.setContext(context);

		// Execute test
		try {
			IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);	
			// Individual must not be found as status is 'F'
			Assert.fail();
			
		} catch (BusinessException be) {
			Assert.assertTrue(be.getFaultInfo().getFaultDescription().contains("NOT FOUND"));
		}
	}


	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_500msRCT() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index

		ProvideIdentifier pi = new  ProvideIdentifier();
		pi.setIdentificationType("GIN");
		pi.setIdentificationValue("110001017463");
		request.setProvideIdentifier(pi);
		
		Context context = new Context();
		context.setTypeOfSearch("I");	// Individual and Firm
		context.setResponseType("U");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setMatricule("T412211");
		requestor.setSignature("T412211");
		requestor.setSite("QVI");
		requestor.setApplicationCode("FID");

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertNotNull(response.getCustomer().get(0).getIndividual());
		assertEquals("110001017463", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}

	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_800msRCT() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index

		ProvideIdentifier pi = new  ProvideIdentifier();
		pi.setIdentificationType("GIN");
		pi.setIdentificationValue("800007953403");
		request.setProvideIdentifier(pi);
		
		Context context = new Context();
		context.setTypeOfSearch("IF");	// Individual and Firm
		context.setResponseType("U");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setMatricule("T412211");
		requestor.setSignature("T412211");
		requestor.setSite("QVI");
		requestor.setApplicationCode("FID");

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertNotNull(response.getCustomer().get(0).getIndividual());
		assertEquals("800007953403", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}

	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_870msRCT() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index

		ProvideIdentifier pi = new  ProvideIdentifier();
		pi.setIdentificationType("GIN");
		pi.setIdentificationValue("400208854075");
		request.setProvideIdentifier(pi);
		
		Context context = new Context();
		context.setTypeOfSearch("I");	// Individual and Firm
		context.setResponseType("U");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setMatricule("T412211");
		requestor.setSignature("T412211");
		requestor.setSite("QVI");
		requestor.setApplicationCode("FID");

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertNotNull(response.getCustomer().get(0).getIndividual());
		assertEquals("400208854075", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}

	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 10 secondes
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_900msRCT() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index

		ProvideIdentifier pi = new  ProvideIdentifier();
		pi.setIdentificationType("GIN");
		pi.setIdentificationValue("800007953403");
		request.setProvideIdentifier(pi);
		
		Context context = new Context();
		context.setTypeOfSearch("IF");	// Individual and Firm
		context.setResponseType("U");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setMatricule("T412211");
		requestor.setSignature("T412211");
		requestor.setSite("QVI");
		requestor.setApplicationCode("FID");

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertNotNull(response.getCustomer().get(0).getIndividual());
		assertEquals("800007953403", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}

	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_600msRCT() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index

		ProvideIdentifier pi = new  ProvideIdentifier();
		pi.setIdentificationType("GIN");
		pi.setIdentificationValue("110000479163");
		request.setProvideIdentifier(pi);
		
		Context context = new Context();
		context.setTypeOfSearch("I");	// Individual and Firm
		context.setResponseType("U");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setMatricule("T412211");
		requestor.setSignature("T412211");
		requestor.setSite("QVI");
		requestor.setApplicationCode("FID");

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertNotNull(response.getCustomer().get(0).getIndividual());
		assertEquals("110000479163", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}

	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_700msRCT() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index

		ProvideIdentifier pi = new  ProvideIdentifier();
		pi.setIdentificationType("GIN");
		pi.setIdentificationValue("800024455273");
		request.setProvideIdentifier(pi);
		
		Context context = new Context();
		context.setTypeOfSearch("I");	// Individual and Firm
		context.setResponseType("U");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setMatricule("T412211");
		requestor.setSignature("T412211");
		requestor.setSite("QVI");
		requestor.setApplicationCode("FID");

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertNotNull(response.getCustomer().get(0).getIndividual());
		assertEquals("800024455273", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}

	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_ByFB_NotFound() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index

		ProvideIdentifier pi = new  ProvideIdentifier();
		pi.setIdentificationType("FP");
		pi.setIdentificationValue("111111111111");
		request.setProvideIdentifier(pi);
		
		Context context = new Context();
		context.setTypeOfSearch("I");	// Individual
		context.setResponseType("F");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setMatricule("T412211");
		requestor.setSignature("T412211");
		requestor.setSite("TLS");
		requestor.setApplicationCode("SVI");

		context.setRequestor(requestor);
		request.setContext(context);

		try {
			IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);
		
			Assert.fail("On doit avoir une Business Exception : NOT FOUND");
		
		} catch (BusinessException be) {
			assertNotNull(be);	
			assertNotNull(be.getFaultInfo());
			
			assertNotNull(be.getFaultInfo().getErrorCode());
			assertEquals("001", be.getFaultInfo().getErrorCode());
			
			assertNotNull(be.getFaultInfo().getFaultDescription());
			assertEquals("NOT FOUND", be.getFaultInfo().getFaultDescription());
		}
	}

	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_ByEmail_NotFound() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index

		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Email email = new Email();
		email.setEmail("111111.1111111@gmail.com");
		searchIdentifier.setEmail(email);
		
		request.setSearchIdentifier(searchIdentifier);
		
		
		Context context = new Context();
		context.setTypeOfSearch("I");	// Individual
		context.setResponseType("F");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setMatricule("T412211");
		requestor.setSignature("T412211");
		requestor.setSite("TLS");
		requestor.setApplicationCode("SVI");

		context.setRequestor(requestor);
		request.setContext(context);

		try {
			IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);
		
			Assert.fail("On doit avoir une Business Exception : NOT FOUND");
		
		} catch (BusinessException be) {
			assertNotNull(be);	
			assertNotNull(be.getFaultInfo());
			
			assertNotNull(be.getFaultInfo().getErrorCode());
			assertEquals("001", be.getFaultInfo().getErrorCode());
			
			assertNotNull(be.getFaultInfo().getFaultDescription());
			assertEquals("INDIVIDUAL NOT FOUND", be.getFaultInfo().getFaultDescription());
		}
	}
	
	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_ByTelecom_NotFound() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index

		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setPhoneNumber("612347854");
		searchIdentifier.setTelecom(telecom);
		
		request.setSearchIdentifier(searchIdentifier);
		
		
		Context context = new Context();
		context.setTypeOfSearch("I");	// Individual
		context.setResponseType("F");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setMatricule("T412211");
		requestor.setSignature("T412211");
		requestor.setSite("TLS");
		requestor.setApplicationCode("SVI");

		context.setRequestor(requestor);
		request.setContext(context);

		try {
			IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);
		
			Assert.fail("On doit avoir une Business Exception : NOT FOUND");
		
		} catch (BusinessException be) {
			assertNotNull(be);	
			assertNotNull(be.getFaultInfo());
			
			assertNotNull(be.getFaultInfo().getErrorCode());
			assertEquals("001", be.getFaultInfo().getErrorCode());
			
			assertNotNull(be.getFaultInfo().getFaultDescription());
			assertEquals("INDIVIDUAL NOT FOUND", be.getFaultInfo().getFaultDescription());
		}
	}


	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_OnFirmByIA_NotFound() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index

		SearchIdentifier searchIdentifier = new SearchIdentifier();

		ProvideIdentifier pi = new  ProvideIdentifier();
		pi.setIdentificationType("IA");
		pi.setIdentificationValue("111111111111");
		request.setProvideIdentifier(pi);		
		
		request.setSearchIdentifier(searchIdentifier);
		
		
		Context context = new Context();
		context.setTypeOfSearch("F");	// Firm
		context.setResponseType("F");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setMatricule("T412211");
		requestor.setSignature("T412211");
		requestor.setSite("TLS");
		requestor.setApplicationCode("SVI");

		context.setRequestor(requestor);
		request.setContext(context);

		try {
			IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);
		
			Assert.fail("On doit avoir une Business Exception : NOT FOUND");
		
		} catch (BusinessException be) {
			assertNotNull(be);	
			assertNotNull(be.getFaultInfo());
			
			assertNotNull(be.getFaultInfo().getErrorCode());
			assertEquals("001", be.getFaultInfo().getErrorCode());
			
			assertNotNull(be.getFaultInfo().getFaultDescription());
			assertEquals("NOT FOUND", be.getFaultInfo().getFaultDescription());
		}
	}

	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_OnAgenceByIA_NotFound() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index

		SearchIdentifier searchIdentifier = new SearchIdentifier();

		ProvideIdentifier pi = new  ProvideIdentifier();
		pi.setIdentificationType("IA");
		pi.setIdentificationValue("111111111111");
		request.setProvideIdentifier(pi);		
		
		request.setSearchIdentifier(searchIdentifier);
		
		
		Context context = new Context();
		context.setTypeOfSearch("A");	// Agence
		context.setResponseType("F");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setMatricule("T412211");
		requestor.setSignature("T412211");
		requestor.setSite("TLS");
		requestor.setApplicationCode("SVI");

		context.setRequestor(requestor);
		request.setContext(context);

		try {
			IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);
		
			Assert.fail("On doit avoir une Business Exception : NOT FOUND");
		
		} catch (BusinessException be) {
			assertNotNull(be);	
			assertNotNull(be.getFaultInfo());
			
			assertNotNull(be.getFaultInfo().getErrorCode());
			assertEquals("001", be.getFaultInfo().getErrorCode());
			
			assertNotNull(be.getFaultInfo().getFaultDescription());
			assertEquals("NOT FOUND", be.getFaultInfo().getFaultDescription());
		}
	}

	// REPIND-1288 : Search by Email must not be Case Sensitive
	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_ByMailLowerRequestLowerDatabase() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index

		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Email email = new Email();
		email.setEmail("sebastien.dan@francetelecom.com");
		searchIdentifier.setEmail(email);
		
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");	// Individual and Firm
		context.setResponseType("U");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setMatricule("T412211");
		requestor.setSignature("T412211");
		requestor.setSite("QVI");
		requestor.setApplicationCode("FID");

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertNotNull(response.getCustomer().get(0).getIndividual());
		assertEquals("400219521541", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}

	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_ByMailUpperRequestLowerDatabase() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index

		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Email email = new Email();
		email.setEmail("wiLLiam2004@msn.com");
		searchIdentifier.setEmail(email);
		
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");	// Individual and Firm
		context.setResponseType("U");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setMatricule("T412211");
		requestor.setSignature("T412211");
		requestor.setSite("QVI");
		requestor.setApplicationCode("FID");

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertNotNull(response.getCustomer().get(0).getIndividual());
		assertEquals("110026790542", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}
	
	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_ByMailUpperRequestLowerDatabase_2() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index

		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Email email = new Email();
		email.setEmail("paverLant@nOrdnet.fr");
		searchIdentifier.setEmail(email);
		
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");	// Individual and Firm
		context.setResponseType("U");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setMatricule("T412211");
		requestor.setSignature("T412211");
		requestor.setSite("QVI");
		requestor.setApplicationCode("FID");

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertNotNull(response.getCustomer().get(0).getIndividual());
		assertEquals("002003338476", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}
	
	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_ByMailUpperRequestLowerDatabase_WithWhiteSpace() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
		request.setIndex(1);			// Continuous index

		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Email email = new Email();
		email.setEmail("  paverLant@nOrdnet.fr ");
		searchIdentifier.setEmail(email);
		
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");	// Individual and Firm
		context.setResponseType("U");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setMatricule("T412211");
		requestor.setSignature("T412211");
		requestor.setSite("QVI");
		requestor.setApplicationCode("FID");

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertNotNull(response.getCustomer().get(0).getIndividual());
		assertEquals("002003338476", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}
	
	
	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_ByTelecom_NotFound_BuG() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("A"); 	// AUTOMATIC
//		request.setIndex(1);			// Continuous index

		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setPhoneNumber("06 11 27 29 81");
		searchIdentifier.setTelecom(telecom);
		
		request.setSearchIdentifier(searchIdentifier);
		
		
		Context context = new Context();
		context.setTypeOfSearch("IA");	// Individual - Agency
		context.setResponseType("U");	// Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel("DS");
		requestor.setManagingCompany("SVI");
		requestor.setMatricule("T412211");
		requestor.setSignature("T412211");
		requestor.setSite("TLS");
		requestor.setApplicationCode("SVI");

		context.setRequestor(requestor);
		request.setContext(context);

		try {
			IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);
		
			Assert.fail("On doit avoir une Business Exception : NOT FOUND");
		
		} catch (BusinessException be) {
			assertNotNull(be);	
			assertNotNull(be.getFaultInfo());
			
			assertNotNull(be.getFaultInfo().getErrorCode());
			assertEquals("133", be.getFaultInfo().getErrorCode());		// REPIND-1439 : Must be 001 instead of 133 
			
			assertNotNull(be.getFaultInfo().getFaultDescription());
			assertEquals("NEITHER INDIVIDUALS NOR AGENCIES FOUND", be.getFaultInfo().getFaultDescription());
		}
	}
	
	@Test		// Impact of FirstName LastName Only implement on Search 
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_Individu_ManualByNameOnly() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("M");	// MANUAL 
		request.setIndex(1);			// Index de continuité
		
		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		PersonsIdentity personsIdentity = new PersonsIdentity();
		personsIdentity.setFirstName("LAMBERTUS WILHELMUS");
		personsIdentity.setLastName("JONKHEER");
		searchIdentifier.setPersonsIdentity(personsIdentity);
		
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");
		context.setResponseType("F");
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setSignature("T412211");
		requestor.setSite("QVI");
		requestor.setManagingCompany("CO");
		requestor.setApplicationCode("B2C");

		context.setRequestor(requestor);
		request.setContext(context);

		try {
			IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		

			Assert.fail("On doit avoir une Business Exception : 133 car NOM ne suffit pas !");
		
		} catch (BusinessException be) {
			assertNotNull(be);	
			assertNotNull(be.getFaultInfo());
			
			assertNotNull(be.getFaultInfo().getErrorCode());
			assertEquals("133", be.getFaultInfo().getErrorCode());
			
			assertNotNull(be.getFaultInfo().getFaultDescription());
			assertEquals("IF FIRST_NAME AND LAST_NAME ARE SET, (COUNTRY_CODE) OR (EMAIL) OR (PHONE) HAVE TO BE SET", be.getFaultInfo().getFaultDescription());
		}
	}
}
