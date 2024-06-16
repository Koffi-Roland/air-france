package com.airfrance.batch.subscriptiondata.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Individual {

    private String id;
    private String emailAddress;
    private String gin;
    private String cin;
    private String firstname;
    private String surname;
    private String civility;
    private String birthdate;
    private String countryCode;
    private String languageCode;
    private String subscriptionType;
    private String domain;
    private String groupType;
    private String status;
    private String source;

    private String dateOfConsent;
    private String preferredDepartureAirport;

}
