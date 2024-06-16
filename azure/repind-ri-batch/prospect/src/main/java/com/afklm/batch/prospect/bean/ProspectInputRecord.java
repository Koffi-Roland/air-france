package com.afklm.batch.prospect.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ProspectInputRecord {

    private String email;
    private String gin;
    private String fbNumber;
    private String firstName;
    private String lastName;
    private String civility;
    private LocalDate dateOfBirth;
    private String countryCode;
    private String languageCode;
    private String subscriptionType;
    private String optin;
    private String source;
    private LocalDate dateOfConsent;
    private String departureAirport;

}
