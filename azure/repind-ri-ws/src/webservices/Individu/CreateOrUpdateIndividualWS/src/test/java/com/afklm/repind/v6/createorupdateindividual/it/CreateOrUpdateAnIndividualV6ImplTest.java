package com.afklm.repind.v6.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v6.createorupdateindividualws.CreateOrUpdateIndividualImplV6;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000442.v6.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v6.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v6.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v6.request.EmailRequest;
import com.afklm.soa.stubs.w000442.v6.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v6.request.PostalAddressRequest;
import com.afklm.soa.stubs.w000442.v6.request.TelecomRequest;
import com.afklm.soa.stubs.w000442.v6.response.BusinessError;
import com.afklm.soa.stubs.w000442.v6.response.BusinessErrorBloc;
import com.afklm.soa.stubs.w000442.v6.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000442.v6.siccommontype.Requestor;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.*;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.CivilityEnum;
import com.airfrance.ref.type.GenderEnum;
import com.airfrance.ref.type.MediumCodeEnum;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;


@ActiveProfiles("test")
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
class CreateOrUpdateAnIndividualV6ImplTest { // extends CreateOrUpdateIndividualImplV6{

	private static Log log = LogFactory.getLog(CreateOrUpdateAnIndividualV6ImplTest.class);

	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v6Bean")
	private CreateOrUpdateIndividualImplV6 createOrUpdateIndividualImplV6;	

	@Autowired
	private EmailDS emailDS;

	@Autowired
	private IndividuRepository individuRepository;

	private final String CHANNEL = "DS";
	private final String APPLI_ISI = "ISI";
	private final String APPLI_BDC = "BDC";
	private final String SITE = "QVI";
	private final String SIGN = "TestI";
	private final String BIRTHDATE = "01/01/1980";

	private String generateString() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random rd = new Random();
		for (int i = 0; i < 10; ++i) {
			char c = chars[rd.nextInt(chars.length)];
			sb.append(c);
		}

		return sb.toString();
	}

	private String generateEmail() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random rd = new Random();

		for (int i = 0; i < 12; ++i) {
			char c = chars[rd.nextInt(chars.length)];
			sb.append(c);
			sb.append(i == 7 ? "." : "");
		}
		sb.append("@bidon.fr");

		return sb.toString();
	}

	// Fix regression REPIND-1587
	@Test
	@Rollback(true)
	void testCreateOrUpdateIndividual_CivilityIgnoreCase()
			throws BusinessErrorBlocBusinessException, SystemException, JrafDaoException {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setStatus("V");

		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel("DS");
		requestor.setApplicationCode("ISI");
		requestor.setSite("TLS");
		requestor.setSignature("DALLAS");

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setIdentifier("400509651532");
		indInfo.setCivility("Miss");
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV6.createIndividual(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());

	}

	// Fix regression REPIND-1587
	@Test
	@Rollback(true)
	void testCreateOrUpdateIndividual_CivilityNull()
			throws BusinessErrorBlocBusinessException, SystemException, JrafDaoException {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setStatus("V");

		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel("DS");
		requestor.setApplicationCode("ISI");
		requestor.setSite("TLS");
		requestor.setSignature("DALLAS");

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setIdentifier("400509651532");
		
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV6.createIndividual(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());

	}

	// Fix regression REPIND-1587
	@Test
	@Rollback(true)
	void testCreateOrUpdateIndividual_CivilityMPointIgnoreCase()
			throws BusinessErrorBlocBusinessException, SystemException, JrafDaoException {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setStatus("V");

		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel("DS");
		requestor.setApplicationCode("ISI");
		requestor.setSite("TLS");
		requestor.setSignature("DALLAS");

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setIdentifier("400509651532");
		indInfo.setCivility("M.");
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV6.createIndividual(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());

	}

	// BUG SURCOUF
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	void testCreateOrUpdateIndividual_SurcoufBUG() throws BusinessErrorBlocBusinessException, SystemException {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setStatus("V");

		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel("SURCW");
		requestor.setToken("WSSiC2");
		requestor.setApplicationCode("MAC");
		requestor.setSite("QVI");
		requestor.setSignature("SURCOUFWEB");

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();
		// indRequest.setCivilian("MR");

		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setBirthDate(new Date());
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("Konijnenbelt"); 
		indInfo.setFirstNameSC("W");

		indRequest.setIndividualInformations(indInfo);		

		request.setIndividualRequest(indRequest);


		// Email
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		email.setMediumCode("D");
		email.setMediumStatus("V");
		email.setEmail("MAIL@KONIJNENBELTWETGEVING.NL");
		emailRequest.setEmail(email);
		request.getEmailRequest().add(emailRequest);

		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("Baronielaan 229");
		pac.setZipCode("4835JK");
		pac.setCity("Breda");
		pac.setCountryCode("NL");
		pac.setAdditionalInformation("");
		pac.setDistrict("");
		pac.setStateCode("");
		pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);

		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("P");
		pap.setMediumStatus("V");
		// pap.setVersion("1");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressProperties(pap);

		/*UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("TU");
		addRequest.setUsageAddress(ua);
		 */		
		request.getPostalAddressRequest().add(addRequest);

		// Telecom
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setCountryCode("+31");
		telecom.setMediumCode("D");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber("0768882841");
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);


		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV6.createIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

	}

	private IndividualRequest generateNewIndividual() {
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformations indInfo = new IndividualInformations();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date date = df.parse(BIRTHDATE);
			indInfo.setBirthDate(date);
			indInfo.setCivility("Miss");
			indInfo.setStatus("V");
			indInfo.setLastNameSC("test"); 
			indInfo.setFirstNameSC("integration");

			indRequest.setIndividualInformations(indInfo);		

		} catch (ParseException e) {
			log.error(e.getMessage());
		}

		return indRequest;
	}

	private PostalAddressRequest generateValidAddress() {
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("1201 ROUTE DES CRETES");
		pac.setZipCode("06560");
		pac.setCity("VALBONNE");
		pac.setCountryCode("FR");
		addRequest.setPostalAddressContent(pac);

		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setIndicAdrNorm(true);
		pap.setMediumCode("D");
		pap.setMediumStatus("V");
		addRequest.setPostalAddressProperties(pap);

		return addRequest;
	}



	// OPTIN OPTOUT ?
	@Test
	void testCreateOrUpdateIndividual_HachikoBUG() throws BusinessErrorBlocBusinessException, SystemException {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setStatus("V");

		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel("IOB");
		requestor.setToken("remove");
		requestor.setApplicationCode("ISI");
		requestor.setSite("TLS");
		requestor.setSignature("HACHIKO");

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();
		// indRequest.setCivilian("MR");

		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setBirthDate(new Date());
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("NOMBUGHACHIKO" + RandomStringUtils.randomAlphabetic(5)); 
		indInfo.setFirstNameSC("PENOMBUGHACHIKO" + RandomStringUtils.randomAlphabetic(5));
		indInfo.setLanguageCode("EN");

		indRequest.setIndividualInformations(indInfo);		


		IndividualProfil ip = new IndividualProfil();
		ip.setEmailOptin("N");
		ip.setLanguageCode("FR");

		indRequest.setIndividualProfil(ip);



		request.setIndividualRequest(indRequest);


		// Email
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		email.setMediumCode("D");
		email.setMediumStatus("V");
		email.setEmail("MAIL" + RandomStringUtils.randomAlphabetic(5) + "CBS@test.fr");
		email.setEmailOptin("N");
		emailRequest.setEmail(email);
		request.getEmailRequest().add(emailRequest);

		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("not collected");
		pac.setZipCode("99999");
		pac.setCity("not collected");
		pac.setCountryCode("FR");
		pac.setAdditionalInformation("not collected");
		pac.setStateCode("ZZ");
		addRequest.setPostalAddressContent(pac);

		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("D");
		pap.setMediumStatus("V");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressProperties(pap);

		/*		UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("ISI");
		ua.setAddressRoleCode("M");
		addRequest.setUsageAddress(ua);
		 */
		request.getPostalAddressRequest().add(addRequest);

		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV6.createIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

	}

	// REPIND-1288 : Store Email as Minus in database

	@Test
	void testCreateOrUpdateIndividual_Prospect_CreateNewIndividualEmailUPPER() throws BusinessErrorBlocBusinessException, JrafDomainException, SystemException {

		log.info("Test start... ");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		/*** Set parameters ***/
		// Requestor
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setContext("B2C_HOME_PAGE");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Individual
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformations indInfo = new IndividualInformations();
		// indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC(generateString()); 
		indInfo.setFirstNameSC(generateString());

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String dateInString = "2014-10-05T12:00:00";
		Date date;
		try {
			date = formatter.parse(dateInString);
			indInfo.setBirthDate(date);
		} catch (ParseException e) {
			log.error(e.getMessage());
		}

		indRequest.setIndividualInformations(indInfo);

		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("Baronielaan 229");
		pac.setZipCode("4835JK");
		pac.setCity("Breda");
		pac.setCountryCode("NL");
		pac.setAdditionalInformation("");
		pac.setDistrict("");
		pac.setStateCode("");
		pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);

		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode(MediumCodeEnum.BUSINESS.toString());
		pap.setMediumStatus("V");
		// pap.setVersion("1");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressProperties(pap);


		// Email
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		String emailUpper = generateEmail().toUpperCase();
		email.setEmail(emailUpper);
		email.setMediumCode("D");
		email.setMediumStatus("V");
		emailRequest.setEmail(email);

		request.setRequestor(requestor);
		request.getEmailRequest().add(emailRequest);
		request.setIndividualRequest(indRequest);
		request.getPostalAddressRequest().add(addRequest);

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV6.createIndividual(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

		List<EmailDTO> listEmails = emailDS.findEmail(response.getGin());
		Assert.assertNotEquals(emailUpper, listEmails.get(0).getEmail());	

		log.info("Gin updated: " + response.getGin());
		log.info("End of test");
	}

	@Test
	void testCreateOrUpdateIndividual_Prospect_UpdateIndividualEmailUPPER() throws BusinessErrorBlocBusinessException, JrafDomainException, SystemException {

		log.info("Test start... ");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		/*** Set parameters ***/
		// Requestor
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setContext("B2C_HOME_PAGE");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Individual
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformations indInfo = new IndividualInformations();
		// indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC(generateString()); 
		indInfo.setFirstNameSC(generateString());

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String dateInString = "2014-10-05T12:00:00";
		Date date;
		try {
			date = formatter.parse(dateInString);
			indInfo.setBirthDate(date);
		} catch (ParseException e) {
			log.error(e.getMessage());
		}

		indRequest.setIndividualInformations(indInfo);

		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("Baronielaan 229");
		pac.setZipCode("4835JK");
		pac.setCity("Breda");
		pac.setCountryCode("NL");
		pac.setAdditionalInformation("");
		pac.setDistrict("");
		pac.setStateCode("");
		pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);

		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode(MediumCodeEnum.BUSINESS.toString());
		pap.setMediumStatus("V");
		// pap.setVersion("1");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressProperties(pap);


		// Email
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		String emailUpper = generateEmail().toUpperCase();
		email.setEmail(emailUpper);
		email.setMediumCode("D");
		email.setMediumStatus("V");
		emailRequest.setEmail(email);

		request.setRequestor(requestor);
		request.getEmailRequest().add(emailRequest);
		request.setIndividualRequest(indRequest);
		request.getPostalAddressRequest().add(addRequest);

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV6.createIndividual(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

		String gin = response.getGin();
		List<EmailDTO> listEmails = emailDS.findEmail(gin);
		Assert.assertNotEquals(emailUpper, listEmails.get(0).getEmail());	

		/* -- UPDATE -- */
		CreateUpdateIndividualRequest requestUpdate = new CreateUpdateIndividualRequest();

		IndividualRequest indRequestUpdate = new IndividualRequest();
		IndividualInformations indInfoUpdate = new IndividualInformations();
		indInfoUpdate.setIdentifier(response.getGin());
		indRequestUpdate.setIndividualInformations(indInfoUpdate);

		// Email
		EmailRequest emailRequestUpdate = new EmailRequest();
		Email emailUpdate = new Email();
		String emailUpperUpdate = "UPDATE_" + listEmails.get(0).getEmail();
		emailUpdate.setEmail(emailUpperUpdate);
		emailUpdate.setMediumCode("D");
		emailUpdate.setMediumStatus("V");
		emailRequestUpdate.setEmail(emailUpdate);

		requestUpdate.setRequestor(requestor);
		requestUpdate.setIndividualRequest(indRequestUpdate);
		requestUpdate.getEmailRequest().add(emailRequestUpdate);

		// Execute test
		response = createOrUpdateIndividualImplV6.createIndividual(requestUpdate);

		listEmails = emailDS.findEmail(gin);
		Assert.assertNotEquals(emailUpperUpdate, listEmails.get(0).getEmail());	

		log.info("Gin updated: " + gin);
		log.info("End of test");
	}



	@Test
	void testCreateOrUpdateIndividual_CreateIndividualWithNonValidCivility() throws BusinessErrorBlocBusinessException, JrafDomainException{

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature("TU-T412211");

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();
		
		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setFirstNameSC("First" + generateString());
		indInfo.setLastNameSC("Last" + generateString());
		indInfo.setCivility("MAN");
		indRequest.setIndividualInformations(indInfo);
		
		IndividualProfil ip = new IndividualProfil();
		ip.setLanguageCode("FR");
		
		indRequest.setIndividualProfil(ip);
		request.setIndividualRequest(indRequest);
		
		// WS call
		try {
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV6.createIndividual(request);		
			Assert.fail("We should have an Exception due to CIVILITY");
			
		} catch (Exception e) {			
			
			BusinessErrorBlocBusinessException bebe = (BusinessErrorBlocBusinessException) e;			
			Assert.assertNotNull(bebe.getFaultInfo());
			
			BusinessErrorBloc beb = bebe.getFaultInfo();			
			Assert.assertNotNull(beb);

			BusinessError be = beb.getBusinessError();			
			Assert.assertNotNull(be);
			Assert.assertNotNull(be.getErrorCode());
			
			Assert.assertEquals("ERROR_133", be.getErrorCode().toString());
			Assert.assertEquals("MISSING PARAMETERS", be.getErrorLabel());
			Assert.assertEquals("Missing parameter exception: The field civility not valid", be.getErrorDetail());
		}
	}

	@Test
	void testCreateOrUpdateIndividual_UpdateIndividualWithNonValidCivility() throws BusinessErrorBlocBusinessException, JrafDomainException{

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature("TU-T412211");

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();
		
		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setIdentifier("400491922886");
		indInfo.setCivility("MAN");
		indRequest.setIndividualInformations(indInfo);
		
		IndividualProfil ip = new IndividualProfil();
		ip.setLanguageCode("FR");
		
		indRequest.setIndividualProfil(ip);
		request.setIndividualRequest(indRequest);
		
		// WS call
		try {
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV6.createIndividual(request);		
			Assert.fail("We should have an Exception due to CIVILITY");
			
		} catch (Exception e) {			
			
			BusinessErrorBlocBusinessException bebe = (BusinessErrorBlocBusinessException) e;			
			Assert.assertNotNull(bebe.getFaultInfo());
			
			BusinessErrorBloc beb = bebe.getFaultInfo();			
			Assert.assertNotNull(beb);

			BusinessError be = beb.getBusinessError();			
			Assert.assertNotNull(be);
			Assert.assertNotNull(be.getErrorCode());
			
			Assert.assertEquals("ERROR_932", be.getErrorCode().toString());
			Assert.assertEquals("INVALID PARAMETER", be.getErrorLabel());
			Assert.assertEquals("Invalid parameter: The field civility not valid", be.getErrorDetail());
		}
	}
	
	@Test
	void testCreateOrUpdateIndividual_CreateIndividualWithNonValidTitle() throws BusinessErrorBlocBusinessException, JrafDomainException{

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature("TU-T412211");

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();
		
		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setIdentifier("400509999266");
		
		
		indInfo.setCivility("MISS");		
		indRequest.setIndividualInformations(indInfo);
		
		Civilian civ = new Civilian();
		civ.setTitleCode("RAF");
		
		indRequest.setCivilian(civ);
		
		IndividualProfil ip = new IndividualProfil();
		ip.setLanguageCode("FR");
		
		indRequest.setIndividualProfil(ip);
		request.setIndividualRequest(indRequest);
		
		// WS call
		try {
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV6.createIndividual(request);		
			Assert.fail("We should have an Exception due to TITLE");
			
		} catch (Exception e) {			
			
			BusinessErrorBlocBusinessException bebe = (BusinessErrorBlocBusinessException) e;			
			Assert.assertNotNull(bebe.getFaultInfo());
			
			BusinessErrorBloc beb = bebe.getFaultInfo();			
			Assert.assertNotNull(beb);

			BusinessError be = beb.getBusinessError();			
			Assert.assertNotNull(be);
			Assert.assertNotNull(be.getErrorCode());
			
			Assert.assertEquals("ERROR_905", be.getErrorCode().toString());
			Assert.assertEquals("TECHNICAL ERROR", be.getErrorLabel());
		}
	}


	@Test
	@Rollback(false)
	void testCreateOrUpdateIndividual_CreateIndividualWithMinusCivility() throws BusinessErrorBlocBusinessException, JrafDomainException, SystemException{

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature("TU-T412211");

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();
		
		IndividualInformations indInfo = new IndividualInformations();
//		indInfo.setIdentifier(GIN);
		indInfo.setCivility("ms ");
		
		indInfo.setFirstNameSC("Nom");
		indInfo.setLastNameSC("Prénom");
		indInfo.setStatus("V");
		
/*		
		"M.";
		"MISS";
		"MR";
		"MRS";
		"MS";
*/	
		indRequest.setIndividualInformations(indInfo);
				
		IndividualProfil ip = new IndividualProfil();
		ip.setLanguageCode("FR");
		
		indRequest.setIndividualProfil(ip);
		request.setIndividualRequest(indRequest);
		
		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV6.createIndividual(request);		
	
		Assert.assertNotNull(response);
		
		Assert.assertNotNull(response.isSuccess());
		
		Assert.assertTrue(response.isSuccess());
		
	}

	@Test
	@Rollback(false)
	void testCreateOrUpdateIndividual_UpdateIndividualWithMinusCivility() throws BusinessErrorBlocBusinessException, JrafDomainException, SystemException{

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature("TU-T412211");

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();
		
		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setIdentifier("400509997925");
		indInfo.setCivility("ms ");
		indInfo.setStatus("V");

		indRequest.setIndividualInformations(indInfo);
				
		IndividualProfil ip = new IndividualProfil();
		ip.setLanguageCode("FR");
		
		indRequest.setIndividualProfil(ip);
		request.setIndividualRequest(indRequest);
		
		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV6.createIndividual(request);		
	
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
	}

	/**
	 * Test to validate that TELEX type telecom is not created and appropriate error
	 * is thrown
	 * @throws SystemException 
	 */
	@Test
	void createOrUpdateIndividualV6_Telex() throws SystemException {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		// Preparing the request
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGN);
		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();
		IndividualInformations indInfo = new IndividualInformations();
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
			createOrUpdateIndividualImplV6.createIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_701, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}

	@Test
	@Rollback(false)
	void testCreateOrUpdateIndividual_DefaultGenderAndCivility() throws BusinessErrorBlocBusinessException, JrafDomainException, SystemException{

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature("TU-T412211");

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setFirstNameSC("Nom");
		indInfo.setLastNameSC("Prénom");
		indInfo.setStatus("V");

/*
		"M.";
		"MISS";
		"MR";
		"MRS";
		"MS";
*/
		indRequest.setIndividualInformations(indInfo);

		IndividualProfil ip = new IndividualProfil();
		ip.setLanguageCode("FR");

		indRequest.setIndividualProfil(ip);
		request.setIndividualRequest(indRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV6.createIndividual(request);

		Assert.assertNotNull(response);

		Assert.assertNotNull(response.isSuccess());

		Assert.assertTrue(response.isSuccess());

		// Check individual
		Individu testIndividu = individuRepository.findBySgin(response.getGin());
		Assertions.assertEquals(GenderEnum.UNKNOWN.toString(), testIndividu.getSexe());
		Assertions.assertEquals(CivilityEnum.M_.toString(), testIndividu.getCivilite());
	}

}
