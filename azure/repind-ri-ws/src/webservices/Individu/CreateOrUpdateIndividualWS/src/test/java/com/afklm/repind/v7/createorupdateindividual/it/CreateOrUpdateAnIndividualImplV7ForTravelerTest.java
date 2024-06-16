package com.afklm.repind.v7.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v7.createorupdateindividualws.CreateOrUpdateIndividualImplV7;
import com.afklm.soa.stubs.w000442.v7.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v7.request.*;
import com.afklm.soa.stubs.w000442.v7.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.*;
import com.afklm.soa.stubs.w000442.v7.sicmarketingtype.MarketingInformation;
import com.airfrance.ref.type.ProcessEnum;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.GregorianCalendar;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplV7ForTravelerTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV7ForTravelerTest.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";

	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v7Bean")
	private CreateOrUpdateIndividualImplV7 createOrUpdateIndividualImplV7;

	private String generateString() {
		return RandomStringUtils.randomAlphabetic(10);
	}

	private String generateEmail() {
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
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Requestor		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		request.setRequestor(requestor);

		// Individual Request
		{
			IndividualRequest indRequest = new IndividualRequest();
			// Info
			IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			indInfo.setBirthDate(new Date( ) );
			indInfo.setCivility("MR");
			indInfo.setStatus("V");
			indInfo.setLastNameSC("COMP" + generateString()); 
			indInfo.setFirstNameSC("COMP" + generateString());
			indRequest.setIndividualInformations(indInfo);
			// Profil
			IndividualProfilV3 indProfil = new IndividualProfilV3();
			indProfil.setChildrenNumber("2");
			indRequest.setIndividualProfil(indProfil);
			request.setIndividualRequest(indRequest);
		}
		// Telecom Data Request
		{
			TelecomRequest telRequest = new TelecomRequest();
			Telecom telecom = new Telecom();
			telecom.setCountryCode("33");
			telecom.setMediumCode("P");
			telecom.setMediumStatus("V");
			telecom.setTerminalType("T");
			telecom.setPhoneNumber(generatePhone());
			telRequest.setTelecom(telecom);
			request.getTelecomRequest().add(telRequest);
		}
		// Email Data request
		{
			EmailRequest emailRequest = new EmailRequest();
			Email email = new Email();
			email.setEmail(generateEmail());
			email.setMediumCode("P");
			email.setMediumStatus("V");
			email.setVersion("1");
			email.setEmailOptin("N");
			emailRequest.setEmail(email);
			request.getEmailRequest().add(emailRequest);
		}
		// External Identifier Data
		{
			ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
			ExternalIdentifier ei = new ExternalIdentifier();
			ei.setIdentifier("NumeroIDGIGYA_12345" + generatePhone());
			ei.setType("GIGYA_ID");
			eir.setExternalIdentifier(ei);

			request.getExternalIdentifierRequest().add(eir);
		}
		// Prefilled Number Data
		{
			PrefilledNumbersRequest pnRequest = new PrefilledNumbersRequest();
			PrefilledNumbers pn = new PrefilledNumbers();
			pn.setContractNumber("S1234567891234567890");
			pn.setContractType("S");
			pnRequest.setPrefilledNumbers(pn);
			request.getPrefilledNumbersRequest().add(pnRequest);
		}
		// Marketing Data
		{
			MarketingDataRequest mdRequest = new MarketingDataRequest();
			MarketingInformation mi = new MarketingInformation();


			mdRequest.setMarketingInformation(mi);
			request.setMarketingDataRequest(mdRequest);
		}
		logger.info("TEST END : CompleteTravelerInsertion");
		logger.info("*************************************************************");
	}

	@Test
	public void testCreateOrUpdateIndividual_TravelerNoContact() {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);
		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC(generateString().toUpperCase());
		indInfo.setFirstNameSC(generateString().toUpperCase());
		indInfo.setStatus("V");
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// WS call
		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
			Assert.fail("An error should be constated");
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals("Missing parameter exception: A bloc \"contact\" (Telecom and/or email) is mandatory for traveler process", mpe.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}

	@Test
	public void testCreateOrUpdateIndividual_TravelerEmailWithoutEmail() throws BusinessErrorBlocBusinessException {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);
		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC(generateString().toUpperCase());
		indInfo.setFirstNameSC(generateString().toUpperCase());
		indInfo.setStatus("V");
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Email
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		email.setVersion("1");
		emailRequest.setEmail(email);
		request.getEmailRequest().add(emailRequest);

		// WS call
		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
			Assert.fail("An error should be constated");
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals("Missing parameter exception: Email is mandatory in an Email bloc", mpe.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}

	@Test
	public void testCreateOrUpdateIndividual_TravelerEmailIncomplete() throws BusinessErrorBlocBusinessException {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);
		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC(generateString().toUpperCase());
		indInfo.setFirstNameSC(generateString().toUpperCase());
		indInfo.setStatus("V");
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Email
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		email.setVersion("1");
		email.setMediumCode("D");
		email.setEmail(generateEmail());
		emailRequest.setEmail(email);
		request.getEmailRequest().add(emailRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
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
	public void testCreateOrUpdateIndividual_TravelerEmail() throws BusinessErrorBlocBusinessException{

		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);
		request.setProcess(ProcessEnum.T.getCode());

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		// indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");				// TODO : Le bonhomme crÃ©Ã© est en status P ! 
		indInfo.setLastNameSC("CAMPANELLA"); 
		indInfo.setFirstNameSC("Jean-Pascal");

		indRequest.setIndividualInformations(indInfo);


		EmailRequest emailRequest = new EmailRequest();

		Email email = new Email();
		email.setEmail("jpcampanella-ext@airfrance.fr");
		email.setEmailOptin("N");
		email.setMediumCode("P");
		email.setMediumStatus("V");
		emailRequest.setEmail(email);
		request.getEmailRequest().add(emailRequest);

		request.setIndividualRequest(indRequest);

		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("91"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());

		logger.info("GIN created : " + response.getGin());

		logger.info("Test stop.");
	}

	@Test
	public void testCreateOrUpdateIndividual_TravelerOnlyEmail_NIF() throws BusinessErrorBlocBusinessException {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);
		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC(generateString().toUpperCase());
		indInfo.setFirstNameSC(generateString().toUpperCase());
		indInfo.setStatus("V");
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Email
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		email.setEmail(generateEmail());
		email.setMediumCode("P");
		email.setEmailOptin("N");
		email.setMediumStatus("V");
		email.setVersion("1");
		emailRequest.setEmail(email);
		request.getEmailRequest().add(emailRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("91"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
		//		Assert.assertEquals(RecognitionType.NIF.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());

		logger.info("GIN created : " + response.getGin());

		logger.info("Test stop.");
	}

	@Test
	public void testCreateOrUpdateIndividual_TravelerOnlyEmail_FOR() throws BusinessErrorBlocBusinessException {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);


		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("TRAVELER" + generateString().toUpperCase());
		indInfo.setFirstNameSC("TRAVELER" + generateString().toUpperCase());
		indInfo.setStatus("V");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Email
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		email.setEmail(generateEmail());
		email.setMediumCode("P");
		email.setMediumStatus("V");
		email.setVersion("1");
		email.setEmailOptin("N");
		emailRequest.setEmail(email);
		request.getEmailRequest().add(emailRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("91"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
		// Assert.assertEquals(RecognitionType.FOR.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());

	}

	@Test
	@Ignore
	public void testCreateOrUpdateIndividual_TravelerOnlyEmail_FLE() throws BusinessErrorBlocBusinessException {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MISS");
		indInfo.setLastNameSC("NOMTRAVESG EMAIL ONLY");
		indInfo.setFirstNameSC("PRENOMTRAVESG EMAIL ONLY");
		indInfo.setStatus("V");
		indInfo.setIdentifier("910000000161");
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Email
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		email.setEmail("jpcampanella-ext@airfrance.fr");
		email.setMediumCode("P");
		email.setMediumStatus("V");
		email.setVersion("1");
		email.setEmailOptin("N");
		emailRequest.setEmail(email);
		request.getEmailRequest().add(emailRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		//		Assert.assertNotNull(response.getGin());
		//		Assert.assertTrue(response.getGin().startsWith("91"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
		// Assert.assertEquals(RecognitionType.FLE.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());
	}

	@Test
	public void testCreateOrUpdateIndividual_TravelerTelMandatory() {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("TRAVE" + generateString());
		indInfo.setFirstNameSC("TRAVE" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Telephone
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);
		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
			Assert.fail("An error should be constated");
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals("Missing parameter exception: Phone number and country code are mandatory in a Telecom bloc", mpe.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}

	
	@Test
	public void testCreateOrUpdateIndividual_TravelerTelWithoutPhoneNumber() {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("TRAVE" + generateString());
		indInfo.setFirstNameSC("TRAVE" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Telephone
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);
		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
			Assert.fail("An error should be constated");
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals("Missing parameter exception: Phone number and country code are mandatory in a Telecom bloc", mpe.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}
	
	
	@Test
	public void testCreateOrUpdateIndividual_TravelerTelWithoutCountryCode() {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("TRAVE" + generateString());
		indInfo.setFirstNameSC("TRAVE" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Telephone
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber(generatePhone());
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);
		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
			Assert.fail("An error should be constated");
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals("Missing parameter exception: Phone number and country code are mandatory in a Telecom bloc", mpe.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}
	
	@Test
	public void testCreateOrUpdateIndividual_TravelerTelIncomplete () throws BusinessErrorBlocBusinessException{
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("TRAVE" + generateString());
		indInfo.setFirstNameSC("TRAVE" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Telephone
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber(generatePhone());
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

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
	public void testCreateOrUpdateIndividual_TravelerOnlyTel_NIF() throws BusinessErrorBlocBusinessException{
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("TRAVE" + generateString());
		indInfo.setFirstNameSC("TRAVE" + generateString());
		indInfo.setStatus("V");

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
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

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
	public void testCreateOrUpdateIndividual_TravelerOnlyTel_FOR() throws BusinessErrorBlocBusinessException{
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("TRAV" + generateString());
		indInfo.setFirstNameSC("TRAV" + generateString());
		indInfo.setStatus("V");

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
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("91"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());

		logger.info("testCreateOrUpdateIndividual_TravelerOnlyTel_FOR " + response.getGin());
		// Assert.assertEquals(RecognitionType.FOR.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());
	}

	@Test
	public void testCreateOrUpdateIndividual_TravelerairOnlyTel_FLT() throws BusinessErrorBlocBusinessException {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MISS");
		indInfo.setLastNameSC("NOMTRAVENUTLSG");
		indInfo.setFirstNameSC("PRENOMTRAVENUTLSG");
		indInfo.setStatus("V");
		indInfo.setIdentifier("910000000065");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Telephone
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber("0405060708");
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		//		Assert.assertNotNull(response.getGin()); 	
		//		Assert.assertTrue(response.getGin().startsWith("91"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());

		// Assert.assertEquals(RecognitionType.FLT.getCode(), response.getInformationResponse().get(0).getInformation().getInformationCode());
	}

	
	
	@Test
	// Update du Telecom d un Traveler sur un type Traveler, on met a jour le telecom
	public void testCreateOrUpdateIndividual_Update_TelecomOnTravelerOnly() throws BusinessErrorBlocBusinessException{
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("910000000000");

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
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

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
	// Update du Telecom sur un Traveler pour un type Individu, on met a jour le Telecom
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
	public void testCreateOrUpdateIndividual_Update_TelecomOnTravelerOnly_ForIndividual() throws BusinessErrorBlocBusinessException{
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("910000000000");

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
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

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
	public void testCreateOrUpdateIndividual_Update_Telecom_WithDOB() throws BusinessErrorBlocBusinessException{
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.T.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setSite("AMS");
		requestor.setSignature("EBT");

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("400510000073");

		indInfo.setCivility("MRS");
		indInfo.setFirstNameSC("Crhistel");
		indInfo.setLastNameSC("Herrera Flores");
		indInfo.setLanguageCode("EN");
		
		GregorianCalendar gc = new GregorianCalendar(1988, 9, 6);
		indInfo.setBirthDate(gc.getTime());
		
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		// Contact bloc - Telephone
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setCountryCode("52");
		telecom.setMediumCode("D");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber("3141255975");
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

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
