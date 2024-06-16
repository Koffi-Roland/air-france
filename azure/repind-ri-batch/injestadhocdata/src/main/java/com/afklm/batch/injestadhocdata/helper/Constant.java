package com.afklm.batch.injestadhocdata.helper;

public class Constant {

	public static final String CLOSING_SQUARE_BRACKET = "]";
	public static final String COMMA = ", ";
	public static final String DOMAIN = " Domain: ";
	public static final String ERROR = " - ERROR: ";
	public static final String GROUP_TYPE = " Group: ";
	public static final String INCORRECT_DATA = "Incorrect data provided for email : ";
	public static final String INPUT_MISSING = " - Input missing: ";
	public static final String LANGUAGE = "Language: ";
	public static final String MANDATORY_ARGUMENT_MISSING = "Mandatory argument missing : ";
	public static final String MANDATORY_INFORMATION_MISSING = "Mandatory information missing for email: ";
	public static final String MARKET = "Market: ";
	public static final String NEWLINE = " \n";
	public static final String OPEN_SQUARE_BRACKET = " [";
	public static final String PREFERRED_AIRPORT_AF = "departureAirportAF";
	public static final String PREFERRED_AIRPORT_KL = "departureAirportKL";

	public static final String PREFERRED_AIRPORT = "preferredAirport";

	public static final String PROCESSED_SUCCESSFULLY = "Processed successfully : ";
	public static final String PROCESSING_FAILED = "Processing failed : ";
	public static final String PROCESSING_FAILED_FOR_EMAIL = "Processing failed for email: ";
	public static final String SUBSCRIPTION_TYPE = "Subscription: ";
	public static final String TOTAL = "Total : ";
	public static final String TRAVEL_PREFERENCE = "TPC";
	public static final String USER_GUIDE = "\n###\n USER GUIDE: \nBatchInjestAdhocData.sh -option:\n -p input path   : [MANDATORY]\n -o output path   : [MANDATORY]\n -f file.csv : [MANDATORY]\\n ";
	public static final String UTF8BOM = "\uFEFF";
	public static final String VALIDATION_FAILED = "Validation failed : ";
	
	private Constant() {
		throw new IllegalStateException("Utility class");
	}
}
