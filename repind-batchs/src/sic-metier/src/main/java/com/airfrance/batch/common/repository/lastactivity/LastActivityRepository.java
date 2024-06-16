package com.airfrance.batch.common.repository.lastactivity;


import com.airfrance.batch.common.entity.lastactivity.LastActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Last activity repository
 */
@Repository
public interface LastActivityRepository extends JpaRepository<LastActivity, Long> {

    /**
     * Find the last activity according to the given gin
     *
     * @param gin Individual number
     * @return Optional last activity (LastActivity)
     */
    Optional<LastActivity> findByGin(String gin);

    /**
     * Update last activity by query
     *
     * @param dateModification      date modification
     * @param siteModification      site modification
     * @param sourceModification    source modification
     * @param signatureModification signature modification
     * @param gin                   individual identity number
     */
    @Modifying
    @Query("UPDATE LastActivity L  SET L.dateModification =:dateModification, L.siteModification=:siteModification, L.signatureModification=:signatureModification,L.sourceModification=:sourceModification WHERE L.gin =:gin")
    void updateLastActivity(@Param("dateModification") Date dateModification, @Param("siteModification") String siteModification, @Param("sourceModification") String sourceModification, @Param("signatureModification") String signatureModification, @Param("gin") String gin);

    /**
     * Find last activity from others referenced table(Adr_Post, Email, telecoms.....)
     *
     * @param gin individula number
     * @return list of last activity
     */
    @Query(
            value = "WITH LAST_ACTIVITY_BATCH  AS (\n" +
                    "SELECT SGIN, DDATE_MODIFICATION AS DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_MODIFICATION , 'ADR_POST' AS SSOURCE_MODIFICATION\n" +
                    "FROM ADR_POST \n" +
                    "UNION ALL\n" +
                    "SELECT SGIN, DDATE_MODIFICATION AS DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_MODIFICATION, 'EMAILS' AS SSOURCE_MODIFICATION\n" +
                    "FROM EMAILS \n" +
                    "UNION ALL\n" +
                    "SELECT SGIN, DDATE_MODIFICATION AS DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_MODIFICATION, 'TELECOMS' AS SSOURCE_MODIFICATION\n" +
                    "FROM TELECOMS\n" +
                    "UNION ALL\n" +
                    "SELECT SGIN, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_MODIFICATION, 'HANDICAP' AS SSOURCE_MODIFICATION\n" +
                    "FROM HANDICAP\n" +
                    "UNION ALL\n" +
                    "SELECT SGIN, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_MODIFICATION, 'PREFERENCE' AS SSOURCE_MODIFICATION\n" +
                    "FROM PREFERENCE\n" +
                    "UNION ALL\n" +
                    "SELECT SGIN, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_MODIFICATION, 'ROLE_CONTRATS' AS SSOURCE_MODIFICATION\n" +
                    "FROM ROLE_CONTRATS\n" +
                    "UNION ALL\n" +
                    "SELECT SGIN, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_MODIFICATION, 'ROLE_UCCR' AS SSOURCE_MODIFICATION\n" +
                    "FROM ROLE_UCCR\n" +
                    "UNION ALL\n" +
                    "SELECT SGIN, MODIFICATION_DATE AS DDATE_MODIFICATION,  MODIFICATION_SIGNATURE AS SSIGNATURE_MODIFICATION, MODIFICATION_SITE AS SSITE_MODIFICATION, 'COMMUNICATION_PREFERENCES' AS SSOURCE_MODIFICATION\n" +
                    "FROM COMMUNICATION_PREFERENCES\n" +
                    "UNION ALL\n" +
                    "SELECT SGIN_DELEGATOR AS SGIN, MODIFICATION_DATE AS DDATE_MODIFICATION,  MODIFICATION_SIGNATURE AS SSIGNATURE_MODIFICATION, MODIFICATION_SITE AS SSITE_MODIFICATION, 'DELEGATION_DATA' AS SSOURCE_MODIFICATION\n" +
                    "FROM DELEGATION_DATA\n" +
                    "UNION ALL\n" +
                    "SELECT SGIN, MODIFICATION_DATE AS DDATE_MODIFICATION,  MODIFICATION_SIGNATURE AS SSIGNATURE_MODIFICATION, MODIFICATION_SITE AS SSITE_MODIFICATION, 'EXTERNAL_IDENTIFIER' AS SSOURCE_MODIFICATION\n" +
                    "FROM EXTERNAL_IDENTIFIER\n" +
                    ")\n" +
                    "\n" +
                    "SELECT dbms_random.value(1,999999999999) AS LAST_ACTIVITY_ID, SGIN, max(DDATE_MODIFICATION) AS DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_MODIFICATION ,SSOURCE_MODIFICATION\n" +
                    "FROM LAST_ACTIVITY_BATCH \n" +
                    "WHERE  SGIN = :gin AND DDATE_MODIFICATION IS NOT NULL\n" +
                    "GROUP BY SGIN, SSIGNATURE_MODIFICATION, SSITE_MODIFICATION,DDATE_MODIFICATION,SSOURCE_MODIFICATION",
            nativeQuery = true)
    List<LastActivity> findLastActivityByOthers(@Param("gin") String gin);

}
