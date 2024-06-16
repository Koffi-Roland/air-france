package com.afklm.repind.msv.provide.preference.data.service;

import com.afklm.repind.common.entity.preferences.PreferenceDataEntity;
import com.afklm.repind.common.entity.preferences.PreferenceEntity;
import com.afklm.repind.common.repository.preferences.PreferenceDataRepository;
import com.afklm.repind.common.repository.preferences.PreferenceRepository;
import com.afklm.repind.msv.provide.preference.data.transform.PreferenceTransform;
import com.afklm.soa.stubs.r000380.v1.model.Preference;
import com.afklm.soa.stubs.r000380.v1.model.PreferenceData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.afklm.soa.stubs.r000380.v1.model.PreferenceResponse;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PreferenceService {
    private PreferenceRepository preferenceRepository;
    private PreferenceDataRepository preferenceDataRepository;
    private PreferenceTransform preferenceTransform;

    public List<PreferenceEntity> getPreferenceEntitiesByGin(String gin){
        return this.preferenceRepository.getPreferenceEntitiesByIndividuGin(gin);
    }

    public List<PreferenceDataEntity> getPreferenceDataEntitiesByPreferencePreferenceId(Long preferenceId){
        return this.preferenceDataRepository.getPreferenceDataEntitiesByPreferencePreferenceId(preferenceId);
    }

    public PreferenceResponse setPreferenceResponse(List<PreferenceEntity> preferenceEntities) {
        List<Preference> preferences = new ArrayList<>();

        for (PreferenceEntity preferenceEntity : preferenceEntities) {
            List<PreferenceDataEntity> preferenceDataEntities = getPreferenceDataEntitiesByPreferencePreferenceId(preferenceEntity.getPreferenceId());
            List<PreferenceData> preferenceData = getPreferenceData(preferenceDataEntities);
            preferences.add(this.preferenceTransform.setPreference(preferenceEntity, preferenceData));
        }
        return this.preferenceTransform.setPreferenceResponse(preferences);
    }

    public List<PreferenceData> getPreferenceData(List<PreferenceDataEntity> preferenceDataEntities){
        List<PreferenceData> preferenceDatas = new ArrayList<>();

        for (PreferenceDataEntity preferenceDataEntity : preferenceDataEntities){
            PreferenceData preferenceData = new PreferenceData();

            preferenceData.setDateCreation(preferenceDataEntity.getDateCreation() == null ? null : preferenceDataEntity.getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            preferenceData.setDateModification(preferenceDataEntity.getDateModification() == null ? null : preferenceDataEntity.getDateModification().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            preferenceData.setKey(preferenceDataEntity.getKey());
            preferenceData.setSignatureCreation(preferenceDataEntity.getSignatureCreation());
            preferenceData.setSignatureModification(preferenceDataEntity.getSignatureModification());
            preferenceData.setSiteCreation(preferenceDataEntity.getSiteCreation());
            preferenceData.setSiteModification(preferenceDataEntity.getSiteModification());
            preferenceData.setValue(preferenceDataEntity.getValue());

            preferenceDatas.add(preferenceData);
        }

        return preferenceDatas;
    }
}
