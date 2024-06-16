package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.Delegator;
import com.afklm.soa.stubs.w000442.v8.request.*;
import com.afklm.soa.stubs.w000442.v8.siccommonenum.LanguageCodesEnum;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.*;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.ProcessEnum;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.util.SicDateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.soap.SOAPException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplV8ForComprefTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV8ForComprefTest.class);
	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String GIN_I = "400263952140";

	@Autowired
	private IndividuDS individuDS;

	@Autowired
	private CommunicationPreferencesRepository communicationPreferencesRepository;
	
	@Autowired
	private CommunicationPreferencesDS communicationPreferencesDS;

	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v8Bean")
	@Spy
	private CreateOrUpdateIndividualImplV8 createOrUpdateIndividualImplV8;
	
	@Rule
    public MockitoRule rule = MockitoJUnit.rule();

	@Before
	@Transactional
	@Rollback(false)
	public void beforeTest() throws JrafDomainException, SOAPException {		
		
		IndividuDTO individu = individuDS.getAllByGin(GIN_I);
		
		individu.getCommunicationpreferencesdto().clear();
		
		CommunicationPreferencesDTO compref_FB_ESS = new CommunicationPreferencesDTO();
		compref_FB_ESS.setGin(GIN_I);
		compref_FB_ESS.setChannel("B2C");
		compref_FB_ESS.setComGroupType("N");
		compref_FB_ESS.setComType("FB_ESS");
		compref_FB_ESS.setCreationDate(new Date());
		compref_FB_ESS.setCreationSignature("TESTMZ");
		compref_FB_ESS.setCreationSite("QVI");
		compref_FB_ESS.setDateOptin(SicDateUtils.convertStringToDateDDMMYYYY("01/01/2020"));
		compref_FB_ESS.setDomain("F");
		compref_FB_ESS.setMedia1("E");
		compref_FB_ESS.setModificationDate(new Date());
		compref_FB_ESS.setModificationSignature("TESTMZ");
		compref_FB_ESS.setModificationSite("QVI");
		compref_FB_ESS.setSubscribe("Y");
		
		MarketLanguageDTO ml_FB_ESS = new MarketLanguageDTO();
		ml_FB_ESS.setCreationDate(new Date());
		ml_FB_ESS.setCreationSignature("TESTMZ");
		ml_FB_ESS.setCreationSite("QVI");
		ml_FB_ESS.setDateOfConsent(SicDateUtils.convertStringToDateDDMMYYYY("01/01/2020"));
		ml_FB_ESS.setLanguage("FR");
		ml_FB_ESS.setMarket("FR");
		ml_FB_ESS.setMedia1("E");
		ml_FB_ESS.setModificationDate(new Date());
		ml_FB_ESS.setModificationSignature("TESTMZ");
		ml_FB_ESS.setModificationSite("QVI");
		ml_FB_ESS.setOptIn("Y");
		
		compref_FB_ESS.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		compref_FB_ESS.getMarketLanguageDTO().add(ml_FB_ESS);
		
		individu.getCommunicationpreferencesdto().add(compref_FB_ESS);
		
		CommunicationPreferencesDTO compref_FB_NEWS = new CommunicationPreferencesDTO();
		compref_FB_NEWS.setGin(GIN_I);
		compref_FB_NEWS.setChannel("B2C");
		compref_FB_NEWS.setComGroupType("N");
		compref_FB_NEWS.setComType("FB_NEWS");
		compref_FB_NEWS.setCreationDate(new Date());
		compref_FB_NEWS.setCreationSignature("TESTMZ");
		compref_FB_NEWS.setCreationSite("QVI");
		compref_FB_NEWS.setDateOptin(SicDateUtils.convertStringToDateDDMMYYYY("01/01/2020"));
		compref_FB_NEWS.setDomain("F");
		compref_FB_NEWS.setMedia1("E");
		compref_FB_NEWS.setModificationDate(new Date());
		compref_FB_NEWS.setModificationSignature("TESTMZ");
		compref_FB_NEWS.setModificationSite("QVI");
		compref_FB_NEWS.setSubscribe("Y");
		
		MarketLanguageDTO ml_FB_NEWS = new MarketLanguageDTO();
		ml_FB_NEWS.setCreationDate(new Date());
		ml_FB_NEWS.setCreationSignature("TESTMZ");
		ml_FB_NEWS.setCreationSite("QVI");
		ml_FB_NEWS.setDateOfConsent(SicDateUtils.convertStringToDateDDMMYYYY("01/01/2020"));
		ml_FB_NEWS.setLanguage("FR");
		ml_FB_NEWS.setMarket("FR");
		ml_FB_NEWS.setMedia1("E");
		ml_FB_NEWS.setModificationDate(new Date());
		ml_FB_NEWS.setModificationSignature("TESTMZ");
		ml_FB_NEWS.setModificationSite("QVI");
		ml_FB_NEWS.setOptIn("Y");
		
		compref_FB_NEWS.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		compref_FB_NEWS.getMarketLanguageDTO().add(ml_FB_NEWS);
		
		individu.getCommunicationpreferencesdto().add(compref_FB_NEWS);
		
		CommunicationPreferencesDTO compref_FB_PART = new CommunicationPreferencesDTO();
		compref_FB_PART.setGin(GIN_I);
		compref_FB_PART.setChannel("B2C");
		compref_FB_PART.setComGroupType("N");
		compref_FB_PART.setComType("FB_PART");
		compref_FB_PART.setCreationDate(new Date());
		compref_FB_PART.setCreationSignature("TESTMZ");
		compref_FB_PART.setCreationSite("QVI");
		compref_FB_PART.setDateOptin(SicDateUtils.convertStringToDateDDMMYYYY("01/01/2020"));
		compref_FB_PART.setDomain("F");
		compref_FB_PART.setMedia1("E");
		compref_FB_PART.setModificationDate(new Date());
		compref_FB_PART.setModificationSignature("TESTMZ");
		compref_FB_PART.setModificationSite("QVI");
		compref_FB_PART.setSubscribe("Y");
		
		MarketLanguageDTO ml_FB_PART = new MarketLanguageDTO();
		ml_FB_PART.setCreationDate(new Date());
		ml_FB_PART.setCreationSignature("TESTMZ");
		ml_FB_PART.setCreationSite("QVI");
		ml_FB_PART.setDateOfConsent(SicDateUtils.convertStringToDateDDMMYYYY("01/01/2020"));
		ml_FB_PART.setLanguage("FR");
		ml_FB_PART.setMarket("FR");
		ml_FB_PART.setMedia1("E");
		ml_FB_PART.setModificationDate(new Date());
		ml_FB_PART.setModificationSignature("TESTMZ");
		ml_FB_PART.setModificationSite("QVI");
		ml_FB_PART.setOptIn("Y");
		
		compref_FB_PART.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		compref_FB_PART.getMarketLanguageDTO().add(ml_FB_PART);
		
		individu.getCommunicationpreferencesdto().add(compref_FB_PART);

		CommunicationPreferencesDTO compref_F_FB_PROG = new CommunicationPreferencesDTO();
		compref_F_FB_PROG.setGin(GIN_I);
		compref_F_FB_PROG.setChannel("B2C");
		compref_F_FB_PROG.setComGroupType("N");
		compref_F_FB_PROG.setComType("FB_PROG");
		compref_F_FB_PROG.setCreationDate(new Date());
		compref_F_FB_PROG.setCreationSignature("TESTMZ");
		compref_F_FB_PROG.setCreationSite("QVI");
		compref_F_FB_PROG.setDateOptin(SicDateUtils.convertStringToDateDDMMYYYY("01/01/2020"));
		compref_F_FB_PROG.setDomain("F");
		compref_F_FB_PROG.setMedia1("E");
		compref_F_FB_PROG.setModificationDate(new Date());
		compref_F_FB_PROG.setModificationSignature("TESTMZ");
		compref_F_FB_PROG.setModificationSite("QVI");
		compref_F_FB_PROG.setSubscribe("N");

		MarketLanguageDTO ml_F_AF = new MarketLanguageDTO();
		ml_F_AF.setCreationDate(new Date());
		ml_F_AF.setCreationSignature("TESTMZ");
		ml_F_AF.setCreationSite("QVI");
		ml_F_AF.setDateOfConsent(SicDateUtils.convertStringToDateDDMMYYYY("01/01/2020"));
		ml_F_AF.setLanguage("FR");
		ml_F_AF.setMarket("FR");
		ml_F_AF.setMedia1("E");
		ml_F_AF.setModificationDate(new Date());
		ml_F_AF.setModificationSignature("TESTMZ");
		ml_F_AF.setModificationSite("QVI");
		ml_F_AF.setOptIn("N");

		compref_F_FB_PROG.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		compref_F_FB_PROG.getMarketLanguageDTO().add(ml_F_AF);

		individu.getCommunicationpreferencesdto().add(compref_F_FB_PROG);
		
		CommunicationPreferencesDTO compref_S_AF = new CommunicationPreferencesDTO();
		compref_S_AF.setGin(GIN_I);
		compref_S_AF.setChannel("B2C");
		compref_S_AF.setComGroupType("N");
		compref_S_AF.setComType("AF");
		compref_S_AF.setCreationDate(new Date());
		compref_S_AF.setCreationSignature("TESTMZ");
		compref_S_AF.setCreationSite("QVI");
		compref_S_AF.setDateOptin(SicDateUtils.convertStringToDateDDMMYYYY("01/01/2020"));
		compref_S_AF.setDomain("S");
		compref_S_AF.setMedia1("E");
		compref_S_AF.setModificationDate(new Date());
		compref_S_AF.setModificationSignature("TESTMZ");
		compref_S_AF.setModificationSite("QVI");
		compref_S_AF.setSubscribe("Y");
		
		MarketLanguageDTO ml_S_AF = new MarketLanguageDTO();
		ml_S_AF.setCreationDate(new Date());
		ml_S_AF.setCreationSignature("TESTMZ");
		ml_S_AF.setCreationSite("QVI");
		ml_S_AF.setDateOfConsent(SicDateUtils.convertStringToDateDDMMYYYY("01/01/2020"));
		ml_S_AF.setLanguage("FR");
		ml_S_AF.setMarket("FR");
		ml_S_AF.setMedia1("E");
		ml_S_AF.setModificationDate(new Date());
		ml_S_AF.setModificationSignature("TESTMZ");
		ml_S_AF.setModificationSite("QVI");
		ml_S_AF.setOptIn("Y");

		MarketLanguageDTO ml_S_AF2 = new MarketLanguageDTO();
		ml_S_AF2.setCreationDate(new Date());
		ml_S_AF2.setCreationSignature("TESTMZ");
		ml_S_AF2.setCreationSite("QVI");
		ml_S_AF2.setDateOfConsent(SicDateUtils.convertStringToDateDDMMYYYY("01/01/2020"));
		ml_S_AF2.setLanguage("AE");
		ml_S_AF2.setMarket("EN");
		ml_S_AF2.setMedia1("E");
		ml_S_AF2.setModificationDate(new Date());
		ml_S_AF2.setModificationSignature("TESTMZ");
		ml_S_AF2.setModificationSite("QVI");
		ml_S_AF2.setOptIn("N");
		
		compref_S_AF.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		compref_S_AF.getMarketLanguageDTO().add(ml_S_AF);
		compref_S_AF.getMarketLanguageDTO().add(ml_S_AF2);

		individu.getCommunicationpreferencesdto().add(compref_S_AF);

		CommunicationPreferencesDTO compref_S_KL = new CommunicationPreferencesDTO();
		compref_S_KL.setGin(GIN_I);
		compref_S_KL.setChannel("B2C");
		compref_S_KL.setComGroupType("N");
		compref_S_KL.setComType("KL");
		compref_S_KL.setCreationDate(new Date());
		compref_S_KL.setCreationSignature("TESTMZ");
		compref_S_KL.setCreationSite("QVI");
		compref_S_KL.setDateOptin(SicDateUtils.convertStringToDateDDMMYYYY("01/01/2020"));
		compref_S_KL.setDomain("S");
		compref_S_KL.setMedia1("E");
		compref_S_KL.setModificationDate(new Date());
		compref_S_KL.setModificationSignature("TESTMZ");
		compref_S_KL.setModificationSite("QVI");
		compref_S_KL.setSubscribe("N");

		MarketLanguageDTO ml_S_AF3 = new MarketLanguageDTO();
		ml_S_AF3.setCreationDate(new Date());
		ml_S_AF3.setCreationSignature("TESTMZ");
		ml_S_AF3.setCreationSite("QVI");
		ml_S_AF3.setDateOfConsent(SicDateUtils.convertStringToDateDDMMYYYY("01/01/2020"));
		ml_S_AF3.setLanguage("FR");
		ml_S_AF3.setMarket("FR");
		ml_S_AF3.setMedia1("E");
		ml_S_AF3.setModificationDate(new Date());
		ml_S_AF3.setModificationSignature("TESTMZ");
		ml_S_AF3.setModificationSite("QVI");
		ml_S_AF3.setOptIn("N");

		compref_S_KL.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		compref_S_KL.getMarketLanguageDTO().add(ml_S_AF3);

		individu.getCommunicationpreferencesdto().add(compref_S_KL);

		CommunicationPreferencesDTO compref_P = new CommunicationPreferencesDTO();
		compref_P.setGin(GIN_I);
		compref_P.setChannel("B2C");
		compref_P.setComGroupType("S");
		compref_P.setComType("RECO");
		compref_P.setCreationDate(new Date());
		compref_P.setCreationSignature("TESTMZ");
		compref_P.setCreationSite("QVI");
		compref_P.setDateOptin(SicDateUtils.convertStringToDateDDMMYYYY("01/01/2020"));
		compref_P.setDomain("P");
		compref_P.setMedia1("E");
		compref_P.setModificationDate(new Date());
		compref_P.setModificationSignature("TESTMZ");
		compref_P.setModificationSite("QVI");
		compref_P.setSubscribe("Y");

		individu.getCommunicationpreferencesdto().add(compref_P);

		individuDS.updateComPrefOfIndividual(individu);
	}
	

	@Test
	public void testCreateOrUpdateIndividual_UpdateOneCompref_FB() throws JrafDaoException, SOAPException {

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");
	
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_I);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);
		
		ComunicationPreferencesRequest comprefRequest = new ComunicationPreferencesRequest();

		CommunicationPreferences compref = new CommunicationPreferences();
		compref.setDomain("F");
		compref.setCommunicationGroupeType("N");
		compref.setCommunicationType("FB_NEWS");
		compref.setOptIn("Y");
		
		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.IT);
		ml.setMarket("IT");
		ml.setOptIn("Y");
		compref.getMarketLanguage().add(ml);
		
		comprefRequest.setCommunicationPreferences(compref);
		
		request.getComunicationPreferencesRequest().add(comprefRequest);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		Assert.assertNotNull(response);		
		Assert.assertTrue(response.isSuccess());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin(GIN_I);
		Assert.assertEquals(5, countComprefByMarketLanguage(cpResult, "FR", "FR"));
		Assert.assertEquals(1, countComprefByMarketLanguage(cpResult, "IT", "IT"));		

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}

	@Test
	public void testCreateOrUpdateIndividual_I_UpdateOneCompref_P_Optin() {

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_I);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		/** Y TO N */

		ComunicationPreferencesRequest comprefRequest = new ComunicationPreferencesRequest();

		CommunicationPreferences compref = new CommunicationPreferences();
		compref.setDomain("P");
		compref.setCommunicationGroupeType("S");
		compref.setCommunicationType("RECO");
		compref.setOptIn("N");

		comprefRequest.setCommunicationPreferences(compref);

		request.getComunicationPreferencesRequest().add(comprefRequest);

		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();

		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}

		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin(GIN_I);
		cpResult.stream().filter(cp -> "P".equals(cp.getDomain()) &&
				"RECO".equals(cp.getComType())).forEach(cp -> Assert.assertEquals(SicDateUtils.dateToString(new Date()), SicDateUtils.dateToString(cp.getDateOptin())));

		Assert.assertEquals(6, countComprefByMarketLanguage(cpResult, "FR", "FR"));

		/** N TO Y */

		compref.setOptIn("N");

		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}

		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());

		cpResult = communicationPreferencesRepository.findByGin(GIN_I);
		cpResult.stream().filter(cp -> "P".equals(cp.getDomain()) &&
				"RECO".equals(cp.getComType())).forEach(cp -> Assert.assertEquals(SicDateUtils.dateToString(new Date()), SicDateUtils.dateToString(cp.getDateOptin())));

		Assert.assertEquals(6, countComprefByMarketLanguage(cpResult, "FR", "FR"));

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}

	@Test
	public void testCreateOrUpdateIndividual_A_UpdateOneCompref_P_Optin() {

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = generateMandatoryRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_I);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		/** Y TO N */

		ComunicationPreferencesRequest comprefRequest = new ComunicationPreferencesRequest();

		CommunicationPreferences compref = new CommunicationPreferences();
		compref.setDomain("P");
		compref.setCommunicationGroupeType("S");
		compref.setCommunicationType("RECO");
		compref.setOptIn("N");

		comprefRequest.setCommunicationPreferences(compref);

		request.getComunicationPreferencesRequest().add(comprefRequest);

		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();

		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}

		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin(GIN_I);
		cpResult.stream().filter(cp -> "P".equals(cp.getDomain()) &&
				"RECO".equals(cp.getComType())).forEach(cp -> Assert.assertEquals(SicDateUtils.dateToString(new Date()), SicDateUtils.dateToString(cp.getDateOptin())));

		Assert.assertEquals(6, countComprefByMarketLanguage(cpResult, "FR", "FR"));

		/** N TO Y */

		compref.setOptIn("N");

		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}

		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());

		cpResult = communicationPreferencesRepository.findByGin(GIN_I);
		cpResult.stream().filter(cp -> "P".equals(cp.getDomain()) &&
				"RECO".equals(cp.getComType())).forEach(cp -> Assert.assertEquals(SicDateUtils.dateToString(new Date()), SicDateUtils.dateToString(cp.getDateOptin())));

		Assert.assertEquals(6, countComprefByMarketLanguage(cpResult, "FR", "FR"));

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}

	@Test
	public void testCreateOrUpdateIndividual_I_UpdateOneMarketLanguage_F_Optin(){

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_I);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		ComunicationPreferencesRequest comprefRequest = new ComunicationPreferencesRequest();

		CommunicationPreferences compref = new CommunicationPreferences();
		compref.setDomain("F");
		compref.setCommunicationGroupeType("N");
		compref.setCommunicationType("FB_NEWS");
		compref.setOptIn("Y");

		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.FR);
		ml.setMarket("FR");
		ml.setOptIn("N");
		compref.getMarketLanguage().add(ml);

		comprefRequest.setCommunicationPreferences(compref);

		request.getComunicationPreferencesRequest().add(comprefRequest);

		ComunicationPreferencesRequest comprefRequest2 = new ComunicationPreferencesRequest();

		CommunicationPreferences compref2 = new CommunicationPreferences();
		compref2.setDomain("F");
		compref2.setCommunicationGroupeType("N");
		compref2.setCommunicationType("FB_PROG");
		compref2.setOptIn("Y");

		MarketLanguage ml2 = new MarketLanguage();
		ml2.setLanguage(LanguageCodesEnum.FR);
		ml2.setMarket("FR");
		ml2.setOptIn("Y");
		compref2.getMarketLanguage().add(ml2);

		comprefRequest2.setCommunicationPreferences(compref2);

		request.getComunicationPreferencesRequest().add(comprefRequest2);

		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();

		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}

		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin(GIN_I);
		cpResult.stream().filter(cp -> "F".equals(cp.getDomain()) &&
				"FB_NEWS".equals(cp.getComType())).forEach(cp -> {
		cp.getMarketLanguage().stream().forEach(mlang -> Assert.assertEquals(SicDateUtils.dateToString(new Date()), SicDateUtils.dateToString(mlang.getDateOfConsent())));
		});
		cpResult.stream().filter(cp -> "F".equals(cp.getDomain()) &&
				"FB_PROG".equals(cp.getComType())).forEach(cp -> {
			cp.getMarketLanguage().stream().forEach(mlang -> Assert.assertEquals(SicDateUtils.dateToString(new Date()), SicDateUtils.dateToString(mlang.getDateOfConsent())));
		});

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}

	@Test
	public void testCreateOrUpdateIndividual_A_UpdateOneMarketLanguage_F_Optin(){

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = generateMandatoryRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_I);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		ComunicationPreferencesRequest comprefRequest = new ComunicationPreferencesRequest();

		CommunicationPreferences compref = new CommunicationPreferences();
		compref.setDomain("F");
		compref.setCommunicationGroupeType("N");
		compref.setCommunicationType("FB_NEWS");
		compref.setOptIn("Y");

		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.FR);
		ml.setMarket("FR");
		ml.setOptIn("N");
		compref.getMarketLanguage().add(ml);

		comprefRequest.setCommunicationPreferences(compref);

		request.getComunicationPreferencesRequest().add(comprefRequest);

		ComunicationPreferencesRequest comprefRequest2 = new ComunicationPreferencesRequest();

		CommunicationPreferences compref2 = new CommunicationPreferences();
		compref2.setDomain("F");
		compref2.setCommunicationGroupeType("N");
		compref2.setCommunicationType("FB_PROG");
		compref2.setOptIn("Y");

		MarketLanguage ml2 = new MarketLanguage();
		ml2.setLanguage(LanguageCodesEnum.FR);
		ml2.setMarket("FR");
		ml2.setOptIn("Y");
		compref2.getMarketLanguage().add(ml2);

		comprefRequest2.setCommunicationPreferences(compref2);

		request.getComunicationPreferencesRequest().add(comprefRequest2);

		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();

		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}

		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin(GIN_I);
		cpResult.stream().filter(cp -> "F".equals(cp.getDomain()) &&
				"FB_NEWS".equals(cp.getComType())).forEach(cp -> {
			cp.getMarketLanguage().stream().forEach(mlang -> Assert.assertEquals(SicDateUtils.dateToString(new Date()), SicDateUtils.dateToString(mlang.getDateOfConsent())));
		});
		cpResult.stream().filter(cp -> "F".equals(cp.getDomain()) &&
				"FB_PROG".equals(cp.getComType())).forEach(cp -> {
			cp.getMarketLanguage().stream().forEach(mlang -> Assert.assertEquals(SicDateUtils.dateToString(new Date()), SicDateUtils.dateToString(mlang.getDateOfConsent())));
		});

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}

	@Test
	public void testCreateOrUpdateIndividual_I_UpdateOneMarketLanguage_S_Optin(){

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_I);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		ComunicationPreferencesRequest comprefRequest = new ComunicationPreferencesRequest();

		CommunicationPreferences compref = new CommunicationPreferences();
		compref.setDomain("S");
		compref.setCommunicationGroupeType("N");
		compref.setCommunicationType("AF");
		compref.setOptIn("Y");

		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.FR);
		ml.setMarket("FR");
		ml.setOptIn("N");
		compref.getMarketLanguage().add(ml);

		MarketLanguage ml2 = new MarketLanguage();
		ml2.setLanguage(LanguageCodesEnum.AE);
		ml2.setMarket("EN");
		ml2.setOptIn("Y");
		compref.getMarketLanguage().add(ml2);

		comprefRequest.setCommunicationPreferences(compref);

		request.getComunicationPreferencesRequest().add(comprefRequest);

		ComunicationPreferencesRequest comprefRequest2 = new ComunicationPreferencesRequest();

		CommunicationPreferences compref2 = new CommunicationPreferences();
		compref2.setDomain("S");
		compref2.setCommunicationGroupeType("N");
		compref2.setCommunicationType("KL");
		compref2.setOptIn("N");

		MarketLanguage ml3 = new MarketLanguage();
		ml3.setLanguage(LanguageCodesEnum.FR);
		ml3.setMarket("FR");
		ml3.setOptIn("Y");
		compref2.getMarketLanguage().add(ml3);

		comprefRequest2.setCommunicationPreferences(compref2);

		request.getComunicationPreferencesRequest().add(comprefRequest2);

		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();

		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}

		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());


		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin(GIN_I);
		cpResult.stream().filter(cp -> "S".equals(cp.getDomain()) &&
				"AF".equals(cp.getComType())).forEach(cp -> {
			cp.getMarketLanguage().stream().forEach(mlang -> Assert.assertEquals(SicDateUtils.dateToString(new Date()), SicDateUtils.dateToString(mlang.getDateOfConsent())));
		});
		cpResult.stream().filter(cp -> "S".equals(cp.getDomain()) &&
				"KL".equals(cp.getComType())).forEach(cp -> {
			cp.getMarketLanguage().stream().forEach(mlang -> Assert.assertEquals(SicDateUtils.dateToString(new Date()), SicDateUtils.dateToString(mlang.getDateOfConsent())));
		});

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}

	@Test
	public void testCreateOrUpdateIndividual_A_UpdateOneMarketLanguage_S_Optin(){

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = generateMandatoryRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_I);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		ComunicationPreferencesRequest comprefRequest = new ComunicationPreferencesRequest();

		CommunicationPreferences compref = new CommunicationPreferences();
		compref.setDomain("S");
		compref.setCommunicationGroupeType("N");
		compref.setCommunicationType("AF");
		compref.setOptIn("Y");

		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.FR);
		ml.setMarket("FR");
		ml.setOptIn("N");
		compref.getMarketLanguage().add(ml);

		MarketLanguage ml2 = new MarketLanguage();
		ml2.setLanguage(LanguageCodesEnum.AE);
		ml2.setMarket("EN");
		ml2.setOptIn("Y");
		compref.getMarketLanguage().add(ml2);

		comprefRequest.setCommunicationPreferences(compref);

		request.getComunicationPreferencesRequest().add(comprefRequest);

		ComunicationPreferencesRequest comprefRequest2 = new ComunicationPreferencesRequest();

		CommunicationPreferences compref2 = new CommunicationPreferences();
		compref2.setDomain("S");
		compref2.setCommunicationGroupeType("N");
		compref2.setCommunicationType("KL");
		compref2.setOptIn("N");

		MarketLanguage ml3 = new MarketLanguage();
		ml3.setLanguage(LanguageCodesEnum.FR);
		ml3.setMarket("FR");
		ml3.setOptIn("Y");
		compref2.getMarketLanguage().add(ml3);

		comprefRequest2.setCommunicationPreferences(compref2);

		request.getComunicationPreferencesRequest().add(comprefRequest2);

		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();

		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}

		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());


		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin(GIN_I);
		cpResult.stream().filter(cp -> "S".equals(cp.getDomain()) &&
				"AF".equals(cp.getComType())).forEach(cp -> {
			cp.getMarketLanguage().stream().forEach(mlang -> Assert.assertEquals(SicDateUtils.dateToString(new Date()), SicDateUtils.dateToString(mlang.getDateOfConsent())));
		});
		cpResult.stream().filter(cp -> "S".equals(cp.getDomain()) &&
				"KL".equals(cp.getComType())).forEach(cp -> {
			cp.getMarketLanguage().stream().forEach(mlang -> Assert.assertEquals(SicDateUtils.dateToString(new Date()), SicDateUtils.dateToString(mlang.getDateOfConsent())));
		});

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}
	
	@Test
	public void testCreateOrUpdateIndividual_UpdateMultipleCompref_FB() throws JrafDaoException {

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_I);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);
		
		ComunicationPreferencesRequest comprefRequestA = new ComunicationPreferencesRequest();

		CommunicationPreferences comprefA = new CommunicationPreferences();
		comprefA.setDomain("F");
		comprefA.setCommunicationGroupeType("N");
		comprefA.setCommunicationType("FB_NEWS");
		comprefA.setOptIn("Y");
		
		MarketLanguage mlA = new MarketLanguage();
		mlA.setLanguage(LanguageCodesEnum.IT);
		mlA.setMarket("IT");
		mlA.setOptIn("Y");
		comprefA.getMarketLanguage().add(mlA);
		
		comprefRequestA.setCommunicationPreferences(comprefA);
		
		request.getComunicationPreferencesRequest().add(comprefRequestA);
		
		ComunicationPreferencesRequest comprefRequestB = new ComunicationPreferencesRequest();
		
		CommunicationPreferences comprefB = new CommunicationPreferences();
		comprefB.setDomain("F");
		comprefB.setCommunicationGroupeType("N");
		comprefB.setCommunicationType("FB_PART");
		comprefB.setOptIn("Y");
		
		MarketLanguage mlB = new MarketLanguage();
		mlB.setLanguage(LanguageCodesEnum.IT);
		mlB.setMarket("IT");
		mlB.setOptIn("Y");
		comprefB.getMarketLanguage().add(mlB);
		
		comprefRequestB.setCommunicationPreferences(comprefB);
		
		request.getComunicationPreferencesRequest().add(comprefRequestB);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		Assert.assertNotNull(response);		
		Assert.assertTrue(response.isSuccess());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin(GIN_I);
		Assert.assertEquals(4, countComprefByMarketLanguage(cpResult, "FR", "FR"));
		Assert.assertEquals(2, countComprefByMarketLanguage(cpResult, "IT", "IT"));		

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}
	
	@Test
	public void testCreateOrUpdateIndividual_UpdateAllCompref_FB() throws JrafDaoException, SOAPException {

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");
		
		Mockito.when(createOrUpdateIndividualImplV8.getConsumerId()).thenReturn("w06536507");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_I);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);
		
		ComunicationPreferencesRequest comprefRequest = new ComunicationPreferencesRequest();

		CommunicationPreferences compref = new CommunicationPreferences();
		compref.setDomain("F");
		compref.setCommunicationGroupeType("N");
		compref.setCommunicationType("FB_ESS");
		compref.setOptIn("Y");
		
		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.IT);
		ml.setMarket("IT");
		ml.setOptIn("Y");
		compref.getMarketLanguage().add(ml);
		
		comprefRequest.setCommunicationPreferences(compref);
		
		request.getComunicationPreferencesRequest().add(comprefRequest);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		Assert.assertNotNull(response);		
		Assert.assertTrue(response.isSuccess());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin(GIN_I);
		Assert.assertEquals(2, countComprefByMarketLanguage(cpResult, "FR", "FR"));
		Assert.assertEquals(4, countComprefByMarketLanguage(cpResult, "IT", "IT"));

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}
	
	@Test
	public void testCreateOrUpdateIndividual_UpdateOneCompref_S() throws JrafDaoException {

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_I);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);
		
		ComunicationPreferencesRequest comprefRequest = new ComunicationPreferencesRequest();

		CommunicationPreferences compref = new CommunicationPreferences();
		compref.setDomain("S");
		compref.setCommunicationGroupeType("N");
		compref.setCommunicationType("AF");
		compref.setOptIn("Y");
		
		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.IT);
		ml.setMarket("IT");
		ml.setOptIn("Y");
		compref.getMarketLanguage().add(ml);
		
		comprefRequest.setCommunicationPreferences(compref);
		
		request.getComunicationPreferencesRequest().add(comprefRequest);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		Assert.assertNotNull(response);		
		Assert.assertTrue(response.isSuccess());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin(GIN_I);
		Assert.assertEquals(6, countComprefByMarketLanguage(cpResult, "FR", "FR"));
		Assert.assertEquals(1, countComprefByMarketLanguage(cpResult, "IT", "IT"));		

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}
	
	@Test
	public void testCreateOrUpdateIndividual_UpdateOneCompref_FB_And_OneCompref_S() throws JrafDaoException {

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_I);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);
		
		ComunicationPreferencesRequest comprefRequestA = new ComunicationPreferencesRequest();

		CommunicationPreferences comprefA = new CommunicationPreferences();
		comprefA.setDomain("F");
		comprefA.setCommunicationGroupeType("N");
		comprefA.setCommunicationType("FB_NEWS");
		comprefA.setOptIn("Y");
		
		MarketLanguage mlA = new MarketLanguage();
		mlA.setLanguage(LanguageCodesEnum.IT);
		mlA.setMarket("IT");
		mlA.setOptIn("Y");
		comprefA.getMarketLanguage().add(mlA);
		
		comprefRequestA.setCommunicationPreferences(comprefA);
		
		request.getComunicationPreferencesRequest().add(comprefRequestA);
		
		ComunicationPreferencesRequest comprefRequestB = new ComunicationPreferencesRequest();
		
		CommunicationPreferences comprefB = new CommunicationPreferences();
		comprefB.setDomain("S");
		comprefB.setCommunicationGroupeType("N");
		comprefB.setCommunicationType("AF");
		comprefB.setOptIn("Y");
		
		MarketLanguage mlB = new MarketLanguage();
		mlB.setLanguage(LanguageCodesEnum.IT);
		mlB.setMarket("IT");
		mlB.setOptIn("Y");
		comprefB.getMarketLanguage().add(mlB);
		
		comprefRequestB.setCommunicationPreferences(comprefB);
		
		request.getComunicationPreferencesRequest().add(comprefRequestB);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		Assert.assertNotNull(response);		
		Assert.assertTrue(response.isSuccess());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin(GIN_I);
		Assert.assertEquals(5, countComprefByMarketLanguage(cpResult, "FR", "FR"));
		Assert.assertEquals(2, countComprefByMarketLanguage(cpResult, "IT", "IT"));		

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}
	
	@Test
	public void testCreateOrUpdateIndividual_Error_MultipleMarketLanguage() throws JrafDaoException, SOAPException {

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");
		
		Mockito.when(createOrUpdateIndividualImplV8.getConsumerId()).thenReturn("w06536507");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_I);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);
		
		ComunicationPreferencesRequest comprefRequest = new ComunicationPreferencesRequest();

		CommunicationPreferences compref = new CommunicationPreferences();
		compref.setDomain("F");
		compref.setCommunicationGroupeType("N");
		compref.setCommunicationType("FB_ESS");
		compref.setOptIn("Y");
		
		MarketLanguage mlA = new MarketLanguage();
		mlA.setLanguage(LanguageCodesEnum.IT);
		mlA.setMarket("IT");
		mlA.setOptIn("Y");
		compref.getMarketLanguage().add(mlA);
		
		MarketLanguage mlB = new MarketLanguage();
		mlB.setLanguage(LanguageCodesEnum.DE);
		mlB.setMarket("DE");
		mlB.setOptIn("Y");
		compref.getMarketLanguage().add(mlB);
		
		comprefRequest.setCommunicationPreferences(compref);
		
		request.getComunicationPreferencesRequest().add(comprefRequest);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		Assert.assertNotNull(response);		
		Assert.assertTrue(response.isSuccess());
		Assert.assertEquals("10", response.getInformationResponse().get(0).getInformation().getInformationCode());
		Assert.assertEquals("When updating all Market Language for FlyingBlue Domain, only one Market Language combinaison is allowed", response.getInformationResponse().get(0).getInformation().getInformationDetails());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin(GIN_I);
		Assert.assertEquals(6, countComprefByMarketLanguage(cpResult, "FR", "FR"));
		Assert.assertEquals(0, countComprefByMarketLanguage(cpResult, "IT", "IT"));
		Assert.assertEquals(0, countComprefByMarketLanguage(cpResult, "DE", "DE"));		

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}
	
	@Test
	public void testCreateOrUpdateIndividual_Error_DefaultLanguage() throws JrafDaoException, SOAPException {

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");
		
		Mockito.when(createOrUpdateIndividualImplV8.getConsumerId()).thenReturn("w06536507");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_I);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);
		
		ComunicationPreferencesRequest comprefRequest = new ComunicationPreferencesRequest();

		CommunicationPreferences compref = new CommunicationPreferences();
		compref.setDomain("F");
		compref.setCommunicationGroupeType("N");
		compref.setCommunicationType("FB_ESS");
		compref.setOptIn("Y");
		
		MarketLanguage mlA = new MarketLanguage();
		mlA.setLanguage(LanguageCodesEnum.DE);
		mlA.setMarket("IT");
		mlA.setOptIn("Y");
		compref.getMarketLanguage().add(mlA);
				
		comprefRequest.setCommunicationPreferences(compref);
		
		request.getComunicationPreferencesRequest().add(comprefRequest);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		Assert.assertNotNull(response);		
		Assert.assertTrue(response.isSuccess());
		Assert.assertEquals("11", response.getInformationResponse().get(0).getInformation().getInformationCode());
		Assert.assertEquals("Language DE not available for Market IT. Default language EN was set", response.getInformationResponse().get(0).getInformation().getInformationDetails());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin(GIN_I);
		Assert.assertEquals(4, countComprefByMarketLanguage(cpResult, "IT", "EN"));
		Assert.assertEquals(2, countComprefByMarketLanguage(cpResult, "FR", "FR"));

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}
	
	@Test
	public void testCreateOrUpdateIndividual_Error_WrongConsumer() throws JrafDaoException, SOAPException {

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");
		
		Mockito.when(createOrUpdateIndividualImplV8.getConsumerId()).thenReturn("w99999999");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_I);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);
		
		ComunicationPreferencesRequest comprefRequest = new ComunicationPreferencesRequest();

		CommunicationPreferences compref = new CommunicationPreferences();
		compref.setDomain("F");
		compref.setCommunicationGroupeType("N");
		compref.setCommunicationType("FB_ESS");
		compref.setOptIn("Y");
		
		MarketLanguage mlA = new MarketLanguage();
		mlA.setLanguage(LanguageCodesEnum.IT);
		mlA.setMarket("IT");
		mlA.setOptIn("Y");
		compref.getMarketLanguage().add(mlA);
				
		comprefRequest.setCommunicationPreferences(compref);
		
		request.getComunicationPreferencesRequest().add(comprefRequest);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		Assert.assertNotNull(response);		
		Assert.assertTrue(response.isSuccess());
		Assert.assertEquals("12", response.getInformationResponse().get(0).getInformation().getInformationCode());
		Assert.assertEquals("Your application is not authorized to perform this update on Flying Blue Communication Preferences", response.getInformationResponse().get(0).getInformation().getInformationDetails());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin(GIN_I);
		Assert.assertEquals(0, countComprefByMarketLanguage(cpResult, "IT", "IT"));
		Assert.assertEquals(6, countComprefByMarketLanguage(cpResult, "FR", "FR"));

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}
	
	@Test
	@Transactional
	public void testCreateOrUpdateIndividual_OptOut_PromoAlert_ComPref_MarketLang() throws JrafDaoException, SOAPException {

		String GIN_PROMOALERT = "400518107333";
		
		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_PROMOALERT);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);
		
		ComunicationPreferencesRequest comprefRequest = new ComunicationPreferencesRequest();

		CommunicationPreferences compref = new CommunicationPreferences();
		compref.setDomain("S");
		compref.setCommunicationGroupeType("P");
		compref.setCommunicationType("AF");
		compref.setOptIn("N");
		
		MarketLanguage mlA = new MarketLanguage();
		mlA.setLanguage(LanguageCodesEnum.FR);
		mlA.setMarket("FR");
		mlA.setOptIn("N");
		compref.getMarketLanguage().add(mlA);
				
		comprefRequest.setCommunicationPreferences(compref);
		
		request.getComunicationPreferencesRequest().add(comprefRequest);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		Assert.assertNotNull(response);		
		Assert.assertTrue(response.isSuccess());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult =
				communicationPreferencesRepository.findByGin(GIN_PROMOALERT);
		for (com.airfrance.repind.entity.individu.CommunicationPreferences cp : cpResult) {
			if ("P".equals(cp.getComGroupType())) {
				Assert.assertEquals("S", cp.getDomain());
				Assert.assertEquals("P", cp.getComGroupType());
				Assert.assertEquals("AF", cp.getComType());
				Assert.assertEquals("N", cp.getSubscribe());		// On verifie que c est bien N !
				
				if (cp.getMarketLanguage() != null) {
					
					for (com.airfrance.repind.entity.individu.MarketLanguage ml : cp.getMarketLanguage()) {
						
						Assert.assertEquals("N", ml.getOptIn());			
					}
				}
			}
		}
		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}

	@Test
	@Transactional
	public void testCreateOrUpdateIndividual_OptOut_PromoAlert_MarketLang() throws JrafDaoException, SOAPException {

		String GIN_PROMOALERT = "400518107333";
		
		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_PROMOALERT);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);
		
		ComunicationPreferencesRequest comprefRequest = new ComunicationPreferencesRequest();

		CommunicationPreferences compref = new CommunicationPreferences();
		compref.setDomain("S");
		compref.setCommunicationGroupeType("P");
		compref.setCommunicationType("AF");
		compref.setOptIn("Y");
		
		MarketLanguage mlA = new MarketLanguage();
		mlA.setLanguage(LanguageCodesEnum.FR);
		mlA.setMarket("FR");
		mlA.setOptIn("N");
		compref.getMarketLanguage().add(mlA);
				
		comprefRequest.setCommunicationPreferences(compref);
		
		request.getComunicationPreferencesRequest().add(comprefRequest);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
			// Assert.fail("On devrait avoir une erreur d inconsistence entre Y de COM PREF et N de MARK LANG");
		} catch (BusinessErrorBlocBusinessException e) {
			// On crash une erreur 382 a cause de la consistence de ML sur CP  
			// Assert.assertEquals("ERROR_932", e.getFaultInfo().getBusinessError().getErrorCode().toString());
			// Assert.assertEquals("INVALID PARAMETER", e.getFaultInfo().getBusinessError().getErrorLabel());
			// Assert.assertEquals("Invalid parameter: global optin is not consistent with market languages optin", e.getFaultInfo().getBusinessError().getErrorDetail());
		}
	
		Assert.assertNotNull(response);		
		Assert.assertTrue(response.isSuccess());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin(GIN_PROMOALERT);
		for (com.airfrance.repind.entity.individu.CommunicationPreferences cp : cpResult) {
			if ("P".equals(cp.getComGroupType())) {
				Assert.assertEquals("S", cp.getDomain());
				Assert.assertEquals("P", cp.getComGroupType());
				Assert.assertEquals("AF", cp.getComType());
				Assert.assertEquals("N", cp.getSubscribe());		// On verifie que c est bien N !
				
				if (cp.getMarketLanguage() != null) {
					
					for (com.airfrance.repind.entity.individu.MarketLanguage ml : cp.getMarketLanguage()) {
						
						Assert.assertEquals("N", ml.getOptIn());			
					}
				}
			}
		}
		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}
	
	
	
	
	@Test
	@Transactional
	public void testCreateOrUpdateIndividual_OptOut_PromoAlert_MarketLang_ToY() throws JrafDaoException, SOAPException {

		String GIN_PROMOALERT = "400518107333";
		
		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_PROMOALERT);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);
		
		ComunicationPreferencesRequest comprefRequest = new ComunicationPreferencesRequest();

		CommunicationPreferences compref = new CommunicationPreferences();
		compref.setDomain("S");
		compref.setCommunicationGroupeType("P");
		compref.setCommunicationType("AF");
		compref.setOptIn("N");
		
		MarketLanguage mlA = new MarketLanguage();
		mlA.setLanguage(LanguageCodesEnum.FR);
		mlA.setMarket("FR");
		mlA.setOptIn("Y");
		compref.getMarketLanguage().add(mlA);
				
		comprefRequest.setCommunicationPreferences(compref);
		
		request.getComunicationPreferencesRequest().add(comprefRequest);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
			// Assert.fail("On devrait avoir une erreur d inconsistence entre Y de COM PREF et N de MARK LANG");
		} catch (BusinessErrorBlocBusinessException e) {
			// On crash une erreur 382 a cause de la consistence de ML sur CP  
			// Assert.assertEquals("ERROR_932", e.getFaultInfo().getBusinessError().getErrorCode().toString());
			// Assert.assertEquals("INVALID PARAMETER", e.getFaultInfo().getBusinessError().getErrorLabel());
			// Assert.assertEquals("Invalid parameter: global optin is not consistent with market languages optin", e.getFaultInfo().getBusinessError().getErrorDetail());
		}
	
		Assert.assertNotNull(response);		
		Assert.assertTrue(response.isSuccess());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin(GIN_PROMOALERT);
		for (com.airfrance.repind.entity.individu.CommunicationPreferences cp : cpResult) {
			if ("P".equals(cp.getComGroupType())) {
				Assert.assertEquals("S", cp.getDomain());
				Assert.assertEquals("P", cp.getComGroupType());
				Assert.assertEquals("AF", cp.getComType());
				Assert.assertEquals("Y", cp.getSubscribe());		// On verifie que c est bien N !
				
				if (cp.getMarketLanguage() != null) {
					
					for (com.airfrance.repind.entity.individu.MarketLanguage ml : cp.getMarketLanguage()) {
						
						Assert.assertEquals("Y", ml.getOptIn());			
					}
				}
			}
		}
		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}	
	
	
	
	
	@Test
//	@Transactional
	public void testCreateOrUpdateIndividual_OptOut_ComPref_Ultimate() throws JrafDaoException, SOAPException {

		String GIN_ULTI = "400518995600";
		
		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");

		
		
		
		
		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult =
				communicationPreferencesRepository.findByGin(GIN_ULTI);
		for (com.airfrance.repind.entity.individu.CommunicationPreferences cp : cpResult) {
			if ("S".equals(cp.getComGroupType())) {
				Assert.assertEquals("U", cp.getDomain());
				Assert.assertEquals("S", cp.getComGroupType());
				Assert.assertEquals("UL_PS", cp.getComType());
				Assert.assertEquals("N", cp.getSubscribe());		// On verifie que c est bien N !
				
				if (cp.getMarketLanguage() != null) {
					
					for (com.airfrance.repind.entity.individu.MarketLanguage ml : cp.getMarketLanguage()) {
						
						Assert.assertEquals("N", ml.getOptIn());			
					}
				}
				
			}
		}
		
		
		
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_ULTI);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);
		
		ComunicationPreferencesRequest comprefRequest = new ComunicationPreferencesRequest();

		CommunicationPreferences compref = new CommunicationPreferences();
		compref.setDomain("U");
		compref.setCommunicationGroupeType("S");
		compref.setCommunicationType("UL_PS");
		
		compref.setDateOfConsent(new Date());
		
		compref.setOptIn("Y");
		
		MarketLanguage mlA = new MarketLanguage();
		mlA.setLanguage(LanguageCodesEnum.EN);
		mlA.setDateOfConsent(new Date());
		mlA.setMarket("FR");
		mlA.setOptIn("Y");
		compref.getMarketLanguage().add(mlA);
				
		comprefRequest.setCommunicationPreferences(compref);
		
		request.getComunicationPreferencesRequest().add(comprefRequest);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		Assert.assertNotNull(response);		
		Assert.assertTrue(response.isSuccess());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult2 =
				communicationPreferencesRepository.findByGin(GIN_ULTI);
		for (com.airfrance.repind.entity.individu.CommunicationPreferences cp : cpResult2) {
			if ("S".equals(cp.getComGroupType())) {
				Assert.assertEquals("U", cp.getDomain());
				Assert.assertEquals("S", cp.getComGroupType());
				Assert.assertEquals("UL_PS", cp.getComType());
				Assert.assertEquals("Y", cp.getSubscribe());		// On verifie que c est bien N !
				
				if (cp.getMarketLanguage() != null) {
					
					for (com.airfrance.repind.entity.individu.MarketLanguage ml : cp.getMarketLanguage()) {
						
						Assert.assertEquals("Y", ml.getOptIn());			
					}
				}
				
			}
		}

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}

	
	@Test
	// @Transactional
	@Rollback(false)
	@Ignore			 // Waiting the rules have been defined by AMO or PO
	public void testCreateOrUpdateIndividual_UpdateEmailTelecom_BUG() throws JrafDaoException {

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("DOCT");
		requestor.setSite("QVI");
		requestor.setSignature("SCF");

		request.setRequestor(requestor);
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("400350559695");

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);
		

		Telecom telecom = new Telecom();
		telecom.setPhoneNumber("0102003405");
		telecom.setCountryCode("+33");
		telecom.setMediumCode("P");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("M");

		TelecomRequest telecomRequest = new TelecomRequest();
		telecomRequest.setTelecom(telecom);

		request.getTelecomRequest().add(telecomRequest);

		
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		email.setEmail("marjolein.peters-bossen@klm.com");
		email.setMediumCode("P");
		email.setMediumStatus("V");
		email.setEmailOptin("T");
		emailRequest.setEmail(email);
		request.getEmailRequest().add(emailRequest);
		request.setIndividualRequest(indRequest);
		
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		Assert.assertNotNull(response);		
		Assert.assertTrue(response.isSuccess());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin("400350559695");
		Assert.assertEquals(4, cpResult.size());

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}

	
	@Test
	@Rollback(false)
	public void testCreateOrUpdateIndividual_Update_DelegationData_BasedOnComPref_DeletagorUltimate() throws JrafDaoException, SOAPException {

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("APPS");
		requestor.setSite("AMS");
		requestor.setSignature("CustomerAPI");
		requestor.setApplicationCode("MAC");
		request.setRequestor(requestor);
		
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("400518995600");
		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		
		AccountDelegationDataRequest addr = new AccountDelegationDataRequest();
		Delegator d = new Delegator();
		DelegationData dd = new DelegationData();
		dd.setGin("400368637766");
		dd.setDelegationAction("D");
		dd.setDelegationType("UF");
		d.setDelegationData(dd);
		addr.getDelegator().add(d);
		request.setAccountDelegationDataRequest(addr);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		Assert.assertNotNull(response);		
		Assert.assertTrue(response.isSuccess());

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}
	
	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividual_ComPref_U_MarketLanguage() throws JrafDaoException, SOAPException {
		String GIN_ULTI = "400518995600";
		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test start...");
		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository
				.findByGin(GIN_ULTI);
		for (com.airfrance.repind.entity.individu.CommunicationPreferences cp : cpResult) {
			if ("S".equals(cp.getComGroupType())) {
				Assert.assertEquals("U", cp.getDomain());
				Assert.assertEquals("S", cp.getComGroupType());
				Assert.assertEquals("UL_PS", cp.getComType());
				Assert.assertEquals(0, cp.getMarketLanguage().size());
			}
		}

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_ULTI);

		IndividualRequest indRequest = new IndividualRequest();
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		ComunicationPreferencesRequest comprefRequest = new ComunicationPreferencesRequest();

		CommunicationPreferences compref = new CommunicationPreferences();
		compref.setDomain("U");
		compref.setCommunicationGroupeType("S");
		compref.setCommunicationType("UL_PS");
		compref.setDateOfConsent(new Date());
		compref.setOptIn("Y");
		MarketLanguage mlA = new MarketLanguage();
		mlA.setLanguage(LanguageCodesEnum.EN);
		mlA.setDateOfConsent(new Date());
		mlA.setMarket("FR");
		mlA.setOptIn("Y");
		compref.getMarketLanguage().add(mlA);
		comprefRequest.setCommunicationPreferences(compref);
		request.getComunicationPreferencesRequest().add(comprefRequest);
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();

		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}

		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult2 = communicationPreferencesRepository
				.findByGin(GIN_ULTI);
		for (com.airfrance.repind.entity.individu.CommunicationPreferences cp : cpResult2) {
			if ("S".equals(cp.getComGroupType())) {
				Assert.assertEquals("U", cp.getDomain());
				Assert.assertEquals("S", cp.getComGroupType());
				Assert.assertEquals("UL_PS", cp.getComType());
				for (com.airfrance.repind.entity.individu.MarketLanguage ml : cp.getMarketLanguage()) {
					Assert.assertEquals(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE, ml.getCreationSite());
					Assert.assertEquals(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE,
							ml.getCreationSignature());
					Assert.assertEquals(CreateOrUpdateAnIndividualImplV8ForComprefTest.SITE, ml.getModificationSite());
					Assert.assertEquals(CreateOrUpdateAnIndividualImplV8ForComprefTest.SIGNATURE,
							ml.getModificationSignature());
				}
			}
		}

		CreateOrUpdateAnIndividualImplV8ForComprefTest.logger.info("Test stop.");
	}

	@Test
	@Rollback(true)
	public void testDeleteUltimate() throws JrafDaoException, SOAPException {
		logger.info("Test start...");

		final String gin = "110000014901";
		final String domain = "U";
		final String groupType = "S";
		final String type = "UL_PS";
		final String optin = "X";

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		communicationPreferencesDS.createUltimateCompref(gin);

		try {
			List<CommunicationPreferencesDTO> compPrefList = communicationPreferencesDS.findComPrefIdByDomain(gin,
					domain);
			Assert.assertEquals(1, compPrefList.size());
			Assert.assertEquals(domain, compPrefList.get(0).getDomain());

			RequestorV2 requestor = new RequestorV2();
			requestor.setChannel("APPS");
			requestor.setSite("AMS");
			requestor.setSignature("CustomerAPI");
			requestor.setApplicationCode("MAC");
			request.setRequestor(requestor);

			IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			indInfo.setIdentifier(gin);
			IndividualRequest indRequest = new IndividualRequest();
			indRequest.setIndividualInformations(indInfo);
			request.setIndividualRequest(indRequest);

			ComunicationPreferencesRequest comPrefRequest = new ComunicationPreferencesRequest();
			CommunicationPreferences comPref = new CommunicationPreferences();
			comPref.setDomain(domain);
			comPref.setCommunicationGroupeType(groupType);
			comPref.setCommunicationType(type);
			comPref.setOptIn(optin);
			comPrefRequest.setCommunicationPreferences(comPref);
			request.getComunicationPreferencesRequest().add(comPrefRequest);

			CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.assertNotNull(response);
			Assert.assertTrue(response.isSuccess());

			compPrefList = communicationPreferencesDS.findComPrefIdByDomain(gin, domain);
			Assert.assertTrue(compPrefList.isEmpty());

		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail(e.getMessage());

		} catch (JrafDomainException e) {
			logger.info(e.getMessage());
			Assert.fail(e.getMessage());
		}

		logger.info("Test stop.");
	}

	private int countComprefByMarketLanguage(List<com.airfrance.repind.entity.individu.CommunicationPreferences> comprefs, String market, String language) {
		int result = 0;
		
		for (com.airfrance.repind.entity.individu.CommunicationPreferences cp : comprefs) {
			Set<com.airfrance.repind.entity.individu.MarketLanguage> marketLanguage = cp.getMarketLanguage();
			if(marketLanguage != null){
				for (com.airfrance.repind.entity.individu.MarketLanguage ml : marketLanguage) {
					if (market.equalsIgnoreCase(ml.getMarket()) && language.equalsIgnoreCase(ml.getLanguage())) {
						result++;
					}
				}
			}
		}
		
		return result;
	}

	private CreateUpdateIndividualRequest generateMandatoryRequest() {

		CreateUpdateIndividualRequest ret =  new CreateUpdateIndividualRequest();

		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		ret.setRequestor(requestor);

		ret.setProcess(ProcessEnum.A.getCode());

		return ret;
	}
}
