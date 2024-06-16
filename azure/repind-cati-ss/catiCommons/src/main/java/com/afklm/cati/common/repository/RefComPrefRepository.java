package com.afklm.cati.common.repository;

import com.afklm.cati.common.entity.RefComPref;
import com.afklm.cati.common.entity.RefComPrefMedia;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.repository.custom.RefComPrefRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefComPrefRepository extends JpaRepository<RefComPref, Integer>, RefComPrefRepositoryCustom {
    /**
     * Getting Market by code pays
     *
     * @return
     */
    @Query("select compref from RefComPref compref, RefComPrefDomain domain "
            + "where compref.market= :market "
            + "and  domain.codeDomain=compref.domain "
            + "and  domain.codeDomain= :domain ")
    List<RefComPref> findComPerfByMarket(@Param("market") String market, @Param("domain") String domain);

    /**
     * @return
     */
    @Query("select compref from RefComPref compref, RefComPrefDomain domain, RefComPrefType comPrefType, RefComPrefGType comPrefGType "
            + "where compref.market= :market "
            + "and  domain.codeDomain=compref.domain "
            + "and  domain.codeDomain= :domain "
            + "and  comPrefGType.codeGType is not null "
            + "and  comPrefGType.codeGType=compref.comGroupeType.codeGType "
            + "and  comPrefType.codeType=compref.comType.codeType "
            + "and  comPrefType.codeType= :type ")
    List<RefComPref> findComPerfByMarketComType(@Param("market") String market, @Param("domain") String domain, @Param("type") String comType);

    /**
     * Getting Market by code pays and CompType
     *
     * @return
     */
    @Query("select compref from RefComPref compref, RefComPrefDomain domain, RefComPrefType comPrefType, RefComPrefGType comPrefGType "
            + "where compref.market in (:market , :marketWorld) "
            + "and  domain.codeDomain=compref.domain "
            + "and  domain.codeDomain= :domain "
            + "and  comPrefType.codeType=compref.comType.codeType "
            + "and  comPrefGType.codeGType is not null "
            + "and  comPrefGType.codeGType=compref.comGroupeType.codeGType "
            + "and  compref.comType.codeType not in ( "
            + "select compref.comType.codeType from RefComPref compref, RefComPrefDomain domain, RefComPrefType comPrefType "
            + "where compref.market= :market "
            + "and  domain.codeDomain=compref.domain "
            + "and  domain.codeDomain= :domain "
            + "and  comPrefType.codeType= :type "
            + "and  comPrefGType.codeGType is not null "
            + "and  comPrefGType.codeGType=compref.comGroupeType.codeGType "
            + "and  comPrefType.codeType=compref.comType.codeType )")
    List<RefComPref> findComPerfByAllMarkets(@Param("market") String market, @Param("domain") String domain, @Param("type") String comType, @Param("marketWorld") String marketWorld);

    @Query("select compref from RefComPref compref, RefComPrefDomain domain, RefComPrefType comPrefType, RefComPrefGType comPrefGType "
            + "where compref.market in (:market , :marketWorld) "
            + "and  domain.codeDomain=compref.domain "
            + "and  domain.codeDomain= :domain "
            + "and  comPrefType.codeType=compref.comType.codeType "
            + "and  comPrefType.codeType= :type "
            + "and  comPrefGType.codeGType is not null "
            + "and  comPrefGType.codeGType=compref.comGroupeType.codeGType")
    List<RefComPref> findComPrefByAllMarketsAndComType(@Param("market") String market, @Param("domain") String domain, @Param("type") String comType, @Param("marketWorld") String marketWorld);

    @Query("select compref from RefComPref compref, RefComPrefDomain domain, RefComPrefType comPrefType, RefComPrefGType comPrefGType "
            + "where compref.market= :market "
            + "and  domain.codeDomain=compref.domain "
            + "and  domain.codeDomain= :domain "
            + "and  comPrefType.codeType=compref.comType.codeType "
            + "and  comPrefType.codeType= :type "
            + "and  comPrefGType.codeGType=compref.comGroupeType.codeGType "
            + "and  comPrefGType.codeGType= :group "
            + "and  (compref.defaultLanguage1= :language "
            + "or compref.defaultLanguage2= :language "
            + "or compref.defaultLanguage3= :language "
            + "or compref.defaultLanguage4= :language "
            + "or compref.defaultLanguage5= :language "
            + "or compref.defaultLanguage6= :language "
            + "or compref.defaultLanguage7= :language "
            + "or compref.defaultLanguage8= :language "
            + "or compref.defaultLanguage9= :language "
            + "or compref.defaultLanguage10= :language )")
    List<RefComPref> findComPrefByMarketComTypeLanguage(@Param("market") String market, @Param("domain") String domain, @Param("type") String comType, @Param("group") String comGroupType, @Param("language") String language) throws JrafDaoException;

    @Query("select compref from RefComPref compref, RefComPrefDomain domain, RefComPrefType comPrefType, RefComPrefGType comPrefGType "
            + "where  domain.codeDomain=compref.domain "
            + "and  domain.codeDomain= :domain "
            + "and  comPrefType.codeType=compref.comType.codeType "
            + "and  comPrefType.codeType= :type "
            + "and  comPrefGType.codeGType=compref.comGroupeType.codeGType "
            + "and  comPrefGType.codeGType= :group ")
    List<RefComPref> findComPrefByDomainComTypeComGroupType(@Param("domain") String domain, @Param("type") String comType, @Param("group") String comGroupType) throws JrafDaoException;

    @Query("select compref from RefComPref compref, RefComPrefDomain domain, RefComPrefType comPrefType, RefComPrefGType comPrefGType "
            + "where compref.market= :market "
            + "and  domain.codeDomain=compref.domain "
            + "and  domain.codeDomain= :domain "
            + "and  comPrefType.codeType=compref.comType.codeType "
            + "and  comPrefType.codeType= :type "
            + "and  comPrefGType.codeGType=compref.comGroupeType.codeGType "
            + "and  comPrefGType.codeGType= :group ")
    List<RefComPref> findComPerfByMarketComTypeComGType(@Param("market") String market, @Param("domain") String domain, @Param("type") String comType, @Param("group") String comGType) throws JrafDaoException;

    @Query("select max(ref.refComprefId) from RefComPref ref")
    Integer getMaxId();

    Long countByMedia(RefComPrefMedia media);

    List<RefComPref> findAllByOrderByRefComprefId();

}
