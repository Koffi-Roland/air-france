package com.airfrance.repind.dao.individu;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.config.WebSicTestConfig;
import com.airfrance.repind.dao.AbstractDAOTest;
import com.airfrance.repind.dao.StaticTestEntitiesGenerator;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.IndividuLight;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebSicTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class IndividuDAOTest extends AbstractDAOTest {

	/** logger */
	private static final Log LOG = LogFactory.getLog(IndividuDAOTest.class);

	/**
	 * Individual DAO
	 */
	@Autowired
	private IndividuRepository individuRepository;


	@Test(expected = Test.None.class /* no exception expected */)
	public void testFindJpa() throws JrafDaoException {

		Individu ind = individuRepository.getOne("400380208372");
		LOG.info(ind.getNom());
		LOG.info(ind.getProfils());
	}

//
//	// @Test
//	public void testFindByGinFetchingRelationships1() throws JrafDaoException {
//
//		long start = System.currentTimeMillis();
//
//		Individu ind = individuRepository.findByGinFetchingRelationships("400350914750",
//				Arrays.asList("accountData as acc", "communicationpreferences as cp", "delegateList as delegates",
//						"delegatorList as delegators", "profil_mere as pm", "postaladdress as pa", "profils", "email",
//						"telecoms", "rolecontrats", "prefilledNumbers", "externalIdentifierList"),
//				Arrays.asList("acc.socialNetworkData", "cp.marketLanguage", "delegates.delegate",
//						"delegators.delegator", "pm.profil_af", "pm.profilContentieux", "pm.profilQualitatif",
//						"pm.profilDemarchage", "pa.usage_medium"));
//
//		long elapsedTime = System.currentTimeMillis() - start;
//		LOG.warn(String.format(" time elapsed = %d ", elapsedTime));
//
//		LOG.info(String.format("ind.getCommunicationpreferences().size() = %d",
//				ind.getCommunicationpreferences().size()));
//		LOG.info(String.format("ind.getDelegateList().size() = %d", ind.getDelegateList().size()));
//		LOG.info(String.format("ind.getDelegatorList().size() = %d", ind.getDelegatorList().size()));
//		LOG.info(String.format("ind.getEmail().size() = %d", ind.getEmail().size()));
//		LOG.info(String.format("ind.getExternalIdentifierList().size() = %d", ind.getExternalIdentifierList().size()));
//		LOG.info(String.format("ind.getPostaladdress().size() = %d", ind.getPostaladdress().size()));
//		LOG.info(String.format("ind.getPrefilledNumbers().size() = %d", ind.getPrefilledNumbers().size()));
//		LOG.info(String.format("ind.getProfil_mere().size() = %d", ind.getProfil_mere().size()));
//		LOG.info(String.format("ind.getRolecontrats().size() = %d", ind.getRolecontrats().size()));
//		LOG.info(String.format("ind.getTelecoms().size() = %d", ind.getTelecoms().size()));
//
//		if (ind.getAccountData() != null && ind.getAccountData().getSocialNetworkData() != null) {
//			LOG.info(String.format("ind.getAccountData().getSocialNetworkData().size() = %d",
//					ind.getAccountData().getSocialNetworkData().size()));
//		}
//		for (CommunicationPreferences cp : ind.getCommunicationpreferences()) {
//			LOG.info(String.format("cp.getMarketLanguage().size().size() = %d", cp.getMarketLanguage().size()));
//		}
//		for (PostalAddress pa : ind.getPostaladdress()) {
//			LOG.info(String.format("pa.getUsage_medium().size().size() = %d", pa.getUsage_medium().size()));
//		}
//	}

//
//	@Test
//	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
//	public void testFindByGinFetchingRelationships2() throws JrafDaoException {
//
//		long start1 = System.currentTimeMillis();
//
//		Individu ind1 = individuRepository.findByGinFetchingRelationships("400350914750",
//				Arrays.asList("accountData as acc", "communicationpreferences as cp", "delegateList as delegates",
//						"delegatorList as delegators", "profil_mere as pm", "postaladdress as pa"),
//				Arrays.asList("acc.socialNetworkData", "cp.marketLanguage", "delegates.delegate",
//						"delegators.delegator", "pm.profil_af", "pm.profilContentieux", "pm.profilQualitatif",
//						"pm.profilDemarchage", "pa.usage_medium"));
//
//		long elapsedTime1 = System.currentTimeMillis() - start1;
//		LOG.warn(String.format(" time elapsed 1 = %d ", elapsedTime1));
//
//		LOG.info(String.format("ind.getAccountData = %s", ind1.getAccountData()));
//		LOG.info(String.format("ind.getProfils = %s", ind1.getProfils()));
//		LOG.info(String.format("ind.getCommunicationpreferences().size() = %d",
//				ind1.getCommunicationpreferences().size()));
//		LOG.info(String.format("ind.getDelegateList().size() = %d", ind1.getDelegateList().size()));
//		LOG.info(String.format("ind.getDelegatorList().size() = %d", ind1.getDelegatorList().size()));
//		LOG.info(String.format("ind.getPostaladdress().size() = %d", ind1.getPostaladdress().size()));
//		LOG.info(String.format("ind.getProfil_mere().size() = %d", ind1.getProfil_mere().size()));
//
//		if (ind1.getAccountData() != null && ind1.getAccountData().getSocialNetworkData() != null) {
//			LOG.info(String.format("ind.getAccountData().getSocialNetworkData().size() = %d",
//					ind1.getAccountData().getSocialNetworkData().size()));
//		}
//		for (CommunicationPreferences cp : ind1.getCommunicationpreferences()) {
//			LOG.info(String.format("cp.getMarketLanguage().size().size() = %d", cp.getMarketLanguage().size()));
//		}
//		for (PostalAddress pa : ind1.getPostaladdress()) {
//			LOG.info(String.format("pa.getUsage_medium().size().size() = %d", pa.getUsage_medium().size()));
//		}
//
//		long start2 = System.currentTimeMillis();
//
//		Individu ind2 = individuDAO.findByGinFetchingRelationships("400350914750", Arrays.asList("profils", "email",
//				"telecoms", "rolecontrats", "prefilledNumbers", "externalIdentifierList"), new ArrayList<String>());
//
//		long elapsedTime2 = System.currentTimeMillis() - start2;
//		LOG.warn(String.format(" time elapsed 2 = %d ", elapsedTime2));
//
//		long elapsedTime12 = elapsedTime1 + elapsedTime2;
//		LOG.warn(String.format(" time elapsed 1 + 2 = %d ", elapsedTime12));
//
//		LOG.info(String.format("ind.getEmail().size() = %d", ind2.getEmail().size()));
//		LOG.info(String.format("ind.getExternalIdentifierList().size() = %d", ind2.getExternalIdentifierList().size()));
//		LOG.info(String.format("ind.getPrefilledNumbers().size() = %d", ind2.getPrefilledNumbers().size()));
//		LOG.info(String.format("ind.getRolecontrats().size() = %d", ind2.getRolecontrats().size()));
//		LOG.info(String.format("ind.getTelecoms().size() = %d", ind2.getTelecoms().size()));
//	}
//
//
//	@Test
//	public void testFindByGinFetchingContracts() throws JrafDaoException {
//
//		Individu ind = individuRepository.findByGinFetchingRelationships("400380208372", Arrays.asList("rolecontrats"),
//				new ArrayList<String>());
//		LOG.info(ind.getRolecontrats().size());
//	}

//	
//	@Test
//	public void testFindByGinFetchingTelecoms() throws JrafDaoException {
//
//		Individu ind = individuRepository.findByGinFetchingRelationships("400350914750", Arrays.asList("telecoms"),
//				new ArrayList<String>());
//		LOG.info(ind.getTelecoms().size());
//		LOG.info(ind.fetchLatestFixedAndMobilePhones().size());
//	}
//	
	@Test
	public void testFindIndividualLightByGin() {

		// Create test entities
		Individu individu = StaticTestEntitiesGenerator.createTestIndividu();

		initTransaction();
		try {
			// Save them in DB
			individuRepository.saveAndFlush(individu);
			System.out.println("Created Individual GIN is: " + individu.getSgin());

			// Declare needed variables
			IndividuLight result = null;

			// Test DAO method: Exception excepected
			// REPIND-1199: Exception moved to DS
//			try {
//				result = individuRepository.findIndividualLightByGin(null);
//				fail(StaticTestEntitiesGenerator.AN_EXCEPTION_TO_BE_THROWN);
//
//			} catch (DataAccessException e) {
//				System.out.println("Test is OK");
//			}

			// Test search by individual GIN
			result = individuRepository.findIndividualLightByGin(individu.getSgin());
			assertNotNull(result);
			assertEquals(individu.getNom(), result.getNom());
			assertEquals(individu.getPrenom(), result.getPrenom());
			assertEquals(individu.getSgin(), result.getSgin());
			assertEquals(individu.getCivilite(), result.getCivilite());
			assertEquals(individu.getSexe(), result.getSexe());
			assertEquals(individu.getType(), result.getType());
			assertEquals(individu.getSiteModification(), result.getSiteModification());
			assertEquals(individu.getSiteCreation(), result.getSiteCreation());
			assertEquals(individu.getSignatureCreation(), result.getSignatureCreation());
			assertEquals(individu.getSignatureModification(), result.getSignatureModification());
			assertEquals(individu.getDateModification().getTime() / 1000,
					result.getDateModification().getTime() / 1000);
			assertEquals(individu.getDateCreation().getTime() / 1000, result.getDateCreation().getTime() / 1000);

		} catch (Exception e) {
			e.printStackTrace();
			fail(StaticTestEntitiesGenerator.NO_EXCEPTION_TO_BE_THROWN);
		}
	}

}
