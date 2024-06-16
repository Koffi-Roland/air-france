package com.afklm.repind.msv.provide.preference.data.transform;

import com.afklm.repind.common.entity.preferences.CommunicationPreferencesEntity;
import com.afklm.soa.stubs.r000380.v1.model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.List;
@Component
@AllArgsConstructor
public class CommunicationPreferenceTransform {

    public CommunicationPreferencesResponse setCommunicationPreferenceResponse(List<CommunicationPreferences> communicationPreferences){
        CommunicationPreferencesResponse communicationPreferencesResponse = new CommunicationPreferencesResponse();
        communicationPreferencesResponse.setCommunicationPreferences(communicationPreferences);
        return communicationPreferencesResponse;
    }

    public CommunicationPreferences setCommunicationPreference(CommunicationPreferencesEntity communicationPreferencesEntity, List<MarketLanguage> marketLanguages){

        CommunicationPreferences communicationPreference = new CommunicationPreferences();

        communicationPreference.setChannel(communicationPreferencesEntity.getChannel());
        communicationPreference.setComGroupType(communicationPreferencesEntity.getComGroupType());
        communicationPreference.setComType(communicationPreferencesEntity.getComType());
        communicationPreference.setDateCreation(communicationPreferencesEntity.getDateCreation().toInstant().atOffset(ZoneOffset.UTC));
        communicationPreference.setDateEntry(communicationPreferencesEntity.getDateEntry() == null ? null : communicationPreferencesEntity.getDateEntry().toInstant().atOffset(ZoneOffset.UTC));
        communicationPreference.setDateModification(communicationPreferencesEntity.getDateModification() == null ? null : communicationPreferencesEntity.getDateModification().toInstant().atOffset(ZoneOffset.UTC));
        communicationPreference.setDateOptin(communicationPreferencesEntity.getDateOptin() == null ? null : communicationPreferencesEntity.getDateOptin().toInstant().atOffset(ZoneOffset.UTC));
        communicationPreference.setDateOptinPartners(communicationPreferencesEntity.getDateOptinPartners() == null ? null : communicationPreferencesEntity.getDateOptinPartners().toInstant().atOffset(ZoneOffset.UTC));
        communicationPreference.setDomain(communicationPreferencesEntity.getDomain());
        communicationPreference.setMarketLanguages(marketLanguages);
        communicationPreference.setMedia1(communicationPreferencesEntity.getMedia1());
        communicationPreference.setMedia2(communicationPreferencesEntity.getMedia2());
        communicationPreference.setMedia3(communicationPreferencesEntity.getMedia3());
        communicationPreference.setMedia4(communicationPreferencesEntity.getMedia4());
        communicationPreference.setMedia5(communicationPreferencesEntity.getMedia5());
        communicationPreference.setOptinPartners(communicationPreferencesEntity.getOptinPartners());
        communicationPreference.setSignatureCreation(communicationPreferencesEntity.getSignatureCreation());
        communicationPreference.setSignatureModification(communicationPreferencesEntity.getSignatureModification());
        communicationPreference.setSiteCreation(communicationPreferencesEntity.getSiteCreation());
        communicationPreference.setSiteModification(communicationPreferencesEntity.getSiteModification());
        communicationPreference.setSubscribe(communicationPreferencesEntity.getSubscribe());

        return communicationPreference;
    }
}