package com.airfrance.repind.dao.individu;

// add not generated imports here

import com.airfrance.repind.entity.individu.AccountData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>Title : IAccountDataDAO.java</p>
 * BO: AccountData
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Repository
public interface AccountDataRepository extends JpaRepository<AccountData, Integer>, AccountDataRepositoryCustom {

    /**
     * Get accounts to expire according to the following criteria:
     * <ul>
     * <li>status = 'V'</li>
     * <li>TODAY - 18 months <= expiredDate < TODAY - 19 months</li>
     * </ul>
     */
    @Query(value = "select * from SIC2.ACCOUNT_DATA "
            + "where FB_IDENTIFIER is null "
            + "and STATUS='V' "
            + "and ( "
            + "( LAST_SOCIAL_NETWORK_LOGON_DATE is null "
            + "and LAST_CONNECTION_DATE <= add_months(trunc(sysdate)+1,:startMonth) "
            + "and LAST_CONNECTION_DATE > add_months(trunc(sysdate)+1,:endMonth) ) "
            + "or "
            + "( LAST_CONNECTION_DATE is null "
            + "and LAST_SOCIAL_NETWORK_LOGON_DATE <= add_months(trunc(sysdate)+1,:startMonth) "
            + "and LAST_SOCIAL_NETWORK_LOGON_DATE > add_months(trunc(sysdate)+1,:endMonth) ) "
            + "or "
            + "( LAST_CONNECTION_DATE <= add_months(trunc(sysdate)+1,:startMonth) "
            + "and LAST_CONNECTION_DATE > add_months(trunc(sysdate)+1,:endMonth) "
            + "and LAST_SOCIAL_NETWORK_LOGON_DATE <= add_months(trunc(sysdate)+1,:startMonth) "
            + "and LAST_SOCIAL_NETWORK_LOGON_DATE > add_months(trunc(sysdate)+1,:endMonth) ) "
            + ") and rownum <= :limit", nativeQuery = true)
    List<AccountData> getAccountsToExpire(@Param("startMonth") Integer startMonth, @Param("endMonth") Integer endMonth, @Param("limit") long limit);

    /**
     * Get accounts to delete according to the following criteria:
     * <ul>
     * <li>status = 'V' OR status = 'E'</li>
     * <li>expiredDate <= TODAY - 19 months</li>
     * </ul>
     */
    @Query(value = "select * from SIC2.ACCOUNT_DATA "
            + "where FB_IDENTIFIER is null "
            + "and (STATUS='V' or STATUS='E') "
            + "and ( "
            + "( LAST_SOCIAL_NETWORK_LOGON_DATE is null "
            + "and LAST_CONNECTION_DATE <= add_months(trunc(sysdate)+1,:startMonth) ) "
            + "or "
            + "( LAST_CONNECTION_DATE is null "
            + "and LAST_SOCIAL_NETWORK_LOGON_DATE <= add_months(trunc(sysdate)+1,:startMonth) ) "
            + "or "
            + "( LAST_CONNECTION_DATE <= add_months(trunc(sysdate)+1,:startMonth) "
            + "and LAST_SOCIAL_NETWORK_LOGON_DATE <= add_months(trunc(sysdate)+1,:startMonth) ) "
            + ") and rownum <= :limit", nativeQuery = true)
    List<AccountData> getAccountsToDelete(@Param("startMonth") Integer startMonth, @Param("limit") long limit);

    /**
     * Get accounts to erase according to the following criteria:
     * <ul>
     * <li>status = 'D'</li>
     * <li>expiredDate <= TODAY - 1 day</li>
     * </ul>
     */
    @Query(value = "select * from SIC2.ACCOUNT_DATA "
            + "where FB_IDENTIFIER is null "
            + "and STATUS='D' "
            + "and ACCOUNT_DELETION_DATE < (sysdate-1) "
            + "and rownum <= :limit ", nativeQuery = true)
    List<AccountData> getAccountsToErase(long limit);

    /**
     * Get number of accounts to expire according to the following criteria:
     * <ul>
     * <li>status = 'V'</li>
     * <li>TODAY - 18 months <= expiredDate < TODAY - 19 months</li>
     * </ul>
     */
    @Query(value = "select count(*) from SIC2.ACCOUNT_DATA "
            + "where FB_IDENTIFIER is null "
            + "and STATUS='V' "
            + "and ( "
            + "( LAST_SOCIAL_NETWORK_LOGON_DATE is null "
            + "and LAST_CONNECTION_DATE <= add_months(trunc(sysdate)+1,:startMonth) "
            + "and LAST_CONNECTION_DATE > add_months(trunc(sysdate)+1,:endMonth) ) "
            + "or "
            + "( LAST_CONNECTION_DATE is null "
            + "and LAST_SOCIAL_NETWORK_LOGON_DATE <= add_months(trunc(sysdate)+1,:startMonth) "
            + "and LAST_SOCIAL_NETWORK_LOGON_DATE > add_months(trunc(sysdate)+1,:endMonth) ) "
            + "or "
            + "( LAST_CONNECTION_DATE <= add_months(trunc(sysdate)+1,:startMonth) "
            + "and LAST_CONNECTION_DATE > add_months(trunc(sysdate)+1,:endMonth) "
            + "and LAST_SOCIAL_NETWORK_LOGON_DATE <= add_months(trunc(sysdate)+1,:startMonth) "
            + "and LAST_SOCIAL_NETWORK_LOGON_DATE > add_months(trunc(sysdate)+1,:endMonth) ) "
            + ") ", nativeQuery = true)
    Long getNbAccountsToExpire(@Param("startMonth") Integer startMonth, @Param("endMonth") Integer endMonth);

    /**
     * Get number of accounts to delete according to the following criteria:
     * <ul>
     * <li>status = 'V' OR status = 'E'</li>
     * <li>expiredDate <= TODAY - 19 months</li>
     * </ul>
     */
    @Query(value = "select count(*) from SIC2.ACCOUNT_DATA "
            + "where FB_IDENTIFIER is null "
            + "and (STATUS='V' or STATUS='E') "
            + "and ( "
            + "( LAST_SOCIAL_NETWORK_LOGON_DATE is null "
            + "and LAST_CONNECTION_DATE <= add_months(trunc(sysdate)+1,:startMonth) ) "
            + "or "
            + "( LAST_CONNECTION_DATE is null "
            + "and LAST_SOCIAL_NETWORK_LOGON_DATE <= add_months(trunc(sysdate)+1,:startMonth) ) "
            + "or "
            + "( LAST_CONNECTION_DATE <= add_months(trunc(sysdate)+1,:startMonth) "
            + "and LAST_SOCIAL_NETWORK_LOGON_DATE <= add_months(trunc(sysdate)+1,:startMonth) ) "
            + ") ", nativeQuery = true)
    Long getNbAccountsToDelete(@Param("startMonth") Integer startMonth);

    /**
     * Get number of accounts to erase according to the following criteria:
     * <ul>
     * <li>status = 'D'</li>
     * <li>expiredDate <= TODAY - 1 day</li>
     * </ul>
     */
    @Query(value = "select count(*) from SIC2.ACCOUNT_DATA "
            + "where FB_IDENTIFIER is null "
            + "and STATUS='D' "
            + "and ACCOUNT_DELETION_DATE < (sysdate-1) ", nativeQuery = true)
    Long getNbAccountsToErase();

    /**
     * Get number of valid accounts according to the following criteria:
     * <ul>
     * <li>status = 'V'</li>
     * </ul>
     */
    @Query(value = "select count(*) from SIC2.ACCOUNT_DATA "
            + "where FB_IDENTIFIER is null "
            + "and STATUS='V' ", nativeQuery = true)
    Long getNbValidAccounts();

    /**
     * Get account by gin
     */
    AccountData findBySgin(String gin);
    
    AccountData findBySginAndStatus(String gin, String status);

    /**
     * Get email by gin
     */

    @Query(value = "select SEMAIL from ( "
            + "select * from sic2.EMAILS "
            + "where SGIN=:gin "
            + "and SSTATUT_MEDIUM='V' "
            + "ORDER BY DDATE_MODIFICATION DESC"
            + ") where ROWNUM <= 1 ", nativeQuery = true)
    String getLastEmailByGin(@Param("gin") String gin);

    @Query(value = "select u1.semail,u1.sgin , r.snumero_contrat "
            + "from SIC2.role_contrats r, sic2.account_data ad, "
            + "(WITH sel_gin AS (SELECT DISTINCT e.sgin FROM emails e "
            + "WHERE e.semail = :email "
            + "AND SSTATUT_MEDIUM IN ('I', 'V')) "
            + "SELECT m.semail,m.sain,	m.ddate_modification,	m.sgin, "
            + "ROW_NUMBER () "
            + "OVER (PARTITION BY m.sgin ORDER BY m.ddate_modification DESC) "
            + "rang "
            + "FROM emails m "
            + "WHERE m.sgin IN (SELECT sgin FROM sel_gin) and m.SSTATUT_MEDIUM IN ('I', 'V')) u1 "
            + "where u1.rang = 1 and u1.sgin=r.sgin(+) and r.stype_contrat(+)='FP' and u1.sgin=ad.sgin", nativeQuery = true)
    List<Object[]> getAProspectIndividuIdentification(@Param("email") String email);

    @Query(value = "SELECT SCODE FROM REF_ACCOUNT_TYPE", nativeQuery = true)
    List<String> getAllAccountType();

    @Query(value = "SELECT SCODE FROM REF_INT_ID_CONTEXT", nativeQuery = true)
    List<String> getAllContextInternalIdentifier();

    @Query(value = "SELECT COUNT(*) FROM SIC2.ACCOUNT_DATA "
            + "WHERE SGIN!= :sgin AND EMAIL_IDENTIFIER= :emailIdentifier ", nativeQuery = true)
    int countWhereEmailIdentifierAndNotGin(@Param("sgin") String sgin, @Param("emailIdentifier") String emailIdentifier);

    List<AccountData> findBySocialNetworkId(String socialNetworkId);

    void deleteBySgin(String gin);

    AccountData findByEmailIdentifier(String emailIdentifier);

    AccountData findByFbIdentifier(String fbIdentifier);

    /**
     * Reactivate the account data for a GIN, that mean change the status from 'D' to 'V' and update auxilaries data
     * @param sgin : Gin of customer to reactivate the Account Data
     * @param dateModification : Modification date
     * @param signatureModification : Modification signature
     * @param siteModification : Modification site
     */
    @Transactional
    @Modifying
    @Query("Update AccountData acd set acd.status = 'V', acd.accountDeletionDate = null, acd.lastConnexionDate = :dateModification, acd.signatureModification = :signatureModification, acd.dateModification = :dateModification, acd.siteModification = :siteModification where acd.sgin = :sgin")
    void reactivateAccountData(@Param("sgin") String sgin, @Param("dateModification") Date dateModification, @Param("signatureModification") String signatureModification, @Param("siteModification") String siteModification);

    @Query("select acd from AccountData acd where acd.sgin = :sgin")
    List<AccountData> findAllByGin(@Param("sgin") String sgin);

    @Query("select count(acd) from AccountData acd where acd.emailIdentifier = :email")
    int countByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("delete from AccountData acd where acd.id = :id")
    void deleteById(@Param("id") Integer id);
    
    @Query("select count(acd) from AccountData acd where acd.sgin = :gin")
    int countByGin(@Param("gin") String gin);
}
