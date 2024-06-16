package com.afklm.repind.common.exception.phone;

public class InvalidPhoneNumberException extends NormalizedPhoneNumberException {
    private static final long serialVersionUID = 5302582003021372082L;

    public InvalidPhoneNumberException(String phoneNumber) {
        super("Invalid phone number" + phoneNumber);
    }
}
