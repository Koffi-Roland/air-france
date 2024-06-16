package com.airfrance.repind.util.ut.comparators;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.MarketLanguage;
import com.airfrance.repind.util.transformer.CommunicationPreferencesTransformer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommunicationPreferencesTransformerTest {
    private static final Log log = LogFactory.getLog(CommunicationPreferencesTransformerTest.class);

	@Test
	public void testUpdateMarketLanguageInsert() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		/*
		 * Insert a new market language
		 */
		
		Set<MarketLanguage> marketLanguageOrigin = new HashSet<MarketLanguage>();
		Set<MarketLanguage> marketLanguageUpdate = new HashSet<MarketLanguage>();
		
		MarketLanguage ml1 = new MarketLanguage();
		ml1.setLanguage("FR");
		ml1.setMarket("FR");
		ml1.setMarketLanguageId(42);
		
		marketLanguageOrigin.add(ml1);

		MarketLanguage ml2 = new MarketLanguage();
		ml2.setLanguage("FR");
		ml2.setMarket("FR");
		
		marketLanguageUpdate.add(ml2);
		
		MarketLanguage ml3 = new MarketLanguage();
		ml3.setLanguage("EN");
		ml3.setMarket("EN");
		
		marketLanguageUpdate.add(ml3);
		
		SignatureDTO signature = new SignatureDTO();
		signature.setApplicationCode("Test");
		signature.setDate(new Date());
		signature.setHeure("10");
		signature.setSignature("toto");
		signature.setSite("QVI");
		signature.setTypeSignature("tata");
		
		final Method method = CommunicationPreferencesTransformer.class.getDeclaredMethod("compareAndUpdateMarketLanguage", Set.class, Set.class, SignatureDTO.class, String.class);
		method.setAccessible(true);
		method.invoke(null, marketLanguageOrigin, marketLanguageUpdate, signature, "S");
		
		assertEquals(marketLanguageOrigin.size(), 2);
		for(MarketLanguage mlResult : marketLanguageOrigin){
			if(StringUtils.equals(mlResult.getLanguage(), "FR") ){
				assertTrue(mlResult.getMarketLanguageId() == 42);
			}else{
				assertTrue(mlResult.getMarketLanguageId() == null);
			}
		}
	}
	
	
	
	@Test
	public void testUpdateMarketLanguageUpdate() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		/*
		 * Update an existing market language
		 */
		
		Set<MarketLanguage> marketLanguageOrigin = new HashSet<MarketLanguage>();
		Set<MarketLanguage> marketLanguageUpdate = new HashSet<MarketLanguage>();
		
		MarketLanguage ml1 = new MarketLanguage();
		ml1.setLanguage("FR");
		ml1.setMarket("FR");
		ml1.setMarketLanguageId(42);
		
		marketLanguageOrigin.add(ml1);
		
		MarketLanguage ml4 = new MarketLanguage();
		ml4.setLanguage("EN");
		ml4.setMarket("FR");
		ml4.setOptIn("N");
		ml4.setMarketLanguageId(43);
		
		marketLanguageOrigin.add(ml4);
	
		MarketLanguage ml2 = new MarketLanguage();
		ml2.setLanguage("FR");
		ml2.setMarket("FR");
		
		marketLanguageUpdate.add(ml2);
		
		MarketLanguage ml3 = new MarketLanguage();
		ml3.setLanguage("EN");
		ml3.setMarket("FR");
		ml3.setOptIn("Y");
		
		marketLanguageUpdate.add(ml3);
		
		SignatureDTO signature = new SignatureDTO();
		signature.setApplicationCode("Test");
		signature.setDate(new Date());
		signature.setHeure("10");
		signature.setSignature("toto");
		signature.setSite("QVI");
		signature.setTypeSignature("tata");
		
		final Method method = CommunicationPreferencesTransformer.class.getDeclaredMethod("compareAndUpdateMarketLanguage", Set.class, Set.class, SignatureDTO.class, String.class);
		method.setAccessible(true);
		method.invoke(null, marketLanguageOrigin, marketLanguageUpdate, signature, "S");
		
		assertEquals(marketLanguageOrigin.size(), 2);
		for(MarketLanguage mlResult : marketLanguageOrigin){
			if(mlResult.getMarketLanguageId() == 43){
				assertTrue(StringUtils.equals(mlResult.getOptIn() , "Y"));
			}
		}
	}
	
	
	@Test
	public void testUpdateCommunicationPreferenceAndMarketLanguageUpdate() throws InvalidParameterException{
		
		/*
		 * Update an existing communication preferences & market language
		 */
		
		Set<CommunicationPreferences> comPrefsOrigin = new HashSet<CommunicationPreferences>();
		Set<CommunicationPreferences> comPrefsUpdate = new HashSet<CommunicationPreferences>();

		CommunicationPreferences cp1 = new CommunicationPreferences();
		cp1.setComGroupType("1");
		cp1.setComPrefId(420);
		cp1.setComType("tata");
		cp1.setDomain(null);
		cp1.setMarketLanguage(new HashSet<MarketLanguage>());

		comPrefsOrigin.add(cp1);
		
		CommunicationPreferences cp2 = new CommunicationPreferences();
		cp2.setComGroupType("2");
		cp2.setComPrefId(421);
		cp2.setComType("tata");
		cp2.setDomain(null);
		cp2.setSubscribe("N");
		
		Set<MarketLanguage> marketLanguageOrigin = new HashSet<MarketLanguage>();
		Set<MarketLanguage> marketLanguageUpdate = new HashSet<MarketLanguage>();
		MarketLanguage ml1 = new MarketLanguage();
		ml1.setLanguage("FR");
		ml1.setMarket("FR");
		ml1.setMarketLanguageId(42);
		marketLanguageOrigin.add(ml1);
		
		MarketLanguage ml4 = new MarketLanguage();
		ml4.setLanguage("EN");
		ml4.setMarket("FR");
		ml4.setOptIn("N");
		ml4.setMarketLanguageId(43);
		marketLanguageOrigin.add(ml4);

		MarketLanguage ml2 = new MarketLanguage();
		ml2.setLanguage("FR");
		ml2.setMarket("FR");
		marketLanguageUpdate.add(ml2);
		
		MarketLanguage ml3 = new MarketLanguage();
		ml3.setLanguage("EN");
		ml3.setMarket("FR");
		ml3.setOptIn("Y");
		marketLanguageUpdate.add(ml3);
		
		MarketLanguage ml5 = new MarketLanguage();
		ml5.setLanguage("RU");
		ml5.setMarket("FR");
		ml5.setOptIn("Y");
		marketLanguageUpdate.add(ml5);

		cp2.setMarketLanguage(marketLanguageOrigin);
		comPrefsOrigin.add(cp2);	
		
		CommunicationPreferences cp3 = new CommunicationPreferences();
		cp3.setComGroupType("1");
		cp3.setComType("tata");
		cp3.setDomain(null);
		cp3.setMarketLanguage(new HashSet<MarketLanguage>());
		comPrefsUpdate.add(cp3);
		
		CommunicationPreferences cp4 = new CommunicationPreferences();
		cp4.setComGroupType("2");
		cp4.setComType("tata");
		cp4.setDomain(null);
		cp4.setSubscribe("Y");
		cp4.setMarketLanguage(new HashSet<MarketLanguage>());
		cp4.setMarketLanguage(marketLanguageUpdate);
		comPrefsUpdate.add(cp4);

		SignatureDTO signature = new SignatureDTO();
		signature.setApplicationCode("Test");
		signature.setDate(new Date());
		signature.setHeure("10");
		signature.setSignature("toto");
		signature.setSite("QVI");
		signature.setTypeSignature("tata");
				
		CommunicationPreferencesTransformer.compareAndUpdate(comPrefsOrigin, comPrefsUpdate, signature);

		assertEquals(comPrefsOrigin.size(), 2);
		for(CommunicationPreferences resultCp: comPrefsOrigin){
						
			if(StringUtils.equals(resultCp.getComGroupType(), "2")){
				assertTrue(StringUtils.equals(resultCp.getSubscribe() , "Y"));
			}
			
			if(resultCp.getComPrefId() ==  421){
				assertEquals(3,resultCp.getMarketLanguage().size());
			}
			for(MarketLanguage mlResult : resultCp.getMarketLanguage()){
				log.info(mlResult);
				if(mlResult.getMarketLanguageId() != null &&  mlResult.getMarketLanguageId() == 43) {
					assertEquals(mlResult.getOptIn(), "Y");
				}
			}
		}
	}
	
	
	@Test
	public void testUpdateCommunicationPreferenceUpdate() throws InvalidParameterException{
		
		/*
		 * Update an existing communication preferences
		 */
		
		Set<CommunicationPreferences> comPrefsOrigin = new HashSet<CommunicationPreferences>();
		Set<CommunicationPreferences> comPrefsUpdate = new HashSet<CommunicationPreferences>();

		CommunicationPreferences cp1 = new CommunicationPreferences();
		cp1.setComGroupType("1");
		cp1.setComPrefId(420);
		cp1.setComType("tata");
		cp1.setDomain(null);
		cp1.setMarketLanguage(new HashSet<MarketLanguage>());
		comPrefsOrigin.add(cp1);
		
		CommunicationPreferences cp2 = new CommunicationPreferences();
		cp2.setComGroupType("2");
		cp2.setComPrefId(421);
		cp2.setComType("tata");
		cp2.setDomain(null);
		cp2.setSubscribe("N");
		cp2.setMarketLanguage(new HashSet<MarketLanguage>());
		comPrefsOrigin.add(cp1);	
		
		CommunicationPreferences cp3 = new CommunicationPreferences();
		cp3.setComGroupType("1");
		cp3.setComType("tata");
		cp3.setDomain(null);
		cp3.setMarketLanguage(new HashSet<MarketLanguage>());
		comPrefsUpdate.add(cp3);
		
		CommunicationPreferences cp4 = new CommunicationPreferences();
		cp4.setComGroupType("2");
		cp4.setComType("tata");
		cp4.setDomain(null);
		cp4.setSubscribe("Y");
		cp4.setMarketLanguage(new HashSet<MarketLanguage>());
		comPrefsUpdate.add(cp4);
				
		SignatureDTO signature = new SignatureDTO();
		signature.setApplicationCode("Test");
		signature.setDate(new Date());
		signature.setHeure("10");
		signature.setSignature("toto");
		signature.setSite("QVI");
		signature.setTypeSignature("tata");
				
		CommunicationPreferencesTransformer.compareAndUpdate(comPrefsOrigin, comPrefsUpdate, signature);

		assertEquals(comPrefsOrigin.size(), 2);
		for(CommunicationPreferences resultCp: comPrefsOrigin){
			
			if(StringUtils.equals(resultCp.getComGroupType(), "2")){
				assertTrue(StringUtils.equals(resultCp.getSubscribe() , "Y"));
			}
		}
	}
	
	@Test
	public void testUpdateCommunicationPreferenceInsertInEmptyList() throws InvalidParameterException{
		
		
		/*
		 * Create a new communication preference
		 */
		
		Set<CommunicationPreferences> comPrefsOrigin = new HashSet<CommunicationPreferences>();
		Set<CommunicationPreferences> comPrefsUpdate = new HashSet<CommunicationPreferences>();

		CommunicationPreferences cp3 = new CommunicationPreferences();
		cp3.setComGroupType("1");
		cp3.setComType("tata");
		cp3.setDomain(null);
		cp3.setMarketLanguage(new HashSet<MarketLanguage>());
		comPrefsUpdate.add(cp3);
				
		SignatureDTO signature = new SignatureDTO();
		signature.setApplicationCode("Test");
		signature.setDate(new Date());
		signature.setHeure("10");
		signature.setSignature("toto");
		signature.setSite("QVI");
		signature.setTypeSignature("tata");
				
		CommunicationPreferencesTransformer.compareAndUpdate(comPrefsOrigin, comPrefsUpdate, signature);
		assertEquals(1, comPrefsOrigin.size());
		
		for(CommunicationPreferences resultCp: comPrefsOrigin){
			
			if(StringUtils.equals(resultCp.getComGroupType(), "1")){
				assertTrue(null == resultCp.getComPrefId() );
			}
		}
	}
	
	@Test
	public void testUpdateCommunicationPreferenceInsert() throws InvalidParameterException{
		
		
		/*
		 * Create a new communication preference
		 * 
		 */
		
		Set<CommunicationPreferences> comPrefsOrigin = new HashSet<CommunicationPreferences>();
		Set<CommunicationPreferences> comPrefsUpdate = new HashSet<CommunicationPreferences>();

		CommunicationPreferences cp1 = new CommunicationPreferences();
		cp1.setComGroupType("1");
		cp1.setComPrefId(420);
		cp1.setComType("tata");
		cp1.setDomain(null);
		cp1.setMarketLanguage(new HashSet<MarketLanguage>());
		comPrefsOrigin.add(cp1);
		
		CommunicationPreferences cp2 = new CommunicationPreferences();
		cp2.setComGroupType("2");
		cp2.setComPrefId(421);
		cp2.setComType("tata");
		cp2.setDomain(null);
		cp2.setMarketLanguage(new HashSet<MarketLanguage>());

		comPrefsOrigin.add(cp2);	
		
		CommunicationPreferences cp3 = new CommunicationPreferences();
		cp3.setComGroupType("1");
		cp3.setComType("tata");
		cp3.setDomain(null);
		cp3.setMarketLanguage(new HashSet<MarketLanguage>());

		comPrefsUpdate.add(cp3);
		
		CommunicationPreferences cp4 = new CommunicationPreferences();
		cp4.setComGroupType("2");
		cp4.setComType("tata");
		cp4.setDomain(null);
		cp4.setMarketLanguage(new HashSet<MarketLanguage>());

		comPrefsUpdate.add(cp4);
		
		CommunicationPreferences cp5 = new CommunicationPreferences();
		cp5.setComGroupType("3");
		cp5.setComType("test1");
		cp5.setDomain("N");
		cp5.setMarketLanguage(new HashSet<MarketLanguage>());

		comPrefsUpdate.add(cp5);
		
		SignatureDTO signature = new SignatureDTO();
		signature.setApplicationCode("Test");
		signature.setDate(new Date());
		signature.setHeure("10");
		signature.setSignature("toto");
		signature.setSite("QVI");
		signature.setTypeSignature("tata");
				
		CommunicationPreferencesTransformer.compareAndUpdate(comPrefsOrigin, comPrefsUpdate, signature);

		assertEquals(3, comPrefsOrigin.size());
		for(CommunicationPreferences resultCp: comPrefsOrigin){
			
			if(StringUtils.equals(resultCp.getComGroupType(), "1")){
				assertTrue(420 == resultCp.getComPrefId() );
			}
			if(StringUtils.equals(resultCp.getComGroupType(), "2")){
				assertTrue(resultCp.getComPrefId() == 421);
			}
			if(StringUtils.equals(resultCp.getComGroupType(), "3")){
				assertTrue(resultCp.getComPrefId() == null);
			}
		}
	}
	
	@Test
	public void testUpdateCommunicationPreferenceDefaultDomain() throws InvalidParameterException{
		
		/*
		 * Check that the default domain "S" is correctly added if the previous domain was NULL
		 */
		
		Set<CommunicationPreferences> comPrefsOrigin = new HashSet<CommunicationPreferences>();
		Set<CommunicationPreferences> comPrefsUpdate = new HashSet<CommunicationPreferences>();

		CommunicationPreferences cp1 = new CommunicationPreferences();
		cp1.setComGroupType("toto");
		cp1.setComPrefId(420);
		cp1.setComType("tata");
		cp1.setDomain(null);
		cp1.setMarketLanguage(new HashSet<MarketLanguage>());

		comPrefsOrigin.add(cp1);
		
		CommunicationPreferences cp2 = new CommunicationPreferences();
		cp2.setComGroupType("toto");
		cp2.setComPrefId(421);
		cp2.setComType("tata");
		cp2.setDomain(null);
		cp2.setMarketLanguage(new HashSet<MarketLanguage>());

		comPrefsOrigin.add(cp2);
		
		
		CommunicationPreferences cp3 = new CommunicationPreferences();
		cp3.setComGroupType("toto");
		cp3.setComType("tata");
		cp3.setDomain(null);
		cp3.setMarketLanguage(new HashSet<MarketLanguage>());
		comPrefsUpdate.add(cp3);
		
		CommunicationPreferences cp4 = new CommunicationPreferences();
		cp4.setComGroupType("toto");
		cp4.setComType("tata");
		cp4.setDomain(null);
		cp4.setMarketLanguage(new HashSet<MarketLanguage>());
		comPrefsUpdate.add(cp4);
		
		SignatureDTO signature = new SignatureDTO();
		signature.setApplicationCode("Test");
		signature.setDate(new Date());
		signature.setHeure("10");
		signature.setSignature("toto");
		signature.setSite("QVI");
		signature.setTypeSignature("tata");
				
		CommunicationPreferencesTransformer.compareAndUpdate(comPrefsOrigin, comPrefsUpdate, signature);
		log.info(comPrefsOrigin.size());
		for(CommunicationPreferences resultCp: comPrefsOrigin){
			log.info(resultCp.getDomain());
			assertEquals("S", resultCp.getDomain() );
		}
	}
}
