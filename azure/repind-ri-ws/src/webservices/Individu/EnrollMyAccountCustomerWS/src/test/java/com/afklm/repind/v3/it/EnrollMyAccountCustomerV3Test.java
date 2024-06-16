package com.afklm.repind.v3.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.helpers.EnrollMyAccountCustomerTestHelper;
import com.afklm.repind.v3.enrollmyaccountcustomerws.EnrollMyAccountCustomerV3Impl;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000431.v3.BusinessException;
import com.afklm.soa.stubs.w000431.v3.data.BusinessErrorEnum;
import com.afklm.soa.stubs.w000431.v3.data.EnrollMyAccountCustomerRequest;
import com.afklm.soa.stubs.w000431.v3.data.EnrollMyAccountCustomerResponse;
import com.afklm.soa.stubs.w000431.v3.siccommontype.RequestorV2;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.environnement.VariablesRepository;
import com.airfrance.repind.entity.environnement.Variables;
import com.airfrance.repind.service.consent.internal.ConsentDS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
class EnrollMyAccountCustomerV3Test {

	private static final  String ROBUST_PASSWORD_AUTHENTICATE_ACTIVATED = "ROBUST_PASSWORD_AUTHENTICATE_ACTIVATED";
	private static final String EMAIL_IDENTIFIER = "test_enroll_it_v2@airfrance.fr";
	private static final String FIRSTNAME = "JIYOU";
	private static final String LASTNAME = "NITE";
	private static final String POINT_OF_SALE = "NCE";
	
	private EnrollMyAccountCustomerRequest request;
	
	@Autowired
	@Qualifier("passenger_EnrollMyAccountCustomer-v03Bean")
	private EnrollMyAccountCustomerV3Impl enrollMyAccountCustomerV3Impl;
	
	@Autowired
	private EnrollMyAccountCustomerTestHelper enrollMyAccountCustomerTestHelper;
	
	@Autowired
	private VariablesRepository variablesRepository;

	@MockBean
	private ConsentDS consentDSMock;
	
	@BeforeEach
	void setUp() throws JrafDomainException {
		request = new EnrollMyAccountCustomerRequest();
		request.setCivility("MR");
		request.setEmailIdentifier(EMAIL_IDENTIFIER);
		request.setFirstName(FIRSTNAME);
		request.setLastName(LASTNAME);
		request.setLanguageCode("FR");
		request.setPassword("456784565aZ");
		request.setPointOfSale(POINT_OF_SALE);
		request.setWebsite("AF");
		RequestorV2 requestor = new RequestorV2();
		requestor.setSignature("TEST");
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		request.setRequestor(requestor);
	}
	
	/**
	 * 
	 * Enroll a new customer with a non robust password (Robust Password Mode Disabled)
	 * @throws com.afklm.soa.stubs.w000431.v3.BusinessException 
	 * @throws JrafApplicativeException 
	 * @throws com.afklm.repind.ws.enrollmyaccountcustomer.v03.SystemException
	 *
	 */
	@Test
	@Transactional
	@Rollback(false)
	void testEnrollWithPasswordEmpty() throws SystemException, JrafDomainException, JrafApplicativeException, BusinessException, SystemException {
		
		EnrollMyAccountCustomerRequest _request = new EnrollMyAccountCustomerRequest();
		_request.setCivility("MR");
		_request.setEmailIdentifier(EMAIL_IDENTIFIER);
		_request.setFirstName(FIRSTNAME);
		_request.setLastName(LASTNAME);
		_request.setLanguageCode("FR");
		_request.setPointOfSale(POINT_OF_SALE);
		_request.setWebsite("AF");
		RequestorV2 requestor = new RequestorV2();
		requestor.setSignature("TEST");
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		_request.setRequestor(requestor);
		
		
		//Desactive Robust Password just for this test
		enrollMyAccountCustomerTestHelper.setRobustPasswordActivated("false");
		
		//Enroll
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerV3Impl.enrollMyAccountCustomer(_request);
		
		Assertions.assertNotNull(response.getAccountID());
		Assertions.assertNotNull(response.getGin());
				
		//Delete test data previously created from database
		enrollMyAccountCustomerTestHelper.deleteDataAfterTest(response.getGin());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testEnrollMyAccountCustomerWithBB() throws BusinessException, SystemException{
		EnrollMyAccountCustomerRequest enrollMyAccountCustomerRequest = new EnrollMyAccountCustomerRequest();
		enrollMyAccountCustomerRequest.setCivility("MR");
		enrollMyAccountCustomerRequest.setFirstName("Apex");
		enrollMyAccountCustomerRequest.setLastName("Legends");
		enrollMyAccountCustomerRequest.setEmailIdentifier("Goodtime06@apexLegends.com");
		enrollMyAccountCustomerRequest.setLanguageCode("FR");
		enrollMyAccountCustomerRequest.setWebsite("BB");
		
		RequestorV2 requestor = new RequestorV2();
		requestor.setSite("QVI");
		requestor.setSignature("TEST");
		
		enrollMyAccountCustomerRequest.setRequestor(requestor);
		
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerV3Impl.enrollMyAccountCustomer(request);
		
		Assertions.assertNotNull(response.getAccountID());
		Assertions.assertNotNull(response.getGin());
		
	}

	


	/**
	 * 
	 *  Test with an invalid signature
	 * @throws JrafApplicativeException 
	 * @throws com.afklm.repind.ws.enrollmyaccountcustomer.v03.SystemException
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	void testInvalidSignature() throws JrafApplicativeException, SystemException {

		request.getRequestor().setSignature("");
		
		try {
			//Enroll
			enrollMyAccountCustomerV3Impl.enrollMyAccountCustomer(request);
			// if no exception is thrown, the test failed
			Assertions.fail();
		} catch (BusinessException e) {
			Assertions.assertEquals(BusinessErrorEnum.ERR_111.value(), e.getFaultInfo().getErrorCode());
		}
	}
	
	
	/**
	 * 
	 *  Test with a valid robust password
	 * @throws JrafApplicativeException 
	 * @throws com.afklm.repind.ws.enrollmyaccountcustomer.v03.SystemException
	 * 
	 */
	@Test
	@Transactional
	@Rollback(false)
	void testRobustPasswordValid() throws BusinessException, SystemException, JrafDomainException, JrafApplicativeException, SystemException {
		
		request.setPassword("123456789aZE");
		
		//Enroll
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerV3Impl.enrollMyAccountCustomer(request);
		
		//Delete test data previously created from database
		enrollMyAccountCustomerTestHelper.deleteDataAfterTest(response.getGin());
	}
	
	/**
	 * 
	 *  Test with a too small password for robust password
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	void testRobustPasswordTooSmall() {
		
		request.setPassword("123s5");
		
		try {
			//Enroll
			enrollMyAccountCustomerV3Impl.enrollMyAccountCustomer(request);
			Assertions.fail("");
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 
	 *  Test with a too long password for robust password
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	void testRobustPasswordTooBig() throws BusinessException, SystemException {
		
		request.setPassword("123456789AZEZ");
		
		try {
			//Enroll
			enrollMyAccountCustomerV3Impl.enrollMyAccountCustomer(request);
			Assertions.fail("");
		} catch (Exception e) {

		}
	}
	
	/**
	 * 
	 *  Test with a password that doesn't respect the pattern for robust pattern
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	void testRobustPasswordWrongPattern() throws BusinessException, SystemException {
		Optional<Variables> optVar = variablesRepository.findById(ROBUST_PASSWORD_AUTHENTICATE_ACTIVATED);
		Variables var = optVar.get();
		var.setEnvValue("true");
		variablesRepository.save(var);
		request.setPassword("123456789123");
		
		try {
			//Enroll
			enrollMyAccountCustomerV3Impl.enrollMyAccountCustomer(request);
			Assertions.fail("");
		} catch (Exception e) {

		}
	}
	
	/**
	 * 
	 *  Test with an invalid email
	 * @throws JrafApplicativeException 
	 * @throws com.afklm.repind.ws.enrollmyaccountcustomer.v03.SystemException
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	void testInvalidEmail() throws JrafApplicativeException, SystemException {

		request.setEmailIdentifier("fake.email/aifrance");
		
		try {
			//Enroll
			enrollMyAccountCustomerV3Impl.enrollMyAccountCustomer(request);
			// if no exception is thrown, the test failed
			Assertions.fail();
		} catch (BusinessException e) {
			Assertions.assertEquals(BusinessErrorEnum.ERR_932.value(), e.getFaultInfo().getErrorCode());
		}
	}

	/**
	 * 
	 *  Test with an invalid password
	 * @throws JrafApplicativeException 
	 * @throws com.afklm.repind.ws.enrollmyaccountcustomer.v03.SystemException
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	void testInvalidPassword() throws JrafApplicativeException, SystemException {

		request.setPassword(null);
		
		try {
			//Enroll
			enrollMyAccountCustomerV3Impl.enrollMyAccountCustomer(request);
			// if no exception is thrown, the test success
			Assertions.assertTrue(true);
		} catch (BusinessException e) {
			Assertions.fail(e.getMessage());
		}
	}

	/**
	 * 
	 *  Test EnrollV3
	 * @throws JrafApplicativeException 
	 * @throws com.afklm.repind.ws.enrollmyaccountcustomer.v03.SystemException
	 * 
	 */
	@Test
	@Transactional
	@Rollback(false)
	void testEnrollMyAccountV3() throws BusinessException, SystemException, JrafDomainException, JrafApplicativeException, SystemException {

		//Enroll
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerV3Impl.enrollMyAccountCustomer(request);
		
		Assertions.assertNotNull(response.getAccountID());
		Assertions.assertNotNull(response.getGin());

		//Delete test data previously created from database
		enrollMyAccountCustomerTestHelper.deleteDataAfterTest(response.getGin());
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	void testEnrollMyAccountV3WithLongEmailDomain() throws BusinessException, SystemException, JrafDomainException, JrafApplicativeException, SystemException {
		
		String email = "testlongdomaintest@gmail.security";

		request.setEmailIdentifier(email);
		//Enroll
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerV3Impl.enrollMyAccountCustomer(request);

		Assertions.assertNotNull(response.getEmail());
		Assertions.assertEquals(email, response.getEmail());
		Assertions.assertNotNull(response.getAccountID());
		Assertions.assertNotNull(response.getGin());

		//Delete test data previously created from database
		enrollMyAccountCustomerTestHelper.deleteDataAfterTest(response.getGin());
	}
	

	@Test
	@Transactional
	@Rollback(false)
	void testEnrollWithUpperEmail() throws SystemException, JrafDomainException, JrafApplicativeException, BusinessException, SystemException {
		
		String emailForTest = "UPPER_EMAIL_V3@gmail.com";
		
		EnrollMyAccountCustomerRequest request = new EnrollMyAccountCustomerRequest();
		request.setCivility("MR");
		request.setEmailIdentifier(emailForTest);
		request.setFirstName(FIRSTNAME);
		request.setLastName(LASTNAME);
		request.setLanguageCode("FR");
		request.setPointOfSale(POINT_OF_SALE);
		request.setWebsite("AF");
		RequestorV2 requestor = new RequestorV2();
		requestor.setSignature("TEST");
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		request.setRequestor(requestor);
		
		//Desactive Robust Password just for this test
		enrollMyAccountCustomerTestHelper.setRobustPasswordActivated("false");
		
		//Enroll
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerV3Impl.enrollMyAccountCustomer(request);
		
		Assertions.assertNotNull(response.getAccountID());
		Assertions.assertNotNull(response.getGin());
		Assertions.assertEquals(emailForTest.toLowerCase(), response.getEmail());
				
		//Delete test data previously created from database
		enrollMyAccountCustomerTestHelper.deleteDataAfterTest(response.getGin());
	}	
	
	@Test
	@Transactional
	@Rollback(true)
	void testEnrollUpperEmailAlreadyUsed() throws SystemException {
		
		String emailForTest = "TEST_UPPER_EMAIL_V3@gmail.com";
		
		EnrollMyAccountCustomerRequest request = new EnrollMyAccountCustomerRequest();
		request.setCivility("MR");
		request.setEmailIdentifier(emailForTest);
		request.setFirstName(FIRSTNAME);
		request.setLastName(LASTNAME);
		request.setLanguageCode("FR");
		request.setPointOfSale(POINT_OF_SALE);
		request.setWebsite("AF");
		RequestorV2 requestor = new RequestorV2();
		requestor.setSignature("TEST");
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		request.setRequestor(requestor);
		
		//Desactive Robust Password just for this test
		enrollMyAccountCustomerTestHelper.setRobustPasswordActivated("false");
		
		try {
			enrollMyAccountCustomerV3Impl.enrollMyAccountCustomer(request);
		} catch (BusinessException e) {			
			Assertions.assertEquals("ACCOUNT ALREADY EXIST", e.getFaultInfo().getFaultDescription());
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testEnrollCreateDefaultConsentsOK() throws SystemException, BusinessException, JrafDomainException {
		
		enrollMyAccountCustomerV3Impl.setConsentDS(consentDSMock);
		Mockito.doNothing().when(consentDSMock).createDefaultConsents(Mockito.anyString(), Mockito.anyString());
				
		EnrollMyAccountCustomerRequest request = new EnrollMyAccountCustomerRequest();
		request.setCivility("MR");
		request.setEmailIdentifier("TESTCREATEDEFCONS@AF.COM");
		request.setFirstName("TESTCREATEDEFCONS");
		request.setLastName("TESTCREATEDEFCONS");
		request.setLanguageCode("FR");
		request.setPointOfSale("FR");
		request.setWebsite("AF");
		RequestorV2 requestor = new RequestorV2();
		requestor.setSignature("TEST");
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		request.setRequestor(requestor);
		
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerV3Impl.enrollMyAccountCustomer(request);

		Assertions.assertNotNull(response.getGin());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testEnrollCreateDefaultConsentsKO() throws SystemException, BusinessException, JrafDomainException {
		
		enrollMyAccountCustomerV3Impl.setConsentDS(consentDSMock);
		Mockito.doThrow(new JrafDomainException("Error during creation of default consents")).when(consentDSMock).createDefaultConsents(Mockito.anyString(), Mockito.anyString());
				
		EnrollMyAccountCustomerRequest request = new EnrollMyAccountCustomerRequest();
		request.setCivility("MR");
		request.setEmailIdentifier("TESTCREATEDEFCONS@AF.COM");
		request.setFirstName("TESTCREATEDEFCONS");
		request.setLastName("TESTCREATEDEFCONS");
		request.setLanguageCode("FR");
		request.setPointOfSale("FR");
		request.setWebsite("AF");
		RequestorV2 requestor = new RequestorV2();
		requestor.setSignature("TEST");
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		request.setRequestor(requestor);
		
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerV3Impl.enrollMyAccountCustomer(request);

		Assertions.assertNotNull(response.getGin());
	}

	@Test
	@Transactional
	void testEnrollMyAccountV3_NoCivility(){
		EnrollMyAccountCustomerRequest requestNoGenderNoCivility = new EnrollMyAccountCustomerRequest();
		requestNoGenderNoCivility.setEmailIdentifier(EMAIL_IDENTIFIER);
		requestNoGenderNoCivility.setFirstName(FIRSTNAME);
		requestNoGenderNoCivility.setLastName(LASTNAME);
		requestNoGenderNoCivility.setLanguageCode("FR");
		requestNoGenderNoCivility.setPassword("456784565aZ");
		requestNoGenderNoCivility.setPointOfSale(POINT_OF_SALE);
		requestNoGenderNoCivility.setWebsite("AF");
		RequestorV2 requestor = new RequestorV2();
		requestor.setSignature("TEST");
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestNoGenderNoCivility.setRequestor(requestor);

		//Enroll
		BusinessException exceptionThrown = assertThrows(BusinessException.class, () -> enrollMyAccountCustomerV3Impl.enrollMyAccountCustomer(requestNoGenderNoCivility));
		Assertions.assertEquals(BusinessErrorEnum.ERR_905.toString(),exceptionThrown.getFaultInfo().getErrorCode());
	}

}
