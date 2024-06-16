package com.afklm.repind.msv.provide.preference.data.transform;


import com.afklm.repind.common.entity.preferences.PreferenceEntity;
import com.afklm.soa.stubs.r000380.v1.model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.List;
@Component
@AllArgsConstructor
public class PreferenceTransform {

    public PreferenceResponse setPreferenceResponse(List<Preference> preferences){
        PreferenceResponse preferenceResponse = new PreferenceResponse();
        preferenceResponse.setPreferences(preferences);
        return preferenceResponse;
    }

    public Preference setPreference(PreferenceEntity preferenceEntity, List<PreferenceData> preferenceData){

        Preference preference = new Preference();

        preference.setDateCreation(preferenceEntity.getDateCreation() == null ? null : preferenceEntity.getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        preference.setDateModification(preferenceEntity.getDateModification() == null ? null : preferenceEntity.getDateModification().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        preference.setId(preferenceEntity.getPreferenceId());
        preference.setLink(preferenceEntity.getLink());
        preference.setPreferenceData(preferenceData);
        preference.setSignatureCreation(preferenceEntity.getSignatureCreation());
        preference.setSignatureModification(preferenceEntity.getSignatureModification());
        preference.setSiteCreation(preferenceEntity.getSiteCreation());
        preference.setSiteModification(preferenceEntity.getSiteModification());
        preference.setType(preferenceEntity.getType());

        return preference;
    }

}
