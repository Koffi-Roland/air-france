package com.afklm.repind.common.exception.phone;

public class InvalidCountryCodeException extends NormalizedPhoneNumberException {
    private static final long serialVersionUID = 5302582003021372082L;

    public InvalidCountryCodeException(String countryCode) {
        super("Invalid country code" + countryCode);
    }
}
