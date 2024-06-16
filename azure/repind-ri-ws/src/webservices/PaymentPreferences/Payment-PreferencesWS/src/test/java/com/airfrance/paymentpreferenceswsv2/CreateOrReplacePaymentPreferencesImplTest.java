package com.airfrance.paymentpreferenceswsv2;

import com.afklm.repindpp.paymentpreference.dto.FieldsDTO;
import com.afklm.repindpp.paymentpreference.dto.PaymentsDetailsDTO;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000469.v2_0_1.BusinessException;
import com.afklm.soa.stubs.w000469.v2_0_1.providedecryptedpaymentpreferencesschema.ProvideFields;
import com.afklm.soa.stubs.w000469.v2_0_1.providedecryptedpaymentpreferencesschema.ProvidePaymentPreferences;
import com.afklm.soa.stubs.w000469.v2_0_1.providedecryptedpaymentpreferencesschema.ProvidePaymentPreferencesRequest;
import com.afklm.soa.stubs.w000469.v2_0_1.providedecryptedpaymentpreferencesschema.ProvidePaymentPreferencesResponse;
import com.afklm.soa.stubs.w000470.v2_0_1.createorreplacepaymentpreferencesschema.CreateFields;
import com.afklm.soa.stubs.w000470.v2_0_1.createorreplacepaymentpreferencesschema.CreatePaymentPreferencesRequest;
import com.afklm.soa.stubs.w000470.v2_0_1.createorreplacepaymentpreferencesschema.CreatePaymentPreferencesResponse;
import com.afklm.soa.stubs.w000471.v1.deletepaymentpreferencesschema.DeletePaymentPreferencesRequest;
import com.afklm.soa.stubs.w000471.v1.deletepaymentpreferencesschema.DeletePaymentPreferencesResponse;
import com.airfrance.config.WebTestConfig;
import com.airfrance.paymentpreferenceswsv2.v2.CreateOrReplacePaymentPreferencesV2Impl;
import com.airfrance.paymentpreferenceswsv2.v2.ProvidePaymentPreferencesV2Impl;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
 
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrReplacePaymentPreferencesImplTest {

    private static final String BILL_CITY = "BILL_CITY";
	private static final Log logger = LogFactory.getLog(CreateOrReplacePaymentPreferencesImplTest.class);
	List<String> mc_l 	= Arrays.asList("MAESTRO", "MC"), //MasterCard List
			 	 visa_l = Arrays.asList("VISA_ELECTRON", "VISA", "VISA_DEBIT"), //Visa List
			 	 cb_l 	= Arrays.asList("CARTE_BANCAIRE"); //CarteBancaire List

	public static final String MASTERCARD_CODE = "MC";
	public static final String VISA_CODE = "VI";
	public static final String BLUECARD_CODE = "CB";
	public static final String NO_EXCEPTION_TO_BE_THROWN = "No exception is supposed to be thrown!";
	public static final String AN_EXCEPTION_TO_BE_THROWN = "An exception is supposed to be thrown!";
	
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();
	
    @Autowired
	@Qualifier("passenger_CreateOrReplacePaymentPreferences-v2Bean")
	private CreateOrReplacePaymentPreferencesV2Impl createOrReplacePaymentPreferencesImpl;

    @Autowired
	@Qualifier("passenger_ProvidePaymentPreferences-v2Bean")
	private ProvidePaymentPreferencesV2Impl providePaymentPreferencesImpl;
    
    @Autowired
	@Qualifier("passenger_DeletePaymentPreferences-v1Bean")
	private DeletePaymentPreferencesImpl deletePaymentPreferencesImpl;
    
    /**
     * Clean all data from BDD before running each tests
     */
    @Before
    public void clearPP() {
    	logger.info("Start clearPP...");
    	String pci_token = randomString(16).toUpperCase();
    	PaymentsDetailsDTO pds_cb = generateBasicPP(BLUECARD_CODE, pci_token);
    	ProvidePaymentPreferencesResponse providePP = new ProvidePaymentPreferencesResponse();
    	try {
			providePP = providePP(pds_cb.getGin());
			deletePP(pds_cb.getGin(), providePP.getProvidepaymentpreferences().size()); //on enleve tous les PP de ce GIN => Nettoyage
		} catch (Exception e) {
			
			if (!"NOT FOUND".equals(e.getMessage())) {
				e.printStackTrace();	
			}
		}
    	logger.info("End clearPP...");
    	Assert.assertTrue(true);
    }   
	
    /**
     * 
     * @throws BusinessException
     * @throws SystemException
     * @throws com.afklm.repindpp.paymentpreference.delete.BusinessException
     * @throws com.afklm.repindpp.paymentpreference.delete.SystemException
     * @throws com.afklm.soa.stubs.w000470.v2_0_1.BusinessException
     */
    @Test
    @Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
    @Transactional
    @Rollback(true)
    public void carteAlreadyExistOk() throws BusinessException, SystemException, com.afklm.soa.stubs.w000471.v1.BusinessException, com.afklm.soa.stubs.w000470.v2_0_1.BusinessException{
    	
    	/**************************
    	 * Preparation
    	 **************************/
    	
    	logger.info("Start carteAlreadyExistOk...");
    	String pci_token = randomString(16).toUpperCase();
    	PaymentsDetailsDTO pds_cb = generateBasicPP(BLUECARD_CODE, pci_token);
    	PaymentsDetailsDTO pds_mc = generateBasicPP(MASTERCARD_CODE, pci_token);
    	PaymentsDetailsDTO pds_vi = generateBasicPP(VISA_CODE, pci_token);
    	ProvidePaymentPreferencesResponse providePP = new ProvidePaymentPreferencesResponse();
    	
    	/**************************
    	 * test CB vers Visa 
    	 **************************/
    	
    	logger.info("Start CB to Visa");
    	
    	createPP(pds_cb);
    	createPP(pds_vi); //normalement aucun erreur 
    	
    	providePP = providePP(pds_cb.getGin());
    	Assert.assertSame(null, 1, providePP.getProvidepaymentpreferences().size()); //Si on as qu'un paymentPref, c'est good
    	logger.info("CB=>Visa ok");

    	
    	/**************************
    	 * test CB vers MasterCard
    	 **************************/
    	
    	logger.info("Start CB to MasterCard");
		deletePP(pds_cb.getGin()); //on enleve tous les PP de ce GIN => Nettoyage
		providePP = null;
		
		createPP(pds_cb);
    	createPP(pds_mc); //normalement aucun erreur 
    	
    	providePP = providePP(pds_cb.getGin());
    	Assert.assertSame(null, 1, providePP.getProvidepaymentpreferences().size()); //Si on as qu'un paymentPref, c'est good
    	logger.info("CB = >MasterCard ok");
    	
    	
    	/**************************
    	 * test Visa vers CB
    	 **************************/
    	logger.info("Start Visa to CB");
		deletePP(pds_cb.getGin()); //on enleve tous les PP de ce GIN => Nettoyage
		providePP = null;
		
    	createPP(pds_vi);
    	createPP(pds_cb); //normalement aucun erreur 
    	
    	providePP = providePP(pds_cb.getGin());
    	Assert.assertSame(null, 1, providePP.getProvidepaymentpreferences().size()); //Si on as qu'un paymentPref, c'est good
    	logger.info("Visa => CB ok");

    	/**************************
    	 * test MasterCard vers CB
    	 **************************/
    	
    	logger.info("Start MasterCard to CB");
		deletePP(pds_cb.getGin()); //on enleve tous les PP de ce GIN => Nettoyage
		providePP = null;
		
		createPP(pds_mc);
    	createPP(pds_cb); //normalement aucun erreur 
    	
    	providePP = providePP(pds_cb.getGin());
    	Assert.assertSame(null, 1, providePP.getProvidepaymentpreferences().size()); //Si on as qu'un paymentPref, c'est good
    	logger.info("MasterCard => CB ok");
}
    
	/**
	 * Tests the maximum length for payment preference field preference. It should not exceed
	 * 111 characters.
	 * 
	 * @throws com.afklm.soa.stubs.w000470.v2_0_1.BusinessException
	 * @throws BusinessException
	 * @throws SystemException
	 * @throws com.afklm.soa.stubs.w000471.v1.BusinessException
	 */
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	@Transactional
	@Rollback(true)
	public void testPreferenceFieldPreferenceLength() throws com.afklm.soa.stubs.w000470.v2_0_1.BusinessException, BusinessException,
			SystemException, com.afklm.soa.stubs.w000471.v1.BusinessException {
		String pci_token = randomString(16).toUpperCase();
		PaymentsDetailsDTO visa = generateBasicPP(VISA_CODE, pci_token);
		FieldsDTO validateLength = null;
		String gin = visa.getGin();
		ProvidePaymentPreferencesResponse providePp = null;

		// clear PP
		try {
			providePp = providePP(gin);
			deletePP(gin, providePp.getProvidepaymentpreferences().size());
		} catch (BusinessException e) {
			//do-nothing		
		}

		// PP field within limit
		validateLength = new FieldsDTO();
		validateLength.setPaymentFieldCode(BILL_CITY);
		String city = RandomStringUtils.random(110, true, true);
		validateLength.setPaymentFieldPreference(city);
		visa.getFieldsdto().add(validateLength);
		try {
			createPP(visa);
		} catch (SystemException e) {
			Assert.fail(NO_EXCEPTION_TO_BE_THROWN);
		}

		// validate retrieval
		providePp = providePP(gin);
		List<ProvidePaymentPreferences> preferences = providePp.getProvidepaymentpreferences();
		assertNotNull(preferences);
		for (ProvidePaymentPreferences preference : preferences) {
			List<ProvideFields> fields = preference.getProvidefields();
			assertNotNull(fields);
			for (ProvideFields field : fields) {
				if (StringUtils.equals(BILL_CITY, field.getPaymentFieldCode())) {
					assertEquals(field.getPaymentFieldPreferences(), city);
					break;
				}
			}
		}

		// clear PP
		deletePP(gin, providePp.getProvidepaymentpreferences().size());

		// PP field exceeds limit
		validateLength = new FieldsDTO();
		validateLength.setPaymentFieldCode("BILL_ADDRESS");
		validateLength.setPaymentFieldPreference(RandomStringUtils.random(112, true, true));
		visa.getFieldsdto().add(validateLength);
		try {
			createPP(visa);
			Assert.fail(AN_EXCEPTION_TO_BE_THROWN);
		} catch (JpaSystemException e) {
			logger.info("Incorrect length was entered for PP code field");
		}
	}

	/**
	 * Tests the maximum length for payment preference field code. It should not exceed
	 * 111 characters.
	 * 
	 * @throws com.afklm.soa.stubs.w000470.v2_0_1.BusinessException
	 * @throws BusinessException
	 * @throws SystemException
	 * @throws com.afklm.soa.stubs.w000471.v1.BusinessException
	 */
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	@Transactional
	@Rollback(true)
	public void testPreferenceFieldCodeLength() throws com.afklm.soa.stubs.w000470.v2_0_1.BusinessException, BusinessException,
	SystemException, com.afklm.soa.stubs.w000471.v1.BusinessException {
		String pci_token = randomString(16).toUpperCase();
		PaymentsDetailsDTO visa = generateBasicPP(VISA_CODE, pci_token);
		FieldsDTO validateLength = null;
		String gin = visa.getGin();
		ProvidePaymentPreferencesResponse providePp = null;

		// clear PP
		try {
			providePp = providePP(gin);
			deletePP(gin, providePp.getProvidepaymentpreferences().size());
		} catch (BusinessException e) {
			//do-nothing		
		}

		// PP field within limit
		validateLength = new FieldsDTO();
		String code = RandomStringUtils.random(110, true, false);
		validateLength.setPaymentFieldCode(code);
		String pref = RandomStringUtils.random(50, true, true);
		validateLength.setPaymentFieldPreference(pref);
		visa.getFieldsdto().add(validateLength);
		try {
			createPP(visa);
		} catch (SystemException e) {
			Assert.fail(NO_EXCEPTION_TO_BE_THROWN);
		}
		
		// validate retrieval
		providePp = providePP(gin);
		List<ProvidePaymentPreferences> preferences = providePp.getProvidepaymentpreferences();
		assertNotNull(preferences);
		for (ProvidePaymentPreferences preference : preferences) {
			List<ProvideFields> fields = preference.getProvidefields();
			assertNotNull(fields);
			for (ProvideFields field : fields) {
				if (StringUtils.equals(code, field.getPaymentFieldCode())) {
					assertEquals(pref, field.getPaymentFieldPreferences());
					break;
				}
			}
		}
		
		// clear PP
		deletePP(gin, providePp.getProvidepaymentpreferences().size());
		
		// PP field exceeds limit
		validateLength = new FieldsDTO();
		validateLength.setPaymentFieldCode(RandomStringUtils.random(112, true, false));
		validateLength.setPaymentFieldPreference(pref);
		visa.getFieldsdto().add(validateLength);
		try {
			createPP(visa);
			Assert.fail(AN_EXCEPTION_TO_BE_THROWN);
		} catch (JpaSystemException e) {
			logger.info("Incorrect length was entered for PP preference field");
		}
	}

    /**
     * Test if maxPaymentPreference is OK (Max 10) !
     * @throws BusinessException
     * @throws SystemException
     */
    @Test
    @Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
    public void maxPaymentPreferenceOk() throws BusinessException, SystemException {
    	logger.info("Start maxPaymentPreferenceOk...");
    	
    	for(int i = 0; i < 11; i++) {
    		String pci_token = randomString(16).toUpperCase();
    		PaymentsDetailsDTO pds_cb = generateBasicPP(BLUECARD_CODE, pci_token);
    		try {
				createPP(pds_cb);
			} catch (com.afklm.soa.stubs.w000470.v2_0_1.BusinessException e) {
		    	ProvidePaymentPreferencesResponse providePP = new ProvidePaymentPreferencesResponse();
		    	providePP = providePP(pds_cb.getGin());
				Assert.assertSame(null, 10, providePP.getProvidepaymentpreferences().size()); //Si on as qu'un paymentPref, c'est good
				logger.info("maxPayment OK !");
			} catch(SystemException e) {
				logger.info("Error maxPaymentPreferenceOk, test failed !");
				Assert.assertTrue(false);
			}
    	}
    	logger.info("End maxPaymentPreferenceOk...");
    }

    @Test
    public void testPaymentPreferenceWithCCNRNumericOk() throws BusinessException, SystemException {
    	logger.info("Start testPaymentPreferenceWithCCNRNumericOk...");
    	
		String pci_token = RandomStringUtils.randomNumeric(16);
		PaymentsDetailsDTO pds_cb = generateBasicPP(VISA_CODE, pci_token);
		pds_cb.setGin("400351914055");
		
		try {
			createPP(pds_cb);
		} catch (com.afklm.soa.stubs.w000470.v2_0_1.BusinessException e) {
			
			Assert.assertEquals("TECHNICAL ERROR: CC_NR or PCI_TOKEN value cannot be numeric, must be a token", e.getMessage());
			
			logger.info("maxPayment OK !");
		} catch(SystemException e) {
			logger.info("Error maxPaymentPreferenceOk, test failed !");
			Assert.assertTrue(false);
		}

    	logger.info("End maxPaymentPreferenceOk...");
    }

    
    //TODO : FINISH TEST UNIT
    
    
    private PaymentsDetailsDTO generateBasicPP(String fieldCard, String pci_token) { //genere un PaymentDetail avec un type de carte specifique
    	PaymentsDetailsDTO pd = new PaymentsDetailsDTO();
    	pd.setGin("400415094784");
    	pd.setCardName("testImpl");
    	pd.setPaymentGroup("CC");
    	pd.setPreferred("Y");
    	pd.setCorporate("Y");
    	pd.setIpAdresse("127.0.0.1");
    	pd.setSignatureCreation("UnitTest");
    	pd.setSiteCreation("UnitTest");
    	pd.setPointOfSell("GB");
    	
    	switch (fieldCard) {
		case MASTERCARD_CODE:
			pd.setPaymentMethod(mc_l.get(new Random().nextInt(mc_l.size())));
			break;

		case VISA_CODE:
			pd.setPaymentMethod(visa_l.get(new Random().nextInt(visa_l.size())));
			break;

		case BLUECARD_CODE:
			pd.setPaymentMethod(cb_l.get(new Random().nextInt(cb_l.size())));
			break;

		default:
			break;
		}
    	
    	Set<FieldsDTO> list = new HashSet<FieldsDTO>();
    	
    	FieldsDTO fname = new FieldsDTO();
    	fname.setPaymentFieldCode("firstName");
    	fname.setPaymentFieldPreference("Clark");
    	
    	FieldsDTO lname = new FieldsDTO();
    	lname.setPaymentFieldCode("lastName");
    	lname.setPaymentFieldPreference("Kent");

    	FieldsDTO cc = new FieldsDTO();
    	cc.setPaymentFieldCode("PCI_TOKEN");
    	cc.setPaymentFieldPreference(pci_token); //genere un PCI_TOKEN UNIQUE
    	
    	
    	list.add(fname);
    	list.add(lname);
    	list.add(cc);
    
    	pd.setFieldsdto(list);
    	return pd;
    }

    private CreatePaymentPreferencesResponse createPP(PaymentsDetailsDTO pdDTO) throws com.afklm.soa.stubs.w000470.v2_0_1.BusinessException, SystemException{

    	logger.info("Create for gin : "+pdDTO.getGin());
    	CreatePaymentPreferencesRequest request = new CreatePaymentPreferencesRequest();
    	
    	request.setGin(pdDTO.getGin());
    	request.setCardName(pdDTO.getCardName());
    	request.setPaymentGroupType(pdDTO.getPaymentGroup());
    	request.setPreferred(pdDTO.getPreferred());
    	request.setCorporate(pdDTO.getCorporate());
    	request.setIpAdress(pdDTO.getIpAdresse());
    	request.setSignature(pdDTO.getSignatureCreation());
    	request.setSignatureSite(pdDTO.getSiteCreation());
    	request.setPointOfSale(pdDTO.getPointOfSell());
    	request.setPaymentMethod(pdDTO.getPaymentMethod());
    	request.setAirlinePaymentPref("KL");
    	
    	for(FieldsDTO fdto : pdDTO.getFieldsdto()) {
			CreateFields fields = new CreateFields();
			fields.setPaymentFieldCode(fdto.getPaymentFieldCode());
			fields.setPaymentFieldPreferences(fdto.getPaymentFieldPreference());
			request.getCreateListfields().add(fields);
    	}
    	
    	CreatePaymentPreferencesResponse response = createOrReplacePaymentPreferencesImpl.createOrReplacePaymentPreferences(request);
    	Assert.assertNotEquals(null, response);
    	
    	return response;
    	
    }


    private ProvidePaymentPreferencesResponse providePP(String gin) throws BusinessException, SystemException{

    	logger.info("Provide for gin : "+gin);
    	ProvidePaymentPreferencesRequest request = new ProvidePaymentPreferencesRequest();
    	request.setGin(gin);
    	    	 
    	ProvidePaymentPreferencesResponse response = providePaymentPreferencesImpl.providePaymentPreferences(request);
    	Assert.assertNotEquals(null, response);
    	
    	return response;
    	
    }
    
    private void deletePP(String gin) throws com.afklm.soa.stubs.w000471.v1.BusinessException, SystemException{

    	logger.info("Delete all PP for gin : "+gin);
    	DeletePaymentPreferencesRequest request = new DeletePaymentPreferencesRequest();
    	request.setGin(gin);
    	request.setSignature("UnitTest");
    	request.setSignatureSite("UnitTest");
    	request.setIpAdress("127.0.0.1");
    	    	 
    	DeletePaymentPreferencesResponse response = deletePaymentPreferencesImpl.deletePaymentPreferences(request);
    	Assert.assertNotEquals(null, response);
    
    	
    }
    
    private void deletePP(String gin, int length) throws com.afklm.soa.stubs.w000471.v1.BusinessException, SystemException{

    	logger.info("Delete all PP for gin : "+gin);
    	DeletePaymentPreferencesRequest request = new DeletePaymentPreferencesRequest();
    	request.setGin(gin);
    	request.setSignature("UnitTest");
    	request.setSignatureSite("UnitTest");
    	request.setIpAdress("127.0.0.1");
    	    	 
    	DeletePaymentPreferencesResponse response = null;
    	for (int i = 0; i < length; i++) {
    		response = deletePaymentPreferencesImpl.deletePaymentPreferences(request);
    	}
    	Assert.assertNotEquals(null, response);
    
    	
    }
	
	 private String randomString( int len ){
		   StringBuilder sb = new StringBuilder( len );
		   for( int i = 0; i < len; i++ ) 
		      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		   return sb.toString();
		}
}
