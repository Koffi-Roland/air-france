package com.afklm.repind.v2.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.helpers.EnrollMyAccountCustomerTestHelper;
import com.afklm.repind.v2.enrollmyaccountcustomerws.EnrollMyAccountCustomerV2Impl;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000431.v2.BusinessException;
import com.afklm.soa.stubs.w000431.v2.data.*;
import com.afklm.soa.stubs.w000431.v2.siccommonenum.CivilityEnum;
import com.afklm.soa.stubs.w000431.v2.siccommonenum.LanguageCodesEnum;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.environnement.VariablesRepository;
import com.airfrance.repind.entity.environnement.Variables;
import com.airfrance.repind.service.consent.internal.ConsentDS;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
class EnrollMyAccountCustomerV2Test {

	private static final  String ROBUST_PASSWORD_AUTHENTICATE_ACTIVATED = "ROBUST_PASSWORD_AUTHENTICATE_ACTIVATED";
	private static final Log logger = LogFactory.getLog(EnrollMyAccountCustomerV2Test.class);
	
	private static final CivilityEnum CIVILITY = CivilityEnum.MR;
	private static final String EMAIL_IDENTIFIER = "test_enroll_it_v2@airfrance.fr";
	private static final String FIRSTNAME = "JIYOU";
	private static final String LASTNAME = "NITE";
	private static final LanguageCodesEnum LANGUAGE = LanguageCodesEnum.FR;
	private static final boolean OPTIN = false;
	private static final String PASSWORD = "456784565aZ";
	private static final String POINT_OF_SALE = "NCE";
	private static final WebSiteEnum WEBSITE = WebSiteEnum.AF;
	private static final String IP_ADDRESS = "127.0.0.1";
	private static final String SIGNATURE = "JUNITTEST";
	private static final String SITE = "QVI";
	
    /** the Entity Manager*/
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

	private EnrollMyAccountCustomerRequest request;

	@Autowired
	@Qualifier("passenger_EnrollMyAccountCustomer-v02Bean")
	private EnrollMyAccountCustomerV2Impl enrollMyAccountCustomerV2Impl = null;
		
	@Autowired
	private EnrollMyAccountCustomerTestHelper enrollMyAccountCustomerTestHelper;

	@Autowired
	private VariablesRepository variablesRepository;

	@MockBean
	private ConsentDS consentDSMock;

	@BeforeEach
	void setUp() throws JrafDomainException {
		request = new EnrollMyAccountCustomerRequest();
		request.setCivility(CIVILITY);
		request.setEmailIdentifier(EMAIL_IDENTIFIER);
		request.setFirstName(FIRSTNAME);
		request.setLastName(LASTNAME);
		request.setLanguageCode(LANGUAGE);
		request.setOptIn(OPTIN);
		request.setPassword(PASSWORD);
		request.setPointOfSale(POINT_OF_SALE);
		request.setWebsite(WEBSITE);
		SignatureLight signatureLight = new SignatureLight();
		signatureLight.setIpAddress(IP_ADDRESS);
		signatureLight.setSignature(SIGNATURE);
		signatureLight.setSite(SITE);
		request.setSignatureLight(signatureLight);
	}
	



	


	/**
	 * 
	 *  Test with an invalid signature
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	void testInvalidSignature() {
		logger.info("testInvalidSignature()");

		request.setSignatureLight(new SignatureLight());
		
		try {
			//Enroll
			enrollMyAccountCustomerV2Impl.enrollMyAccountCustomer(request);
			// if no exception is thrown, the test failed
			Assert.fail();
		} catch (BusinessException e) {
			Assert.assertEquals(BusinessErrorEnum.ERR_111.value(), e.getFaultInfo().getErrorCode());
		} catch (SystemException e) {
			// e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * 
	 *  Test with a valid robust password
	 * 
	 */
	@Test
	@Transactional
	@Rollback(false)
	void testRobustPasswordValid() throws BusinessException, SystemException, JrafDomainException {
		logger.info("testRobustPasswordValid()");
		
		request.setPassword("123456789aZE");
		
		//Enroll
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerV2Impl.enrollMyAccountCustomer(request);
		
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
		logger.info("testRobustPasswordTooSmall()");
		
		request.setPassword("123s5");
		
		try {
			//Enroll
			enrollMyAccountCustomerV2Impl.enrollMyAccountCustomer(request);
			Assert.fail("");
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
		logger.info("testRobustPasswordTooBig()");
		
		request.setPassword("123456789AZEZ");
		
		try {
			//Enroll
			enrollMyAccountCustomerV2Impl.enrollMyAccountCustomer(request);
			Assert.fail("");
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
		logger.info("testRobustPasswordWrongPattern()");
		
		request.setPassword("123456789123");
		
		try {
			//Enroll
			enrollMyAccountCustomerV2Impl.enrollMyAccountCustomer(request);
			Assert.fail("");
		} catch (Exception e) {

		}
	}

	/**
	 * 
	 *  Test with an invalid email
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	void testInvalidEmail() {
		logger.info("testInvalidEmail()");

		request.setEmailIdentifier("fake.email/aifrance");
		
		try {
			//Enroll
			enrollMyAccountCustomerV2Impl.enrollMyAccountCustomer(request);
			// if no exception is thrown, the test failed
			Assert.fail();
		} catch (BusinessException e) {
			Assert.assertEquals(BusinessErrorEnum.ERR_932.value(), e.getFaultInfo().getErrorCode());
		} catch (SystemException e) {
			// e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * 
	 *  Test with an invalid password
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	void testInvalidPassword() {
		logger.info("testInvalidPassword()");

		request.setPassword(null);
		
		try {
			//Enroll
			enrollMyAccountCustomerV2Impl.enrollMyAccountCustomer(request);
			// if no exception is thrown, the test failed
			Assert.fail();
		} catch (BusinessException e) {
			Assert.assertEquals(BusinessErrorEnum.ERR_932.value(), e.getFaultInfo().getErrorCode());
		} catch (SystemException e) {
			// e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * 
	 *  Test EnrollV2
	 * 
	 */
	@Test
	@Transactional
	@Rollback(false)
	void testEnrollMyAccountV2() throws BusinessException, SystemException, JrafDomainException {
		logger.info("testEnrollMyAccountV2()");

		//Enroll
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerV2Impl.enrollMyAccountCustomer(request);
		
		Assert.assertNotNull(response.getAccountID());
		Assert.assertNotNull(response.getGin());

		//Delete test data previously created from database
		enrollMyAccountCustomerTestHelper.deleteDataAfterTest(response.getGin());
	}
	
	@Test
	void testEnroll_With_CommPref_Y() throws BusinessException, SystemException, JrafDomainException {

		request.setEmailIdentifier(RandomStringUtils.randomAlphabetic(10) + EnrollMyAccountCustomerV2Test.EMAIL_IDENTIFIER);
		request.setFirstName(EnrollMyAccountCustomerV2Test.FIRSTNAME + RandomStringUtils.randomAlphabetic(10));
		request.setLastName(EnrollMyAccountCustomerV2Test.LASTNAME + RandomStringUtils.randomAlphabetic(10));
		request.setOptIn(true);
		
		//Enroll
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerV2Impl.enrollMyAccountCustomer(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getAccountID());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("4"));
	}

	@Test
	void testEnroll_With_CommPref_N() throws BusinessException, SystemException, JrafDomainException {

		request.setEmailIdentifier(RandomStringUtils.randomAlphabetic(10) + EnrollMyAccountCustomerV2Test.EMAIL_IDENTIFIER);
		request.setFirstName(EnrollMyAccountCustomerV2Test.FIRSTNAME + RandomStringUtils.randomAlphabetic(10));
		request.setLastName(EnrollMyAccountCustomerV2Test.LASTNAME + RandomStringUtils.randomAlphabetic(10));
		request.setOptIn(false);
		
		//Enroll
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerV2Impl.enrollMyAccountCustomer(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getAccountID());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("4"));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testEnrollMyAccountV2WithLongEmailDomain() throws BusinessException, SystemException, JrafDomainException {
		logger.info("testEnrollMyAccountV2WithLongEmailDomain()");
		
		String email = "testlongdomaintest@gmail.security";

		request.setEmailIdentifier(email);
		//Enroll
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerV2Impl.enrollMyAccountCustomer(request);

		Assert.assertNotNull(response.getEmail());
		Assert.assertEquals(email, response.getEmail());
		Assert.assertNotNull(response.getAccountID());
		Assert.assertNotNull(response.getGin());

		//Delete test data previously created from database
		enrollMyAccountCustomerTestHelper.deleteDataAfterTest(response.getGin());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testEnrollWithUpperEmail() throws BusinessException, SystemException, JrafDomainException {
		logger.info("testEnrollWithUpperEmail()");
		
		//Active Robust Password just for this test
		enrollMyAccountCustomerTestHelper.setRobustPasswordActivated("false");
		
		String emailForTest = "UPPER_EMAIL_V2@gmail.com";

		EnrollMyAccountCustomerRequest request = new EnrollMyAccountCustomerRequest();
		request.setCivility(CIVILITY);
		request.setEmailIdentifier(emailForTest);
		request.setFirstName(FIRSTNAME);
		request.setLastName(LASTNAME);
		request.setLanguageCode(LANGUAGE);
		request.setOptIn(OPTIN);
		request.setPassword(PASSWORD);
		request.setPointOfSale(POINT_OF_SALE);
		request.setWebsite(WEBSITE);
		SignatureLight signatureLight = new SignatureLight();
		signatureLight.setIpAddress(IP_ADDRESS);
		signatureLight.setSignature(SIGNATURE);
		signatureLight.setSite(SITE);
		request.setSignatureLight(signatureLight);
		
		//Enroll
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerV2Impl.enrollMyAccountCustomer(request);
		
		Assert.assertNotNull(response.getAccountID());
		Assert.assertNotNull(response.getGin());
		Assert.assertEquals(emailForTest.toLowerCase(), response.getEmail());
		
		//Delete test data previously created from database
		enrollMyAccountCustomerTestHelper.deleteDataAfterTest(response.getGin());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testEnrollUpperEmailAlreadyUsed() throws BusinessException, SystemException, JrafDomainException {
		logger.info("testEnrollWithUpperEmail()");
		
		//Active Robust Password just for this test
		enrollMyAccountCustomerTestHelper.setRobustPasswordActivated("false");
		
		String emailForTest = "TEST_UPPER_EMAIL_V2@gmail.com";

		EnrollMyAccountCustomerRequest request = new EnrollMyAccountCustomerRequest();
		request.setCivility(CIVILITY);
		request.setEmailIdentifier(emailForTest);
		request.setFirstName(FIRSTNAME);
		request.setLastName(LASTNAME);
		request.setLanguageCode(LANGUAGE);
		request.setOptIn(OPTIN);
		request.setPassword(PASSWORD);
		request.setPointOfSale(POINT_OF_SALE);
		request.setWebsite(WEBSITE);
		SignatureLight signatureLight = new SignatureLight();
		signatureLight.setIpAddress(IP_ADDRESS);
		signatureLight.setSignature(SIGNATURE);
		signatureLight.setSite(SITE);
		request.setSignatureLight(signatureLight);
		
		try {
			enrollMyAccountCustomerV2Impl.enrollMyAccountCustomer(request);
		} catch (BusinessException e) {			
			Assert.assertEquals("ACCOUNT ALREADY EXIST", e.getFaultInfo().getFaultDescription());
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testEnrollCreateDefaultConsentsOK() throws SystemException, BusinessException, JrafDomainException {
		
		enrollMyAccountCustomerV2Impl.setConsentDS(consentDSMock);
		Mockito.doNothing().when(consentDSMock).createDefaultConsents(Mockito.anyString(), Mockito.anyString());
				
		EnrollMyAccountCustomerRequest request = new EnrollMyAccountCustomerRequest();
		request.setCivility(CivilityEnum.MR);
		request.setEmailIdentifier("TESTCREATEDEFCONS@AF.COM");
		request.setFirstName("TESTCREATEDEFCONS");
		request.setLastName("TESTCREATEDEFCONS");
		request.setPassword("Abcd123456");
		request.setLanguageCode(LanguageCodesEnum.FR);
		request.setPointOfSale("FR");
		request.setWebsite(WebSiteEnum.AF);
		SignatureLight signature = new SignatureLight();
		signature.setSignature("TEST");
		signature.setSite("QVI");
		signature.setIpAddress("0.0.0.0");
		request.setSignatureLight(signature);
		
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerV2Impl.enrollMyAccountCustomer(request);

		Assert.assertNotNull(response.getGin());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void testEnrollCreateDefaultConsentsKO() throws SystemException, BusinessException, JrafDomainException {
		
		enrollMyAccountCustomerV2Impl.setConsentDS(consentDSMock);
		Mockito.doThrow(new JrafDomainException("Error during creation of default consents")).when(consentDSMock).createDefaultConsents(Mockito.anyString(), Mockito.anyString());
				
		EnrollMyAccountCustomerRequest request = new EnrollMyAccountCustomerRequest();
		request.setCivility(CivilityEnum.MR);
		request.setEmailIdentifier("TESTCREATEDEFCONS@AF.COM");
		request.setFirstName("TESTCREATEDEFCONS");
		request.setLastName("TESTCREATEDEFCONS");
		request.setPassword("Abcd123456");
		request.setLanguageCode(LanguageCodesEnum.FR);
		request.setPointOfSale("FR");
		request.setWebsite(WebSiteEnum.AF);
		SignatureLight signature = new SignatureLight();
		signature.setSignature("TEST");
		signature.setSite("QVI");
		signature.setIpAddress("0.0.0.0");
		request.setSignatureLight(signature);
		
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerV2Impl.enrollMyAccountCustomer(request);

		Assert.assertNotNull(response.getGin());
	}

	@Test
	@Transactional
	@Rollback(false)
	void testEnrollMyAccountV2_NoCivility() throws BusinessException, SystemException, JrafDomainException {
		logger.info("testEnrollMyAccountV2()");

		EnrollMyAccountCustomerRequest requestNoGenderNoCivility = new EnrollMyAccountCustomerRequest();
		requestNoGenderNoCivility.setCivility(CIVILITY);
		requestNoGenderNoCivility.setEmailIdentifier(EMAIL_IDENTIFIER);
		requestNoGenderNoCivility.setFirstName(FIRSTNAME);
		requestNoGenderNoCivility.setLastName(LASTNAME);
		requestNoGenderNoCivility.setLanguageCode(LANGUAGE);
		requestNoGenderNoCivility.setOptIn(OPTIN);
		requestNoGenderNoCivility.setPassword(PASSWORD);
		requestNoGenderNoCivility.setPointOfSale(POINT_OF_SALE);
		requestNoGenderNoCivility.setWebsite(WEBSITE);
		SignatureLight signatureLight = new SignatureLight();
		signatureLight.setIpAddress(IP_ADDRESS);
		signatureLight.setSignature(SIGNATURE);
		signatureLight.setSite(SITE);
		requestNoGenderNoCivility.setSignatureLight(signatureLight);

		//Enroll
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerV2Impl.enrollMyAccountCustomer(requestNoGenderNoCivility);

		Assert.assertNotNull(response.getAccountID());
		Assert.assertNotNull(response.getGin());

		//Delete test data previously created from database
		enrollMyAccountCustomerTestHelper.deleteDataAfterTest(response.getGin());
	}

}
