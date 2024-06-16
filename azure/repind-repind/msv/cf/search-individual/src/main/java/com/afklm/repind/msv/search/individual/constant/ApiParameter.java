package com.afklm.repind.msv.search.individual.constant;

/**
 * Constant class with api parameter
 */
public final class ApiParameter {
    public static final String CIN = "cin";
    public static final String EMAIL = "email";
    public static final String INTERNATIONAL_PHONE_NUMBER = "internationalPhoneNumber";
    public static final String EXTERNAL_IDENTIFIER_ID = "externalIdentifierId";
    public static final String EXTERNAL_IDENTIFIER_TYPE = "externalIdentifierType";
    public static final String FIRST_NAME = "firstname";
    public static final String LAST_NAME = "lastname";
    public static final String MERGE = "merge";

    private ApiParameter() {
        throw new IllegalStateException("Utility class");
    }
}
