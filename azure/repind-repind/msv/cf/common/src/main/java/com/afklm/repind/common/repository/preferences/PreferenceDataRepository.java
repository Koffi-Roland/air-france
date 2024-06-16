package com.afklm.repind.common.repository.preferences;

import com.afklm.repind.common.entity.preferences.PreferenceDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferenceDataRepository extends JpaRepository<PreferenceDataEntity, Integer> {
    List<PreferenceDataEntity> getPreferenceDataEntitiesByPreferencePreferenceId(Long preferenceId);

    @Query("SELECT pfd FROM PreferenceDataEntity pfd where pfd.preference.preferenceId= :preferenceId")
    List<PreferenceDataEntity> getPreferenceDataEntitiesByPreferenceId(@Param("preferenceId") Long preferenceId);

    @Modifying
    @Query(value = "DELETE FROM PREFERENCE_DATA WHERE PREFERENCE_ID in :ids", nativeQuery = true)
    void deleteByPreferenceIdIn(@Param("ids") List<Long> preferenceIds);
}
