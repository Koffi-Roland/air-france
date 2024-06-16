package com.airfrance.batch.compref.injestadhocdata.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class InputRecord {

	private String id;
	@NotNull
	private String emailAddress;
	private String gin;
	private String cin;
	private String firstname;
	private String surname;
	private String civility;
	private String birthdate;
	@NotNull
	private String countryCode;
	@NotNull
	private String languageCode;
	@NotNull
	private String subscriptionType;
	@NotNull
	private String domain;
	@NotNull
	private String groupType;
	@NotNull
	private String status;
	@NotNull
	private String source;
	@NotNull
	private String dateOfConsent;
	private String preferredDepartureAirport;

}
