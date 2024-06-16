package com.afklm.repind.msv.doctor.attributes.utils;

import lombok.Getter;

public enum ELanguage implements IAttributesEnum {
    ENGLISH("EN" , "EN"),
    FRENCH("FR" , "FR"),
    NETHERLANDS("NL" , "NL"),
    OTHER("OT" , "OT");

    @Getter private final String acronyme;
    @Getter private final String value;

    ELanguage(String acronyme, String value) {
        this.acronyme = acronyme;
        this.value = value;
    }

    public static ELanguage contains(String iValue){
        if(iValue != null){
            for (ELanguage value : ELanguage.values()) {
                if(value.getValue().equalsIgnoreCase(iValue)){
                    return value;
                }
            }
        }
        return null;
    }
}
