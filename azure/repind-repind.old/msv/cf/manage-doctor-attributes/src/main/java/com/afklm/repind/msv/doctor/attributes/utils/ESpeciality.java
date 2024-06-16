package com.afklm.repind.msv.doctor.attributes.utils;

import lombok.Getter;

public enum ESpeciality implements IAttributesEnum {
    ANESTHESIST("ANES"),
    CARDIOLOGIST("CARD"),
    GYNECOLOGIST("GYNE"),
    INTERNIST("INTN"),
    NEUROLOGIST("NEUR"),
    DOCTOR_FIRST_AID("DOFA"),
    PULMOLOGIST("PULM"),
    SURGEON("SURG"),
    UROLOGIST("UROL"),
    GENERAL_PRACTITIONER("GENP"),
    OTHER("OTHR");


    @Getter private final String value;

    ESpeciality(String value) {
        this.value = value;
    }

    public static ESpeciality contains(String iValue){
        if(iValue != null){
            for (ESpeciality value : ESpeciality.values()) {
                if(value.getValue().equalsIgnoreCase(iValue)){
                    return value;
                }
            }
        }
        return null;
    }
}
