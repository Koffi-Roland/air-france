package com.afklm.repind.msv.provide.individual.score.enumeration;

/**
 * Enum the constant variables that this MS uses
 */
public enum Variables {
    RC("RC"),
    DP("DP"),
    RP("RP"),
    PNM("PNM_ID"),
    F("F"),
    Y("Y"),
    P("P"),
    S("S"),
    KL("KL"),
    AF("AF"),
    TM("TM"),
    UF("UF"),

    ;

    private final String name;

    Variables(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
