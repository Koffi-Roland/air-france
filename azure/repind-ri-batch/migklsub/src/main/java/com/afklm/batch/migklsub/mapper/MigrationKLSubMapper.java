package com.afklm.batch.migklsub.mapper;

import com.afklm.batch.migklsub.enums.*;
import com.afklm.batch.migklsub.helper.ValidationHelper;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.MarketLanguage;
import com.airfrance.repind.entity.role.RoleContrats;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;

import static com.airfrance.batch.common.utils.IConstants.*;

public class MigrationKLSubMapper implements FieldSetMapper<Individu> {

    @Autowired
    private Map<String , FieldSet> mapContext;

    @Autowired
    private ValidationHelper validationHelper;

    private ModeEnum mode;

    public MigrationKLSubMapper(ModeEnum iMode) {
        mode = iMode;
    }

    @Override
    public Individu mapFieldSet(FieldSet fieldSet){
        return addIndividu(fieldSet);
    }

    private boolean isHeaderLine(Individu iIndividu){
        return IndividuFieldEnum.CIVILITY.getValue().equals(iIndividu.getCivilite());
    }

    /**
     * Add Individu using FieldSet
     * @param iFieldSet contains data to be used
     */
    private Individu addIndividu(FieldSet iFieldSet){
        Individu individu = feedIndividu(iFieldSet);
        addEmail(individu , iFieldSet);
        addRoleContrats(individu , iFieldSet);
        addComPref(individu , iFieldSet);
        mapContext.put(validationHelper.returnUnicityCode(individu),iFieldSet);
        return individu;
    }

    /**
     * Add Email to Individu using FieldSet
     * @param ioIndividu individu to feed
     * @param iFieldSet contains data to be used
     */
    private void addEmail(Individu ioIndividu , FieldSet iFieldSet){
        Email email = feedEmail(iFieldSet);
        ioIndividu.setEmail(new HashSet<>(Arrays.asList(email)));
    }

    /**
     * Add RoleContrats to Individu using FieldSet
     * @param ioIndividu individu to feed
     * @param iFieldSet contains data to be used
     */
    private void addRoleContrats(Individu ioIndividu , FieldSet iFieldSet){
        RoleContrats roleContrats = feedRolesContrats(iFieldSet);
        ioIndividu.setRolecontrats(new HashSet<>(Arrays.asList(roleContrats)));
    }

    /**
     * Add CommunicationPreferences to Individu using FieldSet
     * @param ioIndividu individu to feed
     * @param iFieldSet contains data to be used
     */
    private void addComPref(Individu ioIndividu , FieldSet iFieldSet){
        CommunicationPreferences communicationPreferences = feedComPref(iFieldSet);
        communicationPreferences.setDomain("S");
        communicationPreferences.setComGroupType(communicationPreferences.getComType().equals(KL_CODE) ? "N" : "M");
        communicationPreferences.setCreationDate(new Date());
        communicationPreferences.setModificationDate(new Date());
        communicationPreferences.setCreationSite(BATCH_QVI);
        communicationPreferences.setModificationSite(BATCH_QVI);
        if(ModeEnum.INIT.equals(mode)){
            communicationPreferences.setCreationSignature(CSM_WW);
            communicationPreferences.setModificationSignature(CSM_WW);
        }else{
            communicationPreferences.setCreationSignature(communicationPreferences.getModificationSignature());
        }


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
        marketLanguage.setCreationDate(new Date());
        marketLanguage.setModificationDate(new Date());
        marketLanguage.setCreationSignature(marketLanguage.getModificationSignature());
        marketLanguage.setModificationSite(BATCH_QVI);
    }

    private Individu feedIndividu( FieldSet fieldSet ){
        Individu individu = new Individu();
        for (Map.Entry<Object, Object> fieldSetEntry : fieldSet.getProperties().entrySet()) {
            if(fieldSetEntry.getKey() != null && fieldSetEntry.getValue() != null){
                Consumer<Object> consumer = IndividuFieldEnum.mapConsumer(IndividuFieldEnum.fromString((String) fieldSetEntry.getKey()), individu);
                if(consumer != null){
                    consumer.accept(fieldSetEntry.getValue());
                }
            }
        }
        return individu;
    }

    private Email feedEmail( FieldSet fieldSet ){
        Email email = new Email();
        for (Map.Entry<Object, Object> fieldSetEntry : fieldSet.getProperties().entrySet()) {
            if(fieldSetEntry.getKey() != null && fieldSetEntry.getValue() != null){
                Consumer<Object> consumer = EmailFieldEnum.mapConsumer(EmailFieldEnum.fromString((String) fieldSetEntry.getKey()), email);
                if(consumer != null){
                    consumer.accept(fieldSetEntry.getValue());
                }
            }
        }
        return email;
    }

    private RoleContrats feedRolesContrats( FieldSet fieldSet ){
        RoleContrats roleContrats = new RoleContrats();
        for (Map.Entry<Object, Object> fieldSetEntry : fieldSet.getProperties().entrySet()) {
            if(fieldSetEntry.getKey() != null && fieldSetEntry.getValue() != null){
                Consumer<Object> consumer = RoleContratsFieldEnum.mapConsumer(RoleContratsFieldEnum.fromString((String) fieldSetEntry.getKey()), roleContrats);
                if(consumer != null){
                    consumer.accept(fieldSetEntry.getValue());
                }
            }
        }
        return roleContrats;
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
