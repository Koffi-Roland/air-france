package com.afklm.repind.msv.provide.preference.data.service;

import com.afklm.repind.common.entity.preferences.CommunicationPreferencesEntity;
import com.afklm.repind.common.entity.preferences.MarketLanguageEntity;
import com.afklm.repind.common.repository.preferences.CommunicationPreferencesRepository;
import com.afklm.repind.common.repository.preferences.MarketLanguageRepository;
import com.afklm.repind.msv.provide.preference.data.transform.CommunicationPreferenceTransform;
import com.afklm.soa.stubs.r000380.v1.model.CommunicationPreferences;
import com.afklm.soa.stubs.r000380.v1.model.CommunicationPreferencesResponse;
import com.afklm.soa.stubs.r000380.v1.model.MarketLanguage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommunicationPreferenceService {
    private CommunicationPreferencesRepository communicationPreferencesRepository;
    private MarketLanguageRepository marketLanguageRepository;
    private CommunicationPreferenceTransform communicationPreferenceTransform;

    public List<CommunicationPreferencesEntity> getCommunicationPreferencesByGin(String gin) {
        return this.communicationPreferencesRepository.getCommunicationPreferencesByIndividuGin(gin);
    }

    public List<MarketLanguageEntity> getMarketLanguageEntitiesByComPrefId(Long comPrefId) {
        return this.marketLanguageRepository.getMarketLanguageEntitiesByComPrefId(comPrefId);
    }

    public CommunicationPreferencesResponse setCommunicationPreferencesResponse(List<CommunicationPreferencesEntity> communicationPreferencesEntities) {
        List<CommunicationPreferences> communicationPreferences = new ArrayList<>();

        for (CommunicationPreferencesEntity communicationPreferencesEntity : communicationPreferencesEntities) {
            List<MarketLanguageEntity> marketLanguageEntities = getMarketLanguageEntitiesByComPrefId(communicationPreferencesEntity.getComPrefId());
            List<MarketLanguage> marketLanguages = getMarketLanguages(marketLanguageEntities);
            communicationPreferences.add(this.communicationPreferenceTransform.setCommunicationPreference(
                    communicationPreferencesEntity,marketLanguages));
        }
        return this.communicationPreferenceTransform.setCommunicationPreferenceResponse(communicationPreferences);
    }

    public List<MarketLanguage> getMarketLanguages(List<MarketLanguageEntity> marketLanguageEntities) {
        List<MarketLanguage> marketLanguages = new ArrayList<>();

        for (MarketLanguageEntity marketLanguageEntity : marketLanguageEntities) {
            MarketLanguage marketLanguage = new MarketLanguage();

            marketLanguage.setDateCreation(marketLanguageEntity.getDateCreation() == null ? null : marketLanguageEntity.getDateCreation().toInstant().atOffset(ZoneOffset.UTC));
            marketLanguage.setDateModification(marketLanguageEntity.getDateModification() == null ? null : marketLanguageEntity.getDateModification().toInstant().atOffset(ZoneOffset.UTC));
            marketLanguage.setDateOptin(marketLanguageEntity.getDateOptin() == null ? null : marketLanguageEntity.getDateOptin().toInstant().atOffset(ZoneOffset.UTC));
            marketLanguage.setLanguageCode(marketLanguageEntity.getLanguageCode());
            marketLanguage.setMarket(marketLanguageEntity.getMarket());
            marketLanguage.setMedia1(marketLanguageEntity.getMedia1());
            marketLanguage.setMedia2(marketLanguageEntity.getMedia2());
            marketLanguage.setMedia3(marketLanguageEntity.getMedia3());
            marketLanguage.setMedia4(marketLanguageEntity.getMedia4());
            marketLanguage.setMedia5(marketLanguageEntity.getMedia5());
            marketLanguage.setOptin(marketLanguageEntity.getOptin());
            marketLanguage.setSignatureCreation(marketLanguageEntity.getSignatureCreation());
            marketLanguage.setSignatureModification(marketLanguageEntity.getSignatureModification());
            marketLanguage.setSiteCreation(marketLanguageEntity.getSiteCreation());
            marketLanguage.setSiteModification(marketLanguageEntity.getSiteModification());

            marketLanguages.add(marketLanguage);
        }

        return marketLanguages;
    }
}
