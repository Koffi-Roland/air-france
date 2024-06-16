package com.afklm.repind.common.repository.preferences;

import com.afklm.repind.common.entity.preferences.CommunicationPreferencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunicationPreferencesRepository extends JpaRepository<CommunicationPreferencesEntity, Long> {
    List<CommunicationPreferencesEntity> getCommunicationPreferencesByIndividuGin(String gin);
}
