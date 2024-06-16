package com.airfrance.batch.subscriptiondata.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SharePointItemsFields {

    private String id;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("GIN")
    private String gin;

    @JsonProperty("CIN")
    private String cin;

    @JsonProperty("Firstname")
    private String firstname;

    @JsonProperty("Surname")
    private String surname;

    @JsonProperty("Civility")
    private String civility;

    @JsonProperty("Birthdate")
    private String birthdate;

    @JsonProperty("Country_Code")
    private String countryCode;

    @JsonProperty("Language_Code")
    private String languageCode;

    @JsonProperty("Subscription_Type")
    private String subscriptionType;

    @JsonProperty("Domain")
    private String domain;

    @JsonProperty("Group_type")
    private String groupType;

    @JsonProperty("Statut")
    private String statut;

    @JsonProperty("Sources")
    private String sources;

    @JsonProperty("DateOfConsent")
    private String dateOfConsent;

    @JsonProperty("Preferred_Departure_Airport")
    private String preferredDepartureAirport;

    @JsonProperty("Flag")
    private String flag;
}
