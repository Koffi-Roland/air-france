package com.airfrance.repind.dao.individu;

import com.airfrance.repind.entity.individu.CommunicationPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * <p>Title : ICommunicationPreferencesDAO.java</p>
 * BO: CommunicationPreferences
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
/**
 * @author t873614
 *
 */
@Repository
public interface CommunicationPreferencesRepository extends JpaRepository<CommunicationPreferences, Integer>, CommunicationPreferencesRepositoryCustom {

	/**
	 * Find communication preferences associated to provided GIN
	 */
	List<CommunicationPreferences> findByGin(String gin);

	void deleteByGin(String gin);

	/**
	 * findComPrefId : look for COM_PREF_ID with 4-uplet (gin,domain,comGroupType,comType)
	 * @param String gin
	 * @param String domain for Communication preferences
	 * @param String comGroupType for Communication preferences
	 * @param comType for Communication preferences
	 * @return CommunicationPreferencesDTO structure
	 * @throws NoResultException;
	 * @see com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS#findComPrefId(String gin, String domain, String comGroupType, String comType)
	 */
	@Query(value="select * from SIC2.COMMUNICATION_PREFERENCES "
			+ "where SGIN=:gin "
			+ "and DOMAIN=:domain "
			+ "and COM_GROUP_TYPE=:comGroupType "
			+ "and COM_TYPE=:comType "
			+ "and MEDIA1='E' "
			+ "and ROWNUM<=1 "
			+ "order by MODIFICATION_DATE,COM_PREF_ID desc ", nativeQuery=true)
	CommunicationPreferences findComPrefIdByMedia(@Param("gin") String gin, @Param("domain") String domain, @Param("comGroupType") String comGroupType, @Param("comType") String comType);

	/**
	 * findComPrefId : look for COM_PREF_ID with 4-uplet (gin,domain,comGroupType,comType)
	 * @param String gin
	 * @param String domain for Communication preferences
	 * @param String comGroupType for Communication preferences
	 * @param comType for Communication preferences
	 * @return CommunicationPreferencesDTO structure
	 * @throws NoResultException;
	 * @see com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS#findComPrefId(String gin, String domain, String comGroupType, String comType)
	 */
	@Query(value="select * from SIC2.COMMUNICATION_PREFERENCES "
			+ "where SGIN=:gin "
			+ "and DOMAIN=:domain "
			+ "and COM_GROUP_TYPE=:comGroupType "
			+ "and COM_TYPE=:comType "
			+ "and ROWNUM<=1 "
			+ "order by MODIFICATION_DATE,COM_PREF_ID desc", nativeQuery=true)
	public CommunicationPreferences findComPrefId(@Param("gin") String gin, @Param("domain") String domain, @Param("comGroupType") String comGroupType, @Param("comType") String comType);

	/**
	* checkOptinCoherence : functional rule to check that if a global CP is set to 'opt-out' then all its ML are opt-outed. Do the reverse logic also  
	* @param bo communicationPreferences in CommunicationPreferences	   
	* @return 0 if no coherence between CP and its related ML and vice-versa. 1 if coherence 
	* @see com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS#checkOptinCoherence(CommunicationPreferencesDTO comPrefDTO)
	*/

	@Query(value="select count(*) from SIC2.COMMUNICATION_PREFERENCES cp "
			+ " where cp.COM_PREF_ID=:comPrefId "
			+ "and ((cp.SUBSCRIBE='Y' "
			+ "and exists (select * from SIC2.MARKET_LANGUAGE ml "
			+ "where ml.COM_PREF_ID=cp.COM_PREF_ID "
			+ "and ml.OPTIN='Y')) "
			+ "OR (cp.SUBSCRIBE='N' "
			+ "and not exists (select * from SIC2.MARKET_LANGUAGE ml "
			+ "where ml.COM_PREF_ID=cp.COM_PREF_ID "
			+ "and ml.OPTIN='Y')))", nativeQuery=true)
    Long checkOptinCoherence(@Param("comPrefId") Integer comPrefId);
    
    /**
     * return a gin having communicationPreference.subscribe=Y 
     */
	@Query(value="SELECT SGIN FROM COMMUNICATION_PREFERENCES "
			+ "WHERE SUBSCRIBE='Y' AND ROWNUM <= 1", nativeQuery=true)
    String getGinWithComPrefSubscribeY();

	@Query("select count(1) from CommunicationPreferences cp "
			+ "where cp.gin = :gin "
			+ "and cp.subscribe = :subscribe ")
	Long getNumberOptoutOrOptinComPrefByGinAndSubscribe(@Param("gin") String gin, @Param("subscribe") String subscribe);

	@Query(value="select * from SIC2.COMMUNICATION_PREFERENCES where SGIN=:gin and DOMAIN=:domain ", nativeQuery=true)
	List<CommunicationPreferences> findComPrefIdByDomain(@Param("gin") String gin, @Param("domain") String domain);

	@Query(value = "SELECT * FROM SIC2.COMMUNICATION_PREFERENCES cp WHERE cp.DOMAIN = :domain AND cp.COM_GROUP_TYPE = :comGroupType AND cp.COM_TYPE = :comType1 AND cp.SGIN NOT IN (SELECT cp2.SGIN FROM SIC2.COMMUNICATION_PREFERENCES cp2 WHERE cp2.COM_TYPE = :comType2) AND ROWNUM <= 400000", nativeQuery=true)
	List<CommunicationPreferences> findByDomainAndComTypeAndComGroupType(@Param("domain") String domain, @Param("comGroupType") String comGroupType, @Param("comType1") String comType1, @Param("comType2") String comType2);
}
