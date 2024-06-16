package com.afklm.repind.msv.doctor.attributes.utils;

import lombok.Getter;

public enum DoctorStatus {

    V("V" , "Valid"),
    X("X" , "Suppressed");

    @Getter
    private final String acronyme;
    @Getter
    private final String value;

    DoctorStatus(String acronyme, String value) {
        this.acronyme = acronyme;
        this.value = value;
    }
}
