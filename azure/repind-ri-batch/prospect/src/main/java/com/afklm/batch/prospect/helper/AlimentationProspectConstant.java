package com.afklm.batch.prospect.helper;

public class AlimentationProspectConstant {

    private AlimentationProspectConstant() {

        throw new IllegalStateException("Utility class");
    }

    public static final String EMAIL = "email";
    public static final String GIN = "gin";
    public static final String FB_NUMBER = "fbNumber";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String CIVILITY = "civility";
    public static final String DATE_OF_BIRTH = "dateOfBirth";
    public static final String COUNTRY_CODE = "countryCode";
    public static final String LANGUAGE_CODE = "languageCode";
    public static final String SUBSCRIPTION_TYPE = "subscriptionType";
    public static final String OPTIN = "optin";
    public static final String SOURCE = "source";
    public static final String DATE_OF_CONSENT = "dateOfConsent";
    public static final String DEPARTURE_AIRPORT = "departureAirport";

    // File Param
    public static final String FILE_DELIMITER = ";";
    public static final String ARGS_SEPARATOR = "-";
    public static final String ARG_INPUT = "inputFile";

    // WS constants
    public static final String WS_PROSPECT_PROCESS = "W";
    public static final String B2C = "B2C";
    public static final String CREATION_CONTEXT = "B2C_BOOKING_PROCESS";
    public static final String SIGNATURE_WS = "ALIM-WW-BATCH";
    public static final String SITE_WS = "BATCH_QVI";
    public static final String SALES_DOMAIN = "S";
    public static final String NEWSLETTER_TYPE = "N";
    public static final String TRAVEL_PREF = "TPC";
    public static final String PREFERRED_AIRPORT = "preferredAirport";

    // Batch usage
    public static final String EM_ADR_LABEL = "Email_Address";
    public static final String REPORT_FILENAME = "alimentation_prospect_report.txt";
}
