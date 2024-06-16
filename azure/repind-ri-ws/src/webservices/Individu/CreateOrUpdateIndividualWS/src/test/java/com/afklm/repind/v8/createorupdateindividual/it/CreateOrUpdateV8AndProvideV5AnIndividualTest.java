package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.soa.stubs.w000418.v5.ProvideIndividualDataServiceV5;
import com.afklm.soa.stubs.w000418.v5.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v5.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v5.siccommontype.Requestor;
import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.EmailRequest;
import com.afklm.soa.stubs.w000442.v8.request.ExternalIdentifierRequest;
import com.afklm.soa.stubs.w000442.v8.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8.request.TelecomRequest;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.*;
import com.airfrance.ref.type.ProcessEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
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
public class CreateOrUpdateV8AndProvideV5AnIndividualTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateV8AndProvideV5AnIndividualTest.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	
	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v8Bean")
	private CreateOrUpdateIndividualImplV8 createOrUpdateIndividualImplV8;

	@Autowired
	@Qualifier("consumerpassenger_ProvideIndividualData-v05")
	private ProvideIndividualDataServiceV5 provideIndividualImplV5;
	
	private String generateString() {
		return RandomStringUtils.randomAlphabetic(10);
	}
	
	private String generateEmail() {
		return RandomStringUtils.randomAlphabetic(10) + "@bidon.fr";
	}
	
	private String generatePhone() {
		return "04" + RandomStringUtils.randomNumeric(8);
	}
	
	@Test
	@Ignore
	@Transactional
	@Rollback(true)
	public void test_CreateOrUpdateV8_Traveler_And_ProvideIndividualv5_NOT_FOUND() throws BusinessErrorBlocBusinessException {

		logger.info("Test start...");
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		request.setProcess(ProcessEnum.T.getCode());
		
		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualRequest indRequest = new IndividualRequest();
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		// indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");				// TODO : Le bonhomme créé est en status P ! 
		indInfo.setLastNameSC("TRVL" + generateString()); 
		indInfo.setFirstNameSC("Jean " + generateString());
		
		indRequest.setIndividualInformations(indInfo);
		
		// BLOC EMAIL
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		email.setEmail(generateEmail());
		email.setEmailOptin("N");
		email.setMediumCode("P");
		email.setMediumStatus("V");
		emailRequest.setEmail(email);
		request.getEmailRequest().add(emailRequest);
		
		request.setIndividualRequest(indRequest);

		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("91"));
		
		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
		
		logger.info("test_CreateOrUpdateV8_Traveler_And_ProvideIndividualv5_NOT_FOUND " + response.getGin());
		
		
		ProvideIndividualInformationRequest requestProvide = new ProvideIndividualInformationRequest();
		
		requestProvide.setOption("GIN");
		requestProvide.setIdentificationNumber(response.getGin());
		
		Requestor requestorProvide = new Requestor();
		requestorProvide.setChannel(CHANNEL);
		requestorProvide.setSite(SITE);
		requestorProvide.setSignature(SIGNATURE);
		
		requestProvide.setRequestor(requestorProvide);
		
		try {
			provideIndividualImplV5.provideIndividualDataByIdentifier(requestProvide);
			Assert.fail("On doit lancer un 404 NOT FOUND...");

		} catch (Exception ex) {
						
			Assert.assertNotNull(ex);
			Assert.assertNotNull((com.afklm.soa.stubs.w000418.v5.BusinessErrorBlocBusinessException) ex);
			Assert.assertNotNull(((com.afklm.soa.stubs.w000418.v5.BusinessErrorBlocBusinessException) ex).getFaultInfo());
			Assert.assertNotNull((((com.afklm.soa.stubs.w000418.v5.BusinessErrorBlocBusinessException) ex).getFaultInfo()).getBusinessError().getErrorLabel() );
			
			Assert.assertEquals("NOT FOUND", (((com.afklm.soa.stubs.w000418.v5.BusinessErrorBlocBusinessException) ex).getFaultInfo()).getBusinessError().getErrorLabel());
			logger.info("test_CreateOrUpdateV8_Traveler_And_ProvideIndividualv5_NOT_FOUND NOT FOUND");

		}
		
		
		logger.info("Test stop.");
	}
	
	@Test
	@Ignore
	@Transactional
	@Rollback(true)
	public void test_CreateOrUpdateV8_External_And_ProvideIndividualv5_NOT_FOUND() throws BusinessErrorBlocBusinessException {

		logger.info("Test start...");
		
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
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("SOCIAL" + generateString());
		indInfo.setFirstNameSC("SOCIAL" + generateString());
		indInfo.setStatus("V");
		
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Social
		ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
		ExternalIdentifier ei = new ExternalIdentifier();
		ei.setIdentifier("NumeroIDGIGYA_1234567_Samuel_EID");
		ei.setType("GIGYA_ID");
		
		eir.setExternalIdentifier(ei);
		
		ExternalIdentifierData eid = new ExternalIdentifierData();
		eid.setKey("USED_BY_KL");
		eid.setValue("Y");
		eir.getExternalIdentifierData().add(eid);
		
		request.getExternalIdentifierRequest().add(eir);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("92"));
		
		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
		
		logger.info("test_CreateOrUpdateV8_External_And_ProvideIndividualv5_NOT_FOUND " + response.getGin());
		
		
		ProvideIndividualInformationRequest requestProvide = new ProvideIndividualInformationRequest();
		
		requestProvide.setOption("GIN");
		requestProvide.setIdentificationNumber(response.getGin());
		
		Requestor requestorProvide = new Requestor();
		requestorProvide.setChannel(CHANNEL);
		requestorProvide.setSite(SITE);
		requestorProvide.setSignature(SIGNATURE);
		
		requestProvide.setRequestor(requestorProvide);
		
		try {
			provideIndividualImplV5.provideIndividualDataByIdentifier(requestProvide);
			Assert.fail("On doit lancer un 404 NOT FOUND...");

		} catch (Exception ex) {
			Assert.assertNotNull(ex);
			Assert.assertNotNull((com.afklm.soa.stubs.w000418.v5.BusinessErrorBlocBusinessException) ex);
			Assert.assertNotNull(((com.afklm.soa.stubs.w000418.v5.BusinessErrorBlocBusinessException) ex).getFaultInfo());
			Assert.assertNotNull((((com.afklm.soa.stubs.w000418.v5.BusinessErrorBlocBusinessException) ex).getFaultInfo()).getBusinessError().getErrorLabel() );
			
			Assert.assertEquals("NOT FOUND", (((com.afklm.soa.stubs.w000418.v5.BusinessErrorBlocBusinessException) ex).getFaultInfo()).getBusinessError().getErrorLabel());
			
			logger.info("test_CreateOrUpdateV8_External_And_ProvideIndividualv5_NOT_FOUND NOT FOUND");
		}
		
		logger.info("Test stop.");
	}

	@Test
	@Ignore
	public void test_CreateOrUpdateV8_IndividuI_And_ProvideIndividualv5_FOUND() throws BusinessErrorBlocBusinessException {

		logger.info("Test start...");
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		request.setProcess(ProcessEnum.I.getCode());
		
		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		// requestor.setContext(null);
		
		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("IND" + generateString());
		String firstNameSC = "IND" + generateString();
		indInfo.setFirstNameSC(firstNameSC);
		indInfo.setStatus("V");
		
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("40"));
		
		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
		
		logger.info("test_CreateOrUpdateV8_IndividuI_And_ProvideIndividualv5_FOUND " + response.getGin());
		
		
		ProvideIndividualInformationRequest requestProvide = new ProvideIndividualInformationRequest();
		
		requestProvide.setOption("GIN");
		requestProvide.setIdentificationNumber(response.getGin());
		
		Requestor requestorProvide = new Requestor();
		requestorProvide.setChannel(CHANNEL);
		requestorProvide.setSite(SITE);
		requestorProvide.setSignature(SIGNATURE);
		
		requestProvide.setRequestor(requestorProvide);
		
		try {
			ProvideIndividualInformationResponse responseProvide = provideIndividualImplV5.provideIndividualDataByIdentifier(requestProvide);
			
			Assert.assertNotNull(responseProvide);		
			Assert.assertNotNull(responseProvide.getIndividualResponse());
			Assert.assertNotNull(responseProvide.getIndividualResponse().getIndividualInformations());
			
			Assert.assertNotNull(responseProvide.getIndividualResponse().getIndividualInformations().getIdentifier());
			Assert.assertEquals(response.getGin(), responseProvide.getIndividualResponse().getIndividualInformations().getIdentifier());
			
			Assert.assertNotNull(responseProvide.getIndividualResponse().getIndividualInformations().getFirstNameSC());
			Assert.assertEquals(firstNameSC, responseProvide.getIndividualResponse().getIndividualInformations().getFirstNameSC());

		} catch (Exception ex) {
			Assert.fail("test_CreateOrUpdateV8_IndividuI_And_ProvideIndividualv5_FOUND " + ex.getMessage());
		}
		
		logger.info("Test stop.");
	}

	@Test
	@Ignore
	public void test_CreateOrUpdateV8_IndividuNull_And_ProvideIndividualv5_FOUND() throws BusinessErrorBlocBusinessException {

		logger.info("Test start...");
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// request.setIndividualType(PopulationType.I.getCode());
		
		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		// requestor.setContext(null);
		
		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("IND" + generateString());
		String firstNameSC = "IND" + generateString();
		indInfo.setFirstNameSC(firstNameSC);
		indInfo.setStatus("V");
		
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("40"));
		
		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
		
		logger.info("test_CreateOrUpdateV8_IndividuNull_And_ProvideIndividualv5_FOUND " + response.getGin());
		
		
		ProvideIndividualInformationRequest requestProvide = new ProvideIndividualInformationRequest();
		
		requestProvide.setOption("GIN");
		requestProvide.setIdentificationNumber(response.getGin());
		
		Requestor requestorProvide = new Requestor();
		requestorProvide.setChannel(CHANNEL);
		requestorProvide.setSite(SITE);
		requestorProvide.setSignature(SIGNATURE);
		
		requestProvide.setRequestor(requestorProvide);
		
		try {
			ProvideIndividualInformationResponse responseProvide = provideIndividualImplV5.provideIndividualDataByIdentifier(requestProvide);
			
			Assert.assertNotNull(responseProvide);		
			Assert.assertNotNull(responseProvide.getIndividualResponse());
			Assert.assertNotNull(responseProvide.getIndividualResponse().getIndividualInformations());
			
			Assert.assertNotNull(responseProvide.getIndividualResponse().getIndividualInformations().getIdentifier());
			Assert.assertEquals(response.getGin(), responseProvide.getIndividualResponse().getIndividualInformations().getIdentifier());
			
			Assert.assertNotNull(responseProvide.getIndividualResponse().getIndividualInformations().getFirstNameSC());
			Assert.assertEquals(firstNameSC, responseProvide.getIndividualResponse().getIndividualInformations().getFirstNameSC());

		} catch (Exception ex) {
			Assert.fail("test_CreateOrUpdateV8_IndividuNull_And_ProvideIndividualv5_FOUND " + ex.getMessage());
		}
		
		logger.info("Test stop.");
	}

	@Test
	@Ignore
	public void test_CreateOrUpdateV8_IndividuWithAllBlock_And_ProvideIndividualv5_FOUND() throws BusinessErrorBlocBusinessException {

		logger.info("Test start...");
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// request.setIndividualType(PopulationType.I.getCode());
		
		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		// requestor.setContext(null);
		
		request.setRequestor(requestor);

		// BLOCK : Individual Request
		IndividualRequest indRequest = new IndividualRequest();
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("IND" + generateString());
		String firstNameSC = "IND" + generateString();
		indInfo.setFirstNameSC(firstNameSC);
		indInfo.setStatus("V");
		
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		
		// BLOCK Social
/*		ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
		ExternalIdentifier ei = new ExternalIdentifier();
		ei.setIdentifier("NumeroIDGIGYA_1234567_Samuel_EID_" + generatePhone());
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifier(ei);
		
		ExternalIdentifierData eid = new ExternalIdentifierData();
		eid.setKey("USED_BY_KL");
		eid.setValue("Y");
		eir.getExternalIdentifierData().add(eid);
		
		request.getExternalIdentifierRequest().add(eir);
*/		
		// BLOCK EMAIL
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		email.setEmail("ALL" + generateEmail());
		email.setEmailOptin("N");
		email.setMediumCode("P");
		email.setMediumStatus("V");
		emailRequest.setEmail(email);
		request.getEmailRequest().add(emailRequest);
		
		// BLOCK TELECOMS
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
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("40"));
		
		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
		
		logger.info("test_CreateOrUpdateV8_IndividuI_And_ProvideIndividualv5_FOUND " + response.getGin());
		
		
		ProvideIndividualInformationRequest requestProvide = new ProvideIndividualInformationRequest();
		
		requestProvide.setOption("GIN");
		requestProvide.setIdentificationNumber(response.getGin());
		
		Requestor requestorProvide = new Requestor();
		requestorProvide.setChannel(CHANNEL);
		requestorProvide.setSite(SITE);
		requestorProvide.setSignature(SIGNATURE);
		
		requestProvide.setRequestor(requestorProvide);
		
		try {
			ProvideIndividualInformationResponse responseProvide = provideIndividualImplV5.provideIndividualDataByIdentifier(requestProvide);
			
			Assert.assertNotNull(responseProvide);		
			Assert.assertNotNull(responseProvide.getIndividualResponse());
			Assert.assertNotNull(responseProvide.getIndividualResponse().getIndividualInformations());
			
			Assert.assertNotNull(responseProvide.getIndividualResponse().getIndividualInformations().getIdentifier());
			Assert.assertEquals(response.getGin(), responseProvide.getIndividualResponse().getIndividualInformations().getIdentifier());
			
			Assert.assertNotNull(responseProvide.getIndividualResponse().getIndividualInformations().getFirstNameSC());
			Assert.assertEquals(firstNameSC, responseProvide.getIndividualResponse().getIndividualInformations().getFirstNameSC());

		} catch (Exception ex) {
			Assert.fail("test_CreateOrUpdateV8_IndividuI_And_ProvideIndividualv5_FOUND " + ex.getMessage());
		}
		
		logger.info("Test stop.");
	}


}
