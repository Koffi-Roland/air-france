package com.afklm.repind.v1.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.enrollmyaccountcustomerws.EnrollMyAccountCustomerImpl;
import com.afklm.repind.helpers.EnrollMyAccountCustomerTestHelper;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000431.v1.BusinessException;
import com.afklm.soa.stubs.w000431.v1.enrollmyaccountcustomeryype.EnrollMyAccountCustomerRequest;
import com.afklm.soa.stubs.w000431.v1.enrollmyaccountcustomeryype.EnrollMyAccountCustomerResponse;
import com.afklm.soa.stubs.w000431.v1.enrollmyaccountcustomeryype.WebSiteEnum;
import com.afklm.soa.stubs.w000431.v1.sicdatatype.CiviliteEnum;
import com.afklm.soa.stubs.w000431.v1.sicdatatype.LanguageCodesEnum;
import com.afklm.soa.stubs.w000431.v1.sicindividutype.SignatureLight;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.consent.internal.ConsentDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class EnrollMyAccountCustomerTest {

	private static final CiviliteEnum CIVILITY = CiviliteEnum.MR;
	private static final String EMAIL_IDENTIFIER = "test_enroll_it_v1@airfrance.fr";
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
	@Qualifier("passenger_EnrollMyAccountCustomer-v01Bean")
	private EnrollMyAccountCustomerImpl enrollMyAccountCustomerImpl;
	
	@Autowired
	private EnrollMyAccountCustomerTestHelper enrollMyAccountCustomerTestHelper;
	
	@Autowired
	private EmailDS emailDS;
	
	@Autowired
	private MyAccountDS myAccountDS;
	
	@MockBean
	private ConsentDS consentDSMock;
	
	@Before
	public void setUp() throws JrafDomainException {
		request = new EnrollMyAccountCustomerRequest();
		request.setCivility(EnrollMyAccountCustomerTest.CIVILITY);
		request.setEmailIdentifier(EnrollMyAccountCustomerTest.EMAIL_IDENTIFIER);
		request.setFirstName(EnrollMyAccountCustomerTest.FIRSTNAME);
		request.setLastName(EnrollMyAccountCustomerTest.LASTNAME);
		request.setLanguageCode(EnrollMyAccountCustomerTest.LANGUAGE);
		request.setOptIn(EnrollMyAccountCustomerTest.OPTIN);
		request.setPassword(EnrollMyAccountCustomerTest.PASSWORD);
		request.setPointOfSale(EnrollMyAccountCustomerTest.POINT_OF_SALE);
		request.setWebsite(EnrollMyAccountCustomerTest.WEBSITE);
		SignatureLight signatureLight = new SignatureLight();
		signatureLight.setIpAddress(EnrollMyAccountCustomerTest.IP_ADDRESS);
		signatureLight.setSignature(EnrollMyAccountCustomerTest.SIGNATURE);
		signatureLight.setSite(EnrollMyAccountCustomerTest.SITE);
		request.setSignature(signatureLight);
		
		enrollMyAccountCustomerImpl.setAccountDS(myAccountDS);
	}
	
	/**
	 * 
	 * Test with a password that is robust
	 * @throws JrafDomainException 
	 *
	 */
	@Transactional
	@Rollback(false)
	@Test
	public void testRobustPasswordValid() throws BusinessException, SystemException, JrafDomainException {
		request.setPassword("123456789aZE");
		
		//Enroll
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerImpl.enrollMyAccountCustomer(request);
		
		//Delete test data previously created from database
		enrollMyAccountCustomerTestHelper.deleteDataAfterTest(response.getGin());
	}

	/**
	 * 
	 * Test with a password too small for Robust Password
	 *
	 */
	@Transactional
	@Rollback(true)
	@Test
	public void testRobustPasswordTooSmall() {
		request.setPassword("123s5");
		
		try {
			//Enroll
			enrollMyAccountCustomerImpl.enrollMyAccountCustomer(request);
			Assert.fail("");
		} catch (Exception e) {

		}
	}

	/**
	 * 
	 * Test with a password too big for Robust Password
	 *
	 */
	@Transactional
	@Rollback(true)
	@Test
	public void testRobustPasswordTooBig() throws BusinessException, SystemException {
		request.setPassword("123456789AZEZ");
		
		try {
			//Enroll
			enrollMyAccountCustomerImpl.enrollMyAccountCustomer(request);
			Assert.fail("");
		} catch (Exception e) {

		}
	}

	/**
	 * 
	 * Test with a password that doens't respect the pattern for Robust Password
	 *
	 */
	@Transactional
	@Rollback(true)
	@Test
	@Ignore	// TODO : De Ignorer
	public void testRobustPasswordWrongPattern() throws BusinessException, SystemException {
		request.setPassword("123456789123");
		
		try {
			//Enroll
			enrollMyAccountCustomerImpl.enrollMyAccountCustomer(request);
			Assert.fail("");
		} catch (Exception e) {

		}
	}
	



	
	@Test
	public void testEnroll_With_CommPref_Y() throws BusinessException, SystemException, JrafDomainException {

		request.setEmailIdentifier(RandomStringUtils.randomAlphabetic(10) + EnrollMyAccountCustomerTest.EMAIL_IDENTIFIER);
		request.setFirstName(EnrollMyAccountCustomerTest.FIRSTNAME + RandomStringUtils.randomAlphabetic(10));
		request.setLastName(EnrollMyAccountCustomerTest.LASTNAME + RandomStringUtils.randomAlphabetic(10));
		request.setOptIn(true);
		
		//Enroll
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerImpl.enrollMyAccountCustomer(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getAccountID());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("4"));
	}

	@Test
	public void testEnroll_With_CommPref_N() throws BusinessException, SystemException, JrafDomainException {

		request.setEmailIdentifier(RandomStringUtils.randomAlphabetic(10) + EnrollMyAccountCustomerTest.EMAIL_IDENTIFIER);
		request.setFirstName(EnrollMyAccountCustomerTest.FIRSTNAME + RandomStringUtils.randomAlphabetic(10));
		request.setLastName(EnrollMyAccountCustomerTest.LASTNAME + RandomStringUtils.randomAlphabetic(10));
		request.setOptIn(false);
		
		//Enroll
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerImpl.enrollMyAccountCustomer(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getAccountID());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("4"));
	}
	

	@Test
	@Transactional
	@Rollback(true)
	public void testEnrollMyAccountWithLongEmailDomain() throws BusinessException, SystemException, JrafDomainException {
		
		String email = "testlongdomaintest@gmail.security";

		request.setEmailIdentifier(email);
		//Enroll
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerImpl.enrollMyAccountCustomer(request);
		
		Assert.assertNotNull(response.getAccountID());
		Assert.assertNotNull(response.getGin());

		//Delete test data previously created from database
		enrollMyAccountCustomerTestHelper.deleteDataAfterTest(response.getGin());
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testEnrollWithUpperEmail() throws BusinessException, SystemException, JrafDomainException {
			
		String emailForTest = "UPPER_EMAIL_V1@gmail.com";
		
		//Active Robust Password just for this test
		enrollMyAccountCustomerTestHelper.setRobustPasswordActivated("true");
		
		EnrollMyAccountCustomerRequest request = new EnrollMyAccountCustomerRequest();
		request.setCivility(EnrollMyAccountCustomerTest.CIVILITY);
		request.setEmailIdentifier(emailForTest);
		request.setFirstName(EnrollMyAccountCustomerTest.FIRSTNAME);
		request.setLastName(EnrollMyAccountCustomerTest.LASTNAME);
		request.setLanguageCode(EnrollMyAccountCustomerTest.LANGUAGE);
		request.setOptIn(EnrollMyAccountCustomerTest.OPTIN);
		request.setPassword(EnrollMyAccountCustomerTest.PASSWORD);
		request.setPointOfSale(EnrollMyAccountCustomerTest.POINT_OF_SALE);
		request.setWebsite(EnrollMyAccountCustomerTest.WEBSITE);
		SignatureLight signatureLight = new SignatureLight();
		signatureLight.setIpAddress(EnrollMyAccountCustomerTest.IP_ADDRESS);
		signatureLight.setSignature(EnrollMyAccountCustomerTest.SIGNATURE);
		signatureLight.setSite(EnrollMyAccountCustomerTest.SITE);
		request.setSignature(signatureLight);
		
		//Enroll
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerImpl.enrollMyAccountCustomer(request);
		
		Assert.assertNotNull(response.getAccountID());
		Assert.assertNotNull(response.getGin());
		List<EmailDTO> listEmails = emailDS.findEmail(response.getGin());
		Assert.assertEquals(emailForTest.toLowerCase(), listEmails.get(0).getEmail());	
		
		//Delete test data previously created from database
		enrollMyAccountCustomerTestHelper.deleteDataAfterTest(response.getGin());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testEnrollUpperEmailAlreadyUsed() throws BusinessException, SystemException, JrafDomainException {
		
		//Active Robust Password just for this test
		enrollMyAccountCustomerTestHelper.setRobustPasswordActivated("false");
		
		String emailForTest = "TEST_UPPER_EMAIL_V1@gmail.com";

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
		request.setSignature(signatureLight);
		
		try {
			enrollMyAccountCustomerImpl.enrollMyAccountCustomer(request);
		} catch (BusinessException e) {			
			Assert.assertEquals("ACCOUNT ALREADY EXIST", e.getFaultInfo().getFaultDescription());
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testEnrollCreateDefaultConsentsOK() throws SystemException, BusinessException, JrafDomainException {
		
		enrollMyAccountCustomerImpl.setConsentDS(consentDSMock);
		Mockito.doNothing().when(consentDSMock).createDefaultConsents(Mockito.anyString(), Mockito.anyString());
				
		EnrollMyAccountCustomerRequest request = new EnrollMyAccountCustomerRequest();
		request.setCivility(CiviliteEnum.MR);
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
		request.setSignature(signature);
		
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerImpl.enrollMyAccountCustomer(request);

		Assert.assertNotNull(response.getGin());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testEnrollCreateDefaultConsentsKO() throws SystemException, BusinessException, JrafDomainException {
		
		enrollMyAccountCustomerImpl.setConsentDS(consentDSMock);
		Mockito.doThrow(new JrafDomainException("Error during creation of default consents")).when(consentDSMock).createDefaultConsents(Mockito.anyString(), Mockito.anyString());
				
		EnrollMyAccountCustomerRequest request = new EnrollMyAccountCustomerRequest();
		request.setCivility(CiviliteEnum.MR);
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
		request.setSignature(signature);
		
		EnrollMyAccountCustomerResponse response = enrollMyAccountCustomerImpl.enrollMyAccountCustomer(request);

		Assert.assertNotNull(response.getGin());
	}
}
