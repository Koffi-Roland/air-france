package com.afklm.repind.v7.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v7.createorupdateindividualws.CreateOrUpdateIndividualImplV7;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000442.v7.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v7.request.Delegator;
import com.afklm.soa.stubs.w000442.v7.request.*;
import com.afklm.soa.stubs.w000442.v7.response.BusinessError;
import com.afklm.soa.stubs.w000442.v7.response.BusinessErrorBloc;
import com.afklm.soa.stubs.w000442.v7.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000442.v7.siccommonenum.LanguageCodesEnum;
import com.afklm.soa.stubs.w000442.v7.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.*;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.CivilityEnum;
import com.airfrance.ref.type.GenderEnum;
import com.airfrance.ref.type.MediumCodeEnum;
import com.airfrance.ref.type.ProcessEnum;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.individu.MarketLanguageRepository;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
class CreateOrUpdateAnIndividualImplV7Test {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV7Test.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String SIGN_MODIF = "T730890";
	private static final String MAIL = "testloha@af.ri";
	private static final String CONTEXT = "B2C_HOME_PAGE";
	private static final String APP_CODE = "ISI";
	
	/** the Entity Manager*/
	@PersistenceContext(unitName="entityManagerFactoryRepind")
	private EntityManager entityManager;

	@Autowired
	private MarketLanguageRepository marketLanguageRepository;
	
	@Autowired
	private IndividuRepository individuRepository;
		
	@Autowired
	private CommunicationPreferencesDS comPrefDS;
	
	@Autowired
	private CommunicationPreferencesRepository communicationPreferencesRepository;
	
	@Autowired
	private EmailDS emailDS;

	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v7Bean")
	private CreateOrUpdateIndividualImplV7 createOrUpdateIndividualImplV7;

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
		/*		
		char[] chars = "0123456789".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random rd = new Random();
		sb.append("0");
		for (int i = 0; i < 9; ++i) {
			char c = chars[rd.nextInt(chars.length)];
			sb.append(c);
		}

		return sb.toString();
		 */		
	}

	@Test
	void testCreateOrUpdateIndividual_simpleIndividu() throws BusinessErrorBlocBusinessException {

		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();
		// indRequest.setCivilian("MR");

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("PHILIPPOT"); 
		indInfo.setFirstNameSC("PATRICK");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assertions.assertNotNull(response);		
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		logger.info("GIN created : " + response.getGin());

		logger.info("Test stop.");
	}



	@Test
	void testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KL() throws BusinessErrorBlocBusinessException {
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
		ei.setIdentifier("NumeroIDGIGYA_KL_1234567_" + generatePhone());
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifier(ei);

		ExternalIdentifierData eid = new ExternalIdentifierData();
		eid.setKey("USED_BY_KL");
		eid.setValue("Y");
		eir.getExternalIdentifierData().add(eid);

		request.getExternalIdentifierRequest().add(eir);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assertions.assertNotNull(response);		
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		Assertions.assertTrue(response.getGin().startsWith("92"));

		Assertions.assertNotNull(response.getInformationResponse());
		Assertions.assertNotEquals(0, response.getInformationResponse());


		logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KL " + response.getGin());
	}

	@Test
	void testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_AF() throws BusinessErrorBlocBusinessException {
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
		ei.setIdentifier("NumeroIDGIGYA_AF_1234567_" + generatePhone());
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifier(ei);

		ExternalIdentifierData eid = new ExternalIdentifierData();
		eid.setKey("USED_BY_AF");
		eid.setValue("Y");
		eir.getExternalIdentifierData().add(eid);

		request.getExternalIdentifierRequest().add(eir);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assertions.assertNotNull(response);		
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		Assertions.assertTrue(response.getGin().startsWith("92"));

		Assertions.assertNotNull(response.getInformationResponse());
		Assertions.assertNotEquals(0, response.getInformationResponse());


		logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_AF " + response.getGin());
	}

	// Fix regression REPIND-1587
	@Test
	@Transactional
	@Rollback(true)
	void testCreateOrUpdateIndividual_CivilityNull()
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

		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());

	}

	// Fix regression REPIND-1587
	@Test
	@Rollback(true)
	void testCreateOrUpdateIndividual_CivilityMPointIgnoreCase()
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
		indInfo.setCivility("M.");
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());

	}
	@Transactional
	@Rollback(true)
	void testCreateOrUpdateIndividual_CivilityIgnoreCase()
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

		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());

	}


	@Test
	void testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KLM_AF() throws BusinessErrorBlocBusinessException {
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
		ei.setIdentifier("NumeroIDGIGYA_AFKL_1234567_" + generatePhone());
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifier(ei);

		ExternalIdentifierData eid = new ExternalIdentifierData();
		eid.setKey("USED_BY_AF");
		eid.setValue("Y");
		eir.getExternalIdentifierData().add(eid);

		ExternalIdentifierData eid_KL = new ExternalIdentifierData();
		eid_KL.setKey("USED_BY_KL");
		eid_KL.setValue("Y");
		eir.getExternalIdentifierData().add(eid_KL);

		request.getExternalIdentifierRequest().add(eir);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assertions.assertNotNull(response);		
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		Assertions.assertTrue(response.getGin().startsWith("92"));

		Assertions.assertNotNull(response.getInformationResponse());
		Assertions.assertNotEquals(0, response.getInformationResponse());


		logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KLM_AF " + response.getGin());
	}

	@Test
	void testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KLM_AF_DIFF() throws BusinessErrorBlocBusinessException {
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
		ei.setIdentifier("NumeroIDGIGYA_AFKL_1234567_2_" + generatePhone());
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifier(ei);

		ExternalIdentifierData eid = new ExternalIdentifierData();
		eid.setKey("USED_BY_AF");
		eid.setValue("Y");
		eir.getExternalIdentifierData().add(eid);

		// Social 2		
		ExternalIdentifierRequest eir2 = new ExternalIdentifierRequest();
		ExternalIdentifier ei2 = new ExternalIdentifier();
		ei2.setIdentifier("NumeroIDGIGYA_AFKL_1234567_1_" + generatePhone());
		ei2.setType("GIGYA_ID");
		eir2.setExternalIdentifier(ei2);

		ExternalIdentifierData eid_KL2 = new ExternalIdentifierData();
		eid_KL2.setKey("USED_BY_KL");
		eid_KL2.setValue("Y");
		eir2.getExternalIdentifierData().add(eid_KL2);

		request.getExternalIdentifierRequest().add(eir2);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assertions.assertNotNull(response);		
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		Assertions.assertTrue(response.getGin().startsWith("92"));

		Assertions.assertNotNull(response.getInformationResponse());
		Assertions.assertNotEquals(0, response.getInformationResponse());


		logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KLM_AF_DIFF " + response.getGin());
	}

	@Test
	void testCreateOrUpdateIndividual_ExternalIdentifier_FACEBOOK() throws BusinessErrorBlocBusinessException {
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
		indInfo.setLastNameSC("SOCFB" + generateString());
		indInfo.setFirstNameSC("SOCFB" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Social
		ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
		ExternalIdentifier ei = new ExternalIdentifier();
		ei.setIdentifier("NumeroIDFACEBOOK_123456789101112");
		ei.setType("FACEBOOK_ID");
		eir.setExternalIdentifier(ei);

		request.getExternalIdentifierRequest().add(eir);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assertions.assertNotNull(response);		
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		Assertions.assertTrue(response.getGin().startsWith("92"));

		Assertions.assertNotNull(response.getInformationResponse());
		Assertions.assertNotEquals(0, response.getInformationResponse());


		logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_FACEBOOK " + response.getGin());
	}

	@Test
	void testCreateOrUpdateIndividual_ExternalIdentifier_TWITTER() throws BusinessErrorBlocBusinessException {
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
		indInfo.setLastNameSC("SOCTWI" + generateString());
		indInfo.setFirstNameSC("SOCTWI" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Social
		ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
		ExternalIdentifier ei = new ExternalIdentifier();
		ei.setIdentifier("NumeroIDTWITTER_123456789101112");
		ei.setType("TWITTER_ID");
		eir.setExternalIdentifier(ei);

		request.getExternalIdentifierRequest().add(eir);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assertions.assertNotNull(response);		
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		Assertions.assertTrue(response.getGin().startsWith("92"));

		Assertions.assertNotNull(response.getInformationResponse());
		Assertions.assertNotEquals(0, response.getInformationResponse());


		logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_TWITTER " + response.getGin());
	}

	@Test
	void testCreateOrUpdateIndividual_ExternalIdentifier_GigyaOnly() throws BusinessErrorBlocBusinessException {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		//		IndividualRequest indRequest = new IndividualRequest();

		//		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		//		indInfo.setCivility("MR");
		//		indInfo.setLastNameSC("SOCTWI" + generateString());
		//		indInfo.setFirstNameSC("SOCTWI" + generateString());
		//		indInfo.setStatus("V");
		//		
		//		indRequest.setIndividualInformations(indInfo);
		//		request.setIndividualRequest(indRequest);

		// Social
		ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
		ExternalIdentifier ei = new ExternalIdentifier();
		ei.setIdentifier("NumeroIDGIGYA_KL_" + generatePhone() + "_");
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifier(ei);

		ExternalIdentifierData eid = new ExternalIdentifierData();
		eid.setKey("USED_BY_KL");
		eid.setValue("Y");
		ExternalIdentifierData eid2 = new ExternalIdentifierData();
		eid2.setKey("USED_BY_AF");
		eid2.setValue("N");
		eir.getExternalIdentifierData().add(eid);
		eir.getExternalIdentifierData().add(eid2);

		request.getExternalIdentifierRequest().add(eir);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assertions.assertNotNull(response);		
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		Assertions.assertTrue(response.getGin().startsWith("92"));

		Assertions.assertNotNull(response.getInformationResponse());
		Assertions.assertNotEquals(0, response.getInformationResponse());


		logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_Gigya Only " + response.getGin());
	}
	// TODO : Mesurer l unicite du GIGYA sur ExternalIdentifier ou plutot SocialNetwork

	@Test
	void testCreateOrUpdateIndividual_ExternalIdentifier_UpdateExternalGenderProvided() throws BusinessErrorBlocBusinessException {

		CreateUpdateIndividualRequest request = buildRequestExternal();
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		entityManager.flush();
		entityManager.clear();

		String gin = response.getGin();

		/* -- UPDATE -- */
		CreateUpdateIndividualRequest requestUpdate = new CreateUpdateIndividualRequest();
		requestUpdate.setProcess(ProcessEnum.E.getCode());

		IndividualRequest indRequestUpdate = new IndividualRequest();
		IndividualInformationsV3 indInfoUpdate = new IndividualInformationsV3();
		indInfoUpdate.setIdentifier(response.getGin());
		indInfoUpdate.setGender(GenderEnum.MALE.toString());
		indRequestUpdate.setIndividualInformations(indInfoUpdate);

		requestUpdate.setRequestor(request.getRequestor());
		requestUpdate.setIndividualRequest(indRequestUpdate);

		// Execute test
		response = createOrUpdateIndividualImplV7.createIndividual(requestUpdate);
		Individu ind = individuRepository.findBySgin(gin);

		Assertions.assertEquals(GenderEnum.MALE.toString(), ind.getSexe());
		Assertions.assertEquals(CivilityEnum.MISTER.toString(), ind.getCivilite());
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());

		logger.info("Gin updated: " + gin);
		logger.info("End of test");
	}

	@Test
	void testCreateOrUpdateIndividual_ExternalIdentifier_UpdateExternalCivilityProvided() throws BusinessErrorBlocBusinessException {

		CreateUpdateIndividualRequest request = buildRequestExternal();
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		entityManager.flush();
		entityManager.clear();

		String gin = response.getGin();

		/* -- UPDATE -- */
		CreateUpdateIndividualRequest requestUpdate = new CreateUpdateIndividualRequest();
		requestUpdate.setProcess(ProcessEnum.E.getCode());

		IndividualRequest indRequestUpdate = new IndividualRequest();
		IndividualInformationsV3 indInfoUpdate = new IndividualInformationsV3();
		indInfoUpdate.setIdentifier(response.getGin());
		indInfoUpdate.setCivility(CivilityEnum.MISTER.toString());
		indRequestUpdate.setIndividualInformations(indInfoUpdate);

		requestUpdate.setRequestor(request.getRequestor());
		requestUpdate.setIndividualRequest(indRequestUpdate);

		// Execute test
		response = createOrUpdateIndividualImplV7.createIndividual(requestUpdate);
		Individu ind = individuRepository.findBySgin(gin);

		Assertions.assertEquals(GenderEnum.MALE.toString(), ind.getSexe());
		Assertions.assertEquals(CivilityEnum.MISTER.toString(), ind.getCivilite());
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());

		logger.info("Gin updated: " + gin);
		logger.info("End of test");
	}

	@Test
	void testCreateOrUpdateIndividual_Prospect_CreateNewProspect() throws BusinessErrorBlocBusinessException {

		logger.info("Test start... ");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect

		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setContext("B2C_HOME_PAGE");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Email
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();

		email.setEmail(MAIL);
		emailRequest.setEmail(email);

		// Communication Preference
		ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
		CommunicationPreferences comPref = new CommunicationPreferences();
		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("AF");
		comPref.setOptIn("N");
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			comPref.setDateOfConsent(df.parse("30-08-2016"));
		} catch (ParseException e) {
			logger.error("unable to set date of consent");
		}
		comPref.setSubscriptionChannel("B2C Home Page");

		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.FR);
		ml.setMarket("FR");
		ml.setOptIn("N");
		try {
			ml.setDateOfConsent(df.parse("01-09-2016"));
		} catch (ParseException e) {
			logger.error("unable to set market language date of consent");
		}

		comPref.getMarketLanguage().add(ml);
		comPrefrequest.setCommunicationPreferences(comPref);

		request.setRequestor(requestor);
		request.getEmailRequest().add(emailRequest);
		request.getComunicationPreferencesRequest().add(comPrefrequest);

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	void testCreateOrUpdateIndividual_Prospect_UpdateExistingIndividual() throws BusinessErrorBlocBusinessException {

		logger.info("Test start... ");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect

		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setContext("B2C_HOME_PAGE");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");
		requestor.setLoggedGin("400424668522");

		// Email
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();

		email.setEmail("t.loharano@gmail.com");
		emailRequest.setEmail(email);

		// Communication Preference
		ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
		CommunicationPreferences comPref = new CommunicationPreferences();
		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("AF");
		comPref.setOptIn("N");
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			comPref.setDateOfConsent(df.parse("30-08-2016"));
		} catch (ParseException e) {
			logger.error("unable to set date of consent");
		}
		comPref.setSubscriptionChannel("B2C Home Page");

		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.FR);
		ml.setMarket("FR");
		ml.setOptIn("N");
		try {
			ml.setDateOfConsent(df.parse("01-09-2016"));
		} catch (ParseException e) {
			logger.error("unable to set market language date of consent");
		}

		comPref.getMarketLanguage().add(ml);
		comPrefrequest.setCommunicationPreferences(comPref);

		request.setRequestor(requestor);
		request.getEmailRequest().add(emailRequest);
		request.getComunicationPreferencesRequest().add(comPrefrequest);

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());

		logger.info("End of test");
	}

	@Test
	void testCreateOrUpdateIndividual_ExternalIdentifier_GigyaOnlyUsingTheSameKey() throws BusinessErrorBlocBusinessException {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		//		IndividualRequest indRequest = new IndividualRequest();

		//		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		//		indInfo.setCivility("MR");
		//		indInfo.setLastNameSC("SOCTWI" + generateString());
		//		indInfo.setFirstNameSC("SOCTWI" + generateString());
		//		indInfo.setStatus("V");
		//		
		//		indRequest.setIndividualInformations(indInfo);
		//		request.setIndividualRequest(indRequest);

		// Social
		ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
		ExternalIdentifier ei = new ExternalIdentifier();
		ei.setIdentifier("NumeroIDGIGYA_KL_12345478_" + generateString());
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifier(ei);

		ExternalIdentifierData eid = new ExternalIdentifierData();
		eid.setKey("USED_BY_KL");
		eid.setValue("Y");
		ExternalIdentifierData eid2 = new ExternalIdentifierData();
		eid2.setKey("USED_BY_AF");
		eid2.setValue("N");
		eir.getExternalIdentifierData().add(eid);
		eir.getExternalIdentifierData().add(eid2);

		request.getExternalIdentifierRequest().add(eir);

		// WS call
		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

			Assertions.assertNotNull(response);
			Assertions.assertNotNull(response.getGin());
			Assertions.assertTrue(response.getGin().startsWith("92"));

		} catch (BusinessErrorBlocBusinessException exc) {
			Assertions.fail("ERROR : " + exc.getMessage());
		}
		// Tests
	}

	@Test
	void testCreateOrUpdateIndividual_UpdateExternalIdentifier_On_ExistingIndividual() throws BusinessErrorBlocBusinessException {
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
		ei.setIdentifier("NumeroIDGIGYA_AF_1234567_" + generatePhone());
		ei.setType("GIGYA_ID");
		eir.setExternalIdentifier(ei);

		ExternalIdentifierData eid = new ExternalIdentifierData();
		eid.setKey("USED_BY_AF");
		eid.setValue("Y");
		eir.getExternalIdentifierData().add(eid);

		request.getExternalIdentifierRequest().add(eir);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assertions.assertNotNull(response);		
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		Assertions.assertTrue(response.getGin().startsWith("92"));

		Assertions.assertNotNull(response.getInformationResponse());
		Assertions.assertNotEquals(0, response.getInformationResponse());


		logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_AF " + response.getGin());
	}


	@Test
	@Transactional
	@Rollback(true)
	void testCreateOrUpdateIndividual_CreateDelegationLinkWithInformationCodeNull() throws BusinessErrorBlocBusinessException {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("B2B");
		requestor.setToken("WSSiC2");

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("400622316554");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Social
		AccountDelegationDataRequest accountDelegationDataRequest = new AccountDelegationDataRequest();

		Delegator delegator = new Delegator();
		DelegationData delegationData = new DelegationData();
		delegationData.setGin("110000315411");
		delegationData.setDelegationAction("A");
		delegationData.setDelegationType("UM");
		delegator.setDelegationData(delegationData);

		accountDelegationDataRequest.getDelegator().add(delegator);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assertions.assertNotNull(response);		
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());

		Assertions.assertNotNull(response.getInformationResponse());
		Assertions.assertEquals(0, response.getInformationResponse().size());

		logger.info("testCreateOrUpdateIndividual_CreateDelegationLinkWithInformationCodeNull");
	}

	@Test
	@Ignore // select * from sic2.REF_PREFERENCE_VALUE; select * from sic2.REF_PREFERENCE_KEY;
	void testCreateOrUpdateIndividual_Preferences_UpdateExistingIndividual() throws BusinessErrorBlocBusinessException {

		logger.info("Test start... ");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");
		request.setRequestor(requestor);

		// Individual to update 
		IndividualRequest reqInd = new IndividualRequest();
		IndividualInformationsV3 indInf = new IndividualInformationsV3();
		indInf.setIdentifier("400491922886");
		reqInd.setIndividualInformations(indInf);
		request.setIndividualRequest(reqInd);

		// Preferences
		Preference preference = new Preference();
		PreferenceData prefData = new PreferenceData();
		prefData.setKey("ACTIVITYPATTERN");
		prefData.setValue("TEST");
		preference.setTypePreference("ULO");
		preference.getPreferenceData().add(prefData);

		// Update
		PreferenceDataRequest prefDataReq = new PreferenceDataRequest();
		prefDataReq.getPreference().add(preference);
		request.setPreferenceDataRequest(prefDataReq);

		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());

		logger.info("End of test. ");
	}

	@Test
	void testCreateOrUpdateIndividual_Alert_UpdateProspectWithGIN() throws BusinessErrorBlocBusinessException {
		logger.info("Test start... ");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setProcess(ProcessEnum.A.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Individual to update 
		IndividualRequest reqInd = new IndividualRequest();
		IndividualInformationsV3 indInf = new IndividualInformationsV3();
		indInf.setIdentifier("400622215824");
		reqInd.setIndividualInformations(indInf);

		// Communication Preference
		ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
		CommunicationPreferences comPref = new CommunicationPreferences();
		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("P");
		comPref.setCommunicationType("AF");
		comPref.setOptIn("N");
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			comPref.setDateOfConsent(df.parse("30-08-2016"));
		} catch (ParseException e) {
			logger.error("unable to set date of consent");
		}
		comPref.setSubscriptionChannel("B2C Home Page");

		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.FR);
		ml.setMarket("FR");
		ml.setOptIn("N");
		try {
			ml.setDateOfConsent(df.parse("01-09-2016"));
		} catch (ParseException e) {
			logger.error("unable to set market language date of consent");
		}

		comPref.getMarketLanguage().add(ml);
		comPrefrequest.setCommunicationPreferences(comPref);

		// Alert
		AlertRequest alertRequest = new AlertRequest();
		Alert alert = new Alert();
		alert.setType("P");
		alert.setOptIn("N");
		/* origin */
		AlertData orig = new AlertData();
		orig.setKey("ORIGIN");
		orig.setValue("CDG");
		alert.getAlertData().add(orig);
		AlertData origEnr = new AlertData();
		origEnr.setKey("ORIGIN_ENR");
		origEnr.setValue("CDG");
		alert.getAlertData().add(origEnr);
		/* origin type */
		AlertData origType = new AlertData();
		origType.setKey("ORIGIN_TYPE");
		origType.setValue("A");
		alert.getAlertData().add(origType);
		/* destination */
		AlertData dest = new AlertData();
		dest.setKey("DESTINATION");
		dest.setValue("NCE");
		alert.getAlertData().add(dest);
		/* destination type */
		AlertData destType = new AlertData();
		destType.setKey("DESTINATION_TYPE");
		destType.setValue("C");
		alert.getAlertData().add(destType);
		/* start date */
		AlertData sDate = new AlertData();
		sDate.setKey("START_DATE");
		sDate.setValue("01102016");
		alert.getAlertData().add(sDate);
		/* end date */
		AlertData eDate = new AlertData();
		eDate.setKey("END_DATE");
		eDate.setValue("31102016");
		alert.getAlertData().add(eDate);
		/* cabin */
		AlertData cabin = new AlertData();
		cabin.setKey("CABIN");
		cabin.setValue("P");
		alert.getAlertData().add(cabin);

		alertRequest.getAlert().add(alert);

		request.setIndividualRequest(reqInd);
		request.setRequestor(requestor);
		request.getComunicationPreferencesRequest().add(comPrefrequest);
		request.setAlertRequest(alertRequest);

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());

		logger.info("End of test. ");
	}

	@Test
	void testCreateOrUpdateIndividual_Alert_CreatProspectWithMail() throws BusinessErrorBlocBusinessException {
		logger.info("Test start... ");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setProcess(ProcessEnum.A.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");


		// Email
		EmailRequest emailReq = new EmailRequest();
		Email email = new Email();
		String mail = generateEmail();
		//		Random rd = new Random();
		//		int randomInt = rd.nextInt(1000);

		//		mail.append("tualert");
		//		mail.append(Integer.toString(randomInt));
		//		mail.append("@yopmail.com");
		email.setEmail(mail);
		emailReq.setEmail(email);

		// Communication Preference
		ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
		CommunicationPreferences comPref = new CommunicationPreferences();
		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("P");
		comPref.setCommunicationType("AF");
		comPref.setOptIn("N");
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			comPref.setDateOfConsent(df.parse("30-08-2016"));
		} catch (ParseException e) {
			logger.error("unable to set date of consent");
		}
		comPref.setSubscriptionChannel("B2C Home Page");

		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.FR);
		ml.setMarket("FR");
		ml.setOptIn("N");
		try {
			ml.setDateOfConsent(df.parse("01-09-2016"));
		} catch (ParseException e) {
			logger.error("unable to set market language date of consent");
		}

		comPref.getMarketLanguage().add(ml);
		comPrefrequest.setCommunicationPreferences(comPref);

		// Alert
		AlertRequest alertRequest = new AlertRequest();
		Alert alert = new Alert();
		alert.setType("P");
		alert.setOptIn("N");
		/* origin */
		AlertData orig = new AlertData();
		orig.setKey("ORIGIN");
		orig.setValue("PAR");
		alert.getAlertData().add(orig);
		AlertData origEnr = new AlertData();
		origEnr.setKey("ORIGIN_ENR");
		origEnr.setValue("PAR");
		alert.getAlertData().add(origEnr);
		/* origin type */
		AlertData origType = new AlertData();
		origType.setKey("ORIGIN_TYPE");
		origType.setValue("C");
		alert.getAlertData().add(origType);
		/* destination */
		AlertData dest = new AlertData();
		dest.setKey("DESTINATION");
		dest.setValue("LAX");
		alert.getAlertData().add(dest);
		/* destination type */
		AlertData destType = new AlertData();
		destType.setKey("DESTINATION_TYPE");
		destType.setValue("A");
		alert.getAlertData().add(destType);
		/* start date */
		AlertData sDate = new AlertData();
		sDate.setKey("START_DATE");
		sDate.setValue("01012016");
		alert.getAlertData().add(sDate);
		/* end date */
		AlertData eDate = new AlertData();
		eDate.setKey("END_DATE");
		eDate.setValue("31122016");
		alert.getAlertData().add(eDate);
		/* cabin */
		AlertData cabin = new AlertData();
		cabin.setKey("CABIN");
		cabin.setValue("W");
		alert.getAlertData().add(cabin);

		alertRequest.getAlert().add(alert);

		request.getEmailRequest().add(emailReq);
		request.setRequestor(requestor);
		request.getComunicationPreferencesRequest().add(comPrefrequest);
		request.setAlertRequest(alertRequest);

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());
		Assertions.assertTrue(response.getGin().startsWith("90"));

		logger.info("End of test. ");
	}

	// BUG SURCOUF
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	void testCreateOrUpdateIndividual_SurcoufBUG() throws BusinessErrorBlocBusinessException {

		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setStatus("V");

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("SURCW");
		requestor.setToken("WSSiC2");
		requestor.setApplicationCode("MAC");
		requestor.setSite("QVI");
		requestor.setSignature("SURCOUFWEB");

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();
		// indRequest.setCivilian("MR");

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
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


		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assertions.assertNotNull(response);		
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		logger.info("GIN created : " + response.getGin());

		logger.info("Test stop.");
	}

	@Test
	void testCreateOrUpdateIndividual_CaractereCHINOIS() throws BusinessErrorBlocBusinessException {

		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setStatus("V");

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("SURCW");
		requestor.setToken("WSSiC2");
		requestor.setApplicationCode("MAC");
		requestor.setSite("QVI");
		requestor.setSignature("T412211");

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setBirthDate(new Date());
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("繁体字"); 
		indInfo.setFirstNameSC("齉");

		indRequest.setIndividualInformations(indInfo);		

		request.setIndividualRequest(indRequest);

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

		try {			
			// CreateUpdateIndividualResponse response = 
			createOrUpdateIndividualImplV7.createIndividual(request);

			Assertions.fail("On devrait avoir une erreur de caracteres invalid");

		} catch (BusinessErrorBlocBusinessException e) {

			Assertions.assertNotNull(e);
			BusinessErrorBloc beb = e.getFaultInfo();

			Assertions.assertNotNull(beb);
			BusinessError be = beb.getBusinessError();

			Assertions.assertNotNull(be);
			Assertions.assertNotNull(be.getErrorDetail() );
			Assertions.assertTrue(be.getErrorDetail().contains("Invalid character in lastname"));
		}

		logger.info("Test stop.");
	}

	// Tester la mise à jour d'une commPref d'un individu en conservant la date d'optin initiale 
		@Test
		@Transactional
		@Rollback(true)
		void testCreateOrUpdateIndividual_UpdateIndivGinExistingComPref() throws BusinessErrorBlocBusinessException, JrafDomainException {

			logger.info("Test start...");

			CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

			// Init data
			// Date = 01 Janvier 2010
			Calendar initDate = new GregorianCalendar(2010,	01, 01);
					
			Individu testIndividu = individuRepository.findBySgin("400622109881");
			com.airfrance.repind.entity.individu.CommunicationPreferences comPrefInit = new com.airfrance.repind.entity.individu.CommunicationPreferences();
			com.airfrance.repind.entity.individu.MarketLanguage ml = new com.airfrance.repind.entity.individu.MarketLanguage();
			
			comPrefInit.setGin("400622109881");
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
			ml.setCreationSite(SITE);

			Set<com.airfrance.repind.entity.individu.MarketLanguage> listMl = new HashSet<com.airfrance.repind.entity.individu.MarketLanguage>();
			listMl.add(ml);
			comPrefInit.setMarketLanguage(listMl);

			testIndividu.getCommunicationpreferences().add(comPrefInit);
			
			individuRepository.saveAndFlush(testIndividu);
			
			// Individual
			IndividualRequest indReq = new IndividualRequest();
			IndividualInformationsV3 indInfo = new IndividualInformationsV3();

			indInfo.setIdentifier("400424668522");
			indReq.setIndividualInformations(indInfo);
			request.setIndividualRequest(indReq);

			// Requestor
			RequestorV2 req = new RequestorV2();

			req.setChannel("B2C");
			req.setContext("FB_ENROLMENT");
			req.setApplicationCode("ISI");
			req.setSite("QVI");
			req.setSignature("Test U");
			request.setRequestor(req);

			// Communication preferences
			ComunicationPreferencesRequest comPrefReq = new ComunicationPreferencesRequest();
			CommunicationPreferences comPref = new CommunicationPreferences();

			comPref.setDomain("S");
			comPref.setCommunicationGroupeType("N");
			comPref.setCommunicationType("KL");
			comPref.setOptIn("Y");
			//comPref.setDateOfConsent(new Date());
			comPrefReq.setCommunicationPreferences(comPref);
			request.getComunicationPreferencesRequest().add(comPrefReq);

			// Execute test
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

			// Retrieve updated datas
			com.airfrance.repind.entity.individu.CommunicationPreferences comPref2 =  new com.airfrance.repind.entity.individu.CommunicationPreferences();
			comPref2.setGin("400622109881");
			comPref2.setDomain("S");
			comPref2.setComGroupType("N");
			comPref2.setComType("KL");
			List<com.airfrance.repind.entity.individu.CommunicationPreferences> listFound = communicationPreferencesRepository.findAll(Example.of(comPref2));
			com.airfrance.repind.entity.individu.CommunicationPreferences foundCP = listFound.get(0);
			// Test results
			Assertions.assertNotNull(response);
			Assertions.assertTrue(response.isSuccess());
			Assertions.assertEquals(1, listFound.size());
			Assertions.assertEquals("KL", foundCP.getComType());
			Assertions.assertEquals(initDate.getTime(), foundCP.getDateOptin());

		}

	// Tester la mise à jour d'une commPref d'un prospet sans conservation de la date d'optin initiale 
	@Test
	@Transactional
	@Rollback(true)
	void testCreateOrUpdateIndividual_UpdateProspectGinExistingComPref() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Init data
		// Date = 01 Janvier 2010
		Calendar initDate = new GregorianCalendar(2010,	01, 01);
				
		Individu testIndividu = individuRepository.findBySgin("900025086459");
		com.airfrance.repind.entity.individu.CommunicationPreferences comPrefInit = new com.airfrance.repind.entity.individu.CommunicationPreferences();
		com.airfrance.repind.entity.individu.MarketLanguage ml = new com.airfrance.repind.entity.individu.MarketLanguage();
		
		comPrefInit.setGin("900025086459");
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
		ml.setCreationSite(SITE);

		Set<com.airfrance.repind.entity.individu.MarketLanguage> listMl = new HashSet<com.airfrance.repind.entity.individu.MarketLanguage>();
		listMl.add(ml);
		comPrefInit.setMarketLanguage(listMl);

		testIndividu.getCommunicationpreferences().add(comPrefInit);
		
		individuRepository.saveAndFlush(testIndividu);
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();

		indInfo.setIdentifier("900025086459");
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);

		// Requestor
		RequestorV2 req = new RequestorV2();

		req.setChannel("B2C");
		req.setContext("B2C_HOME_PAGE");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U");
		request.setRequestor(req);

		// Communication preferences
		ComunicationPreferencesRequest comPrefReq = new ComunicationPreferencesRequest();
		CommunicationPreferences comPref = new CommunicationPreferences();

		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("KL");
		comPref.setOptIn("N");
		comPref.setDateOfConsent(new Date());
		comPref.setSubscriptionChannel("B2C");

		MarketLanguage marketLanguage = new MarketLanguage();
		marketLanguage.setDateOfConsent(new Date());
		marketLanguage.setLanguage(LanguageCodesEnum.FR);
		marketLanguage.setMarket("NL");
		marketLanguage.setOptIn("N");

		comPref.getMarketLanguage().add(marketLanguage);
		comPrefReq.setCommunicationPreferences(comPref);
		request.getComunicationPreferencesRequest().add(comPrefReq);

		// Process W for prospect
		request.setProcess("W");

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Retrieve updated datas
		com.airfrance.repind.entity.individu.CommunicationPreferences comPref2 =  new com.airfrance.repind.entity.individu.CommunicationPreferences();
		comPref2.setGin("900025086459");
		comPref2.setDomain("S");
		comPref2.setComGroupType("N");
		comPref2.setComType("KL");
		List<com.airfrance.repind.entity.individu.CommunicationPreferences> listFound = communicationPreferencesRepository.findAll(Example.of(comPref2));
		// CommunicationPreferencesDTO foundCP = listFound.get(0);

		// Test results
		Assertions.assertNotNull(response);
		Assertions.assertTrue(response.isSuccess());

		// Il y a deux ComPref aucune d elle n'a ete mise a jour...
		for (com.airfrance.repind.entity.individu.CommunicationPreferences cp : listFound) {
			Assertions.assertEquals("S", cp.getDomain());
			Assertions.assertEquals("N", cp.getComGroupType());
			Assertions.assertEquals("KL", cp.getComType());
			Assertions.assertNotEquals(initDate.getTime(), cp.getDateOptin());
		}
		// Assertions.assertEquals("KL", foundCP.getComType());
		// Assertions.assertNotEquals(initDate.getTime(), foundCP.getDateOptin());
	}

	// Tester la mise à jour d'une commPref d'un prospet en conservant la date d'optin initiale 
	@Test
	@Transactional
	@Rollback(true)
	void testCreateOrUpdateIndividual_UpdateProspectEmailExistingComPref() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

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
		ml.setCreationSite(SITE);

		Set<com.airfrance.repind.entity.individu.MarketLanguage> listMl = new HashSet<com.airfrance.repind.entity.individu.MarketLanguage>();
		listMl.add(ml);
		comPrefInit.setMarketLanguage(listMl);

		testIndividu.getCommunicationpreferences().add(comPrefInit);
		
		marketLanguageRepository.saveAndFlush(ml);
		// Individual
		EmailRequest emailReq = new EmailRequest();
		Email email = new Email();

		email.setEmail("t_loharano@msn.com");
		emailReq.setEmail(email);
		request.getEmailRequest().add(emailReq);

		// Requestor
		RequestorV2 req = new RequestorV2();

		req.setChannel("B2C");
		req.setContext("B2C_HOME_PAGE");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U");
		request.setRequestor(req);

		// Communication preferences
		ComunicationPreferencesRequest comPrefReq = new ComunicationPreferencesRequest();
		CommunicationPreferences comPref = new CommunicationPreferences();

		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("KL");
		comPref.setOptIn("Y");
		comPref.setDateOfConsent(new Date());
		comPref.setSubscriptionChannel("B2C");

		MarketLanguage marketLanguage = new MarketLanguage();
		marketLanguage.setDateOfConsent(new Date());
		marketLanguage.setLanguage(LanguageCodesEnum.FR);
		marketLanguage.setMarket("NL");
		marketLanguage.setOptIn("Y");

		comPref.getMarketLanguage().add(marketLanguage);
		comPrefReq.setCommunicationPreferences(comPref);
		request.getComunicationPreferencesRequest().add(comPrefReq);

		// Process W for prospect
		request.setProcess("W");

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Retrieve updated datas
		com.airfrance.repind.entity.individu.CommunicationPreferences comPref2 =  new com.airfrance.repind.entity.individu.CommunicationPreferences();
		comPref2.setGin("900025086459");
		comPref2.setDomain("S");
		comPref2.setComGroupType("N");
		comPref2.setComType("KL");
		List<com.airfrance.repind.entity.individu.CommunicationPreferences> listFound = communicationPreferencesRepository.findAll(Example.of(comPref2));
		// Test results
		Assertions.assertNotNull(response);
		Assertions.assertTrue(response.isSuccess());

		// Il y a deux ComPref il faut tester la bonne...
		for (com.airfrance.repind.entity.individu.CommunicationPreferences cp : listFound) {
			if ("B2C".equals(cp.getChannel())) {
				Assertions.assertEquals("S", cp.getDomain());
				Assertions.assertEquals("N", cp.getComGroupType());
				Assertions.assertEquals("KL", cp.getComType());
			} else {				
				Assertions.assertEquals("S", cp.getDomain());
				Assertions.assertEquals("N", cp.getComGroupType());
				Assertions.assertEquals("KL", cp.getComType());
				Assertions.assertEquals(initDate.getTime(), cp.getDateOptin());
			}
		}
	}

	@Test
	@Ignore
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	void testCreateOrUpdateIndividual_UpdateTelecomInvalid_OK() throws BusinessErrorBlocBusinessException {

		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setStatus("V");

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setToken("WSSiC2");
		requestor.setApplicationCode("B2C");
		requestor.setSite("QVI");
		requestor.setSignature("TU_REPIND");

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();
		// indRequest.setCivilian("MR");

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();

		indInfo.setIdentifier("110000727011");
		indInfo.setVersion("15");

		indRequest.setIndividualInformations(indInfo);		

		request.setIndividualRequest(indRequest);

		// Telecom
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setVersion("2");		
		telecom.setCountryCode("33");
		telecom.setMediumCode("D");
		telecom.setTerminalType("T");
		telecom.setPhoneNumber("0440174939");
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);


		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assertions.assertNotNull(response);		
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());

		logger.info("Test stop.");
	}

	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	void testCreateOrUpdateIndividual_UpdateTelecomValid_KO() throws BusinessErrorBlocBusinessException {

		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setStatus("V");

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setToken("WSSiC2");
		requestor.setApplicationCode("B2C");
		requestor.setSite("QVI");
		requestor.setSignature("TU_REPIND");

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();
		// indRequest.setCivilian("MR");

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();

		indInfo.setIdentifier("400000003685");
		indInfo.setVersion("15");

		indRequest.setIndividualInformations(indInfo);		

		request.setIndividualRequest(indRequest);

		// Telecom
		TelecomRequest telRequest = new TelecomRequest();
		Telecom telecom = new Telecom();
		telecom.setVersion("3");		
		telecom.setCountryCode("225");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");	
		telecom.setTerminalType("M");
		telecom.setPhoneNumber("48487557");
		telRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telRequest);

		BusinessErrorBlocBusinessException exceptionThrown = assertThrows(BusinessErrorBlocBusinessException.class, () -> createOrUpdateIndividualImplV7.createIndividual(request));
		Assertions.assertEquals(BusinessErrorCodeEnum.ERROR_702,exceptionThrown.getFaultInfo().getBusinessError().getErrorCode());
	}

	// REPIND-1288 : Store Email as Minus in database
	@Test
	void testCreateOrUpdateIndividual_Prospect_CreateNewProspectEmailUPPER() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect

		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setContext("B2C_HOME_PAGE");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Email
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();

		String emailUpper = generateEmail().toUpperCase();
		email.setEmail(emailUpper);
		emailRequest.setEmail(email);

		// Communication Preference
		ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
		CommunicationPreferences comPref = new CommunicationPreferences();
		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("AF");
		comPref.setOptIn("N");
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			comPref.setDateOfConsent(df.parse("30-08-2016"));
		} catch (ParseException e) {
			logger.error("unable to set date of consent");
		}
		comPref.setSubscriptionChannel("B2C Home Page");

		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.FR);
		ml.setMarket("FR");
		ml.setOptIn("N");
		try {
			ml.setDateOfConsent(df.parse("01-09-2016"));
		} catch (ParseException e) {
			logger.error("unable to set market language date of consent");
		}

		comPref.getMarketLanguage().add(ml);
		comPrefrequest.setCommunicationPreferences(comPref);

		request.setRequestor(requestor);
		request.getEmailRequest().add(emailRequest);
		request.getComunicationPreferencesRequest().add(comPrefrequest);

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		List<EmailDTO> listEmails = emailDS.findEmail(response.getGin());
		Assertions.assertNotEquals(emailUpper, listEmails.get(0).getEmail());		

		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	@Test
	void testCreateOrUpdateIndividual_Prospect_CreateNewTravelerEmailUPPER() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setProcess(ProcessEnum.T.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setContext("B2C_HOME_PAGE");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Individual
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC(generateString()); 
		indInfo.setFirstNameSC(generateString());

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String dateInString = "0014-10-05T12:00:00";
		Date date;
		try {
			date = formatter.parse(dateInString);
			indInfo.setBirthDate(date);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}

		indRequest.setIndividualInformations(indInfo);


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

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
		
		entityManager.flush();
		entityManager.clear();

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		List<EmailDTO> listEmails = emailDS.findEmail(response.getGin());
		Assertions.assertNotEquals(emailUpper, listEmails.get(0).getEmail());	

		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	@Test
	void testCreateOrUpdateIndividual_Prospect_CreateNewIndividualEmailUPPER() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setProcess(ProcessEnum.I.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setContext("B2C_HOME_PAGE");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Individual
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
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
			logger.error(e.getMessage());
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
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		List<EmailDTO> listEmails = emailDS.findEmail(response.getGin());
		Assertions.assertNotEquals(emailUpper, listEmails.get(0).getEmail());	

		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	@Test
	void testCreateOrUpdateIndividual_Prospect_UpdateIndividualEmailUPPER() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setProcess(ProcessEnum.I.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setContext("B2C_HOME_PAGE");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Individual
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
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
			logger.error(e.getMessage());
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
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		String gin = response.getGin();
		List<EmailDTO> listEmails = emailDS.findEmail(gin);
		Assertions.assertNotEquals(emailUpper, listEmails.get(0).getEmail());	

		/* -- UPDATE -- */
		CreateUpdateIndividualRequest requestUpdate = new CreateUpdateIndividualRequest();
		requestUpdate.setProcess(ProcessEnum.I.getCode());

		IndividualRequest indRequestUpdate = new IndividualRequest();
		IndividualInformationsV3 indInfoUpdate = new IndividualInformationsV3();
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
		response = createOrUpdateIndividualImplV7.createIndividual(requestUpdate);

		listEmails = emailDS.findEmail(gin);
		Assertions.assertNotEquals(emailUpperUpdate, listEmails.get(0).getEmail());	

		logger.info("Gin updated: " + gin);
		logger.info("End of test");
	}

	@Test
	void testCreateOrUpdateIndividual_Prospect_UpdateTravelerEmailUPPER() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setProcess(ProcessEnum.T.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setContext("B2C_HOME_PAGE");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Individual
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC(generateString()); 
		indInfo.setFirstNameSC(generateString());

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String dateInString = "0014-10-05T12:00:00";
		Date date;
		try {
			date = formatter.parse(dateInString);
			indInfo.setBirthDate(date);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}

		indRequest.setIndividualInformations(indInfo);


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

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		String gin = response.getGin();
		List<EmailDTO> listEmails = emailDS.findEmail(gin);
		Assertions.assertNotEquals(emailUpper, listEmails.get(0).getEmail());	

		/* -- UPDATE -- */
		CreateUpdateIndividualRequest requestUpdate = new CreateUpdateIndividualRequest();
		requestUpdate.setProcess(ProcessEnum.T.getCode());

		IndividualRequest indRequestUpdate = new IndividualRequest();
		IndividualInformationsV3 indInfoUpdate = new IndividualInformationsV3();
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
		response = createOrUpdateIndividualImplV7.createIndividual(requestUpdate);

		listEmails = emailDS.findEmail(gin);
		Assertions.assertNotEquals(emailUpperUpdate, listEmails.get(0).getEmail());	

		logger.info("Gin updated: " + gin);
		logger.info("End of test");
	}


	@Test
	void testCreateOrUpdateIndividual_Prospect_UpdateProspectGenderProvided() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");

		/* -- CREATE -- */
		CreateUpdateIndividualRequest request = buildRequestProspect();
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		entityManager.flush();
		entityManager.clear();

		String gin = response.getGin();

		/* -- UPDATE -- */
		CreateUpdateIndividualRequest requestUpdate = new CreateUpdateIndividualRequest();
		requestUpdate.setProcess(ProcessEnum.W.getCode());

		IndividualRequest indRequestUpdate = new IndividualRequest();
		IndividualInformationsV3 indInfoUpdate = new IndividualInformationsV3();
		indInfoUpdate.setIdentifier(response.getGin());
		indInfoUpdate.setGender(GenderEnum.FEMALE.toString());
		indRequestUpdate.setIndividualInformations(indInfoUpdate);


		requestUpdate.setRequestor(request.getRequestor());
		requestUpdate.setIndividualRequest(indRequestUpdate);

		// Execute test
		response = createOrUpdateIndividualImplV7.createIndividual(requestUpdate);
		Individu ind = individuRepository.findBySgin(gin);

		Assertions.assertEquals(GenderEnum.FEMALE.toString(), ind.getSexe());
		Assertions.assertEquals(CivilityEnum.MRS.toString(), ind.getCivilite());
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());


		logger.info("Gin updated: " + gin);
		logger.info("End of test");
	}

	@Test
	void testCreateOrUpdateIndividual_Prospect_UpdateProspectCivilityProvided() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");

		/* -- CREATE -- */
		CreateUpdateIndividualRequest request = buildRequestProspect();
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		entityManager.flush();
		entityManager.clear();

		String gin = response.getGin();

		/* -- UPDATE -- */
		CreateUpdateIndividualRequest requestUpdate = new CreateUpdateIndividualRequest();
		requestUpdate.setProcess(ProcessEnum.W.getCode());

		IndividualRequest indRequestUpdate = new IndividualRequest();
		IndividualInformationsV3 indInfoUpdate = new IndividualInformationsV3();
		indInfoUpdate.setIdentifier(response.getGin());
		indInfoUpdate.setCivility(CivilityEnum.MISTER.toString());
		indRequestUpdate.setIndividualInformations(indInfoUpdate);


		requestUpdate.setRequestor(request.getRequestor());
		requestUpdate.setIndividualRequest(indRequestUpdate);

		// Execute test
		response = createOrUpdateIndividualImplV7.createIndividual(requestUpdate);
		Individu ind = individuRepository.findBySgin(gin);

		Assertions.assertEquals(GenderEnum.MALE.toString(), ind.getSexe());
		Assertions.assertEquals(CivilityEnum.MISTER.toString(), ind.getCivilite());
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());


		logger.info("Gin updated: " + gin);
		logger.info("End of test");
	}


	@Test
	@Transactional
	void testCreateOrUpdateIndividual_Prospect_UpdateProspectEmailUPPER() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect

		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setContext("B2C_HOME_PAGE");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Email
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();

		String emailUpper = generateEmail().toUpperCase();
		email.setEmail(emailUpper);
		emailRequest.setEmail(email);

		// Communication Preference
		ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
		CommunicationPreferences comPref = new CommunicationPreferences();
		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("AF");
		comPref.setOptIn("N");
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			comPref.setDateOfConsent(df.parse("30-08-2016"));
		} catch (ParseException e) {
			logger.error("unable to set date of consent");
		}
		comPref.setSubscriptionChannel("B2C Home Page");

		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.FR);
		ml.setMarket("FR");
		ml.setOptIn("N");
		try {
			ml.setDateOfConsent(df.parse("01-09-2016"));
		} catch (ParseException e) {
			logger.error("unable to set market language date of consent");
		}

		comPref.getMarketLanguage().add(ml);
		comPrefrequest.setCommunicationPreferences(comPref);

		request.setRequestor(requestor);
		request.getEmailRequest().add(emailRequest);
		request.getComunicationPreferencesRequest().add(comPrefrequest);

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
		
		entityManager.flush();
		entityManager.clear();

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		String gin = response.getGin();
		List<EmailDTO> listEmails = emailDS.findEmail(gin);
		Assertions.assertNotEquals(emailUpper, listEmails.get(0).getEmail());

		/* -- UPDATE -- */
		CreateUpdateIndividualRequest requestUpdate = new CreateUpdateIndividualRequest();
		requestUpdate.setProcess(ProcessEnum.W.getCode());

		IndividualRequest indRequestUpdate = new IndividualRequest();
		IndividualInformationsV3 indInfoUpdate = new IndividualInformationsV3();
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
		response = createOrUpdateIndividualImplV7.createIndividual(requestUpdate);

		listEmails = emailDS.findEmail(gin);
		Assertions.assertNotEquals(emailUpperUpdate, listEmails.get(0).getEmail());	

		logger.info("Gin updated: " + gin);
		logger.info("End of test");
	}

	@Test
	void testCreateOrUpdateIndividual_Update_CommPref() throws BusinessErrorBlocBusinessException {

		logger.info("Test start... ");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		//			request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect
		// Individual
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("400363297643");
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setContext("B2C_HOME_PAGE");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Communication Preference
		ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
		CommunicationPreferences comPref = new CommunicationPreferences();
		comPref.setDomain("F");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("SB");
		comPref.setOptIn("Y");
		/*			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			try {
				comPref.setDateOfConsent(df.parse("30-08-2016"));
			} catch (ParseException e) {
				logger.error("unable to set date of consent");
			}
			comPref.setSubscriptionChannel("B2C Home Page");
		 */
		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.EN);
		ml.setMarket("AU");
		ml.setOptIn("Y");
		/*			
			try {
				ml.setDateOfConsent(df.parse("01-09-2016"));
			} catch (ParseException e) {
				logger.error("unable to set market language date of consent");
			}
		 */
		comPref.getMarketLanguage().add(ml);
		comPrefrequest.setCommunicationPreferences(comPref);

		request.setRequestor(requestor);
		//			request.getEmailRequest().add(emailRequest);
		request.getComunicationPreferencesRequest().add(comPrefrequest);

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());

		logger.info("End of test");
	}

	@Test
	@Transactional
	@Rollback(true)
	@Ignore // Le S08924 ne gere pas la transaction
	void testCreateOrUpdateIndividual_Update_Crisis() throws BusinessErrorBlocBusinessException {

		logger.info("Test start... ");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		//			request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect
		// Individual
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("400450427682");

		// indInfo.setBirthDate(new GregorianCalendar(1987, 06, 01).getTime());

		indInfo.setFirstNameSC("SAMIRA");
		indInfo.setLastNameSC("MARX PINHEIRO");
		indInfo.setStatus("X");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setContext("B2C_HOME_PAGE");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		request.setRequestor(requestor);
		//			request.getEmailRequest().add(emailRequest);

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());

		logger.info("End of test");
	}
	
	
	@Test
	void testCreateOrUpdateIndividual_CreateIndividualWithNonValidCivility() throws BusinessErrorBlocBusinessException, JrafDomainException{

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

		indInfo.setCivility("MAN");
		indInfo.setFirstNameSC("F" + generateString());
		indInfo.setLastNameSC("L" + generateString());		
		
		indRequest.setIndividualInformations(indInfo);
		
		IndividualProfilV3 ip = new IndividualProfilV3();
		ip.setLanguageCode("FR");
		
		indRequest.setIndividualProfil(ip);
		request.setIndividualRequest(indRequest);
		
		// WS call
		try {
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);		
			Assertions.fail("We should have an Exception due to CIVILITY");
			
		} catch (Exception e) {			
			
			BusinessErrorBlocBusinessException bebe = (BusinessErrorBlocBusinessException) e;			
			Assertions.assertNotNull(bebe.getFaultInfo());
			
			BusinessErrorBloc beb = bebe.getFaultInfo();			
			Assertions.assertNotNull(beb);

			BusinessError be = beb.getBusinessError();			
			Assertions.assertNotNull(be);
			Assertions.assertNotNull(be.getErrorCode());
			
			Assertions.assertEquals("ERROR_133", be.getErrorCode().toString());
			Assertions.assertEquals("MISSING PARAMETERS", be.getErrorLabel());
			Assertions.assertEquals("Missing parameter exception: The field civility not valid", be.getErrorDetail());
		}
	}

	@Test
	void testCreateOrUpdateIndividual_UpdateIndividualWithNonValidCivility() throws BusinessErrorBlocBusinessException, JrafDomainException{

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
		
		indInfo.setIdentifier("400509997306");
		indInfo.setCivility("MAN");
		
		indRequest.setIndividualInformations(indInfo);
		
		IndividualProfilV3 ip = new IndividualProfilV3();
		ip.setLanguageCode("FR");
		
		indRequest.setIndividualProfil(ip);
		request.setIndividualRequest(indRequest);
		
		// WS call
		try {
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);		
			Assertions.fail("We should have an Exception due to CIVILITY");
			
		} catch (Exception e) {			
			
			BusinessErrorBlocBusinessException bebe = (BusinessErrorBlocBusinessException) e;			
			Assertions.assertNotNull(bebe.getFaultInfo());
			
			BusinessErrorBloc beb = bebe.getFaultInfo();			
			Assertions.assertNotNull(beb);

			BusinessError be = beb.getBusinessError();			
			Assertions.assertNotNull(be);
			Assertions.assertNotNull(be.getErrorCode());
			
			Assertions.assertEquals("ERROR_133", be.getErrorCode().toString());
			Assertions.assertEquals("MISSING PARAMETERS", be.getErrorLabel());
			Assertions.assertEquals("Missing parameter exception: The field civility not valid", be.getErrorDetail());
		}
	}
	
	@Test
	void testCreateOrUpdateIndividual_CreateIndividualWithNonValidTitle() throws BusinessErrorBlocBusinessException, JrafDomainException{

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
		
		indInfo.setCivility("MISS");
		indInfo.setFirstNameSC("F" + generateString());
		indInfo.setLastNameSC("L" + generateString());
		
		indRequest.setIndividualInformations(indInfo);
		
		Civilian civ = new Civilian();
		civ.setTitleCode("RAF");
		
		indRequest.setCivilian(civ);
		
		IndividualProfilV3 ip = new IndividualProfilV3();
		ip.setLanguageCode("FR");
		
		indRequest.setIndividualProfil(ip);
		request.setIndividualRequest(indRequest);
		
		// WS call
		try {
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);		
			Assertions.fail("We should have an Exception due to TITLE");
			
		} catch (Exception e) {			
			
			BusinessErrorBlocBusinessException bebe = (BusinessErrorBlocBusinessException) e;			
			Assertions.assertNotNull(bebe.getFaultInfo());
			
			BusinessErrorBloc beb = bebe.getFaultInfo();			
			Assertions.assertNotNull(beb);

			BusinessError be = beb.getBusinessError();			
			Assertions.assertNotNull(be);
			Assertions.assertNotNull(be.getErrorCode());
			
			Assertions.assertEquals("ERROR_133", be.getErrorCode().toString());
			Assertions.assertEquals("MISSING PARAMETERS", be.getErrorLabel());
			Assertions.assertEquals("Missing parameter exception: The field title not valid", be.getErrorDetail());
		}
	}

	@Test
	void testCreateOrUpdateIndividual_UpdateIndividualWithNonValidTitle() throws BusinessErrorBlocBusinessException, JrafDomainException{

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
		indInfo.setIdentifier("400509998850");
		indInfo.setCivility("MISS");		
		indRequest.setIndividualInformations(indInfo);
		
		Civilian civ = new Civilian();
		civ.setTitleCode("RAF");
		
		indRequest.setCivilian(civ);
		
		IndividualProfilV3 ip = new IndividualProfilV3();
		ip.setLanguageCode("FR");
		
		indRequest.setIndividualProfil(ip);
		request.setIndividualRequest(indRequest);
		
		// WS call
		try {
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);		
			Assertions.fail("We should have an Exception due to TITLE");
			
		} catch (Exception e) {			
			
			BusinessErrorBlocBusinessException bebe = (BusinessErrorBlocBusinessException) e;			
			Assertions.assertNotNull(bebe.getFaultInfo());
			
			BusinessErrorBloc beb = bebe.getFaultInfo();			
			Assertions.assertNotNull(beb);

			BusinessError be = beb.getBusinessError();			
			Assertions.assertNotNull(be);
			Assertions.assertNotNull(be.getErrorCode());
			
			Assertions.assertEquals("ERROR_133", be.getErrorCode().toString());
			Assertions.assertEquals("MISSING PARAMETERS", be.getErrorLabel());
			Assertions.assertEquals("Missing parameter exception: The field title not valid", be.getErrorDetail());
		}
	}
	
	@Test
	@Rollback(false)
	void testCreateOrUpdateIndividual_CreateIndividualWithMinusCivility() throws BusinessErrorBlocBusinessException, JrafDomainException{

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
//		indInfo.setIdentifier(GIN);
		indInfo.setCivility("ms ");
		
		indInfo.setFirstNameSC("Nom");
		indInfo.setLastNameSC("Prénom");
		indInfo.setStatus("V");

		indRequest.setIndividualInformations(indInfo);
				
		IndividualProfilV3 ip = new IndividualProfilV3();
		ip.setLanguageCode("FR");
		
		indRequest.setIndividualProfil(ip);
		request.setIndividualRequest(indRequest);
		
		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);		
	
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
	}

	@Test
	@Rollback(false)
	void testCreateOrUpdateIndividual_UpdateIndividualWithMinusCivility() throws BusinessErrorBlocBusinessException, JrafDomainException{

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
		indInfo.setIdentifier("400509997682");
		indInfo.setCivility("ms ");
		
		indInfo.setStatus("V");

		indRequest.setIndividualInformations(indInfo);
				
		IndividualProfilV3 ip = new IndividualProfilV3();
		ip.setLanguageCode("FR");
		
		indRequest.setIndividualProfil(ip);
		request.setIndividualRequest(indRequest);
		
		// WS call
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);		
	
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
	}

	@Test
	void testCreateOrUpdateIndividual_DefaultGenderAndCivility_Process_W() throws BusinessErrorBlocBusinessException {

		logger.info("Test start... ");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect

		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setContext("B2C_HOME_PAGE");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");

		// Email
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();

		email.setEmail(MAIL);
		emailRequest.setEmail(email);

		// Communication Preference
		ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
		CommunicationPreferences comPref = new CommunicationPreferences();
		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("AF");
		comPref.setOptIn("N");
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			comPref.setDateOfConsent(df.parse("30-08-2016"));
		} catch (ParseException e) {
			logger.error("unable to set date of consent");
		}
		comPref.setSubscriptionChannel("B2C Home Page");

		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.FR);
		ml.setMarket("FR");
		ml.setOptIn("N");
		try {
			ml.setDateOfConsent(df.parse("01-09-2016"));
		} catch (ParseException e) {
			logger.error("unable to set market language date of consent");
		}

		comPref.getMarketLanguage().add(ml);
		comPrefrequest.setCommunicationPreferences(comPref);

		request.setRequestor(requestor);
		request.getEmailRequest().add(emailRequest);
		request.getComunicationPreferencesRequest().add(comPrefrequest);

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());

		logger.info("Gin updated: " + response.getGin());

		// Check individual
		Individu testIndividu = individuRepository.findBySgin(response.getGin());
		Assertions.assertEquals(GenderEnum.UNKNOWN.toString(), testIndividu.getSexe());
		Assertions.assertEquals(CivilityEnum.M_.toString(), testIndividu.getCivilite());

		logger.info("End of test");
	}

	@Test
	void testCreateOrUpdateIndividual_DefaultGenderAndCivility_Process_A() throws BusinessErrorBlocBusinessException {
		logger.info("Test start... ");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setProcess(ProcessEnum.A.getCode());

		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TU_RI");


		// Email
		EmailRequest emailReq = new EmailRequest();
		Email email = new Email();
		String mail = generateEmail();
		//		Random rd = new Random();
		//		int randomInt = rd.nextInt(1000);

		//		mail.append("tualert");
		//		mail.append(Integer.toString(randomInt));
		//		mail.append("@yopmail.com");
		email.setEmail(mail);
		emailReq.setEmail(email);

		// Communication Preference
		ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
		CommunicationPreferences comPref = new CommunicationPreferences();
		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("P");
		comPref.setCommunicationType("AF");
		comPref.setOptIn("N");
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			comPref.setDateOfConsent(df.parse("30-08-2016"));
		} catch (ParseException e) {
			logger.error("unable to set date of consent");
		}
		comPref.setSubscriptionChannel("B2C Home Page");

		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.FR);
		ml.setMarket("FR");
		ml.setOptIn("N");
		try {
			ml.setDateOfConsent(df.parse("01-09-2016"));
		} catch (ParseException e) {
			logger.error("unable to set market language date of consent");
		}

		comPref.getMarketLanguage().add(ml);
		comPrefrequest.setCommunicationPreferences(comPref);

		// Alert
		AlertRequest alertRequest = new AlertRequest();
		Alert alert = new Alert();
		alert.setType("P");
		alert.setOptIn("N");
		/* origin */
		AlertData orig = new AlertData();
		orig.setKey("ORIGIN");
		orig.setValue("PAR");
		alert.getAlertData().add(orig);
		AlertData origEnr = new AlertData();
		origEnr.setKey("ORIGIN_ENR");
		origEnr.setValue("PAR");
		alert.getAlertData().add(origEnr);
		/* origin type */
		AlertData origType = new AlertData();
		origType.setKey("ORIGIN_TYPE");
		origType.setValue("C");
		alert.getAlertData().add(origType);
		/* destination */
		AlertData dest = new AlertData();
		dest.setKey("DESTINATION");
		dest.setValue("LAX");
		alert.getAlertData().add(dest);
		/* destination type */
		AlertData destType = new AlertData();
		destType.setKey("DESTINATION_TYPE");
		destType.setValue("A");
		alert.getAlertData().add(destType);
		/* start date */
		AlertData sDate = new AlertData();
		sDate.setKey("START_DATE");
		sDate.setValue("01012016");
		alert.getAlertData().add(sDate);
		/* end date */
		AlertData eDate = new AlertData();
		eDate.setKey("END_DATE");
		eDate.setValue("31122016");
		alert.getAlertData().add(eDate);
		/* cabin */
		AlertData cabin = new AlertData();
		cabin.setKey("CABIN");
		cabin.setValue("W");
		alert.getAlertData().add(cabin);

		alertRequest.getAlert().add(alert);

		request.getEmailRequest().add(emailReq);
		request.setRequestor(requestor);
		request.getComunicationPreferencesRequest().add(comPrefrequest);
		request.setAlertRequest(alertRequest);

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		// Tests
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.isSuccess());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getGin());
		Assertions.assertTrue(response.getGin().startsWith("90"));
		// Check individual
		Individu testIndividu = individuRepository.findBySgin(response.getGin());
		Assertions.assertEquals(GenderEnum.UNKNOWN.toString(), testIndividu.getSexe());
		Assertions.assertEquals(CivilityEnum.M_.toString(), testIndividu.getCivilite());

		logger.info("End of test. ");
	}

	CreateUpdateIndividualRequest buildRequestProspect(){

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect

		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setContext(CONTEXT);
		requestor.setApplicationCode(APP_CODE);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);

		IndividualRequest individualRequest = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setFirstNameSC("Prénom");
		indInfo.setLastNameSC("Nom");
		Date date = new Date();
		indInfo.setBirthDate(date);
		indInfo.setNationality("FR");
		indInfo.setSecondNationality("IT");
		indInfo.setStatus("V");

		individualRequest.setIndividualInformations(indInfo);
		// Email
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();

		String emailUpper = generateEmail().toUpperCase();
		email.setEmail(emailUpper);
		emailRequest.setEmail(email);

		// Communication Preference
		ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
		CommunicationPreferences comPref = new CommunicationPreferences();
		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("AF");
		comPref.setOptIn("N");
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			comPref.setDateOfConsent(df.parse("30-08-2016"));
		} catch (ParseException e) {
			logger.error("unable to set date of consent");
		}
		comPref.setSubscriptionChannel("B2C Home Page");

		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.FR);
		ml.setMarket("FR");
		ml.setOptIn("N");
		try {
			ml.setDateOfConsent(df.parse("01-09-2016"));
		} catch (ParseException e) {
			logger.error("unable to set market language date of consent");
		}

		comPref.getMarketLanguage().add(ml);
		comPrefrequest.setCommunicationPreferences(comPref);

		request.setRequestor(requestor);
		request.getEmailRequest().add(emailRequest);
		request.getComunicationPreferencesRequest().add(comPrefrequest);
		request.setIndividualRequest(individualRequest);

		return request;
	}

	CreateUpdateIndividualRequest buildRequestExternal(){
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.E.getCode());

		// Preparing the request
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setContext(CONTEXT);
		requestor.setApplicationCode(APP_CODE);

		request.setRequestor(requestor);

		// Individual Request Bloc
		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setLastNameSC("SOCTWI" + generateString());
		indInfo.setFirstNameSC("SOCTWI" + generateString());
		indInfo.setStatus("V");

		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		// Social
		ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
		ExternalIdentifier ei = new ExternalIdentifier();
		ei.setIdentifier("NumeroIDTWITTER_123456789101112");
		ei.setType("TWITTER_ID");
		eir.setExternalIdentifier(ei);

		request.getExternalIdentifierRequest().add(eir);

		return request;
	}

}
