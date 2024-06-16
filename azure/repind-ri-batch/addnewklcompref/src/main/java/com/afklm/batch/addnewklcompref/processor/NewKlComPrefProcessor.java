package com.afklm.batch.addnewklcompref.processor;

import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.individu.MarketLanguageRepository;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.MarketLanguage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class NewKlComPrefProcessor implements ItemProcessor<CommunicationPreferences, CommunicationPreferences> {

    @Autowired
    private CommunicationPreferencesRepository communicationPreferencesRepository;

    @Autowired
    private MarketLanguageRepository marketLanguagesRepository;

    private final static String COM_TYPE = "KL_PART";
    private final static String MODIFICATION_SITE = "REPIN-2564";
    private final static String MODIFICATION_SIGNATURE = "KL_PART_INIT";


    @Override
    public CommunicationPreferences process(CommunicationPreferences communicationPreferences) throws Exception {
        log.info("\nCompref to copy: " +
                "\n --> GIN : " +communicationPreferences.getGin()+
                "\n --> ComprefID : " +communicationPreferences.getComPrefId());
        // Create a copy of CommunicationPreferences entity
        CommunicationPreferences newCommunicationPreferences = createNewKLComPref(communicationPreferences);

        // Set the new MarketLanguage entity to the new CommunicationPreferences entity
        newCommunicationPreferences.setMarketLanguage(createNewMarketLanguage(communicationPreferences, newCommunicationPreferences.getComPrefId()));

        // Save the updated New CommunicationPreferences entity
        communicationPreferencesRepository.save(newCommunicationPreferences);

        // Return the new CommunicationPreferences entity as the processed item
        return newCommunicationPreferences;
    }

    @Transactional
  public  CommunicationPreferences createNewKLComPref(CommunicationPreferences communicationPreferences){

        // Create a copy of CommunicationPreferences entity
        CommunicationPreferences newCommunicationPreferences = new CommunicationPreferences(
                null, communicationPreferences.getAccountIdentifier(),
                communicationPreferences.getGin(),
                communicationPreferences.getDomain(),
                communicationPreferences.getComGroupType(),
                COM_TYPE,
                communicationPreferences.getMedia1(),
                communicationPreferences.getMedia2(),
                communicationPreferences.getMedia3(),
                communicationPreferences.getMedia4(),
                communicationPreferences.getMedia5(),
                communicationPreferences.getSubscribe(),
                communicationPreferences.getCreationDate(),
                communicationPreferences.getDateOptin(),
                communicationPreferences.getDateOptinPartners(),
                communicationPreferences.getDateOfEntry(),
                new Date(),
                MODIFICATION_SIGNATURE,
                MODIFICATION_SITE,
                communicationPreferences.getOptinPartners(),
                communicationPreferences.getCreationSignature(),
                communicationPreferences.getCreationSite(),
                communicationPreferences.getChannel()
        );

        return communicationPreferencesRepository.save(newCommunicationPreferences);
    }

    @Transactional
   public Set<MarketLanguage> createNewMarketLanguage(CommunicationPreferences communicationPreferences, Integer comPrefId){

        Set<MarketLanguage> newMarketLanguageSet = new HashSet<>();

        // Create a copy of MarketLanguage entity associated with the original CommunicationPreferences entity
        Set<MarketLanguage> marketLanguageSet = communicationPreferences.getMarketLanguage();
        for(MarketLanguage marketLanguage : marketLanguageSet){
            if (marketLanguage != null) {
                MarketLanguage newMarketLanguage = new MarketLanguage(
                        null,
                        comPrefId,
                        marketLanguage.getMarket(),
                        marketLanguage.getLanguage(),
                        marketLanguage.getOptIn(),
                        marketLanguage.getDateOfConsent(),
                        marketLanguage.getCommunicationMedia1(),
                        marketLanguage.getCommunicationMedia2(),
                        marketLanguage.getCommunicationMedia3(),
                        marketLanguage.getCommunicationMedia4(),
                        marketLanguage.getCommunicationMedia5(),
                        marketLanguage.getCreationDate(),
                        marketLanguage.getCreationSignature(),
                        marketLanguage.getCreationSite(),
                        new Date(),
                        MODIFICATION_SIGNATURE,
                        MODIFICATION_SITE
                );

                // Save the new MarketLanguage entity using Spring Data JPA
                newMarketLanguage = marketLanguagesRepository.save(newMarketLanguage);
                //add new ML to the SET
                newMarketLanguageSet.add(newMarketLanguage);
            }
        }

        return newMarketLanguageSet;

    }
}
