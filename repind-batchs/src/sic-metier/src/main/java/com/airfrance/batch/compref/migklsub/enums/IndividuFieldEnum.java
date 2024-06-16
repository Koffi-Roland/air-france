package com.airfrance.batch.compref.migklsub.enums;

import com.airfrance.batch.utils.SicUtils;
import com.airfrance.repind.entity.individu.Individu;

import java.util.Arrays;
import java.util.function.Consumer;

import static com.airfrance.batch.utils.IConstants.DATE_FORMATTER;
public enum IndividuFieldEnum {
    SF_KEY ( "SF_Key"),
    GIN ( "GIN"  ),
    FIRST_NAME ( "Firstname" ),
    SURNAME ( "Surname" ),
    CIVILITY ( "Civility" ),
    BIRTHDATE ( "Birthdate" );

    private String value;

    IndividuFieldEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static IndividuFieldEnum fromString(String iValue){
        return Arrays.stream(IndividuFieldEnum.values()).filter(x -> x.getValue().equals(iValue)).findFirst().orElse(null);
    }

    public static Consumer<Object> mapConsumer(IndividuFieldEnum individuFieldEnum , Individu ioIndividu){
        if(ioIndividu != null && individuFieldEnum != null){
            switch (individuFieldEnum){
                case FIRST_NAME:
                    return (x) -> ioIndividu.setPrenom((String) x);
                case SURNAME:
                    return (x) -> ioIndividu.setNom((String) x);
                case CIVILITY:
                    return (x) -> ioIndividu.setCivilite((String) x);
                case BIRTHDATE:
                    return (x) -> ioIndividu.setDateNaissance(SicUtils.encodeDate(DATE_FORMATTER , (String)x));
                default:
                    return null;
            }
        }
        return null;
    }
}
