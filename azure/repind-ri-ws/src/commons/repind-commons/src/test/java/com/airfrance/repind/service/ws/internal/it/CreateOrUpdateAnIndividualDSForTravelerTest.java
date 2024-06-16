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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualDSForTravelerTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualDSForTravelerTest.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";

	@Autowired
	private CreateOrUpdateIndividualDS createOrUpdateDS;

	private String generateString() {
		return RandomStringUtils.randomAlphabetic(10);
	}

	private String generateEmailDTO() {
		return generateString() + "@free.nul";
	}

	private String generatePhone() {
		return "0497283" + RandomStringUtils.randomNumeric(3);
	}


	@Test(expected = Test.None.class /* no exception expected */)
	@Ignore
	public void testCreateOrUpdateIndividual_CompleteInsertion() {
		logger.info("*************************************************************");
		logger.info("TEST START : CompleteTravelerInsertion");
		// REQUEST BUILDING
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		// Requestor		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		request.setRequestorDTO(requestor);

		// Individual Request
		{
			IndividualRequestDTO indRequest = new IndividualRequestDTO();
			// Info
			IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
			indInfo.setBirthDate(new Date( ) );
			indInfo.setCivility("MR");
			indInfo.setStatus("V");
			indInfo.setLastNameSC("COMP" + generateString()); 
			indInfo.setFirstNameSC("COMP" + generateString());
			indRequest.setIndividualInformationsDTO(indInfo);
			// Profil
			IndividualProfilDTO indProfil = new IndividualProfilDTO();
			indProfil.setChildrenNumber("2");
			indRequest.setIndividualProfilDTO(indProfil);
			request.setIndividualRequestDTO(indRequest);
		}
		// TelecomDTO Data Request
		{
			TelecomRequestDTO telRequest = new TelecomRequestDTO();
			TelecomDTO telecom = new TelecomDTO();
			telecom.setCountryCode("33");
			telecom.setMediumCode("P");
			telecom.setMediumStatus("V");
			telecom.setTerminalType("T");
			telecom.setPhoneNumber(generatePhone());
			telRequest.setTelecomDTO(telecom);
			request.getTelecomRequestDTO().add(telRequest);
		}
		// EmailDTO Data request
		{
			EmailRequestDTO emailRequest = new EmailRequestDTO();
			EmailDTO email = new EmailDTO();
			email.setEmail(generateEmailDTO());
			email.setMediumCode("P");
			email.setMediumStatus("V");
			email.setVersion("1");
			email.setEmailOptin("N");
			emailRequest.setEmailDTO(email);
			request.getEmailRequestDTO().add(emailRequest);
		}
		// External Identifier Data
		{
			ExternalIdentifierRequestDTO eir = new ExternalIdentifierRequestDTO();
			ExternalIdentifierDTO ei = new ExternalIdentifierDTO();
			ei.setIdentifier("NumeroIDGIGYA_12345" + generatePhone());
			ei.setType("GIGYA_ID");
			eir.setExternalIdentifierDTO(ei);

			request.getExternalIdentifierRequestDTO().add(eir);
		}
		// Prefilled Number Data
		{
			PrefilledNumbersRequestDTO pnRequest = new PrefilledNumbersRequestDTO();
			com.airfrance.repind.dto.ws.PrefilledNumbersDTO pn = new com.airfrance.repind.dto.ws.PrefilledNumbersDTO();
			pn.setContractNumber("S1234567891234567890");
			pn.setContractType("S");
			pnRequest.setPrefilledNumbersDTO(pn);
			request.getPrefilledNumbersRequestDTO().add(pnRequest);
		}
		// Marketing Data
		{
//			MarketingDataRequest mdRequest = new MarketingDataRequest();
//			MarketingInformation mi = new MarketingInformation();


//			mdRequest.setMarketingInformation(mi);
//			request.setMarketingDataRequest(mdRequest);
		}
		logger.info("TEST END : CompleteTravelerInsertion");
		logger.info("*************************************************************");
	}
	
	@Test
	public void testCreateOrUpdateIndividual_TravelerEmailIncomplete() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException  {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);
		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC(generateString().toUpperCase());
		indInfo.setFirstNameSC(generateString().toUpperCase());
		indInfo.setStatus("V");
		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);
		// Contact bloc - Email
		EmailRequestDTO emailRequest = new EmailRequestDTO();
		EmailDTO email = new EmailDTO();
		email.setVersion("1");
		email.setMediumCode("D");
		email.setEmail(generateEmailDTO());
		emailRequest.setEmailDTO(email);
		request.setEmailRequestDTO(new ArrayList<EmailRequestDTO>());
		request.getEmailRequestDTO().add(emailRequest);

		// WS call
		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);
		
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("91"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
		//			Assert.assertEquals(RecognitionType.NIF.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());

		logger.info("GIN created : " + response.getGin());

		logger.info("Test stop.");

	}
	@Test
	@Ignore
	public void testCreateOrUpdateIndividual_TravelerEmail() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("Test start...");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);
		request.setProcess(ProcessEnum.T.getCode());

		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		// indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");				// TODO : Le bonhomme crÃ©Ã© est en status P ! 
		indInfo.setLastNameSC("CAMPANELLA"); 
		indInfo.setFirstNameSC("Jean-Pascal");

		indRequest.setIndividualInformationsDTO(indInfo);


		EmailRequestDTO emailRequest = new EmailRequestDTO();

		EmailDTO email = new EmailDTO();
		email.setEmail("jpcampanella-ext@airfrance.fr");
		email.setEmailOptin("N");
		email.setMediumCode("P");
		email.setMediumStatus("V");
		emailRequest.setEmailDTO(email);
		request.getEmailRequestDTO().add(emailRequest);

		request.setIndividualRequestDTO(indRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("91"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());

		logger.info("GIN created : " + response.getGin());

		logger.info("Test stop.");
	}

	@Test
	public void testCreateOrUpdateIndividual_TravelerOnlyEmail_NIF() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);
		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC(generateString().toUpperCase());
		indInfo.setFirstNameSC(generateString().toUpperCase());
		indInfo.setStatus("V");
		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);
		// Contact bloc - Email
		EmailRequestDTO emailRequest = new EmailRequestDTO();
		EmailDTO email = new EmailDTO();
		email.setEmail(generateEmailDTO());
		email.setMediumCode("P");
		email.setEmailOptin("N");
		email.setMediumStatus("V");
		email.setVersion("1");
		emailRequest.setEmailDTO(email);
		request.setEmailRequestDTO(new ArrayList<EmailRequestDTO>());
		request.getEmailRequestDTO().add(emailRequest);

		// WS call
		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("91"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
		//		Assert.assertEquals(RecognitionType.NIF.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());

		logger.info("GIN created : " + response.getGin());

		logger.info("Test stop.");
	}

	@Test
	public void testCreateOrUpdateIndividual_TravelerOnlyEmail_FOR() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);


		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("TRAVELER" + generateString().toUpperCase());
		indInfo.setFirstNameSC("TRAVELER" + generateString().toUpperCase());
		indInfo.setStatus("V");

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);
		// Contact bloc - Email
		EmailRequestDTO emailRequest = new EmailRequestDTO();
		EmailDTO email = new EmailDTO();
		email.setEmail(generateEmailDTO());
		email.setMediumCode("P");
		email.setMediumStatus("V");
		email.setVersion("1");
		email.setEmailOptin("N");
		emailRequest.setEmailDTO(email);
		request.setEmailRequestDTO(new ArrayList<EmailRequestDTO>());
		request.getEmailRequestDTO().add(emailRequest);

		// WS call
		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("91"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
		// Assert.assertEquals(RecognitionType.FOR.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());

	}

	@Test
	@Ignore
	public void testCreateOrUpdateIndividual_TravelerOnlyEmail_FLE() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);

		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MISS");
		indInfo.setLastNameSC("NOMTRAVESG EMAIL ONLY");
		indInfo.setFirstNameSC("PRENOMTRAVESG EMAIL ONLY");
		indInfo.setStatus("V");
		indInfo.setIdentifier("910000000161");
		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);
		// Contact bloc - Email
		EmailRequestDTO emailRequest = new EmailRequestDTO();
		EmailDTO email = new EmailDTO();
		email.setEmail("jpcampanella-ext@airfrance.fr");
		email.setMediumCode("P");
		email.setMediumStatus("V");
		email.setVersion("1");
		email.setEmailOptin("N");
		emailRequest.setEmailDTO(email);
		request.setEmailRequestDTO(new ArrayList<EmailRequestDTO>());
		request.getEmailRequestDTO().add(emailRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		// Tests
		
		Assert.fail();
//		Assert.assertNotNull(response);		
//		Assert.assertNotNull(response.getSuccess());
//		Assert.assertTrue(response.getSuccess());
		//		Assert.assertNotNull(response.getGin());
		//		Assert.assertTrue(response.getGin().startsWith("91"));

//		Assert.assertNotNull(response.getInformationResponse());
//		Assert.assertNotEquals(0, response.getInformationResponse());
		// Assert.assertEquals(RecognitionType.FLE.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());
	}

	
	@Test
	public void testCreateOrUpdateIndividual_TravelerTelWithoutPhoneNumber() {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);

		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("TRAVE" + generateString());
		indInfo.setFirstNameSC("TRAVE" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);
		// Contact bloc - Telephone
		TelecomRequestDTO telRequest = new TelecomRequestDTO();
		TelecomDTO telecom = new TelecomDTO();
		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telRequest.setTelecomDTO(telecom);
		request.setTelecomRequestDTO(new ArrayList<TelecomRequestDTO>());
		request.getTelecomRequestDTO().add(telRequest);
		try {
			CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
			// Execute test
			response = createOrUpdateDS.createOrUpdateV8(request, response);

			Assert.fail("An error should be constated");
		} catch (Exception mpe) {
			Assert.assertEquals("Missing parameter exception: Phone number is mandatory for the telecom structure", mpe.getMessage());
		}
	}
	
	@Test
	public void testCreateOrUpdateIndividual_TravelerTelIncomplete () throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);

		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("TRAVE" + generateString());
		indInfo.setFirstNameSC("TRAVE" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);
		// Contact bloc - Telephone
		TelecomRequestDTO telRequest = new TelecomRequestDTO();
		TelecomDTO telecom = new TelecomDTO();
		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber(generatePhone());
		telRequest.setTelecomDTO(telecom);
		request.setTelecomRequestDTO(new ArrayList<TelecomRequestDTO>());
		request.getTelecomRequestDTO().add(telRequest);

		// WS call
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
	public void testCreateOrUpdateIndividual_TravelerOnlyTel_NIF() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);

		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("TRAVE" + generateString());
		indInfo.setFirstNameSC("TRAVE" + generateString());
		indInfo.setStatus("V");

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

		// WS call
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
	public void testCreateOrUpdateIndividual_TravelerOnlyTel_FOR() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException{
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);

		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("TRAV" + generateString());
		indInfo.setFirstNameSC("TRAV" + generateString());
		indInfo.setStatus("V");

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

		// WS call
		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);
		
		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("91"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());

		logger.info("testCreateOrUpdateIndividual_TravelerOnlyTel_FOR " + response.getGin());
		// Assert.assertEquals(RecognitionType.FOR.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());
	}

	@Test
	public void testCreateOrUpdateIndividual_TravelerairOnlyTel_FLT() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);

		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MISS");
		indInfo.setLastNameSC("NOMTRAVENUTLSG");
		indInfo.setFirstNameSC("PRENOMTRAVENUTLSG");
		indInfo.setStatus("V");
		indInfo.setIdentifier("910000000065");

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);
		// Contact bloc - Telephone
		TelecomRequestDTO telRequest = new TelecomRequestDTO();
		TelecomDTO telecom = new TelecomDTO();
		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber("0405060708");
		telRequest.setTelecomDTO(telecom);
		request.setTelecomRequestDTO(new ArrayList<TelecomRequestDTO>());
		request.getTelecomRequestDTO().add(telRequest);

		// WS call
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

		// Assert.assertEquals(RecognitionType.FLT.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());
	}

	
	
	@Test
	// Update du TelecomDTO d un Traveler sur un type Traveler, on met a jour le telecom
	public void testCreateOrUpdateIndividual_Update_TelecomOnTravelerOnly() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);

		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();
		
		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setIdentifier("910000000000");

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
	// Update du TelecomDTO sur un Traveler pour un type Individu, on met a jour le Telecom
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
	public void testCreateOrUpdateIndividual_Update_TelecomOnTravelerOnly_ForIndividual() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		// request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestorDTO(requestor);

		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();
		
		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setIdentifier("910000000000");

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
