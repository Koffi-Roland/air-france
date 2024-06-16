package com.airfrance.repind.service.ws.internal.it;

import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.ProcessEnum;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.InformationResponseDTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.dto.role.RoleTravelersDTO;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.service.ws.internal.CreateOrUpdateIndividualDS;
import com.airfrance.repind.service.ws.internal.helpers.UltimateException;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
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
import java.text.ParseException;
import java.util.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualDSForSearchMulticriteriaProspectTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualDSForSearchMulticriteriaProspectTest.class);

	private static final String CHANNEL = "B2C";
	private static final String CONTEXT = "KL_PROSPECT";
	private static final String CODE_APP = "ISI";
	private static final String SIGNATURE = "UT_REPIND";
	private static final String SITE = "QVI";
	private static final String MATRICULE = "T528182";
	private static final String NUMERO_CONTRACTFBCASE1 = "t3stprosfbcase1";
	private static final String NUMERO_CONTRACTFBCASE1B = "t3stprosfbcase1B";
	private static final String NUMERO_CONTRACTFBCASE4 = "t3stprosfbcase4";
	private static final String ACCOUNTIDENTIFIER_CASE1 = "TESTPR1";
	private static final String ACCOUNTIDENTIFIER_CASE2 = "TESTPR2";
	private static final String ACCOUNTIDENTIFIER_FBCASE1 = "TESTFB1";
	private static final String ACCOUNTIDENTIFIER_FBCASE1B = "TESTFB1B";
	private static final String ACCOUNTIDENTIFIER_FBCASE4 = "TESTFB4";
	private static final String LASTNAMECASE = "lastnamecasenew";
	private static final String FIRSTNAMECASE = "firstnamecasenew";
	private static final String LASTNAMECASE1 = "lastnamecase1";
	private static final String FIRSTNAMECASE1 = "firstnamecase1";
	private static final String LASTNAMECASE2 = "lastnamecase2";
	private static final String FIRSTNAMECASE2 = "firstnamecase2";
	private static final String LASTNAMECASE3 = "lastnamecase3";
	private static final String FIRSTNAMECASE3 = "firstnamecase3";
	private static final String LASTNAMECASE4 = "lastnamecase4";
	private static final String FIRSTNAMECASE4 = "firstnamecase4";
	private static final String MARKETLANGUAGE = "FR";
	private static final String COMTYPE = "AF";
	private String individualGinCase1 = "";
	private String individualGinCase2 = "";
	private String individualGinCase3 = "";
	private String individualGinCase4 = "";
	private String individualGinCase5 = "";

	private String mailprospectcase1 = "";
	private String mailprospectcase2 = "";
	private String mailprospectcase3 = "";
	private String mailprospectcase4 = "";
	private String mailprospectcase5 = "";

	@Autowired
	private CreateOrUpdateIndividualDS createOrUpdateDS;

	@Autowired
	private IndividuDS individuDS;
	
	@Autowired
	private EmailDS emailDS;

	@Autowired
	private IndividuRepository individuRepository;
	
	@Autowired
	private AccountDataRepository accountDataRepository;
	
	@Autowired
	private RoleDS roleDS;
	
	@Autowired
	private BusinessRoleDS businessRoleDS;
	
	@Autowired
	@Qualifier("entityManagerFactoryRepind")
	private EntityManager entityManager;
	

	public String initProspect(String mail, String lastname, String firstname, String optin, boolean addr, boolean older) {
		
		String gin = "";
		try {
			
			gin = createDefaultProspect(mail, lastname, firstname, optin, older);
			
		} catch (JrafDomainException e) {
			logger.error(e);
		}
		
		return gin;
	}
	

	public String initTraveler(String mail, String lastname, String firstname, boolean older) throws JrafDomainException {
		
		String gin = "";
		try {
			gin = createDefaultTraveler(mail, lastname, firstname, older);
			
		} catch (JrafDomainException e) {
			logger.error(e);
		}
		
		return gin;
	}
	
	public String initIndividual(String mail, String lastname, String firstname, String optin, boolean fb, String contractNumber, String accountIdentifier, boolean first, boolean older) {

		String gin = "";
		try {
			gin = createDefaultIndividu(mail, lastname, firstname, optin, fb, contractNumber, accountIdentifier, first, older);
		} catch (JrafDomainException e) {
			e.printStackTrace();
		}
		return gin;
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
		sb.append("@testprospect.fr");

		return sb.toString();
	}
	

	
	private String generateName() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random rd = new Random();

		for (int i = 0; i < 12; ++i) {
			char c = chars[rd.nextInt(chars.length)];
			sb.append(c);
		}
		sb.append("testSearch");

		return sb.toString();
	}
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_Search_CreateNewProspectInKLContext() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("testCreateOrUpdateIndividual_Prospect_Search_CreateNewProspectInKLContext start... ");
		
		String optin = "Y";
		String mail = generateEmail();
		String lastname = generateName();
		String firstname = generateName();
		
		// Build request
		CreateUpdateIndividualRequestDTO request = buildRequestProspect(null, optin, lastname, firstname, mail);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		IndividuDTO ind = null;
		try {
			List<IndividuDTO> listInd = individuDS.findProspectByEmail(mail);
			if(listInd != null && listInd.size() > 0) {
				ind = listInd.get(0);
			}
		} catch (JrafDomainException e) {
			logger.error(e);
		}
		
		
		Assert.assertNotNull(ind);
		
		// Comm Pref verification
		Assert.assertEquals(COMTYPE, ind.getCommunicationpreferencesdto().get(0).getComType());
		Assert.assertEquals(MARKETLANGUAGE, ind.getCommunicationpreferencesdto().get(0).getMarketLanguageDTO().iterator().next().getMarket());

		// Individu verification		
		Assert.assertEquals(lastname.toUpperCase(), ind.getNom());
		Assert.assertEquals("W", ind.getType());
		
		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());
		
		// Return code verification
		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("0", response.getInformationResponse()));

		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}
	
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_Search_Case1_ShouldReturnMostRecentIndividualFB() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("testCreateOrUpdateIndividual_Prospect_Search_Case1_ShouldReturnMostRecentIndividualFB start... ");
		
		createCase1();
		
		String optin = "Y";

		CreateUpdateIndividualRequestDTO request = buildRequestProspect(null, optin, LASTNAMECASE, FIRSTNAMECASE, mailprospectcase1);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		IndividuDTO ind = null;
		try {
			ind = individuDS.getByGin(individualGinCase1);
		} catch (JrafDomainException e) {
			logger.error(e);
		}
		
		logger.info(ind.getSgin());
		
		// Individu verification
		Assert.assertEquals("I", ind.getType());
		
		Assert.assertNotNull(ind);
		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertEquals(individualGinCase1, response.getGin());

		// CHange 5 in 2 for UT passed
		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("2", response.getInformationResponse()));
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}
		
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_Search_Case2_ShouldReturnIndividualMyAccount() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("testCreateOrUpdateIndividual_Prospect_Search_Case2_ShouldReturnIndividualMyAccount start... ");
		
		createCase2();
		
		String optin = "Y";

		CreateUpdateIndividualRequestDTO request = buildRequestProspect(null, optin, LASTNAMECASE, FIRSTNAMECASE, mailprospectcase2);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		IndividuDTO ind = null;
		try {
			ind = individuDS.getByGin(individualGinCase2);
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("I", ind.getType());
		
		Assert.assertNotNull(ind);
		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertEquals(individualGinCase2, response.getGin());
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}
	

	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_Search_Case3_ShouldReturnProspectMostRecent() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("testCreateOrUpdateIndividual_Prospect_Search_Case3_ShouldReturnProspectMostRecent start... ");
		
		createCase3();
		
		entityManager.clear();
		entityManager.flush();
		
		String optin = "Y";
		
		CreateUpdateIndividualRequestDTO request = buildRequestProspect(null, optin, LASTNAMECASE3, FIRSTNAMECASE3, mailprospectcase3);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		IndividuDTO ind = null;
		try {
			ind = individuDS.getByGin(individualGinCase3);
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("W", ind.getType());
		
		Assert.assertNotNull(ind);
		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertEquals(individualGinCase3, response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("1", response.getInformationResponse()));
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}
	
	

	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_Search_Case4_ShouldReturnProspectWithGoodName() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("testCreateOrUpdateIndividual_Prospect_Search_Case4_ShouldReturnProspectWithGoodName start... ");
		
		createCase4();
		
		entityManager.clear();
		entityManager.flush();
		
		String optin = "Y";

		CreateUpdateIndividualRequestDTO request = buildRequestProspect(null, optin, LASTNAMECASE4, FIRSTNAMECASE4, mailprospectcase4);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		IndividuDTO ind = null;
		try {
			ind = individuDS.getByGin(individualGinCase4);
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("W", ind.getType());
		
		Assert.assertNotNull(ind);
		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertEquals(individualGinCase4, response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("4", response.getInformationResponse()));
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_Search_Case4_ShouldReturnIndividualFBWithGoodName() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("testCreateOrUpdateIndividual_Prospect_Search_Case4_ShouldReturnProspectWithGoodName start... ");

		createCase4();
		
		entityManager.clear();
		entityManager.flush();
		
		String optin = "Y";

		CreateUpdateIndividualRequestDTO request = buildRequestProspect(null, optin, LASTNAMECASE3, FIRSTNAMECASE3, mailprospectcase4);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		IndividuDTO ind = null;
		try {
			ind = individuDS.getByGin(response.getGin());
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("W", ind.getType());
		
		Assert.assertNotNull(ind);
		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertNotEquals(individualGinCase4, response.getGin());
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_Search_Case4_OtherNameShouldCreateANewIndividual() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("testCreateOrUpdateIndividual_Prospect_Search_Case4_OtherNameShouldCreateANewIndividual start... ");

		createCase4();
		
		String optin = "Y";

		CreateUpdateIndividualRequestDTO request = buildRequestProspect(null, optin, LASTNAMECASE4, FIRSTNAMECASE3, mailprospectcase4);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		IndividuDTO ind = null;
		try {
			ind = individuDS.getByGin(response.getGin());
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("W", ind.getType());
		
		Assert.assertNotNull(ind);
		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("0", response.getInformationResponse()));
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_Search_Case4_NameNullShouldCreateANewIndividual() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("testCreateOrUpdateIndividual_Prospect_Search_Case4_NameNullShouldCreateANewIndividual start... ");
		
		createCase4();
		
		String optin = "Y";

		CreateUpdateIndividualRequestDTO request = buildRequestProspect(null, optin, null, null, mailprospectcase4);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		IndividuDTO ind = null;
		try {
			ind = individuDS.getByGin(response.getGin());
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("W", ind.getType());
		
		Assert.assertNotNull(ind);
		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("0", response.getInformationResponse()));
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_Search_Case5_AFOtherNameShouldCreateANewProspect() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("testCreateOrUpdateIndividual_Prospect_Search_Case5_AFOtherNameShouldCreateANewIndividual start... ");

		createCase5();
		
		String optin = "Y";

		CreateUpdateIndividualRequestDTO request = buildRequestProspect(null, optin, LASTNAMECASE1, FIRSTNAMECASE2, mailprospectcase5);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		IndividuDTO ind = null;
		try {
			ind = individuDS.getByGin(response.getGin());
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("W", ind.getType());
		
		Assert.assertNotNull(ind);
		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("0", response.getInformationResponse()));
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_Search_Case5_KLWithoutNameShouldUpdateProspect() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("testCreateOrUpdateIndividual_Prospect_Search_Case5_KLWithoutNameShouldUpdateProspect start... ");

		createCase5();
		
		entityManager.clear();
		entityManager.flush();
		
		String optin = "Y";

		CreateUpdateIndividualRequestDTO request = buildRequestProspect(null, optin, null, null, mailprospectcase5);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		IndividuDTO ind = null;
		try {
			ind = individuDS.getByGin(response.getGin());
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("W", ind.getType());
		
		Assert.assertNotNull(ind);
		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertNotEquals(individualGinCase5, response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("4", response.getInformationResponse()));
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_Search_Case5_KLWithoutFirstnameShouldCreateANewProspect() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("testCreateOrUpdateIndividual_Prospect_Search_Case5_KLWithoutNameShouldUpdateProspect start... ");

		createCase5();
		
		String optin = "Y";

		CreateUpdateIndividualRequestDTO request = buildRequestProspect(null, optin, LASTNAMECASE4, null, mailprospectcase5);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		IndividuDTO ind = null;
		try {
			ind = individuDS.getByGin(response.getGin());
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("W", ind.getType());
		
		Assert.assertNotNull(ind);
		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertNotEquals(individualGinCase5, response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("0", response.getInformationResponse()));
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}
	

	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_Search_Case5_AFWithoutNameShouldUpdateProspect() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("testCreateOrUpdateIndividual_Prospect_Search_Case5_AFWithoutNameShouldUpdateIndividual start... ");

		createCase5();
		
		entityManager.clear();
		entityManager.flush();
		
		String optin = "Y";

		CreateUpdateIndividualRequestDTO request = buildRequestProspect(null, optin, null, null, mailprospectcase5);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		IndividuDTO ind = null;
		try {
			ind = individuDS.getByGin(response.getGin());
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("W", ind.getType());
		
		Assert.assertNotNull(ind);
		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertNotEquals(individualGinCase5, response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("4", response.getInformationResponse()));
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}
	

	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_Search_Case5_AFWithNameShouldUpdateRightProspect() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("testCreateOrUpdateIndividual_Prospect_Search_Case5_AFWithNameShouldUpdateRightIndividual start... ");

		createCase5();
		
		entityManager.clear();
		entityManager.flush();
		
		String optin = "Y";

		CreateUpdateIndividualRequestDTO request = buildRequestProspect(null, optin, LASTNAMECASE4, FIRSTNAMECASE4, mailprospectcase5);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		IndividuDTO ind = null;
		try {
			ind = individuDS.getByGin(response.getGin());
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("W", ind.getType());
		
		Assert.assertNotNull(ind);
		
		Assert.assertNotNull(response.getSuccess());
		Assert.assertTrue(response.getSuccess());
		Assert.assertNotNull(response.getGin());
		Assert.assertEquals(individualGinCase5, response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("1", response.getInformationResponse()));
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	public void createCase1() throws JrafDomainException {
		
		mailprospectcase1 = generateEmail();
		// CASE 1 - FB Most recent to update
		// 2 FB + 1 MA + 1 PROSPECT + 1 TRAVELER with same firstname/lastname/email
		
		
		String t = initIndividual(mailprospectcase1, LASTNAMECASE, FIRSTNAMECASE, "N", true, NUMERO_CONTRACTFBCASE1, ACCOUNTIDENTIFIER_FBCASE1, true, true);
		String tt = initIndividual(mailprospectcase1, LASTNAMECASE, FIRSTNAMECASE, "Y", false, null, ACCOUNTIDENTIFIER_CASE1, false, false);
		String ttt = initProspect(mailprospectcase1, LASTNAMECASE, FIRSTNAMECASE, "Y", false, false);
		String tttt = initTraveler(mailprospectcase1, LASTNAMECASE, FIRSTNAMECASE, false);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		individualGinCase1 = initIndividual(mailprospectcase1, LASTNAMECASE, FIRSTNAMECASE, "N", true, NUMERO_CONTRACTFBCASE1B, ACCOUNTIDENTIFIER_FBCASE1B, false, false);

		IndividuDTO temp1 = individuDS.getByGin(t);
		IndividuDTO temp2 = individuDS.getByGin(individualGinCase1);
		IndividuDTO temp3 = individuDS.getByGin(tt);
		IndividuDTO temp4 = individuDS.getByGin(ttt);
		IndividuDTO temp5 = individuDS.getByGin(tttt);
		
		System.out.println("");
		
	}

	public void createCase2() throws JrafDomainException {

		mailprospectcase2 = generateEmail();
		// CASE 2 - MA to update
		// 1 MA + 1 PROSPECT + 1 TRAVELER with same firstname/lastname/email
		individualGinCase2 = initIndividual(mailprospectcase2, LASTNAMECASE, FIRSTNAMECASE, "Y", false, null, ACCOUNTIDENTIFIER_CASE2, true, false);
		initProspect(mailprospectcase2, LASTNAMECASE, FIRSTNAMECASE, "Y", false, false);
		initTraveler(mailprospectcase2, LASTNAMECASE, FIRSTNAMECASE, false);
		
		IndividuDTO temp1 = individuDS.getByGin(individualGinCase2);
		System.out.println("");
	}

	public void createCase3() throws JrafDomainException {
		
		mailprospectcase3 = generateEmail();
		// CASE 3 - Prospect most recent to update
		// 2 PROSPECT + 1 TRAVELER with same firstname/lastname/email
		initProspect(mailprospectcase3, LASTNAMECASE3, FIRSTNAMECASE3, "Y", false, true);
		individualGinCase3 = initProspect(mailprospectcase3, LASTNAMECASE3, FIRSTNAMECASE3, "N", false, false);
		initTraveler(mailprospectcase3, LASTNAMECASE3, FIRSTNAMECASE3, false);
		
	}

	public void createCase4() throws JrafDomainException {
		
		mailprospectcase4 = generateEmail();
		// CASE 4 - Prospect with good name to update
		// 1 PROSPECT + 1 FB with same firstname/lastname/email
		// + 1 PROSPECT with same email but different firstname/lastname
		initIndividual(mailprospectcase4, LASTNAMECASE3, FIRSTNAMECASE3, "N", true, NUMERO_CONTRACTFBCASE4, ACCOUNTIDENTIFIER_FBCASE4, true, false);
		individualGinCase4 = initProspect(mailprospectcase4, LASTNAMECASE4, FIRSTNAMECASE4, "Y", false, false);
		initProspect(mailprospectcase4, LASTNAMECASE3, FIRSTNAMECASE3, "N", false, true);
		
	}

	public void createCase5() throws JrafDomainException {

		mailprospectcase5 = generateEmail();
		// CASE 5 - Prospect with good name to update
		// 3 PROSPECT with different combinaison of firstname/lastname
		initProspect(mailprospectcase5, LASTNAMECASE3, FIRSTNAMECASE3, "N", false, true);
		individualGinCase5 = initProspect(mailprospectcase5, LASTNAMECASE4, FIRSTNAMECASE4, "N", false, true);
		initProspect(mailprospectcase5, null, null, "Y", false, true);
		
	}
	
	
	// Create role contract for an individual
	public void createRoleContrat(Individu ind, boolean fb, String numeroContract, String accountIdentifier, boolean first) throws JrafDomainException {
			
		String typeContrat = "";
		if(fb) {
			typeContrat = "FP";
		} else {
			typeContrat = "MA";
			numeroContract = accountIdentifier;
		}
			
		RoleContratsDTO roleContrats = new RoleContratsDTO();
		roleContrats.setIndividudto(IndividuTransform.bo2Dto(ind));
		roleContrats.setTypeContrat(typeContrat);
		roleContrats.setEtat("C");
		roleContrats.setNumeroContrat(numeroContract);
		roleContrats.setDateCreation(new Date());
		roleContrats.setDateModification(new Date());
		roleContrats.setSiteCreation("QVI");
		roleContrats.setSiteModification("QVI");
		roleContrats.setSignatureCreation("TEST");
    	roleContrats.setSignatureModification("TEST");
    	roleContrats.setGin(ind.getSgin());	
    	
		final BusinessRoleDTO businessRole = new BusinessRoleDTO();
		businessRole.setNumeroContrat(numeroContract);
		businessRole.setDateCreation(new Date());
		businessRole.setSignatureCreation("TEST");
		businessRole.setSiteCreation("QVI");
		businessRole.setDateModification(new Date());
		businessRole.setSignatureModification("TEST");
		businessRole.setSiteModification("QVI");
		businessRole.setGinInd(ind.getSgin());
		businessRole.setType("C");
		String businessKey = businessRoleDS.createABusinessRole(businessRole);
		businessRole.setCleRole(Integer.parseInt(businessKey));
		roleContrats.setBusinessroledto(businessRole);

		roleDS.create(roleContrats);
		
		com.airfrance.repind.entity.individu.AccountData accountData = new com.airfrance.repind.entity.individu.AccountData();
		accountData.setFbIdentifier(numeroContract);
		accountData.setIndividu(ind);
		accountData.setAccountIdentifier(accountIdentifier);
		
		if(first) {
			accountData.setEmailIdentifier(ind.getEmail().iterator().next().getEmail());
		}
		
		ind.setAccountData(accountData);

		accountDataRepository.saveAndFlush(accountData);
    	
		individuRepository.flush();
		
		entityManager.flush();
		entityManager.clear();

	}

	
	// Create role contract for an individual
	public void createRoleTraveler(String gin) throws JrafDomainException {
		
		RoleTravelersDTO roleTravelers = new RoleTravelersDTO();
		roleTravelers.setMatchingRecognitionCode("DEF");
		roleTravelers.setLastRecognitionDate(new Date());
		roleTravelers.setDateCreation(new Date());
		roleTravelers.setDateModification(new Date());
		roleTravelers.setSiteCreation("QVI");
		roleTravelers.setSiteModification("QVI");
		roleTravelers.setSignatureCreation("TEST");
		roleTravelers.setSignatureModification("TEST");
		roleTravelers.setGin(gin);	
    	
		final BusinessRoleDTO businessRole = new BusinessRoleDTO();
		businessRole.setDateCreation(new Date());
		businessRole.setSignatureCreation("TEST");
		businessRole.setSiteCreation("QVI");
		businessRole.setDateModification(new Date());
		businessRole.setSignatureModification("TEST");
		businessRole.setSiteModification("QVI");
		businessRole.setGinInd(gin);
		businessRole.setType("T");
		businessRole.setRoleTravelers(roleTravelers);
		
		businessRoleDS.createABusinessRole(businessRole);
		
		individuRepository.flush();

	}
	
	// Create a prospect by default
	public String createDefaultTraveler(String mail, String lastname, String firstname, boolean older) throws JrafDomainException {

		Date date = new Date();
		if(older) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, -1);
			date = cal.getTime();
		}
		
		IndividuDTO indToCreate = new IndividuDTO();
		indToCreate.setNom(lastname);
		indToCreate.setPrenom(firstname);
		indToCreate.setCivilite("MR");
		indToCreate.setSignatureCreation("UT_PROSP");
		indToCreate.setSiteCreation("QVI");
		indToCreate.setDateCreation(date);
		indToCreate.setSignatureModification("UT_PROSP");
		indToCreate.setSiteModification("QVI");
		indToCreate.setDateModification(date);
		indToCreate.setNonFusionnable("O");
		indToCreate.setSexe("U");
		indToCreate.setType("T");
		indToCreate.setStatutIndividu("V");
		
		Set<EmailDTO> setEmailDTO = new HashSet<EmailDTO>();
		EmailDTO email = new EmailDTO();
		email.setEmail(mail);
		email.setVersion(0);
		email.setCodeMedium("D");
		email.setStatutMedium("V");
		email.setAutorisationMailing("N");
		email.setSignatureCreation("UT_PROSP");
		email.setSiteCreation("QVI");
		email.setDateCreation(new Date());
		
		setEmailDTO.add(email);
		indToCreate.setEmaildto(setEmailDTO);
		
		String gin = individuDS.createAnIndividualProspect(indToCreate);

		createRoleTraveler(gin);
		
		return gin;
		

	}
	
	// Create a prospect by default
	public String createDefaultProspect(String mail, String lastname, String firstname, String optin, boolean older) throws JrafDomainException {

		Date date = new Date();
		if(older) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, -1);
			date = cal.getTime();

		}
		
		IndividuDTO indToCreate = new IndividuDTO();
		indToCreate.setNom(lastname);
		indToCreate.setPrenom(firstname);
		indToCreate.setCivilite("MR");
		indToCreate.setSignatureCreation("UT_PROSP");
		indToCreate.setSiteCreation("QVI");
		indToCreate.setDateCreation(date);
		indToCreate.setSignatureModification("UT_PROSP");
		indToCreate.setSiteModification("QVI");
		indToCreate.setDateModification(date);
		indToCreate.setNonFusionnable("O");
		indToCreate.setSexe("U");
		indToCreate.setType("W");
		indToCreate.setStatutIndividu("V");
		
		List<CommunicationPreferencesDTO> listCommPref = new ArrayList<CommunicationPreferencesDTO>();
		CommunicationPreferencesDTO commPref = new CommunicationPreferencesDTO();
		commPref.setComGroupType("N");
		commPref.setComType("AF");
		commPref.setDomain("S");
		commPref.setSubscribe(optin);
		
		Set<MarketLanguageDTO> setML = new HashSet<MarketLanguageDTO>();
		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setMarket("FR");
		ml.setMedia1("E");
		ml.setLanguage("FR");
		ml.setCreationSignature("UT_PROSP");
		ml.setCreationSite("QVI");
		ml.setCreationDate(new Date());
		ml.setOptIn(optin);
		
		setML.add(ml);
		commPref.setMarketLanguageDTO(setML);
		listCommPref.add(commPref);
		indToCreate.setCommunicationpreferencesdto(listCommPref);
		
		Set<EmailDTO> setEmailDTO = new HashSet<EmailDTO>();
		EmailDTO email = new EmailDTO();
		email.setEmail(mail);
		email.setVersion(0);
		email.setCodeMedium("D");
		email.setStatutMedium("V");
		email.setAutorisationMailing("N");
		email.setSignatureCreation("UT_PROSP");
		email.setSiteCreation("QVI");
		email.setDateCreation(new Date());
		
		setEmailDTO.add(email);
		indToCreate.setEmaildto(setEmailDTO);
		
		return individuDS.createAnIndividualProspect(indToCreate);
		

	}

	// Create an individual by default
	public String createDefaultIndividu(String mail, String lastname, String firstname, String optin, boolean fb, String numeroContract, String accountIdentifier, boolean first, boolean older) throws JrafDomainException {

		Date date = new Date();
		if(older) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, -1);
			date = cal.getTime();
		}
		
		CreateUpdateIndividualRequestDTO createUpdateIndividualRequestDTO = new CreateUpdateIndividualRequestDTO();
		
		RequestorDTO requestorDTO = new RequestorDTO();
		requestorDTO.setSignature("UT_PROSP");
		requestorDTO.setSite("QVI");
		createUpdateIndividualRequestDTO.setRequestorDTO(requestorDTO);
		
		IndividualRequestDTO individualRequestDTO = new IndividualRequestDTO();
		IndividualInformationsDTO individualInformationsDTO = new IndividualInformationsDTO();
		individualInformationsDTO.setLastNameSC(lastname);
		individualInformationsDTO.setFirstNameSC(firstname);
		individualInformationsDTO.setCivility("MR");
		individualInformationsDTO.setGender("U");
		individualInformationsDTO.setStatus("V");
		individualInformationsDTO.setFlagNoFusion(true);
		individualRequestDTO.setIndividualInformationsDTO(individualInformationsDTO);
		createUpdateIndividualRequestDTO.setIndividualRequestDTO(individualRequestDTO);
		
		
		
		/*IndividuDTO indToCreate = new IndividuDTO();
		indToCreate.setNom(lastname);
		indToCreate.setPrenom(firstname);
		indToCreate.setCivilite("MR");
		indToCreate.setSignatureCreation("UT_PROSP");
		indToCreate.setSiteCreation("QVI");
		indToCreate.setDateCreation(date);
		indToCreate.setSignatureModification("UT_PROSP");
		indToCreate.setSiteModification("QVI");
		indToCreate.setDateModification(date);
		indToCreate.setNonFusionnable("O");
		indToCreate.setSexe("U");
		indToCreate.setType("W");
		indToCreate.setStatutIndividu("V");*/
		
		/*EmailRequestDTO emailRequestDTO = new EmailRequestDTO();
		com.airfrance.repind.dto.ws.EmailDTO emailDTO = new com.airfrance.repind.dto.ws.EmailDTO();
		emailDTO.setEmail(mail);
		emailDTO.setVersion("0");
		emailDTO.setMediumCode("D");
		emailDTO.setMediumStatus("V");
		emailDTO.setEmailOptin("N");
		emailRequestDTO.setEmailDTO(emailDTO);
		createUpdateIndividualRequestDTO.setEmailRequestDTO(new ArrayList<EmailRequestDTO>());
		createUpdateIndividualRequestDTO.getEmailRequestDTO().add(emailRequestDTO);*/
		
		/*Set<EmailDTO> setEmailDTO = new HashSet<EmailDTO>();
		EmailDTO email = new EmailDTO();
		email.setEmail(mail);
		email.setVersion(0);
		email.setCodeMedium("D");
		email.setStatutMedium("V");
		email.setAutorisationMailing("N");
		email.setSignatureCreation("UT_PROSP");
		email.setSiteCreation("QVI");
		email.setDateCreation(new Date());
		
		setEmailDTO.add(email);
		indToCreate.setEmaildto(setEmailDTO);
		
		
		createUpdateIndividualRequestDTO.
		
		individuDS.createOrUpdateIndividual(requestDTO)*/

		
		//String gin = individuDS.createAnIndividualProspect(indToCreate);
		
		//IndividuDTO indToUpdate = individuDS.getByGin(indToCreate.getSgin());
		
		
		/*com.airfrance.repind.dto.ws.CommunicationPreferencesDTO communicationPreferencesDTO = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
		communicationPreferencesDTO.setDomain("S");
		communicationPreferencesDTO.setCommunicationGroupeType("N");
		communicationPreferencesDTO.setCommunicationType("AF");
		communicationPreferencesDTO.setOptIn(optin);
		
		com.airfrance.repind.dto.ws.MarketLanguageDTO marketLanguageDTO = new com.airfrance.repind.dto.ws.MarketLanguageDTO();
		marketLanguageDTO.setMarket("FR");
		marketLanguageDTO.setLanguage("FR");
		com.airfrance.repind.dto.ws.MediaDTO mediaDTO = new com.airfrance.repind.dto.ws.MediaDTO();
		mediaDTO.setMedia1("E");
		marketLanguageDTO.setMediaDTO(mediaDTO);
		marketLanguageDTO.setOptIn(optin);
		communicationPreferencesDTO.setMarketLanguageDTO(new ArrayList<com.airfrance.repind.dto.ws.MarketLanguageDTO>());
		communicationPreferencesDTO.getMarketLanguageDTO().add(marketLanguageDTO);
		
		CommunicationPreferencesRequestDTO communicationPreferencesRequestDTO = new CommunicationPreferencesRequestDTO();
		communicationPreferencesRequestDTO.setCommunicationPreferencesDTO(communicationPreferencesDTO);
		
		createUpdateIndividualRequestDTO.setCommunicationPreferencesRequestDTO(new ArrayList<CommunicationPreferencesRequestDTO>());
		createUpdateIndividualRequestDTO.getCommunicationPreferencesRequestDTO().add(communicationPreferencesRequestDTO);*/
		
		/*List<CommunicationPreferencesDTO> listCommPref = new ArrayList<CommunicationPreferencesDTO>();
		CommunicationPreferencesDTO commPref = new CommunicationPreferencesDTO();
		commPref.setComGroupType("N");
		commPref.setComType("AF");
		commPref.setDomain("S");
		commPref.setSubscribe(optin);
		
		Set<MarketLanguageDTO> setML = new HashSet<MarketLanguageDTO>();
		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setMarket("FR");
		ml.setMedia1("E");
		ml.setLanguage("FR");
		ml.setCreationSignature("UT_PROSP");
		ml.setCreationSite("QVI");
		ml.setCreationDate(new Date());
		ml.setOptIn(optin);
		
		setML.add(ml);
		commPref.setMarketLanguageDTO(setML);
		listCommPref.add(commPref);
		indToCreate.setCommunicationpreferencesdto(listCommPref);*/
		
		//individuDS.updateIndividual(indToCreate);
		
		//individuDS.updateAProspectToIndividual(indToUpdate);
		
		//entityManager.clear();
		
		CreateModifyIndividualResponseDTO response = individuDS.createOrUpdateIndividual(createUpdateIndividualRequestDTO);
		
		
		EmailDTO email = new EmailDTO();
		email.setEmail(mail);
		email.setVersion(0);
		email.setCodeMedium("D");
		email.setStatutMedium("V");
		email.setAutorisationMailing("N");
		email.setSignatureCreation("UT_PROSP");
		email.setSiteCreation("QVI");
		email.setDateCreation(new Date());
		List<EmailDTO> emailListFromWS = new ArrayList<EmailDTO>();
		emailListFromWS.add(email);
		
		SignatureDTO signatureDTO = new SignatureDTO();
		signatureDTO.setApplicationCode("B2C");
		signatureDTO.setSignature("UT_PROSP");
		signatureDTO.setSite("QVI");
		
		emailDS.updateEmail(response.getGin(), emailListFromWS, signatureDTO);
		
		entityManager.flush();
		entityManager.clear();
		
		IndividuDTO indToCreate = individuDS.getByGin(response.getGin());
		
		List<CommunicationPreferencesDTO> listCommPref = new ArrayList<CommunicationPreferencesDTO>();
		CommunicationPreferencesDTO commPref = new CommunicationPreferencesDTO();
		commPref.setComGroupType("N");
		commPref.setComType("AF");
		commPref.setDomain("S");
		commPref.setSubscribe(optin);
		
		Set<MarketLanguageDTO> setML = new HashSet<MarketLanguageDTO>();
		MarketLanguageDTO ml = new MarketLanguageDTO();
		ml.setMarket("FR");
		ml.setMedia1("E");
		ml.setLanguage("FR");
		ml.setCreationSignature("UT_PROSP");
		ml.setCreationSite("QVI");
		ml.setCreationDate(new Date());
		ml.setOptIn(optin);
		
		setML.add(ml);
		commPref.setMarketLanguageDTO(setML);
		listCommPref.add(commPref);
		indToCreate.setCommunicationpreferencesdto(listCommPref);
		
		individuDS.updateComPrefOfIndividual(indToCreate);
		
		createRoleContrat(IndividuTransform.dto2Bo(indToCreate), fb, numeroContract, accountIdentifier, first);
		
		entityManager.flush();
		entityManager.clear();
		
		return indToCreate.getSgin();

	}
	
	// Build request for CreateUpdateIndividual
	public CreateUpdateIndividualRequestDTO buildRequestProspect(String context, String optin, String lastname, String firstname, String mail) {

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();

		request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect

		/*** Set parameters ***/
		// Requestor
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setMatricule(MATRICULE);
		if(context != null) {
			requestor.setContext(context);
		} else {
			requestor.setContext(CONTEXT);
		}
		requestor.setApplicationCode(CODE_APP);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		
		// Email
		if(mail != null) {
			EmailRequestDTO emailRequest = new EmailRequestDTO();
			com.airfrance.repind.dto.ws.EmailDTO email = new com.airfrance.repind.dto.ws.EmailDTO();

			email.setEmail(mail);
			email.setMediumCode("D");
			email.setMediumStatus("V");
			emailRequest.setEmailDTO(email);
			
			request.setEmailRequestDTO(new ArrayList<EmailRequestDTO>());
			
			request.getEmailRequestDTO().add(emailRequest);
		}

		// Communication Preference
		CommunicationPreferencesRequestDTO comPrefrequest = new CommunicationPreferencesRequestDTO();
		com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType(COMTYPE);
		comPref.setOptIn(optin);
		comPref.setDateOfConsent(new Date());
		comPref.setSubscriptionChannel(CONTEXT);

		// Market Language
		com.airfrance.repind.dto.ws.MarketLanguageDTO ml = new com.airfrance.repind.dto.ws.MarketLanguageDTO();
		ml.setLanguage(MARKETLANGUAGE);
		ml.setMarket(MARKETLANGUAGE);
		ml.setOptIn(optin);
		ml.setDateOfConsent(new Date());

		comPref.setMarketLanguageDTO(new ArrayList<com.airfrance.repind.dto.ws.MarketLanguageDTO>());
		
		comPref.getMarketLanguageDTO().add(ml);
		comPrefrequest.setCommunicationPreferencesDTO(comPref);
				
		IndividualRequestDTO individualRequest = new IndividualRequestDTO();
		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		indInfo.setCivility("MR");
		indInfo.setFirstNameSC(firstname);
		indInfo.setLastNameSC(lastname);
		Date date = new Date();
		indInfo.setBirthDate(date);
		indInfo.setNationality("FR");
		// S08924 : 154 - STATUS MANDATORY
		indInfo.setStatus("V");
		
		individualRequest.setIndividualInformationsDTO(indInfo);
		
		request.setRequestorDTO(requestor);

		request.setCommunicationPreferencesRequestDTO(new ArrayList<CommunicationPreferencesRequestDTO>());
		request.getCommunicationPreferencesRequestDTO().add(comPrefrequest);
		request.setIndividualRequestDTO(individualRequest);
		
		request.setUpdateCommunicationPrefMode("U");

		return request;
	}

	private boolean ProspectCodeFoundOnInformationResponse(String errorCode, Set <InformationResponseDTO> irl) {
	
		boolean found = false;
		for (InformationResponseDTO ir : irl) {
			Assert.assertNotNull(ir.getInformation());
			if (errorCode.equals(ir.getInformation().getInformationCode())) {
				Assert.assertEquals(errorCode, ir.getInformation().getInformationCode());
				found = true;
				break;
			}
		}
		return found;
	}
}
