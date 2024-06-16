package com.afklm.rigui.dao.individu;

import com.afklm.rigui.entity.individu.MarketLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Title : IMarketLanguageDAO.java
 * </p>
 * BO: MarketLanguage
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Repository
public interface MarketLanguageRepository extends JpaRepository<MarketLanguage, Integer> {


	/**
	 * findMarketId : look for MARKET_LANGUAGE_ID with triplet
	 * (comPrefId,market,language)
	 * 
	 * @param String
	 *            comPrefId
	 * @param String
	 *            market for Market language
	 * @param String
	 *            language for Market language
	 * @return bo MarketLanguage filled in
	 * @see com.airfrance.repind.dao.individu.MarketLanguageDAO#findMarketId(int
	 *      comPrefId, String market, String language)
	 */
	@Query(value="select * from SIC2.MARKET_LANGUAGE "
			+ "where COM_PREF_ID=:comPrefId "
			+ "and MARKET=:market "
			+ "and LANGUAGE_CODE=:language "
			+ "and ROWNUM<=1 "
			+ "order by MODIFICATION_DATE,MARKET_LANGUAGE_ID desc ", nativeQuery=true)
	MarketLanguage findMarketId(@Param("comPrefId") int comPrefId, @Param("market") String market, @Param("language") String language);

	public MarketLanguage findByMarketLanguageId(Integer id);

}
