package com.afklm.repind.common.repository.preferences;

import com.afklm.repind.common.entity.preferences.CommunicationPreferencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunicationPreferencesRepository extends JpaRepository<CommunicationPreferencesEntity, Long> {
    List<CommunicationPreferencesEntity> getCommunicationPreferencesByIndividuGin(String gin);

    CommunicationPreferencesEntity getComPrefByComPrefId(Long comprefId);

    /*
      RULES CustomCAD:
        -- No Market for subscription
        -- No language for subscription
     */
    @Query("SELECT CASE WHEN COUNT(cp) > 0 THEN false ELSE true END FROM CommunicationPreferencesEntity cp "
            + "where cp.individu.gin = :gin and cp.comType in ('KL','KL_PART') and exists (SELECT 1 FROM MarketLanguageEntity ml" +
            "        where ml.comPrefId = cp.comPrefId" +
            "        and (ml.market is not null OR ml.languageCode is not null)" +
            ")")
    boolean checkComPrefMarketLanguageNotExist(@Param("gin") String gin);

    /*
    RULES CustomCAD:
      -- No KL subscription (type 'KL' or 'KL_PART') where optin value 'Y'
    */
    @Query("SELECT CASE WHEN COUNT(cp) > 0 THEN false ELSE true END FROM CommunicationPreferencesEntity cp " +
            "where cp.individu.gin = :gin and cp.comType in ('KL','KL_PART')" +
            " and exists (SELECT 1 FROM MarketLanguageEntity ml" +
            "        where ml.comPrefId = cp.comPrefId" +
            "        and ml.optin='Y')")
    boolean checkIfGinIsNotOptin(@Param("gin") String gin);
}