package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.ComunicationPreferencesRequest;
import com.afklm.soa.stubs.w000442.v8.request.EmailRequest;
import com.afklm.soa.stubs.w000442.v8.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8.request.PostalAddressRequest;
import com.afklm.soa.stubs.w000442.v8.siccommonenum.LanguageCodesEnum;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.*;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.service.individu.internal.IndividuDS;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.Random;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplV8ForUTF8Test {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV8ForUTF8Test.class);

	private static final String CHANNEL = "B2C";
	private static final String CONTEXT = "B2C_HOME_PAGE";
	private static final String CODE_APP = "ISI";
	private static final String SIGNATURE = "UT_REPIND";
	private static final String SITE = "QVI";
	private static final String MATRICULE = "T412211";
	private static final String TOKEN = "WSSiC2";
	
	/** the Entity Manager*/
	@PersistenceContext(unitName="entityManagerFactoryRepind")
	private EntityManager entityManager;

	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v8Bean")
	private CreateOrUpdateIndividualImplV8 createOrUpdateIndividualImplV8;

	@Autowired
	private IndividuDS individuDS;
	
	@Before
	public void setUp() throws JrafDomainException {
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

	private String generateEmailForRussian() {
		// char[] chars = "كشقسجاكشيراكالسابق".toCharArray();
		// char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		char[] chars = "ФедрациРосйк".toCharArray();
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

	// Create Individual With UTF8
	@Test
	@Rollback(false)
	public void testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_Moroco() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_Moroco start... ");
		
		CreateUpdateIndividualRequest request = CreateNewProspectSimple("السابق", "جاك شيراك", generateEmail(), "إقامة سعادة");

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		
		// Individu verification
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	@Test
	@Rollback(false)
	public void testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_Russian() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_Russian start... ");
		
		CreateUpdateIndividualRequest request = CreateNewProspectSimple("Федерации", "Российской", generateEmail(), "декабря конференции");

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		
		// Individu verification
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	@Test
	@Rollback(false)
	public void testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_Taiwan() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_Taiwan start... ");
		
		CreateUpdateIndividualRequest request = CreateNewProspectSimple("国立台湾美術館", "一連のイベントを開催", generateEmail(), "3 Rue des nains");

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		
		// Individu verification
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	@Test
	@Rollback(false)
	public void testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_EmailRussian() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_French start... ");
		
		CreateUpdateIndividualRequest request = CreateNewProspectSimple("Jean", "Campa", generateEmailForRussian(), "7 Rue Mur");

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		
		// Individu verification
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	@Test
	@Rollback(false)
	public void testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_IndividualTaiwanEmailRussian() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_IndividualTaiwanEmailRussian start... ");
		
		CreateUpdateIndividualRequest request = CreateNewProspectSimple("国立台湾美術館", "一連のイベントを開催", generateEmailForRussian(), "14 Rue du Mur");

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		
		// Individu verification
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	@Test
	@Rollback(false)
	public void testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_AddressChinese() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_AddressChinese start... ");
		
		CreateUpdateIndividualRequest request = CreateNewProspectSimple("Jean", "Campa", generateEmail(), "台湾美");

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		
		// Individu verification
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	@Test
	@Rollback(false)
	public void testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_NoUTF8() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_NoUTF8 start... ");
		
		CreateUpdateIndividualRequest request = CreateNewProspectSimple("Jean", "Campa", generateEmail(), "50 Rue Bourgois");

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		
		// Individu verification
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}
	
	@Test
	@Rollback(false)
	public void testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_IndividualTaiwanEmailRussianPostalAddressMoroco() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_IndividualTaiwanEmailRussian start... ");
		
		CreateUpdateIndividualRequest request = CreateNewProspectSimple("国立台湾美術館", "一連のイベントを開催", generateEmailForRussian(), "السابقجاك شيراك");

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		
		// Individu verification
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	@Test
	@Rollback(false)
	public void testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_FullUTF8() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_IndividualTaiwanEmailRussian start... ");
		//                                                               NOM            PRENOM             EMAIL                      CORPO           NUM STREET  ADD INFO      CITY    ZIPCODE STATE DISTRICT   COUNTRY
		CreateUpdateIndividualRequest request = CreateNewProspectSimple("国立台湾美術館", "一連のイベントを開催", generateEmailForRussian(), "الأفريقية وسيحشد", "الرباط أكدال", "1 زنقة ابن حجر", "العنوان", "الهاتف", "عد", "اي", "MA");

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		
		// Individu verification
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	@Test
	@Rollback(false)
	public void testCreateOrUpdateIndividual_UTF8_UpdateProspectSimple_FullUTF8() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_IndividualTaiwanEmailRussian start... ");
		//                                                               NOM            PRENOM             EMAIL                      CORPO           NUM STREET  ADD INFO      CITY    ZIPCODE STATE DISTRICT   COUNTRY
		CreateUpdateIndividualRequest request = CreateNewProspectSimple("国立台湾美", "一連のイベントを", generateEmailForRussian(), "فريقية وسيحشد", "اباط أكدال", "1 ن ابن حجر", "انوان", "ااتف", "عد", "اي", "MA");

		// UPDATE
		request.getIndividualRequest().getIndividualInformations().setIdentifier("900047765351");
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		
		// Individu verification
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}


	@Test
	@Rollback(false)
	public void testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_FullHTML() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("testCreateOrUpdateIndividual_UTF8_CreateNewProspectSimple_IndividualTaiwanEmailRussian start... ");
		//                                                               NOM                     PRENOM             EMAIL                      CORPO           NUM STREET  ADD INFO      CITY    ZIPCODE STATE DISTRICT   COUNTRY
		CreateUpdateIndividualRequest request = CreateNewProspectSimple("HYEONGSEOK&amp;#8232;", "MICHA&#321;", generateEmail(), "Société MICHA&#321;", "Rue MICHA&#321;", "Etage MICHA&#321;", "Ville MICHA&#321;", "", "", "", "FR");

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		
		// Individu verification
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}
		
	
	// PRIVATE FUNCTION
	private CreateUpdateIndividualRequest CreateNewProspectSimple(String prenom, String nom, String email, String numberAndStreet) {
		return CreateNewProspectSimple(prenom, nom, email, null, numberAndStreet, null, null, null, null, null, null);
	}
	
	private CreateUpdateIndividualRequest CreateNewProspectSimple(String prenom, String nom, String email, String corporateName, String numberAndStreet, String additionalInformation, String city, String zipCode, String stateCode, String district, String countryCode) {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess("W");
		
		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setMatricule(MATRICULE);
		requestor.setContext(CONTEXT);
		requestor.setApplicationCode(CODE_APP);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setToken(TOKEN);
		
		// Email
		EmailRequest emailRequest = new EmailRequest();
		Email e = new Email();

		e.setEmail(email);
		e.setMediumCode("D");
		e.setMediumStatus("V");
		emailRequest.setEmail(e);

		// Postal address
		PostalAddressContent contentPostalAddress = new PostalAddressContent();
		contentPostalAddress.setCorporateName(corporateName);
		contentPostalAddress.setNumberAndStreet(numberAndStreet);
		contentPostalAddress.setAdditionalInformation(additionalInformation);		
		contentPostalAddress.setCity(city);
		contentPostalAddress.setZipCode(zipCode);
		contentPostalAddress.setStateCode(stateCode);
		contentPostalAddress.setDistrict(district);
		contentPostalAddress.setCountryCode(countryCode);
		
		PostalAddressProperties propertiesPostalAddress = new PostalAddressProperties();
		propertiesPostalAddress.setMediumCode("P");
		propertiesPostalAddress.setMediumStatus("V");
		propertiesPostalAddress.setIndicAdrNorm(true);		// No Normalization
		
		PostalAddressRequest postalAddressRequest = new PostalAddressRequest();
		postalAddressRequest.setPostalAddressContent(contentPostalAddress);
		postalAddressRequest.setPostalAddressProperties(propertiesPostalAddress);
				
		IndividualRequest individualRequest = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setFirstNameSC(prenom);
		indInfo.setLastNameSC(nom);
		Date date = new Date();
		indInfo.setBirthDate(date);
		indInfo.setNationality("FR");
		indInfo.setSecondNationality("IT");
		indInfo.setStatus("V");
		
		individualRequest.setIndividualInformations(indInfo);

		
		// Communication Preference
		ComunicationPreferencesRequest comPrefRequest = new ComunicationPreferencesRequest();
		CommunicationPreferences comPref = new CommunicationPreferences();
		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("AF");
		comPref.setOptIn("Y");
		comPref.setDateOfConsent(new Date());
		comPref.setSubscriptionChannel(CONTEXT);

		// Market Language
		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.fromValue("FR"));
		ml.setMarket("BR");
		ml.setOptIn("Y");
		ml.setDateOfConsent(new Date());

		comPref.getMarketLanguage().add(ml);
		comPrefRequest.setCommunicationPreferences(comPref);
		
		request.setRequestor(requestor);
		request.getEmailRequest().add(emailRequest);
		request.getPostalAddressRequest().add(postalAddressRequest);
		request.setIndividualRequest(individualRequest);
		request.getComunicationPreferencesRequest().add(comPrefRequest);


		return request;
	}
	
	
}
