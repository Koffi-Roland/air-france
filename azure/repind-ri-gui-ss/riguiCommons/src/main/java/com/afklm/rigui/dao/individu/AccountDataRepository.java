package com.afklm.rigui.dao.individu;

// add not generated imports here

import com.afklm.rigui.entity.individu.AccountData;
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
public interface AccountDataRepository extends JpaRepository<AccountData, Integer> {
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

    @Query(value = "SELECT COUNT(*) FROM SIC2.ACCOUNT_DATA "
            + "WHERE SGIN!= :sgin AND EMAIL_IDENTIFIER= :emailIdentifier ", nativeQuery = true)
    int countWhereEmailIdentifierAndNotGin(@Param("sgin") String sgin, @Param("emailIdentifier") String emailIdentifier);

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

    @Transactional
    @Modifying
    @Query("delete from AccountData acd where acd.id = :id")
    void deleteById(@Param("id") Integer id);
    
    @Query("select count(acd) from AccountData acd where acd.sgin = :gin")
    int countByGin(@Param("gin") String gin);
}
