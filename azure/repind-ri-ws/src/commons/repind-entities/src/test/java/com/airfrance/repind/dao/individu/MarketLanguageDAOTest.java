package com.airfrance.repind.dao.individu;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.config.WebSicTestConfig;
import com.airfrance.repind.dao.StaticTestEntitiesGenerator;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.MarketLanguage;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebSicTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class MarketLanguageDAOTest {
	
    @Autowired
    private MarketLanguageRepository marketLanguageRepository;

	@Autowired
	private CommunicationPreferencesRepository communicationPreferencesRepository;

	@Autowired
	private IndividuRepository individuRepository;

	@Test(expected = Test.None.class /* no exception expected */)
	@Ignore
	public void testFindMarketIdByMedia() {
		MarketLanguage marketLanguage = marketLanguageRepository.findMarketIdByMedia(10240241, "FR", "FR");
		// FIXME : LA HONTE, faire des tests pareils cause des NullDeveloperException !!!!
		// FIXME : Qu'est ce qui te prouve que ton MarketLanguage est toujours présent sur la base ????
//		LOG.info(marketLanguage.getOptIn());
	}


	@Test(expected = Test.None.class /* no exception expected */)
	@Ignore
	public void testUpdateMarketIdByMedia() throws JrafDaoException {
//		MarketLanguage marketLanguageupdate = new MarketLanguage();
		MarketLanguage marketLanguage = marketLanguageRepository.findMarketIdByMedia(10240241, "FR", "FR");
		// FIXME : PAREIL
//		marketLanguageupdate.setComPrefId(999999999);
//		marketLanguage.setModificationSignature("update_fix");
//		marketLanguage.setModificationSite("QVI");
//		marketLanguageDS.updateMarketLanguage(marketLanguage);
//		MarketLanguage marketLanguage = marketLanguageDao.findMarketIdByMedia(10240241, "FR", "FR");
//		LOG.info(marketLanguage.getOptIn());
		
	}


	@Test(expected = Test.None.class /* no exception expected */)
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testInsertMarketLanguage() throws JrafDaoException {
		MarketLanguage marketLanguage = new MarketLanguage();
		marketLanguage.setCommunicationMedia1("E");
		marketLanguage.setComPrefId(10240241);
		marketLanguage.setLanguage("FR");
		marketLanguage.setMarket("FR");
		marketLanguage.setCreationDate(new Date());
		marketLanguage.setDateOfConsent(new Date());
		marketLanguage.setOptIn("Y");
		marketLanguage.setCreationSignature("MYA");
		marketLanguage.setCreationSite("KLM");
		marketLanguageRepository.insertMarketLanguage(marketLanguage);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testFindMarketLanguageByGin() throws JrafDaoException {
		// Create test entities
		Individu testIndividual = StaticTestEntitiesGenerator.createTestIndividu();
		CommunicationPreferences testCommunicationPreferences = StaticTestEntitiesGenerator
				.createTestCommunicationPreferences();

		Set<CommunicationPreferences> communicationPreferencesSet = new HashSet<CommunicationPreferences>();
		communicationPreferencesSet.add(testCommunicationPreferences);
		testIndividual.setCommunicationpreferences(communicationPreferencesSet);

		Set<MarketLanguage> marketLanguageSet = new HashSet<MarketLanguage>();
		testCommunicationPreferences.setMarketLanguage(marketLanguageSet);

		// Save them in the database
		testIndividual = individuRepository.saveAndFlush(testIndividual);

		String gin = testIndividual.getSgin();
		testCommunicationPreferences.setGin(gin);
		testCommunicationPreferences = communicationPreferencesRepository.saveAndFlush(testCommunicationPreferences);

		Integer comPrefId = testCommunicationPreferences.getComPrefId();
		marketLanguageSet.add(StaticTestEntitiesGenerator.createTestMarketLanguage("FR", "FR", comPrefId));
		marketLanguageSet.add(StaticTestEntitiesGenerator.createTestMarketLanguage("IN", "EN", comPrefId));
		marketLanguageSet.add(StaticTestEntitiesGenerator.createTestMarketLanguage("NL", "NL", comPrefId));
		marketLanguageSet.add(StaticTestEntitiesGenerator.createTestMarketLanguage("IT", "IT", comPrefId));
		marketLanguageSet.add(StaticTestEntitiesGenerator.createTestMarketLanguage("US", "EN", comPrefId));
		for (MarketLanguage ml : marketLanguageSet) {
			marketLanguageRepository.saveAndFlush(ml);
		}

		List<MarketLanguage> languages = marketLanguageRepository.findMarketLanguageByGin(gin);
		assertNotNull(languages);
		assertEquals(marketLanguageSet.size(), languages.size());
	}
	
}
