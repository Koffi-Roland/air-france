package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.ExternalIdentifierRequest;
import com.afklm.soa.stubs.w000442.v8.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8.request.TelecomRequest;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.ExternalIdentifier;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.ExternalIdentifierData;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.IndividualInformationsV3;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.Telecom;
import com.airfrance.ref.type.ProcessEnum;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplV8ForExternalIdentifierTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV8ForExternalIdentifierTest.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String APPLICATION_CODE = "B2C";

	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v8Bean")
	private CreateOrUpdateIndividualImplV8 createOrUpdateIndividualImplV8;

	private String generateString() {
		return RandomStringUtils.randomAlphabetic(10);
	}

	private String generateEmail() {
		return generateString() + "@free.nul";
	}

	private String generatePhone() {
		return "0497283" + RandomStringUtils.randomNumeric(3);
	}

	@Test
	public void testCreateOrUpdateIndividual_Create_ExternalIdentifier_CasBase_2GIGYA_Differents() throws BusinessErrorBlocBusinessException {

		String numeroGigya = "NumeroIDGIGYA_AFKL_" + generatePhone() + "_" + generateString();

		insertAGigya(numeroGigya);
		
		numeroGigya = "NumeroIDGIGYA_AFKL_" + generatePhone() + "_" + generateString();
		
		insertAGigya(numeroGigya);
		
		Assert.assertTrue(true);
	}

	@Test
	public void testCreateOrUpdateIndividual_Create_ExternalIdentifier_CasBase_GIGYA_ExistInSocialNetworkData() throws BusinessErrorBlocBusinessException {

		String numeroGigyaExistInSocialNetworkData = "AFKL_201509181351__guid_zV5EvSaLk6DTBZEb04-ZtZJe4Wauh7l6rNPN";

		String ginFound = insertAGigya(numeroGigyaExistInSocialNetworkData);
		
		Assert.assertNotNull(ginFound);
		Assert.assertNotEquals("", ginFound);
		Assert.assertTrue(ginFound.startsWith("0000"));
	}

	@Test
	public void testCreateOrUpdateIndividual_Create_ExternalIdentifier_CasBase_GIGYA_KEY_InSocialNetworkData() throws BusinessErrorBlocBusinessException {

		String numeroGigyaExistInSocialNetworkData = "AFKL_201605181128__guid_lw2zbgNqrT2cw1HvHfT6OX0JRIrVPmgnL1TW";
		String key = "USED_BY_AF";
		String value = "Y";
		
		String ginFound = insertAGigya(numeroGigyaExistInSocialNetworkData, key, value);
		
		Assert.assertNotNull(ginFound);
		Assert.assertNotEquals("", ginFound);
		Assert.assertTrue(ginFound.startsWith("000"));
	}

	@Test
	public void testCreateOrUpdateIndividual_Create_ExternalIdentifier_CasBase_GIGYA_KEY_VALUE_InSocialNetworkData() throws BusinessErrorBlocBusinessException {

		String numeroGigyaExistInSocialNetworkData = "AFKL_201505211440__guid_jyTKRYkOH4QTBVloFoxplaAldKy91dPIO4jm";
		String key = "USED_BY_KL";
		String value = "N";
		
		String ginFound = insertAGigya(numeroGigyaExistInSocialNetworkData, key, value);
		
		Assert.assertNotNull(ginFound);
		Assert.assertNotEquals("", ginFound);
		Assert.assertTrue(ginFound.startsWith("9"));
	}

	@Test
	public void testCreateOrUpdateIndividual_Create_ExternalIdentifier_CasKO_GIGYA_KEY_NotValid() throws BusinessErrorBlocBusinessException {

		String numeroGigyaExistInSocialNetworkData = "AFKL_201412110804__guid_Byzb9rctyUqwk8nR4lMRrZaDfuSmd0ed5s4p";
		String key = "USED_BY_FR";
		String value = "Y";
		
		try {
			insertAGigya(numeroGigyaExistInSocialNetworkData, key, value);
		
			Assert.fail("On devrait avoir un message d erreur, KEY USED_BY_FR non valide");
		} catch (BusinessErrorBlocBusinessException e) {

			Assert.assertNotNull(e.getFaultInfo());
			Assert.assertNotNull(e.getFaultInfo().getBusinessError());
			
			Assert.assertEquals("ERROR_715", e.getFaultInfo().getBusinessError().getErrorCode().toString());
			Assert.assertEquals("INVALID EXTERNAL IDENTIFIER DATA KEY", e.getFaultInfo().getBusinessError().getErrorLabel());
			Assert.assertTrue(e.getFaultInfo().getBusinessError().getErrorDetail().startsWith("Invalid external identifier key value:"));
		}
	}

	@Test
	public void testCreateOrUpdateIndividual_Create_ExternalIdentifier_CasBase_GIGYA_KEY_VALUE_InSocialNetworkData_IN() throws BusinessErrorBlocBusinessException {

		String numeroGigyaExistInSocialNetworkData = "AFKL_201503130948__guid_E3Rj2rPFS8p7SsON3X_8hhz5jFtQcp3i6lEY";
		String key = "USED_BY_KL";
		String value = "Y";
		
		String ginFound = insertAGigya(numeroGigyaExistInSocialNetworkData, key, value);
		
		Assert.assertNotNull(ginFound);
		Assert.assertNotEquals("", ginFound);
		Assert.assertTrue(ginFound.startsWith("9"));
	}
	
	@Test
	public void testCreateOrUpdateIndividual_Create_ExternalIdentifier_CasKO_2GIGYA_Identique() {

		String numeroGigya = "NumeroIDGIGYA_AFKL_" + generatePhone() + "_" + generateString();
		String ginJustInserted = "";
		
		try {
			String gin = insertAGigya(numeroGigya);
			Assert.assertNotNull(gin);
			Assert.assertTrue(gin.startsWith("92"));
			ginJustInserted = gin;
			
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.fail("On ne devrait pas avoir de message d erreur, le GIGYA est unique dans le cas présent");
		}
		
		try {
			String gin = insertAGigya(numeroGigya);
			
			Assert.assertEquals(ginJustInserted, gin);
			
		} catch (BusinessErrorBlocBusinessException e) {

			Assert.fail("On ne devrait pas avoir de message d erreur, car on recupere le GIGYA precedement insere");
		}
	}

	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testCreateOrUpdateIndividual_Update_ExternalIdentifier_CasBase() throws BusinessErrorBlocBusinessException {

		String numeroGin = "920000001405";
		String numeroGigya = "NumeroIDGIGYA_AFKL_" + generatePhone() + "_" + generateString();

		updateAGigya(numeroGin, numeroGigya);
		
		Assert.assertTrue(true);
	}

	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testCreateOrUpdateIndividual_Update_ExternalIdentifier_CasBase_Individu() throws BusinessErrorBlocBusinessException {

		String numeroGin = "400413556501";
		String numeroGigya = "NumeroIDGIGYA_AFKL_" + generatePhone() + "_" + generateString();

		updateAGigya(numeroGin, numeroGigya);
		
		Assert.assertTrue(true);
	}
	
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testCreateOrUpdateIndividual_Update_ExternalIdentifier_CasOK_2GIGYA_Identique() throws BusinessErrorBlocBusinessException {

		String numeroGin = "920000001324";
		String numeroGigya = "NumeroIDGIGYA_AFKL_" + generatePhone() + "_" + generateString();

		boolean inserted = false;
		
		inserted = updateAGigya(numeroGin, numeroGigya);
		
		Assert.assertTrue(inserted);
		
		inserted = updateAGigya(numeroGin, numeroGigya);
		
		Assert.assertTrue(inserted);
	}

	@Test
	@Ignore
	public void testCreateOrUpdateIndividual_Update_ExternalIdentifier_CasOK_CivilityLastNameFirstName() throws BusinessErrorBlocBusinessException {

		String numeroGigya = "NumeroIDGIGYA_AFKL_" + generatePhone() + "_" + generateString();
		String gin = "";
		
		try {
			gin = insertAGigya(numeroGigya);
			Assert.assertNotNull(gin);
			Assert.assertTrue(gin.startsWith("92"));
			
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.fail("On ne devrait pas avoir de message d erreur, le GIGYA est unique dans le cas présent");
		}
		
		if (updateAnIndividual(gin, numeroGigya, "TOI", "MOI")) {
			Assert.assertTrue(true);
		} else {
			Assert.fail("Error on Update");
		}
	}
	
	@Test
	public void testCreateOrUpdateIndividual_Delete_ExternalIdentifier_CasDeBase_Create() throws BusinessErrorBlocBusinessException {

		String numeroGigya = "NumeroIDGIGYA_1234567890";
		
		try {
			String ginCreated = insertAGigya(numeroGigya);

			Assert.assertNotNull(ginCreated);
			Assert.assertNotEquals("", ginCreated);
			Assert.assertTrue(ginCreated.startsWith("92"));
			
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.fail("On ne devrait pas avoir de message d erreur, le GIGYA est cree");
		}
	}

	@Test
	public void testCreateOrUpdateIndividual_Delete_ExternalIdentifier_CasDeBase_Update() throws BusinessErrorBlocBusinessException {

		String numeroGigya = "NumeroIDGIGYA_AFKL_0444591440_COfkBEQWHJ";
		String numeroGin = "920000001884";
		
		try {
			boolean updated = updateAGigya(numeroGin, numeroGigya);
			Assert.assertTrue(updated);
			
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.fail("On ne devrait pas avoir de message d erreur, le GIGYA est present");
		}
	}
	
	@Test
	public void testCreateOrUpdateIndividual_Delete_ExternalIdentifier_CasDeBase_2GIGYA() throws BusinessErrorBlocBusinessException {

		String numeroGigya = "NumeroIDGIGYA_AFKL_0416173848_HxmpjpicQI";
		String numeroGigya2 = "NumeroIDGIGYA_AFKL_0447323502_ZewqJPRJcl";
		String numeroGin = "920000001394";
		
		try {
			boolean updated = updateAGigya(numeroGin, numeroGigya, numeroGigya2);
			Assert.assertTrue(updated);
			
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.fail("On ne devrait pas avoir de message d erreur, le GIGYA est present");
		}
	}
	
	@Ignore
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Update_Individual_CasDeBase_WithGIGYA() throws BusinessErrorBlocBusinessException {

		String numeroGigya = "NumeroIDGIGYA_AFKL_" + generatePhone() + "_" + generateString();
		String numeroGin = "400300011376";
		
		// TODO : A terminer, tester l update d un individu qui a des Externals afin de profiter dans ce cas precis du delete 
		
		boolean updated = createOrUpdateAnIndividual(ProcessEnum.I.getCode(), numeroGin, numeroGigya, "");
		Assert.assertTrue(updated);
	}


	@Test
	@Rollback(value = true)
	public void testCreateOrUpdateIndividual_Update_ExternalIdentifier_CivilityNull() throws BusinessErrorBlocBusinessException{
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode(APPLICATION_CODE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("920000007473");
		indInfo.setCivility(null);

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
		ExternalIdentifier ei = new ExternalIdentifier();
		ei.setIdentifier("ExternalIdIntegrationTest");
		ei.setType("PNM_ID");
		eir.setExternalIdentifier(ei);

		request.getExternalIdentifierRequest().add(eir);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	// Mise a jour  

	private boolean updateAnIndividual(String numeroGin, String numeroGigya, String firstName, String lastName) {
		
		boolean update = false;
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);


		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(numeroGin);
		indInfo.setCivility("M.");
		indInfo.setStatus("V");
		indInfo.setFirstNameSC(firstName);
		indInfo.setLastNameSC(lastName);

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Social
		ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
		ExternalIdentifier ei = new ExternalIdentifier();
		ei.setIdentifier(numeroGigya);
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifier(ei);

		ExternalIdentifierData eid = new ExternalIdentifierData();
		eid.setKey("USED_BY_KL");
		eid.setValue("Y");
		eir.getExternalIdentifierData().add(eid);

		request.getExternalIdentifierRequest().add(eir);

		// WS call
		// TODO workaround for maven issue with Bamboo
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
		}
		// Tests
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		
		update = response.isSuccess();

		logger.info("updateAGigya " + response.getGin());
	
		return update;
	}
	
	private boolean updateAGigya(String numeroGin, String numeroGigya) throws BusinessErrorBlocBusinessException {
		
		return updateAGigya(numeroGin, numeroGigya, null);
	}

	private boolean createOrUpdateAnIndividual(String process, String numeroGin, String numeroGigya, String numeroGigya2) {

		boolean update = false;
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		request.setProcess(process);

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);


		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		
		if (numeroGin != null && !"".equals(numeroGin)) {
			indInfo.setIdentifier(numeroGin);
		}
		indInfo.setStatus("V");
		indInfo.setGender("M");
		
		if (process.equals(ProcessEnum.I.getCode())) {
			indInfo.setCivility("MR");
			indInfo.setLastNameSC("VON HAEFEN");
			indInfo.setFirstNameSC("OLE");
		} else if (process.equals(ProcessEnum.E.getCode())) {
			indInfo.setCivility("M.");
		}

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Social
		ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
		ExternalIdentifier ei = new ExternalIdentifier();
		ei.setIdentifier(numeroGigya);
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifier(ei);

		ExternalIdentifierData eid = new ExternalIdentifierData();
		eid.setKey("USED_BY_KL");
		eid.setValue("Y");
		eir.getExternalIdentifierData().add(eid);
		request.getExternalIdentifierRequest().add(eir);
		
		if (numeroGigya2 != null && !"".equals(numeroGigya2)) {
			
			ExternalIdentifierRequest eir2 = new ExternalIdentifierRequest();
			ExternalIdentifier ei2 = new ExternalIdentifier();
			ei2.setIdentifier(numeroGigya2);
			ei2.setType("GIGYA_ID");
			eir2.setExternalIdentifier(ei2);

			ExternalIdentifierData eid2 = new ExternalIdentifierData();
			eid2.setKey("USED_BY_KL");
			eid2.setValue("Y");
			eir2.getExternalIdentifierData().add(eid2);
			request.getExternalIdentifierRequest().add(eir2);
		}

		// WS call
		// TODO workaround for maven issue with Bamboo
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
		}

		// Tests
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		
		update = response.isSuccess();

		logger.info("updateAGigya " + response.getGin());
	
		return update;
	}
	
	private boolean updateAGigya(String numeroGin, String numeroGigya, String numeroGigya2) throws BusinessErrorBlocBusinessException {

		return createOrUpdateAnIndividual(ProcessEnum.E.getCode(), numeroGin, numeroGigya, numeroGigya2);
		
	}
	
	// Insertion 
	private String insertAGigya(String numeroGigya, String key, String value) throws BusinessErrorBlocBusinessException {
		
		String insertGin = "";
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode(APPLICATION_CODE);

		request.setRequestor(requestor);


		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("M.");
		indInfo.setStatus("V");
		indInfo.setGender("M");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Social
		ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
		ExternalIdentifier ei = new ExternalIdentifier();
		ei.setIdentifier(numeroGigya);
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifier(ei);

		ExternalIdentifierData eid = new ExternalIdentifierData();
		eid.setKey(key);
		eid.setValue(value);
		eir.getExternalIdentifierData().add(eid);

		request.getExternalIdentifierRequest().add(eir);

		// WS call
		CreateUpdateIndividualResponse response;

		response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		
		Assert.assertNotNull(response.getGin());
		
		insertGin = response.getGin(); 

		// Assert.assertTrue(response.getGin().startsWith("92"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());


		logger.info("insertAGigya " + response.getGin());
	
		return insertGin;
	}	

	private String insertAGigya(String numeroGigya) throws BusinessErrorBlocBusinessException {
		
		return insertAGigya(numeroGigya, "USED_BY_KL", "Y");
	}
	
	
	@Test
	// Create/Update d un Telecom sur un External via le type E
	public void testCreateOrUpdateIndividual_Update_TelecomOnTravelerOnly_ForExternal() throws BusinessErrorBlocBusinessException{
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode(APPLICATION_CODE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("920000001906");
		indInfo.setCivility("MR");
		indInfo.setGender("M");
		
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Telephone
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber(generatePhone());
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		//		Assert.assertNotNull(response.getGin());
		//		Assert.assertTrue(response.getGin().startsWith("91"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());

		// Assert.assertEquals(RecognitionType.NIF.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());
	}

	@Test
	// Create/Update d un Telecom sur un External via le type I
	public void testCreateOrUpdateIndividual_Update_TelecomOnTravelerOnly_ForIndividual() throws BusinessErrorBlocBusinessException{
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode(APPLICATION_CODE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("920000001140");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Telephone
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber(generatePhone());
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		//		Assert.assertNotNull(response.getGin());
		//		Assert.assertTrue(response.getGin().startsWith("91"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());

		// Assert.assertEquals(RecognitionType.NIF.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());
	}
	
}
