package com.afklm.repind.msv.doctor.attributes.utils;

import lombok.Getter;

public enum ESource implements IAttributesEnum {
    CBS("CBS"),
    CAPI("CAPI" );

    @Getter private final String value;

    ESource( String value) {
        this.value = value;
    }

    public static ESource contains(String iValue){
        if(iValue != null){
            for (ESource value : ESource.values()) {
                if(value.getValue().equalsIgnoreCase(iValue)){
                    return value;
                }
            }
        }
        return null;
    }
}
