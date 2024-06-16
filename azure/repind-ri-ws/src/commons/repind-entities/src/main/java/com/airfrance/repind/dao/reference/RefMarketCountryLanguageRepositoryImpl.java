package com.airfrance.repind.dao.reference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class RefMarketCountryLanguageRepositoryImpl implements RefMarketCountryLanguageRepositoryCustom {
	
	private static final Log log = LogFactory.getLog(RefMarketCountryLanguageRepositoryImpl.class);
	
	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

	/**
	 *  Find ISO language code from not ISO language
	 *  
	 *  @param market Country Code
	 *  @param nonIsoLang Language not ISO
	 *  @param context
	 *  
	 *  @return String value of ISO language found in SIC DB
	 */
	public String findCodeIsoByMarketLangNotIsoContext(String market, String nonIsoLang, String context) {
		log.debug("START findCodeIsoByMarketLangNotIsoContext : " + System.currentTimeMillis());

		StringBuffer buffer = new StringBuffer("select rmcl.langIso from RefMarketCountryLanguage rmcl ");
		buffer.append("where rmcl.codeMarket = :pMarket ");
		buffer.append("and rmcl.langNotIso = :pLang ");
		buffer.append("and rmcl.context = :pContext ");

		Query query = entityManager.createQuery(buffer.toString());
		query.setParameter("pMarket", market);
		query.setParameter("pLang", nonIsoLang);
		query.setParameter("pContext", context);

		log.debug("END findCodeIsoByMarketLangNotIsoContext : " + System.currentTimeMillis());
		
		try {
			return (String) query.getSingleResult();
			
		} catch (NoResultException e) {
			log.info("No transcoding required for such market/language couple");
			return null;
		}
	}
}
