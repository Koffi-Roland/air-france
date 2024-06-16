package com.afklm.repind.common.exception.phone;

public class NormalizedPhoneNumberException extends Exception {
    private static final long serialVersionUID = 1351960823577812516L;

    public NormalizedPhoneNumberException(String msg) {
        super("Not normalized phone number exception: " + msg);
    }

    public NormalizedPhoneNumberException(String errorMsg, String errorVal) {
        super(errorMsg + " : " + errorVal);
    }
}
