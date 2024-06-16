package com.afklm.rigui.model.error;

public class BusinessErorMessageList {

	public static final String ERROR_MESSAGE_400     = "Missing Request Parameter";
	public static final String ERROR_MESSAGE_403_001 = "Access forbidden to call MS and get data";
	public static final String ERROR_MESSAGE_404_001 = "Gin or frequentFlyerNumber not found";
	public static final String ERROR_MESSAGE_404_002 = "Individual not found";
	public static final String ERROR_MESSAGE_404_003 = "Data not found";
	public static final String ERROR_MESSAGE_412     = "Business constraint violation";
	public static final String ERROR_MESSAGE_412_001 = "Mismatch type parameter";
	public static final String ERROR_MESSAGE_412_002 = "Invalid value for the 'status' parameter";
	public static final String ERROR_MESSAGE_412_003 = "Gin or FrequentFlyerNumber must not be empty";
	public static final String ERROR_MESSAGE_412_004 = "Invalid value for the 'gin' parameter, length must be equal to 12";
	public static final String ERROR_MESSAGE_412_005 = "An individual can not merge himself";
	public static final String ERROR_MESSAGE_412_006 = "Can only merge FB and FB";
	public static final String ERROR_MESSAGE_412_007 = "Invalid status for merge";
	public static final String ERROR_MESSAGE_412_008 = "Invalid business rule";
	public static final String ERROR_MESSAGE_412_009 = "Invalid value";
	public static final String ERROR_MESSAGE_412_010 = "Business unknown error";
	public static final String ERROR_MESSAGE_412_011 = "Too many addresses after merging (Max: 5)";
	public static final String ERROR_MESSAGE_412_012 = "Can't finalize merge, error S04600";
	public static final String ERROR_MESSAGE_412_013 = "Can't merge GP";
	public static final String ERROR_MESSAGE_412_014 = "Can't finalize merge, error S06880";
	public static final String ERROR_MESSAGE_412_015 = "You don't have the habilitation to merge this kind of individual!";
	public static final String ERROR_MESSAGE_412_016 = "Merge not allowed";
	public static final String ERROR_MESSAGE_412_017 = "The direction of merge can not be defined automatically";
	public static final String ERROR_MESSAGE_412_018 = "FB of first GIN can not be merged";
	public static final String ERROR_MESSAGE_412_019 = "FB of second GIN can not be merged";
	public static final String ERROR_MESSAGE_412_020 = "FB of first and second GIN can not be merged";
	public static final String ERROR_MESSAGE_412_021 = "None of the customers has FB contract";
	public static final String ERROR_MESSAGE_412_022 = "A mandatory parameter is missing in input";
	public static final String ERROR_MESSAGE_412_023 = "[WS] CreateUpdateIndividual - invalid email";
	public static final String ERROR_MESSAGE_412_024 = "[WS] CreateUpdateIndividual - invalid country code";
	public static final String ERROR_MESSAGE_412_025 = "[WS] CreateUpdateIndividual - invalid phone number: too short";
	public static final String ERROR_MESSAGE_412_026 = "[WS] CreateUpdateIndividual - invalid phone number: too long";
	public static final String ERROR_MESSAGE_412_027 = "[WS] CreateUpdateIndividual - invalid phone number";
	public static final String ERROR_MESSAGE_412_028 = "A technical error (database, network…) occurred";
	public static final String ERROR_MESSAGE_412_029 = "Postal address country does not exist";
	public static final String ERROR_MESSAGE_412_030 = "A parameter is invalid or did not match the backend checkings";
	public static final String ERROR_MESSAGE_412_079 = "Normalization process failed for postal address";
	public static final String ERROR_MESSAGE_412_080 = "Missing parameter: birthdate or country code required";
	public static final String ERROR_MESSAGE_412_081 = "Too many results";
	public static final String ERROR_MESSAGE_412_082 = "Technical error: invalid parameter";
	public static final String ERROR_MESSAGE_412_083 = "Invalid email (pattern not correct)";
	public static final String ERROR_MESSAGE_412_084 = "Too many GP Roles";
	public static final String ERROR_MESSAGE_412_085 = "Gin cible format is invalid";
	public static final String ERROR_MESSAGE_412_086 = "Gin not found";
	public static final String ERROR_MESSAGE_412_087 = "Gin source format is invalid";
	public static final String ERROR_MESSAGE_412_088 = "An error has occurred during the process";
	public static final String ERROR_MESSAGE_412_089 = "An error has occurred independent from the process";
	public static final String ERROR_MESSAGE_412_090 = "Only GIN with status 'T' could be unmerge!";
	public static final String ERROR_MESSAGE_412_091 = "Only GIN with account status 'D' could be reactivate!";
	public static final String ERROR_MESSAGE_412_092 = "Could not found all individu infos for the given gins";
	public static final String ERROR_MESSAGE_412_093 = "Too many gins found for the given search";
	public static final String ERROR_MESSAGE_412_094 = "Can not have a email identifier and CIN identifier at the same time";
	public static final String ERROR_MESSAGE_500_001 = "Can't access to DB";
	public static final String ERROR_MESSAGE_500_002 = "Can't access to Talend";
	public static final String ERROR_MESSAGE_500_003 = "Technical unknown error";
	public static final String ERROR_MESSAGE_500_004 = "Technical error for providing data";
	public static final String ERROR_MESSAGE_1000    = "Business error generated by backend";
	public static final String ERROR_MESSAGE_1007 = "[HACHIKO] The direction of merge can not be defined automatically ";

}
