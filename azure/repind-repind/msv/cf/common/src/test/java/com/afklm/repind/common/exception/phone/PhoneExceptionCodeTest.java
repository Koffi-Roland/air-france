package com.afklm.repind.common.exception.phone;

import com.afklm.repind.common.model.error.ApiError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class PhoneExceptionCodeTest {

    @Test
    void testClasses() {
        NormalizedPhoneNumberException normalizedPhoneNumberException = new NormalizedPhoneNumberException("message");
        assertEquals("Not normalized phone number exception: message", normalizedPhoneNumberException.getMessage());
        //
        InvalidCountryCodeException invalidCountryCodeException = new InvalidCountryCodeException("message");
        assertEquals("Not normalized phone number exception: Invalid country codemessage", invalidCountryCodeException.getMessage());
        //
        InvalidPhoneNumberException invalidPhoneNumberException = new InvalidPhoneNumberException("message");
        assertEquals("Not normalized phone number exception: Invalid phone numbermessage", invalidPhoneNumberException.getMessage());
        //
        TooShortPhoneNumberException tooShortPhoneNumberException = new TooShortPhoneNumberException("message");
        assertEquals("Not normalized phone number exception: Too short phone numbermessage", tooShortPhoneNumberException.getMessage());
        //
        TooLongPhoneNumberException tooLongPhoneNumberException = new TooLongPhoneNumberException("message");
        assertEquals("Not normalized phone number exception: Too long phone numbermessage", tooLongPhoneNumberException.getMessage());
        }
}
