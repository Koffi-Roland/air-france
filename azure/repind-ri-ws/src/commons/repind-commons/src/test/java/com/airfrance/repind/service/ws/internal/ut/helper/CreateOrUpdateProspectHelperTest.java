package com.airfrance.repind.service.ws.internal.ut.helper;

import com.airfrance.ref.exception.ReconciliationProcessException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.IndividualMulticriteriaDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.SearchIndividualByMulticriteriaRequestDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.SearchIndividualByMulticriteriaResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.InformationResponseDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.dto.ws.EmailDTO;
import com.airfrance.repind.dto.ws.TelecomDTO;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.MarketLanguage;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import com.airfrance.repind.service.ws.internal.helpers.CommunicationPreferencesHelper;
import com.airfrance.repind.service.ws.internal.helpers.CreateOrUpdateProspectHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateProspectHelperTest extends CreateOrUpdateProspectHelper {
	
	@Autowired 
	private IndividuRepository individuRepository;
		
	@Autowired
	private CreateOrUpdateProspectHelper createOrUpdateProspectHelper;


	private static final Log logger = LogFactory.getLog(CreateOrUpdateProspectHelperTest.class);
	
	// Initialization : done before each test case annotated @Test
	@Before
	public void setUp() throws JrafDomainException {
		individuDS = EasyMock.createMock(IndividuDS.class);
		myAccountDS = EasyMock.createMock(MyAccountDS.class);
		telecomDS = EasyMock.createMock(TelecomDS.class);
		communicationPreferencesHelper = EasyMock.createNiceMock(CommunicationPreferencesHelper.class);
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperFindIndividual() throws JrafDomainException {
		
		logger.info("Start test");
		
		String mail = generateEmail();
		
		List<ProspectIndividuDTO> prospectIndDTOList = new ArrayList<ProspectIndividuDTO>();
		
		ProspectIndividuDTO prospectIndDTO = createProspectIndividuDTO(mail, "400518839290", "contractNumberTest");
		
		prospectIndDTOList.add(prospectIndDTO);
		
		EasyMock.expect(myAccountDS.findAnIndividualByEmail(mail)).andReturn(prospectIndDTOList);
		EasyMock.replay(myAccountDS);
		
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		RequestorDTO requestorV2 = new RequestorDTO();
		request.setRequestorDTO(requestorV2);
		EmailRequestDTO emailRequest = new EmailRequestDTO();
		EmailDTO emailProspect = new EmailDTO();
		emailProspect.setEmail(mail);
		emailRequest.setEmailDTO(emailProspect);
		request.setEmailRequestDTO(new ArrayList<EmailRequestDTO>());
		request.getEmailRequestDTO().add(emailRequest);
		
		ProspectIndividuDTO returnProspectInd = findIndividual(request);
		Assert.assertNotNull(returnProspectInd);
		Assert.assertEquals(prospectIndDTO.getCin(), returnProspectInd.getCin());
		Assert.assertEquals(prospectIndDTO.getGin(), returnProspectInd.getGin());
		Assert.assertEquals(mail, returnProspectInd.getEmail());
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperFindIndividualReconciliationByGin() throws JrafDomainException {
		
		logger.info("Start test");
		
		String mail = generateEmail();
		
		List<ProspectIndividuDTO> prospectIndDTOList = new ArrayList<ProspectIndividuDTO>();
		
		ProspectIndividuDTO prospectIndDTO = createProspectIndividuDTO(mail, "400518839290", "contractNumberTest");
		ProspectIndividuDTO prospectIndDTO2 = createProspectIndividuDTO(mail, "400518839111", "contractNumberTest2");
		ProspectIndividuDTO prospectIndDTO3 = createProspectIndividuDTO(mail, "400518839112", "contractNumberTest3");
		ProspectIndividuDTO prospectIndDTO4 = createProspectIndividuDTO(mail, "400518839113", "contractNumberTest4");
		
		prospectIndDTOList.add(prospectIndDTO);
		prospectIndDTOList.add(prospectIndDTO2);
		prospectIndDTOList.add(prospectIndDTO3);
		prospectIndDTOList.add(prospectIndDTO4);
		
		EasyMock.expect(myAccountDS.findAnIndividualByEmail(mail)).andReturn(prospectIndDTOList);
		EasyMock.replay(myAccountDS);
		
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		RequestorDTO requestorV2 = new RequestorDTO();
		requestorV2.setLoggedGin(prospectIndDTO3.getGin());
		request.setRequestorDTO(requestorV2);
		EmailRequestDTO emailRequest = new EmailRequestDTO();
		EmailDTO emailProspect = new EmailDTO();
		emailProspect.setEmail(mail);
		emailRequest.setEmailDTO(emailProspect);
		request.setEmailRequestDTO(new ArrayList<EmailRequestDTO>());
		request.getEmailRequestDTO().add(emailRequest);
		
		ProspectIndividuDTO returnProspectInd = findIndividual(request);
		Assert.assertNotNull(returnProspectInd);
		Assert.assertEquals(prospectIndDTO3.getCin(), returnProspectInd.getCin());
		Assert.assertEquals(prospectIndDTO3.getGin(), returnProspectInd.getGin());
		Assert.assertEquals(mail, returnProspectInd.getEmail());
		
		logger.info("End test");
		
	}
	

	@Test
	public void testCreateOrUpdateProspectHelperFindIndividualReconciliationByCin() throws JrafDomainException {
		
		logger.info("Start test");
		
		String mail = generateEmail();
		
		List<ProspectIndividuDTO> prospectIndDTOList = new ArrayList<ProspectIndividuDTO>();
		
		ProspectIndividuDTO prospectIndDTO = createProspectIndividuDTO(mail, "400518839290", "contractNumberTest");
		ProspectIndividuDTO prospectIndDTO2 = createProspectIndividuDTO(mail, "400518839111", "contractNumberTest2");
		ProspectIndividuDTO prospectIndDTO3 = createProspectIndividuDTO(mail, "400518839112", "contractNumberTest3");
		ProspectIndividuDTO prospectIndDTO4 = createProspectIndividuDTO(mail, "400518839113", "contractNumberTest4");
		
		prospectIndDTOList.add(prospectIndDTO);
		prospectIndDTOList.add(prospectIndDTO2);
		prospectIndDTOList.add(prospectIndDTO3);
		prospectIndDTOList.add(prospectIndDTO4);
		
		EasyMock.expect(myAccountDS.findAnIndividualByEmail(mail)).andReturn(prospectIndDTOList);
		EasyMock.replay(myAccountDS);
		
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		RequestorDTO requestorV2 = new RequestorDTO();
		requestorV2.setReconciliationDataCIN(prospectIndDTO3.getCin());
		request.setRequestorDTO(requestorV2);
		EmailRequestDTO emailRequest = new EmailRequestDTO();
		EmailDTO emailProspect = new EmailDTO();
		emailProspect.setEmail(mail);
		emailRequest.setEmailDTO(emailProspect);
		request.setEmailRequestDTO(new ArrayList<EmailRequestDTO>());
		request.getEmailRequestDTO().add(emailRequest);
		
		ProspectIndividuDTO returnProspectInd = findIndividual(request);
		Assert.assertNotNull(returnProspectInd);
		Assert.assertEquals(prospectIndDTO3.getCin(), returnProspectInd.getCin());
		Assert.assertEquals(prospectIndDTO3.getGin(), returnProspectInd.getGin());
		Assert.assertEquals(mail, returnProspectInd.getEmail());
		
		logger.info("End test");
		
	}

	@Test
	public void testCreateOrUpdateProspectHelperFindIndividualReconciliationProcessException() throws JrafDomainException {
		
		logger.info("Start test");
		
		String mail = generateEmail();
		
		List<ProspectIndividuDTO> prospectIndDTOList = new ArrayList<ProspectIndividuDTO>();
		
		ProspectIndividuDTO prospectIndDTO = createProspectIndividuDTO(mail, "400518839290", "contractNumberTest");
		ProspectIndividuDTO prospectIndDTO2 = createProspectIndividuDTO(mail, "400518839111", "contractNumberTest2");
		ProspectIndividuDTO prospectIndDTO3 = createProspectIndividuDTO(mail, "400518839112", "contractNumberTest3");
		
		prospectIndDTOList.add(prospectIndDTO);
		prospectIndDTOList.add(prospectIndDTO2);
		prospectIndDTOList.add(prospectIndDTO3);
		
		EasyMock.expect(myAccountDS.findAnIndividualByEmail(mail)).andReturn(prospectIndDTOList);
		EasyMock.replay(myAccountDS);
		
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		RequestorDTO requestorV2 = new RequestorDTO();
		requestorV2.setContext("B2C_HOME_PAGE");
		request.setRequestorDTO(requestorV2);
		EmailRequestDTO emailRequest = new EmailRequestDTO();
		EmailDTO emailProspect = new EmailDTO();
		emailProspect.setEmail(mail);
		emailRequest.setEmailDTO(emailProspect);
		request.setEmailRequestDTO(new ArrayList<EmailRequestDTO>());
		request.getEmailRequestDTO().add(emailRequest);
		
		boolean reconciliationException = false;
		try {
			findIndividual(request);
		} catch (ReconciliationProcessException e) {
			reconciliationException = true;
		}
		
		Assert.assertTrue(reconciliationException);
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperUpdateIndividualProspect() throws JrafDomainException {
		
		logger.info("Start test");
		
		String mail = generateEmail();
		
		List<ProspectIndividuDTO> prospectIndDTOList = new ArrayList<ProspectIndividuDTO>();
		
		ProspectIndividuDTO prospectIndDTO = createProspectIndividuDTO(mail, "400518839290", "contractNumberTest");
		
		prospectIndDTOList.add(prospectIndDTO);
		
		EasyMock.expect(myAccountDS.findAnIndividualByEmail(mail)).andReturn(prospectIndDTOList);
		EasyMock.replay(myAccountDS);
		
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		RequestorDTO requestorV2 = new RequestorDTO();
		requestorV2.setContext("B2C_HOME_PAGE");
		request.setRequestorDTO(requestorV2);
		
		IndividualRequestDTO indRequest = new IndividualRequestDTO();
		IndividualInformationsDTO indInformations = new IndividualInformationsDTO();
		indInformations.setNationality("FR");
		indInformations.setSecondNationality("EN");
		indInformations.setMiddleNameSC("TestName");

		IndividualProfilDTO indProfil = new IndividualProfilDTO();
		indProfil.setLanguageCode("FR");
		indRequest.setIndividualProfilDTO(indProfil);
		indRequest.setIndividualInformationsDTO(indInformations);
		request.setIndividualRequestDTO(indRequest);
		
		CommunicationPreferencesRequestDTO comPrefRequest = new CommunicationPreferencesRequestDTO();
		
		MediaDTO media = new MediaDTO();
		media.setMedia1("E");
		
		com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType("AF");
		comPref.setDomain("S");
		comPref.setMediaDTO(media);
		comPref.setOptIn("Y");
		com.airfrance.repind.dto.ws.MarketLanguageDTO ml = new com.airfrance.repind.dto.ws.MarketLanguageDTO();
		ml.setMarket("FR");
		ml.setLanguage("FR");
		ml.setMediaDTO(media);
		ml.setOptIn("Y");
		
		comPref.setMarketLanguageDTO(new ArrayList<com.airfrance.repind.dto.ws.MarketLanguageDTO>());
		
		comPref.getMarketLanguageDTO().add(ml);
		
		comPrefRequest.setCommunicationPreferencesDTO(comPref);
		
		request.setCommunicationPreferencesRequestDTO(new ArrayList<CommunicationPreferencesRequestDTO>());
		request.getCommunicationPreferencesRequestDTO().add(comPrefRequest);
		
		TelecomRequestDTO telecomRequest = new TelecomRequestDTO();
		TelecomDTO telecom = new TelecomDTO();
		telecom.setCountryCode("FR");
		telecom.setTerminalType("T");
		telecom.setMediumCode("D");
		telecom.setMediumStatus("V");
		telecom.setPhoneNumber("0658794565");
		telecomRequest.setTelecomDTO(telecom);
		request.setTelecomRequestDTO(new ArrayList<TelecomRequestDTO>());
		request.getTelecomRequestDTO().add(telecomRequest);
		
		PreferenceRequestDTO prefRequest = new PreferenceRequestDTO();
		PreferenceDTO pref = new PreferenceDTO();
		pref.setType("TPC");
		
		PreferenceDatasDTO prefDatasV2 = new PreferenceDatasDTO();
		
		PreferenceDataDTO prefDataV2 = new PreferenceDataDTO();
		prefDataV2.setKey("PREFERREDAIRPORT");
		prefDataV2.setValue("NCE");
		
		prefDatasV2.setPreferenceDataDTO(new ArrayList<PreferenceDataDTO>());
		prefDatasV2.getPreferenceDataDTO().add(prefDataV2);
		
		pref.setPreferencesDatasDTO(prefDatasV2);
		prefRequest.setPreferenceDTO(new ArrayList<PreferenceDTO>());
		prefRequest.getPreferenceDTO().add(pref);
		
		request.setPreferenceRequestDTO(prefRequest);
		
		EmailRequestDTO emailRequest = new EmailRequestDTO();
		EmailDTO emailProspect = new EmailDTO();
		emailProspect.setEmail(mail);
		emailRequest.setEmailDTO(emailProspect);
		request.setEmailRequestDTO(new ArrayList<EmailRequestDTO>());
		request.getEmailRequestDTO().add(emailRequest);
		
		CreateUpdateIndividualRequestDTO createUpdateIndRequest = updateIndividualProspect(prospectIndDTO, request);
		
		// Test requestor
		Assert.assertEquals("B2C", createUpdateIndRequest.getRequestorDTO().getApplicationCode());
		Assert.assertEquals("B2C", createUpdateIndRequest.getRequestorDTO().getChannel());
		
		// Test IndividualRequest
		Assert.assertEquals("FR", createUpdateIndRequest.getIndividualRequestDTO().getIndividualInformationsDTO().getNationality());
		Assert.assertEquals("EN", createUpdateIndRequest.getIndividualRequestDTO().getIndividualInformationsDTO().getSecondNationality());
		Assert.assertEquals("TestName", createUpdateIndRequest.getIndividualRequestDTO().getIndividualInformationsDTO().getSecondFirstName());
		Assert.assertEquals("400518839290", createUpdateIndRequest.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier());
		Assert.assertEquals("FR", createUpdateIndRequest.getIndividualRequestDTO().getIndividualInformationsDTO().getLanguageCode());

		// Test ComPrefRequest
		Assert.assertEquals("AF", createUpdateIndRequest.getCommunicationPreferencesRequestDTO().get(0).getCommunicationPreferencesDTO().getCommunicationType());
		Assert.assertEquals("S", createUpdateIndRequest.getCommunicationPreferencesRequestDTO().get(0).getCommunicationPreferencesDTO().getDomain());
		Assert.assertEquals("FR", createUpdateIndRequest.getCommunicationPreferencesRequestDTO().get(0).getCommunicationPreferencesDTO().getMarketLanguageDTO().get(0).getMarket());

		// Test TelecomRequest
		Assert.assertEquals("0658794565", createUpdateIndRequest.getTelecomRequestDTO().get(0).getTelecomDTO().getPhoneNumber());

		// Test PreferenceRequest
		Assert.assertEquals("NCE", createUpdateIndRequest.getPreferenceRequestDTO().getPreferenceDTO().get(0).getPreferencesDatasDTO().getPreferenceDataDTO().get(0).getValue());
		
		logger.info("End test");
		
	}
	

	@Test
	@Rollback(true)
	public void testCreateOrUpdateProspectHelperUpdateProspectCode4() throws JrafDomainException {
		
		logger.info("Start test");
		
		Individu individuForTest = individuRepository.findBySgin("900045821344");
		individuForTest.getCommunicationpreferences().clear();
	
		CommunicationPreferences communicationPreferencesBO = new CommunicationPreferences();
		communicationPreferencesBO.setChannel("B2C");
		communicationPreferencesBO.setComGroupType("N");
		communicationPreferencesBO.setComType("AF");
		communicationPreferencesBO.setCreationDate(new Date());
		communicationPreferencesBO.setCreationSignature("TESTRI");
		communicationPreferencesBO.setCreationSite("QVI");
		communicationPreferencesBO.setDateOptin(new Date());
		communicationPreferencesBO.setDomain("S");
		communicationPreferencesBO.setGin("900045821344");
		communicationPreferencesBO.setSubscribe("Y");
		communicationPreferencesBO.setMedia1("E");
		
		MarketLanguage marketLanguageBO = new MarketLanguage();
		marketLanguageBO.setCommunicationMedia1("E");
		marketLanguageBO.setCreationDate(new Date());
		marketLanguageBO.setCreationSignature("TESTRI");
		marketLanguageBO.setCreationSite("QVI");
		marketLanguageBO.setDateOfConsent(new Date());
		marketLanguageBO.setLanguage("FR");
		marketLanguageBO.setMarket("FR");
		marketLanguageBO.setOptIn("Y");
		communicationPreferencesBO.setMarketLanguage(new HashSet<MarketLanguage>());
		communicationPreferencesBO.getMarketLanguage().add(marketLanguageBO);
		
		individuForTest.getCommunicationpreferences().add(communicationPreferencesBO);
		individuForTest = individuRepository.saveAndFlush(individuForTest);
		
		individuForTest = individuRepository.findBySgin("900045821344");
		
		IndividuDTO updateProspectDTO = new IndividuDTO();
		IndividuDTO findProspectDTO = IndividuTransform.bo2Dto(individuForTest);
		
		List<CommunicationPreferencesDTO> comPrefDTO = new ArrayList<CommunicationPreferencesDTO>();
		
		updateProspectDTO.setCommunicationpreferencesdto(comPrefDTO);
		
		CreateUpdateIndividualRequestDTO requestDTO = new CreateUpdateIndividualRequestDTO();
		RequestorDTO requestorDTO = new RequestorDTO();
		requestorDTO.setApplicationCode("ISI");
		requestorDTO.setChannel("B2C");
		requestorDTO.setSignature("TESTRI");
		requestorDTO.setSite("QVI");
		requestDTO.setRequestorDTO(requestorDTO);
		
		requestDTO.setUpdateCommunicationPrefMode("U");

		CreateModifyIndividualResponseDTO responseDTO = new CreateModifyIndividualResponseDTO();
		
		
		List<CommunicationPreferencesDTO> listComPref = new ArrayList<CommunicationPreferencesDTO>();
		CommunicationPreferencesDTO comPref = new CommunicationPreferencesDTO();
		comPref.setComType("AF");
		comPref.setComGroupType("N");
		comPref.setDomain("S");
		comPref.setMedia1("E");
		comPref.setSubscribe("Y");
		
		Set<MarketLanguageDTO> setMarketLanguage = new HashSet<MarketLanguageDTO>();
		MarketLanguageDTO marketLanguage = new MarketLanguageDTO();
		marketLanguage.setMarket("FR");
		marketLanguage.setLanguage("FR");
		marketLanguage.setOptIn("Y");
		marketLanguage.setMedia1("E");

		setMarketLanguage.add(marketLanguage);
		comPref.setMarketLanguageDTO(setMarketLanguage);
		listComPref.add(comPref);
		updateProspectDTO.setCommunicationpreferencesdto(listComPref);
			
		createOrUpdateProspectHelper.updateProspect(updateProspectDTO, findProspectDTO, responseDTO, requestDTO);

		for (InformationResponseDTO ir : responseDTO.getInformationResponse() ) {
			Assert.assertEquals("4", ir.getInformation().getInformationCode());
		}
		
		logger.info("End test");
		
	}
	

	@Test
	@Rollback(true)
	public void testCreateOrUpdateProspectHelperUpdateProspectCode1() throws JrafDomainException {
		
		logger.info("Start test");
		
		Individu individuForTest = individuRepository.findBySgin("900045821344");
		individuForTest.getCommunicationpreferences().clear();
		individuForTest = individuRepository.saveAndFlush(individuForTest);
		
		IndividuDTO updateProspectDTO = new IndividuDTO();
		IndividuDTO findProspectDTO = IndividuTransform.bo2Dto(individuForTest);
		
		List<CommunicationPreferencesDTO> comPrefDTO = new ArrayList<CommunicationPreferencesDTO>();
		
		updateProspectDTO.setCommunicationpreferencesdto(comPrefDTO);
		
		CreateUpdateIndividualRequestDTO requestDTO = new CreateUpdateIndividualRequestDTO();
		RequestorDTO requestorDTO = new RequestorDTO();
		requestorDTO.setApplicationCode("ISI");
		requestorDTO.setChannel("B2C");
		requestorDTO.setSignature("TESTRI");
		requestorDTO.setSite("QVI");
		requestDTO.setRequestorDTO(requestorDTO);

		CreateModifyIndividualResponseDTO responseDTO = new CreateModifyIndividualResponseDTO();	
		
		List<CommunicationPreferencesDTO> listComPref = new ArrayList<CommunicationPreferencesDTO>();
		CommunicationPreferencesDTO comPref = new CommunicationPreferencesDTO();
		comPref.setComType("AF");
		comPref.setComGroupType("N");
		comPref.setDomain("S");
		comPref.setMedia1("E");
		comPref.setSubscribe("Y");
		
		Set<MarketLanguageDTO> setMarketLanguage = new HashSet<MarketLanguageDTO>();
		MarketLanguageDTO marketLanguage = new MarketLanguageDTO();
		marketLanguage.setMarket("FR");
		marketLanguage.setLanguage("FR");
		marketLanguage.setOptIn("Y");
		marketLanguage.setMedia1("E");

		setMarketLanguage.add(marketLanguage);
		comPref.setMarketLanguageDTO(setMarketLanguage);
		listComPref.add(comPref);
		updateProspectDTO.setCommunicationpreferencesdto(listComPref);	
		
		createOrUpdateProspectHelper.updateProspect(updateProspectDTO, findProspectDTO, responseDTO, requestDTO);

		for (InformationResponseDTO ir : responseDTO.getInformationResponse() ) {
			Assert.assertEquals("1", ir.getInformation().getInformationCode());
		}
		
		logger.info("End test");
		
	}
	
	@Test
	@Rollback(true)
	public void testCreateOrUpdateProspectHelperUpdateProspectCode7() throws JrafDomainException {
		
		logger.info("Start test");
		
		Individu individuForTest = individuRepository.findBySgin("900045821344");
		individuForTest.getCommunicationpreferences().clear();
	
		CommunicationPreferences communicationPreferencesBO = new CommunicationPreferences();
		communicationPreferencesBO.setChannel("B2C");
		communicationPreferencesBO.setComGroupType("N");
		communicationPreferencesBO.setComType("AF");
		communicationPreferencesBO.setCreationDate(new Date());
		communicationPreferencesBO.setCreationSignature("TESTRI");
		communicationPreferencesBO.setCreationSite("QVI");
		communicationPreferencesBO.setDateOptin(new Date());
		communicationPreferencesBO.setDomain("S");
		communicationPreferencesBO.setGin("900045821344");
		communicationPreferencesBO.setSubscribe("N");
		communicationPreferencesBO.setMedia1("E");
		
		MarketLanguage marketLanguageBO = new MarketLanguage();
		marketLanguageBO.setCommunicationMedia1("E");
		marketLanguageBO.setCreationDate(new Date());
		marketLanguageBO.setCreationSignature("TESTRI");
		marketLanguageBO.setCreationSite("QVI");
		marketLanguageBO.setDateOfConsent(new Date());
		marketLanguageBO.setLanguage("FR");
		marketLanguageBO.setMarket("FR");
		marketLanguageBO.setOptIn("N");
		communicationPreferencesBO.setMarketLanguage(new HashSet<MarketLanguage>());
		communicationPreferencesBO.getMarketLanguage().add(marketLanguageBO);
		
		individuForTest.getCommunicationpreferences().add(communicationPreferencesBO);
		individuForTest = individuRepository.saveAndFlush(individuForTest);
		
		individuForTest = individuRepository.findBySgin("900045821344");
		
		IndividuDTO updateProspectDTO = new IndividuDTO();
		IndividuDTO findProspectDTO = IndividuTransform.bo2Dto(individuForTest);
		
		List<CommunicationPreferencesDTO> comPrefDTO = new ArrayList<CommunicationPreferencesDTO>();
		
		updateProspectDTO.setCommunicationpreferencesdto(comPrefDTO);
		
		CreateUpdateIndividualRequestDTO requestDTO = new CreateUpdateIndividualRequestDTO();
		RequestorDTO requestorDTO = new RequestorDTO();
		requestorDTO.setApplicationCode("ISI");
		requestorDTO.setChannel("B2C");
		requestorDTO.setSignature("TESTRI");
		requestorDTO.setSite("QVI");
		requestDTO.setRequestorDTO(requestorDTO);
		
		requestDTO.setUpdateCommunicationPrefMode("U");

		CreateModifyIndividualResponseDTO responseDTO = new CreateModifyIndividualResponseDTO();
		
		
		List<CommunicationPreferencesDTO> listComPref = new ArrayList<CommunicationPreferencesDTO>();
		CommunicationPreferencesDTO comPref = new CommunicationPreferencesDTO();
		comPref.setComType("AF");
		comPref.setComGroupType("N");
		comPref.setDomain("S");
		comPref.setMedia1("E");
		comPref.setSubscribe("N");
		
		Set<MarketLanguageDTO> setMarketLanguage = new HashSet<MarketLanguageDTO>();
		MarketLanguageDTO marketLanguage = new MarketLanguageDTO();
		marketLanguage.setMarket("FR");
		marketLanguage.setLanguage("FR");
		marketLanguage.setOptIn("N");
		marketLanguage.setMedia1("E");

		setMarketLanguage.add(marketLanguage);
		comPref.setMarketLanguageDTO(setMarketLanguage);
		listComPref.add(comPref);
		updateProspectDTO.setCommunicationpreferencesdto(listComPref);
			
		createOrUpdateProspectHelper.updateProspect(updateProspectDTO, findProspectDTO, responseDTO, requestDTO);
		
		for (InformationResponseDTO ir : responseDTO.getInformationResponse() ) {
			Assert.assertEquals("7", ir.getInformation().getInformationCode());
		}
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperSearchByMulticriteria() throws JrafDomainException {
		
		logger.info("Start test");
		
		SearchIndividualByMulticriteriaResponseDTO responseDTO = new SearchIndividualByMulticriteriaResponseDTO();
		IndividualMulticriteriaDTO indMultiDTO = new IndividualMulticriteriaDTO();
		indMultiDTO.setRelevance("80");
		indMultiDTO.setIndividu(createGenericIndForFilter("test1", "I", "FP", "C", new Date(), new Date(), true));

		IndividualMulticriteriaDTO indMultiDTO2 = new IndividualMulticriteriaDTO();
		indMultiDTO2.setRelevance("100");
		IndividuDTO indToReturn = createGenericIndForFilter("test2", "W", null, null, new Date(), new Date(), false);
		indToReturn.setSgin("9999999999");
		indMultiDTO2.setIndividu(indToReturn);

		IndividualMulticriteriaDTO indMultiDTO3 = new IndividualMulticriteriaDTO();
		indMultiDTO3.setRelevance("100");
		indMultiDTO3.setIndividu(createGenericIndForFilter("test3", "W", null, null, new Date(), new Date(), true));

		IndividualMulticriteriaDTO indMultiDTO4 = new IndividualMulticriteriaDTO();
		indMultiDTO4.setRelevance("85");
		indMultiDTO4.setIndividu(createGenericIndForFilter("test4", "I", "FP", "C", new Date(), new Date(), false));
		
		responseDTO.setIndividuals(new HashSet<IndividualMulticriteriaDTO>());
		responseDTO.getIndividuals().add(indMultiDTO);
		responseDTO.getIndividuals().add(indMultiDTO2);
		responseDTO.getIndividuals().add(indMultiDTO3);
		responseDTO.getIndividuals().add(indMultiDTO4);
		
		CreateUpdateIndividualRequestDTO createUpdateIndividualRequestDTO = createRequestDTO("test@email.fr", null, "name", "name", "APP");
		
		EasyMock.expect(individuDS.searchIndividual(EasyMock.anyObject(SearchIndividualByMulticriteriaRequestDTO.class))).andReturn(responseDTO);
		EasyMock.expect(individuDS.getIndividualOrProspectByGin("9999999999")).andReturn(indToReturn);
		EasyMock.replay(individuDS);
		
		IndividuDTO indResponse = searchByMulticriteria(createUpdateIndividualRequestDTO);
		Assert.assertEquals("test2", indResponse.getNom());
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterSearchListIndividualNotReturn() throws JrafDomainException {
		
		logger.info("Start test");
		
		SearchIndividualByMulticriteriaResponseDTO responseDTO = new SearchIndividualByMulticriteriaResponseDTO();
		IndividualMulticriteriaDTO indMultiDTO = new IndividualMulticriteriaDTO();
		indMultiDTO.setRelevance("80");
		indMultiDTO.setIndividu(createGenericIndForFilter("test1", "I", "FP", "C", new Date(), new Date(), true));

		IndividualMulticriteriaDTO indMultiDTO2 = new IndividualMulticriteriaDTO();
		indMultiDTO2.setRelevance("65");
		indMultiDTO2.setIndividu(createGenericIndForFilter("test2", "W", null, null, new Date(), new Date(), false));
		
		responseDTO.setIndividuals(new HashSet<IndividualMulticriteriaDTO>());
		responseDTO.getIndividuals().add(indMultiDTO);
		responseDTO.getIndividuals().add(indMultiDTO2);

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		
		IndividuDTO indResponse = filterSearchList(request, responseDTO);
		Assert.assertNull(indResponse);
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterSearchListReturnRelevance100() throws JrafDomainException {
		
		logger.info("Start test");
		
		SearchIndividualByMulticriteriaResponseDTO responseDTO = new SearchIndividualByMulticriteriaResponseDTO();
		IndividualMulticriteriaDTO indMultiDTO = new IndividualMulticriteriaDTO();
		indMultiDTO.setRelevance("80");
		indMultiDTO.setIndividu(createGenericIndForFilter("test1", "I", "FP", "C", new Date(), new Date(), true));

		IndividualMulticriteriaDTO indMultiDTO2 = new IndividualMulticriteriaDTO();
		indMultiDTO2.setRelevance("100");
		indMultiDTO2.setIndividu(createGenericIndForFilter("test2", "W", null, null, new Date(), new Date(), false));

		IndividualMulticriteriaDTO indMultiDTO3 = new IndividualMulticriteriaDTO();
		indMultiDTO3.setRelevance("85");
		indMultiDTO3.setIndividu(createGenericIndForFilter("test3", "I", "FP", "C", new Date(), new Date(), false));
		
		responseDTO.setIndividuals(new HashSet<IndividualMulticriteriaDTO>());
		responseDTO.getIndividuals().add(indMultiDTO);
		responseDTO.getIndividuals().add(indMultiDTO2);
		responseDTO.getIndividuals().add(indMultiDTO3);

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		
		IndividuDTO indResponse = filterSearchList(request, responseDTO);
		Assert.assertEquals("test2", indResponse.getNom());
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterSearchListReturnMostRecent() throws JrafDomainException {
		
		logger.info("Start test");
		
		SearchIndividualByMulticriteriaResponseDTO responseDTO = new SearchIndividualByMulticriteriaResponseDTO();
		IndividualMulticriteriaDTO indMultiDTO = new IndividualMulticriteriaDTO();
		indMultiDTO.setRelevance("100");
		indMultiDTO.setIndividu(createGenericIndForFilter("test1", "I", "FP", "C", new Date(), new Date(), true));

		IndividualMulticriteriaDTO indMultiDTO2 = new IndividualMulticriteriaDTO();
		indMultiDTO2.setRelevance("100");
		indMultiDTO2.setIndividu(createGenericIndForFilter("test2", "I", "FP", "C", new Date(), new Date(), false));
		
		responseDTO.setIndividuals(new HashSet<IndividualMulticriteriaDTO>());
		responseDTO.getIndividuals().add(indMultiDTO);
		responseDTO.getIndividuals().add(indMultiDTO2);

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		
		IndividuDTO indResponse = filterSearchList(request, responseDTO);
		Assert.assertEquals("test2", indResponse.getNom());
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterSearchList() throws JrafDomainException {
		
		logger.info("Start test");
		
		SearchIndividualByMulticriteriaResponseDTO responseDTO = new SearchIndividualByMulticriteriaResponseDTO();
		IndividualMulticriteriaDTO indMultiDTO = new IndividualMulticriteriaDTO();
		indMultiDTO.setRelevance("100");
		indMultiDTO.setIndividu(createGenericIndForFilter("test1", "I", "FP", "C", new Date(), new Date(), true));

		IndividualMulticriteriaDTO indMultiDTO2 = new IndividualMulticriteriaDTO();
		indMultiDTO2.setRelevance("100");
		indMultiDTO2.setIndividu(createGenericIndForFilter("test2", "I", "MA", "C", new Date(), new Date(), true));
		
		responseDTO.setIndividuals(new HashSet<IndividualMulticriteriaDTO>());
		responseDTO.getIndividuals().add(indMultiDTO);
		responseDTO.getIndividuals().add(indMultiDTO2);
		
		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		
		IndividuDTO indResponse = filterSearchList(request, responseDTO);
		Assert.assertEquals("test1", indResponse.getNom());
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterSearchListIndividualWithLastnameOnly() throws JrafDomainException {
		
		logger.info("Start test");
		
		SearchIndividualByMulticriteriaResponseDTO responseDTO = new SearchIndividualByMulticriteriaResponseDTO();
		IndividualMulticriteriaDTO indMultiDTO = new IndividualMulticriteriaDTO();
		indMultiDTO.setRelevance("70");
		indMultiDTO.setIndividu(createGenericIndForFilter("test1", "W", null, null, new Date(), new Date(), false));

		IndividualMulticriteriaDTO indMultiDTO2 = new IndividualMulticriteriaDTO();
		indMultiDTO2.setRelevance("70");
		indMultiDTO2.setIndividu(createGenericIndForFilter("test2", "W", null, null, new Date(), new Date(), false));
		
		IndividualMulticriteriaDTO indMultiDTO3 = new IndividualMulticriteriaDTO();
		indMultiDTO3.setRelevance("70");
		indMultiDTO3.setIndividu(createGenericIndForFilter("test3", "W", null, null, new Date(), new Date(), false));
		
		responseDTO.setIndividuals(new HashSet<IndividualMulticriteriaDTO>());
		responseDTO.getIndividuals().add(indMultiDTO);
		responseDTO.getIndividuals().add(indMultiDTO2);
		responseDTO.getIndividuals().add(indMultiDTO3);

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		IndividualRequestDTO individualRequestDTO = new IndividualRequestDTO();
		IndividualInformationsDTO individualInformationsDTO = new IndividualInformationsDTO();
		individualInformationsDTO.setLastNameSC("test3");
		individualRequestDTO.setIndividualInformationsDTO(individualInformationsDTO);
		request.setIndividualRequestDTO(individualRequestDTO);
		
		IndividuDTO indResponse = filterSearchList(request, responseDTO);
		Assert.assertNotNull(indResponse);
		Assert.assertEquals("test3", indResponse.getNom());
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterSearchListIndividualWithLastnameOnlyAndRelevance100() throws JrafDomainException {
		
		logger.info("Start test");
		
		SearchIndividualByMulticriteriaResponseDTO responseDTO = new SearchIndividualByMulticriteriaResponseDTO();
		IndividualMulticriteriaDTO indMultiDTO = new IndividualMulticriteriaDTO();
		indMultiDTO.setRelevance("100");
		indMultiDTO.setIndividu(createGenericIndForFilter("test1", "W", null, null, new Date(), new Date(), false));

		IndividualMulticriteriaDTO indMultiDTO2 = new IndividualMulticriteriaDTO();
		indMultiDTO2.setRelevance("70");
		indMultiDTO2.setIndividu(createGenericIndForFilter("test2", "W", null, null, new Date(), new Date(), false));
		
		IndividualMulticriteriaDTO indMultiDTO3 = new IndividualMulticriteriaDTO();
		indMultiDTO3.setRelevance("70");
		indMultiDTO3.setIndividu(createGenericIndForFilter("test3", "W", null, null, new Date(), new Date(), false));
		
		responseDTO.setIndividuals(new HashSet<IndividualMulticriteriaDTO>());
		responseDTO.getIndividuals().add(indMultiDTO);
		responseDTO.getIndividuals().add(indMultiDTO2);
		responseDTO.getIndividuals().add(indMultiDTO3);

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		IndividualRequestDTO individualRequestDTO = new IndividualRequestDTO();
		IndividualInformationsDTO individualInformationsDTO = new IndividualInformationsDTO();
		individualInformationsDTO.setLastNameSC("test1");
		individualRequestDTO.setIndividualInformationsDTO(individualInformationsDTO);
		request.setIndividualRequestDTO(individualRequestDTO);
		
		IndividuDTO indResponse = filterSearchList(request, responseDTO);
		Assert.assertNotNull(indResponse);
		Assert.assertEquals("test1", indResponse.getNom());
		
		logger.info("End test");
		
	}
	
	
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterIndividualsNoReturn() throws JrafDomainException {
		
		logger.info("Start test");
		
		List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
		listInd.add(createGenericIndForFilter("test1", "I", "MA", "A", new Date(), new Date(), true));
		listInd.add(createGenericIndForFilter("test2", "I", "FP", "A", new Date(), new Date(), true));
		IndividuDTO indResponse = filterIndividuals(listInd);
		Assert.assertNull(indResponse);
		
		logger.info("End test");
		
	}

	@Test
	public void testCreateOrUpdateProspectHelperFilterIndividualsReturnProspectMostRecentModificationAndCreationDate() throws JrafDomainException {
		
		logger.info("Start test");
		
		List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
		listInd.add(createGenericIndForFilter("test1", "W", null, null, new Date(), null, true));
		listInd.add(createGenericIndForFilter("test2", "W", null, null, null, new Date(), false));
		listInd.add(createGenericIndForFilter("test3", "W", null, null, new Date(), null, true));
		
		IndividuDTO indResponse = filterIndividuals(listInd);
		Assert.assertEquals("test2", indResponse.getNom());
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterIndividualsReturnProspectMostRecentCreationAndModificationDate() throws JrafDomainException {
		
		logger.info("Start test");
		
		List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
		listInd.add(createGenericIndForFilter("test1", "W", null, null, null, new Date(), false));
		listInd.add(createGenericIndForFilter("test2", "W", null, null, new Date(), null, true));
		listInd.add(createGenericIndForFilter("test3", "W", null, null, new Date(), null, true));
		
		IndividuDTO indResponse = filterIndividuals(listInd);
		Assert.assertEquals("test1", indResponse.getNom());
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterIndividualsReturnProspectMostRecentCreationDate() throws JrafDomainException {
		
		logger.info("Start test");
		
		List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
		listInd.add(createGenericIndForFilter("test1", "W", null, null, null, new Date(), true));
		
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		date = cal.getTime();
		listInd.add(createGenericIndForFilter("test2", "W", null, null, null, date, true));
		
		IndividuDTO indResponse = filterIndividuals(listInd);
		Assert.assertEquals("test1", indResponse.getNom());
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterIndividualsReturnTravelerMostRecent() throws JrafDomainException {
		
		logger.info("Start test");
		
		List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
		listInd.add(createGenericIndForFilter("test1", "T", null, null, new Date(), new Date(), true));
		listInd.add(createGenericIndForFilter("test2", "T", null, null, new Date(), new Date(), false));
		
		IndividuDTO indResponse = filterIndividuals(listInd);
		Assert.assertEquals("test2", indResponse.getNom());
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterIndividualsReturnProspect() throws JrafDomainException {
		
		logger.info("Start test");
		
		List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
		listInd.add(createGenericIndForFilter("test1", "T", null, null, new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test2", "W", null, null, new Date(), new Date(), true));
		
		IndividuDTO indResponse = filterIndividuals(listInd);
		Assert.assertEquals("test2", indResponse.getNom());
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterIndividualsReturnProspectMostRecent() throws JrafDomainException {
		
		logger.info("Start test");
		
		List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
		listInd.add(createGenericIndForFilter("test1", "I", "MA", "A", new Date(), new Date(), true));
		listInd.add(createGenericIndForFilter("test2", "W", null, null, new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test3", "W", null, null, new Date(), new Date(), true));
		listInd.add(createGenericIndForFilter("test4", "T", null, null, new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test5", "W", null, null, new Date(), new Date(), true));
		
		IndividuDTO indResponse = filterIndividuals(listInd);
		Assert.assertEquals("test2", indResponse.getNom());
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterIndividualsReturnMAMostRecent() throws JrafDomainException {
		
		logger.info("Start test");
		
		List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
		listInd.add(createGenericIndForFilter("test1", "I", "MA", "C", new Date(), new Date(), true));
		listInd.add(createGenericIndForFilter("test2", "I", "MA", "C", new Date(), new Date(), true));
		listInd.add(createGenericIndForFilter("test3", "I", "MA", "C", new Date(), new Date(), true));
		listInd.add(createGenericIndForFilter("test4", "I", "MA", "C", new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test5", "W", null, null, new Date(), new Date(), false));
		
		IndividuDTO indResponse = filterIndividuals(listInd);
		Assert.assertEquals("test4", indResponse.getNom());
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterIndividualsReturnMA() throws JrafDomainException {
		
		logger.info("Start test");
		
		List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
		listInd.add(createGenericIndForFilter("test1", "I", "FP", "A", new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test2", "I", "MA", "C", new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test3", "W", null, null, new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test4", "T", null, null, new Date(), new Date(), false));
		
		IndividuDTO indResponse = filterIndividuals(listInd);
		Assert.assertEquals("test2", indResponse.getNom());
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterIndividualsReturnFBMostRecent() throws JrafDomainException {
		
		logger.info("Start test");
		
		List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
		listInd.add(createGenericIndForFilter("test1", "I", "FP", "C", new Date(), new Date(), true));
		listInd.add(createGenericIndForFilter("test2", "I", "FP", "C", new Date(), new Date(), true));
		listInd.add(createGenericIndForFilter("test3", "I", "FP", "C", new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test4", "I", "FP", "C", new Date(), new Date(), true));
		listInd.add(createGenericIndForFilter("test5", "W", null, null, new Date(), new Date(), false));
		
		IndividuDTO indResponse = filterIndividuals(listInd);
		Assert.assertEquals("test3", indResponse.getNom());
		
		logger.info("End test");
		
	}
	
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterIndividualsReturnFB() throws JrafDomainException {
		
		logger.info("Start test");
		
		List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
		listInd.add(createGenericIndForFilter("test1", "I", "FP", "C", new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test2", "I", "MA", "C", new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test3", "W", null, null, new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test4", "T", null, null, new Date(), new Date(), false));
		
		IndividuDTO indResponse = filterIndividuals(listInd);
		Assert.assertEquals("test1", indResponse.getNom());
		
		logger.info("End test");
		
	}
	
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterIndividualsByContractsFP() throws JrafDomainException {
		
		logger.info("Start test");
		
		List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
		
		listInd.add(createGenericIndForFilter("test1", "I", "FP", "C", new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test2", "I", null, null, new Date(), new Date(), false));
		
		List<IndividuDTO> listIndResponse = filterIndividualsByContracts(listInd, true);
		Assert.assertEquals(1, listIndResponse.size());
		Assert.assertEquals("test1", listIndResponse.get(0).getNom());
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperFilterIndividualsByContractsNoFP() throws JrafDomainException {
		
		logger.info("Start test");
		
		List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
		listInd.add(createGenericIndForFilter("test1", "I", null, null, new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test2", "I", null, null, new Date(), new Date(), false));
		
		List<IndividuDTO> listIndResponse = filterIndividualsByContracts(listInd, true);
		Assert.assertEquals(0, listIndResponse.size());
		
		logger.info("End test");
		
	}

	@Test
	public void testCreateOrUpdateProspectHelperFilterIndividualsByContractsMA() throws JrafDomainException {
		
		logger.info("Start test");
		
		List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
		listInd.add(createGenericIndForFilter("test1", "I", "MA", "C", new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test2", "I", null, null, new Date(), new Date(), false));
		
		List<IndividuDTO> listIndResponse = filterIndividualsByContracts(listInd, false);
		Assert.assertEquals(1, listIndResponse.size());
		Assert.assertEquals("test1", listIndResponse.get(0).getNom());
		
		logger.info("End test");
		
	}

	@Test
	public void testCreateOrUpdateProspectHelperFilterIndividualsByContractsNoMA() throws JrafDomainException {
		
		logger.info("Start test");
		
		List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
		
		listInd.add(createGenericIndForFilter("test1", "I", null, null, new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test2", "I", null, null, new Date(), new Date(), false));
		
		List<IndividuDTO> listIndResponse = filterIndividualsByContracts(listInd, false);
		Assert.assertEquals(0, listIndResponse.size());
		
		logger.info("End test");
		
	}

	@Test
	public void testCreateOrUpdateProspectHelperFilterIndividualsByType() throws JrafDomainException {
		
		logger.info("Start test");
		
		List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();

		listInd.add(createGenericIndForFilter("test1", "W", null, null, new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test2", "T", null, null, new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test3", "W", null, null, new Date(), new Date(), false));
		
		List<IndividuDTO> listIndResponse = filterIndividualsByType(listInd, true);
		Assert.assertEquals(2, listIndResponse.size());
		
		listIndResponse = filterIndividualsByType(listInd, false);
		Assert.assertEquals(1, listIndResponse.size());
		Assert.assertEquals("test2", listIndResponse.get(0).getNom());
		
		logger.info("End test");
		
	}

	@Test
	public void testCreateOrUpdateProspectHelperFilterIndividualsByTypeNone() throws JrafDomainException {
		
		logger.info("Start test");
		
		List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();

		listInd.add(createGenericIndForFilter("test1", "T", null, null, new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test2", "T", null, null, new Date(), new Date(), false));
		listInd.add(createGenericIndForFilter("test3", "T", null, null, new Date(), new Date(), false));
		
		List<IndividuDTO> listIndResponse = filterIndividualsByType(listInd, true);
		Assert.assertEquals(0, listIndResponse.size());
		
		logger.info("End test");
		
	}
	@Test
	public void testCreateOrUpdateProspectHelperBuildSearchIndividualByMultiCriteriaWithoutAppCode() throws JrafDomainException {
		
		logger.info("Start test");
		
		String email = "test@email.fr";
		Date birthday = new Date();
		String firstname = "firstname";
		String lastname = "lastname";
		
		SearchIndividualByMulticriteriaRequestDTO requestBuildDTO = buildSearchIndividualByMultiCriteriaRequest(createRequestDTO(email, birthday, firstname, lastname, null));
		Assert.assertEquals("REPIND", requestBuildDTO.getRequestor().getApplicationCode());
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperBuildSearchIndividualByMultiCriteriaWithoutBirthday() throws JrafDomainException {
		
		logger.info("Start test");
		
		String email = "test@email.fr";
		String firstname = "firstname";
		String lastname = "lastname";
		String appCode = "APP";
		
		SearchIndividualByMulticriteriaRequestDTO requestBuildDTO = buildSearchIndividualByMultiCriteriaRequest(createRequestDTO(email, null, firstname, lastname, appCode));
		Assert.assertEquals(email, requestBuildDTO.getContact().getEmail());
		Assert.assertEquals(firstname, requestBuildDTO.getIdentity().getFirstName());
		Assert.assertEquals(lastname, requestBuildDTO.getIdentity().getLastName());
		Assert.assertEquals(appCode, requestBuildDTO.getRequestor().getApplicationCode());
		Assert.assertEquals("A", requestBuildDTO.getPopulationTargeted());
		Assert.assertEquals("A", requestBuildDTO.getProcessType());
		
		logger.info("End test");
		
	}
	
	@Test
	public void testCreateOrUpdateProspectHelperBuildSearchIndividualByMultiCriteria() throws JrafDomainException {
		
		logger.info("Start test");
		
		String email = "test@email.fr";
		Date birthday = new Date();
		String firstname = "firstname";
		String lastname = "lastname";
		String appCode = "APP";
		
		SearchIndividualByMulticriteriaRequestDTO requestBuildDTO = buildSearchIndividualByMultiCriteriaRequest(createRequestDTO(email, birthday, firstname, lastname, appCode));
		Assert.assertEquals(email, requestBuildDTO.getContact().getEmail());
		Assert.assertEquals(birthday, requestBuildDTO.getIdentity().getBirthday());
		Assert.assertEquals(firstname, requestBuildDTO.getIdentity().getFirstName());
		Assert.assertEquals(lastname, requestBuildDTO.getIdentity().getLastName());
		Assert.assertEquals(appCode, requestBuildDTO.getRequestor().getApplicationCode());
		Assert.assertEquals("A", requestBuildDTO.getPopulationTargeted());
		Assert.assertEquals("A", requestBuildDTO.getProcessType());
		
		logger.info("End test");
		
	}

	public IndividuDTO createGenericIndForFilter(String name, String type, String typeContract, String etatContract, Date dateModification, Date dateCreation, boolean olderModification) {

		IndividuDTO indDTO = new IndividuDTO();
		
		indDTO.setNom(name);
		indDTO.setType(type);
		
		if(typeContract != null) {

			Set<RoleContratsDTO> setRolecontratsdto = new HashSet<RoleContratsDTO>();
			RoleContratsDTO roleContratsDTO = new RoleContratsDTO();
			roleContratsDTO.setTypeContrat(typeContract);
			roleContratsDTO.setEtat(etatContract);
			setRolecontratsdto.add(roleContratsDTO);
			indDTO.setRolecontratsdto(setRolecontratsdto);
			
		}
		
		if(dateModification != null) {
			if(olderModification) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dateModification);
				cal.add(Calendar.DATE, -1);
				dateModification = cal.getTime();

			}
			indDTO.setDateModification(dateModification);
		} 
		if(dateCreation != null) {
			indDTO.setDateCreation(dateCreation);
		}
		
		return indDTO;
		
	}


	public CreateUpdateIndividualRequestDTO createRequestDTO(String email, Date birthday, String firstname, String lastname, String appCode) {

		CreateUpdateIndividualRequestDTO requestDTO = new CreateUpdateIndividualRequestDTO();
		
		// Requestor
		RequestorDTO requestorDTO = new RequestorDTO();
		requestorDTO.setApplicationCode(appCode);
		requestDTO.setRequestorDTO(requestorDTO);

		// Email
		List<EmailRequestDTO> listEmailRequestDTO = new ArrayList<EmailRequestDTO>();
		EmailRequestDTO emailRequestDTO = new EmailRequestDTO();
		EmailDTO emailDTO = new EmailDTO();
		emailDTO.setEmail(email);
		emailRequestDTO.setEmailDTO(emailDTO);
		listEmailRequestDTO.add(emailRequestDTO);
		requestDTO.setEmailRequestDTO(listEmailRequestDTO);
		
		// Individual
		IndividualRequestDTO individualRequestDTO = new IndividualRequestDTO();
		IndividualInformationsDTO individualInformationsDTO = new IndividualInformationsDTO();
		individualInformationsDTO.setBirthDate(birthday);
		individualInformationsDTO.setFirstNameSC(firstname);
		individualInformationsDTO.setLastNameSC(lastname);
		individualRequestDTO.setIndividualInformationsDTO(individualInformationsDTO);
		requestDTO.setIndividualRequestDTO(individualRequestDTO);
		
		return requestDTO;
		
	}

	public ProspectIndividuDTO createProspectIndividuDTO(String mail, String gin, String cin) {

		ProspectIndividuDTO prospectIndDTO = new ProspectIndividuDTO();
		prospectIndDTO.setEmail(mail);
		prospectIndDTO.setGin(gin);
		prospectIndDTO.setCin(cin);
		
		return prospectIndDTO;
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
	
}
