package com.airfrance.repind.dao.individu;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.individu.MarketLanguage;

import java.util.List;

public interface MarketLanguageRepositoryCustom {

	/**
	 * unsubscribeMarketLanguage
	 * 
	 * @param bo
	 *            MarketLanguage marketLanguage
	 * @return 1 if update happened
	 * @throws JrafDaoException
	 * @see com.airfrance.repind.dao.individu.MarketLanguageDAO#unsubscribeMarketLanguage(MarketLanguage
	 *      marketLanguage)
	 */
	int unsubscribeMarketLanguage(MarketLanguage marketLanguage) throws JrafDaoException;


	/**
	 * For SGIN, COM_GROUP_TYPE, COM_TYPE, MEDIA='E', DOMAIN insert
	 * communication preferences
	 * 
	 * @param gin
	 * @param domain
	 * @param comGroupType
	 * @param comType
	 * @throws JrafDaoException
	 */
	void insertMarketLanguage(MarketLanguage marketLanguage) throws JrafDaoException;
	
	
	void updateMarketLanguage(MarketLanguage marketLanguage) throws JrafDaoException;
	
	/**
	 * Retrieves the list of market languages associated with communication
	 * preferences for an individual's GIN ordered by modification date.
	 * 
	 * @param gin individual's GIN
	 * @return the list of market languages associated with communication
	 *         preferences for an individual's GIN
	 * @throws JrafDaoException
	 */
	List<MarketLanguage> findMarketLanguageByGin(final String gin) throws JrafDaoException;
}
