package com.afklm.repind.common.exception.phone;

public class TooShortPhoneNumberException extends NormalizedPhoneNumberException {
    private static final long serialVersionUID = -8580280604710949730L;

    public TooShortPhoneNumberException(String phoneNumber) {
        super("Too short phone number" + phoneNumber);
    }
}