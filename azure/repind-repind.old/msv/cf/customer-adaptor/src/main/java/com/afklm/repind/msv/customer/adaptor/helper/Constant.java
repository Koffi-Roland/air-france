package com.afklm.repind.msv.customer.adaptor.helper;

public class Constant {
    public static final String TRIGGER = "customer-adaptor";

    // TABLES listened on KAFKA
    public static final String EMAILS = "EMAILS";
    public static final String INDIVIDUS = "INDIVIDUS";
    public static final String ROLE_CONTRATS = "ROLE_CONTRATS";
    public static final String PREFERENCE = "PREFERENCE";
    public static final String MARKET_LANGUAGE = "MARKET LANGUAGE";
    public static final String ADR_POST ="ADR_POST";

    // payload specific fields
    public static final String TABLE_NAME = "TABLE_NAME";
    public static final String CONTENT_DATA ="CONTENT_DATA";
    public static final String GIN ="GIN";
    public static final String ACTION_TYPE ="ACTION_TYPE";

    // fields
    public static final String CODE_MEDIUM_P = "P";
    public static final String CODE_MEDIUM_D = "D";
    public static final String STATUT_MEDIUM_V = "V";
    public static final String TYPE_CONTRAT_FP = "FP";
    public static final String TYPE_CONTRAT_MA = "MA";

    private Constant() {
        throw new IllegalStateException("Utility class");
    }
}
