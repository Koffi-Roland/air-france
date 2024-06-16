package com.afklm.rigui.enums;

public enum AdhocColumnEnum {


    EMAIL("EMAIL_ADDRESS"),
    GIN("GIN"),
    CIN("CIN"),
    FIRSTNAME("FIRSTNAME"),
    LASTNAME("LASTNAME"),
    SURNAME("SURNAME"),
    CIVILITY("CIVILITY"),
    BIRTHDATE("BIRTHDATE"),
    COUNTRY_CODE("COUNTRY_CODE"),
    LANGUAGE_CODE("LANGUAGE_CODE"),
    SUBSCRIPTION_TYPE("SUBSCRIPTION_TYPE"),
    DOMAIN("DOMAIN"),
    GROUP_TYPE("GROUP_TYPE"),
    STATUS("STATUS"),
    SOURCE("SOURCE"),
    DATE_OF_CONSENT("DATE_OF_CONSENT"),
    PREFERRED_DEPARTURE_AIRPORT("PREFERRED_DEPARTURE_AIRPORT");

    private final String column;

    public String column() {
        return column;
    }

    private AdhocColumnEnum(String column) {
        this.column = column;
    }
}
