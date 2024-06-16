package com.afklm.repind.msv.doctor.role.utils;

import lombok.Getter;

public enum DoctorStatus {

    V("V" , "Valid");

    @Getter
    private final String acronyme;
    @Getter
    private final String value;

    DoctorStatus(String acronyme, String value) {
        this.acronyme = acronyme;
        this.value = value;
    }
}
