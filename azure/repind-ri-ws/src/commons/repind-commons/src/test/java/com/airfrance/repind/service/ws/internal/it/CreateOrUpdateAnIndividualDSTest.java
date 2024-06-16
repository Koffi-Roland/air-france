package com.airfrance.repind.service.ws.internal.it;

import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafException;
import com.airfrance.ref.type.*;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.individu.MarketLanguageRepository;
import com.airfrance.repind.dao.profil.ProfilsRepository;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesTransform;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.profil.Profils;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.ws.internal.CreateOrUpdateIndividualDS;
import com.airfrance.repind.service.ws.internal.helpers.UltimateException;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(locations="classpath:/config/application-context-spring-test.xml")
@Transactional(transactionManager="transactionManagerRepind")
@Ignore
public class CreateOrUpdateAnIndividualDSTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualDSTest.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String SIGN_MODIF = "T730890";
	private static final String MAIL = "testloha@af.ri";
	private final String GIN_DELEGATOR = "400363296011";
	private final int TWO_TELECOMS 	= 2;
	private final int ONE_TELECOM = 1;
	private final int ONE_DELEGATION = 1;
	private final int ZERO_DELEGATION = 0;

	@Autowired
	private MarketLanguageRepository marketLanguageRepository;

	@Autowired
	private CommunicationPreferencesRepository communicationPreferencesRepository;

	@Autowired
	private IndividuRepository individuRepository;

	@Autowired
	private CommunicationPreferencesDS comPrefDS;
	
	@Autowired
	private ProfilsRepository profilsRepository;

	@Autowired
	private IndividuDS individuDS;

	@Autowired
	private CreateOrUpdateIndividualDS createOrUpdateDS;

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

	private String generatePhone() {

		return "04" + RandomStringUtils.randomNumeric(8);
	}

	@Test
	public void testCreateOrUpdateIndividual_simpleIndividu() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		CreateOrUpdateAnIndividualDSTest.logger.info("Test start...");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CreateOrUpdateAnIndividualDSTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualDSTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualDSTest.SIGNATURE);

		request.setRequestorDTO(requestor);

		IndividualRequestDTO indRequest = new IndividualRequestDTO();
		// indRequest.setCivilian("MR");

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("PHILIPPOT"); 
		indInfo.setFirstNameSC("PATRICK");
		indInfo.setMiddleNameSC("PAT");

		indRequest.setIndividualInformationsDTO(indInfo);

		request.setIndividualRequestDTO(indRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);
		
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());

		CreateOrUpdateAnIndividualDSTest.logger.info("GIN created : " + response.getGin());

		CreateOrUpdateAnIndividualDSTest.logger.info("Test stop.");
	}



	@Test
	public void testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KL() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CreateOrUpdateAnIndividualDSTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualDSTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualDSTest.SIGNATURE);

		request.setRequestorDTO(requestor);


		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("SOCIAL" + generateString());
		indInfo.setFirstNameSC("SOCIAL" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);

		// Social
		ExternalIdentifierRequestDTO eir = new ExternalIdentifierRequestDTO();
		ExternalIdentifierDTO ei = new ExternalIdentifierDTO();
		ei.setIdentifier("NumeroIDGIGYA_KL_1234567_" + generatePhone());
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifierDTO(ei);

		ExternalIdentifierDataDTO eid = new ExternalIdentifierDataDTO();
		eid.setKey("USED_BY_KL");
		eid.setValue("Y");
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

		Assert.assertTrue(response.getGin().startsWith("92"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());


		CreateOrUpdateAnIndividualDSTest.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KL " + response.getGin());
	}

	@Test
	public void testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_AF() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CreateOrUpdateAnIndividualDSTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualDSTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualDSTest.SIGNATURE);

		request.setRequestorDTO(requestor);


		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("SOCIAL" + generateString());
		indInfo.setFirstNameSC("SOCIAL" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);

		// Social
		ExternalIdentifierRequestDTO eir = new ExternalIdentifierRequestDTO();
		ExternalIdentifierDTO ei = new ExternalIdentifierDTO();
		ei.setIdentifier("NumeroIDGIGYA_AF_1234567_" + generatePhone());
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifierDTO(ei);

		ExternalIdentifierDataDTO eid = new ExternalIdentifierDataDTO();
		eid.setKey("USED_BY_AF");
		eid.setValue("Y");
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

		Assert.assertTrue(response.getGin().startsWith("92"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());


		CreateOrUpdateAnIndividualDSTest.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_AF " + response.getGin());
	}


	@Test
	public void testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KLM_AF() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException  {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CreateOrUpdateAnIndividualDSTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualDSTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualDSTest.SIGNATURE);

		request.setRequestorDTO(requestor);


		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("SOCIAL" + generateString());
		indInfo.setFirstNameSC("SOCIAL" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);

		// Social
		ExternalIdentifierRequestDTO eir = new ExternalIdentifierRequestDTO();
		ExternalIdentifierDTO ei = new ExternalIdentifierDTO();
		ei.setIdentifier("NumeroIDGIGYA_AFKL_1234567_" + generatePhone());
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifierDTO(ei);

		ExternalIdentifierDataDTO eid = new ExternalIdentifierDataDTO();
		eid.setKey("USED_BY_AF");
		eid.setValue("Y");
		eir.setExternalIdentifierDataDTO(new ArrayList<ExternalIdentifierDataDTO>());
		eir.getExternalIdentifierDataDTO().add(eid);

		ExternalIdentifierDataDTO eid_KL = new ExternalIdentifierDataDTO();
		eid_KL.setKey("USED_BY_KL");
		eid_KL.setValue("Y");
		eir.getExternalIdentifierDataDTO().add(eid_KL);

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

		Assert.assertTrue(response.getGin().startsWith("92"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());


		CreateOrUpdateAnIndividualDSTest.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KLM_AF " + response.getGin());
	}

	@Test
	public void testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KLM_AF_DIFF() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException  {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CreateOrUpdateAnIndividualDSTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualDSTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualDSTest.SIGNATURE);

		request.setRequestorDTO(requestor);


		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("SOCIAL" + generateString());
		indInfo.setFirstNameSC("SOCIAL" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);

		// Social
		ExternalIdentifierRequestDTO eir = new ExternalIdentifierRequestDTO();
		ExternalIdentifierDTO ei = new ExternalIdentifierDTO();
		ei.setIdentifier("NumeroIDGIGYA_AFKL_1234567_2_" + generatePhone());
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifierDTO(ei);

		ExternalIdentifierDataDTO eid = new ExternalIdentifierDataDTO();
		eid.setKey("USED_BY_AF");
		eid.setValue("Y");
		eir.setExternalIdentifierDataDTO(new ArrayList<ExternalIdentifierDataDTO>());
		eir.getExternalIdentifierDataDTO().add(eid);

		// Social 2		
		ExternalIdentifierRequestDTO eir2 = new ExternalIdentifierRequestDTO();
		ExternalIdentifierDTO ei2 = new ExternalIdentifierDTO();
		ei2.setIdentifier("NumeroIDGIGYA_AFKL_1234567_1_" + generatePhone());
		ei2.setType("GIGYA_ID");
		eir2.setExternalIdentifierDTO(ei2);

		ExternalIdentifierDataDTO eid_KL2 = new ExternalIdentifierDataDTO();
		eid_KL2.setKey("USED_BY_KL");
		eid_KL2.setValue("Y");
		eir2.setExternalIdentifierDataDTO(new ArrayList<ExternalIdentifierDataDTO>());
		eir2.getExternalIdentifierDataDTO().add(eid_KL2);

		request.setExternalIdentifierRequestDTO(new ArrayList<ExternalIdentifierRequestDTO>());
		request.getExternalIdentifierRequestDTO().add(eir2);

		// WS call
		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());

		Assert.assertTrue(response.getGin().startsWith("92"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());


		CreateOrUpdateAnIndividualDSTest.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KLM_AF_DIFF " + response.getGin());
	}

	@Test
	public void testCreateOrUpdateIndividual_ExternalIdentifier_FACEBOOK() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CreateOrUpdateAnIndividualDSTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualDSTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualDSTest.SIGNATURE);

		request.setRequestorDTO(requestor);

		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("SOCFB" + generateString());
		indInfo.setFirstNameSC("SOCFB" + generateString());
		indInfo.setMiddleNameSC("SOCFB" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);

		// Social
		ExternalIdentifierRequestDTO eir = new ExternalIdentifierRequestDTO();
		ExternalIdentifierDTO ei = new ExternalIdentifierDTO();
		ei.setIdentifier("NumeroIDFACEBOOK_123456789101112");
		ei.setType("FACEBOOK_ID");
		eir.setExternalIdentifierDTO(ei);
		
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

		Assert.assertTrue(response.getGin().startsWith("92"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());


		CreateOrUpdateAnIndividualDSTest.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_FACEBOOK " + response.getGin());
	}

	@Test
	public void testCreateOrUpdateIndividual_ExternalIdentifier_TWITTER() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CreateOrUpdateAnIndividualDSTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualDSTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualDSTest.SIGNATURE);

		request.setRequestorDTO(requestor);

		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("SOCTWI" + generateString());
		indInfo.setFirstNameSC("SOCTWI" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);

		// Social
		ExternalIdentifierRequestDTO eir = new ExternalIdentifierRequestDTO();
		ExternalIdentifierDTO ei = new ExternalIdentifierDTO();
		ei.setIdentifier("NumeroIDTWITTER_123456789101112");
		ei.setType("TWITTER_ID");
		eir.setExternalIdentifierDTO(ei);

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

		Assert.assertTrue(response.getGin().startsWith("92"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());


		CreateOrUpdateAnIndividualDSTest.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_TWITTER " + response.getGin());
	}

	@Test
	public void testCreateOrUpdateIndividual_ExternalIdentifier_GigyaOnly() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CreateOrUpdateAnIndividualDSTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualDSTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualDSTest.SIGNATURE);

		request.setRequestorDTO(requestor);

		// Social
		ExternalIdentifierRequestDTO eir = new ExternalIdentifierRequestDTO();
		ExternalIdentifierDTO ei = new ExternalIdentifierDTO();
		ei.setIdentifier("NumeroIDGIGYA_KL_" + generatePhone() + "_");
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifierDTO(ei);

		ExternalIdentifierDataDTO eid = new ExternalIdentifierDataDTO();
		eid.setKey("USED_BY_KL");
		eid.setValue("Y");
		ExternalIdentifierDataDTO eid2 = new ExternalIdentifierDataDTO();
		eid2.setKey("USED_BY_AF");
		eid2.setValue("N");
		eir.setExternalIdentifierDataDTO(new ArrayList<ExternalIdentifierDataDTO>());
		eir.getExternalIdentifierDataDTO().add(eid);
		eir.getExternalIdentifierDataDTO().add(eid2);

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

		Assert.assertTrue(response.getGin().startsWith("92"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());


		CreateOrUpdateAnIndividualDSTest.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_Gigya Only " + response.getGin());
	}
	// TODO : Mesurer l unicite du GIGYA sur ExternalIdentifierDTO ou plutot SocialNetwork

	@Test
	public void testCreateOrUpdateIndividual_Prospect_CreateNewProspect() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		CreateOrUpdateAnIndividualDSTest.logger.info("Test start... ");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect

		/*** Set parameters ***/
		// Requestor
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setContext("B2C_HOME_PAGE");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Email
		EmailRequestDTO emailRequest = new EmailRequestDTO();
		com.airfrance.repind.dto.ws.EmailDTO email = new com.airfrance.repind.dto.ws.EmailDTO();

		email.setEmail(CreateOrUpdateAnIndividualDSTest.MAIL);
		emailRequest.setEmailDTO(email);

		// Communication Preference
		CommunicationPreferencesRequestDTO comPrefrequest = new CommunicationPreferencesRequestDTO();
		com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("AF");
		comPref.setOptIn("N");
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			comPref.setDateOfConsent(df.parse("30-08-2016"));
		} catch (ParseException e) {
			CreateOrUpdateAnIndividualDSTest.logger.error("unable to set date of consent");
		}
		comPref.setSubscriptionChannel("B2C Home Page");

		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setLanguage("FR");
		ml.setMarket("FR");
		ml.setOptIn("N");
		try {
			ml.setDateOfConsent(df.parse("01-09-2016"));
		} catch (ParseException e) {
			CreateOrUpdateAnIndividualDSTest.logger.error("unable to set market language date of consent");
		}
		
		comPref.setMarketLanguageDTO(new ArrayList<MarketLanguageDTO>());
		comPref.getMarketLanguageDTO().add(ml);
		comPrefrequest.setCommunicationPreferencesDTO(comPref);

		request.setRequestorDTO(requestor);
		request.setEmailRequestDTO(new ArrayList<EmailRequestDTO>());
		request.getEmailRequestDTO().add(emailRequest);
		request.setCommunicationPreferencesRequestDTO(new ArrayList<CommunicationPreferencesRequestDTO>());
		request.getCommunicationPreferencesRequestDTO().add(comPrefrequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());

		CreateOrUpdateAnIndividualDSTest.logger.info("Gin updated: " + response.getGin());
		CreateOrUpdateAnIndividualDSTest.logger.info("End of test");
	}

	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testCreateOrUpdateIndividual_Prospect_UpdateExistingIndividual() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		CreateOrUpdateAnIndividualDSTest.logger.info("Test start... ");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect

		/*** Set parameters ***/
		// Requestor
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setContext("B2C_HOME_PAGE");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");
		requestor.setLoggedGin("400424668522");

		// Email
		EmailRequestDTO emailRequest = new EmailRequestDTO();
		com.airfrance.repind.dto.ws.EmailDTO email = new com.airfrance.repind.dto.ws.EmailDTO();

		email.setEmail("t.loharano@gmail.com");
		emailRequest.setEmailDTO(email);

		// Communication Preference
		CommunicationPreferencesRequestDTO comPrefrequest = new CommunicationPreferencesRequestDTO();
		com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("AF");
		comPref.setOptIn("N");
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			comPref.setDateOfConsent(df.parse("30-08-2016"));
		} catch (ParseException e) {
			CreateOrUpdateAnIndividualDSTest.logger.error("unable to set date of consent");
		}
		comPref.setSubscriptionChannel("B2C Home Page");

		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setLanguage("FR");
		ml.setMarket("FR");
		ml.setOptIn("N");
		try {
			ml.setDateOfConsent(df.parse("01-09-2016"));
		} catch (ParseException e) {
			CreateOrUpdateAnIndividualDSTest.logger.error("unable to set market language date of consent");
		}
		
		comPref.setMarketLanguageDTO(new ArrayList<MarketLanguageDTO>());
		comPref.getMarketLanguageDTO().add(ml);
		comPrefrequest.setCommunicationPreferencesDTO(comPref);

		request.setRequestorDTO(requestor);
		request.setEmailRequestDTO(new ArrayList<EmailRequestDTO>());
		request.getEmailRequestDTO().add(emailRequest);
		request.setCommunicationPreferencesRequestDTO(new ArrayList<CommunicationPreferencesRequestDTO>());
		request.getCommunicationPreferencesRequestDTO().add(comPrefrequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);
		

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());

		CreateOrUpdateAnIndividualDSTest.logger.info("Gin updated: " + response.getGin());
		CreateOrUpdateAnIndividualDSTest.logger.info("End of test");
	}

	@Test
	public void testCreateOrUpdateIndividual_ExternalIdentifier_GigyaOnlyUsingTheSameKey() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CreateOrUpdateAnIndividualDSTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualDSTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualDSTest.SIGNATURE);

		request.setRequestorDTO(requestor);

		// Social
		ExternalIdentifierRequestDTO eir = new ExternalIdentifierRequestDTO();
		ExternalIdentifierDTO ei = new ExternalIdentifierDTO();
		ei.setIdentifier("NumeroIDGIGYA_KL_12345478_" + generateString());
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifierDTO(ei);

		ExternalIdentifierDataDTO eid = new ExternalIdentifierDataDTO();
		eid.setKey("USED_BY_KL");
		eid.setValue("Y");
		ExternalIdentifierDataDTO eid2 = new ExternalIdentifierDataDTO();
		eid2.setKey("USED_BY_AF");
		eid2.setValue("N");
		eir.setExternalIdentifierDataDTO(new ArrayList<ExternalIdentifierDataDTO>());
		eir.getExternalIdentifierDataDTO().add(eid);
		eir.getExternalIdentifierDataDTO().add(eid2);

		request.setExternalIdentifierRequestDTO(new ArrayList<ExternalIdentifierRequestDTO>());
		request.getExternalIdentifierRequestDTO().add(eir);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("92"));
		// Tests
	}

	@Test
	public void testCreateOrUpdateIndividual_UpdateExternalIdentifier_On_ExistingIndividual() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CreateOrUpdateAnIndividualDSTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualDSTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualDSTest.SIGNATURE);

		request.setRequestorDTO(requestor);

		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("SOCIAL" + generateString());
		indInfo.setFirstNameSC("SOCIAL" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);

		// Social
		ExternalIdentifierRequestDTO eir = new ExternalIdentifierRequestDTO();
		ExternalIdentifierDTO ei = new ExternalIdentifierDTO();
		ei.setIdentifier("NumeroIDGIGYA_AF_1234567_" + generatePhone());
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifierDTO(ei);

		ExternalIdentifierDataDTO eid = new ExternalIdentifierDataDTO();
		eid.setKey("USED_BY_AF");
		eid.setValue("Y");
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

		Assert.assertTrue(response.getGin().startsWith("92"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());


		CreateOrUpdateAnIndividualDSTest.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_AF " + response.getGin());
	}

	@Test
	@Ignore // select * from sic2.REF_PREFERENCE_VALUE; select * from sic2.REF_PREFERENCE_KEY;
	public void testCreateOrUpdateIndividual_Preferences_UpdateExistingIndividual() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		CreateOrUpdateAnIndividualDSTest.logger.info("Test start... ");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		/*** Set parameters ***/
		// Requestor
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");
		request.setRequestorDTO(requestor);

		// Individual to update 
		IndividualRequestDTO reqInd = new IndividualRequestDTO();
		IndividualInformationsDTO indInf = new IndividualInformationsDTO();
		indInf.setIdentifier("400491922886");
		reqInd.setIndividualInformationsDTO(indInf);
		request.setIndividualRequestDTO(reqInd);

		// Preferences
		PreferenceDTO preference = new PreferenceDTO();
		PreferenceDataDTO prefData = new PreferenceDataDTO();
		prefData.setKey("ML");
		prefData.setValue("LSML");
		preference.setType("B2C");
		preference.getPreferencesDatasDTO().getPreferenceDataDTO().add(prefData);

		// Update
		//		PreferenceDataRequest prefDataReq = new PreferenceDataRequest();
		//		prefDataReq.getPreference().add(preference);
		//		request.setPreferenceDataRequest(prefDataReq);

		// WS call
		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		// Tests
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());

		CreateOrUpdateAnIndividualDSTest.logger.info("End of test. ");
	}

	@Test
	@Ignore
	public void testCreateOrUpdateIndividual_AlertDTO_UpdateProspectWithGIN() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateOrUpdateAnIndividualDSTest.logger.info("Test start... ");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		request.setProcess(ProcessEnum.A.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Individual to update 
		IndividualRequestDTO reqInd = new IndividualRequestDTO();
		IndividualInformationsDTO indInf = new IndividualInformationsDTO();
		indInf.setIdentifier("900025059357");
		reqInd.setIndividualInformationsDTO(indInf);

		// Communication Preference
		CommunicationPreferencesRequestDTO comPrefrequest = new CommunicationPreferencesRequestDTO();
		com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("AF");
		comPref.setOptIn("N");
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			comPref.setDateOfConsent(df.parse("30-08-2016"));
		} catch (ParseException e) {
			CreateOrUpdateAnIndividualDSTest.logger.error("unable to set date of consent");
		}
		comPref.setSubscriptionChannel("B2C Home Page");

		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setLanguage("FR");
		ml.setMarket("FR");
		ml.setOptIn("N");
		try {
			ml.setDateOfConsent(df.parse("01-09-2016"));
		} catch (ParseException e) {
			CreateOrUpdateAnIndividualDSTest.logger.error("unable to set market language date of consent");
		}

		comPref.getMarketLanguageDTO().add(ml);
		comPrefrequest.setCommunicationPreferencesDTO(comPref);

		// AlertDTO
		AlertRequestDTO alertRequest = new AlertRequestDTO();
		AlertDTO alert = new AlertDTO();
		alert.setType("P");
		alert.setOptin("N");
		/* origin */
		AlertDataDTO orig = new AlertDataDTO();
		orig.setKey("ORIGIN");
		orig.setValue("CDG");
		alert.getAlertDataDTO().add(orig);
		/* origin type */
		AlertDataDTO origType = new AlertDataDTO();
		origType.setKey("ORIGIN_TYPE");
		origType.setValue("A");
		alert.getAlertDataDTO().add(origType);
		/* destination */
		AlertDataDTO dest = new AlertDataDTO();
		dest.setKey("DESTINATION");
		dest.setValue("NCE");
		alert.getAlertDataDTO().add(dest);
		/* destination type */
		AlertDataDTO destType = new AlertDataDTO();
		destType.setKey("DESTINATION_TYPE");
		destType.setValue("C");
		alert.getAlertDataDTO().add(destType);
		/* start date */
		AlertDataDTO sDate = new AlertDataDTO();
		sDate.setKey("START_DATE");
		sDate.setValue("01102016");
		alert.getAlertDataDTO().add(sDate);
		/* end date */
		AlertDataDTO eDate = new AlertDataDTO();
		eDate.setKey("END_DATE");
		eDate.setValue("31102016");
		alert.getAlertDataDTO().add(eDate);
		/* cabin */
		AlertDataDTO cabin = new AlertDataDTO();
		cabin.setKey("CABIN");
		cabin.setValue("P");
		alert.getAlertDataDTO().add(cabin);

		alertRequest.getAlertDTO().add(alert);

		request.setIndividualRequestDTO(reqInd);
		request.setRequestorDTO(requestor);
		request.getCommunicationPreferencesRequestDTO().add(comPrefrequest);
		request.setAlertRequestDTO(alertRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		// Tests
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());

		CreateOrUpdateAnIndividualDSTest.logger.info("End of test. ");
	}

	@Test
	@Ignore
	public void testCreateOrUpdateIndividual_AlertDTO_CreatProspectWithMail() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateOrUpdateAnIndividualDSTest.logger.info("Test start... ");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		request.setProcess(ProcessEnum.A.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");


		// Email
		EmailRequestDTO emailReq = new EmailRequestDTO();
		com.airfrance.repind.dto.ws.EmailDTO email = new com.airfrance.repind.dto.ws.EmailDTO();
		String mail = generateEmail();
		//		Random rd = new Random();
		//		int randomInt = rd.nextInt(1000);

		//		mail.append("tualert");
		//		mail.append(Integer.toString(randomInt));
		//		mail.append("@yopmail.com");
		email.setEmail(mail);
		emailReq.setEmailDTO(email);

		// Communication Preference
		CommunicationPreferencesRequestDTO comPrefrequest = new CommunicationPreferencesRequestDTO();
		com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("AF");
		comPref.setOptIn("N");
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			comPref.setDateOfConsent(df.parse("30-08-2016"));
		} catch (ParseException e) {
			CreateOrUpdateAnIndividualDSTest.logger.error("unable to set date of consent");
		}
		comPref.setSubscriptionChannel("B2C Home Page");

		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setLanguage("FR");
		ml.setMarket("FR");
		ml.setOptIn("N");
		try {
			ml.setDateOfConsent(df.parse("01-09-2016"));
		} catch (ParseException e) {
			CreateOrUpdateAnIndividualDSTest.logger.error("unable to set market language date of consent");
		}

		comPref.getMarketLanguageDTO().add(ml);
		comPrefrequest.setCommunicationPreferencesDTO(comPref);

		// AlertDTO
		AlertRequestDTO alertRequest = new AlertRequestDTO();
		AlertDTO alert = new AlertDTO();
		alert.setType("P");
		alert.setOptin("N");
		/* origin */
		AlertDataDTO orig = new AlertDataDTO();
		orig.setKey("ORIGIN");
		orig.setValue("PAR");
		alert.getAlertDataDTO().add(orig);
		/* origin type */
		AlertDataDTO origType = new AlertDataDTO();
		origType.setKey("ORIGIN_TYPE");
		origType.setValue("C");
		alert.getAlertDataDTO().add(origType);
		/* destination */
		AlertDataDTO dest = new AlertDataDTO();
		dest.setKey("DESTINATION");
		dest.setValue("LAX");
		alert.getAlertDataDTO().add(dest);
		/* destination type */
		AlertDataDTO destType = new AlertDataDTO();
		destType.setKey("DESTINATION_TYPE");
		destType.setValue("A");
		alert.getAlertDataDTO().add(destType);
		/* start date */
		AlertDataDTO sDate = new AlertDataDTO();
		sDate.setKey("START_DATE");
		sDate.setValue("01012016");
		alert.getAlertDataDTO().add(sDate);
		/* end date */
		AlertDataDTO eDate = new AlertDataDTO();
		eDate.setKey("END_DATE");
		eDate.setValue("31122016");
		alert.getAlertDataDTO().add(eDate);
		/* cabin */
		AlertDataDTO cabin = new AlertDataDTO();
		cabin.setKey("CABIN");
		cabin.setValue("W");
		alert.getAlertDataDTO().add(cabin);

		alertRequest.getAlertDTO().add(alert);

		request.getEmailRequestDTO().add(emailReq);
		request.setRequestorDTO(requestor);
		request.getCommunicationPreferencesRequestDTO().add(comPrefrequest);
		request.setAlertRequestDTO(alertRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		// Tests
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("90"));

		CreateOrUpdateAnIndividualDSTest.logger.info("End of test. ");
	}

	// BUG SURCOUF
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testCreateOrUpdateIndividual_SurcoufBUG() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		CreateOrUpdateAnIndividualDSTest.logger.info("Test start...");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setStatus("V");

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("SURCW");
		requestor.setToken("WSSiC2");
		requestor.setApplicationCode("MAC");
		requestor.setSite("QVI");
		requestor.setSignature("SURCOUFWEB");

		request.setRequestorDTO(requestor);

		IndividualRequestDTO indRequest = new IndividualRequestDTO();
		// indRequest.setCivilian("MR");

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setBirthDate(new Date());
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("Konijnenbelt"); 
		indInfo.setFirstNameSC("W");

		indRequest.setIndividualInformationsDTO(indInfo);		

		request.setIndividualRequestDTO(indRequest);


		// Email
		EmailRequestDTO emailRequest = new EmailRequestDTO();
		com.airfrance.repind.dto.ws.EmailDTO email = new com.airfrance.repind.dto.ws.EmailDTO();
		email.setMediumCode(MediumCodeEnum.HOME.toString());
		email.setMediumStatus("V");
		email.setEmail("MAIL@KONIJNENBELTWETGEVING.NL");
		emailRequest.setEmailDTO(email);
		request.setEmailRequestDTO(new ArrayList<EmailRequestDTO>());
		request.getEmailRequestDTO().add(emailRequest);

		// Postal Address
		PostalAddressRequestDTO addRequest = new PostalAddressRequestDTO();
		PostalAddressContentDTO pac = new PostalAddressContentDTO();
		pac.setNumberAndStreet("Baronielaan 229");
		pac.setZipCode("4835JK");
		pac.setCity("Breda");
		pac.setCountryCode("NL");
		pac.setAdditionalInformation("");
		pac.setDistrict("");
		pac.setStateCode("");
		pac.setCorporateName("");
		addRequest.setPostalAddressContentDTO(pac);

		PostalAddressPropertiesDTO pap = new PostalAddressPropertiesDTO();
		pap.setMediumCode(MediumCodeEnum.BUSINESS.toString());
		pap.setMediumStatus("V");
		// pap.setVersion("1");
		pap.setIndicAdrNorm(true);	// On force l'adresse
		addRequest.setPostalAddressPropertiesDTO(pap);

		/*UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("TU");
		addRequest.setUsageAddress(ua);
		 */		
		request.setPostalAddressRequestDTO(new ArrayList<PostalAddressRequestDTO>());
		request.getPostalAddressRequestDTO().add(addRequest);

		// Telecom
		TelecomRequestDTO telRequest = new TelecomRequestDTO();
		TelecomDTO telecom = new TelecomDTO();
		telecom.setCountryCode("+31");
		telecom.setMediumCode(MediumCodeEnum.HOME.toString());
		telecom.setMediumStatus("V");
		telecom.setTerminalType(TerminalTypeEnum.MOBILE.toString());
		telecom.setPhoneNumber("0768882841");
		telRequest.setTelecomDTO(telecom);
		request.setTelecomRequestDTO(new ArrayList<TelecomRequestDTO>());
		request.getTelecomRequestDTO().add(telRequest);


		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);
		
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());

		CreateOrUpdateAnIndividualDSTest.logger.info("GIN created : " + response.getGin());

		CreateOrUpdateAnIndividualDSTest.logger.info("Test stop.");
	}

	// Tester la mise à jour d'une commPref d'un individu en conservant la date d'optin initiale 
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_UpdateIndivGinExistingComPref() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		CreateOrUpdateAnIndividualDSTest.logger.info("Test start...");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		// Init data
		// Date = 01 Janvier 2010
		Calendar initDate = new GregorianCalendar(2010,	01, 01);

		Individu testIndividu = individuRepository.findBySgin("400424668522");
		com.airfrance.repind.entity.individu.CommunicationPreferences comPrefInit = new com.airfrance.repind.entity.individu.CommunicationPreferences();
		com.airfrance.repind.entity.individu.MarketLanguage ml = new com.airfrance.repind.entity.individu.MarketLanguage();

		comPrefInit.setDomain("S");
		comPrefInit.setComGroupType("N");
		comPrefInit.setComType("ZZ");
		comPrefInit.setSubscribe("Y");
		comPrefInit.setDateOptin(initDate.getTime());

		ml.setDateOfConsent(new Date());
		ml.setLanguage("FR");
		ml.setMarket("FR");
		ml.setOptIn("Y");
		ml.setCreationSignature("Test U");
		ml.setCreationSite(CreateOrUpdateAnIndividualDSTest.SITE);

		Set<com.airfrance.repind.entity.individu.MarketLanguage> listMl = new HashSet<>();
		listMl.add(ml);
		comPrefInit.setMarketLanguage(listMl);

		testIndividu.getCommunicationpreferences().add(comPrefInit);

		marketLanguageRepository.saveAndFlush(ml);

		// Individual
		IndividualRequestDTO indReq = new IndividualRequestDTO();
		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();

		indInfo.setIdentifier("400424668522");
		indReq.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indReq);

		// Requestor
		RequestorDTO req = new RequestorDTO();

		req.setChannel("B2C");
		req.setContext("FB_ENROLMENT");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U");
		request.setRequestorDTO(req);

		// Communication preferences
		CommunicationPreferencesRequestDTO comPrefReq = new CommunicationPreferencesRequestDTO();
		com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();

		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("ZZ");
		comPref.setOptIn("Y");
		comPref.setDateOfConsent(new Date());
		comPrefReq.setCommunicationPreferencesDTO(comPref);
		request.setCommunicationPreferencesRequestDTO(new ArrayList<CommunicationPreferencesRequestDTO>());
		request.getCommunicationPreferencesRequestDTO().add(comPrefReq);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		// Retrieve updated datas
		CommunicationPreferencesDTO comPrefDto =  new CommunicationPreferencesDTO();
		comPrefDto.setGin("400424668522");
		comPrefDto.setDomain("S");
		comPrefDto.setComGroupType("N");
		comPrefDto.setComType("ZZ");
		List<CommunicationPreferences> listFound = communicationPreferencesRepository.findAll(
				Example.of(CommunicationPreferencesTransform.dto2BoLight(comPrefDto))
				);
		CommunicationPreferences foundCP = listFound.get(0);

		// Test results
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getSuccess());
		Assert.assertEquals(1, listFound.size());
		Assert.assertEquals("ZZ", foundCP.getComType());
		Assert.assertEquals(initDate.getTime(), foundCP.getDateOptin());

	}

	// Tester la mise à jour d'une commPref d'un prospet sans conservation de la date d'optin initiale 
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_UpdateProspectGinExistingComPref() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		CreateOrUpdateAnIndividualDSTest.logger.info("Test start...");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		// Init data
		// Date = 01 Janvier 2010
		Calendar initDate = new GregorianCalendar(2010,	01, 01);

		Individu testIndividu = individuRepository.findBySgin("900025086459");
		com.airfrance.repind.entity.individu.CommunicationPreferences comPrefInit = new com.airfrance.repind.entity.individu.CommunicationPreferences();
		com.airfrance.repind.entity.individu.MarketLanguage ml = new com.airfrance.repind.entity.individu.MarketLanguage();

		comPrefInit.setDomain("S");
		comPrefInit.setComGroupType("N");
		comPrefInit.setComType("KL");
		comPrefInit.setSubscribe("Y");
		comPrefInit.setDateOptin(initDate.getTime());

		ml.setDateOfConsent(new Date());
		ml.setLanguage("FR");
		ml.setMarket("FR");
		ml.setOptIn("Y");
		ml.setCreationSignature("Test U");
		ml.setCreationSite(CreateOrUpdateAnIndividualDSTest.SITE);

		Set<com.airfrance.repind.entity.individu.MarketLanguage> listMl = new HashSet<>();
		listMl.add(ml);
		comPrefInit.setMarketLanguage(listMl);

		testIndividu.getCommunicationpreferences().add(comPrefInit);

		marketLanguageRepository.saveAndFlush(ml);

		// Individual
		IndividualRequestDTO indReq = new IndividualRequestDTO();
		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();

		indInfo.setIdentifier("900025086459");
		indReq.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indReq);

		// Requestor
		RequestorDTO req = new RequestorDTO();

		req.setChannel("B2C");
		req.setContext("B2C_HOME_PAGE");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U");
		request.setRequestorDTO(req);

		// Communication preferences
		CommunicationPreferencesRequestDTO comPrefReq = new CommunicationPreferencesRequestDTO();
		com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();

		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("KL");
		comPref.setOptIn("N");
		comPref.setDateOfConsent(new Date());
		comPref.setSubscriptionChannel("B2C");

		MarketLanguageDTO marketLanguage = new MarketLanguageDTO();
		marketLanguage.setDateOfConsent(new Date());
		marketLanguage.setLanguage("FR");
		marketLanguage.setMarket("NL");
		marketLanguage.setOptIn("N");
		
		comPref.setMarketLanguageDTO(new ArrayList<MarketLanguageDTO>());
		comPref.getMarketLanguageDTO().add(marketLanguage);
		comPrefReq.setCommunicationPreferencesDTO(comPref);
		request.setCommunicationPreferencesRequestDTO(new ArrayList<CommunicationPreferencesRequestDTO>());
		request.getCommunicationPreferencesRequestDTO().add(comPrefReq);

		// Process W for prospect
		request.setProcess("W");

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);
		

		// Retrieve updated datas
		CommunicationPreferencesDTO comPrefDto =  new CommunicationPreferencesDTO();
		comPrefDto.setGin("900025086459");
		comPrefDto.setDomain("S");
		comPrefDto.setComGroupType("N");
		comPrefDto.setComType("KL");
		List<CommunicationPreferences> listFound = communicationPreferencesRepository.findAll(
				Example.of(CommunicationPreferencesTransform.dto2BoLight(comPrefDto))
				);
		CommunicationPreferences foundCP = listFound.get(0);

		// Test results
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getSuccess());

		// Il y a deux ComPref aucune d elle n'a ete mise a jour...
		for (CommunicationPreferences cp : listFound) {
			Assert.assertEquals("S", cp.getDomain());
			Assert.assertEquals("N", cp.getComGroupType());
			Assert.assertEquals("KL", cp.getComType());				
			Assert.assertNotEquals(initDate.getTime(), cp.getDateOptin());
		}		
	}

	// Tester la mise à jour d'une commPref d'un prospet en conservant la date d'optin initiale 
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_UpdateProspectEmailExistingComPref() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		CreateOrUpdateAnIndividualDSTest.logger.info("Test start...");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		// Init data
		// Date = 01 Janvier 2010
		Calendar initDate = new GregorianCalendar(2010,	01, 01);

		Individu testIndividu = individuRepository.findBySgin("900025086459");
		com.airfrance.repind.entity.individu.CommunicationPreferences comPrefInit = new com.airfrance.repind.entity.individu.CommunicationPreferences();
		com.airfrance.repind.entity.individu.MarketLanguage ml = new com.airfrance.repind.entity.individu.MarketLanguage();

		comPrefInit.setDomain("S");
		comPrefInit.setComGroupType("N");
		comPrefInit.setComType("KL");
		comPrefInit.setSubscribe("Y");
		comPrefInit.setDateOptin(initDate.getTime());

		ml.setDateOfConsent(new Date());
		ml.setLanguage("FR");
		ml.setMarket("FR");
		ml.setOptIn("Y");
		ml.setCreationSignature("Test U");
		ml.setCreationSite(CreateOrUpdateAnIndividualDSTest.SITE);

		Set<com.airfrance.repind.entity.individu.MarketLanguage> listMl = new HashSet<>();
		listMl.add(ml);
		comPrefInit.setMarketLanguage(listMl);

		testIndividu.getCommunicationpreferences().add(comPrefInit);

		marketLanguageRepository.saveAndFlush(ml);

		// Individual
		EmailRequestDTO emailReq = new EmailRequestDTO();
		com.airfrance.repind.dto.ws.EmailDTO email = new com.airfrance.repind.dto.ws.EmailDTO();

		email.setEmail("t_loharano@msn.com");
		emailReq.setEmailDTO(email);
		request.setEmailRequestDTO(new ArrayList<EmailRequestDTO>());
		request.getEmailRequestDTO().add(emailReq);

		// Requestor
		RequestorDTO req = new RequestorDTO();

		req.setChannel("B2C");
		req.setContext("B2C_HOME_PAGE");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U");
		request.setRequestorDTO(req);

		// Communication preferences
		CommunicationPreferencesRequestDTO comPrefReq = new CommunicationPreferencesRequestDTO();
		com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();

		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("KL");
		comPref.setOptIn("Y");
		comPref.setDateOfConsent(new Date());
		comPref.setSubscriptionChannel("B2C");

		com.airfrance.repind.dto.ws.MarketLanguageDTO marketLanguage = new com.airfrance.repind.dto.ws.MarketLanguageDTO();
		marketLanguage.setDateOfConsent(new Date());
		marketLanguage.setLanguage("FR");
		marketLanguage.setMarket("NL");
		marketLanguage.setOptIn("Y");
		
		comPref.setMarketLanguageDTO(new ArrayList<MarketLanguageDTO>());
		comPref.getMarketLanguageDTO().add(marketLanguage);
		comPrefReq.setCommunicationPreferencesDTO(comPref);
		request.setCommunicationPreferencesRequestDTO(new ArrayList<CommunicationPreferencesRequestDTO>());
		request.getCommunicationPreferencesRequestDTO().add(comPrefReq);

		// Process W for prospect
		request.setProcess("W");
		
		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		// Retrieve updated datas
		CommunicationPreferencesDTO comPrefDto =  new CommunicationPreferencesDTO();
		comPrefDto.setGin("900025086459");
		comPrefDto.setDomain("S");
		comPrefDto.setComGroupType("N");
		comPrefDto.setComType("KL");
		List<CommunicationPreferences> listFound = communicationPreferencesRepository.findAll(
				Example.of(CommunicationPreferencesTransform.dto2BoLight(comPrefDto))
				);
		// CommunicationPreferencesDTO foundCP = listFound.get(0);

		// Test results
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getSuccess());

		// Il y a deux ComPref on cherche celle que l on vient de mettre a jour...
		for (CommunicationPreferences cp : listFound) {
			if ("B2C".equals(cp.getChannel())) {
				Assert.assertEquals("S", cp.getDomain());
				Assert.assertEquals("N", cp.getComGroupType());
				Assert.assertEquals("KL", cp.getComType());
			} else {
				Assert.assertEquals("S", cp.getDomain());
				Assert.assertEquals("N", cp.getComGroupType());
				Assert.assertEquals("KL", cp.getComType());				
				Assert.assertEquals(initDate.getTime(), cp.getDateOptin());
			}
		}

		//		Assert.assertEquals("KL", foundCP.getComType());
		//		Assert.assertEquals(initDate.getTime(), foundCP.getDateOptin());

	}

	// Add a travel doc to a customer
	@Test(expected = Test.None.class /* no exception expected */)
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddTravelDoc() throws JrafDomainException {

		CreateOrUpdateAnIndividualDSTest.logger.info("Test start...");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		// Individual
		IndividualRequestDTO indReq = new IndividualRequestDTO();
		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();

		indInfo.setIdentifier("400424668522");
		indReq.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indReq);

		// Requestor
		RequestorDTO req = new RequestorDTO();

		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestorDTO(req);

		// PreferenceDTO = TravelDoc
		PreferenceRequestDTO preferenceRequest = new PreferenceRequestDTO();
		request.setPreferenceRequestDTO(preferenceRequest);

		PreferenceDTO preference = new PreferenceDTO();
		preferenceRequest.setPreferenceDTO(new ArrayList<PreferenceDTO>());
		preferenceRequest.getPreferenceDTO().add(preference);
		preference.setType("TDC");

		PreferenceDatasDTO preferenceDatas = new PreferenceDatasDTO();
		preference.setPreferencesDatasDTO(preferenceDatas);

		// key = type
		PreferenceDataDTO typeTdc = new PreferenceDataDTO();
		preferenceDatas.setPreferenceDataDTO(new ArrayList<PreferenceDataDTO>());
		preferenceDatas.getPreferenceDataDTO().add(typeTdc);
		typeTdc.setKey("type");
		typeTdc.setValue("PA");

		// key = number
		PreferenceDataDTO number = new PreferenceDataDTO();
		preferenceDatas.getPreferenceDataDTO().add(number);
		number.setKey("number");
		number.setValue("01AA00011122");

		// key = expirationDate
		PreferenceDataDTO expDate = new PreferenceDataDTO();
		preferenceDatas.getPreferenceDataDTO().add(expDate);
		expDate.setKey("expirationDate");
		expDate.setValue("31/12/2039");

		// key = countryIssued
		PreferenceDataDTO country = new PreferenceDataDTO();
		preferenceDatas.getPreferenceDataDTO().add(country);
		country.setKey("countryOfIssue");
		country.setValue("PR");

		// key = touchPoint
		PreferenceDataDTO touch = new PreferenceDataDTO();
		preferenceDatas.getPreferenceDataDTO().add(touch);
		touch.setKey("touchPoint");
		touch.setValue("ISI");

	}

	/**
	 * CONTEXT UM : KIDSOLO
	 * REPIND-895
	 */
	@Test
	public void testTelecomKeyEnum(){
		TelecomKeyEnum telecomKeyEnum = TelecomKeyEnum.fromString("terminalType");
		Assert.assertNotNull(telecomKeyEnum);
		Assert.assertEquals(telecomKeyEnum.name(), "TERMINAL_TYPE");

		telecomKeyEnum = TelecomKeyEnum.fromString("countryCode");
		Assert.assertNotNull(telecomKeyEnum);
		Assert.assertEquals(telecomKeyEnum.name(), "COUNTRY_CODE");

		telecomKeyEnum = TelecomKeyEnum.fromString("phoneNumber");
		Assert.assertNotNull(telecomKeyEnum);
		Assert.assertEquals(telecomKeyEnum.name(), "PHONE_NUMBER");
	}

	/**
	 * CONTEXT UN : KIDSOLO
	 * REPIND-895
	 */
	@Test
	public void testPostalAddressKeyEnum(){
		PostalAddressKeyEnum postalAddressKeyEnum = PostalAddressKeyEnum.fromString("numberAndStreet");
		Assert.assertNotNull(postalAddressKeyEnum);
		Assert.assertEquals(postalAddressKeyEnum.name(), "NUMBER_AND_STREET");

		postalAddressKeyEnum = PostalAddressKeyEnum.fromString("additionalInformation");
		Assert.assertNotNull(postalAddressKeyEnum);
		Assert.assertEquals(postalAddressKeyEnum.name(), "ADDITIONAL_INFORMATION");

		postalAddressKeyEnum = PostalAddressKeyEnum.fromString("city");
		Assert.assertNotNull(postalAddressKeyEnum);
		Assert.assertEquals(postalAddressKeyEnum.name(), "CITY");

		postalAddressKeyEnum = PostalAddressKeyEnum.fromString("district");
		Assert.assertNotNull(postalAddressKeyEnum);
		Assert.assertEquals(postalAddressKeyEnum.name(), "DISTRICT");

		postalAddressKeyEnum = PostalAddressKeyEnum.fromString("zipCode");
		Assert.assertNotNull(postalAddressKeyEnum);
		Assert.assertEquals(postalAddressKeyEnum.name(), "ZIPCODE");

		postalAddressKeyEnum = PostalAddressKeyEnum.fromString("state");
		Assert.assertNotNull(postalAddressKeyEnum);
		Assert.assertEquals(postalAddressKeyEnum.name(), "STATE");

		postalAddressKeyEnum = PostalAddressKeyEnum.fromString("country");
		Assert.assertNotNull(postalAddressKeyEnum);
		Assert.assertEquals(postalAddressKeyEnum.name(), "COUNTRY");
	}

	/**
	 * CONTEXT UM : KIDSOLO
	 * REPIND-895
	 * @throws ParseException 
	 * @throws UtfException 
	 * @throws UltimateException 
	 * @throws JrafApplicativeException 
	 * @throws JrafDomainException 
	 */
	private CreateModifyIndividualResponseDTO createDelegation(String gin, int scenary) throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException{
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		//BALISE REQUESTOR
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestor.setApplicationCode("W000442v8");
		requestor.setSignature("UpdateMax");
		request.setRequestorDTO(requestor);

		//BALISE INDIVIDUAL_REQUEST
		IndividualRequestDTO indRequest = new IndividualRequestDTO();
		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();

		indInfo.setIdentifier(gin);
		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);

		//BALISE ACCOUNT_DELEGATION_DATA_REQUEST
		AccountDelegationDataRequestDTO accountDelegationDataRequest = new AccountDelegationDataRequestDTO();

		DelegateDTO delegate = new DelegateDTO();

		DelegateDataDTO delegationData = new DelegateDataDTO();
		delegationData.setGin("400518868826");
		delegationData.setDelegationAction("A");
		delegationData.setDelegationType("UM");

		delegate.setDelegateDataDTO(delegationData);
		delegate.setComplementaryInformationDTO(new ArrayList<com.airfrance.repind.dto.ws.ComplementaryInformationDTO>());
		switch(scenary){
			// create only one telecom and one postalAddress (update)
			case 1 : delegate.getComplementaryInformationDTO().add(createComplementaryInformationOfTypeTelecom("0480770888", "33")); 
			delegate.getComplementaryInformationDTO().add(createComplementaryInformationOfTypePostalAddress("Marseille", "3 avenue du TEST", "06"));
			break;
			
			// create two telecoms and one postalAddress
			case 2 : delegate.getComplementaryInformationDTO().add(createComplementaryInformationOfTypeTelecom("0480770202", "33"));
			delegate.getComplementaryInformationDTO().add(createComplementaryInformationOfTypeTelecom("0445770101", "33")); 
			delegate.getComplementaryInformationDTO().add(createComplementaryInformationOfTypePostalAddress("Marseille", "3 avenue du BUG", "06"));
			break;
		}
		
		accountDelegationDataRequest.setAccountDelegationDataDTO(new AccountDelegationDataDTO());
		accountDelegationDataRequest.getAccountDelegationDataDTO().setDelegateDTO(new ArrayList<DelegateDTO>());
		accountDelegationDataRequest.getAccountDelegationDataDTO().getDelegateDTO().add(delegate);

		request.setAccountDelegationDataRequestDTO(accountDelegationDataRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		return createOrUpdateDS.createOrUpdateV8(request, response);
	}
	
	/**
	 * CONTEXT UM : KIDSOLO
	 * REPIND-895
	 */
	private com.airfrance.repind.dto.ws.ComplementaryInformationDTO createComplementaryInformationOfTypePostalAddress(String city, String numberAndStreet, String zipCode){
		com.airfrance.repind.dto.ws.ComplementaryInformationDTO complementaryInformation = new com.airfrance.repind.dto.ws.ComplementaryInformationDTO();
		complementaryInformation.setType(ComplementaryInformationTypeEnum.POSTAL_ADDRESS.getKey());

		ComplementaryInformationDatasDTO complementaryInformationDatas = new ComplementaryInformationDatasDTO();
		complementaryInformationDatas.setComplementaryInformationDataDTO(new ArrayList<com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO>());

		com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO complementaryInformationData = new com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO();
		complementaryInformationData.setKey(PostalAddressKeyEnum.NUMBER_AND_STREET.getKey());
		complementaryInformationData.setValue(numberAndStreet);
		complementaryInformationDatas.getComplementaryInformationDataDTO().add(complementaryInformationData);

		complementaryInformationData = new com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO();
		complementaryInformationData.setKey(PostalAddressKeyEnum.CITY.getKey());
		complementaryInformationData.setValue(city);
		complementaryInformationDatas.getComplementaryInformationDataDTO().add(complementaryInformationData);

		complementaryInformationData = new com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO();
		complementaryInformationData.setKey(PostalAddressKeyEnum.ZIPCODE.getKey());
		complementaryInformationData.setValue(zipCode);
		complementaryInformationDatas.getComplementaryInformationDataDTO().add(complementaryInformationData);

		complementaryInformation.setComplementaryInformationDatasDTO(complementaryInformationDatas);

		return complementaryInformation;
	}

	/**
	 * CONTEXT UM : KIDSOLO
	 * REPIND-895
	 */
	private com.airfrance.repind.dto.ws.ComplementaryInformationDTO createComplementaryInformationOfTypeTelecom(String phoneNumber, String countryCode){
		com.airfrance.repind.dto.ws.ComplementaryInformationDTO complementaryInformation = new com.airfrance.repind.dto.ws.ComplementaryInformationDTO();
		complementaryInformation.setType(ComplementaryInformationTypeEnum.PHONE.getKey());
		
		ComplementaryInformationDatasDTO complementaryInformationDatas = new ComplementaryInformationDatasDTO();
		complementaryInformationDatas.setComplementaryInformationDataDTO(new ArrayList<com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO>());

		com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO complementaryInformationData = new com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO();
		complementaryInformationData.setKey(TelecomKeyEnum.TERMINAL_TYPE.getKey());
		complementaryInformationData.setValue("M");
		complementaryInformationDatas.getComplementaryInformationDataDTO().add(complementaryInformationData);

		complementaryInformationData = new com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO();
		complementaryInformationData.setKey(TelecomKeyEnum.COUNTRY_CODE.getKey());
		complementaryInformationData.setValue(countryCode);
		complementaryInformationDatas.getComplementaryInformationDataDTO().add(complementaryInformationData);

		complementaryInformationData = new com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO();
		complementaryInformationData.setKey(TelecomKeyEnum.PHONE_NUMBER.getKey());
		complementaryInformationData.setValue(phoneNumber);
		complementaryInformationDatas.getComplementaryInformationDataDTO().add(complementaryInformationData);

		complementaryInformation.setComplementaryInformationDatasDTO(complementaryInformationDatas);

		return complementaryInformation;
	}
	
	/**
	 * CONTEXT UM : KIDSOLO
	 * REPIND-907 
	 * @return
	 */
	private CreateUpdateIndividualRequestDTO createDelegateIndividual(){
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		
		//Type K for kidSolo (UM)
		request.setProcess(ProcessEnum.K.getCode());

		//BALISE REQUESTOR
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestor.setApplicationCode("W000442v8");
		requestor.setSignature("xxxx");

		request.setRequestorDTO(requestor);

		//BALISE INDIVIDUAL_REQUEST
		IndividualRequestDTO indRequest = new IndividualRequestDTO();
		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();

		indInfo.setCivility("MR");
		indInfo.setLastNameSC("XAVDEVELOP");
		indInfo.setFirstNameSC("KIDPRENOM");
		indInfo.setBirthDate(new Date());
		indInfo.setStatus("V");
		
		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);
		
		IndividualProfilDTO indProfil = new IndividualProfilDTO();
		indProfil.setLanguageCode("FR");
		indProfil.setChildrenNumber("2");
		
		indRequest.setIndividualProfilDTO(indProfil);

		return request;
	}
	
	/**
	 * CONTEXT UM : KIDSOLO
	 * REPIND-907 
	 * @return
	 */
	private CreateUpdateIndividualRequestDTO updateDelegateIndividual(){
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		
		//Type K for kidSolo (UM)
		request.setProcess(ProcessEnum.K.getCode());

		//BALISE REQUESTOR
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestor.setApplicationCode("W000442v8");
		requestor.setSignature("xxxx");
		request.setRequestorDTO(requestor);

		//BALISE INDIVIDUAL_REQUEST
		IndividualRequestDTO indRequest = new IndividualRequestDTO();
		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();

		indInfo.setIdentifier("940000001985");
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("TOT");
		indInfo.setFirstNameSC("TESTTEST");
		indInfo.setBirthDate(new Date());
		indInfo.setStatus("V");
		indInfo.setVersion("1");
		indRequest.setIndividualInformationsDTO(indInfo);
		
		IndividualProfilDTO indProfil = new IndividualProfilDTO();
		indProfil.setLanguageCode("EN");
		indProfil.setChildrenNumber("2");
		
		indRequest.setIndividualProfilDTO(indProfil);
		
		request.setIndividualRequestDTO(indRequest);
	
		return request;
	}

	/**
	 * CONTEXT UM : KIDSOLO
	 * REPIND-907 
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testCreateOrUpdateAnIndividual_createIndividualData(){
		
		CreateUpdateIndividualRequestDTO request = createDelegateIndividual();
		
		try {
			CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
			// Execute test
			response = createOrUpdateDS.createOrUpdateV8(request, response);

			//TEST output
			Assert.assertNotNull(response);		
			Assert.assertTrue(response.getSuccess());
			
			String gin = response.getGin();
			try {
				//TEST if the new individu exist
				IndividuDTO indDTO = individuDS.getByGin(gin);
				Assert.assertNotNull(indDTO);
				Assert.assertEquals("XAVDEVELOP", indDTO.getNom());
				Assert.assertEquals("KIDPRENOM", indDTO.getPrenom());
				
				//TEST Profils
				Optional<Profils> profils = profilsRepository.findById(gin);
				Assert.assertTrue(profils.isPresent());
				Assert.assertEquals("FR", profils.get().getScode_langue());
					
			} catch (JrafDomainException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * CONTEXT UM : KIDSOLO
	 * REPIND-907 
	 */
	@Test
	@Transactional
	public void testCreateOrUpdateAnIndividual_updateIndividualData(){
		
		CreateUpdateIndividualRequestDTO request = updateDelegateIndividual();
		
		try {
			CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
			// Execute test
			response = createOrUpdateDS.createOrUpdateV8(request, response);

			//TEST response
			Assert.assertNotNull(response);		
			Assert.assertTrue(response.getSuccess());
						
			try {
//				IndividuDTO individuDTO = IndividuRequestTransform.transformRequestToIndividuDTO(request);
				IndividuDTO indDTO = individuDS.getByGin(request.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier());
				
				Assert.assertNotNull(indDTO);
				Assert.assertEquals(indDTO.getProfilsdto().getScode_langue(), "EN");
				Assert.assertEquals(indDTO.getNom(), "TOT");
	
			} catch (JrafDomainException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Transactional
	public void testCreateOrUpdateAnIndividual_createOrUpdateV8_NormalizationFailed() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException{
		
		CreateUpdateIndividualRequestDTO request = updateDelegateIndividual();
		
		// Postal Address
		PostalAddressRequestDTO addRequest = new PostalAddressRequestDTO();
		PostalAddressContentDTO pac = new PostalAddressContentDTO();
		pac.setNumberAndStreet("Baronielaan 229");
		pac.setZipCode("4835JK");
		pac.setCity("Breda");
		pac.setCountryCode("FR");			// NL en fait ! 
		pac.setAdditionalInformation("");
		pac.setDistrict("");
		pac.setStateCode("");
		pac.setCorporateName("");
		addRequest.setPostalAddressContentDTO(pac);

		PostalAddressPropertiesDTO pap = new PostalAddressPropertiesDTO();
		pap.setMediumCode(MediumCodeEnum.BUSINESS.toString());
		pap.setMediumStatus("V");
		pap.setIndicAdrNorm(false);						// On cherche a effectuer la normalisation
		addRequest.setPostalAddressPropertiesDTO(pap);

		request.setPostalAddressRequestDTO(new ArrayList<PostalAddressRequestDTO>());
		request.getPostalAddressRequestDTO().add(addRequest);
		
		
		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);
		
		//TEST response
		Assert.assertNotNull(response);		
		Assert.assertFalse(response.getSuccess());
		
		// Normalisation Failed		
		Assert.assertNotNull(response.getPostalAddressResponse());
		Assert.assertNotNull(response.getPostalAddressResponse().size() > 0);
	}

	@Test
	@Transactional
	public void testCreateOrUpdateAnIndividual_createOrUpdateV8_createIndividualDataByDefault() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException{
		
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess("I");
		
		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CreateOrUpdateAnIndividualDSTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualDSTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualDSTest.SIGNATURE);

		request.setRequestorDTO(requestor);

		IndividualRequestDTO indRequest = new IndividualRequestDTO();
		// indRequest.setCivilian("MR");

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("PHILIPPOTI"); 
		indInfo.setFirstNameSC("PATRICE");

		indRequest.setIndividualInformationsDTO(indInfo);

		request.setIndividualRequestDTO(indRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);
		
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());

		CreateOrUpdateAnIndividualDSTest.logger.info("GIN created : " + response.getGin());
	}
	
	@Test
	@Transactional
	public void testCreateOrUpdateAnIndividual_createOrUpdateV8_updateIndividualDataByDefault() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException{
		
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess("I");
		
		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CreateOrUpdateAnIndividualDSTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualDSTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualDSTest.SIGNATURE);

		request.setRequestorDTO(requestor);

		IndividualRequestDTO indRequest = new IndividualRequestDTO();
		// indRequest.setCivilian("MR");

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		

		// Individual
		IndividualRequestDTO indReq = new IndividualRequestDTO();

		indInfo.setIdentifier("400045915661");
		indReq.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indReq);

		
		indRequest.setIndividualInformationsDTO(indInfo);

		request.setIndividualRequestDTO(indRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);
		
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		// Assert.assertNotNull(response.getGin());		// Dans le cas d'un Update, on ne renvoi pas le GIN... Etrange...
	}

	@Test	// Create an External Identifier without any External Identifier... Strange too... 
	public void testCreateOrUpdateIndividual_createAnIndividualExternal_By_Default() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException  {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CreateOrUpdateAnIndividualDSTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualDSTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualDSTest.SIGNATURE);

		request.setRequestorDTO(requestor);

		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("SOCIAL" + generateString());
		indInfo.setFirstNameSC("SOCIAL" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);
		
		// Tests
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());

		Assert.assertTrue(response.getGin().startsWith("92"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());


		CreateOrUpdateAnIndividualDSTest.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KLM_AF " + response.getGin());
	}

	@Test
	public void testCreateOrUpdateIndividual_createIndividualDataAlert_findProspectDTOTest() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateOrUpdateAnIndividualDSTest.logger.info("Test start... ");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		request.setProcess(ProcessEnum.A.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");


		// Email
		EmailRequestDTO emailReq = new EmailRequestDTO();
		com.airfrance.repind.dto.ws.EmailDTO email = new com.airfrance.repind.dto.ws.EmailDTO();
		String mail = generateEmail();
		//		Random rd = new Random();
		//		int randomInt = rd.nextInt(1000);

		//		mail.append("tualert");
		//		mail.append(Integer.toString(randomInt));
		//		mail.append("@yopmail.com");
		email.setEmail(mail);
		email.setEmailOptin("A");
		email.setMediumCode("D");
		email.setMediumStatus("V");
		emailReq.setEmailDTO(email);

		// Communication Preference
		CommunicationPreferencesRequestDTO comPrefrequest = new CommunicationPreferencesRequestDTO();
		com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("AF");
		comPref.setOptIn("N");
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			comPref.setDateOfConsent(df.parse("30-08-2016"));
		} catch (ParseException e) {
			CreateOrUpdateAnIndividualDSTest.logger.error("unable to set date of consent");
		}
		comPref.setSubscriptionChannel("B2C Home Page");

		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setLanguage("FR");
		ml.setMarket("FR");
		ml.setOptIn("N");
		try {
			ml.setDateOfConsent(df.parse("01-09-2016"));
		} catch (ParseException e) {
			CreateOrUpdateAnIndividualDSTest.logger.error("unable to set market language date of consent");
		}

		List <MarketLanguageDTO> lml = new ArrayList <MarketLanguageDTO>();
		lml.add(ml);
		
		comPref.setMarketLanguageDTO(lml);
		comPrefrequest.setCommunicationPreferencesDTO(comPref);

		// AlertDTO
		AlertRequestDTO alertRequest = new AlertRequestDTO();
		AlertDTO alert = new AlertDTO();
		alert.setType("P");
		alert.setOptin("N");
		/* origin */
		AlertDataDTO orig = new AlertDataDTO();
		orig.setKey("ORIGIN");
		orig.setValue("PAR");
		
		List <AlertDataDTO> ladd = new ArrayList<AlertDataDTO>();
		ladd.add(orig);
		alert.setAlertDataDTO(ladd);
		/* origin type */
		AlertDataDTO origType = new AlertDataDTO();
		origType.setKey("ORIGIN_TYPE");
		origType.setValue("C");
		alert.getAlertDataDTO().add(origType);
		/* destination */
		AlertDataDTO dest = new AlertDataDTO();
		dest.setKey("DESTINATION");
		dest.setValue("LAX");
		alert.getAlertDataDTO().add(dest);
		/* destination type */
		AlertDataDTO destType = new AlertDataDTO();
		destType.setKey("DESTINATION_TYPE");
		destType.setValue("A");
		alert.getAlertDataDTO().add(destType);
		/* start date */
		AlertDataDTO sDate = new AlertDataDTO();
		sDate.setKey("START_DATE");
		sDate.setValue("01012016");
		alert.getAlertDataDTO().add(sDate);
		/* end date */
		AlertDataDTO eDate = new AlertDataDTO();
		eDate.setKey("END_DATE");
		eDate.setValue("31122016");
		alert.getAlertDataDTO().add(eDate);
		/* cabin */
		AlertDataDTO cabin = new AlertDataDTO();
		cabin.setKey("CABIN");
		cabin.setValue("W");
		alert.getAlertDataDTO().add(cabin);

		List <AlertDTO> lad = new ArrayList<AlertDTO>();
		lad.add(alert);
		alertRequest.setAlertDTO(lad);

		List <EmailRequestDTO> lerd = new ArrayList<EmailRequestDTO>();
		lerd.add(emailReq);
		request.setEmailRequestDTO(lerd);		
		request.setRequestorDTO(requestor);
		
		List <CommunicationPreferencesRequestDTO> lcprd = new ArrayList<CommunicationPreferencesRequestDTO>();
		lcprd.add(comPrefrequest);
		request.setCommunicationPreferencesRequestDTO(lcprd);
		
		request.setAlertRequestDTO(alertRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		// Tests
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertTrue(response.getGin().startsWith("90"));

		CreateOrUpdateAnIndividualDSTest.logger.info("End of test. ");
	}

	
	@Test
	public void testCreateOrUpdateIndividual_createIndividualDataAlert_MaximumSubscriptionsException() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateOrUpdateAnIndividualDSTest.logger.info("Test start... ");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		request.setProcess(ProcessEnum.A.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");


		// Email
		EmailRequestDTO emailReq = new EmailRequestDTO();
		com.airfrance.repind.dto.ws.EmailDTO email = new com.airfrance.repind.dto.ws.EmailDTO();
		String mail = "jmseksik@airfrance.fr";
		
		email.setEmail(mail);
		emailReq.setEmailDTO(email);

		// Communication Preference
		List <CommunicationPreferencesRequestDTO> lcprd = new ArrayList<CommunicationPreferencesRequestDTO>();
		
//		for (int i = 1; i <= 5; i++) {
			CommunicationPreferencesRequestDTO comPrefrequest = new CommunicationPreferencesRequestDTO();
			com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
			comPref.setDomain("S");
			comPref.setCommunicationGroupeType("N");
			comPref.setCommunicationType("AF");
			comPref.setOptIn("N");
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			try {
				comPref.setDateOfConsent(df.parse("03-08-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set date of consent");
			}
			comPref.setSubscriptionChannel("B2C Home Page");
	
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setLanguage("FR");
			ml.setMarket("FR");
			ml.setOptIn("N");
			try {
				ml.setDateOfConsent(df.parse("01-09-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set market language date of consent");
			}
	
			List <MarketLanguageDTO> lml = new ArrayList <MarketLanguageDTO>();
			lml.add(ml);
			
			comPref.setMarketLanguageDTO(lml);
			comPrefrequest.setCommunicationPreferencesDTO(comPref);
				
			lcprd.add(comPrefrequest);
		
//		}
		
		request.setCommunicationPreferencesRequestDTO(lcprd);

		
		// AlertDTO
		AlertRequestDTO alertRequest = new AlertRequestDTO();
		AlertDTO alert = new AlertDTO();
		alert.setType("P");
		alert.setOptin("N");
		/* origin */
		AlertDataDTO orig = new AlertDataDTO();
		orig.setKey("ORIGIN");
		orig.setValue("PAR");
		
		List <AlertDataDTO> ladd = new ArrayList<AlertDataDTO>();
		ladd.add(orig);
		alert.setAlertDataDTO(ladd);
		/* origin type */
		AlertDataDTO origType = new AlertDataDTO();
		origType.setKey("ORIGIN_TYPE");
		origType.setValue("C");
		alert.getAlertDataDTO().add(origType);
		/* destination */
		AlertDataDTO dest = new AlertDataDTO();
		dest.setKey("DESTINATION");
		dest.setValue("LAX");
		alert.getAlertDataDTO().add(dest);
		/* destination type */
		AlertDataDTO destType = new AlertDataDTO();
		destType.setKey("DESTINATION_TYPE");
		destType.setValue("A");
		alert.getAlertDataDTO().add(destType);
		/* start date */
		AlertDataDTO sDate = new AlertDataDTO();
		sDate.setKey("START_DATE");
		sDate.setValue("01012016");
		alert.getAlertDataDTO().add(sDate);
		/* end date */
		AlertDataDTO eDate = new AlertDataDTO();
		eDate.setKey("END_DATE");
		eDate.setValue("31122016");
		alert.getAlertDataDTO().add(eDate);
		/* cabin */
		AlertDataDTO cabin = new AlertDataDTO();
		cabin.setKey("CABIN");
		cabin.setValue("W");
		alert.getAlertDataDTO().add(cabin);

		List <AlertDTO> lad = new ArrayList<AlertDTO>();
		lad.add(alert);
		alertRequest.setAlertDTO(lad);

		List <EmailRequestDTO> lerd = new ArrayList<EmailRequestDTO>();
		lerd.add(emailReq);
		request.setEmailRequestDTO(lerd);		
		request.setRequestorDTO(requestor);
				
		request.setAlertRequestDTO(alertRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		
		try {		
			response = createOrUpdateDS.createOrUpdateV8(request, response);
			Assert.fail("On devrait avoir une MaximumSubscriptionsException");
		} catch (JrafException ex) {
			Assert.assertNotNull(ex);
			Assert.assertEquals("MAXIMUM NUMBER OF SUBSCRIBED NEWLETTER SALES REACHED", ex.getMessage());
		}

		CreateOrUpdateAnIndividualDSTest.logger.info("End of test. ");
	}
	

	@Test
	public void testCreateOrUpdateIndividual_createIndividualDataAlert_NotFoundException_DeletedProspect() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateOrUpdateAnIndividualDSTest.logger.info("Test start... ");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		request.setProcess(ProcessEnum.A.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");


		// Email
		EmailRequestDTO emailReq = new EmailRequestDTO();
		com.airfrance.repind.dto.ws.EmailDTO email = new com.airfrance.repind.dto.ws.EmailDTO();
		String mail = "xxppss2008@yahoo.com.cn";
		
		email.setEmail(mail);
		emailReq.setEmailDTO(email);

		// Communication Preference
		List <CommunicationPreferencesRequestDTO> lcprd = new ArrayList<CommunicationPreferencesRequestDTO>();
		
//		for (int i = 1; i <= 5; i++) {
			CommunicationPreferencesRequestDTO comPrefrequest = new CommunicationPreferencesRequestDTO();
			com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
			comPref.setDomain("S");
			comPref.setCommunicationGroupeType("N");
			comPref.setCommunicationType("AF");
			comPref.setOptIn("N");
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			try {
				comPref.setDateOfConsent(df.parse("03-08-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set date of consent");
			}
			comPref.setSubscriptionChannel("B2C Home Page");
	
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setLanguage("FR");
			ml.setMarket("FR");
			ml.setOptIn("N");
			try {
				ml.setDateOfConsent(df.parse("01-09-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set market language date of consent");
			}
	
			List <MarketLanguageDTO> lml = new ArrayList <MarketLanguageDTO>();
			lml.add(ml);
			
			comPref.setMarketLanguageDTO(lml);
			comPrefrequest.setCommunicationPreferencesDTO(comPref);
				
			lcprd.add(comPrefrequest);
		
//		}
		
		request.setCommunicationPreferencesRequestDTO(lcprd);

		
		// AlertDTO
		AlertRequestDTO alertRequest = new AlertRequestDTO();
		AlertDTO alert = new AlertDTO();
		alert.setType("P");
		alert.setOptin("N");
		/* origin */
		AlertDataDTO orig = new AlertDataDTO();
		orig.setKey("ORIGIN");
		orig.setValue("PAR");
		
		List <AlertDataDTO> ladd = new ArrayList<AlertDataDTO>();
		ladd.add(orig);
		alert.setAlertDataDTO(ladd);
		/* origin type */
		AlertDataDTO origType = new AlertDataDTO();
		origType.setKey("ORIGIN_TYPE");
		origType.setValue("C");
		alert.getAlertDataDTO().add(origType);
		/* destination */
		AlertDataDTO dest = new AlertDataDTO();
		dest.setKey("DESTINATION");
		dest.setValue("LAX");
		alert.getAlertDataDTO().add(dest);
		/* destination type */
		AlertDataDTO destType = new AlertDataDTO();
		destType.setKey("DESTINATION_TYPE");
		destType.setValue("A");
		alert.getAlertDataDTO().add(destType);
		/* start date */
		AlertDataDTO sDate = new AlertDataDTO();
		sDate.setKey("START_DATE");
		sDate.setValue("01012016");
		alert.getAlertDataDTO().add(sDate);
		/* end date */
		AlertDataDTO eDate = new AlertDataDTO();
		eDate.setKey("END_DATE");
		eDate.setValue("31122016");
		alert.getAlertDataDTO().add(eDate);
		/* cabin */
		AlertDataDTO cabin = new AlertDataDTO();
		cabin.setKey("CABIN");
		cabin.setValue("W");
		alert.getAlertDataDTO().add(cabin);

		List <AlertDTO> lad = new ArrayList<AlertDTO>();
		lad.add(alert);
		alertRequest.setAlertDTO(lad);

		List <EmailRequestDTO> lerd = new ArrayList<EmailRequestDTO>();
		lerd.add(emailReq);
		request.setEmailRequestDTO(lerd);		
		request.setRequestorDTO(requestor);
				
		request.setAlertRequestDTO(alertRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		
		try {		
			response = createOrUpdateDS.createOrUpdateV8(request, response);
			Assert.fail("On devrait avoir une NotFoundException");
		} catch (JrafException ex) {
			Assert.assertNotNull(ex);
			Assert.assertEquals("Individual not found: Deleted prospect", ex.getMessage());
		}

		CreateOrUpdateAnIndividualDSTest.logger.info("End of test. ");
	}
	
	
	@Test
	public void testCreateOrUpdateIndividual_updateIndividualDataAlert_MaximumSubscriptionsException() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateOrUpdateAnIndividualDSTest.logger.info("Test start... ");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		request.setProcess(ProcessEnum.A.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Individual
		IndividualRequestDTO indReq = new IndividualRequestDTO();
		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();

		indInfo.setIdentifier("900000000024");
		indReq.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indReq);

		// Email
		EmailRequestDTO emailReq = new EmailRequestDTO();
		com.airfrance.repind.dto.ws.EmailDTO email = new com.airfrance.repind.dto.ws.EmailDTO();
		String mail = "jmseksik@airfrance.fr";
		
		email.setEmail(mail);
		emailReq.setEmailDTO(email);

		// Communication Preference
		List <CommunicationPreferencesRequestDTO> lcprd = new ArrayList<CommunicationPreferencesRequestDTO>();
		
//		for (int i = 1; i <= 5; i++) {
			CommunicationPreferencesRequestDTO comPrefrequest = new CommunicationPreferencesRequestDTO();
			com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
			comPref.setDomain("S");
			comPref.setCommunicationGroupeType("N");
			comPref.setCommunicationType("AF");
			comPref.setOptIn("N");
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			try {
				comPref.setDateOfConsent(df.parse("03-08-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set date of consent");
			}
			comPref.setSubscriptionChannel("B2C Home Page");
	
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setLanguage("FR");
			ml.setMarket("FR");
			ml.setOptIn("N");
			try {
				ml.setDateOfConsent(df.parse("01-09-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set market language date of consent");
			}
	
			List <MarketLanguageDTO> lml = new ArrayList <MarketLanguageDTO>();
			lml.add(ml);
			
			comPref.setMarketLanguageDTO(lml);
			comPrefrequest.setCommunicationPreferencesDTO(comPref);
				
			lcprd.add(comPrefrequest);
		
//		}
		
		request.setCommunicationPreferencesRequestDTO(lcprd);

		
		// AlertDTO
		AlertRequestDTO alertRequest = new AlertRequestDTO();
		AlertDTO alert = new AlertDTO();
		alert.setType("P");
		alert.setOptin("N");
		/* origin */
		AlertDataDTO orig = new AlertDataDTO();
		orig.setKey("ORIGIN");
		orig.setValue("PAR");
		
		List <AlertDataDTO> ladd = new ArrayList<AlertDataDTO>();
		ladd.add(orig);
		alert.setAlertDataDTO(ladd);
		/* origin type */
		AlertDataDTO origType = new AlertDataDTO();
		origType.setKey("ORIGIN_TYPE");
		origType.setValue("C");
		alert.getAlertDataDTO().add(origType);
		/* destination */
		AlertDataDTO dest = new AlertDataDTO();
		dest.setKey("DESTINATION");
		dest.setValue("LAX");
		alert.getAlertDataDTO().add(dest);
		/* destination type */
		AlertDataDTO destType = new AlertDataDTO();
		destType.setKey("DESTINATION_TYPE");
		destType.setValue("A");
		alert.getAlertDataDTO().add(destType);
		/* start date */
		AlertDataDTO sDate = new AlertDataDTO();
		sDate.setKey("START_DATE");
		sDate.setValue("01012016");
		alert.getAlertDataDTO().add(sDate);
		/* end date */
		AlertDataDTO eDate = new AlertDataDTO();
		eDate.setKey("END_DATE");
		eDate.setValue("31122016");
		alert.getAlertDataDTO().add(eDate);
		/* cabin */
		AlertDataDTO cabin = new AlertDataDTO();
		cabin.setKey("CABIN");
		cabin.setValue("W");
		alert.getAlertDataDTO().add(cabin);

		List <AlertDTO> lad = new ArrayList<AlertDTO>();
		lad.add(alert);
		alertRequest.setAlertDTO(lad);

		List <EmailRequestDTO> lerd = new ArrayList<EmailRequestDTO>();
		lerd.add(emailReq);
		request.setEmailRequestDTO(lerd);		
		request.setRequestorDTO(requestor);
				
		request.setAlertRequestDTO(alertRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		
		try {		
			response = createOrUpdateDS.createOrUpdateV8(request, response);
			Assert.fail("On devrait avoir une MaximumSubscriptionsException");
		} catch (JrafException ex) {
			Assert.assertNotNull(ex);
			Assert.assertEquals("MAXIMUM NUMBER OF SUBSCRIBED NEWLETTER SALES REACHED", ex.getMessage());
		}

		CreateOrUpdateAnIndividualDSTest.logger.info("End of test. ");
	}
	

	@Test
	public void testCreateOrUpdateIndividual_updateIndividualDataAlert_NotFoundException() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateOrUpdateAnIndividualDSTest.logger.info("Test start... ");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		request.setProcess(ProcessEnum.A.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Individual
		IndividualRequestDTO indReq = new IndividualRequestDTO();
		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();

		indInfo.setIdentifier("999999999999");
		indReq.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indReq);

		// Email
		EmailRequestDTO emailReq = new EmailRequestDTO();
		com.airfrance.repind.dto.ws.EmailDTO email = new com.airfrance.repind.dto.ws.EmailDTO();
		String mail = "xxppss2008@yahoo.com.cn";
		
		email.setEmail(mail);
		emailReq.setEmailDTO(email);

		// Communication Preference
		List <CommunicationPreferencesRequestDTO> lcprd = new ArrayList<CommunicationPreferencesRequestDTO>();
		
//		for (int i = 1; i <= 5; i++) {
			CommunicationPreferencesRequestDTO comPrefrequest = new CommunicationPreferencesRequestDTO();
			com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
			comPref.setDomain("S");
			comPref.setCommunicationGroupeType("N");
			comPref.setCommunicationType("AF");
			comPref.setOptIn("N");
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			try {
				comPref.setDateOfConsent(df.parse("03-08-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set date of consent");
			}
			comPref.setSubscriptionChannel("B2C Home Page");
	
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setLanguage("FR");
			ml.setMarket("FR");
			ml.setOptIn("N");
			try {
				ml.setDateOfConsent(df.parse("01-09-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set market language date of consent");
			}
	
			List <MarketLanguageDTO> lml = new ArrayList <MarketLanguageDTO>();
			lml.add(ml);
			
			comPref.setMarketLanguageDTO(lml);
			comPrefrequest.setCommunicationPreferencesDTO(comPref);
				
			lcprd.add(comPrefrequest);
		
//		}
		
		request.setCommunicationPreferencesRequestDTO(lcprd);

		
		// AlertDTO
		AlertRequestDTO alertRequest = new AlertRequestDTO();
		AlertDTO alert = new AlertDTO();
		alert.setType("P");
		alert.setOptin("N");
		/* origin */
		AlertDataDTO orig = new AlertDataDTO();
		orig.setKey("ORIGIN");
		orig.setValue("PAR");
		
		List <AlertDataDTO> ladd = new ArrayList<AlertDataDTO>();
		ladd.add(orig);
		alert.setAlertDataDTO(ladd);
		/* origin type */
		AlertDataDTO origType = new AlertDataDTO();
		origType.setKey("ORIGIN_TYPE");
		origType.setValue("C");
		alert.getAlertDataDTO().add(origType);
		/* destination */
		AlertDataDTO dest = new AlertDataDTO();
		dest.setKey("DESTINATION");
		dest.setValue("LAX");
		alert.getAlertDataDTO().add(dest);
		/* destination type */
		AlertDataDTO destType = new AlertDataDTO();
		destType.setKey("DESTINATION_TYPE");
		destType.setValue("A");
		alert.getAlertDataDTO().add(destType);
		/* start date */
		AlertDataDTO sDate = new AlertDataDTO();
		sDate.setKey("START_DATE");
		sDate.setValue("01012016");
		alert.getAlertDataDTO().add(sDate);
		/* end date */
		AlertDataDTO eDate = new AlertDataDTO();
		eDate.setKey("END_DATE");
		eDate.setValue("31122016");
		alert.getAlertDataDTO().add(eDate);
		/* cabin */
		AlertDataDTO cabin = new AlertDataDTO();
		cabin.setKey("CABIN");
		cabin.setValue("W");
		alert.getAlertDataDTO().add(cabin);

		List <AlertDTO> lad = new ArrayList<AlertDTO>();
		lad.add(alert);
		alertRequest.setAlertDTO(lad);

		List <EmailRequestDTO> lerd = new ArrayList<EmailRequestDTO>();
		lerd.add(emailReq);
		request.setEmailRequestDTO(lerd);		
		request.setRequestorDTO(requestor);
				
		request.setAlertRequestDTO(alertRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		
		try {		
			response = createOrUpdateDS.createOrUpdateV8(request, response);
			Assert.fail("On devrait avoir une NotFoundException");
		} catch (JrafException ex) {
			Assert.assertNotNull(ex);
			Assert.assertEquals("Individual not found: Individual not found", ex.getMessage());
		}

		CreateOrUpdateAnIndividualDSTest.logger.info("End of test. ");
	}

	@Test
	public void testCreateOrUpdateIndividual_createIndividualDataWhiteWinger_MaximumSubscriptionsException() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateOrUpdateAnIndividualDSTest.logger.info("Test start... ");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		request.setProcess(ProcessEnum.W.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");


		// Email
		EmailRequestDTO emailReq = new EmailRequestDTO();
		com.airfrance.repind.dto.ws.EmailDTO email = new com.airfrance.repind.dto.ws.EmailDTO();
		String mail = "jmseksik@airfrance.fr";
		
		email.setEmail(mail);
		emailReq.setEmailDTO(email);

		// Communication Preference
		List <CommunicationPreferencesRequestDTO> lcprd = new ArrayList<CommunicationPreferencesRequestDTO>();
		
//		for (int i = 1; i <= 5; i++) {
			CommunicationPreferencesRequestDTO comPrefrequest = new CommunicationPreferencesRequestDTO();
			com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
			comPref.setDomain("S");
			comPref.setCommunicationGroupeType("N");
			comPref.setCommunicationType("AF");
			comPref.setOptIn("N");
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			try {
				comPref.setDateOfConsent(df.parse("03-08-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set date of consent");
			}
			comPref.setSubscriptionChannel("B2C Home Page");
	
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setLanguage("FR");
			ml.setMarket("FR");
			ml.setOptIn("N");
			try {
				ml.setDateOfConsent(df.parse("01-09-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set market language date of consent");
			}
	
			List <MarketLanguageDTO> lml = new ArrayList <MarketLanguageDTO>();
			lml.add(ml);
			
			comPref.setMarketLanguageDTO(lml);
			comPrefrequest.setCommunicationPreferencesDTO(comPref);
				
			lcprd.add(comPrefrequest);
		
//		}
		
		request.setCommunicationPreferencesRequestDTO(lcprd);

		
		// AlertDTO
		AlertRequestDTO alertRequest = new AlertRequestDTO();
		AlertDTO alert = new AlertDTO();
		alert.setType("P");
		alert.setOptin("N");
		/* origin */
		AlertDataDTO orig = new AlertDataDTO();
		orig.setKey("ORIGIN");
		orig.setValue("PAR");
		
		List <AlertDataDTO> ladd = new ArrayList<AlertDataDTO>();
		ladd.add(orig);
		alert.setAlertDataDTO(ladd);
		/* origin type */
		AlertDataDTO origType = new AlertDataDTO();
		origType.setKey("ORIGIN_TYPE");
		origType.setValue("C");
		alert.getAlertDataDTO().add(origType);
		/* destination */
		AlertDataDTO dest = new AlertDataDTO();
		dest.setKey("DESTINATION");
		dest.setValue("LAX");
		alert.getAlertDataDTO().add(dest);
		/* destination type */
		AlertDataDTO destType = new AlertDataDTO();
		destType.setKey("DESTINATION_TYPE");
		destType.setValue("A");
		alert.getAlertDataDTO().add(destType);
		/* start date */
		AlertDataDTO sDate = new AlertDataDTO();
		sDate.setKey("START_DATE");
		sDate.setValue("01012016");
		alert.getAlertDataDTO().add(sDate);
		/* end date */
		AlertDataDTO eDate = new AlertDataDTO();
		eDate.setKey("END_DATE");
		eDate.setValue("31122016");
		alert.getAlertDataDTO().add(eDate);
		/* cabin */
		AlertDataDTO cabin = new AlertDataDTO();
		cabin.setKey("CABIN");
		cabin.setValue("W");
		alert.getAlertDataDTO().add(cabin);

		List <AlertDTO> lad = new ArrayList<AlertDTO>();
		lad.add(alert);
		alertRequest.setAlertDTO(lad);

		List <EmailRequestDTO> lerd = new ArrayList<EmailRequestDTO>();
		lerd.add(emailReq);
		request.setEmailRequestDTO(lerd);		
		request.setRequestorDTO(requestor);
				
		request.setAlertRequestDTO(alertRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		
		try {		
			response = createOrUpdateDS.createOrUpdateV8(request, response);
			Assert.fail("On devrait avoir une MaximumSubscriptionsException");
		} catch (JrafException ex) {
			Assert.assertNotNull(ex);
			Assert.assertEquals("MAXIMUM NUMBER OF SUBSCRIBED NEWLETTER SALES REACHED", ex.getMessage());
		}

		CreateOrUpdateAnIndividualDSTest.logger.info("End of test. ");
	}
	

	@Test
	public void testCreateOrUpdateIndividual_createIndividualDataWhiteWinger_DeletedProspect() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateOrUpdateAnIndividualDSTest.logger.info("Test start... ");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		request.setProcess(ProcessEnum.W.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");
		
		// Email
		EmailRequestDTO emailReq = new EmailRequestDTO();
		com.airfrance.repind.dto.ws.EmailDTO email = new com.airfrance.repind.dto.ws.EmailDTO();
		String mail = "xxppss2008@yahoo.com.cn";
		email.setEmail(mail);
		emailReq.setEmailDTO(email);
		
		// Communication Preference
		List <CommunicationPreferencesRequestDTO> lcprd = new ArrayList<CommunicationPreferencesRequestDTO>();
		
//		for (int i = 1; i <= 5; i++) {
			CommunicationPreferencesRequestDTO comPrefrequest = new CommunicationPreferencesRequestDTO();
			com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
			comPref.setDomain("S");
			comPref.setCommunicationGroupeType("N");
			comPref.setCommunicationType("AF");
			comPref.setOptIn("N");
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			try {
				comPref.setDateOfConsent(df.parse("03-08-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set date of consent");
			}
			comPref.setSubscriptionChannel("B2C Home Page");
	
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setLanguage("FR");
			ml.setMarket("FR");
			ml.setOptIn("N");
			try {
				ml.setDateOfConsent(df.parse("01-09-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set market language date of consent");
			}
	
			List <MarketLanguageDTO> lml = new ArrayList <MarketLanguageDTO>();
			lml.add(ml);
			
			comPref.setMarketLanguageDTO(lml);
			comPrefrequest.setCommunicationPreferencesDTO(comPref);
				
			lcprd.add(comPrefrequest);
		
//		}
		
		request.setCommunicationPreferencesRequestDTO(lcprd);

		
		// AlertDTO
		AlertRequestDTO alertRequest = new AlertRequestDTO();
		AlertDTO alert = new AlertDTO();
		alert.setType("P");
		alert.setOptin("N");
		/* origin */
		AlertDataDTO orig = new AlertDataDTO();
		orig.setKey("ORIGIN");
		orig.setValue("PAR");
		
		List <AlertDataDTO> ladd = new ArrayList<AlertDataDTO>();
		ladd.add(orig);
		alert.setAlertDataDTO(ladd);
		/* origin type */
		AlertDataDTO origType = new AlertDataDTO();
		origType.setKey("ORIGIN_TYPE");
		origType.setValue("C");
		alert.getAlertDataDTO().add(origType);
		/* destination */
		AlertDataDTO dest = new AlertDataDTO();
		dest.setKey("DESTINATION");
		dest.setValue("LAX");
		alert.getAlertDataDTO().add(dest);
		/* destination type */
		AlertDataDTO destType = new AlertDataDTO();
		destType.setKey("DESTINATION_TYPE");
		destType.setValue("A");
		alert.getAlertDataDTO().add(destType);
		/* start date */
		AlertDataDTO sDate = new AlertDataDTO();
		sDate.setKey("START_DATE");
		sDate.setValue("01012016");
		alert.getAlertDataDTO().add(sDate);
		/* end date */
		AlertDataDTO eDate = new AlertDataDTO();
		eDate.setKey("END_DATE");
		eDate.setValue("31122016");
		alert.getAlertDataDTO().add(eDate);
		/* cabin */
		AlertDataDTO cabin = new AlertDataDTO();
		cabin.setKey("CABIN");
		cabin.setValue("W");
		alert.getAlertDataDTO().add(cabin);

		List <AlertDTO> lad = new ArrayList<AlertDTO>();
		lad.add(alert);
		alertRequest.setAlertDTO(lad);

		List <EmailRequestDTO> lerd = new ArrayList<EmailRequestDTO>();
		lerd.add(emailReq);
		request.setEmailRequestDTO(lerd);		
		request.setRequestorDTO(requestor);
				
		request.setAlertRequestDTO(alertRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		
		try {		
			response = createOrUpdateDS.createOrUpdateV8(request, response);
			Assert.fail("On devrait avoir une MaximumSubscriptionsException");
		} catch (JrafException ex) {
			Assert.assertNotNull(ex);
			Assert.assertEquals("Individual not found: Deleted prospect", ex.getMessage());
		}

		CreateOrUpdateAnIndividualDSTest.logger.info("End of test. ");
	}
	

	@Test
	public void testCreateOrUpdateIndividual_updateIndividualDataWhiteWinger_MaximumSubscriptionsException() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateOrUpdateAnIndividualDSTest.logger.info("Test start... ");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		request.setProcess(ProcessEnum.W.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Individual
		IndividualRequestDTO indReq = new IndividualRequestDTO();
		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();

		indInfo.setIdentifier("900000000024");
		indReq.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indReq);
		
		// Email
		EmailRequestDTO emailReq = new EmailRequestDTO();
		com.airfrance.repind.dto.ws.EmailDTO email = new com.airfrance.repind.dto.ws.EmailDTO();
		String mail = "jmseksik@airfrance.fr";
		
		email.setEmail(mail);
		emailReq.setEmailDTO(email);

		// Communication Preference
		List <CommunicationPreferencesRequestDTO> lcprd = new ArrayList<CommunicationPreferencesRequestDTO>();
		
//		for (int i = 1; i <= 5; i++) {
			CommunicationPreferencesRequestDTO comPrefrequest = new CommunicationPreferencesRequestDTO();
			com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
			comPref.setDomain("S");
			comPref.setCommunicationGroupeType("N");
			comPref.setCommunicationType("AF");
			comPref.setOptIn("N");
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			try {
				comPref.setDateOfConsent(df.parse("03-08-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set date of consent");
			}
			comPref.setSubscriptionChannel("B2C Home Page");
	
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setLanguage("FR");
			ml.setMarket("FR");
			ml.setOptIn("N");
			try {
				ml.setDateOfConsent(df.parse("01-09-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set market language date of consent");
			}
	
			List <MarketLanguageDTO> lml = new ArrayList <MarketLanguageDTO>();
			lml.add(ml);
			
			comPref.setMarketLanguageDTO(lml);
			comPrefrequest.setCommunicationPreferencesDTO(comPref);
				
			lcprd.add(comPrefrequest);
		
//		}
		
		request.setCommunicationPreferencesRequestDTO(lcprd);

		
		// AlertDTO
		AlertRequestDTO alertRequest = new AlertRequestDTO();
		AlertDTO alert = new AlertDTO();
		alert.setType("P");
		alert.setOptin("N");
		/* origin */
		AlertDataDTO orig = new AlertDataDTO();
		orig.setKey("ORIGIN");
		orig.setValue("PAR");
		
		List <AlertDataDTO> ladd = new ArrayList<AlertDataDTO>();
		ladd.add(orig);
		alert.setAlertDataDTO(ladd);
		/* origin type */
		AlertDataDTO origType = new AlertDataDTO();
		origType.setKey("ORIGIN_TYPE");
		origType.setValue("C");
		alert.getAlertDataDTO().add(origType);
		/* destination */
		AlertDataDTO dest = new AlertDataDTO();
		dest.setKey("DESTINATION");
		dest.setValue("LAX");
		alert.getAlertDataDTO().add(dest);
		/* destination type */
		AlertDataDTO destType = new AlertDataDTO();
		destType.setKey("DESTINATION_TYPE");
		destType.setValue("A");
		alert.getAlertDataDTO().add(destType);
		/* start date */
		AlertDataDTO sDate = new AlertDataDTO();
		sDate.setKey("START_DATE");
		sDate.setValue("01012016");
		alert.getAlertDataDTO().add(sDate);
		/* end date */
		AlertDataDTO eDate = new AlertDataDTO();
		eDate.setKey("END_DATE");
		eDate.setValue("31122016");
		alert.getAlertDataDTO().add(eDate);
		/* cabin */
		AlertDataDTO cabin = new AlertDataDTO();
		cabin.setKey("CABIN");
		cabin.setValue("W");
		alert.getAlertDataDTO().add(cabin);

		List <AlertDTO> lad = new ArrayList<AlertDTO>();
		lad.add(alert);
		alertRequest.setAlertDTO(lad);

		List <EmailRequestDTO> lerd = new ArrayList<EmailRequestDTO>();
		lerd.add(emailReq);
		request.setEmailRequestDTO(lerd);		
		request.setRequestorDTO(requestor);
				
		request.setAlertRequestDTO(alertRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		
		try {		
			response = createOrUpdateDS.createOrUpdateV8(request, response);
			Assert.fail("On devrait avoir une MaximumSubscriptionsException");
		} catch (JrafException ex) {
			Assert.assertNotNull(ex);
			Assert.assertEquals("MAXIMUM NUMBER OF SUBSCRIBED NEWLETTER SALES REACHED", ex.getMessage());
		}

		CreateOrUpdateAnIndividualDSTest.logger.info("End of test. ");
	}
	

	@Test
	public void testCreateOrUpdateIndividual_updateIndividualDataWhiteWinger_DeletedProspect() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateOrUpdateAnIndividualDSTest.logger.info("Test start... ");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		request.setProcess(ProcessEnum.W.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Email
		EmailRequestDTO emailReq = new EmailRequestDTO();
		com.airfrance.repind.dto.ws.EmailDTO email = new com.airfrance.repind.dto.ws.EmailDTO();
		String mail = "xxppss2008@yahoo.com.cn";
		email.setEmail(mail);
		emailReq.setEmailDTO(email);
		
		// Communication Preference
		List <CommunicationPreferencesRequestDTO> lcprd = new ArrayList<CommunicationPreferencesRequestDTO>();
		
//		for (int i = 1; i <= 5; i++) {
			CommunicationPreferencesRequestDTO comPrefrequest = new CommunicationPreferencesRequestDTO();
			com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
			comPref.setDomain("S");
			comPref.setCommunicationGroupeType("N");
			comPref.setCommunicationType("AF");
			comPref.setOptIn("N");
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			try {
				comPref.setDateOfConsent(df.parse("03-08-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set date of consent");
			}
			comPref.setSubscriptionChannel("B2C Home Page");
	
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setLanguage("FR");
			ml.setMarket("FR");
			ml.setOptIn("N");
			try {
				ml.setDateOfConsent(df.parse("01-09-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set market language date of consent");
			}
	
			List <MarketLanguageDTO> lml = new ArrayList <MarketLanguageDTO>();
			lml.add(ml);
			
			comPref.setMarketLanguageDTO(lml);
			comPrefrequest.setCommunicationPreferencesDTO(comPref);
				
			lcprd.add(comPrefrequest);
		
//		}
		
		request.setCommunicationPreferencesRequestDTO(lcprd);

		
		// AlertDTO
		AlertRequestDTO alertRequest = new AlertRequestDTO();
		AlertDTO alert = new AlertDTO();
		alert.setType("P");
		alert.setOptin("N");
		/* origin */
		AlertDataDTO orig = new AlertDataDTO();
		orig.setKey("ORIGIN");
		orig.setValue("PAR");
		
		List <AlertDataDTO> ladd = new ArrayList<AlertDataDTO>();
		ladd.add(orig);
		alert.setAlertDataDTO(ladd);
		/* origin type */
		AlertDataDTO origType = new AlertDataDTO();
		origType.setKey("ORIGIN_TYPE");
		origType.setValue("C");
		alert.getAlertDataDTO().add(origType);
		/* destination */
		AlertDataDTO dest = new AlertDataDTO();
		dest.setKey("DESTINATION");
		dest.setValue("LAX");
		alert.getAlertDataDTO().add(dest);
		/* destination type */
		AlertDataDTO destType = new AlertDataDTO();
		destType.setKey("DESTINATION_TYPE");
		destType.setValue("A");
		alert.getAlertDataDTO().add(destType);
		/* start date */
		AlertDataDTO sDate = new AlertDataDTO();
		sDate.setKey("START_DATE");
		sDate.setValue("01012016");
		alert.getAlertDataDTO().add(sDate);
		/* end date */
		AlertDataDTO eDate = new AlertDataDTO();
		eDate.setKey("END_DATE");
		eDate.setValue("31122016");
		alert.getAlertDataDTO().add(eDate);
		/* cabin */
		AlertDataDTO cabin = new AlertDataDTO();
		cabin.setKey("CABIN");
		cabin.setValue("W");
		alert.getAlertDataDTO().add(cabin);

		List <AlertDTO> lad = new ArrayList<AlertDTO>();
		lad.add(alert);
		alertRequest.setAlertDTO(lad);

		List <EmailRequestDTO> lerd = new ArrayList<EmailRequestDTO>();
		lerd.add(emailReq);
		request.setEmailRequestDTO(lerd);		
		request.setRequestorDTO(requestor);
				
		request.setAlertRequestDTO(alertRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		
		try {		
			response = createOrUpdateDS.createOrUpdateV8(request, response);
			Assert.fail("On devrait avoir une Individual not found");
		} catch (JrafException ex) {
			Assert.assertNotNull(ex);
			Assert.assertEquals("Individual not found: Deleted prospect", ex.getMessage());
		}

		CreateOrUpdateAnIndividualDSTest.logger.info("End of test. ");
	}
	
	@Test
	public void testCreateOrUpdateIndividual_updateIndividualDataWhiteWinger_DeletedProspect_NotFound() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateOrUpdateAnIndividualDSTest.logger.info("Test start... ");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		request.setProcess(ProcessEnum.W.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Individual
		IndividualRequestDTO indReq = new IndividualRequestDTO();
		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();

		indInfo.setIdentifier("999999999999");
		indReq.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indReq);

		// Email
		EmailRequestDTO emailReq = new EmailRequestDTO();
		com.airfrance.repind.dto.ws.EmailDTO email = new com.airfrance.repind.dto.ws.EmailDTO();
		String mail = "xxppss2008@yahoo.com.cn";
		email.setEmail(mail);
		emailReq.setEmailDTO(email);
		
		// Communication Preference
		List <CommunicationPreferencesRequestDTO> lcprd = new ArrayList<CommunicationPreferencesRequestDTO>();
		
//		for (int i = 1; i <= 5; i++) {
			CommunicationPreferencesRequestDTO comPrefrequest = new CommunicationPreferencesRequestDTO();
			com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
			comPref.setDomain("S");
			comPref.setCommunicationGroupeType("N");
			comPref.setCommunicationType("AF");
			comPref.setOptIn("N");
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			try {
				comPref.setDateOfConsent(df.parse("03-08-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set date of consent");
			}
			comPref.setSubscriptionChannel("B2C Home Page");
	
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setLanguage("FR");
			ml.setMarket("FR");
			ml.setOptIn("N");
			try {
				ml.setDateOfConsent(df.parse("01-09-2016"));
			} catch (ParseException e) {
				CreateOrUpdateAnIndividualDSTest.logger.error("unable to set market language date of consent");
			}
	
			List <MarketLanguageDTO> lml = new ArrayList <MarketLanguageDTO>();
			lml.add(ml);
			
			comPref.setMarketLanguageDTO(lml);
			comPrefrequest.setCommunicationPreferencesDTO(comPref);
				
			lcprd.add(comPrefrequest);
		
//		}
		
		request.setCommunicationPreferencesRequestDTO(lcprd);

		
		// AlertDTO
		AlertRequestDTO alertRequest = new AlertRequestDTO();
		AlertDTO alert = new AlertDTO();
		alert.setType("P");
		alert.setOptin("N");
		/* origin */
		AlertDataDTO orig = new AlertDataDTO();
		orig.setKey("ORIGIN");
		orig.setValue("PAR");
		
		List <AlertDataDTO> ladd = new ArrayList<AlertDataDTO>();
		ladd.add(orig);
		alert.setAlertDataDTO(ladd);
		/* origin type */
		AlertDataDTO origType = new AlertDataDTO();
		origType.setKey("ORIGIN_TYPE");
		origType.setValue("C");
		alert.getAlertDataDTO().add(origType);
		/* destination */
		AlertDataDTO dest = new AlertDataDTO();
		dest.setKey("DESTINATION");
		dest.setValue("LAX");
		alert.getAlertDataDTO().add(dest);
		/* destination type */
		AlertDataDTO destType = new AlertDataDTO();
		destType.setKey("DESTINATION_TYPE");
		destType.setValue("A");
		alert.getAlertDataDTO().add(destType);
		/* start date */
		AlertDataDTO sDate = new AlertDataDTO();
		sDate.setKey("START_DATE");
		sDate.setValue("01012016");
		alert.getAlertDataDTO().add(sDate);
		/* end date */
		AlertDataDTO eDate = new AlertDataDTO();
		eDate.setKey("END_DATE");
		eDate.setValue("31122016");
		alert.getAlertDataDTO().add(eDate);
		/* cabin */
		AlertDataDTO cabin = new AlertDataDTO();
		cabin.setKey("CABIN");
		cabin.setValue("W");
		alert.getAlertDataDTO().add(cabin);

		List <AlertDTO> lad = new ArrayList<AlertDTO>();
		lad.add(alert);
		alertRequest.setAlertDTO(lad);

		List <EmailRequestDTO> lerd = new ArrayList<EmailRequestDTO>();
		lerd.add(emailReq);
		request.setEmailRequestDTO(lerd);		
		request.setRequestorDTO(requestor);
				
		request.setAlertRequestDTO(alertRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		
		try {		
			response = createOrUpdateDS.createOrUpdateV8(request, response);
			Assert.fail("On devrait avoir une MaximumSubscriptionsException");
		} catch (JrafException ex) {
			Assert.assertNotNull(ex);
			Assert.assertEquals("Individual not found: Individual not found", ex.getMessage());
		}

		CreateOrUpdateAnIndividualDSTest.logger.info("End of test. ");
	}
	

	@Test
	public void testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KL_MultipleGIN() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CreateOrUpdateAnIndividualDSTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualDSTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualDSTest.SIGNATURE);

		request.setRequestorDTO(requestor);

		// Individual Request Bloc
		IndividualRequestDTO indRequest = new IndividualRequestDTO();

		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("SOCIAL" + generateString());
		indInfo.setFirstNameSC("SOCIAL" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indRequest);

		// Social
		ExternalIdentifierRequestDTO eir = new ExternalIdentifierRequestDTO();
		ExternalIdentifierDTO ei = new ExternalIdentifierDTO();
		ei.setIdentifier("sefjnjgn_dubef_5618614_fjbgv");
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifierDTO(ei);

		ExternalIdentifierDataDTO eid = new ExternalIdentifierDataDTO();
		eid.setKey("USED_BY_KL");
		eid.setValue("Y");
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

		Assert.assertTrue(response.getGin().startsWith("92"));

		Assert.assertNotNull(response.getInformationResponse());
		Assert.assertNotEquals(0, response.getInformationResponse());


		CreateOrUpdateAnIndividualDSTest.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KL " + response.getGin());
	}
}
