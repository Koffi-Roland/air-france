package com.afklm.repind.v6.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v6.createorupdateindividualws.CreateOrUpdateIndividualImplV6;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000442.v6.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v6.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v6.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v6.request.ComunicationPreferencesRequest;
import com.afklm.soa.stubs.w000442.v6.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v6.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000442.v6.siccommonenum.LanguageCodesEnum;
import com.afklm.soa.stubs.w000442.v6.siccommontype.Requestor;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.CommunicationPreferences;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.IndividualInformations;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.MarketLanguage;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
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

import javax.xml.soap.SOAPException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplForComprefTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplForComprefTest.class);
	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String GIN = "400263952140";

	@Autowired
	private IndividuDS individuDS;

	@Autowired
	private CommunicationPreferencesRepository communicationPreferencesRepository;

	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v6Bean")
	private CreateOrUpdateIndividualImplV6 createOrUpdateIndividualImplV6;

	@Before
	@Transactional
	@Rollback(false)
	public void beforeTest() throws JrafDomainException, SOAPException {		
		
		IndividuDTO individu = individuDS.getAllByGin(GIN);
		
		individu.getCommunicationpreferencesdto().clear();
		
		CommunicationPreferencesDTO compref_FB_ESS = new CommunicationPreferencesDTO();
		compref_FB_ESS.setGin(GIN);
		compref_FB_ESS.setChannel("B2C");
		compref_FB_ESS.setComGroupType("N");
		compref_FB_ESS.setComType("FB_ESS");
		compref_FB_ESS.setCreationDate(new Date());
		compref_FB_ESS.setCreationSignature("TESTMZ");
		compref_FB_ESS.setCreationSite("QVI");
		compref_FB_ESS.setDateOptin(new Date());
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
		ml_FB_ESS.setDateOfConsent(new Date());
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
		compref_FB_NEWS.setGin(GIN);
		compref_FB_NEWS.setChannel("B2C");
		compref_FB_NEWS.setComGroupType("N");
		compref_FB_NEWS.setComType("FB_NEWS");
		compref_FB_NEWS.setCreationDate(new Date());
		compref_FB_NEWS.setCreationSignature("TESTMZ");
		compref_FB_NEWS.setCreationSite("QVI");
		compref_FB_NEWS.setDateOptin(new Date());
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
		ml_FB_NEWS.setDateOfConsent(new Date());
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
		compref_FB_PART.setGin(GIN);
		compref_FB_PART.setChannel("B2C");
		compref_FB_PART.setComGroupType("N");
		compref_FB_PART.setComType("FB_PART");
		compref_FB_PART.setCreationDate(new Date());
		compref_FB_PART.setCreationSignature("TESTMZ");
		compref_FB_PART.setCreationSite("QVI");
		compref_FB_PART.setDateOptin(new Date());
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
		ml_FB_PART.setDateOfConsent(new Date());
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
		
		CommunicationPreferencesDTO compref_S_AF = new CommunicationPreferencesDTO();
		compref_S_AF.setGin(GIN);
		compref_S_AF.setChannel("B2C");
		compref_S_AF.setComGroupType("N");
		compref_S_AF.setComType("AF");
		compref_S_AF.setCreationDate(new Date());
		compref_S_AF.setCreationSignature("TESTMZ");
		compref_S_AF.setCreationSite("QVI");
		compref_S_AF.setDateOptin(new Date());
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
		ml_S_AF.setDateOfConsent(new Date());
		ml_S_AF.setLanguage("FR");
		ml_S_AF.setMarket("FR");
		ml_S_AF.setMedia1("E");
		ml_S_AF.setModificationDate(new Date());
		ml_S_AF.setModificationSignature("TESTMZ");
		ml_S_AF.setModificationSite("QVI");
		ml_S_AF.setOptIn("Y");
		
		compref_S_AF.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		compref_S_AF.getMarketLanguageDTO().add(ml_S_AF);
		
		individu.getCommunicationpreferencesdto().add(compref_S_AF);
		
		individuDS.updateComPrefOfIndividual(individu);
	}
	

	@Test
	public void testCreateOrUpdateIndividual_UpdateOneCompref_FB() throws JrafDaoException, SOAPException, SystemException {

		CreateOrUpdateAnIndividualImplForComprefTest.logger.info("Test start...");
	
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		Requestor requestor = new Requestor();
		requestor.setChannel(CreateOrUpdateAnIndividualImplForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setIdentifier(GIN);

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
		
		CreateUpdateIndividualResponse response = null;
		
		try {
			response = createOrUpdateIndividualImplV6.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, e.getFaultInfo().getBusinessError().getErrorCode());
			Assert.assertEquals("Invalid parameter: Unknown domain, group type, or type", e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.assertEquals("INVALID PARAMETER", e.getFaultInfo().getBusinessError().getErrorLabel());
		}
		
		Assert.assertNull(response);
		
		CreateOrUpdateAnIndividualImplForComprefTest.logger.info("Test stop.");
	}
	
	@Test
	public void testCreateOrUpdateIndividual_UpdateMultipleCompref_FB() throws JrafDaoException, SystemException {

		CreateOrUpdateAnIndividualImplForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		Requestor requestor = new Requestor();
		requestor.setChannel(CreateOrUpdateAnIndividualImplForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setIdentifier(GIN);

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
		
		CreateUpdateIndividualResponse response = null;
		
		try {
			response = createOrUpdateIndividualImplV6.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, e.getFaultInfo().getBusinessError().getErrorCode());
			Assert.assertEquals("Invalid parameter: Unknown domain, group type, or type", e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.assertEquals("INVALID PARAMETER", e.getFaultInfo().getBusinessError().getErrorLabel());
		}
		
		Assert.assertNull(response);	

		CreateOrUpdateAnIndividualImplForComprefTest.logger.info("Test stop.");
	}
	
	@Test
	public void testCreateOrUpdateIndividual_UpdateAllCompref_FB() throws JrafDaoException, SOAPException, SystemException {

		CreateOrUpdateAnIndividualImplForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		Requestor requestor = new Requestor();
		requestor.setChannel(CreateOrUpdateAnIndividualImplForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setIdentifier(GIN);

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
		
		CreateUpdateIndividualResponse response = null;
		
		try {
			response = createOrUpdateIndividualImplV6.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, e.getFaultInfo().getBusinessError().getErrorCode());
			Assert.assertEquals("Invalid parameter: Unknown domain, group type, or type", e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.assertEquals("INVALID PARAMETER", e.getFaultInfo().getBusinessError().getErrorLabel());
		}
		
		Assert.assertNull(response);	

		CreateOrUpdateAnIndividualImplForComprefTest.logger.info("Test stop.");
	}
	
	@Test
	public void testCreateOrUpdateIndividual_UpdateOneCompref_S() throws JrafDaoException, SystemException {

		CreateOrUpdateAnIndividualImplForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		Requestor requestor = new Requestor();
		requestor.setChannel(CreateOrUpdateAnIndividualImplForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setIdentifier(GIN);

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
			response = createOrUpdateIndividualImplV6.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		Assert.assertNotNull(response);		
		Assert.assertTrue(response.isSuccess());

		List<com.airfrance.repind.entity.individu.CommunicationPreferences> cpResult = communicationPreferencesRepository.findByGin(GIN);
		Assert.assertEquals(4, countComprefByMarketLanguage(cpResult, "FR", "FR"));
		Assert.assertEquals(1, countComprefByMarketLanguage(cpResult, "IT", "IT"));		

		CreateOrUpdateAnIndividualImplForComprefTest.logger.info("Test stop.");
	}
	
	@Test
	public void testCreateOrUpdateIndividual_UpdateOneCompref_FB_And_OneCompref_S() throws JrafDaoException, SystemException {

		CreateOrUpdateAnIndividualImplForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		Requestor requestor = new Requestor();
		requestor.setChannel(CreateOrUpdateAnIndividualImplForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setIdentifier(GIN);

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
		
		CreateUpdateIndividualResponse response = null;
		
		try {
			response = createOrUpdateIndividualImplV6.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, e.getFaultInfo().getBusinessError().getErrorCode());
			Assert.assertEquals("Invalid parameter: Unknown domain, group type, or type", e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.assertEquals("INVALID PARAMETER", e.getFaultInfo().getBusinessError().getErrorLabel());
		}
		
		Assert.assertNull(response);	

		CreateOrUpdateAnIndividualImplForComprefTest.logger.info("Test stop.");
	}
	
	@Test
	public void testCreateOrUpdateIndividual_Error_MultipleMarketLanguage() throws JrafDaoException, SOAPException, SystemException {

		CreateOrUpdateAnIndividualImplForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		Requestor requestor = new Requestor();
		requestor.setChannel(CreateOrUpdateAnIndividualImplForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setIdentifier(GIN);

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
		
		CreateUpdateIndividualResponse response = null;
		
		try {
			response = createOrUpdateIndividualImplV6.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, e.getFaultInfo().getBusinessError().getErrorCode());
			Assert.assertEquals("Invalid parameter: Unknown domain, group type, or type", e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.assertEquals("INVALID PARAMETER", e.getFaultInfo().getBusinessError().getErrorLabel());
		}
		
		Assert.assertNull(response);

		CreateOrUpdateAnIndividualImplForComprefTest.logger.info("Test stop.");
	}
	
	@Test
	public void testCreateOrUpdateIndividual_Error_DefaultLanguage() throws JrafDaoException, SOAPException, SystemException {

		CreateOrUpdateAnIndividualImplForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		Requestor requestor = new Requestor();
		requestor.setChannel(CreateOrUpdateAnIndividualImplForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setIdentifier(GIN);

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
		
		CreateUpdateIndividualResponse response = null;
		
		try {
			response = createOrUpdateIndividualImplV6.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, e.getFaultInfo().getBusinessError().getErrorCode());
			Assert.assertEquals("Invalid parameter: Unknown domain, group type, or type", e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.assertEquals("INVALID PARAMETER", e.getFaultInfo().getBusinessError().getErrorLabel());
		}
		
		Assert.assertNull(response);	

		CreateOrUpdateAnIndividualImplForComprefTest.logger.info("Test stop.");
	}
	
	@Test
	public void testCreateOrUpdateIndividual_Error_WrongConsumer() throws JrafDaoException, SOAPException, SystemException {

		CreateOrUpdateAnIndividualImplForComprefTest.logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	
		Requestor requestor = new Requestor();
		requestor.setChannel(CreateOrUpdateAnIndividualImplForComprefTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplForComprefTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplForComprefTest.SIGNATURE);

		request.setRequestor(requestor);
		
		IndividualInformations indInfo = new IndividualInformations();
		indInfo.setIdentifier(GIN);

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
		
		CreateUpdateIndividualResponse response = null;
		
		try {
			response = createOrUpdateIndividualImplV6.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, e.getFaultInfo().getBusinessError().getErrorCode());
			Assert.assertEquals("Invalid parameter: Unknown domain, group type, or type", e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.assertEquals("INVALID PARAMETER", e.getFaultInfo().getBusinessError().getErrorLabel());
		}
		
		Assert.assertNull(response);	

		CreateOrUpdateAnIndividualImplForComprefTest.logger.info("Test stop.");
	}
	
	private int countComprefByMarketLanguage(List<com.airfrance.repind.entity.individu.CommunicationPreferences> comprefs, String market, String language) {
		int result = 0;
		
		for (com.airfrance.repind.entity.individu.CommunicationPreferences cp : comprefs) {
			for (com.airfrance.repind.entity.individu.MarketLanguage ml : cp.getMarketLanguage()) {
				if (market.equalsIgnoreCase(ml.getMarket()) && language.equalsIgnoreCase(ml.getLanguage())) {
					result++;
				}
			}
		}
		
		return result;
	}
}
