package com.airfrance.repind.util.ut;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.exception.MaximumSubscriptionsException;
import com.airfrance.repind.util.CommunicationPreferencesManagment;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CommunicationPreferencesManagmentTest {
	
	@Before
	public void setUp() throws IOException {
	 
	}
	
	@After
	public void tearDown() {
	}
	
	/**
	 * TEST si une commpref a plus de 10 marcher/langue
	 * alors on lève une exception
	 * @throws JrafDomainException
	 */
	@Test(expected=MaximumSubscriptionsException.class)
	public void test1checkNumberMarketLanguage() throws  JrafDomainException {
		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
		Set<MarketLanguageDTO> pMarketLanguageDTO = new HashSet<MarketLanguageDTO>();
		for(int i=0;i<11;i++){
			MarketLanguageDTO ml = new MarketLanguageDTO();
			pMarketLanguageDTO.add(ml);
		}
		cp.setMarketLanguageDTO(pMarketLanguageDTO);
		CommunicationPreferencesManagment.checkNumberMarketLanguage(cp);
	}
	
	/**
	 * Test si l'optin global correspond bien aux marché langues associé
	 * @throws JrafDomainException
	 */
	@Test
	public void test2checkComPrefGlobalOptTRUE() throws  JrafDomainException {
		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
		cp.setSubscribe("Y");
		Set<MarketLanguageDTO> pMarketLanguageDTO = new HashSet<MarketLanguageDTO>();
		for(int i=0;i<10;i++){
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setOptIn("Y");
			pMarketLanguageDTO.add(ml);
		}
		cp.setMarketLanguageDTO(pMarketLanguageDTO);
		Assert.assertTrue(CommunicationPreferencesManagment.isValidCommPrefOptin(cp));
	}
	
	/**
	 * Test si l'optin global correspond bien aux marché langues associé
	 * @throws JrafDomainException
	 */
	@Test
	public void test2checkComPrefGlobalOptFALSE() throws  JrafDomainException {
		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
		cp.setSubscribe("Y");
		Set<MarketLanguageDTO> pMarketLanguageDTO = new HashSet<MarketLanguageDTO>();
		for(int i=0;i<10;i++){
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setOptIn("N");
			pMarketLanguageDTO.add(ml);
		}
		cp.setMarketLanguageDTO(pMarketLanguageDTO);
		Assert.assertFalse(CommunicationPreferencesManagment.isValidCommPrefOptin(cp));
	}
	
	/**
	 * Check if an exception is raised with a YES global optin and inconsistent ml
	 */
	@Test(expected=JrafDomainException.class)
	public void test3editComPrefGlobalOptN() throws  JrafDomainException {
		
		// global optin = Y
		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
		cp.setSubscribe("Y");
		
		// market lang option = N
		Set<MarketLanguageDTO> pMarketLanguageDTO = new HashSet<MarketLanguageDTO>();
		for(int i=0;i<10;i++){
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setOptIn("N");
			pMarketLanguageDTO.add(ml);
		}
		
		cp.setMarketLanguageDTO(pMarketLanguageDTO);
		
		// validate
		CommunicationPreferencesManagment.validateGlobalOptin(cp);
		
	}

	/**
	 * Check if no exception is raised with a YES global optin and inconsistent ml
	 */
	@Test
	public void test3editComPrefGlobalOptN_Alert() throws  JrafDomainException {
		
		// global optin = Y
		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
		cp.setSubscribe("Y");			// Optin Y
		cp.setDomain("S");				// PROMO ALERT
		cp.setComGroupType("P");
		cp.setComType("AF");
		
		// market lang option = N
		Set<MarketLanguageDTO> pMarketLanguageDTO = new HashSet<MarketLanguageDTO>();
		for(int i=0;i<10;i++){
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setOptIn("N");
			pMarketLanguageDTO.add(ml);
		}
		
		cp.setMarketLanguageDTO(pMarketLanguageDTO);
		
		// validate
		CommunicationPreferencesManagment.validateGlobalOptin(cp);
	}

	/**
	 * Check if no exception is raised with a NO global optin and inconsistent ml
	 */
	@Test
	public void test3editComPrefGlobalOptY_Alert() throws  JrafDomainException {
		
		// global optin = N
		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
		cp.setSubscribe("N");			// Optin N
		cp.setDomain("S");				// PROMO ALERT
		cp.setComGroupType("P");
		cp.setComType("AF");
		
		// market lang option = Y
		Set<MarketLanguageDTO> pMarketLanguageDTO = new HashSet<MarketLanguageDTO>();
		for(int i=0;i<10;i++){
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setOptIn("Y");
			pMarketLanguageDTO.add(ml);
		}
		
		cp.setMarketLanguageDTO(pMarketLanguageDTO);
		
		// validate
		CommunicationPreferencesManagment.validateGlobalOptin(cp);
	}
	
	/**
	 * Check if no exception is raised with a NO global optin and NO inconsistent ml
	 */
	@Test
	public void test3editComPrefGlobalOptN_Alert_WithCons() throws  JrafDomainException {
		
		// global optin = N
		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
		cp.setSubscribe("N");			// Optin N
		cp.setDomain("S");				// PROMO ALERT
		cp.setComGroupType("P");
		cp.setComType("AF");
		
		// market lang option = N
		Set<MarketLanguageDTO> pMarketLanguageDTO = new HashSet<MarketLanguageDTO>();
		for(int i=0;i<10;i++){
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setOptIn("N");
			pMarketLanguageDTO.add(ml);
		}
		
		cp.setMarketLanguageDTO(pMarketLanguageDTO);
		
		// validate
		CommunicationPreferencesManagment.validateGlobalOptin(cp);
	}

	/**
	 * Check if no exception is raised with a YES global optin and NO inconsistent ml
	 */
	@Test
	public void test3editComPrefGlobalOptY_Alert_WithCons() throws  JrafDomainException {
		
		// global optin = Y
		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
		cp.setSubscribe("Y");			// Optin Y
		cp.setDomain("S");				// PROMO ALERT
		cp.setComGroupType("P");
		cp.setComType("AF");
		
		// market lang option = Y
		Set<MarketLanguageDTO> pMarketLanguageDTO = new HashSet<MarketLanguageDTO>();
		for(int i=0;i<10;i++){
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setOptIn("Y");
			pMarketLanguageDTO.add(ml);
		}
		
		cp.setMarketLanguageDTO(pMarketLanguageDTO);
		
		// validate
		CommunicationPreferencesManagment.validateGlobalOptin(cp);
	}
	
	/**
	 * Check if market lang optin are updated from YES to NO with a NO global optin
	 */
	@Test
	public void test3editComPrefGlobalOptY() throws  JrafDomainException {
		
		// global optin = N
		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
		cp.setSubscribe("N");
		
		// all market lang optin = Y
		Set<MarketLanguageDTO> pMarketLanguageDTO = new HashSet<MarketLanguageDTO>();
		for(int i=0;i<10;i++){
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setOptIn("Y");
			pMarketLanguageDTO.add(ml);
		}
		
		cp.setMarketLanguageDTO(pMarketLanguageDTO);
		
		// validate
		CommunicationPreferencesManagment.validateGlobalOptin(cp);
		
		// check global optin
		Assert.assertEquals("N",cp.getSubscribe());
		
		// check market languages optin
		for(MarketLanguageDTO ml : cp.getMarketLanguageDTO()) {
			Assert.assertEquals("N",ml.getOptIn());
		}
	}
	
	/**
	 * global optin = Y
	 * 1 ML = Y
	 * 
	 * global optin = Y
	 * 1 ML = N (has to raise an exception)
	 * 
	 * @throws JrafDomainException
	 */
	@Test(expected=JrafDomainException.class)
	public void test4updateCommunicationPreferences() throws  JrafDomainException {
		
		Set<CommunicationPreferencesDTO> cpExist = new HashSet<CommunicationPreferencesDTO>();
		CommunicationPreferencesDTO cp1 = new CommunicationPreferencesDTO();
		cp1.setDomain("S");
		cp1.setComGroupType("N");
		cp1.setComType("AF");
		cp1.setSubscribe("Y");
		
		Set<MarketLanguageDTO> pMarketLanguageDTO = new HashSet<MarketLanguageDTO>();
		for(int i=0;i<1;i++){
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setMarket("FR");
			ml.setLanguage("FR");
			ml.setOptIn("Y");
			pMarketLanguageDTO.add(ml);
		}
		
		cp1.setMarketLanguageDTO(pMarketLanguageDTO);
		cpExist.add(cp1);
		
		Set<CommunicationPreferencesDTO> cpRequest = new HashSet<CommunicationPreferencesDTO>();
		CommunicationPreferencesDTO cp2 = new CommunicationPreferencesDTO();
		cp2.setSubscribe("Y");
		cp2.setDomain("S");
		cp2.setComGroupType("N");
		cp2.setComType("AF");
		Set<MarketLanguageDTO> pMarketLanguageDTO2 = new HashSet<MarketLanguageDTO>();
		for(int i=0;i<1;i++){
			MarketLanguageDTO ml = new MarketLanguageDTO();
			ml.setMarket("FR");
			ml.setLanguage("FR");
			ml.setOptIn("N");
			pMarketLanguageDTO2.add(ml);
		}
		cp2.setMarketLanguageDTO(pMarketLanguageDTO2);
		cpRequest.add(cp2);
		
		CommunicationPreferencesManagment.updateProspectCommunicationPreferences(cpExist,cpRequest, true);

	}

	@Test
	public void testValidateGlobalOptin1() throws JrafDomainException {
		CommunicationPreferencesDTO cp = createSample("N", "N", "N");
		CommunicationPreferencesManagment.validateGlobalOptin(cp);
		checkOptin(cp,"N","N","N");
	}
	
	@Test
	public void testValidateGlobalOptin2() throws JrafDomainException {
		CommunicationPreferencesDTO cp = createSample("N", "Y", "N");
		CommunicationPreferencesManagment.validateGlobalOptin(cp);
		checkOptin(cp,"N","N","N");
	}
	
	@Test
	public void testValidateGlobalOptin3() throws JrafDomainException {
		CommunicationPreferencesDTO cp = createSample("N", "N", "Y");
		CommunicationPreferencesManagment.validateGlobalOptin(cp);
		checkOptin(cp,"N","N","N");
	}
		
	@Test
	public void testValidateGlobalOptin4() throws JrafDomainException {
		CommunicationPreferencesDTO cp = createSample("N", "Y", "Y");
		CommunicationPreferencesManagment.validateGlobalOptin(cp);
		checkOptin(cp,"N","N","N");
	}
	
	
	@Test(expected=JrafDomainException.class)
	public void testValidateGlobalOptin5() throws JrafDomainException {
		CommunicationPreferencesDTO cp = createSample("Y", "N", "N");
		CommunicationPreferencesManagment.validateGlobalOptin(cp);
	}
		
	@Test
	public void testValidateGlobalOptin6() throws JrafDomainException {
		CommunicationPreferencesDTO cp = createSample("Y", "Y", "N");
		CommunicationPreferencesManagment.validateGlobalOptin(cp);
		checkOptin(cp,"Y","Y","N");
	}
	
	@Test
	public void testValidateGlobalOptin7() throws JrafDomainException {
		CommunicationPreferencesDTO cp = createSample("Y", "N", "Y");
		CommunicationPreferencesManagment.validateGlobalOptin(cp);
		checkOptin(cp,"Y","N","Y");
	}
	
	@Test
	public void testValidateGlobalOptin8() throws JrafDomainException {
		CommunicationPreferencesDTO cp = createSample("Y", "Y", "Y");
		CommunicationPreferencesManagment.validateGlobalOptin(cp);
		checkOptin(cp,"Y","Y","Y");
	}
	
	private void checkOptin(CommunicationPreferencesDTO cp, String globalOptin, String optin1, String optin2) {

		Assert.assertEquals(globalOptin, cp.getSubscribe());
		
		for(MarketLanguageDTO ml : cp.getMarketLanguageDTO()) {
			if("FR".equals(ml.getMarket())) {
				Assert.assertEquals(optin1, ml.getOptIn());
			}
			if("UK".equals(ml.getMarket())) {
				Assert.assertEquals(optin2, ml.getOptIn());
			}
		}
	}
	
	private CommunicationPreferencesDTO createSample(String globalOptin, String optin1, String optin2) {
		
		CommunicationPreferencesDTO cp = createCommPref("S","N","AF",globalOptin);
		Set<MarketLanguageDTO> mlSet = new HashSet<MarketLanguageDTO>();
		mlSet.add(createMarketLang("FR","FR",optin1));
		mlSet.add(createMarketLang("UK","EN",optin2));
		cp.setMarketLanguageDTO(mlSet);
		
		return cp;
	}
	
	private CommunicationPreferencesDTO createCommPref(String domain, String groupType, String type, String optin) {
		CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
		cp.setDomain(domain);
		cp.setComGroupType(groupType);
		cp.setComType(type);
		cp.setSubscribe(optin);
		return cp;
	}
	
	private MarketLanguageDTO createMarketLang(String market, String language, String optin) {
		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setMarket(market);
		ml.setLanguage(language);
		ml.setOptIn(optin);
		return ml;
	}
}
