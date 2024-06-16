package com.airfrance.repind.util;

public class PhoneNumberUtils {

    private static final String FORBIDDEN_STRING = "[^0-9]"; // non digit
    
	public static String cleanPhoneNumber(String phoneNumber) {
		return phoneNumber.replaceAll(FORBIDDEN_STRING, "");
	}
	
}
