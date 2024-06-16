package com.airfrance.repind.dao.individu;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.individu.MarketLanguage;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class MarketLanguageRepositoryImpl implements MarketLanguageRepositoryCustom {


	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;
	
	private static final String NATIVE_QUERY_SELECT_ML_BYGIN = "SELECT * FROM MARKET_LANGUAGE WHERE com_pref_id IN (SELECT com_pref_id FROM COMMUNICATION_PREFERENCES WHERE SGIN =:sgin) ORDER BY modification_date ASC";

	/**
	 * {@inheritDoc}
	 * @throws JrafDaoException 
	 */
	public int unsubscribeMarketLanguage(MarketLanguage marketLanguage) throws JrafDaoException {

		Date today = new Date();

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("update SIC2.MARKET_LANGUAGE ");
		strQuery.append("set");
		strQuery.append(" OPTIN=:optin, ");
		strQuery.append(" MODIFICATION_SIGNATURE=:signatureModification, ");
		strQuery.append(" MODIFICATION_SITE=:siteModification, ");
		strQuery.append(" MODIFICATION_DATE=:dateModification ");
		strQuery.append(" where MARKET_LANGUAGE_ID=:marketLanguageId ");
		
		final Query myQuery = entityManager.createNativeQuery(strQuery.toString());
		
		myQuery.setParameter("optin", "N");
		myQuery.setParameter("signatureModification", marketLanguage.getModificationSignature());
		myQuery.setParameter("siteModification", marketLanguage.getModificationSite());
		myQuery.setParameter("dateModification", today);
		myQuery.setParameter("marketLanguageId", marketLanguage.getMarketLanguageId());

		try {
			return myQuery.executeUpdate();
		} catch (Exception e) {
			throw new JrafDaoException(e);
		}
	}


	@Override
	@Transactional
	public void insertMarketLanguage(MarketLanguage pMarketLanguage) throws JrafDaoException {
		
		MarketLanguage marketLanguage = new MarketLanguage();
		marketLanguage.setComPrefId(pMarketLanguage.getComPrefId());
		marketLanguage.setLanguage(pMarketLanguage.getLanguage());
		marketLanguage.setMarket(pMarketLanguage.getMarket());
		marketLanguage.setCreationDate(pMarketLanguage.getCreationDate());
		marketLanguage.setDateOfConsent(pMarketLanguage.getDateOfConsent());
		marketLanguage.setOptIn(pMarketLanguage.getOptIn());
		marketLanguage.setCreationSignature(pMarketLanguage.getCreationSignature());
		marketLanguage.setCreationSite(pMarketLanguage.getCreationSite());
		marketLanguage.setCommunicationMedia1(pMarketLanguage.getCommunicationMedia1());
		
		entityManager.persist(marketLanguage);
		
	}

	@Override
	@Transactional
	public void updateMarketLanguage(MarketLanguage marketLanguage) throws JrafDaoException {

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("update SIC2.MARKET_LANGUAGE ");
		strQuery.append("set");
		strQuery.append(" OPTIN=:optin ,");
		strQuery.append(" LANGUAGE_CODE=:languageCode ,");
		strQuery.append(" MARKET=:market ,");
		
		strQuery.append(" MODIFICATION_SIGNATURE=:signatureModification, ");
		strQuery.append(" MODIFICATION_SITE=:siteModification, ");
		strQuery.append(" MODIFICATION_DATE=:dateModification ");
		strQuery.append(" where MARKET_LANGUAGE_ID=:marketLanguageId ");
		
		final Query myQuery = entityManager.createNativeQuery(strQuery.toString());
		
		myQuery.setParameter("optin", marketLanguage.getOptIn());
		myQuery.setParameter("languageCode", marketLanguage.getLanguage());
		myQuery.setParameter("market", marketLanguage.getMarket());
		myQuery.setParameter("signatureModification", marketLanguage.getModificationSignature());
		myQuery.setParameter("siteModification", marketLanguage.getModificationSite());
		myQuery.setParameter("dateModification", new Date());
		myQuery.setParameter("marketLanguageId", marketLanguage.getMarketLanguageId());

		try {
			myQuery.executeUpdate();
		} catch (Exception e) {
			throw new JrafDaoException(e);
		}
	}


	/**
	 * {@inheritDoc}
	 * @throws JrafDaoException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MarketLanguage> findMarketLanguageByGin(String gin) throws JrafDaoException {
		Query query = entityManager.createNativeQuery(NATIVE_QUERY_SELECT_ML_BYGIN, MarketLanguage.class);
		query.setParameter("sgin", gin);
		return query.getResultList();
	}
}
