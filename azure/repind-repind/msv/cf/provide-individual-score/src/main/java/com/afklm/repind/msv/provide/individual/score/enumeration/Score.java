package com.afklm.repind.msv.provide.individual.score.enumeration;

/**
 * Enum the codes of different fields for scoring
 */
public enum Score {
    PN("PN"),
    SI("SI"),
    FP("FP"),
    MA("MA"),
    S("S"),
    AB("AB"),
    OC("OC"),
    FN("FN"),
    LN("LN"),
    DB("DB"),
    GE("GE"),
    CI("CI"),
    SL("SL"),
    EI("EI"),
    ED("ED"),
    EP("EP"),
    MP("MP"),
    MD("MD"),
    PA("PA"),
    PR("PR"),
    TP("TP"),
    UP("UP"),
    FC("FC"),
    AS("AS"),
    KS("KS"),
    PC("PC"),
    DF("DF"),
    DU("DU"),
    GP("GP"),
    UC("UC"),
    PF("PF"),
    ;

    private final String name;

    Score(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
