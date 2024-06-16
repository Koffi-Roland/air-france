package com.airfrance.repind.service;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.IdentifierOptionTypeEnum;
import com.airfrance.ref.type.UpdateModeEnum;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.individu.AccountDataDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.MyAccountUpdateConnectionDataDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.individu.adh.individualinformation.IndividualInformationRequestDTO;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerRequestDTO;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerResponseDTO;
import com.airfrance.repind.dto.individu.myaccountcustomerdata.ReturnDetailsDTO;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdRequestDTO;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdResponseDTO;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>Title : MyAccountDSTest.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class MyAccountDSTest {

	public static String GIN = "000000000124";

	private static String GIN_SAMPLE = "400350914750";
	// private static String FP_SAMPLE = "2075275321";
	private static String FP_SAMPLE = "001024803230";
	// private static String AI_SAMPLE = "000024AA";
	private static String AI_SAMPLE = "088669AG";

	/*
	// REPFIRM-61 : FP non disponible suite purge ou autre
	private static String FP_SAMPLE = "1024815060";
	// private static String FP_SAMPLE = "2075275321";
	private static String AI_SAMPLE = "402703AD";
	// private static String AI_SAMPLE = "000024AA";
	 */

	/** logger */
	private static final Log logger = LogFactory.getLog(MyAccountDSTest.class);


	/**
	 * Email Domain Servicde
	 */
	@Autowired
	private MyAccountDS myAccountDS;

	@Autowired
	private AccountDataDS AccountDataDS;
		
	@After
	public void tearDown() {
	}

	@Before
	public void setUp() throws JrafDomainException {		
	}
	/**
	 * Test de la methode ProvideGinByIdentifier
	 */
	@Test
	@Rollback(true)
	public void testProvideGinByIdentifier() {
		try {
			String identifierType = "EM";        	
			String identifier = "monemail@airfrance.fr";

			String FBidentifierType = "FP";
			String FBidentifier = "001024800032";

			String MyAccntType = "MA";
			String mail = "monemail@email.com";
			String myaccountID = "000011AA";
			String personnalizedId = "identperso54";


			ProvideGinForUserIdRequestDTO request = new ProvideGinForUserIdRequestDTO();
			ProvideGinForUserIdResponseDTO response = null;

			//        	request.setIdentifier(identifier);
			//        	request.setIdentifierType(identifierType);
			//        	response = myAccountDS.provideGinForUserId(request);
			//        	logger.info("id trouvé : "+response.getGin()); 
			//        	
			//        	request.setIdentifier(FBidentifier);
			//        	request.setIdentifierType(FBidentifierType);
			//        	response =  myAccountDS.provideGinForUserId(request);
			//        	logger.info("id trouvé : "+response.getGin()); 

			request.setIdentifier(personnalizedId);
			request.setIdentifierType(MyAccntType);
			response =  myAccountDS.provideGinForUserId(request);
			logger.info("id trouvé : "+response.getGin()); 

		} catch (JrafDomainException e) {
			Assert.fail("Erreur non prevue");
			logger.error(e);
		}
	}

	@Test
	@Rollback(true)
	public void testSignatureUpdate() throws JrafDomainException{
		AccountDataDTO dtoResponse = new AccountDataDTO();
		dtoResponse = AccountDataDS.getByGin("000000000264");
		
		if(dtoResponse != null){
			Date date = dtoResponse.getDateModification();
			String signature = dtoResponse.getSignatureModification();
			
			dtoResponse.setSignatureModification("test");
			dtoResponse.setDateModification(new Date());

			AccountDataDS.update(dtoResponse);
			dtoResponse = AccountDataDS.getByGin("000000000264");
			System.out.println(date + " " + dtoResponse.getDateModification());
			Assert.assertTrue(dtoResponse.getDateModification() != date);
			Assert.assertTrue(!dtoResponse.getSignatureModification().equals(signature));
			Assert.assertTrue(dtoResponse.getSignatureModification().equals("test"));
		}
	}

	/**
	 * Test de la methode SearchIndividualInformation
	 */
	@Test
	@Rollback(true)
	public void testSearchIndividualInformationByGIN() {

		IndividuDTO individual = null;

		try {

			IndividualInformationRequestDTO request = new IndividualInformationRequestDTO();
			request.setIdentificationNumber(GIN_SAMPLE);
			request.setOption(IdentifierOptionTypeEnum.GIN.toString());

			individual = myAccountDS.searchIndividualInformation(request);

			if(logger.isDebugEnabled() && individual.getNom() != null) {
				logger.debug("NOM : " + individual.getNom());
			}

		} catch (JrafDomainException e) {
			Assert.fail("Erreur non prevue");
			logger.error(e);
		}

		Assert.assertNotNull("Expected 1 result", individual);

	}

	@Test
	@Rollback(true)
	public void testSearchIndividualInformationByAC() {

		IndividuDTO individual = null;

		try {
			IndividualInformationRequestDTO request = new IndividualInformationRequestDTO();
			request.setIdentificationNumber(FP_SAMPLE);
			request.setOption(IdentifierOptionTypeEnum.ALL_CONTRACTS.toString());

			individual = myAccountDS.searchIndividualInformation(request);

			if(logger.isDebugEnabled() && individual.getNom() != null) {
				logger.debug("NOM : " + individual.getNom());
			}

		} catch (JrafDomainException e) {
			Assert.fail("Erreur non prevue");
			logger.error(e);
		}

		Assert.assertNotNull("Expected a result", individual);

	}

	@Test
	@Rollback(true)
	public void testSearchIndividualInformationByAI() {

		IndividuDTO individual = null;

		try {
			IndividualInformationRequestDTO request = new IndividualInformationRequestDTO();
			request.setIdentificationNumber(AI_SAMPLE);
			request.setOption(IdentifierOptionTypeEnum.ANY_MYACCOUNT.toString());

			individual = myAccountDS.searchIndividualInformation(request);

			if(logger.isDebugEnabled() && individual.getNom() != null) {
				logger.debug("NOM : " + individual.getNom());
			}

		} catch (JrafDomainException e) {
			Assert.fail("Erreur non prevue");
			logger.error(e);
		}

		Assert.assertNotNull("Expected a result", individual);

	}

	/**
	 * Test de la methode deleteMyAccountCustomerConnectionData
	 */
	@Test(expected = Test.None.class /* no exception expected */)
	@Rollback(true)
	public void testDeleteMyAccountCustomerConnectionData() {
		/*	MyAccountCustomerConnectionDataDTO connectionData = new MyAccountCustomerConnectionDataDTO();
		try {
			connectionData.setGin("000000012094");			
			String response = myAccountDS.deleteMyAccountCustomerConnectionData(connectionData);
			logger.info(response);
		} catch (JrafDomainException e) {
			e.printStackTrace();
		}*/

	}

	/**
	 * Test de la methode enrollMyAccountCustomer
	 */
	@Test(expected = Test.None.class /* no exception expected */)
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testenrollMyAccountCustomer() {
		MyAccountCustomerRequestDTO requestDTO = new MyAccountCustomerRequestDTO();
		requestDTO.setEmailIdentifier("testenroll1@gmail.com");
		requestDTO.setCivility("MR");
		requestDTO.setFirstName("FirstName1");
		requestDTO.setLastName("LastName1");
		SignatureDTO signature = new SignatureDTO();
		signature.setSite("QVI");
		signature.setSignature("T412211");
		requestDTO.setSignature(signature);
		try {
			myAccountDS.enrollMyAccountCustomer(requestDTO, true, false);
		} catch (JrafDomainException e) {
			// e.printStackTrace();
			logger.fatal(e);
		}

	}    

	/**
	 * Test de la methode updateMyAccountCustomer
	 */
	@Test(expected = Test.None.class /* no exception expected */)
	// @Rollback(true)
	public void testUpdateMyAccountCustomer() {
		// Individu
		IndividuDTO individu = new IndividuDTO();
		individu.setSgin("000000001443");

		// Telecom
		List<TelecomsDTO> telecom = new ArrayList<TelecomsDTO>();
		TelecomsDTO telecomDTO = new TelecomsDTO();
		telecomDTO.setSnumero("0442278713");
		telecom.add(telecomDTO);

		// Email 
		List<EmailDTO> email = new ArrayList<EmailDTO>();

		// Adresse Postale
		List<PostalAddressDTO> address = new ArrayList<PostalAddressDTO>();
		PostalAddressDTO postAdrDTO = new PostalAddressDTO();
		postAdrDTO.setSno_et_rue("61 BOULEVARD CARNOT");
		address.add(postAdrDTO);

		// MyAccountCustomerConnectionData
		MyAccountUpdateConnectionDataDTO myAccountData = null;

		// Signature
		SignatureDTO signature = new SignatureDTO();

		try {
			ReturnDetailsDTO result  = myAccountDS.updateMyAccountCustomer(individu, address, telecom, email, myAccountData, null, "C",  signature, "V", UpdateModeEnum.REPLACE.toString(), null, null);
			boolean success = result.getErasePayment();
			logger.info(success);
		} catch (JrafDomainException e) {
			// e.printStackTrace();
			logger.fatal(e);
		}

	}

	/**
	 * Test de la methode enrollMyAccountCustomer
	 * @throws JrafDomainException 
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testenrollMyAccountCustomer_AddEmailCom() throws JrafDomainException {
		MyAccountCustomerRequestDTO requestDTO = new MyAccountCustomerRequestDTO();
		String email = generateEmail();
		requestDTO.setEmailIdentifier(email);
		requestDTO.setGin("400015676976");
		requestDTO.setCivility("MR");
		requestDTO.setFirstName("JEAN LUC");
		requestDTO.setLastName("GUINANT");
		SignatureDTO signature = new SignatureDTO();
		signature.setSite("QVI");
		signature.setSignature("T412211");
		// signature.setTypeSignature("M");
		requestDTO.setSignature(signature);

		MyAccountCustomerResponseDTO response = myAccountDS.enrollMyAccountCustomer(requestDTO, true, false);

		Assert.assertNotNull(response);
		Assert.assertEquals("400015676976", response.getGin());
		Assert.assertEquals(email.toLowerCase(), response.getEmail());

	}  

	@Test
	@Rollback(true)
	public void testenrollMyAccountCustomer_AddEmailComBis() throws JrafDomainException {
		MyAccountCustomerRequestDTO requestDTO = new MyAccountCustomerRequestDTO();
		String email = generateEmail();
		requestDTO.setEmailIdentifier(email);
		requestDTO.setGin("400400029561");
		requestDTO.setCivility("MR");
		requestDTO.setFirstName("ANTONIO");
		requestDTO.setLastName("BONGIOVANNI");
		SignatureDTO signature = new SignatureDTO();
		signature.setSite("QVI");
		signature.setSignature("T412211");
		// signature.setTypeSignature("M");
		requestDTO.setSignature(signature);

		MyAccountCustomerResponseDTO response = myAccountDS.enrollMyAccountCustomer(requestDTO, true, false);

		Assert.assertNotNull(response);
		Assert.assertEquals("400400029561", response.getGin());
		Assert.assertEquals(email.toLowerCase(), response.getEmail());

	}  


	private String generateEmail() {
		StringBuilder sb = new StringBuilder();

		sb.append(RandomStringUtils.randomAlphanumeric(8));
		sb.append("@bidon.fr");

		return sb.toString();
	}

	@Test
	@Rollback(true)
	public void testSearchIndividualInformationByGIGYAOnly() {

		IndividuDTO individual = null;

		try {
			IndividualInformationRequestDTO request = new IndividualInformationRequestDTO();
			request.setIdentificationNumber("AFKL_201412121811__guid_272CcgAV72vKICF_NEW_IiDmhwMjh_xi7PIU");
			request.setOption(IdentifierOptionTypeEnum.GIGYAID.toString());
			// Call "All" because it's a new pop
			individual = myAccountDS.searchIndividualInformationAll(request);

		} catch (JrafDomainException e) {
			Assert.fail("Erreur non prevue");
			logger.error(e);
		}

		Assert.assertNotNull("Expected a result", individual);
		Assert.assertNotNull(individual.getSgin());
	}

	@Test
	@Rollback(true)
	public void testSearchIndividualInformationByGIGYAMyA() {

		IndividuDTO individual = null;

		try {
			IndividualInformationRequestDTO request = new IndividualInformationRequestDTO();
			request.setIdentificationNumber("AFKL_201412110804__guid_Byzb9rctyUqwk8nR4lMRrZaDfuSmd0ed5s4p");
			request.setOption(IdentifierOptionTypeEnum.GIGYAID.toString());

			individual = myAccountDS.searchIndividualInformation(request);

		} catch (JrafDomainException e) {
			Assert.fail("Erreur non prevue");
			logger.error(e);
		}

		Assert.assertNotNull("Expected a result", individual);
		Assert.assertNotNull(individual.getSgin());
	}

	@Test
	@Rollback(true)
	public void testSearchIndividualInformationByFACEBOOK() {

		IndividuDTO individual = null;

		try {
			IndividualInformationRequestDTO request = new IndividualInformationRequestDTO();
			request.setIdentificationNumber("FACEBOOK_ID_Exempledqsqdhqhslkdhsqui");
			request.setOption(IdentifierOptionTypeEnum.FACEBOOKID.toString());
			// Call "All" because it's a new pop
			individual = myAccountDS.searchIndividualInformationAll(request);

		} catch (JrafDomainException e) {
			Assert.fail("Erreur non prevue");
			logger.error(e);
		}

		Assert.assertNotNull("Expected a result", individual);
		Assert.assertNotNull(individual.getSgin());
	}
    
	
	@Test
	@Rollback(true)
	@Ignore("No reliable datas")
	public void testSearchIndividualInformationByTWITTER() {

		IndividuDTO individual = null;

		try {
			IndividualInformationRequestDTO request = new IndividualInformationRequestDTO();
			request.setIdentificationNumber("");
			request.setOption(IdentifierOptionTypeEnum.TWITTERID.toString());
			// Call "All" because it's a new pop
			individual = myAccountDS.searchIndividualInformationAll(request);

		} catch (JrafDomainException e) {
			Assert.fail("Erreur non prevue");
			logger.error(e);
		}

		Assert.assertNotNull("Expected a result", individual);
		Assert.assertNotNull(individual.getSgin());
	}

	@Test
	@Rollback(true)
	@Ignore("Enum still used in parameter control")
	public void testSearchIndividualInformationByNotExisting() {

		IndividuDTO individual = null;

		try {
			IndividualInformationRequestDTO request = new IndividualInformationRequestDTO();
			request.setIdentificationNumber("moazipeoikljh");
			request.setOption("RANDOM");
			// Call "All" because it's a new pop
			individual = myAccountDS.searchIndividualInformationAll(request);

		} catch (JrafDomainException e) {
			Assert.fail("Erreur non prevue");
			logger.error(e);
		}

		Assert.assertNull("Should not have a result", individual);
	}


	/**
	 * Test de la methode testUpdateMyAccountCustomerKLM
	 */
	@Test
	@Rollback(true)
	public void testUpdateMyAccountCustomerKLM() {
		// Individu
		IndividuDTO individu = new IndividuDTO();
		individu.setSgin("400399318814");
		individu.setCodeLangue("EN");

		// Email 
		List<EmailDTO> email = new ArrayList<EmailDTO>();
		
		EmailDTO em = new EmailDTO();
		em.setCodeMedium("D");
		em.setEmail("ais.cristian@yahoo.com");
		em.setAutorisationMailing("N");
		email.add(em);
		
		// Signature
		SignatureDTO signature = new SignatureDTO();
		signature.setApplicationCode("ISI");
		signature.setSignature("MYA");
		signature.setSite("KLM");

		try {
			ReturnDetailsDTO result  = myAccountDS.updateMyAccountCustomer(individu, null, null, email, null, null, "T",  signature, "V", UpdateModeEnum.REPLACE.toString(), null, null);
			
			Assert.assertNotNull(result);
			Assert.assertNull(result.getDetailedCode());
			Assert.assertNull(result.getLabelCode());

		} catch (JrafDomainException e) {
			logger.fatal(e);
		}

	}
}
