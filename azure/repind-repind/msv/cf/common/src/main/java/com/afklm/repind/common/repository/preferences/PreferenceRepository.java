package com.afklm.repind.common.repository.preferences;

import com.afklm.repind.common.entity.preferences.PreferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PreferenceRepository extends JpaRepository<PreferenceEntity, Long> {
    List<PreferenceEntity> getPreferenceEntitiesByIndividuGin(String gin);

    void deleteByPreferenceIdIn(List<Long> prefToDelete);
}
