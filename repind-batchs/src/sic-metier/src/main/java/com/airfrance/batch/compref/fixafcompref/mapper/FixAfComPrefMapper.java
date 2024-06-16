package com.airfrance.batch.compref.fixafcompref.mapper;

import com.airfrance.batch.compref.fixafcompref.enums.ComPrefFieldEnum;
import com.airfrance.batch.compref.fixafcompref.enums.MarketLanguageFieldEnum;
import com.airfrance.batch.compref.fixafcompref.helper.ValidationHelper;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.MarketLanguage;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;

import static com.airfrance.batch.utils.IConstants.BATCH_QVI;


@Service("fixAfComPrefMapper")
public class FixAfComPrefMapper implements FieldSetMapper<Individu> {

    @Autowired
    private Map<String , FieldSet> mapContext;

    @Autowired
    private ValidationHelper validationHelper;

    private static final String signature = "REPIND-2179";

    @Override
    public Individu mapFieldSet(FieldSet fieldSet){
        return addIndividu(fieldSet);
    }

    /**
     * Add Individu using FieldSet
     * @param iFieldSet contains data to be used
     */
    private Individu addIndividu(FieldSet iFieldSet){
        Individu individu = new Individu();
        addComPref(individu , iFieldSet);
        mapContext.put(validationHelper.returnUnicityCode(individu),iFieldSet);
        return individu;
    }

    /**
     * Add CommunicationPreferences to Individu using FieldSet
     * @param ioIndividu individu to feed
     * @param iFieldSet contains data to be used
     */
    private void addComPref(Individu ioIndividu , FieldSet iFieldSet){
        CommunicationPreferences communicationPreferences = feedComPref(iFieldSet);
        communicationPreferences.setDomain("S");
        communicationPreferences.setComGroupType("N");
        communicationPreferences.setComType("AF");
        communicationPreferences.setModificationDate(new Date());
        communicationPreferences.setCreationSite(BATCH_QVI);
        communicationPreferences.setModificationSite(BATCH_QVI);
        communicationPreferences.setCreationSignature(signature);
        communicationPreferences.setModificationSignature(signature);
        communicationPreferences.setSubscribe("Y");

        addMarketLangugage(communicationPreferences , iFieldSet);

        ioIndividu.setCommunicationpreferences(new HashSet<>(Arrays.asList(communicationPreferences)));
    }

    /**
     * Add MarketLanguage to CommunicationPreferences using FieldSet
     * @param ioMarkCommunicationPreferences com pref to feed
     * @param iFieldSet contains data to be used
     */
    private void addMarketLangugage(CommunicationPreferences ioMarkCommunicationPreferences , FieldSet iFieldSet){
        MarketLanguage marketLanguage = feedMarketLanguage(iFieldSet);
        ioMarkCommunicationPreferences.setMarketLanguage(new HashSet<>(Arrays.asList(marketLanguage)));
        marketLanguage.setModificationDate(new Date());
        marketLanguage.setCreationSignature(marketLanguage.getModificationSignature());
        marketLanguage.setModificationSite(BATCH_QVI);
        marketLanguage.setOptIn("Y");
    }

    private CommunicationPreferences feedComPref( FieldSet fieldSet ){
        CommunicationPreferences communicationPreferences = new CommunicationPreferences();
        for (Map.Entry<Object, Object> fieldSetEntry : fieldSet.getProperties().entrySet()) {
            if(fieldSetEntry.getKey() != null && fieldSetEntry.getValue() != null){
                Consumer<Object> consumer = ComPrefFieldEnum.mapConsumer(ComPrefFieldEnum.fromString((String) fieldSetEntry.getKey()), communicationPreferences);
                if(consumer != null){
                    consumer.accept(fieldSetEntry.getValue());
                }
            }
        }
        return communicationPreferences;
    }

    private MarketLanguage feedMarketLanguage( FieldSet fieldSet ){
        MarketLanguage marketLanguage = new MarketLanguage();
        for (Map.Entry<Object, Object> fieldSetEntry : fieldSet.getProperties().entrySet()) {
            if(fieldSetEntry.getKey() != null && fieldSetEntry.getValue() != null){
                Consumer<Object> consumer = MarketLanguageFieldEnum.mapConsumer(MarketLanguageFieldEnum.fromString((String) fieldSetEntry.getKey()), marketLanguage);
                if(consumer != null){
                    consumer.accept(fieldSetEntry.getValue());
                }
            }
        }
        return marketLanguage;
    }
}
