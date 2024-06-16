package com.airfrance.repind.service.ws.internal.ut.helper;

import com.airfrance.ref.exception.compref.CommunicationPreferenceException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.reference.RefComPrefCountryMarketRepository;
import com.airfrance.repind.dao.reference.RefComPrefRepository;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.InformationResponseDTO;
import com.airfrance.repind.dto.profil.ProfilsDTO;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.reference.RefComPref;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.ws.internal.helpers.CommunicationPreferencesHelper;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class CommunicationPreferencesHelperTest extends CommunicationPreferencesHelper {
	
	
	// Initialization : done before each test case annotated @Test
	@Before
	public void setUp() throws JrafDomainException {
		refComPrefRepository = EasyMock.createMock(RefComPrefRepository.class);
		refComPrefCountryMarketRepository = EasyMock.createMock(RefComPrefCountryMarketRepository.class);
		communicationPreferencesRepository = EasyMock.createMock(CommunicationPreferencesRepository.class);
		individuDS = EasyMock.createMock(IndividuDS.class);
	}
	
	@Test
	public void testCrmOptinComPrefsWithASalesComPref() throws JrafDaoException, CommunicationPreferenceException {
		List<CommunicationPreferencesDTO> comPrefsList = new ArrayList<CommunicationPreferencesDTO>();
		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
		CreateModifyIndividualResponseDTO responseDTO = new CreateModifyIndividualResponseDTO();
		cp.setDomain("S");
		cp.setSubscribe("Y");
		comPrefsList.add(cp);
		crmOptinComPrefs(comPrefsList, responseDTO);
		Assert.assertEquals(1, comPrefsList.size());
		Assert.assertEquals("Y", comPrefsList.get(0).getSubscribe());

		for (InformationResponseDTO ir : responseDTO.getInformationResponse() ) {
			Assert.assertEquals("Warning : One or many market languages created with an unknown value", ir.getInformation().getInformationDetails());
		}
	}
	
	@Test
	public void testCrmOptinComPrefsWithAFBComPrefMandatoryOptin() throws JrafDaoException, CommunicationPreferenceException {
		List<CommunicationPreferencesDTO> comPrefsList = new ArrayList<CommunicationPreferencesDTO>();
		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
		cp.setDomain("F");
		cp.setComType("FB_CC");
		cp.setComGroupType("N");
		cp.setSubscribe("N");
		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setLanguage("FR");
		ml.setMarket("FR");
		ml.setOptIn("N");
		Set<MarketLanguageDTO> mlSet = new HashSet<MarketLanguageDTO>();
		mlSet.add(ml);
		cp.setMarketLanguageDTO(mlSet);
		comPrefsList.add(cp);
		
		RefComPref rcp = new RefComPref();
		rcp.setMandatoryOptin("Y");
		rcp.setMarket("FR");
		
		List<RefComPref> rcpList = new ArrayList<RefComPref>();
		rcpList.add(rcp);
		EasyMock.expect(refComPrefRepository.findComPrefByDomainComTypeComGroupType("F", "FB_CC", "N")).andReturn(rcpList);
		EasyMock.expect(refComPrefRepository.findComPerfByMarketComTypeComGType("FR", "F", "FB_CC", "N")).andReturn(rcpList);
		EasyMock.replay(refComPrefRepository);
		
		crmOptinComPrefs(comPrefsList, null);
		
		Assert.assertEquals(1, comPrefsList.size());
		
		// Optin should be "Y" due to mandatory optin
		Assert.assertEquals("Y", comPrefsList.get(0).getSubscribe());
		for(MarketLanguageDTO ml2 : comPrefsList.get(0).getMarketLanguageDTO()) {
			Assert.assertEquals("Y", ml2.getOptIn());
		}
	}
	
	@Test
	public void testCrmOptinComPrefsWithAFBComPrefMarketAll() throws JrafDaoException, CommunicationPreferenceException {
		List<CommunicationPreferencesDTO> comPrefsList = new ArrayList<CommunicationPreferencesDTO>();
		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
		cp.setDomain("F");
		cp.setComType("FB_CC");
		cp.setComGroupType("N");
		cp.setSubscribe("N");
		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setLanguage("FR");
		ml.setMarket("FR");
		ml.setOptIn("N");
		Set<MarketLanguageDTO> mlSet = new HashSet<MarketLanguageDTO>();
		mlSet.add(ml);
		cp.setMarketLanguageDTO(mlSet);
		comPrefsList.add(cp);
		
		RefComPref rcp = new RefComPref();
		rcp.setMandatoryOptin("Y");
		rcp.setMarket("FR");
		
		List<RefComPref> rcpList = new ArrayList<RefComPref>();
		rcpList.add(rcp);
		
		RefComPref rcp2 = new RefComPref();
		rcp2.setMandatoryOptin("Y");
		rcp2.setMarket("*");
		rcp2.setDefaultLanguage1("EN");
		List<RefComPref> rcpList2 = new ArrayList<RefComPref>();
		rcpList2.add(rcp2);
		
		EasyMock.expect(refComPrefRepository.findComPrefByDomainComTypeComGroupType("F", "FB_CC", "N")).andReturn(rcpList);
		EasyMock.expect(refComPrefRepository.findComPerfByMarketComTypeComGType("FR", "F", "FB_CC", "N")).andReturn(null);
		EasyMock.expect(refComPrefRepository.findComPerfByMarketComTypeComGType("*", "F", "FB_CC", "N")).andReturn(rcpList);
		EasyMock.expect(refComPrefRepository.findComPerfByMarketComTypeComGType("FR", "F", "FB_ESS", "N")).andReturn(rcpList2);
		
		EasyMock.replay(refComPrefRepository);
		
		crmOptinComPrefs(comPrefsList, null);
		
		Assert.assertEquals(1, comPrefsList.size());
		
		// Optin should be "Y" due to mandatory optin
		// Language should be "EN" due to languages rules and FB_ESS
		Assert.assertEquals("Y", comPrefsList.get(0).getSubscribe());
		for(MarketLanguageDTO ml2 : comPrefsList.get(0).getMarketLanguageDTO()) {
			Assert.assertEquals("Y", ml2.getOptIn());
			Assert.assertEquals("EN", ml2.getLanguage());
		}
	}
	
	@Test
	public void testCrmOptinComPrefsWithASalesProspectComPref() throws JrafDaoException, CommunicationPreferenceException {
		List<CommunicationPreferencesDTO> comPrefsSet = new ArrayList<CommunicationPreferencesDTO>();
		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
		CreateModifyIndividualResponseDTO responseDTO = new CreateModifyIndividualResponseDTO();
		cp.setDomain("S");
		cp.setSubscribe("Y");
		comPrefsSet.add(cp);
		crmOptinComPrefs(comPrefsSet, responseDTO);
		for(CommunicationPreferencesDTO cp2 : comPrefsSet) {
			Assert.assertEquals("Y", cp2.getSubscribe());
		}		
		for (InformationResponseDTO ir : responseDTO.getInformationResponse() ) {
			Assert.assertEquals("Warning : One or many market languages created with an unknown value", ir.getInformation().getInformationDetails());
		}
	}
	
	@Test
	public void testCrmOptinComPrefsWithAFBProspectComPrefMandatoryOptin() throws JrafDaoException, CommunicationPreferenceException {
		List<CommunicationPreferencesDTO> comPrefsSet = new ArrayList<CommunicationPreferencesDTO>();
		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
		cp.setDomain("F");
		cp.setComType("FB_CC");
		cp.setComGroupType("N");
		cp.setSubscribe("N");
		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setLanguage("FR");
		ml.setMarket("FR");
		ml.setOptIn("N");
		Set<MarketLanguageDTO> mlSet = new HashSet<MarketLanguageDTO>();
		mlSet.add(ml);
		cp.setMarketLanguageDTO(mlSet);
		comPrefsSet.add(cp);
		
		RefComPref rcp = new RefComPref();
		rcp.setMandatoryOptin("Y");
		rcp.setMarket("FR");
		
		List<RefComPref> rcpList = new ArrayList<RefComPref>();
		rcpList.add(rcp);
		EasyMock.expect(refComPrefRepository.findComPrefByDomainComTypeComGroupType("F", "FB_CC", "N")).andReturn(rcpList);
		EasyMock.expect(refComPrefRepository.findComPerfByMarketComTypeComGType("FR", "F", "FB_CC", "N")).andReturn(rcpList);
		EasyMock.replay(refComPrefRepository);
		
		crmOptinComPrefs(comPrefsSet, null);
		
		// Optin should be "Y" due to mandatory optin
		for(CommunicationPreferencesDTO cp2 : comPrefsSet) {
			Assert.assertEquals("Y", cp2.getSubscribe());
			for(MarketLanguageDTO ml2 : cp2.getMarketLanguageDTO()) {
				Assert.assertEquals("Y", ml2.getOptIn());
			}
		}	
	}
	
	@Test
	public void testCrmOptinComPrefsWithAFBProspectComPrefMarketAll() throws JrafDaoException, CommunicationPreferenceException {
		List<CommunicationPreferencesDTO> comPrefsSet = new ArrayList<CommunicationPreferencesDTO>();
		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
		cp.setDomain("F");
		cp.setComType("FB_CC");
		cp.setComGroupType("N");
		cp.setSubscribe("N");
		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setLanguage("FR");
		ml.setMarket("FR");
		ml.setOptIn("N");
		Set<MarketLanguageDTO> mlSet = new HashSet<MarketLanguageDTO>();
		mlSet.add(ml);
		cp.setMarketLanguageDTO(mlSet);
		comPrefsSet.add(cp);
		
		RefComPref rcp = new RefComPref();
		rcp.setMandatoryOptin("Y");
		rcp.setMarket("FR");
		
		List<RefComPref> rcpList = new ArrayList<RefComPref>();
		rcpList.add(rcp);
		
		RefComPref rcp2 = new RefComPref();
		rcp2.setMandatoryOptin("Y");
		rcp2.setMarket("*");
		rcp2.setDefaultLanguage1("EN");
		List<RefComPref> rcpList2 = new ArrayList<RefComPref>();
		rcpList2.add(rcp2);
		
		EasyMock.expect(refComPrefRepository.findComPrefByDomainComTypeComGroupType("F", "FB_CC", "N")).andReturn(rcpList);
		EasyMock.expect(refComPrefRepository.findComPerfByMarketComTypeComGType("FR", "F", "FB_CC", "N")).andReturn(null);
		EasyMock.expect(refComPrefRepository.findComPerfByMarketComTypeComGType("*", "F", "FB_CC", "N")).andReturn(rcpList);
		EasyMock.expect(refComPrefRepository.findComPerfByMarketComTypeComGType("FR", "F", "FB_ESS", "N")).andReturn(rcpList2);
		
		EasyMock.replay(refComPrefRepository);
		
		crmOptinComPrefs(comPrefsSet, null);
		
		// Optin should be "Y" due to mandatory optin
		// Language should be "EN" due to languages rules and FB_ESS
		for(CommunicationPreferencesDTO cp2 : comPrefsSet) {
			Assert.assertEquals("Y", cp2.getSubscribe());
			for(MarketLanguageDTO ml2 : cp2.getMarketLanguageDTO()) {
				Assert.assertEquals("Y", ml2.getOptIn());
				Assert.assertEquals("EN", ml2.getLanguage());
			}
		}
	}
	
	
	@Test
	public void testUpdateFBMarketLanguagesWhenMovingFullTest() throws JrafDomainException {
		
		String oldSpokenLanguage = "FR";
		String oldCountryCode = "FR";
		String newSpokenLanguage = "IT";
		String newCountryCode = "DE";
		String sgin = "112233445566";

		Instant nowInstant = Instant.now();
		Date yesterday = Date.from(nowInstant.minus(1, ChronoUnit.DAYS));
		Date now = Date.from(nowInstant);

		IndividuDTO individualFromDB = new IndividuDTO();
		individualFromDB.setSgin(sgin);

		ProfilsDTO profilsDtoFromDB = new ProfilsDTO();
		profilsDtoFromDB.setScode_langue(oldSpokenLanguage);
		individualFromDB.setProfilsdto(profilsDtoFromDB);
		
		List<PostalAddressDTO> postalAddressListFromDB = new ArrayList<PostalAddressDTO>();
		PostalAddressDTO postalAddressFromDB = new PostalAddressDTO();
		postalAddressFromDB.setScode_medium("D");
		postalAddressFromDB.setSstatut_medium("V");
		postalAddressFromDB.setScode_pays(oldCountryCode);
		Usage_mediumDTO usageMediumDto = new Usage_mediumDTO();
		usageMediumDto.setSrole1("M");
		usageMediumDto.setScode_application("ISI");
		Set<Usage_mediumDTO> usageMediumDtoSet = new HashSet<Usage_mediumDTO>();
		usageMediumDtoSet.add(usageMediumDto);
		postalAddressFromDB.setUsage_mediumdto(usageMediumDtoSet);
		postalAddressListFromDB.add(postalAddressFromDB);
		individualFromDB.setPostaladdressdto(postalAddressListFromDB);
		
		IndividuDTO individualFromWS = new IndividuDTO();
		
		ProfilsDTO profilsDtoFromWS = new ProfilsDTO();
		profilsDtoFromWS.setScode_langue(newSpokenLanguage);
		individualFromWS.setProfilsdto(profilsDtoFromWS);
		
		List<PostalAddressDTO> postalAddressListFromWS = new ArrayList<PostalAddressDTO>();
		PostalAddressDTO postalAddressFromWS = new PostalAddressDTO();
		postalAddressFromWS.setScode_medium("D");
		postalAddressFromWS.setSstatut_medium("V");
		postalAddressFromWS.setScode_pays(newCountryCode);
		Usage_mediumDTO usageMediumDtoFromWS = new Usage_mediumDTO();
		usageMediumDtoFromWS.setSrole1("M");
		usageMediumDtoFromWS.setScode_application("ISI");
		Set<Usage_mediumDTO> usageMediumDtoSetFromWS = new HashSet<Usage_mediumDTO>();
		usageMediumDtoSetFromWS.add(usageMediumDtoFromWS);
		postalAddressFromWS.setUsage_mediumdto(usageMediumDtoSetFromWS);
		postalAddressListFromWS.add(postalAddressFromWS);
		individualFromWS.setPostaladdressdto(postalAddressListFromWS);
		
		List<CommunicationPreferencesDTO> communicationPreferencesFromDB = new ArrayList<>();
		
		CommunicationPreferencesDTO oldCp1 = new CommunicationPreferencesDTO();
		Set<MarketLanguageDTO> oldMlSet1 = new HashSet<>();
		MarketLanguageDTO oldMl1 = new MarketLanguageDTO();
		oldMl1.setMarketLanguageId(1);
		oldMl1.setMarket("FR");
		oldMl1.setLanguage("FR");
		oldMl1.setModificationDate(yesterday);
		oldMl1.setModificationSite("QVI");
		oldMl1.setModificationSignature("CBS");
		oldMlSet1.add(oldMl1);
		oldCp1.setMarketLanguageDTO(oldMlSet1);
		oldCp1.setDomain("F");
		oldCp1.setComType("FB_NEWS");
		oldCp1.setModificationDate(yesterday);
		oldCp1.setModificationSite("QVI");
		oldCp1.setModificationSignature("CBS");
		communicationPreferencesFromDB.add(oldCp1);
		
		CommunicationPreferencesDTO oldCp2 = new CommunicationPreferencesDTO();
		Set<MarketLanguageDTO> oldMlSet2 = new HashSet<MarketLanguageDTO>();
		MarketLanguageDTO oldMl2 = new MarketLanguageDTO();
		oldMl2.setMarketLanguageId(1);
		oldMl2.setMarket("FR");
		oldMl2.setLanguage("FR");
		oldMl2.setModificationDate(yesterday);
		oldMl2.setModificationSite("QVI");
		oldMl2.setModificationSignature("CBS");
		oldMlSet2.add(oldMl2);
		oldCp2.setMarketLanguageDTO(oldMlSet2);
		oldCp2.setDomain("F");
		oldCp2.setComType("FB_PROG");
		oldCp2.setModificationDate(yesterday);
		oldCp2.setModificationSite("QVI");
		oldCp2.setModificationSignature("CBS");
		communicationPreferencesFromDB.add(oldCp2);
		
		CommunicationPreferencesDTO oldCp3 = new CommunicationPreferencesDTO();
		Set<MarketLanguageDTO> oldMlSet3 = new HashSet<MarketLanguageDTO>();
		MarketLanguageDTO oldMl3 = new MarketLanguageDTO();
		oldMl3.setMarketLanguageId(1);
		oldMl3.setMarket("FR");
		oldMl3.setLanguage("FR");
		oldMl3.setModificationDate(yesterday);
		oldMl3.setModificationSite("QVI");
		oldMl3.setModificationSignature("CBS");
		oldMlSet3.add(oldMl3);
		oldCp3.setMarketLanguageDTO(oldMlSet3);
		oldCp3.setDomain("F");
		oldCp3.setComType("FB_PART");
		oldCp3.setModificationDate(yesterday);
		oldCp3.setModificationSite("QVI");
		oldCp3.setModificationSignature("CBS");
		communicationPreferencesFromDB.add(oldCp3);
		
		CommunicationPreferencesDTO oldCp4 = new CommunicationPreferencesDTO();
		Set<MarketLanguageDTO> oldMlSet4 = new HashSet<MarketLanguageDTO>();
		MarketLanguageDTO oldMl4 = new MarketLanguageDTO();
		oldMl4.setMarketLanguageId(1);
		oldMl4.setMarket("FR");
		oldMl4.setLanguage("FR");
		oldMl4.setModificationDate(yesterday);
		oldMl4.setModificationSite("QVI");
		oldMl4.setModificationSignature("CBS");
		oldMlSet4.add(oldMl4);
		oldCp4.setMarketLanguageDTO(oldMlSet4);
		oldCp4.setDomain("S");
		oldCp4.setComType("AF");
		oldCp4.setModificationDate(yesterday);
		oldCp4.setModificationSite("QVI");
		oldCp4.setModificationSignature("CBS");
		communicationPreferencesFromDB.add(oldCp4);
		
		individualFromDB.setCommunicationpreferencesdto(communicationPreferencesFromDB);
		
		List<CommunicationPreferencesDTO> communicationPreferencesFromWS = new ArrayList<CommunicationPreferencesDTO>();
		
		CommunicationPreferencesDTO newCp1 = new CommunicationPreferencesDTO();
		Set<MarketLanguageDTO> newMlSet1 = new HashSet<MarketLanguageDTO>();
		MarketLanguageDTO newMl1 = new MarketLanguageDTO();
		newMl1.setMarketLanguageId(1);
		newMl1.setMarket("FR");
		newMl1.setLanguage("FR");
		newMlSet1.add(newMl1);
		newCp1.setMarketLanguageDTO(newMlSet1);
		newCp1.setDomain("F");
		newCp1.setComType("FB_CC");
		communicationPreferencesFromWS.add(newCp1);
		
		CommunicationPreferencesDTO newCp2 = new CommunicationPreferencesDTO();
		Set<MarketLanguageDTO> newMlSet2 = new HashSet<MarketLanguageDTO>();
		MarketLanguageDTO newMl2 = new MarketLanguageDTO();
		newMl2.setMarketLanguageId(1);
		newMl2.setMarket("FR");
		newMl2.setLanguage("FR");
		newMlSet2.add(newMl2);
		newCp2.setMarketLanguageDTO(newMlSet2);
		newCp2.setDomain("S");
		newCp2.setComType("KL");
		communicationPreferencesFromWS.add(newCp2);
				
		SignatureDTO signature = new SignatureDTO();
		signature.setDate(now);
		signature.setSite("QVI");
		signature.setSignature("REPIND");
		
		List<String> marketList = new ArrayList<String>();
		marketList.add("DE");
		EasyMock.expect(refComPrefCountryMarketRepository.findMarketByCodePays("DE")).andReturn(marketList);
		EasyMock.replay(refComPrefCountryMarketRepository);
		
		List<RefComPref> listRefComPref = new ArrayList<RefComPref>();
		RefComPref refComPrefFbEss = new RefComPref();
		refComPrefFbEss.setDefaultLanguage1("DE");
		refComPrefFbEss.setDefaultLanguage2("EN");
		refComPrefFbEss.setDefaultLanguage3("FR");
		listRefComPref.add(refComPrefFbEss);
		EasyMock.expect(refComPrefRepository.findComPerfByMarketComTypeComGType("DE", "F", "FB_ESS", "N")).andReturn(listRefComPref);
		
		List<RefComPref> listRefComPrefMarketWorld = new ArrayList<RefComPref>();
		RefComPref refComPrefMarketWorld = new RefComPref();
		refComPrefMarketWorld.setMarket("*");
		listRefComPrefMarketWorld.add(refComPrefMarketWorld);
		
		List<RefComPref> listRefComPrefDeIt = new ArrayList<RefComPref>();
		RefComPref refComPrefMarketDeIt = new RefComPref();
		refComPrefMarketDeIt.setMarket("DE");
		refComPrefMarketDeIt.setDefaultLanguage1("DE");
		refComPrefMarketDeIt.setDefaultLanguage2("IT");
		listRefComPrefDeIt.add(refComPrefMarketDeIt);
		
		List<RefComPref> listRefComPrefDeDe = new ArrayList<RefComPref>();
		RefComPref refComPrefMarketDeDe = new RefComPref();
		refComPrefMarketDeDe.setMarket("DE");
		refComPrefMarketDeDe.setDefaultLanguage1("DE");
		listRefComPrefDeDe.add(refComPrefMarketDeDe);
		
		EasyMock.expect(refComPrefRepository.findComPrefByAllMarketsAndComType("DE", "F", "FB_NEWS", "*")).andReturn(listRefComPrefDeIt);
		EasyMock.expect(refComPrefRepository.findComPrefByAllMarketsAndComType("DE", "F", "FB_PART", "*")).andReturn(listRefComPrefDeDe);
		EasyMock.expect(refComPrefRepository.findComPrefByAllMarketsAndComType("DE", "F", "FB_PROG", "*")).andReturn(listRefComPrefMarketWorld);
		EasyMock.expect(refComPrefRepository.findComPrefByAllMarketsAndComType("DE", "F", "FB_CC", "*")).andReturn(listRefComPrefMarketWorld);
		EasyMock.replay(refComPrefRepository);

		CommunicationPreferences cp1 = CommunicationPreferencesTransform.dto2Bo2(oldCp1);
		cp1.setMarketLanguage(Collections.singleton(MarketLanguageTransform.dto2Bo(oldMl1)));

		CommunicationPreferences cp2 = CommunicationPreferencesTransform.dto2Bo2(oldCp2);
		cp2.setMarketLanguage(Collections.singleton(MarketLanguageTransform.dto2Bo(oldMl2)));

		CommunicationPreferences cp3 = CommunicationPreferencesTransform.dto2Bo2(oldCp3);
		cp3.setMarketLanguage(Collections.singleton(MarketLanguageTransform.dto2Bo(oldMl3)));

		CommunicationPreferences cp4 = CommunicationPreferencesTransform.dto2Bo2(oldCp4);
		cp4.setMarketLanguage(Collections.singleton(MarketLanguageTransform.dto2Bo(oldMl4)));

		EasyMock.expect(communicationPreferencesRepository.findComPrefId(sgin, "F", null, "FB_NEWS"))
				.andReturn(cp1);
		EasyMock.expect(communicationPreferencesRepository.findComPrefId(sgin, "F", null, "FB_PART"))
				.andReturn(cp2);
		EasyMock.expect(communicationPreferencesRepository.findComPrefId(sgin, "F", null, "FB_PROG"))
				.andReturn(cp3);
		EasyMock.expect(communicationPreferencesRepository.findComPrefId(sgin, "S", null, "AF"))
				.andReturn(cp4);
		EasyMock.replay(communicationPreferencesRepository);

		//Start the Test
		updateFBMarketLanguagesWhenMoving(individualFromWS, postalAddressListFromWS, communicationPreferencesFromWS, individualFromDB, signature);
		
		//Asserts
		
		//new spoken language is available for the given compref
		Assert.assertEquals("DE", oldCp1.getMarketLanguageDTO().iterator().next().getMarket());
		Assert.assertEquals("IT", oldCp1.getMarketLanguageDTO().iterator().next().getLanguage());
		
		//new spoken language is not available, the old one is still available for the new market
		Assert.assertEquals("DE", oldCp2.getMarketLanguageDTO().iterator().next().getMarket());
		Assert.assertEquals("FR", oldCp2.getMarketLanguageDTO().iterator().next().getLanguage());
		
		//both new and old language are not available, we chose the default one
		Assert.assertEquals("DE", oldCp3.getMarketLanguageDTO().iterator().next().getMarket());
		Assert.assertEquals("DE", oldCp3.getMarketLanguageDTO().iterator().next().getLanguage());
		
		//new spoken language is not available, the old one is still available for the new market
		Assert.assertEquals("DE", newCp1.getMarketLanguageDTO().iterator().next().getMarket());
		Assert.assertEquals("FR", newCp1.getMarketLanguageDTO().iterator().next().getLanguage());
		
		//compref Sales is not updated
		Assert.assertEquals("FR", newCp2.getMarketLanguageDTO().iterator().next().getMarket());
		Assert.assertEquals("FR", newCp2.getMarketLanguageDTO().iterator().next().getLanguage());

		//Modification date of sales is not updated
		Assert.assertEquals(now, cp1.getMarketLanguage().iterator().next().getModificationDate());
		Assert.assertEquals(now, cp2.getMarketLanguage().iterator().next().getModificationDate());
		Assert.assertEquals(now, cp3.getMarketLanguage().iterator().next().getModificationDate());
		Assert.assertEquals(yesterday, cp4.getMarketLanguage().iterator().next().getModificationDate());

		//Modification date of Compref is not updated
		Assert.assertEquals(now, cp1.getModificationDate());
		Assert.assertEquals(now, cp2.getModificationDate());
		Assert.assertEquals(now, cp3.getModificationDate());
		Assert.assertEquals(yesterday,cp4.getModificationDate());

		//Modification site of sales is not updated
		Assert.assertEquals("QVI", cp1.getMarketLanguage().iterator().next().getModificationSite());
		Assert.assertEquals("QVI", cp2.getMarketLanguage().iterator().next().getModificationSite());
		Assert.assertEquals("QVI", cp3.getMarketLanguage().iterator().next().getModificationSite());
		Assert.assertEquals("QVI", cp4.getMarketLanguage().iterator().next().getModificationSite());

		//Modification site of Compref is not updated
		Assert.assertEquals("QVI", cp1.getModificationSite());
		Assert.assertEquals("QVI", cp2.getModificationSite());
		Assert.assertEquals("QVI", cp3.getModificationSite());
		Assert.assertEquals("QVI", cp4.getModificationSite());
	}
}
