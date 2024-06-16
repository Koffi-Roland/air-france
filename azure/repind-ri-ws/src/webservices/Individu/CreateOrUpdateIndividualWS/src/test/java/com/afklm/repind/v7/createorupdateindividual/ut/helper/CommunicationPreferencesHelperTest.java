package com.afklm.repind.v7.createorupdateindividual.ut.helper;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v7.createorupdateindividualws.helpers.CommunicationPreferencesHelper;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualResponse;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.compref.CommunicationPreferenceException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.reference.RefComPrefRepository;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.entity.reference.RefComPref;
import com.airfrance.repind.service.reference.internal.ReferenceDS;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
// import com.airfrance.sicutf8.dto.prospect.ProspectCommunicationPreferencesDTO;
// import com.airfrance.sicutf8.dto.prospect.ProspectMarketLanguageDTO;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CommunicationPreferencesHelperTest extends CommunicationPreferencesHelper {
	
	
	// Initialization : done before each test case annotated @Test
	@Before
	public void setUp() throws JrafDomainException {
		refComPrefRepository = EasyMock.createMock(RefComPrefRepository.class);	
		referencesDS = EasyMock.createMock(ReferenceDS.class);
	}
	
	@Test
	public void testCrmOptinComPrefsWithASalesComPref() throws JrafDaoException, CommunicationPreferenceException {
		List<CommunicationPreferencesDTO> comPrefsList = new ArrayList<CommunicationPreferencesDTO>();
		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		cp.setDomain("S");
		cp.setSubscribe("Y");
		comPrefsList.add(cp);
		crmOptinComPrefs(comPrefsList, response);
		Assert.assertEquals(1, comPrefsList.size());
		Assert.assertEquals("Y", comPrefsList.get(0).getSubscribe());
		Assert.assertEquals("Warning : One or many market languages created with an unknown value", response.getInformationResponse().get(0).getInformation().getInformationDetails());
		
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
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		cp.setDomain("S");
		cp.setSubscribe("Y");
		comPrefsSet.add(cp);
		crmOptinComPrefs(comPrefsSet, response);
		for(CommunicationPreferencesDTO cp2 : comPrefsSet) {
			Assert.assertEquals("Y", cp2.getSubscribe());
		}
		Assert.assertEquals("Warning : One or many market languages created with an unknown value", response.getInformationResponse().get(0).getInformation().getInformationDetails());
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
	public void checkMarketLanguageEmptyOrNullLanguage() throws InvalidParameterException {
		Set<MarketLanguageDTO> mls = new HashSet<>();
		MarketLanguageDTO market1 = new MarketLanguageDTO();
		market1.setLanguage("");
		market1.setOptIn("Y");
		market1.setMarket("EN");
		mls.add(market1);
		try {
			this.checkMarketLanguage(mls);
			Assert.fail();
		} catch (MissingParameterException mpe) {
			Assert.assertTrue(mpe.getMessage().contains("Language is a mandatory field"));
		}

		market1.setLanguage(null);
		try {
			this.checkMarketLanguage(mls);
			Assert.fail();
		} catch (MissingParameterException mpe) {
			Assert.assertTrue(mpe.getMessage().contains("Language is a mandatory field"));
		}
	}
	
	@Test
	@Ignore	// Because Optin check code have been comment in checkMarketLanguage function
	public void checkMarketLanguageEmptyOrNullOptIn() throws InvalidParameterException {
		Set<MarketLanguageDTO> mls = new HashSet<>();
		MarketLanguageDTO market1 = new MarketLanguageDTO();
		market1.setLanguage("FR");
		market1.setOptIn("");
		market1.setMarket("EN");
		mls.add(market1);
		try {
			this.checkMarketLanguage(mls);
			Assert.fail();
		} catch (MissingParameterException mpe) {
			Assert.assertTrue(mpe.getMessage().contains("Optin is a mandatory field"));
		}
		market1.setOptIn(null);
		
		try {
			this.checkMarketLanguage(mls);
			Assert.fail();
		} catch (MissingParameterException mpe) {
			Assert.assertTrue(mpe.getMessage().contains("Optin is a mandatory field"));
		}
	}
	
	@Test
	public void checkMarketLanguageEmptyOrNullMarket() throws InvalidParameterException {
		Set<MarketLanguageDTO> mls = new HashSet<>();
		MarketLanguageDTO market1 = new MarketLanguageDTO();
		market1.setLanguage("FR");
		market1.setOptIn("Y");
		market1.setMarket("");
		mls.add(market1);
		try {
			this.checkMarketLanguage(mls);
			Assert.fail();
		} catch (MissingParameterException mpe) {
			Assert.assertTrue(mpe.getMessage().contains("Market is a mandatory field"));
		}
		market1.setMarket(null);
		
		try {
			this.checkMarketLanguage(mls);
			Assert.fail();
		} catch (MissingParameterException mpe) {
			Assert.assertTrue(mpe.getMessage().contains("Market is a mandatory field"));
		}
	}

	@Test
	public void checkCommPrefNullGroupTypeTest() throws JrafDomainException {

		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();

		cp.setDomain("F");
		cp.setComType("FB_CC");
		cp.setComGroupType(null);
		cp.setSubscribe("N");

		try {
			checkCommPref(cp);
		} catch (InvalidParameterException ipe) {
			Assert.assertEquals("Invalid parameter: Invalid communication preference data", ipe.getMessage());
		}

	}

	@Test
	public void checkCommPrefNullComTypeTest() throws JrafDomainException {

		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();

		cp.setDomain("F");
		cp.setComType(null);
		cp.setComGroupType("N");
		cp.setSubscribe("N");

		try {
			checkCommPref(cp);
			Assert.fail();
		} catch (InvalidParameterException ipe) {
			Assert.assertEquals("Invalid parameter: Invalid communication preference data", ipe.getMessage());
		}

	}

	@Test
	public void checkCommPrefNotExistTest() throws JrafDomainException {

		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();

		cp.setDomain("F");
		cp.setComType("TEST");
		cp.setComGroupType("N");
		cp.setSubscribe("N");

		EasyMock.expect(referencesDS.refComPrefExistForIndividual("F", "N", "TEST")).andReturn(false);
		EasyMock.replay(referencesDS);
		try {
			checkCommPref(cp);
			Assert.fail();
		} catch (InvalidParameterException ipe) {
			Assert.assertEquals("Invalid parameter: Unknown domain, group type, or type", ipe.getMessage());
		}
	}
	
	@Test
	public void checkCommPrefSubscribeNotYNDomainNotUtest() throws JrafDomainException {

		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();

		cp.setDomain("F");
		cp.setComType("TEST");
		cp.setComGroupType("N");
		cp.setSubscribe("P");

		EasyMock.expect(referencesDS.refComPrefExistForIndividual("F", "N", "TEST")).andReturn(true);
		EasyMock.replay(referencesDS);
		try {
			checkCommPref(cp);
			Assert.fail();
		} catch (InvalidParameterException ipe) {
			Assert.assertEquals("Invalid parameter: Unknonw optIn, should be Y, N or P (for Ultimate only)", ipe.getMessage());
		}
	}

	@Test
	public void checkCommPrefSubscribeYtest() throws JrafDomainException {

		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();

		cp.setDomain("F");
		cp.setComType("TEST");
		cp.setComGroupType("N");

		// Subscribe is Y
		cp.setSubscribe("Y");

		EasyMock.expect(referencesDS.refComPrefExistForIndividual("F", "N", "TEST")).andReturn(true);
		EasyMock.replay(referencesDS);
		try {
			// No return value to test - no Error means test is okay
			checkCommPref(cp); 
		} catch (InvalidParameterException ipe) {
			Assert.fail();
		}
	}

	@Test
	public void checkCommPrefSubscribeNtest() throws JrafDomainException {

		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();

		cp.setDomain("F");
		cp.setComType("TEST");
		cp.setComGroupType("N");

		// Subscribe is N
		cp.setSubscribe("N");

		EasyMock.expect(referencesDS.refComPrefExistForIndividual("F", "N", "TEST")).andReturn(true);
		EasyMock.replay(referencesDS);
		try {
			// No return value to test - no Error means test is okay
			checkCommPref(cp); 
		} catch (InvalidParameterException ipe) {
			Assert.fail();
		}
	}

	@Test
	public void checkCommPrefSubscribeYPNDomainUtest() throws JrafDomainException {

		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();

		cp.setDomain("U");
		cp.setComType("TEST");
		cp.setComGroupType("N");

		// Subscribe is P and Domain is U
		cp.setSubscribe("P");

		EasyMock.expect(referencesDS.refComPrefExistForIndividual("U", "N", "TEST")).andReturn(true).anyTimes();
		EasyMock.replay(referencesDS);
		try {
			// No return value to test - no Error means test is okay
			checkCommPref(cp); 
		} catch (InvalidParameterException ipe) {
			Assert.fail();
		}

		// Subscribe is N
		cp.setSubscribe("N");
		try {
			// No return value to test - no Error means test is okay
			checkCommPref(cp);
		} catch (InvalidParameterException ipe) {
			Assert.fail();
		}

		// Subscribe is Y
		cp.setSubscribe("Y");
		try {
			// No return value to test - no Error means test is okay
			checkCommPref(cp);
		} catch (InvalidParameterException ipe) {
			Assert.fail();
		}
	}

	@Test
	public void checkCommPrefSubscribeNotYNPDomainUtest() throws JrafDomainException {

		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();

		cp.setDomain("U");
		cp.setComType("TEST");
		cp.setComGroupType("N");
		cp.setSubscribe("F");

		EasyMock.expect(referencesDS.refComPrefExistForIndividual("U", "N", "TEST")).andReturn(true);
		EasyMock.replay(referencesDS);
		try {
			checkCommPref(cp);
			Assert.fail();
		} catch (InvalidParameterException ipe) {
			Assert.assertEquals("Invalid parameter: Unknonw optIn, should be Y, N or P (for Ultimate only)", ipe.getMessage());
		}
	}
	
}
