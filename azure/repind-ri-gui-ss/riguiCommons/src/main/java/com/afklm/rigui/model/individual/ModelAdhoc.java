package com.afklm.rigui.model.individual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelAdhoc {

    private Integer id;
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
