package com.airfrance.batch.compref.injestadhocdata.enums;

public enum InputFieldEnum {
	EMAIL("email"),
	GIN("gin"),
	CIN("cin"),
	FIRSTNAME("firstname"),
	LASTNAME("lastname"),
	CIVILITY("civility"),
	BIRTHDATE("birthdate"),
	COUNTRY_CODE("countryCode"),
	LANGUAGE_CODE("languageCode"),
	SUBSCRIPTION_TYPE("subscriptionType"),
	DOMAIN("domain"),
	GROUP_TYPE("group"),
	STATUS("status"),
	SOURCE("source"),
	DATE_OF_CONSENT("dateOfConsent"),
	PREFERRED_DEPARTURE_AIRPORT("preferrredDepartureAirport");
	
	private final String value;

	InputFieldEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
