package com.airfrance.repind.dao.individu;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.config.WebSicTestConfig;
import com.airfrance.repind.dao.AbstractDAOTest;
import com.airfrance.repind.dao.StaticTestEntitiesGenerator;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.MarketLanguage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.fail;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebSicTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CommunicationPreferencesDAOTest extends AbstractDAOTest {

	/** logger */
	private static final Log LOG = LogFactory.getLog(CommunicationPreferencesDAOTest.class);

	@Autowired
	private CommunicationPreferencesRepository communicationPreferencesRepository;

	@Autowired
	private IndividuRepository individuRepository;

	@Autowired
	private MarketLanguageRepository marketLanguageRepository;


	@Test
	public void testFindComPrefIdByMedia() throws JrafDaoException {

		// Create test entities
		Individu testIndividu = StaticTestEntitiesGenerator.createTestIndividu();
		CommunicationPreferences testCommunicationPreferences = StaticTestEntitiesGenerator
				.createTestCommunicationPreferences();
		MarketLanguage testMarketLanguage = StaticTestEntitiesGenerator.createTestMarketLanguage();

		Set<CommunicationPreferences> communicationPreferencesSet = new HashSet<CommunicationPreferences>();
		communicationPreferencesSet.add(testCommunicationPreferences);
		testIndividu.setCommunicationpreferences(communicationPreferencesSet);

		Set<MarketLanguage> marketLanguageSet = new HashSet<MarketLanguage>();
		marketLanguageSet.add(testMarketLanguage);
		testCommunicationPreferences.setMarketLanguage(marketLanguageSet);

		initTransaction();
		try {
			// Save them in the database
			individuRepository.saveAndFlush(testIndividu);
			LOG.info("Created GIN is: " + testIndividu.getSgin());

			testCommunicationPreferences.setGin(testIndividu.getSgin());
			communicationPreferencesRepository.saveAndFlush(testCommunicationPreferences);
			LOG.info("Created CP ID is: " + testCommunicationPreferences.getComPrefId());

			marketLanguageRepository.saveAndFlush(testMarketLanguage);
			LOG.info("Created ML ID is: " + testMarketLanguage.getMarketLanguageId());

			// Read the saved communicationPreference
			CommunicationPreferences readCommunicationPreferences = communicationPreferencesRepository.findComPrefIdByMedia(
					testCommunicationPreferences.getGin(), testCommunicationPreferences.getDomain(),
					testCommunicationPreferences.getComGroupType(), testCommunicationPreferences.getComType());

			// Check read values
			Assert.assertNotNull(readCommunicationPreferences);
			Assert.assertEquals(StaticTestEntitiesGenerator.signature,
					readCommunicationPreferences.getCreationSignature());
			Assert.assertEquals(testCommunicationPreferences.getGin(), readCommunicationPreferences.getGin());
			Assert.assertEquals(testCommunicationPreferences.getComGroupType(),
					readCommunicationPreferences.getComGroupType());
			Assert.assertEquals(testCommunicationPreferences.getGin(), readCommunicationPreferences.getGin());
			Assert.assertEquals(testCommunicationPreferences.getComType(), readCommunicationPreferences.getComType());
			Assert.assertEquals(testCommunicationPreferences.getSubscribe(),
					readCommunicationPreferences.getSubscribe());
			Assert.assertEquals(testCommunicationPreferences.getDomain(), readCommunicationPreferences.getDomain());
			LOG.info(readCommunicationPreferences.toStringImpl());

			// Check Market language
			Set<MarketLanguage> readMarketLanguageSet = readCommunicationPreferences.getMarketLanguage();
			Assert.assertNotNull(readMarketLanguageSet);
			Assert.assertEquals(1, readMarketLanguageSet.size());
			for (MarketLanguage readMarket : readCommunicationPreferences.getMarketLanguage()) {
				Assert.assertEquals(testMarketLanguage.getMarket(), readMarket.getMarket());
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail(StaticTestEntitiesGenerator.NO_EXCEPTION_TO_BE_THROWN);
		}
	}


	@Test
	public void testInsertComPref() throws JrafDaoException {

		// Create test entities
		Individu testIndividu = StaticTestEntitiesGenerator.createTestIndividu();
		CommunicationPreferences testCommunicationPreferences = StaticTestEntitiesGenerator
				.createTestCommunicationPreferences();
		MarketLanguage testMarketLanguage = StaticTestEntitiesGenerator.createTestMarketLanguage();

		Set<CommunicationPreferences> communicationPreferencesSet = new HashSet<CommunicationPreferences>();
		communicationPreferencesSet.add(testCommunicationPreferences);
		testIndividu.setCommunicationpreferences(communicationPreferencesSet);

		Set<MarketLanguage> marketLanguageSet = new HashSet<MarketLanguage>();
		marketLanguageSet.add(testMarketLanguage);
		testCommunicationPreferences.setMarketLanguage(marketLanguageSet);

		initTransaction();
		try {
			// Save them in the database
			individuRepository.saveAndFlush(testIndividu);
			LOG.info("Created GIN is: " + testIndividu.getSgin());

			testCommunicationPreferences.setGin(testIndividu.getSgin());
			communicationPreferencesRepository.saveAndFlush(testCommunicationPreferences);
			LOG.info(testCommunicationPreferences.toStringImpl());
			LOG.info("Created CP ID is: " + testCommunicationPreferences.getComPrefId());

			// Read the saved communicationPreference
			CommunicationPreferences readCommunicationPreferences = communicationPreferencesRepository.getOne(testCommunicationPreferences.getComPrefId());

			// Check read values
			Assert.assertNotNull(readCommunicationPreferences);
			Assert.assertEquals(StaticTestEntitiesGenerator.signature,
					readCommunicationPreferences.getCreationSignature());
			Assert.assertEquals(testCommunicationPreferences.getGin(), readCommunicationPreferences.getGin());
			Assert.assertEquals(testCommunicationPreferences.getComGroupType(),
					readCommunicationPreferences.getComGroupType());
			Assert.assertEquals(testCommunicationPreferences.getGin(), readCommunicationPreferences.getGin());
			Assert.assertEquals(testCommunicationPreferences.getComType(), readCommunicationPreferences.getComType());
			Assert.assertEquals(testCommunicationPreferences.getSubscribe(),
					readCommunicationPreferences.getSubscribe());
			Assert.assertEquals(testCommunicationPreferences.getDomain(), readCommunicationPreferences.getDomain());
			LOG.info(readCommunicationPreferences.toStringImpl());

			// Check Market language
			Set<MarketLanguage> readMarketLanguageSet = readCommunicationPreferences.getMarketLanguage();
			Assert.assertNotNull(readMarketLanguageSet);
			Assert.assertEquals(1, readMarketLanguageSet.size());
			for (MarketLanguage readMarket : readCommunicationPreferences.getMarketLanguage()) {
				Assert.assertEquals(testMarketLanguage.getMarket(), readMarket.getMarket());
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail(StaticTestEntitiesGenerator.NO_EXCEPTION_TO_BE_THROWN);
		}
	}


	@Test
	public void testUpdateComPref() throws JrafDaoException {

		// Create test entities
		Individu testIndividu = StaticTestEntitiesGenerator.createTestIndividu();
		CommunicationPreferences testCommunicationPreferences = StaticTestEntitiesGenerator
				.createTestCommunicationPreferences();

		Set<CommunicationPreferences> communicationPreferencesSet = new HashSet<CommunicationPreferences>();
		communicationPreferencesSet.add(testCommunicationPreferences);
		testIndividu.setCommunicationpreferences(communicationPreferencesSet);

		initTransaction();
		try {
			// Save them in the database
			individuRepository.saveAndFlush(testIndividu);
			LOG.info("Created GIN is: " + testIndividu.getSgin());

			testCommunicationPreferences.setGin(testIndividu.getSgin());
			communicationPreferencesRepository.saveAndFlush(testCommunicationPreferences);
			LOG.info("Created CP ID is: " + testCommunicationPreferences.getComPrefId());

			// Update communicationPreferences
			testCommunicationPreferences.setSubscribe("N");
			communicationPreferencesRepository.updateComPref(testCommunicationPreferences);

			// Read the saved communicationPreference
			CommunicationPreferences readCommunicationPreferences = communicationPreferencesRepository
					.getOne(testCommunicationPreferences.getComPrefId());

			// Check read values
			Assert.assertNotNull(readCommunicationPreferences);
			Assert.assertEquals(StaticTestEntitiesGenerator.signature,
					readCommunicationPreferences.getCreationSignature());
			Assert.assertEquals(testCommunicationPreferences.getGin(), readCommunicationPreferences.getGin());
			Assert.assertEquals(testCommunicationPreferences.getComGroupType(),
					readCommunicationPreferences.getComGroupType());
			Assert.assertEquals(testCommunicationPreferences.getGin(), readCommunicationPreferences.getGin());
			Assert.assertEquals(testCommunicationPreferences.getComType(), readCommunicationPreferences.getComType());
			Assert.assertEquals(testCommunicationPreferences.getSubscribe(),
					readCommunicationPreferences.getSubscribe());
			Assert.assertEquals(testCommunicationPreferences.getDomain(), readCommunicationPreferences.getDomain());
			LOG.info(readCommunicationPreferences.toStringImpl());

		} catch (Exception e) {
			e.printStackTrace();
			fail(StaticTestEntitiesGenerator.NO_EXCEPTION_TO_BE_THROWN);
		}
	}

	@Test
	@Transactional
	public void test_getGinWithComPrefSubscribeY(){
		String gin = communicationPreferencesRepository.getGinWithComPrefSubscribeY();
		Assert.assertTrue(gin != null);
		Assert.assertTrue(!gin.equals(""));
		
	}
	
}
