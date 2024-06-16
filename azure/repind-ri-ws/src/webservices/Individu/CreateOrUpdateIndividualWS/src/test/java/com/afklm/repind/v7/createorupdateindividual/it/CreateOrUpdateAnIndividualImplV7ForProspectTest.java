package com.afklm.repind.v7.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v7.createorupdateindividualws.CreateOrUpdateIndividualImplV7;
import com.afklm.soa.stubs.w000442.v7.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v7.request.*;
import com.afklm.soa.stubs.w000442.v7.response.InformationResponse;
import com.afklm.soa.stubs.w000442.v7.siccommonenum.LanguageCodesEnum;
import com.afklm.soa.stubs.w000442.v7.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.*;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.ProcessEnum;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.IndividuTransform;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.dto.preference.PreferenceDataDTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.service.adresse.internal.PostalAddressDS;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplV7ForProspectTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV7ForProspectTest.class);

	private static final String CHANNEL = "B2C";
	private static final String CONTEXT = "B2C_HOME_PAGE";
	private static final String CODE_APP = "ISI";
	private static final String SIGNATURE = "UT_REPIND";
	private static final String SITE = "QVI";
	private static final String MATRICULE = "T528182";
	private static final String NAMEIND = "Name UT_REPIND";
	private static final String TOKEN = "WSSiC2";
	
	private static final String preferredAirportTitle = "PA";
	
	private static final String COUNTRY = "FR";
	private static final String CITY = "NICE";
	private static final String ZIPCODE = "06600";
	private static final String COUNTRYCODE = "33";
	private static final String TERMINALTYPE = "M";
	private static final String MAILUPDATEPROSPECT = "testmailupdateprospectV7@bidon.fr";
	private static final String MAILUPDATEPROSPECTOPTIN = "testmailupdateprospectoptinV7@bidon.fr";
	private static final String MAILUPDATEPROSPECTOPTOUT = "testmailupdateprospectoptoutV7@bidon.fr";
	private static final String MAILUPDATEFBOPTIN = "testmailupdatefboptinV7@bidon.fr";
	private static final String MAILUPDATEFBOPTOUT = "testmailupdatefboptoutV7@bidon.fr";
	private static final String MAILUPDATEMAOPTIN = "testmailupdatemaoptinV7@bidon.fr";
	private static final String MAILUPDATEMAOPTOUT = "testmailupdatemaoptoutV7@bidon.fr";
	private static final String NUMERO_CONTRACTFBOPTIN = "t3stprosfboptin";
	private static final String NUMERO_CONTRACTFBOPTOUT = "t3stprosfboptout";
	private static final String ACCOUNTIDENTIFIER_OPTOUT = "TESTPROO";
	private static final String ACCOUNTIDENTIFIER_OPTIN = "TESTPROI";
	private static final String ACCOUNTIDENTIFIER_FBOPTIN = "TESTFBI";
	private static final String ACCOUNTIDENTIFIER_FBOPTOUT = "TESTFBO";
	private static final String NOANDSTREETBEFOREUPDATE = "3 rue de france";
	private static final String NOANDSTREETAFTERUPDATE = "8 rue de france";
	private static final String PREFERENCESBEFOREUPDATE = "NCE";
	private static final String PREFERENCESAFTERUPDATE = "ORY";
	private static final String MARKETLANGUAGE = "FR";
	private static final String COMTYPE = "AF";
	private String prospectGin = "";
	private String individualGin = "";
	
	/** the Entity Manager*/
	@PersistenceContext(unitName="entityManagerFactoryRepind")
	private EntityManager entityManager;

	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v7Bean")
	private CreateOrUpdateIndividualImplV7 createOrUpdateIndividualImplV7;

	@Autowired
	private IndividuDS individuDS;
	
	@Autowired
	private PreferenceDS preferenceDS;
	
	@Autowired
	private PostalAddressDS postalAddressDS;
	
	@Autowired
	private TelecomDS telecomDS;
	
	@Autowired
	private AccountDataRepository accountDataRepository;
	
	@Autowired
	private RoleDS roleDS;
	
	@Autowired
	private BusinessRoleDS businessRoleDS;
	
	@Before
	public void setUp() throws JrafDomainException {

		initProspects();
		initIndividuals();
		
	}
	
	public void initProspects() {
		
		prospectGin = initProspect(MAILUPDATEPROSPECT, "N", true);
		initProspect(MAILUPDATEPROSPECTOPTIN, "Y", false);
		initProspect(MAILUPDATEPROSPECTOPTOUT, "N", false);
		
	}
	
	public void initIndividuals() {

		individualGin = initIndividual(MAILUPDATEFBOPTOUT, "N", true, NUMERO_CONTRACTFBOPTOUT, ACCOUNTIDENTIFIER_FBOPTOUT);
		initIndividual(MAILUPDATEFBOPTIN, "Y", true, NUMERO_CONTRACTFBOPTIN, ACCOUNTIDENTIFIER_FBOPTIN);
		initIndividual(MAILUPDATEMAOPTIN, "Y", false, null, ACCOUNTIDENTIFIER_OPTIN);
		initIndividual(MAILUPDATEMAOPTOUT, "N", false, null, ACCOUNTIDENTIFIER_OPTOUT);
		
	}
	

	public String initProspect(String mail, String optin, boolean addr) {
		
		String gin = "";
		IndividuDTO prospect = null;
		try {
			List<IndividuDTO> listProspect = individuDS.findProspectByEmail(mail);
			if(listProspect != null && listProspect.size() > 0) {
				prospect = listProspect.get(0);
			}
			
			if(prospect == null) {
				gin = createDefaultProspect(mail, optin);
			}  else {
				gin = prospect.getSgin();
				if(addr == true) {
					if(prospect.getPostaladdressdto() != null && prospect.getPostaladdressdto().size() > 0) {
						if(!NOANDSTREETBEFOREUPDATE.equals(prospect.getPostaladdressdto().get(0).getSno_et_rue())) {
							IndividuDTO indToUpdate = prospect;
							indToUpdate.getPostaladdressdto().get(0).setSno_et_rue(NOANDSTREETBEFOREUPDATE);
							individuDS.updateAnIndividualProspect(indToUpdate, prospect);
						}
					}
				}
			}
			
		} catch (JrafDomainException | BusinessErrorBlocBusinessException e) {
			logger.error(e);
		}
		
		return gin;
	}
	
	@Transactional
	public String initIndividual(String mail, String optin, boolean fb, String contractNumber, String accountIdentifier) {

		String gin = "";
		Individu checkIfExists = null;
		try {
			checkIfExists = individuDS.findByEmail(mail);
			
			if(checkIfExists == null) {
				gin = createDefaultIndividu(mail, optin, fb, contractNumber, accountIdentifier);

			} else if (checkIfExists.getRolecontrats() == null || checkIfExists.getRolecontrats().isEmpty() || checkIfExists.getAccountData() == null) {
				createRoleContrat(checkIfExists, fb, contractNumber, accountIdentifier);
				gin = checkIfExists.getSgin();
			} else {
				gin = checkIfExists.getSgin();
			}
			
		} catch (JrafDomainException | BusinessErrorBlocBusinessException e) {
			logger.error(e);
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
		sb.append("@bidon.fr");

		return sb.toString();
	}

	private String generatePhone() {

		return "06" + RandomStringUtils.randomNumeric(8);	
	}
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_CreateNewProspectSimple() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("testCreateOrUpdateIndividual_Prospect_CreateNewProspectSimple start... ");
		
		String optin = "Y";
		String mail = generateEmail();
		
		// Build request
		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, mail);

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

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
		Assert.assertEquals(NAMEIND.toUpperCase(), ind.getNom());
		Assert.assertEquals("W", ind.getType());
		
		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		
		// Return code verification
		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("0", response.getInformationResponse()));

		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}
	

	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_CreateNewProspectWithAddress() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("testCreateOrUpdateIndividual_Prospect_CreateNewProspectWithAddress start... ");

		String optin = "Y";
		String mail = generateEmail();
		
		// Build request
		CreateUpdateIndividualRequest request = buildRequestProspect(NOANDSTREETAFTERUPDATE, null, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, mail);

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

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

		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		
		// AddressPostal verification
		Assert.assertEquals(NOANDSTREETAFTERUPDATE.toUpperCase(), ind.getPostaladdressdto().get(0).getSno_et_rue().toUpperCase());
		Assert.assertEquals(COUNTRY, ind.getPostaladdressdto().get(0).getScode_pays());
		Assert.assertEquals(ZIPCODE, ind.getPostaladdressdto().get(0).getScode_postal());
		Assert.assertEquals(CITY, ind.getPostaladdressdto().get(0).getSville());

		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_CreateNewProspectWithTelecom() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("testCreateOrUpdateIndividual_Prospect_CreateNewProspectWithTelecom start... ");

		String numero = generatePhone();
		String optin = "Y";
		String mail = generateEmail();
		
		// Build request
		CreateUpdateIndividualRequest request = buildRequestProspect(null, numero, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, mail);

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

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

		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		
		// Telecom verification
		List<TelecomsDTO> listTelecomsDTO = telecomDS.findLatest(ind.getSgin());
		Assert.assertEquals(numero, listTelecomsDTO.get(0).getSnumero());
		Assert.assertEquals(TERMINALTYPE, listTelecomsDTO.get(0).getSterminal());
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}

	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_CreateNewProspectWithPreferences() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("testCreateOrUpdateIndividual_Prospect_CreateNewProspectWithPreferences start... ");

		String preferredAirport = "NCE";
		String optin = "Y";
		String mail = generateEmail();
		
		// Build request
		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, preferredAirport, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, mail);

		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

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

		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

		// Preference verification
		Assert.assertEquals(1, ind.getPreferenceDTO().iterator().next().getPreferenceDataDTO().size());
		for (PreferenceDataDTO prefData : ind.getPreferenceDTO().iterator().next().getPreferenceDataDTO()) {
			if(prefData.getKey().equals(preferredAirportTitle)) {
				Assert.assertEquals(preferredAirport, prefData.getValue());
// REPIND-768 : We do not need anymore to identify origin of the preference
//			} else if(prefData.getKey().equals("type")) {
//				Assert.assertEquals("WWP", prefData.getValue());
			}
		}
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_UpdateProspectAdresse() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("testCreateOrUpdateIndividual_Prospect_UpdateProspectAdresse start... ");
		
		String optin = "Y";

		CreateUpdateIndividualRequest request = buildRequestProspect(NOANDSTREETAFTERUPDATE, null, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, MAILUPDATEPROSPECT);
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		IndividuDTO ind = null;
		try {
			List<IndividuDTO> listInd = individuDS.findProspectByEmail(MAILUPDATEPROSPECT);
			if(listInd != null && listInd.size() > 0) {
				ind = listInd.get(0);
			}
		} catch (JrafDomainException e) {
			logger.error(e);
		}
		
		Assert.assertNotNull(ind);
		
		// AddressPostal verification
		List<PostalAddressDTO> postAddr = postalAddressDS.findPostalAddress(ind.getSgin());
		
		Assert.assertEquals(NOANDSTREETAFTERUPDATE.toUpperCase(), postAddr.get(0).getSno_et_rue().toUpperCase());
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_UpdateProspectTelecom() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("testCreateOrUpdateIndividual_Prospect_UpdateProspectTelecom start... ");
		String optin = "Y";
		String numero = generatePhone();

		CreateUpdateIndividualRequest request = buildRequestProspect(null, numero, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, MAILUPDATEPROSPECT);
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		IndividuDTO ind = null;
		try {
			List<IndividuDTO> listInd = individuDS.findProspectByEmail(MAILUPDATEPROSPECT);
			if(listInd != null && listInd.size() > 0) {
				ind = listInd.get(0);
			}
		} catch (JrafDomainException e) {
			logger.error(e);
		}
		
		Assert.assertNotNull(ind);
		
		// Telecom verification
		List<TelecomsDTO> listTelecomsDTO = telecomDS.findLatest(ind.getSgin());
		Assert.assertEquals(numero, listTelecomsDTO.get(0).getSnumero());
		Assert.assertEquals(TERMINALTYPE, listTelecomsDTO.get(0).getSterminal());
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
	}
	
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
	public void testCreateOrUpdateIndividual_Prospect_UpdateExistingIndividualFbOptoutToOptin() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");
		
		String optin = "Y";

		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, MAILUPDATEFBOPTOUT);
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
		
		IndividuDTO ind = null;
		try {
			ind = IndividuTransform.bo2Dto(individuDS.findByEmail(MAILUPDATEFBOPTOUT));
		} catch (JrafDomainException e) {
			logger.error(e);
		}		

		// Individu verification
		Assert.assertEquals("I", ind.getType());
		
		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("2", response.getInformationResponse()));
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
		
	}
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
	public void testCreateOrUpdateIndividual_Prospect_UpdateExistingIndividualFbOptinToOptin() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");
		
		String optin = "Y";

		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, MAILUPDATEFBOPTIN);
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
		
		IndividuDTO ind = null;
		try {
			ind = IndividuTransform.bo2Dto(individuDS.findByEmail(MAILUPDATEFBOPTIN));
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("I", ind.getType());
		
		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("5", response.getInformationResponse()));
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
		
	}
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
	public void testCreateOrUpdateIndividual_Prospect_UpdateExistingIndividualFbOptinToOptout() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");
		
		String optin = "N";

		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, MAILUPDATEFBOPTIN);
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
		
		IndividuDTO ind = null;
		try {
			ind = IndividuTransform.bo2Dto(individuDS.findByEmail(MAILUPDATEFBOPTIN));
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("I", ind.getType());
		
		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("2", response.getInformationResponse()));
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
		
	}
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
	public void testCreateOrUpdateIndividual_Prospect_UpdateExistingIndividualFbOptoutToOptout() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");
		
		String optin = "N";
		
		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, MAILUPDATEFBOPTOUT);
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
		
		IndividuDTO ind = null;
		try {
			ind = IndividuTransform.bo2Dto(individuDS.findByEmail(MAILUPDATEFBOPTOUT));
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("I", ind.getType());

		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		
		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("8", response.getInformationResponse()));
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
		
	}

	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
	public void testCreateOrUpdateIndividual_Prospect_UpdateExistingIndividualMaOptoutToOptin() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");
		
		String optin = "Y";
		
		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, MAILUPDATEMAOPTOUT);
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
		
		IndividuDTO ind = null;
		try {
			ind = IndividuTransform.bo2Dto(individuDS.findByEmail(MAILUPDATEMAOPTOUT));
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("I", ind.getType());
		
		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("3", response.getInformationResponse()));

		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
		
	}
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
	public void testCreateOrUpdateIndividual_Prospect_UpdateExistingIndividualMaOptoutToOptout() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");
		
		String optin = "N";
		
		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, MAILUPDATEMAOPTOUT);
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
		
		IndividuDTO ind = null;
		try {
			ind = IndividuTransform.bo2Dto(individuDS.findByEmail(MAILUPDATEMAOPTOUT));
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("I", ind.getType());
		
		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("9", response.getInformationResponse()));

		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
		
	}
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
	public void testCreateOrUpdateIndividual_Prospect_UpdateExistingIndividualMaOptinToOptin() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");
		
		String optin = "Y";
		
		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, MAILUPDATEMAOPTIN);
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
		
		IndividuDTO ind = null;
		try {
			ind = IndividuTransform.bo2Dto(individuDS.findByEmail(MAILUPDATEMAOPTIN));
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("I", ind.getType());
		
		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("6", response.getInformationResponse()));
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
		
	}
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
	public void testCreateOrUpdateIndividual_Prospect_UpdateExistingIndividualMaOptinToOptout() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");
		
		String optin = "N";
		
		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, MAILUPDATEMAOPTIN);
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
		
		IndividuDTO ind = null;
		try {
			ind = IndividuTransform.bo2Dto(individuDS.findByEmail(MAILUPDATEMAOPTIN));
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("I", ind.getType());
		
		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("3", response.getInformationResponse()));
		
		logger.info("Gin updated: " + response.getGin());
		logger.info("End of test");
		
	}



	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_UpdateIndividualByGin() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");
		
		String optin = "Y";
		
		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, MAILUPDATEFBOPTOUT);
		request.getIndividualRequest().getIndividualInformations().setIdentifier(individualGin);

		request.getIndividualRequest().getIndividualInformations().setCivility(null);
		request.getIndividualRequest().getIndividualInformations().setFirstNameSC(null);
		request.getIndividualRequest().getIndividualInformations().setSecondFirstName(null);
		request.getIndividualRequest().getIndividualInformations().setLastNameSC(null);
		request.getIndividualRequest().getIndividualInformations().setBirthDate(null);
		request.getIndividualRequest().getIndividualInformations().setNationality(null);
		request.getIndividualRequest().getIndividualInformations().setSecondNationality(null);
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
		
		IndividuDTO ind = null;
		try {
			ind = IndividuTransform.bo2Dto(individuDS.findByEmail(MAILUPDATEFBOPTOUT));
		} catch (JrafDomainException e) {
			logger.error(e);
		}

		// Individu verification
		Assert.assertEquals("I", ind.getType());
		
		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		logger.info("End of test");
		
	}

	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_UpdateProspectByGin() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");
		
		String optin = "Y";
		
		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, MAILUPDATEPROSPECT);
		request.getIndividualRequest().getIndividualInformations().setIdentifier(prospectGin);
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		IndividuDTO ind = null;
		try {
			List<IndividuDTO> listInd = individuDS.findProspectByEmail(MAILUPDATEPROSPECT);
			if(listInd != null && listInd.size() > 0) {
				ind = listInd.get(0);
			}
		} catch (JrafDomainException e) {
			logger.error(e);
		}
		
		Assert.assertNotNull(ind);

		// Individu verification
		Assert.assertEquals("W", ind.getType());
		
		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		
		logger.info("End of test");
		
	}
	
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_Prospect_UpdateProspectOptinToOptin() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");
		
		String optin = "Y";
		
		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, MAILUPDATEPROSPECTOPTIN);
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		IndividuDTO ind = null;
		try {
			List<IndividuDTO> listInd = individuDS.findProspectByEmail(MAILUPDATEPROSPECTOPTIN);
			if(listInd != null && listInd.size() > 0) {
				ind = listInd.get(0);
			}
		} catch (JrafDomainException e) {
			logger.error(e);
		}
		
		
		Assert.assertNotNull(ind);
		
		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("4", response.getInformationResponse()));
		
	}
	

	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
	public void testCreateOrUpdateIndividual_Prospect_UpdateProspectOptinToOptout() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");
		
		String optin = "N";
		
		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, MAILUPDATEPROSPECTOPTIN);
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		IndividuDTO ind = null;
		try {
			List<IndividuDTO> listInd = individuDS.findProspectByEmail(MAILUPDATEPROSPECTOPTIN);
			if(listInd != null && listInd.size() > 0) {
				ind = listInd.get(0);
			}
		} catch (JrafDomainException e) {
			logger.error(e);
		}
		
		
		Assert.assertNotNull(ind);

		// Individu verification
		Assert.assertEquals("W", ind.getType());
		
		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("1", response.getInformationResponse()));
		
	}
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
	public void testCreateOrUpdateIndividual_Prospect_UpdateProspectOptoutToOptout() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");
		
		String optin = "N";
		
		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, MAILUPDATEPROSPECTOPTOUT);
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		IndividuDTO ind = null;
		try {
			List<IndividuDTO> listInd = individuDS.findProspectByEmail(MAILUPDATEPROSPECTOPTOUT);
			if(listInd != null && listInd.size() > 0) {
				ind = listInd.get(0);
			}
		} catch (JrafDomainException e) {
			logger.error(e);
		}
		
		
		Assert.assertNotNull(ind);

		// Individu verification
		Assert.assertEquals("W", ind.getType());
		
		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("7", response.getInformationResponse()));
		
	}

	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
	public void testCreateOrUpdateIndividual_Prospect_UpdateProspectOptoutToOptin() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");
		
		String optin = "Y";
		
		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, null, COMTYPE, MARKETLANGUAGE, optin, NAMEIND, MAILUPDATEPROSPECTOPTOUT);
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		IndividuDTO ind = null;
		try {
			List<IndividuDTO> listInd = individuDS.findProspectByEmail(MAILUPDATEPROSPECTOPTOUT);
			if(listInd != null && listInd.size() > 0) {
				ind = listInd.get(0);
			}
		} catch (JrafDomainException e) {
			logger.error(e);
		}
		
		
		Assert.assertNotNull(ind);

		// Individu verification
		Assert.assertEquals("W", ind.getType());
		
		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());

		Assert.assertTrue(ProspectCodeFoundOnInformationResponse("1", response.getInformationResponse()));
		
	}
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
	public void testCreateOrUpdateIndividual_Prospect_ChangeMarketLanguage() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");
		
		String optin = "Y";
		String marketLanguage = "IT";
		
		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, null, COMTYPE, marketLanguage, optin, NAMEIND, MAILUPDATEPROSPECTOPTIN);
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		IndividuDTO ind = null;
		try {
			List<IndividuDTO> listInd = individuDS.findProspectByEmail(MAILUPDATEPROSPECTOPTIN);
			if(listInd != null && listInd.size() > 0) {
				ind = listInd.get(0);
			}
		} catch (JrafDomainException e) {
			logger.error(e);
		}
		
		
		Assert.assertNotNull(ind);
		
		// MarketLanguage verification
		boolean mlExist = false;
		for (MarketLanguageDTO mlDTO : ind.getCommunicationpreferencesdto().get(0).getMarketLanguageDTO()) {
			if(marketLanguage.equals(mlDTO.getMarket())) {
				mlExist = true;
			}
		}
		Assert.assertTrue(mlExist);
		
		// Individu verification
		Assert.assertEquals("W", ind.getType());
		
		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		
	}
	
	// MIGRATION DES PROSPECTS DE SICUTF8 VERS SIC
	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
	public void testCreateOrUpdateIndividual_Prospect_ChangeCommPref() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test start... ");
		
		String optin = "Y";
		String comType = "KL";
		
		CreateUpdateIndividualRequest request = buildRequestProspect(null, null, null, comType, MARKETLANGUAGE, optin, NAMEIND, MAILUPDATEPROSPECTOPTIN);
		
		// Execute test
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		IndividuDTO ind = null;
		try {
			List<IndividuDTO> listInd = individuDS.findProspectByEmail(MAILUPDATEPROSPECTOPTIN);
			if(listInd != null && listInd.size() > 0) {
				ind = listInd.get(0);
			}
		} catch (JrafDomainException e) {
			logger.error(e);
		}
		
		Assert.assertNotNull(ind);
		
		// Comm Pref verification
		boolean cpExist = false;
		for (CommunicationPreferencesDTO cpDTO : ind.getCommunicationpreferencesdto()) {
			if(comType.equals(cpDTO.getComType())) {
				cpExist = true;
			}
		}
		Assert.assertTrue(cpExist);
		
		// Individu verification
		Assert.assertEquals("W", ind.getType());
		
		// Response verification
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		
	}
	
	// Create role contract for an individual
	public void createRoleContrat(Individu ind, boolean fb, String numeroContract, String accountIdentifier) throws JrafDomainException {
				
		if(fb) {
			
			RoleContratsDTO roleContrats = new RoleContratsDTO();
			roleContrats.setIndividudto(IndividuTransform.bo2Dto(ind));
			roleContrats.setTypeContrat("FP");
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

		}
		
		com.airfrance.repind.entity.individu.AccountData accountData = new com.airfrance.repind.entity.individu.AccountData();
		accountData.setFbIdentifier(numeroContract);
		accountData.setIndividu(ind);
		accountData.setAccountIdentifier(accountIdentifier);
		accountData.setEmailIdentifier(ind.getEmail().iterator().next().getEmail());
		
		ind.setAccountData(accountData);

		accountDataRepository.saveAndFlush(accountData);
		
		entityManager.clear();
	}
	
	// Create a prospect by default
	public String createDefaultProspect(String mail, String optin) throws BusinessErrorBlocBusinessException, JrafDomainException {

		IndividuDTO indToCreate = new IndividuDTO();
		indToCreate.setNom(NAMEIND);
		indToCreate.setPrenom("FIRSTNAME_UT");
		indToCreate.setCivilite("MR");
		indToCreate.setSignatureCreation("UT_PROSP");
		indToCreate.setSiteCreation("QVI");
		indToCreate.setDateCreation(new Date());
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
		
//		return response.getGin();
		

	}

	// Create an individual by default
	public String createDefaultIndividu(String mail, String optin, boolean fb, String numeroContract, String accountIdentifier) throws BusinessErrorBlocBusinessException, JrafDomainException {

		IndividuDTO indToCreate = new IndividuDTO();
		indToCreate.setNom(NAMEIND);
		indToCreate.setPrenom("FIRSTNAME_UT");
		indToCreate.setCivilite("MR");
		indToCreate.setSignatureCreation("UT_PROSP");
		indToCreate.setSiteCreation("QVI");
		indToCreate.setDateCreation(new Date());
		indToCreate.setNonFusionnable("O");
		indToCreate.setSexe("U");
		indToCreate.setType("W");
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
		
		IndividuDTO indToUpdate = individuDS.getByGin(gin);
		
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
		indToUpdate.setCommunicationpreferencesdto(listCommPref);
		
		individuDS.updateAProspectToIndividual(indToUpdate);
		
		createRoleContrat(IndividuTransform.dto2Bo(indToUpdate), fb, numeroContract, accountIdentifier);
		
		return gin;

	}
	
	// Build request for individual on CreateUpdateIndividual
	public CreateUpdateIndividualRequest buildRequestIndividual(String noAndStreet, String numero, String preferredAirport, String nom, String mail) {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setMatricule(MATRICULE);
		requestor.setContext(CONTEXT);
		requestor.setApplicationCode(CODE_APP);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setToken(TOKEN);
		
		// Email
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();

		email.setEmail(mail);
		email.setMediumCode("D");
		email.setMediumStatus("V");
		emailRequest.setEmail(email);

		// Postal address
		PostalAddressContent contentPostalAddress = new PostalAddressContent();
		contentPostalAddress.setNumberAndStreet(noAndStreet);
		contentPostalAddress.setCity("Nice");
		contentPostalAddress.setCountryCode("FR");
		contentPostalAddress.setZipCode("06600");
		
		PostalAddressProperties propertiesPostalAddress = new PostalAddressProperties();
		propertiesPostalAddress.setMediumCode("P");
		propertiesPostalAddress.setMediumStatus("V");
		
		PostalAddressRequest postalAddressRequest = new PostalAddressRequest();
		postalAddressRequest.setPostalAddressContent(contentPostalAddress);
		postalAddressRequest.setPostalAddressProperties(propertiesPostalAddress);
				
		Telecom telecom = new Telecom();
		telecom.setPhoneNumber(numero);
		telecom.setCountryCode("33");
		telecom.setMediumCode("D");
		telecom.setMediumStatus("V");
		telecom.setTerminalType("M");
		
		TelecomRequest telecomRequest = new TelecomRequest();
		telecomRequest.setTelecom(telecom);
		
		IndividualRequest individualRequest = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setFirstNameSC("Prénom");
		indInfo.setSecondFirstName("Second prénom");
		indInfo.setLastNameSC(nom);
		Date date = new Date();
		indInfo.setBirthDate(date);
		indInfo.setNationality("FR");
		indInfo.setSecondNationality("IT");
		indInfo.setStatus("V");
		
		individualRequest.setIndividualInformations(indInfo);
		
		request.setRequestor(requestor);
		request.getEmailRequest().add(emailRequest);
		request.getPostalAddressRequest().add(postalAddressRequest);
		request.getTelecomRequest().add(telecomRequest);
		request.setIndividualRequest(individualRequest);

		return request;
	}

	
	// Build request for CreateUpdateIndividual
	public CreateUpdateIndividualRequest buildRequestProspect(String noAndStreet, String numero, String preferredAirport, String comType, String marketLanguage, String optin, String nom, String mail) {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect

		/*** Set parameters ***/
		// Requestor
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setMatricule(MATRICULE);
		requestor.setContext(CONTEXT);
		requestor.setApplicationCode(CODE_APP);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		
		// Email
		if(mail != null) {
			EmailRequest emailRequest = new EmailRequest();
			Email email = new Email();

			email.setEmail(mail);
			email.setEmailOptin("T");
			email.setMediumCode("D");
			email.setMediumStatus("V");
			emailRequest.setEmail(email);
			
			request.getEmailRequest().add(emailRequest);
		}

		// Communication Preference
		ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
		CommunicationPreferences comPref = new CommunicationPreferences();
		comPref.setDomain("S");
		comPref.setCommunicationGroupeType("N");
		comPref.setCommunicationType(comType);
		comPref.setOptIn(optin);
		comPref.setDateOfConsent(new Date());
		comPref.setSubscriptionChannel(CONTEXT);

		// Market Language
		MarketLanguage ml = new MarketLanguage();
		ml.setLanguage(LanguageCodesEnum.fromValue(marketLanguage));
		ml.setMarket(marketLanguage);
		ml.setOptIn(optin);
		ml.setDateOfConsent(new Date());

		comPref.getMarketLanguage().add(ml);
		comPrefrequest.setCommunicationPreferences(comPref);
		
		// ADRESSE
		if(noAndStreet != null) {

			// Postal address
			PostalAddressContent contentPostalAddress = new PostalAddressContent();
			contentPostalAddress.setNumberAndStreet(noAndStreet);
			contentPostalAddress.setCity(CITY);
			contentPostalAddress.setCountryCode(COUNTRY);
			contentPostalAddress.setZipCode(ZIPCODE);
			
			PostalAddressProperties propertiesPostalAddress = new PostalAddressProperties();
			propertiesPostalAddress.setMediumCode("D");
			propertiesPostalAddress.setMediumStatus("V");
			propertiesPostalAddress.setIndicAdrNorm(true);
			
			PostalAddressRequest postalAddressRequest = new PostalAddressRequest();
			postalAddressRequest.setPostalAddressContent(contentPostalAddress);
			postalAddressRequest.setPostalAddressProperties(propertiesPostalAddress);

			request.getPostalAddressRequest().add(postalAddressRequest);
			
		}
		
		// Preferences
		if(preferredAirport != null) {

			Preference preference = new Preference();
			preference.setTypePreference("WW");
			
			PreferenceData preferenceDataPA = new PreferenceData();
			preferenceDataPA.setKey(preferredAirportTitle);
			preferenceDataPA.setValue(preferredAirport);
			
			preference.getPreferenceData().add(preferenceDataPA);

			PreferenceDataRequest preferenceRequest = new PreferenceDataRequest();
			preferenceRequest.getPreference().add(preference);
			
			request.setPreferenceDataRequest(preferenceRequest);
			
		}
		
		// Numero
		if(numero != null) {
			Telecom telecom = new Telecom();
			telecom.setPhoneNumber(numero);
			telecom.setCountryCode(COUNTRYCODE);
			telecom.setMediumCode("D");
			telecom.setMediumStatus("V");
			telecom.setTerminalType(TERMINALTYPE);
			
			TelecomRequest telecomRequest = new TelecomRequest();
			telecomRequest.setTelecom(telecom);
			
			request.getTelecomRequest().add(telecomRequest);
		}
		
		IndividualRequest individualRequest = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setCivility("MR");
		indInfo.setFirstNameSC("Prénom");
		indInfo.setSecondFirstName("Second prénom");
		indInfo.setLastNameSC(nom);
		Date date = new Date();
		indInfo.setBirthDate(date);
		indInfo.setNationality("FR");
		indInfo.setSecondNationality("IT");
		// S08924 : 154 - STATUS MANDATORY
		indInfo.setStatus("V");
		
		individualRequest.setIndividualInformations(indInfo);
		
		request.setRequestor(requestor);
		request.getComunicationPreferencesRequest().add(comPrefrequest);
		request.setIndividualRequest(individualRequest);
		
		request.setUpdateCommunicationPrefMode("U");

		return request;
	}

	private boolean ProspectCodeFoundOnInformationResponse(String errorCode, List <InformationResponse> irl) {
	
		boolean found = false;
		for (InformationResponse ir : irl) {
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
