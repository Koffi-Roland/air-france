package com.afklm.repind.common.repository.preferences;

import com.afklm.repind.common.entity.preferences.PreferenceDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferenceDataRepository extends JpaRepository<PreferenceDataEntity, Integer> {
    List<PreferenceDataEntity> getPreferenceDataEntitiesByPreferencePreferenceId(Long preferenceId);
}
