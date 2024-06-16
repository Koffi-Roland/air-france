package com.afklm.repind.msv.provide.contact.data.utils;

import lombok.Getter;

public enum StatusToKeep {

    V("V" , "Valid"),
    I("I" , "Invalid");

    @Getter
    private final String acronyme;
    @Getter
    private final String value;

    StatusToKeep(String acronyme, String value) {
        this.acronyme = acronyme;
        this.value = value;
    }
}
