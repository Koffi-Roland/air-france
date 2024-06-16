package com.airfrance.ref.util;

/**
 * @deprecated use com.airfrance.repind.util.PhoneNumberUtils instead
 */
@Deprecated
public class PhoneNumberUtils {

    private static final String FORBIDDEN_STRING = "[^0-9]"; // non digit
    
	public static String cleanPhoneNumber(String phoneNumber) {
		return phoneNumber.replaceAll(FORBIDDEN_STRING, "");
	}
	
}
