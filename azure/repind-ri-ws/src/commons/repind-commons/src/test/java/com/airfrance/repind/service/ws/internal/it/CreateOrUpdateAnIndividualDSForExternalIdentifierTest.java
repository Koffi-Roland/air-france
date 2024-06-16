package com.airfrance.repind.service.ws.internal.it;

import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.ProcessEnum;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.service.ws.internal.CreateOrUpdateIndividualDS;
import com.airfrance.repind.service.ws.internal.helpers.UltimateException;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualDSForExternalIdentifierTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualDSForExternalIdentifierTest.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";

	@Autowired
	private CreateOrUpdateIndividualDS createOrUpdateDS;

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
	public void testCreateOrUpdateIndividual_Create_ExternalIdentifier_CasBase_2GIGYA_Differents() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		String numeroGigya = "NumeroIDGIGYA_AFKL_" + generatePhone() + "_" + generateString();

		insertAGigya(numeroGigya);
		
		numeroGigya = "NumeroIDGIGYA_AFKL_" + generatePhone() + "_" + generateString();
		
		insertAGigya(numeroGigya);
		
		Assert.assertTrue(true);
	}

	@Test
	public void testCreateOrUpdateIndividual_Create_ExternalIdentifier_CasBase_GIGYA_ExistInSocialNetworkData() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		String numeroGigyaExistInSocialNetworkData = "AFKL_201509181351__guid_zV5EvSaLk6DTBZEb04-ZtZJe4Wauh7l6rNPN";

		String ginFound = insertAGigya(numeroGigyaExistInSocialNetworkData);
		
		Assert.assertNotNull(ginFound);
		Assert.assertNotEquals("", ginFound);
		Assert.assertTrue(ginFound.startsWith("0000"));
	}

	@Test
	public void testCreateOrUpdateIndividual_Create_ExternalIdentifier_CasBase_GIGYA_KEY_InSocialNetworkData() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		String numeroGigyaExistInSocialNetworkData = "AFKL_201605181128__guid_lw2zbgNqrT2cw1HvHfT6OX0JRIrVPmgnL1TW";
		String key = "USED_BY_AF";
		String value = "Y";
		
		String ginFound = insertAGigya(numeroGigyaExistInSocialNetworkData, key, value);
		
		Assert.assertNotNull(ginFound);
		Assert.assertNotEquals("", ginFound);
		Assert.assertTrue(ginFound.startsWith("000"));
	}

	@Test
	public void testCreateOrUpdateIndividual_Create_ExternalIdentifier_CasBase_GIGYA_KEY_VALUE_InSocialNetworkData() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		String numeroGigyaExistInSocialNetworkData = "AFKL_201505211440__guid_jyTKRYkOH4QTBVloFoxplaAldKy91dPIO4jm";
		String key = "USED_BY_KL";
		String value = "N";
		
		String ginFound = insertAGigya(numeroGigyaExistInSocialNetworkData, key, value);
		
		Assert.assertNotNull(ginFound);
		Assert.assertNotEquals("", ginFound);
		Assert.assertTrue(ginFound.startsWith("000"));
	}

	@Test
	public void testCreateOrUpdateIndividual_Create_ExternalIdentifier_CasKO_GIGYA_KEY_NotValid() {

		String numeroGigyaExistInSocialNetworkData = "AFKL_201412110804__guid_Byzb9rctyUqwk8nR4lMRrZaDfuSmd0ed5s4p";
		String key = "USED_BY_FR";
		String value = "Y";
		
		try {
			insertAGigya(numeroGigyaExistInSocialNetworkData, key, value);
		
			Assert.fail("On devrait avoir un message d erreur, KEY USED_BY_FR non valide");
		} catch (Exception e) {
			
			Assert.assertTrue(e.getMessage().startsWith("Invalid external identifier key value:"));
		}
	}

	@Test
	public void testCreateOrUpdateIndividual_Create_ExternalIdentifier_CasBase_GIGYA_KEY_VALUE_InSocialNetworkData_IN() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

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
			
		} catch (Exception e) {
			Assert.fail("On ne devrait pas avoir de message d erreur, le GIGYA est unique dans le cas présent");
		}
		
		try {
			String gin = insertAGigya(numeroGigya);
			
			Assert.assertEquals(ginJustInserted, gin);
			
		} catch (Exception e) {

			Assert.fail("On ne devrait pas avoir de message d erreur, car on recupere le GIGYA precedement insere");
		}
	}

	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testCreateOrUpdateIndividual_Update_ExternalIdentifier_CasBase() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		String numeroGin = "920000001405";
		String numeroGigya = "NumeroIDGIGYA_AFKL_" + generatePhone() + "_" + generateString();

		updateAGigya(numeroGin, numeroGigya);
		
		Assert.assertTrue(true);
	}

	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testCreateOrUpdateIndividual_Update_ExternalIdentifier_CasBase_Individu() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		String numeroGin = "400413556501";
		String numeroGigya = "NumeroIDGIGYA_AFKL_" + generatePhone() + "_" + generateString();

		updateAGigya(numeroGin, numeroGigya);
		
		Assert.assertTrue(true);
	}
	
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testCreateOrUpdateIndividual_Update_ExternalIdentifier_CasOK_2GIGYA_Identique() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

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
	public void testCreateOrUpdateIndividual_Update_ExternalIdentifier_CasOK_CivilityLastNameFirstName() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		String numeroGigya = "NumeroIDGIGYA_AFKL_" + generatePhone() + "_" + generateString();
		String gin = "";
		
		try {
			gin = insertAGigya(numeroGigya);
			Assert.assertNotNull(gin);
			Assert.assertTrue(gin.startsWith("92"));
			
		} catch (Exception e) {
			Assert.fail("On ne devrait pas avoir de message d erreur, le GIGYA est unique dans le cas présent");
		}
		
		if (updateAnIndividual(gin, numeroGigya, "TOI", "MOI")) {
			Assert.assertTrue(true);
		} else {
			Assert.fail("Error on Update");
		}
	}
	
	@Test
	public void testCreateOrUpdateIndividual_Delete_ExternalIdentifier_CasDeBase_Create() {

		String numeroGigya = "NumeroIDGIGYA_1234567890";
		
		try {
			String ginCreated = insertAGigya(numeroGigya);

			Assert.assertNotNull(ginCreated);
			Assert.assertNotEquals("", ginCreated);
			Assert.assertTrue(ginCreated.startsWith("92"));
			
		} catch (Exception e) {
			Assert.fail("On ne devrait pas avoir de message d erreur, le GIGYA est cree");
		}
	}

	@Test
	public void testCreateOrUpdateIndividual_Delete_ExternalIdentifier_CasDeBase_Update() {

		String numeroGigya = "NumeroIDGIGYA_AFKL_0444591440_COfkBEQWHJ";
		String numeroGin = "920000001884";
		
		try {
			boolean updated = updateAGigya(numeroGin, numeroGigya);
			Assert.assertTrue(updated);
			
		} catch (Exception e) {
			Assert.fail("On ne devrait pas avoir de message d erreur, le GIGYA est present");
		}
	}
	
	@Test
	public void testCreateOrUpdateIndividual_Delete_ExternalIdentifier_CasDeBase_2GIGYA() {

		String numeroGigya = "NumeroIDGIGYA_AFKL_0416173848_HxmpjpicQI";
		String numeroGigya2 = "NumeroIDGIGYA_AFKL_0447323502_ZewqJPRJcl";
		String numeroGin = "920000001394";
		
		try {
			boolean updated = updateAGigya(numeroGin, numeroGigya, numeroGigya2);
			Assert.assertTrue(updated);
			
		} catch (Exception e) {
			Assert.fail("On ne devrait pas avoir de message d erreur, le GIGYA est present");
		}
	}
	
	@Ignore
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Update_Individual_CasDeBase_WithGIGYA() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		String numeroGigya = "NumeroIDGIGYA_AFKL_" + generatePhone() + "_" + generateString();
		String numeroGin = "400300011376";
		
		// TODO : A terminer, tester l update d un individu qui a des Externals afin de profiter dans ce cas precis du delete 
		
		boolean updated = createOrUpdateAnIndividual(ProcessEnum.I.getCode(), numeroGin, numeroGigya, "");
		Assert.assertTrue(updated);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// Mise a jour  

	private boolean updateAnIndividual(String numeroGin, String numeroGigya, String firstName, String lastName) throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		
		boolean update = false;
		
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		
		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);


		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setIdentifier(numeroGin);
		indInfo.setCivility("M.");
		indInfo.setStatus("V");
		indInfo.setFirstNameSC(firstName);
		indInfo.setLastNameSC(lastName);

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);

		// Social
		ExternalIdentifierRequestDTO eir = new ExternalIdentifierRequestDTO();
		ExternalIdentifierDTO ei = new ExternalIdentifierDTO();
		ei.setIdentifier(numeroGigya);
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifierDTO(ei);

		ExternalIdentifierDataDTO eid = new ExternalIdentifierDataDTO();
		eid.setKey("USED_BY_KL");
		eid.setValue("Y");
		eir.getExternalIdentifierDataDTO().add(eid);

		request.getExternalIdentifierRequestDTO().add(eir);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);
		
		// Tests
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getSuccess());
		
		update = response.getSuccess();

		logger.info("updateAGigya " + response.getGin());
	
		return update;
	}
	
	private boolean updateAGigya(String numeroGin, String numeroGigya) throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		
		return updateAGigya(numeroGin, numeroGigya, null);
	}

	private boolean createOrUpdateAnIndividual(String process, String numeroGin, String numeroGigya, String numeroGigya2) throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		boolean update = false;
		
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		
		request.setProcess(process);

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);


		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		
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

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);

		// Social
		ExternalIdentifierRequestDTO eir = new ExternalIdentifierRequestDTO();
		ExternalIdentifierDTO ei = new ExternalIdentifierDTO();
		ei.setIdentifier(numeroGigya);
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifierDTO(ei);

		ExternalIdentifierDataDTO eid = new ExternalIdentifierDataDTO();
		eid.setKey("USED_BY_KL");
		eid.setValue("Y");
		eir.setExternalIdentifierDataDTO(new ArrayList<ExternalIdentifierDataDTO>());
		eir.getExternalIdentifierDataDTO().add(eid);
		
		request.setExternalIdentifierRequestDTO(new ArrayList<ExternalIdentifierRequestDTO>());
		request.getExternalIdentifierRequestDTO().add(eir);
		
		if (numeroGigya2 != null && !"".equals(numeroGigya2)) {
			
			ExternalIdentifierRequestDTO eir2 = new ExternalIdentifierRequestDTO();
			ExternalIdentifierDTO ei2 = new ExternalIdentifierDTO();
			ei2.setIdentifier(numeroGigya2);
			ei2.setType("GIGYA_ID");
			eir2.setExternalIdentifierDTO(ei2);

			ExternalIdentifierDataDTO eid2 = new ExternalIdentifierDataDTO();
			eid2.setKey("USED_BY_KL");
			eid2.setValue("Y");
			eir2.setExternalIdentifierDataDTO(new ArrayList<ExternalIdentifierDataDTO>());
			eir2.getExternalIdentifierDataDTO().add(eid2);
			request.getExternalIdentifierRequestDTO().add(eir2);
		}

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);
		
		// Tests
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getSuccess());
		
		update = response.getSuccess();

		logger.info("updateAGigya " + response.getGin());
	
		return update;
	}
	
	private boolean updateAGigya(String numeroGin, String numeroGigya, String numeroGigya2) throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		return createOrUpdateAnIndividual(ProcessEnum.E.getCode(), numeroGin, numeroGigya, numeroGigya2);
		
	}
	
	// Insertion 
	private String insertAGigya(String numeroGigya, String key, String value) throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		
		String insertGin = "";
		
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		
		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);


		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("M.");
		indInfo.setStatus("V");

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);

		// Social
		ExternalIdentifierRequestDTO eir = new ExternalIdentifierRequestDTO();
		ExternalIdentifierDTO ei = new ExternalIdentifierDTO();
		ei.setIdentifier(numeroGigya);
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifierDTO(ei);

		ExternalIdentifierDataDTO eid = new ExternalIdentifierDataDTO();
		eid.setKey(key);
		eid.setValue(value);
		eir.setExternalIdentifierDataDTO(new ArrayList<ExternalIdentifierDataDTO>());
		eir.getExternalIdentifierDataDTO().add(eid);

		request.setExternalIdentifierRequestDTO(new ArrayList<ExternalIdentifierRequestDTO>());
		request.getExternalIdentifierRequestDTO().add(eir);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		
		Assert.assertNotNull(response.getGin());
		
		insertGin = response.getGin(); 

		// Assert.assertTrue(response.getGin().startsWith("92"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());


		logger.info("insertAGigya " + response.getGin());
	
		return insertGin;
	}	

	private String insertAGigya(String numeroGigya) throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		
		return insertAGigya(numeroGigya, "USED_BY_KL", "Y");
	}
	
	
	@Test
	// Create/Update d un Telecom sur un External via le type E
	public void testCreateOrUpdateIndividual_Update_TelecomOnTravelerOnly_ForExternal() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);

		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();
		
		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setIdentifier("920000001906");
		indInfo.setCivility("MR");
		indInfo.setGender("M");
		
		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);
		// Contact bloc - Telephone
		TelecomRequestDTO telRequest = new TelecomRequestDTO();
		TelecomDTO telecom = new TelecomDTO();
		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber(generatePhone());
		telRequest.setTelecomDTO(telecom);
		
		request.setTelecomRequestDTO(new ArrayList<TelecomRequestDTO>());
		request.getTelecomRequestDTO().add(telRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		//		Assert.assertNotNull(response.getGin());
		//		Assert.assertTrue(response.getGin().startsWith("91"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());

		// Assert.assertEquals(RecognitionType.NIF.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());
	}

	@Test
	// Create/Update d un Telecom sur un External via le type I
	public void testCreateOrUpdateIndividual_Update_TelecomOnTravelerOnly_ForIndividual() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		// request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);

		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();
		
		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setIdentifier("920000001140");

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);
		// Contact bloc - Telephone
		TelecomRequestDTO telRequest = new TelecomRequestDTO();
		TelecomDTO telecom = new TelecomDTO();
		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber(generatePhone());
		telRequest.setTelecomDTO(telecom);
		request.setTelecomRequestDTO(new ArrayList<TelecomRequestDTO>());
		request.getTelecomRequestDTO().add(telRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		//		Assert.assertNotNull(response.getGin());
		//		Assert.assertTrue(response.getGin().startsWith("91"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());

		// Assert.assertEquals(RecognitionType.NIF.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());
	}
	
}
