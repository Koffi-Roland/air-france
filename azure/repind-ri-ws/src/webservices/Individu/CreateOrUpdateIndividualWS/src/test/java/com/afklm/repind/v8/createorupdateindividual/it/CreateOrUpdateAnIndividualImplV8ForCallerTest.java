package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8.request.TelecomRequest;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.IndividualInformationsV3;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.Telecom;
import com.airfrance.ref.type.ProcessEnum;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
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
public class CreateOrUpdateAnIndividualImplV8ForCallerTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV8ForCallerTest.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";

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
		return "04" + RandomStringUtils.randomNumeric(8);
	}

	// REPIND-1808 : Create a Caller in RI database
	@Test
	public void testCreateOrUpdateIndividual_Caller_NoTelecom()  {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.C.toString());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);
		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("M.");
		indInfo.setStatus("V");
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// WS call
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.assertEquals("ERROR_932", e.getFaultInfo().getBusinessError().getErrorCode().toString());
			Assert.assertEquals("INVALID PARAMETER", e.getFaultInfo().getBusinessError().getErrorLabel());
			Assert.assertEquals("Create a Caller is not allowed without a Telecom", e.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}


	@Test
	public void testCreateOrUpdateIndividual_Caller_TelecomNoCountryCode()  {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.C.toString());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);
		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("M.");
		indInfo.setStatus("V");
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Contact bloc - Telecom - Mandatory
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
//		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber(generatePhone());
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);
		
		// WS call
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.assertEquals("ERROR_932", e.getFaultInfo().getBusinessError().getErrorCode().toString());
			Assert.assertEquals("INVALID PARAMETER", e.getFaultInfo().getBusinessError().getErrorLabel());
			Assert.assertEquals("Create a Caller is not allowed without a Telecom", e.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}

	@Test
	public void testCreateOrUpdateIndividual_Caller_TelecomNoPhoneNumber()  {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.C.toString());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);
		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("M.");
		indInfo.setStatus("V");
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Contact bloc - Telecom - Mandatory
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
//		telecom.setPhoneNumber(generatePhone());
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);
		
		// WS call
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.assertEquals("ERROR_932", e.getFaultInfo().getBusinessError().getErrorCode().toString());
			Assert.assertEquals("INVALID PARAMETER", e.getFaultInfo().getBusinessError().getErrorLabel());
			Assert.assertEquals("Create a Caller is not allowed without a Telecom", e.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}

	@Test
	public void testCreateOrUpdateIndividual_Caller_TelecomNoCountryCodeAndNoPhoneNumber()  {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.C.toString());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);
		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("M.");
		indInfo.setStatus("V");
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Contact bloc - Telecom - Mandatory
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
//		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
//		telecom.setPhoneNumber(generatePhone());
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);
		
		// WS call
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.assertEquals("ERROR_932", e.getFaultInfo().getBusinessError().getErrorCode().toString());
			Assert.assertEquals("INVALID PARAMETER", e.getFaultInfo().getBusinessError().getErrorLabel());
			Assert.assertEquals("Create a Caller is not allowed without a Telecom", e.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}

	
	@Test
	@Rollback(false)
	public void testCreateOrUpdateIndividual_Caller_TelecomNormalized()  {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.C.toString());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);
		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("M.");
		indInfo.setStatus("V");
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Contact bloc - Telecom - Mandatory
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber("+33" + generatePhone().substring(1));
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);
		
		// WS call
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail("On ne devrait pas avoir d erreur");
		}
		
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("96"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());

		logger.info("GIN created : " + response.getGin());

		logger.info("Test stop.");
	}
	

	@Test
	@Rollback(false)
	public void testCreateOrUpdateIndividual_Caller_TelecomNormalized_With_ContryCodeWhite()  {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.C.toString());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);
		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("M.");
		indInfo.setStatus("V");
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Contact bloc - Telecom - Mandatory
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33"); // TELECOM COUNTRY CODE SEEMS NOT TO BE TAKEN WHEN PHONE NUMBER IS ON INTERNATIONAL NUMBER
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber("+33" + generatePhone().substring(1));
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);
		
		// WS call
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail("On ne devrait pas avoir d erreur");
		}
		
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("96"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());

		logger.info("GIN created : " + response.getGin());

		logger.info("Test stop.");
	}

	
	@Test
	@Rollback(false)
	public void testCreateOrUpdateIndividual_CallerTelecomComplete()  {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.C.toString());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);
		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("M.");
		indInfo.setStatus("V");
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Contact bloc - Telecom - Mandatory
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
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail("On ne devrait pas avoir d erreur");
		}
		
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("96"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());

		logger.info("GIN created : " + response.getGin());

		logger.info("Test stop.");
	}
	

}
