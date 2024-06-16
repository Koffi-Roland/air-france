package com.afklm.repind.common.exception.phone;

public class TooLongPhoneNumberException extends NormalizedPhoneNumberException {
    private static final long serialVersionUID = -8580280604710949730L;

    public TooLongPhoneNumberException(String phoneNumber) {
        super("Too long phone number" + phoneNumber);
    }
}
