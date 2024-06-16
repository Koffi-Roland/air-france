package com.airfrance.batch.compref.fixafcompref.enums;

import com.airfrance.repind.entity.individu.CommunicationPreferences;

import java.util.Arrays;
import java.util.function.Consumer;

public enum ComPrefFieldEnum {
    GIN ( "GIN_IDV"  );

    private String value;

    ComPrefFieldEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ComPrefFieldEnum fromString(String iValue){
        return Arrays.stream(ComPrefFieldEnum.values()).filter(x -> x.getValue().equals(iValue)).findFirst().orElse(null);
    }

    public static Consumer<Object> mapConsumer(ComPrefFieldEnum iComPrefFieldEnum , CommunicationPreferences ioCompPref){
        if(ioCompPref != null && iComPrefFieldEnum != null){
            switch (iComPrefFieldEnum){
                case GIN:
                    return (x) -> ioCompPref.setGin((String)x);
                default:
                    return null;
            }
        }
        return null;
    }
}
