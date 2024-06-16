package com.airfrance.repind.service.individu.internal.it;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.adresse.PostalAddressRepository;
import com.airfrance.repind.dao.adresse.Usage_mediumRepository;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.profil.ProfilsRepository;
import com.airfrance.repind.dao.reference.RefComPrefRepository;
import com.airfrance.repind.dto.compref.InformationDTO;
import com.airfrance.repind.dto.compref.InformationsDTO;
import com.airfrance.repind.dto.compref.PermissionDTO;
import com.airfrance.repind.dto.compref.PermissionsDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.IndividuTransform;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.dto.reference.*;
import com.airfrance.repind.dto.ws.RequestorDTO;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.adresse.Usage_medium;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.MarketLanguage;
import com.airfrance.repind.entity.profil.Profils;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.reference.internal.RefComPrefDgtDS;
import com.airfrance.repind.service.reference.internal.RefPermissionsDS;
import com.airfrance.repind.service.reference.internal.RefPermissionsQuestionDS;
import com.airfrance.repind.service.tracking.internal.TrackingPermissionsDS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CommunicationPreferencesDSTest {

	/** logger */
	private static final Log LOG = LogFactory.getLog(CommunicationPreferencesDSTest.class);

	@Autowired
	private CommunicationPreferencesDS communicationPreferencesDS;
	
	@Autowired
	private CommunicationPreferencesRepository communicationPreferencesRepository;
	
	@Autowired
	private IndividuRepository individuRepository;

	@Autowired
	private ProfilsRepository profilsRepository;

	@Autowired
	private PostalAddressRepository postalAddressRepository;

	@Autowired
	private Usage_mediumRepository usageMediumRepository;

	@Autowired
	private RefComPrefRepository refComPrefRepository;

	@Autowired
	private RefPermissionsDS refPermissionsDS;

	@Autowired
	private RefComPrefDgtDS refComPrefDgtDS;

	@Autowired
	private RefPermissionsQuestionDS refPermissionsQuestionDS;

	@Autowired
	private TrackingPermissionsDS trackingPermissionsDS;

	@Transactional
	@Rollback(true)
	private RefPermissionsDTO createPermissionsForTest() throws JrafDomainException {		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("F", "N", "FB_NEWS");

		RefPermissionsQuestionDTO refPermissionsQuestion = new RefPermissionsQuestionDTO();
		refPermissionsQuestion.setDateCreation(new Date());
		refPermissionsQuestion.setName("FLYING_BLUE_TEST");
		refPermissionsQuestion.setQuestion("Blabla en FR");
		refPermissionsQuestion.setQuestionEN("Blabla en En");
		refPermissionsQuestion.setSignatureCreation("TESTMZ_C");
		refPermissionsQuestion.setSiteCreation("QVI_C");
		refPermissionsQuestionDS.create(refPermissionsQuestion);

		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);

		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTMZ_C");
		refPermissions.setSiteCreation("QVI_C");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTMZ_M");
		refPermissions.setSiteModification("QVI_M");

		refPermissionsDS.create(refPermissions);

		return refPermissions;
	}

	@Transactional
	@Rollback(true)
	private IndividuDTO createIndividuForTest(boolean createCompref) throws JrafDomainException {
		Individu individu = new Individu();

		individu.setSexe("M");
		individu.setDateNaissance(new Date());
		individu.setDateCreation(new Date());
		individu.setSignatureCreation("REPIND");
		individu.setSiteCreation("QVI");
		individu.setStatutIndividu("V");
		individu.setCivilite("MR");
		individu.setNomSC("TEST COM");
		individu.setPrenomSC("TEST PREF");
		individu.setSgin("999999999999");
		individu.setNonFusionnable("N");
		individu.setType("I");
		individu.setCommunicationpreferences(new HashSet<CommunicationPreferences>());
		individuRepository.saveAndFlush(individu);

		Profils profils = new Profils();
		profils.setSgin(individu.getSgin());
		profils.setSmailing_autorise("T");
		profils.setSrin("1");
		profils.setSsolvabilite("O");
		profils.setScode_langue("FR");
		profils.setIversion(1);
		profilsRepository.saveAndFlush(profils);
		individu.setProfils(profils);

		PostalAddress postalAddress = new PostalAddress();
		postalAddress.setSgin("999999999999");
		postalAddress.setVersion(1);
		postalAddress.setScode_medium("D");
		postalAddress.setSstatut_medium("V");
		postalAddress.setSsite_creation("QVI");
		postalAddress.setSignature_creation("TESTRI");
		postalAddress.setDdate_creation(new Date());
		postalAddress.setIcod_err(0);
		postalAddress.setScode_pays("FR");
		postalAddress.setDdate_modification(new Date());
		postalAddressRepository.save(postalAddress);

		Usage_medium usageMedium = new Usage_medium();
		usageMedium.setSain_adr(postalAddress.getSain());
		usageMedium.setInum(1);
		usageMedium.setScode_application("ISI");
		usageMedium.setSrole1("M");
		usageMediumRepository.saveAndFlush(usageMedium);
		postalAddress.setUsage_medium(new HashSet<Usage_medium>());
		postalAddress.getUsage_medium().add(usageMedium);
		postalAddressRepository.saveAndFlush(postalAddress);

		individu.setPostaladdress(new HashSet<PostalAddress>());
		individu.getPostaladdress().add(postalAddress);
		individuRepository.saveAndFlush(individu);
		
		if (createCompref) {
			CommunicationPreferences communicationPreferences = new CommunicationPreferences();
			communicationPreferences.setChannel("B2C");
			communicationPreferences.setComGroupType("N");
			communicationPreferences.setComType("AF");
			communicationPreferences.setCreationDate(new Date());
			communicationPreferences.setCreationSignature("TESTRI");
			communicationPreferences.setCreationSite("QVI");
			communicationPreferences.setDateOptin(new Date());
			communicationPreferences.setDomain("S");
			communicationPreferences.setGin(individu.getSgin());
			communicationPreferences.setMedia1("E");
			communicationPreferences.setSubscribe("Y");
			communicationPreferences.setModificationDate(new Date());
			communicationPreferences.setModificationSignature("TESTRI");
			communicationPreferences.setModificationSite("QVI");
			communicationPreferences.setMarketLanguage(new HashSet<MarketLanguage>());
			
			MarketLanguage marketLanguageFR = new MarketLanguage();
			marketLanguageFR.setCommunicationMedia1("E");
			marketLanguageFR.setCreationDate(new Date());
			marketLanguageFR.setCreationSignature("TESTRI");
			marketLanguageFR.setCreationSite("QVI");
			marketLanguageFR.setDateOfConsent(new Date());
			marketLanguageFR.setLanguage("FR");
			marketLanguageFR.setMarket("FR");
			marketLanguageFR.setModificationDate(new Date());
			marketLanguageFR.setModificationSignature("TESTRI");
			marketLanguageFR.setModificationSite("QVI");
			marketLanguageFR.setOptIn("Y");
			communicationPreferences.getMarketLanguage().add(marketLanguageFR);
			
			MarketLanguage marketLanguageIT = new MarketLanguage();
			marketLanguageIT.setCommunicationMedia1("E");
			marketLanguageIT.setCreationDate(new Date());
			marketLanguageIT.setCreationSignature("TESTRI");
			marketLanguageIT.setCreationSite("QVI");
			marketLanguageIT.setDateOfConsent(new Date());
			marketLanguageIT.setLanguage("IT");
			marketLanguageIT.setMarket("IT");
			marketLanguageIT.setModificationDate(new Date());
			marketLanguageIT.setModificationSignature("TESTRI");
			marketLanguageIT.setModificationSite("QVI");
			marketLanguageIT.setOptIn("Y");
			communicationPreferences.getMarketLanguage().add(marketLanguageIT);
			
			communicationPreferencesRepository.save(communicationPreferences);
			
			individu.getCommunicationpreferences().add(communicationPreferences);
			individuRepository.saveAndFlush(individu);	
		}
		
		return IndividuTransform.bo2Dto(individu);
	}

	@Transactional
	@Rollback(true)
	private RefComPrefDgtDTO createRefComPrefDGTForTest() throws JrafDomainException {
		RefComPrefDomainDTO refComPrefDomain = new RefComPrefDomainDTO();
		refComPrefDomain.setCodeDomain("P");

		RefComPrefGTypeDTO refComPrefGType = new RefComPrefGTypeDTO();
		refComPrefGType.setCodeGType("M");

		RefComPrefTypeDTO refComPrefType = new RefComPrefTypeDTO();
		refComPrefType.setCodeType("FB_NEWS");

		RefComPrefDgtDTO refComPrefDgt = new RefComPrefDgtDTO();
		refComPrefDgt.setDomain(refComPrefDomain);
		refComPrefDgt.setGroupType(refComPrefGType);
		refComPrefDgt.setType(refComPrefType);
		refComPrefDgt.setDescription("Blabla");
		refComPrefDgt.setDateCreation(new Date());
		refComPrefDgt.setSignatureCreation("TESTMZ_C");
		refComPrefDgt.setSiteCreation("QVI_C");
		refComPrefDgt.setDateModification(new Date());
		refComPrefDgt.setSignatureModification("TESTMZ_M");
		refComPrefDgt.setSiteModification("QVI_M");
		refComPrefDgtDS.create(refComPrefDgt);

		return refComPrefDgt;
	}


	@Test
	@Transactional
	@Rollback(true)
	public void testCreateUpdateComPrefFromDGT_CREATE() throws JrafDomainException {
		LOG.info("START testCreateUpdateComPrefFromDGT_CREATE...");

		IndividuDTO individuDTO = createIndividuForTest(false);		
		
		CommunicationPreferencesDTO comPrefToProcess = new CommunicationPreferencesDTO();
		comPrefToProcess.setChannel("B2C");
		comPrefToProcess.setComGroupType("N");
		comPrefToProcess.setComType("FB_NEWS");
		comPrefToProcess.setDomain("F");
		comPrefToProcess.setDateOptin(new Date());
		comPrefToProcess.setMedia1("E");
		comPrefToProcess.setSubscribe("Y");
		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setDateOfConsent(new Date());
		ml.setMedia1("E");
		ml.setOptIn("Y");
		ml.setMarket("FR");
		ml.setLanguage("FR");
		comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		comPrefToProcess.getMarketLanguageDTO().add(ml);		

		Map<InformationDTO, CommunicationPreferencesDTO> informationCompref = communicationPreferencesDS.createUpdateComPrefFromDGT(individuDTO.getSgin(), comPrefToProcess, "QVI", "TESTMZ");
		List<InformationDTO> listInformationDTO = new ArrayList<InformationDTO>();
		listInformationDTO.addAll(informationCompref.keySet());

		Assert.assertEquals("0", listInformationDTO.get(0).getCode());
		Assert.assertEquals("Communication preference created", listInformationDTO.get(0).getDetails());
		
		List<CommunicationPreferences> listComPref = communicationPreferencesRepository.findByGin(individuDTO.getSgin());
		Assert.assertEquals(1, listComPref.size());
		
		CommunicationPreferences comPref = listComPref.get(0);
		Assert.assertEquals("F", comPref.getDomain());
		Assert.assertEquals("N", comPref.getComGroupType());
		Assert.assertEquals("FB_NEWS", comPref.getComType());
		Assert.assertEquals("Y", comPref.getSubscribe());
		Assert.assertNotNull(comPref.getMarketLanguage());
		
		LOG.info("END testCreateUpdateComPrefFromDGT_CREATE...");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCreateUpdateComPrefFromDGT_UPDATE() throws JrafDomainException {
		LOG.info("START testCreateUpdateComPrefFromDGT_UPDATE...");

		IndividuDTO individuDTO = createIndividuForTest(false);

		CommunicationPreferencesDTO comPrefToProcess = new CommunicationPreferencesDTO();
		comPrefToProcess.setChannel("B2C");
		comPrefToProcess.setComGroupType("N");
		comPrefToProcess.setComType("FB_NEWS");
		comPrefToProcess.setDomain("F");
		comPrefToProcess.setDateOptin(new Date());
		comPrefToProcess.setMedia1("E");
		comPrefToProcess.setSubscribe("Y");
		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setDateOfConsent(new Date());
		ml.setMedia1("E");
		ml.setOptIn("Y");
		ml.setMarket("FR");
		ml.setLanguage("FR");
		comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		comPrefToProcess.getMarketLanguageDTO().add(ml);

		Map<InformationDTO, CommunicationPreferencesDTO> informationCompref = communicationPreferencesDS.createUpdateComPrefFromDGT(individuDTO.getSgin(), comPrefToProcess, "QVI", "TESTMZ");
		
		List<CommunicationPreferences> listComPref = communicationPreferencesRepository.findByGin(individuDTO.getSgin());
		
		CommunicationPreferencesDTO comPrefToProcessBis = new CommunicationPreferencesDTO();
		comPrefToProcessBis.setChannel("B2C");
		comPrefToProcessBis.setComGroupType("N");
		comPrefToProcessBis.setComType("FB_NEWS");
		comPrefToProcessBis.setDomain("F");
		comPrefToProcessBis.setDateOptin(new Date());
		comPrefToProcessBis.setMedia1("E");
		comPrefToProcessBis.setSubscribe("N");
		MarketLanguageDTO mlBis = new MarketLanguageDTO();
		mlBis.setDateOfConsent(new Date());
		mlBis.setMedia1("E");
		mlBis.setOptIn("N");
		mlBis.setMarket("DE");
		mlBis.setLanguage("DE");
		comPrefToProcessBis.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		comPrefToProcessBis.getMarketLanguageDTO().add(mlBis);

		informationCompref = communicationPreferencesDS.createUpdateComPrefFromDGT(individuDTO.getSgin(), comPrefToProcessBis, "QVI", "TESTMZ");

		List<InformationDTO> listInformationDTO = new ArrayList<InformationDTO>();
		listInformationDTO.addAll(informationCompref.keySet());

		Assert.assertEquals("0", listInformationDTO.get(0).getCode());
		Assert.assertEquals("Communication preference updated", listInformationDTO.get(0).getDetails());
		
		listComPref = communicationPreferencesRepository.findByGin(individuDTO.getSgin());
		Assert.assertEquals(1, listComPref.size());
		
		CommunicationPreferences comPref = listComPref.get(0);
		Assert.assertEquals("F", comPref.getDomain());
		Assert.assertEquals("N", comPref.getComGroupType());
		Assert.assertEquals("FB_NEWS", comPref.getComType());
		Assert.assertEquals("N", comPref.getSubscribe());
		Assert.assertNotNull(comPref.getMarketLanguage());		
		
		LOG.info("END testCreateUpdateComPrefFromDGT_UPDATE...");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCreateComPrefBasedOnAPermission() throws JrafDomainException {
		LOG.info("START testCreateComPrefBasedOnAPermission...");

		IndividuDTO individuDTO = createIndividuForTest(false);
		RefPermissionsDTO refPermissionsDTO = createPermissionsForTest();

		PermissionDTO permissionDTO = new PermissionDTO();
		permissionDTO.setAnswer(false);
		permissionDTO.setLanguage("EN");
		permissionDTO.setMarket("EN");
		permissionDTO.setPermissionId(refPermissionsDTO.getRefPermissionsId().getQuestionId().getId().toString());
		List<PermissionDTO> listPermissionDTO = new ArrayList<PermissionDTO>();
		listPermissionDTO.add(permissionDTO);
		PermissionsDTO permissionsDTO = new PermissionsDTO();
		permissionsDTO.setGin(individuDTO.getSgin());
		RequestorDTO requestorDTO = new RequestorDTO();
		requestorDTO.setChannel("B2C");
		requestorDTO.setSignature("TESTMZ");
		requestorDTO.setSite("QVI");
		permissionsDTO.setRequestorDTO(requestorDTO);
		permissionsDTO.setPermission(listPermissionDTO);

		List<InformationsDTO> listInformationsDTO = communicationPreferencesDS.createComPrefBasedOnAPermission(permissionsDTO);

		Assert.assertNotNull(listInformationsDTO);
		Assert.assertEquals("0", listInformationsDTO.get(0).getInformation().get(0).getCode());
		LOG.info("END testCreateComPrefBasedOnAPermission...");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCreateComPrefBasedOnAPermissionBoucle() throws JrafDomainException {
		LOG.info("START testCreateComPrefBasedOnAPermissionBoucle");

		IndividuDTO individuDTO = createIndividuForTest(false);
		RefPermissionsDTO refPermissionsDTO = createPermissionsForTest();

		PermissionDTO permissionDTO = new PermissionDTO();
		permissionDTO.setAnswer(false);
		permissionDTO.setLanguage("EN");
		permissionDTO.setMarket("EN");
		permissionDTO.setPermissionId(refPermissionsDTO.getRefPermissionsId().getQuestionId().getId().toString());
		List<PermissionDTO> listPermissionDTO = new ArrayList<PermissionDTO>();
		listPermissionDTO.add(permissionDTO);
		PermissionsDTO permissionsDTO = new PermissionsDTO();
		permissionsDTO.setGin(individuDTO.getSgin());
		RequestorDTO requestorDTO = new RequestorDTO();
		requestorDTO.setChannel("B2C");
		requestorDTO.setSignature("TESTMZ");
		requestorDTO.setSite("QVI");
		permissionsDTO.setRequestorDTO(requestorDTO);
		permissionsDTO.setPermission(listPermissionDTO);

		List<InformationsDTO> listInformationsDTO = null;
		for(int i=0; i<2; i++){
			listInformationsDTO = communicationPreferencesDS.createComPrefBasedOnAPermission(permissionsDTO);
		}

		Assert.assertTrue(trackingPermissionsDS.getByGin(individuDTO.getSgin()) == 2);
		Assert.assertNotNull(listInformationsDTO);
		Assert.assertEquals("0", listInformationsDTO.get(0).getInformation().get(0).getCode());
		LOG.info("END testCreateComPrefBasedOnAPermissionBoucle...");

	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateUpdateComPrefFromDGT_UPDATE_Sales() throws JrafDomainException {
		LOG.info("START testCreateUpdateComPrefFromDGT_UPDATE_Sales...");

		IndividuDTO individuDTO = createIndividuForTest(false);

		CommunicationPreferencesDTO comPrefToProcess = new CommunicationPreferencesDTO();
		comPrefToProcess.setChannel("B2C");
		comPrefToProcess.setComGroupType("N");
		comPrefToProcess.setComType("AF");
		comPrefToProcess.setDomain("S");
		comPrefToProcess.setDateOptin(new Date());
		comPrefToProcess.setMedia1("E");
		comPrefToProcess.setSubscribe("Y");
		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setDateOfConsent(new Date());
		ml.setMedia1("E");
		ml.setOptIn("Y");
		ml.setMarket("FR");
		ml.setLanguage("FR");
		comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		comPrefToProcess.getMarketLanguageDTO().add(ml);

		communicationPreferencesDS.createUpdateComPrefFromDGT(individuDTO.getSgin(), comPrefToProcess, "QVI", "TESTMZ");
		
		List<CommunicationPreferences> listComPref = communicationPreferencesRepository.findByGin(individuDTO.getSgin());
		Assert.assertEquals(1, listComPref.size());
		Assert.assertEquals(1, listComPref.get(0).getMarketLanguage().size());
		
		comPrefToProcess = new CommunicationPreferencesDTO();
		comPrefToProcess.setChannel("B2C");
		comPrefToProcess.setComGroupType("N");
		comPrefToProcess.setComType("AF");
		comPrefToProcess.setDomain("S");
		comPrefToProcess.setDateOptin(new Date());
		comPrefToProcess.setMedia1("E");
		comPrefToProcess.setSubscribe("Y");
		ml = new MarketLanguageDTO();
		ml.setDateOfConsent(new Date());
		ml.setMedia1("E");
		ml.setOptIn("Y");
		ml.setMarket("DE");
		ml.setLanguage("DE");
		comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		comPrefToProcess.getMarketLanguageDTO().add(ml);

		communicationPreferencesDS.createUpdateComPrefFromDGT(individuDTO.getSgin(), comPrefToProcess, "QVI", "TESTMZ");
		
		listComPref = communicationPreferencesRepository.findByGin(individuDTO.getSgin());
		Assert.assertEquals(1, listComPref.size());
		Assert.assertEquals(2, listComPref.get(0).getMarketLanguage().size());
		
		comPrefToProcess = new CommunicationPreferencesDTO();
		comPrefToProcess.setChannel("B2C");
		comPrefToProcess.setComGroupType("N");
		comPrefToProcess.setComType("AF");
		comPrefToProcess.setDomain("S");
		comPrefToProcess.setDateOptin(new Date());
		comPrefToProcess.setMedia1("E");
		comPrefToProcess.setSubscribe("Y");
		ml = new MarketLanguageDTO();
		ml.setDateOfConsent(new Date());
		ml.setMedia1("E");
		ml.setOptIn("Y");
		ml.setMarket("IT");
		ml.setLanguage("IT");
		comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		comPrefToProcess.getMarketLanguageDTO().add(ml);

		communicationPreferencesDS.createUpdateComPrefFromDGT(individuDTO.getSgin(), comPrefToProcess, "QVI", "TESTMZ");
		
		listComPref = communicationPreferencesRepository.findByGin(individuDTO.getSgin());
		Assert.assertEquals(1, listComPref.size());
		Assert.assertEquals(3, listComPref.get(0).getMarketLanguage().size());
		
		comPrefToProcess = new CommunicationPreferencesDTO();
		comPrefToProcess.setChannel("B2C");
		comPrefToProcess.setComGroupType("N");
		comPrefToProcess.setComType("AF");
		comPrefToProcess.setDomain("S");
		comPrefToProcess.setDateOptin(new Date());
		comPrefToProcess.setMedia1("E");
		comPrefToProcess.setSubscribe("Y");
		ml = new MarketLanguageDTO();
		ml.setDateOfConsent(new Date());
		ml.setMedia1("E");
		ml.setOptIn("Y");
		ml.setMarket("NL");
		ml.setLanguage("NL");
		comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		comPrefToProcess.getMarketLanguageDTO().add(ml);

		communicationPreferencesDS.createUpdateComPrefFromDGT(individuDTO.getSgin(), comPrefToProcess, "QVI", "TESTMZ");
		
		listComPref = communicationPreferencesRepository.findByGin(individuDTO.getSgin());
		Assert.assertEquals(1, listComPref.size());
		Assert.assertEquals(4, listComPref.get(0).getMarketLanguage().size());
		
		comPrefToProcess = new CommunicationPreferencesDTO();
		comPrefToProcess.setChannel("B2C");
		comPrefToProcess.setComGroupType("N");
		comPrefToProcess.setComType("AF");
		comPrefToProcess.setDomain("S");
		comPrefToProcess.setDateOptin(new Date());
		comPrefToProcess.setMedia1("E");
		comPrefToProcess.setSubscribe("Y");
		ml = new MarketLanguageDTO();
		ml.setDateOfConsent(new Date());
		ml.setMedia1("E");
		ml.setOptIn("Y");
		ml.setMarket("ES");
		ml.setLanguage("ES");
		comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		comPrefToProcess.getMarketLanguageDTO().add(ml);

		communicationPreferencesDS.createUpdateComPrefFromDGT(individuDTO.getSgin(), comPrefToProcess, "QVI", "TESTMZ");
		
		listComPref = communicationPreferencesRepository.findByGin(individuDTO.getSgin());
		Assert.assertEquals(1, listComPref.size());
		Assert.assertEquals(5, listComPref.get(0).getMarketLanguage().size());
		
		comPrefToProcess = new CommunicationPreferencesDTO();
		comPrefToProcess.setChannel("B2C");
		comPrefToProcess.setComGroupType("N");
		comPrefToProcess.setComType("AF");
		comPrefToProcess.setDomain("S");
		comPrefToProcess.setDateOptin(new Date());
		comPrefToProcess.setMedia1("E");
		comPrefToProcess.setSubscribe("Y");
		ml = new MarketLanguageDTO();
		ml.setDateOfConsent(new Date());
		ml.setMedia1("E");
		ml.setOptIn("Y");
		ml.setMarket("FR");
		ml.setLanguage("JP");
		comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		comPrefToProcess.getMarketLanguageDTO().add(ml);

		communicationPreferencesDS.createUpdateComPrefFromDGT(individuDTO.getSgin(), comPrefToProcess, "QVI", "TESTMZ");
		
		listComPref = communicationPreferencesRepository.findByGin(individuDTO.getSgin());
		Assert.assertEquals(1, listComPref.size());
		Assert.assertEquals(5, listComPref.get(0).getMarketLanguage().size());
		
		LOG.info("END testCreateUpdateComPrefFromDGT_UPDATE_Sales...");
	}
	
	@Test
	public void testCreateUpdateComPrefFromDGT_GlobalOptin() throws JrafDomainException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		LOG.info("START testCreateUpdateComPrefFromDGT_GlobalOptin...");

		CommunicationPreferencesDTO comPrefToProcess = new CommunicationPreferencesDTO();
		comPrefToProcess.setSubscribe("N");
		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setOptIn("Y");
		comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		comPrefToProcess.getMarketLanguageDTO().add(ml);

		CommunicationPreferencesDS communicationPreferencesDS = new CommunicationPreferencesDS();
		Method method = CommunicationPreferencesDS.class.getDeclaredMethod("validateGlobalOptin", CommunicationPreferencesDTO.class);
		method.setAccessible(true);
		method.invoke(communicationPreferencesDS, comPrefToProcess);
		
		Assert.assertEquals("Y", comPrefToProcess.getSubscribe());
		
		comPrefToProcess = new CommunicationPreferencesDTO();
		comPrefToProcess.setSubscribe("Y");
		ml = new MarketLanguageDTO();
		ml.setOptIn("N");
		comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		comPrefToProcess.getMarketLanguageDTO().add(ml);

		method.invoke(communicationPreferencesDS, comPrefToProcess);
		
		Assert.assertEquals("N", comPrefToProcess.getSubscribe());
		
		comPrefToProcess = new CommunicationPreferencesDTO();
		comPrefToProcess.setSubscribe("N");
		ml = new MarketLanguageDTO();
		ml.setOptIn("N");
		comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		comPrefToProcess.getMarketLanguageDTO().add(ml);
		MarketLanguageDTO mlBis = new MarketLanguageDTO();
		mlBis.setOptIn("Y");
		comPrefToProcess.getMarketLanguageDTO().add(mlBis);

		method.invoke(communicationPreferencesDS, comPrefToProcess);
		
		Assert.assertEquals("Y", comPrefToProcess.getSubscribe());
		
		LOG.info("END testCreateUpdateComPrefFromDGT_GlobalOptin...");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateUpdateComPrefFromDGT_DELETE_OK() throws JrafDomainException {
		
		LOG.info("START testCreateUpdateComPrefFromDGT_DELETE_OK...");

		IndividuDTO individuDTO = createIndividuForTest(true);
		
		CommunicationPreferencesDTO comPrefToProcess = new CommunicationPreferencesDTO();
		comPrefToProcess.setComGroupType("N");
		comPrefToProcess.setComType("AF");
		comPrefToProcess.setDomain("S");
		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setDateOfConsent(new Date());
		ml.setMarket("FR");
		ml.setLanguage("FR");
		comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		comPrefToProcess.getMarketLanguageDTO().add(ml);
		
		List<CommunicationPreferencesDTO> comprefWS = new ArrayList<CommunicationPreferencesDTO>();
		comprefWS.add(comPrefToProcess);
		
		
		List<CommunicationPreferences> listComPref = communicationPreferencesRepository.findByGin(individuDTO.getSgin());
		
		Assert.assertEquals(2, listComPref.get(0).getMarketLanguage().size());
		
		communicationPreferencesDS.deleteComPrefFromDGT(individuDTO.getSgin(), comprefWS);
		
		listComPref = communicationPreferencesRepository.findByGin(individuDTO.getSgin());
		
		CommunicationPreferences comprefResult = listComPref.get(0);
		
		Assert.assertEquals(1, comprefResult.getMarketLanguage().size());
		Assert.assertEquals("IT", comprefResult.getMarketLanguage().stream().findFirst().get().getMarket());
		Assert.assertEquals("IT", comprefResult.getMarketLanguage().stream().findFirst().get().getLanguage());
		
		LOG.info("END testCreateUpdateComPrefFromDGT_DELETE_OK...");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateUpdateComPrefFromDGT_DELETE_COMPREF() throws JrafDomainException {
		
		LOG.info("START testCreateUpdateComPrefFromDGT_DELETE_COMPREF...");

		IndividuDTO individuDTO = createIndividuForTest(true);
		
		CommunicationPreferencesDTO comPrefToProcess = new CommunicationPreferencesDTO();
		comPrefToProcess.setComGroupType("N");
		comPrefToProcess.setComType("AF");
		comPrefToProcess.setDomain("S");
		MarketLanguageDTO mlA = new MarketLanguageDTO();
		mlA.setDateOfConsent(new Date());
		mlA.setMarket("FR");
		mlA.setLanguage("FR");
		MarketLanguageDTO mlB = new MarketLanguageDTO();
		mlB.setDateOfConsent(new Date());
		mlB.setMarket("IT");
		mlB.setLanguage("IT");
		comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		comPrefToProcess.getMarketLanguageDTO().add(mlA);
		comPrefToProcess.getMarketLanguageDTO().add(mlB);
		
		List<CommunicationPreferencesDTO> comprefWS = new ArrayList<CommunicationPreferencesDTO>();
		comprefWS.add(comPrefToProcess);
		
		
		List<CommunicationPreferences> listComPref = communicationPreferencesRepository.findByGin(individuDTO.getSgin());
		
		Assert.assertEquals(2, listComPref.get(0).getMarketLanguage().size());
		
		communicationPreferencesDS.deleteComPrefFromDGT(individuDTO.getSgin(), comprefWS);
		
		listComPref = communicationPreferencesRepository.findByGin(individuDTO.getSgin());
		
		Assert.assertEquals(0, listComPref.size());

		LOG.info("END testCreateUpdateComPrefFromDGT_DELETE_COMPREF...");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateUpdateComPrefFromDGT_DELETE_COMPREF_NOT_FOUND() throws JrafDomainException {
		
		LOG.info("START testCreateUpdateComPrefFromDGT_DELETE_COMPREF_NOT_FOUND...");

		IndividuDTO individuDTO = createIndividuForTest(true);
		
		CommunicationPreferencesDTO comPrefToProcess = new CommunicationPreferencesDTO();
		comPrefToProcess.setComGroupType("N");
		comPrefToProcess.setComType("KL");
		comPrefToProcess.setDomain("S");
		MarketLanguageDTO mlA = new MarketLanguageDTO();
		mlA.setDateOfConsent(new Date());
		mlA.setMarket("FR");
		mlA.setLanguage("FR");
		comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		comPrefToProcess.getMarketLanguageDTO().add(mlA);
		
		List<CommunicationPreferencesDTO> comprefWS = new ArrayList<CommunicationPreferencesDTO>();
		comprefWS.add(comPrefToProcess);
		
		List<CommunicationPreferences> listComPref = communicationPreferencesRepository.findByGin(individuDTO.getSgin());
		
		Assert.assertEquals(2, listComPref.get(0).getMarketLanguage().size());
		
		try {
			communicationPreferencesDS.deleteComPrefFromDGT(individuDTO.getSgin(), comprefWS);
		} catch (JrafDomainException e) {
			Assert.assertEquals("Communication Preference does not exist. Unable to delete.", e.getMessage());
		}

		LOG.info("END testCreateUpdateComPrefFromDGT_DELETE_COMPREF_NOT_FOUND...");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateUpdateComPrefFromDGT_DELETE_ML_NOT_FOUND() throws JrafDomainException {
		
		LOG.info("START testCreateUpdateComPrefFromDGT_DELETE_COMPREF_NOT_FOUND...");

		IndividuDTO individuDTO = createIndividuForTest(true);
		
		CommunicationPreferencesDTO comPrefToProcess = new CommunicationPreferencesDTO();
		comPrefToProcess.setComGroupType("N");
		comPrefToProcess.setComType("AF");
		comPrefToProcess.setDomain("S");
		MarketLanguageDTO mlA = new MarketLanguageDTO();
		mlA.setDateOfConsent(new Date());
		mlA.setMarket("DE");
		mlA.setLanguage("DE");
		comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		comPrefToProcess.getMarketLanguageDTO().add(mlA);
		
		List<CommunicationPreferencesDTO> comprefWS = new ArrayList<CommunicationPreferencesDTO>();
		comprefWS.add(comPrefToProcess);
		
		List<CommunicationPreferences> listComPref = communicationPreferencesRepository.findByGin(individuDTO.getSgin());
		
		Assert.assertEquals(2, listComPref.get(0).getMarketLanguage().size());
		
		try {
			communicationPreferencesDS.deleteComPrefFromDGT(individuDTO.getSgin(), comprefWS);
		} catch (JrafDomainException e) {
			Assert.assertEquals("Market Language does not exist. Unable to delete.", e.getMessage());
		}

		LOG.info("END testCreateUpdateComPrefFromDGT_DELETE_COMPREF_NOT_FOUND...");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateUpdateComPrefFromDGT_DELETE_ML_NOT_PROVIDED() throws JrafDomainException {
		
		LOG.info("START testCreateUpdateComPrefFromDGT_DELETE_COMPREF_NOT_FOUND...");

		IndividuDTO individuDTO = createIndividuForTest(true);
		
		CommunicationPreferencesDTO comPrefToProcess = new CommunicationPreferencesDTO();
		comPrefToProcess.setComGroupType("N");
		comPrefToProcess.setComType("AF");
		comPrefToProcess.setDomain("S");
		comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		
		List<CommunicationPreferencesDTO> comprefWS = new ArrayList<CommunicationPreferencesDTO>();
		comprefWS.add(comPrefToProcess);
		
		List<CommunicationPreferences> listComPref = communicationPreferencesRepository.findByGin(individuDTO.getSgin());
		
		Assert.assertEquals(2, listComPref.get(0).getMarketLanguage().size());
		
		try {
			communicationPreferencesDS.deleteComPrefFromDGT(individuDTO.getSgin(), comprefWS);
		} catch (JrafDomainException e) {
			Assert.assertEquals("Market Language not provided. Unable to delete.", e.getMessage());
		}

		LOG.info("END testCreateUpdateComPrefFromDGT_DELETE_COMPREF_NOT_FOUND...");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateUpdateComPrefFromDGT_DELETE_WRONG_DOMAIN() throws JrafDomainException {
		
		LOG.info("START testCreateUpdateComPrefFromDGT_DELETE_COMPREF_NOT_FOUND...");

		IndividuDTO individuDTO = createIndividuForTest(true);
		
		CommunicationPreferencesDTO comPrefToProcess = new CommunicationPreferencesDTO();
		comPrefToProcess.setComGroupType("N");
		comPrefToProcess.setComType("FB_NEWS");
		comPrefToProcess.setDomain("F");
		comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
		
		List<CommunicationPreferencesDTO> comprefWS = new ArrayList<CommunicationPreferencesDTO>();
		comprefWS.add(comPrefToProcess);
		
		List<CommunicationPreferences> listComPref = communicationPreferencesRepository.findByGin(individuDTO.getSgin());
		
		Assert.assertEquals(2, listComPref.get(0).getMarketLanguage().size());
		
		try {
			communicationPreferencesDS.deleteComPrefFromDGT(individuDTO.getSgin(), comprefWS);
		} catch (JrafDomainException e) {
			Assert.assertEquals("Only Sales Domain can be deleted.", e.getMessage());
		}

		LOG.info("END testCreateUpdateComPrefFromDGT_DELETE_COMPREF_NOT_FOUND...");
	}
}
