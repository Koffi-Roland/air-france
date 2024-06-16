package com.airfrance.repind.dao.individu;

import com.airfrance.repind.entity.individu.MarketLanguage;
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
public interface MarketLanguageRepository extends JpaRepository<MarketLanguage, Integer>, MarketLanguageRepositoryCustom {


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

	/**
	 * findMarketId : look for MARKET_LANGUAGE_ID with
	 * (comPrefId,market,language, MEDIA = 'E' )
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
			+ "and MEDIA1='E' "
			+ "and ROWNUM<=1 "
			+ "order by MODIFICATION_DATE,MARKET_LANGUAGE_ID desc ", nativeQuery=true)
	MarketLanguage findMarketIdByMedia(@Param("comPrefId") int comPrefId, @Param("market") String market, @Param("language") String language);

	/**
	 * 
	 * @param gin
	 */
	@Query(value="delete from SIC2.MARKET_LANGUAGE "
			+ "where COM_PREF_ID in "
			+ "( "
			+ "select COM_PREF_ID from SIC2.COMMUNICATION_PREFERENCES "
			+ "where SGIN = :gin "
			+ ") ", nativeQuery=true)
	void removeByGin(@Param("gin") String gin);
	
	public MarketLanguage findByMarketLanguageId(Integer id);

}
