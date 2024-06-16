package com.afklm.repind.msv.customer.adaptor.helper;

public class Constant {
    public static final String TRIGGER = "customer-adaptor";

    // TABLES listened on KAFKA
    public static final String EMAILS = "EMAILS";
    public static final String INDIVIDUS = "INDIVIDUS";
    public static final String ROLE_CONTRATS = "ROLE_CONTRATS";
    public static final String PREFERENCE = "PREFERENCE";
    public static final String MARKET_LANGUAGE = "MARKET_LANGUAGE";
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
    public static final String STATUT_MEDIUM_I = "I";
    public static final String TYPE_CONTRAT_FP = "FP";
    public static final String TYPE_CONTRAT_MA = "MA";


    // preferences constants*
    public static final String PREFERRED_DESTINATION_CONTINENT = "preferreddestinationcontinent"; // RI field : preferredDestinationContinent
    public static final String PREFERRED_DESTINATION_CITY = "preferreddestinationcity"; // RI field preferredDestinationCity
    public static final String DEPARTURE_AIRPORT_KL = "departureairportkl"; //RI field departureAirportKL
    public static final String HOLIDAY_TYPE = "holidaytype"; // RI field holidayType
    public static final String PREFERRED_AIRPORT = "preferredairport"; // RI field preferredAirport
    public static final String COUNTRY_OF_RESIDENCE = "countryofresidence"; //RI field countryOfResidence


    private Constant() {
        throw new IllegalStateException("Utility class");
    }
}
