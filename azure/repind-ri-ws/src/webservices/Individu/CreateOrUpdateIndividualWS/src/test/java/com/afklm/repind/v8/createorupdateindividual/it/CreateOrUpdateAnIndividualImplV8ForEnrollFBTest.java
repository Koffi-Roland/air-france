package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.EmailRequest;
import com.afklm.soa.stubs.w000442.v8.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8.request.PostalAddressRequest;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.*;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.adresse.PostalAddressRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.profil.ProfilsRepository;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.profil.Profils;
import com.airfrance.repind.util.SicDateUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplV8ForEnrollFBTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV8ForEnrollFBTest.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String APPLICATION_CODE = "ISI";
	
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	@Autowired
	private IndividuRepository individuRepository;

	@Autowired 
	private PostalAddressRepository postalAddressRepository;
	
	@Autowired 
	private ProfilsRepository profilsRepository;

	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v8Bean")
	private CreateOrUpdateIndividualImplV8 createOrUpdateIndividualImplV8;

	private String generateString() {
		return RandomStringUtils.randomAlphabetic(10);
	}


	// Nettoyage en base de données juste avant de lancer les TU 
	@Before
 	@Transactional
	@Rollback(false)
	public void beforeTest() throws JrafDaoException, InvalidParameterException {

				
	}

 	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Rollback(false)
	private void resetIndividuAndPostalAddress(String gin, String type) throws JrafDaoException, InvalidParameterException {
 		individuRepository.updateTypeByGin(gin, type);		
		List<PostalAddress> lpad = 	postalAddressRepository.findPostalAddress(gin);
		for (PostalAddress pa : lpad) {			
			postalAddressRepository.deleteById(pa.getSain());
		}
		
		entityManager.flush();
	}

 	@Transactional
	@Rollback(false)
	private void resetIndividu(String gin, String type) throws JrafDaoException, InvalidParameterException {
 		individuRepository.updateTypeByGin(gin, type);		
	}
 	
	@Test
	public void testCreateOrUpdateIndividual_Update_Prospect_ForEnroll() throws BusinessErrorBlocBusinessException, InvalidParameterException, JrafDaoException{

		// Reset individual data and postal address
		resetIndividuAndPostalAddress("900029605553", "W");
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

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
		indInfo.setIdentifier("900029605553");
		indInfo.setCivility("MISS");
		indInfo.setLastNameSC("WITTENBERG");
		indInfo.setFirstNameSC("ISAAC");
		indInfo.setBirthDate(new Date());
		indInfo.setStatus("V");
		indInfo.setLanguageCode("EN");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("1180 route des dollines");
		pac.setZipCode("99999");
		pac.setCity("not collected");
		pac.setCountryCode("BR");
		pac.setAdditionalInformation("not collected");
		pac.setDistrict("");
		pac.setStateCode("ZZ");
		pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);
		
		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("D");		// Direct
		pap.setMediumStatus("I");	// Invalid
		pap.setVersion("1");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressProperties(pap);
		
		UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("ISI");
		ua.setAddressRoleCode("M");
		addRequest.setUsageAddress(ua);
		
		request.getPostalAddressRequest().add(addRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
	}

	@Test
	public void testCreateOrUpdateIndividual_Update_Prospect_ForEnroll_Usage() throws BusinessErrorBlocBusinessException, InvalidParameterException, JrafDaoException{

		// Reset individual data and postal address
		resetIndividuAndPostalAddress("900000152049", "W");		
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

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
		indInfo.setIdentifier("900000152049");
		indInfo.setCivility("M.");
		indInfo.setLastNameSC("IVANOVA");
		indInfo.setFirstNameSC("ALEKSANDRE");
		indInfo.setBirthDate(new Date());
		indInfo.setStatus("V");
		indInfo.setLanguageCode("EN");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("1180 route des dollines");
		pac.setZipCode("99999");
		pac.setCity("not collected");
		pac.setCountryCode("BR");
		pac.setAdditionalInformation("not collected");
		pac.setDistrict("");
		pac.setStateCode("ZZ");
		pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);
		
		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("D");		// Direct
		pap.setMediumStatus("I");	// Invalid
		pap.setVersion("1");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressProperties(pap);
		
		UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("ISI");
		ua.setAddressRoleCode("M");
		addRequest.setUsageAddress(ua);
		
		request.getPostalAddressRequest().add(addRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
	}

	@Test
	public void testCreateOrUpdateIndividual_Update_Traveler_ForEnroll_Usage() throws BusinessErrorBlocBusinessException, InvalidParameterException, JrafDaoException{

		// Reset individual data and postal address
		resetIndividuAndPostalAddress("910000076402", "T");		

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

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
		indInfo.setIdentifier("910000076402");
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("TRAVDMERATTFXD");
		indInfo.setFirstNameSC("TRAVIBFUQDONJL");
		indInfo.setBirthDate(new Date());
		indInfo.setStatus("V");
		indInfo.setLanguageCode("EN");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("1180 route des dollines");
		pac.setZipCode("99999");
		pac.setCity("not collected");
		pac.setCountryCode("BR");
		pac.setAdditionalInformation("not collected");
		pac.setDistrict("");
		pac.setStateCode("ZZ");
		pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);
		
		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("D");		// Direct
		pap.setMediumStatus("I");	// Invalid
		pap.setVersion("1");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressProperties(pap);
		
		UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("ISI");
		ua.setAddressRoleCode("M");
		addRequest.setUsageAddress(ua);
		
		request.getPostalAddressRequest().add(addRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
	}

	@Test
	public void testCreateOrUpdateIndividual_Update_Traveler_BirthDateNull_ForEnroll_Usage() throws BusinessErrorBlocBusinessException, InvalidParameterException, JrafDaoException{

		// Reset individual data and postal address
		resetIndividuAndPostalAddress("910000018265", "T");		

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
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
		indInfo.setIdentifier("910000018265");
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("CZZVHZPOUV");
		indInfo.setFirstNameSC("SZVQLZETPN");
		indInfo.setStatus("V");
		indInfo.setLanguageCode("FR");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("1180 route des dollines");
		pac.setZipCode("99999");
		pac.setCity("not collected");
		pac.setCountryCode("FR");
		pac.setAdditionalInformation("not collected");
		pac.setDistrict("");
		pac.setStateCode("ZZ");
		pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);
		
		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("D");		// Direct
		pap.setMediumStatus("I");	// Invalid
		pap.setVersion("1");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressProperties(pap);
		
		UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("ISI");
		ua.setAddressRoleCode("M");
		addRequest.setUsageAddress(ua);
		
		request.getPostalAddressRequest().add(addRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
	}

	@Test
	public void testCreateOrUpdateIndividual_Update_Prospect_NomPrenomBirthDateNull_ForEnroll_Usage() throws BusinessErrorBlocBusinessException, InvalidParameterException, JrafDaoException{

		// Reset individual data and postal address
		resetIndividuAndPostalAddress("900000245436", "W");		
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

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
		indInfo.setIdentifier("900000245436");
		indInfo.setCivility("M.");
 		indInfo.setLastNameSC("MA");
		indInfo.setFirstNameSC("FA");
		indInfo.setStatus("V");
		indInfo.setLanguageCode("FR");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("1180 route des dollines");
		pac.setZipCode("99999");
		pac.setCity("not collected");
		pac.setCountryCode("FR");
		pac.setAdditionalInformation("not collected");
		pac.setDistrict("");
		pac.setStateCode("ZZ");
		pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);
		
		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("D");		// Direct
		pap.setMediumStatus("I");	// Invalid
		pap.setVersion("1");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressProperties(pap);
		
		UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("ISI");
		ua.setAddressRoleCode("M");
		addRequest.setUsageAddress(ua);
		
		request.getPostalAddressRequest().add(addRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
		
		
	}

	@Test
	public void testCreateOrUpdateIndividual_Update_Prospect_NomPrenomNullDateNaissance1900_ForEnroll_Usage() throws BusinessErrorBlocBusinessException, InvalidParameterException, JrafDaoException{
	// La date de naissance de base de données est mise a NULL car pas de date de naissance fourni 

		// Reset individual data and postal address
		resetIndividuAndPostalAddress("900000168555", "W");		
				
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

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
		indInfo.setIdentifier("900000168555");
		indInfo.setCivility("M.");
 		indInfo.setLastNameSC("MA");
		indInfo.setFirstNameSC("MA");
		indInfo.setStatus("V");
		indInfo.setLanguageCode("FR");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("1180 route des dollines");
		pac.setZipCode("99999");
		pac.setCity("not collected");
		pac.setCountryCode("FR");
		pac.setAdditionalInformation("not collected");
		pac.setDistrict("");
		pac.setStateCode("ZZ");
		pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);
		
		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("D");		// Direct
		pap.setMediumStatus("I");	// Invalid
		pap.setVersion("1");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressProperties(pap);
		
		UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("ISI");
		ua.setAddressRoleCode("M");
		addRequest.setUsageAddress(ua);
		
		request.getPostalAddressRequest().add(addRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
	}

	@Test
	public void testCreateOrUpdateIndividual_Update_Prospect_NomPrenomNullDateNaissance1900_WithDate_ForEnroll_Usage() throws BusinessErrorBlocBusinessException, InvalidParameterException, JrafDaoException{
	// La date de naissance de base de données est mise a jour avec la date de naissance fourni 

		// Reset individual data and postal address
		resetIndividuAndPostalAddress("900000168672", "W");		
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

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
		indInfo.setIdentifier("900000168672");
		indInfo.setCivility("M.");
 		indInfo.setLastNameSC("MA");
		indInfo.setFirstNameSC("MA");
		indInfo.setStatus("V");
		indInfo.setBirthDate(new Date()); 	// Date de naissance a ce jour !
		indInfo.setLanguageCode("FR");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("1180 route des dollines");
		pac.setZipCode("99999");
		pac.setCity("not collected");
		pac.setCountryCode("FR");
		pac.setAdditionalInformation("not collected");
		pac.setDistrict("");
		pac.setStateCode("ZZ");
		pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);
		
		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("D");		// Direct
		pap.setMediumStatus("I");	// Invalid
		pap.setVersion("1");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressProperties(pap);
		
		UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("ISI");
		ua.setAddressRoleCode("M");
		addRequest.setUsageAddress(ua);
		
		request.getPostalAddressRequest().add(addRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
	}
	
	@Test
	public void testCreateOrUpdateIndividual_Update_Prospect_NomPrenomDateNaissanceEmail_RCT() throws BusinessErrorBlocBusinessException, JrafDomainException{

		// Reset individual data and postal address
		resetIndividu("900029595554", "W");		
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

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
		indInfo.setIdentifier("900029595554");
		indInfo.setCivility("MR");
 		indInfo.setLastNameSC("MAYGAR");
		indInfo.setFirstNameSC("JANTINUS");
		indInfo.setStatus("V");
		indInfo.setBirthDate(SicDateUtils.convertStringToDateDDMMYYYY("01/07/1955"));
		indInfo.setLanguageCode("EN");
		indRequest.setIndividualInformations(indInfo);
		
		IndividualProfilV3 ip = new IndividualProfilV3();
		ip.setEmailOptin("N");
		ip.setLanguageCode("EN");
		
		indRequest.setIndividualProfil(ip);
		request.setIndividualRequest(indRequest);

		// Email address
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		email.setEmail("e6ff198ea576e4ca@gmail.com");
		email.setMediumCode("D");
		email.setMediumStatus("V");
		// email.setVersion("1");
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
		// pac.setDistrict("");
		pac.setStateCode("ZZ");
		// pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);
		
		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("D");		// Direct
		pap.setMediumStatus("I");	// Invalid
		// pap.setVersion("1");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressProperties(pap);
		
		UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("ISI");
		ua.setAddressRoleCode("M");
		addRequest.setUsageAddress(ua);
		
		request.getPostalAddressRequest().add(addRequest);

		
		
		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
	}

	// SWITCH PROSPECT/TRAVELER/SOCIAL TO INDIVIDUAL WITHOUT PROFIL
	
	@Test
 	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Update_Prospect_CreateProfil() throws BusinessErrorBlocBusinessException, JrafDomainException{

		String gin = "900045826141";
		
		// Reset individual data and postal address
		// resetIndividu(gin, "W");
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(gin);
		indInfo.setCivility("MR");
 		indInfo.setLastNameSC("MAYGARUU");
		indInfo.setFirstNameSC("JANTINUSUUS");
		indInfo.setStatus("V");
		indInfo.setBirthDate(SicDateUtils.convertStringToDateDDMMYYYY("01/07/1956"));
		indInfo.setLanguageCode("CO");
		indRequest.setIndividualInformations(indInfo);
		
		IndividualProfilV3 ip = new IndividualProfilV3();
		ip.setEmailOptin("T");
		ip.setLanguageCode("CR");
		ip.setChildrenNumber("2");
		ip.setCustomerSegment("SEG");
		
		indRequest.setIndividualProfil(ip);
		request.setIndividualRequest(indRequest);

		// Email address
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		email.setEmail("e.thuonghime@gmail.com");
		email.setMediumCode("D");
		email.setMediumStatus("V");
		// email.setVersion("1");
		email.setEmailOptin("N");
		emailRequest.setEmail(email);
		request.getEmailRequest().add(emailRequest);
		
		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("not collected");
		pac.setZipCode("99999");
		pac.setCity("not collected");
		pac.setCountryCode("BI");
		pac.setAdditionalInformation("not collected");
		// pac.setDistrict("");
		pac.setStateCode("ZZ");
		// pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);
		
		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("D");		// Direct
		pap.setMediumStatus("I");	// Invalid
		// pap.setVersion("1");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressProperties(pap);

		UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("ISI");
		ua.setAddressRoleCode("M");
		addRequest.setUsageAddress(ua);

		request.getPostalAddressRequest().add(addRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
		
		
		Profils profil = profilsRepository.findBySgin(gin);
		Assert.assertNotNull(profil);
		Assert.assertEquals("CR", profil.getScode_langue());
		Assert.assertEquals("T", profil.getSmailing_autorise());
		Assert.assertEquals("2", profil.getInb_enfants().toString());
		Assert.assertEquals("SEG", profil.getSsegment());
	}

	@Test
 	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Update_Traveler_CreateProfil() throws BusinessErrorBlocBusinessException, JrafDomainException{

		String gin = "910000001152";
		
		// Reset individual data and postal address
		// resetIndividu(gin, "T");
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(gin);
		indInfo.setCivility("MRS");
 		indInfo.setLastNameSC("CACERES");
		indInfo.setFirstNameSC("MANUELA");
		indInfo.setStatus("V");
		indInfo.setBirthDate(SicDateUtils.convertStringToDateDDMMYYYY("01/07/1944"));
		indInfo.setLanguageCode("CO");
		indRequest.setIndividualInformations(indInfo);
		
		IndividualProfilV3 ip = new IndividualProfilV3();
		ip.setEmailOptin("T");
		ip.setLanguageCode("CR");
		ip.setChildrenNumber("2");
		ip.setCustomerSegment("SEG");
		
		indRequest.setIndividualProfil(ip);
		request.setIndividualRequest(indRequest);

		// Email address
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		email.setEmail("omarcaceresarroyave@me.com");
		email.setMediumCode("D");
		email.setMediumStatus("V");
		// email.setVersion("1");
		email.setEmailOptin("N");
		emailRequest.setEmail(email);
		request.getEmailRequest().add(emailRequest);
		
		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("not collected");
		pac.setZipCode("99999");
		pac.setCity("not collected");
		pac.setCountryCode("BI");
		pac.setAdditionalInformation("not collected");
		// pac.setDistrict("");
		pac.setStateCode("ZZ");
		// pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);
		
		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("D");		// Direct
		pap.setMediumStatus("I");	// Invalid
		// pap.setVersion("1");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressProperties(pap);

		UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("ISI");
		ua.setAddressRoleCode("M");
		addRequest.setUsageAddress(ua);

		request.getPostalAddressRequest().add(addRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
		
		
		Profils profil = profilsRepository.findBySgin(gin);
		Assert.assertNotNull(profil);
		Assert.assertEquals("CR", profil.getScode_langue());
		Assert.assertEquals("T", profil.getSmailing_autorise());
		Assert.assertEquals("2", profil.getInb_enfants().toString());
		Assert.assertEquals("SEG", profil.getSsegment());
	}

	@Test
 	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Update_Social_CreateProfil() throws BusinessErrorBlocBusinessException, JrafDomainException{

		String gin = "920000001140";
		
		// Reset individual data and postal address
		// resetIndividu(gin, "T");
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(gin);
		indInfo.setCivility("M.");
 		indInfo.setLastNameSC("MOI");
		indInfo.setFirstNameSC("TOI");
		indInfo.setStatus("V");
		indInfo.setBirthDate(SicDateUtils.convertStringToDateDDMMYYYY("01/07/1965"));
		indInfo.setLanguageCode("CO");
		indRequest.setIndividualInformations(indInfo);
		
		IndividualProfilV3 ip = new IndividualProfilV3();
		ip.setEmailOptin("T");
		ip.setLanguageCode("CR");
		ip.setChildrenNumber("2");
		ip.setCustomerSegment("SEG");
		
		indRequest.setIndividualProfil(ip);
		request.setIndividualRequest(indRequest);

		// Email address
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		email.setEmail("nomail@me.com");
		email.setMediumCode("D");
		email.setMediumStatus("V");
		// email.setVersion("1");
		email.setEmailOptin("N");
		emailRequest.setEmail(email);
		request.getEmailRequest().add(emailRequest);
		
		// Postal Address
		PostalAddressRequest addRequest = new PostalAddressRequest();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("not collected");
		pac.setZipCode("99999");
		pac.setCity("not collected");
		pac.setCountryCode("BI");
		pac.setAdditionalInformation("not collected");
		// pac.setDistrict("");
		pac.setStateCode("ZZ");
		// pac.setCorporateName("");
		addRequest.setPostalAddressContent(pac);
		
		PostalAddressProperties pap = new PostalAddressProperties();
		pap.setMediumCode("D");		// Direct
		pap.setMediumStatus("I");	// Invalid
		// pap.setVersion("1");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressProperties(pap);

		UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("ISI");
		ua.setAddressRoleCode("M");
		addRequest.setUsageAddress(ua);

		request.getPostalAddressRequest().add(addRequest);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());
		
		
		Profils profil = profilsRepository.findBySgin(gin);
		Assert.assertNotNull(profil);
		Assert.assertEquals("CR", profil.getScode_langue());
		Assert.assertEquals("T", profil.getSmailing_autorise());
		Assert.assertEquals("2", profil.getInb_enfants().toString());
		Assert.assertEquals("SEG", profil.getSsegment());
	}
}
