package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.*;
import com.afklm.soa.stubs.w000442.v8.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.*;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.type.MediumCodeEnum;
import com.airfrance.ref.type.MediumStatusEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplV8ErrorTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV8ErrorTest.class);
	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T528182";
	private static final String SITE = "QVI";

	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v8Bean")
	private CreateOrUpdateIndividualImplV8 createOrUpdateIndividualImplV8;


	// Too long phone number
	@Test
	public void testCreateOrUpdateAnIndividualImplV8_Error701() {

		CreateOrUpdateAnIndividualImplV8ErrorTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ErrorTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ErrorTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ErrorTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("PHILIPPOT"); 
		indInfo.setFirstNameSC("PATRICK");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		Telecom telecom = new Telecom();
		telecom.setPhoneNumber("084845458452121");
		telecom.setCountryCode("FR");
		telecom.setMediumCode("D");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("M");

		TelecomRequest telecomRequest = new TelecomRequest();
		telecomRequest.setTelecom(telecom);

		request.getTelecomRequest().add(telecomRequest);

		try{
			createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		}
		catch(BusinessErrorBlocBusinessException e){
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_701, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}

	// Too short phone number
	@Test
	public void testCreateOrUpdateAnIndividualImplV8_Error702() {

		CreateOrUpdateAnIndividualImplV8ErrorTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ErrorTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ErrorTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ErrorTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("PHILIPPOT"); 
		indInfo.setFirstNameSC("PATRICK");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		Telecom telecom = new Telecom();
		telecom.setPhoneNumber("0848454");
		telecom.setCountryCode("FR");
		telecom.setMediumCode("D");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("M");

		TelecomRequest telecomRequest = new TelecomRequest();
		telecomRequest.setTelecom(telecom);

		request.getTelecomRequest().add(telecomRequest);

		try{
			createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		}
		catch(BusinessErrorBlocBusinessException e){
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_702, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}

	// Invalid phone number
	@Test
	public void testCreateOrUpdateAnIndividualImplV8_Error703() {

		CreateOrUpdateAnIndividualImplV8ErrorTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ErrorTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ErrorTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ErrorTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("PHILIPPOT"); 
		indInfo.setFirstNameSC("PATRICK");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		Telecom telecom = new Telecom();
		telecom.setPhoneNumber("084-h8454");
		telecom.setCountryCode("FR");
		telecom.setMediumCode("D");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("M");

		TelecomRequest telecomRequest = new TelecomRequest();
		telecomRequest.setTelecom(telecom);

		request.getTelecomRequest().add(telecomRequest);

		try{
			createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		}
		catch(BusinessErrorBlocBusinessException e){
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_703, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}

	// Invalid phone country code
	@Test
	public void testCreateOrUpdateAnIndividualImplV8_Error711() {

		CreateOrUpdateAnIndividualImplV8ErrorTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ErrorTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ErrorTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ErrorTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("PHILIPPOT"); 
		indInfo.setFirstNameSC("PATRICK");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		Telecom telecom = new Telecom();
		telecom.setPhoneNumber("0840948454");
		telecom.setCountryCode("FFSDR");
		telecom.setMediumCode("D");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("M");

		TelecomRequest telecomRequest = new TelecomRequest();
		telecomRequest.setTelecom(telecom);

		request.getTelecomRequest().add(telecomRequest);

		try{
			createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		}
		catch(BusinessErrorBlocBusinessException e){
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_711, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}

	// No normalized
	@Test
	public void testCreateOrUpdateAnIndividualImplV8_Error705() {

		CreateOrUpdateAnIndividualImplV8ErrorTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ErrorTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ErrorTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ErrorTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("PHILIPPOT"); 
		indInfo.setFirstNameSC("PATRICK");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		Telecom telecom = new Telecom();
		telecom.setPhoneNumber("754-3010");
		telecom.setCountryCode("US");
		telecom.setMediumCode("D");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");

		TelecomRequest telecomRequest = new TelecomRequest();
		telecomRequest.setTelecom(telecom);

		request.getTelecomRequest().add(telecomRequest);

		try{
			createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		}
		catch(BusinessErrorBlocBusinessException e){
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_705, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}


	// Invalid external identifier type
	@Test
	public void testCreateOrUpdateAnIndividualImplV8_Error714() {

		CreateOrUpdateAnIndividualImplV8ErrorTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ErrorTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ErrorTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ErrorTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("110000582531");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		ExternalIdentifier externalIdentifier = new ExternalIdentifier();
		externalIdentifier.setIdentifier("PNM_NAME");
		externalIdentifier.setType("CAPP");

		ExternalIdentifierRequest externalIdentifierRequest = new ExternalIdentifierRequest();
		externalIdentifierRequest.setExternalIdentifier(externalIdentifier);

		request.getExternalIdentifierRequest().add(externalIdentifierRequest);

		try{
			createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		}
		catch(BusinessErrorBlocBusinessException e){
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_714, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}


	// Invalid external identifier data value
	@Test
	public void testCreateOrUpdateAnIndividualImplV8_Error716() {

		CreateOrUpdateAnIndividualImplV8ErrorTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ErrorTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ErrorTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ErrorTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("110000582531");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		ExternalIdentifier externalIdentifier = new ExternalIdentifier();
		externalIdentifier.setIdentifier("a");
		externalIdentifier.setType("PNM_ID");

		ExternalIdentifierRequest externalIdentifierRequest = new ExternalIdentifierRequest();
		externalIdentifierRequest.setExternalIdentifier(externalIdentifier);

		ExternalIdentifierData externalIdentifierData = new ExternalIdentifierData();
		externalIdentifierData.setKey("PNM_NAME");
		externalIdentifierData.setValue("CAPPAAAAA");
		externalIdentifierRequest.getExternalIdentifierData().add(externalIdentifierData);

		request.getExternalIdentifierRequest().add(externalIdentifierRequest);

		try{
			createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		}
		catch(BusinessErrorBlocBusinessException e){
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_716, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}

	// Invalid pnm id
	@Test
	public void testCreateOrUpdateAnIndividualImplV8_Error717() {

		CreateOrUpdateAnIndividualImplV8ErrorTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ErrorTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ErrorTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ErrorTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("110000582531");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		ExternalIdentifier externalIdentifier = new ExternalIdentifier();
		externalIdentifier.setIdentifier("$-fg");
		externalIdentifier.setType("PNM_ID");

		ExternalIdentifierRequest externalIdentifierRequest = new ExternalIdentifierRequest();
		externalIdentifierRequest.setExternalIdentifier(externalIdentifier);

		request.getExternalIdentifierRequest().add(externalIdentifierRequest);

		try{
			createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		}
		catch(BusinessErrorBlocBusinessException e){
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_717, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}

	// MAXIMUM NUMBER OF PNM ID REACHED
	@Test
	public void testCreateOrUpdateAnIndividualImplV8_Error718() {

		CreateOrUpdateAnIndividualImplV8ErrorTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ErrorTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ErrorTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ErrorTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("800010231612");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		ExternalIdentifier externalIdentifier = new ExternalIdentifier();
		externalIdentifier.setIdentifier("a");
		externalIdentifier.setType("PNM_ID");

		ExternalIdentifierRequest externalIdentifierRequest = new ExternalIdentifierRequest();
		externalIdentifierRequest.setExternalIdentifier(externalIdentifier);

		ExternalIdentifierData externalIdentifierData = new ExternalIdentifierData();
		externalIdentifierData.setKey("OPTIN");
		externalIdentifierData.setValue("Y");
		externalIdentifierRequest.getExternalIdentifierData().add(externalIdentifierData);

		request.getExternalIdentifierRequest().add(externalIdentifierRequest);

		try{
			createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		}
		catch(BusinessErrorBlocBusinessException e){
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_718, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}

	// SHARED EMAIL
	@Test(expected = Test.None.class /* no exception expected */)
	@Rollback(true)
	public void testCreateOrUpdateAnIndividualImplV8_SharedEmailAllowed() throws BusinessErrorBlocBusinessException {

		CreateOrUpdateAnIndividualImplV8ErrorTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ErrorTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ErrorTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ErrorTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("000000195866");

		indRequest.setIndividualInformations(indInfo);

		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		email.setEmail("guichardch@hotmail.com");
		email.setMediumCode("D");
		emailRequest.setEmail(email);
		request.getEmailRequest().add(emailRequest);
		request.setIndividualRequest(indRequest);
		createOrUpdateIndividualImplV8.createIndividual(request);
	}


	// ALREADY EXISTS
	@Test
	@Ignore
	public void testCreateOrUpdateAnIndividualImplV8_Error384() {

		CreateOrUpdateAnIndividualImplV8ErrorTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ErrorTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ErrorTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ErrorTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("000000200700");

		indRequest.setIndividualInformations(indInfo);

		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		email.setEmail("p.mattei@corsicaferries.com");
		email.setMediumCode("D");
		emailRequest.setEmail(email);
		request.getEmailRequest().add(emailRequest);

		request.setIndividualRequest(indRequest);

		try{
			createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		}
		catch(BusinessErrorBlocBusinessException e){
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_384, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}

	// ACCOUNT NOT FOUND
	@Test
	public void testCreateOrUpdateAnIndividualImplV8_Error385() {

		CreateOrUpdateAnIndividualImplV8ErrorTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ErrorTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ErrorTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ErrorTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("PHILIPPOT"); 
		indInfo.setFirstNameSC("PATRICK");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		ExternalIdentifier externalIdentifier = new ExternalIdentifier();
		externalIdentifier.setIdentifier("a");
		externalIdentifier.setType("PNM_ID");

		ExternalIdentifierRequest externalIdentifierRequest = new ExternalIdentifierRequest();
		externalIdentifierRequest.setExternalIdentifier(externalIdentifier);

		request.getExternalIdentifierRequest().add(externalIdentifierRequest);

		try{
			createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		}
		catch(BusinessErrorBlocBusinessException e){
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_385, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}

	// SAPHIR CONTRACT NOT FOUND
	@Test
	public void testCreateOrUpdateAnIndividualImplV8_Error712() {

		CreateOrUpdateAnIndividualImplV8ErrorTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ErrorTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ErrorTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ErrorTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("400491001093");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		PrefilledNumbers prefilledNumbers = new PrefilledNumbers();
		prefilledNumbers.setContractNumber("AEFRDSF");
		prefilledNumbers.setContractType("S");

		PrefilledNumbersRequest prefilledNumbersRequest = new PrefilledNumbersRequest();
		prefilledNumbersRequest.setPrefilledNumbers(prefilledNumbers);

		request.getPrefilledNumbersRequest().add(prefilledNumbersRequest);

		try{
			createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		}
		catch(BusinessErrorBlocBusinessException e){
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_712, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}

	// SAPHIR NUMBER NOT MATCHING WITH INDIVIDUAL DATAS
	@Test
	public void testCreateOrUpdateAnIndividualImplV8_Error538() {

		CreateOrUpdateAnIndividualImplV8ErrorTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ErrorTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ErrorTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ErrorTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();

		indInfo.setIdentifier("400419686235");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		PrefilledNumbers prefilledNumbers = new PrefilledNumbers();
		prefilledNumbers.setContractNumber("000223635995");
		prefilledNumbers.setContractType("S");

		PrefilledNumbersRequest prefilledNumbersRequest = new PrefilledNumbersRequest();
		prefilledNumbersRequest.setPrefilledNumbers(prefilledNumbers);

		request.getPrefilledNumbersRequest().add(prefilledNumbersRequest);

		try{
			createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		}
		catch(BusinessErrorBlocBusinessException e){
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_538, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}

	// fix regression REPIND-1587
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_CiviltyIgnoreCase()
			throws BusinessErrorBlocBusinessException, SystemException, JrafDaoException {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setStatus("V");

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("DS");
		requestor.setSite("TLS");
		requestor.setSignature("DALLAS");

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();
		// indRequest.setCivilian("MR");

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("400509651532");
		indInfo.setCivility("Miss");
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);


		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());


	}


	// fix regression REPIND-1587
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_CiviltyNull()
			throws BusinessErrorBlocBusinessException, SystemException, JrafDaoException {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setStatus("V");

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("DS");
		requestor.setSite("TLS");
		requestor.setSignature("DALLAS");

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();
		// indRequest.setCivilian("MR");

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("400509651532");
		
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);


		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());


	}
	
	/**
	 * Test to validate that TELEX type telecom is not created and appropriate error
	 * is thrown
	 */
	@Test
	public void testCreateOrUpdateAnIndividualImplV8_Telex() {
		CreateOrUpdateAnIndividualImplV8ErrorTest.logger.info("Test start...");
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		// Preparing the request
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ErrorTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ErrorTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ErrorTest.SIGNATURE);
		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setBirthDate(new Date());
		indInfo.setCivility("MR");
		indInfo.setStatus(MediumStatusEnum.VALID.toString());
		indInfo.setLastNameSC("LNAME");
		indInfo.setFirstNameSC("FNAME");
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		Telecom telecom = new Telecom();
		telecom.setPhoneNumber("084845458452121");
		telecom.setCountryCode("FR");
		telecom.setMediumCode(MediumCodeEnum.HOME.toString());
		telecom.setMediumStatus(MediumStatusEnum.VALID.toString());
		telecom.setTerminalType("X");
		TelecomRequest telecomRequest = new TelecomRequest();
		telecomRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telecomRequest);

		try {
			createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}

	// Travel Companion with non-alphabetic characters for firstName
	@Test
	public void testCreateOrUpdateAnIndividualImplV8_TravelCompanionWrongFN() {
		CreateOrUpdateAnIndividualImplV8ErrorTest.logger.info("Test start...");
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ErrorTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ErrorTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ErrorTest.SIGNATURE);
		request.setRequestor(requestor);

		request.setProcess("I");

		IndividualRequest indRequest = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("400509651532");
		indInfo.setCivility("Miss");
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Set preference with wrong FN
		PreferenceV2 preference = new PreferenceV2();
		preference.setType("TCC");

		PreferenceDataV2 preferenceData1 = new PreferenceDataV2();
		preferenceData1.setKey("civility");
		preferenceData1.setValue("MR");

		PreferenceDataV2 preferenceData2 = new PreferenceDataV2();
		preferenceData2.setKey("email");
		preferenceData2.setValue("POI@REP.COM");

		PreferenceDataV2 preferenceData3 = new PreferenceDataV2();
		preferenceData3.setKey("lastname");
		preferenceData3.setValue("POIROT");

		PreferenceDataV2 preferenceData4 = new PreferenceDataV2();
		preferenceData4.setKey("firstname");
		preferenceData4.setValue("VALOU !");

		PreferenceDataV2 preferenceData5 = new PreferenceDataV2();
		preferenceData5.setKey("FFPNumber");
		preferenceData5.setValue("001070184011");

		PreferenceDataV2 preferenceData6 = new PreferenceDataV2();
		preferenceData6.setKey("dateOfBirth");
		preferenceData6.setValue("01/01/1964");

		// Create the full preference request
		PreferenceDatasV2 preferenceDatasV2 = new PreferenceDatasV2();
		preferenceDatasV2.getPreferenceData().add(preferenceData1);
		preferenceDatasV2.getPreferenceData().add(preferenceData2);
		preferenceDatasV2.getPreferenceData().add(preferenceData3);
		preferenceDatasV2.getPreferenceData().add(preferenceData4);
		preferenceDatasV2.getPreferenceData().add(preferenceData5);
		preferenceDatasV2.getPreferenceData().add(preferenceData6);

		preference.setPreferenceDatas(preferenceDatasV2);

		PreferenceRequest preferenceRequest = new PreferenceRequest();
		preferenceRequest.getPreference().add(preference);

		request.setPreferenceRequest(preferenceRequest);



		try {
			createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}

	// Travel Companion with non-alphabetic characters for lastName
	@Test
	public void testCreateOrUpdateAnIndividualImplV8_TravelCompanionWrongLN() {
		CreateOrUpdateAnIndividualImplV8ErrorTest.logger.info("Test start...");
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ErrorTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ErrorTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ErrorTest.SIGNATURE);
		request.setRequestor(requestor);

		request.setProcess("I");

		IndividualRequest indRequest = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("400509651532");
		indInfo.setCivility("Miss");
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Set preference with wrong FN
		PreferenceV2 preference = new PreferenceV2();
		preference.setType("TCC");

		PreferenceDataV2 preferenceData1 = new PreferenceDataV2();
		preferenceData1.setKey("civility");
		preferenceData1.setValue("MR");

		PreferenceDataV2 preferenceData2 = new PreferenceDataV2();
		preferenceData2.setKey("email");
		preferenceData2.setValue("POI@REP.COM");

		PreferenceDataV2 preferenceData3 = new PreferenceDataV2();
		preferenceData3.setKey("lastname");
		preferenceData3.setValue("POIROT&");

		PreferenceDataV2 preferenceData4 = new PreferenceDataV2();
		preferenceData4.setKey("firstname");
		preferenceData4.setValue("VALOU");

		PreferenceDataV2 preferenceData5 = new PreferenceDataV2();
		preferenceData5.setKey("FFPNumber");
		preferenceData5.setValue("001070184011");

		PreferenceDataV2 preferenceData6 = new PreferenceDataV2();
		preferenceData6.setKey("dateOfBirth");
		preferenceData6.setValue("01/01/1964");

		// Create the full preference request
		PreferenceDatasV2 preferenceDatasV2 = new PreferenceDatasV2();
		preferenceDatasV2.getPreferenceData().add(preferenceData1);
		preferenceDatasV2.getPreferenceData().add(preferenceData2);
		preferenceDatasV2.getPreferenceData().add(preferenceData3);
		preferenceDatasV2.getPreferenceData().add(preferenceData4);
		preferenceDatasV2.getPreferenceData().add(preferenceData5);
		preferenceDatasV2.getPreferenceData().add(preferenceData6);

		preference.setPreferenceDatas(preferenceDatasV2);

		PreferenceRequest preferenceRequest = new PreferenceRequest();
		preferenceRequest.getPreference().add(preference);

		request.setPreferenceRequest(preferenceRequest);


		try {
			createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}

}
